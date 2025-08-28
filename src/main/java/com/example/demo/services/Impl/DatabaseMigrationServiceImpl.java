package com.example.demo.services.Impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.example.demo.exception.ResourceTableNotFound;
import com.example.demo.exception.ResourceTablePathNotFormed;
import com.example.demo.model.QueryPreparedStatementCreator;
import com.example.demo.model.SQLColumn;
import com.example.demo.model.TestBooleanResultSetExtractor;
import com.example.demo.services.DatabaseMigrationService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DatabaseMigrationServiceImpl implements DatabaseMigrationService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private ResourceLoader resourceLoader;

    @Setter
    private String databaseMigrationTablesPath = "database/migration/table";

    public boolean hasColumn(String schema, String table, String column) {
        Map<String, SQLColumn> columns = this.getTableColumns(schema, table);
        return columns != null && columns.containsKey(column);
    }

    public Map<String, SQLColumn> getTableColumns(String schema, String table) {
        final Map<String, SQLColumn> columns = new HashMap();
        this.jdbcTemplate.query("select * from " + schema + "." + table, new ResultSetExtractor<Integer>() {
            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();

                for(int i = 1; i <= columnCount; ++i) {
                    SQLColumn column = new SQLColumn();
                    column.setName(rsmd.getColumnName(i));
                    column.setType(rsmd.getColumnTypeName(i));
                    column.setTypeCode(rsmd.getColumnType(i));
                    column.setSize(rsmd.getColumnDisplaySize(i));
                    columns.put(column.getName(), column);
                }

                return columnCount;
            }
        });

        return columns;
    }

    public void createColumn(String schema, String table, String column, String dataType) {
        if (!this.hasColumn(schema, table, column)) {
            this.jdbcTemplate.execute("ALTER TABLE " + schema + "." + table + " ADD COLUMN " + column + " " + dataType);
        }
    }

    public void updateColumnType(String schema, String table, String column, String dataType) {
        if (this.hasColumn(schema, table, column)) {
            this.jdbcTemplate.execute("ALTER TABLE " + schema + "." + table + " ALTER COLUMN " + column + " TYPE " + dataType);
        }
    }

    public void executeSql(String request) {
        if (!request.trim().isEmpty()) {
            this.jdbcTemplate.execute(request);
        }
    }

    public void executeSql(InputStream is) throws IOException {
        String request = this.readFile(is);
        this.executeSql(request);
    }

    public void executeSqlFromClasspath(String classPathFile) throws IOException {
        Resource resourceDataSqlFile = this.resourceLoader.getResource("classpath:" + classPathFile);
        String request = this.readFile(resourceDataSqlFile.getInputStream());
        this.executeSql(request);
    }

    public void createTableFromResource(String schema, String table) {
        this.createTableFromResource(schema, table, this.databaseMigrationTablesPath);
    }

    public void createTableFromResource(String schema, String table, String resourceTablePath) {
        try {
            if (resourceTablePath == null) {
                throw new ResourceTablePathNotFormed();
            }

            if (resourceTablePath.startsWith("/") && resourceTablePath.length() > 1) {
                resourceTablePath = resourceTablePath.substring(1);
            }

            if (resourceTablePath.endsWith("/") && resourceTablePath.length() > 1) {
                resourceTablePath = resourceTablePath.substring(0, resourceTablePath.length() - 1);
            }

            Resource r = this.resourceLoader.getResource("classpath:" + resourceTablePath + "/" + schema + "_" + table + ".tab");
            if (r == null) {
                throw new ResourceTableNotFound();
            }

            this.createTable(schema, table, r.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional(rollbackFor = {FileNotFoundException.class})
    public void createTable(String schema, String table, InputStream is) throws Exception {
        String requestString = this.readFile(is);
        if (!requestString.trim().isEmpty()) {
            this.createTable(schema, table, requestString.toString());
        }
    }

    private String readFile(InputStream is) throws IOException {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        try {
            br = new BufferedReader(new InputStreamReader(is));
            String line = null;

            while((line = br.readLine()) != null) {
                sb.append(line);
            }
        } finally {
            if (br != null) {
                br.close();
            }

        }

        return sb.toString();
    }

    public void createTable(String schema, String table, String request) throws Exception {
        if (!this.isTableExist(schema, table)) {
            if (!this.isSchemaExist(schema)) {
                this.createSchema(schema);
            }

            this.executeSql(request);
        }
    }

    public boolean isTableExist(final String schema, final String table) {
        String request = "SELECT tablename FROM pg_tables WHERE schemaname = '" + schema + "' AND tablename='" + table + "'";
        ResultSetExtractor<Boolean> rse = new TestBooleanResultSetExtractor();
        QueryPreparedStatementCreator query = new QueryPreparedStatementCreator(request);
        Boolean test = (Boolean)this.jdbcTemplate.query(query, rse);
        return test != null && test;
    }

    public void updateColumnWithUniqueConstraint(String schema, String table, String column) {
        if (!this.hasColummnConstraint(schema, table, column + "_unique")) {
            String reqConstraint = "ALTER TABLE " + schema + "." + table + " ADD CONSTRAINT " + column + "_unique" + " UNIQUE " + "(" + column + ")";
            this.jdbcTemplate.execute(reqConstraint);
        }
    }

    public boolean isColumnExist(String schema, String table, String column) {
        return this.hasColumn(schema, table, column);
    }

    public void deleteColumnUniqueConstraint(String schema, String table, String column) {
        if (this.hasColummnConstraint(schema, table, column)) {
            table = schema + "." + table;
            String req = "ALTER TABLE " + table + " DROP CONSTRAINT " + column;
            this.jdbcTemplate.execute(req);
        }
    }

    public void deleteColumn(String schema, String table, String column) {
        table = schema + "." + table;
        String req = "ALTER TABLE " + table + " DROP COLUMN IF EXISTS " + column;
        this.jdbcTemplate.execute(req);
    }

    public void deleteTableConstraint(String schema, String table, String constraint) {
        this.deleteConstraintIfExists(schema, table, constraint);
    }

    private void deleteConstraintIfExists(String schema, String table, String constraint) {
        table = schema + "." + table;
        String req = "ALTER TABLE " + table + " DROP CONSTRAINT IF EXISTS " + constraint;
        this.jdbcTemplate.execute(req);
    }

    public boolean hasColummnConstraint(String schema, String table, String column) {
        String request = "SELECT constraint_name FROM information_schema.constraint_column_usage WHERE  table_schema= '" + schema + "' and table_name = '" + table + "' and constraint_name='" + column + "'";
        ResultSetExtractor<Boolean> rse = new TestBooleanResultSetExtractor();
        QueryPreparedStatementCreator query = new QueryPreparedStatementCreator(request);
        Boolean test = (Boolean)this.jdbcTemplate.query(query, rse);
        return test != null && test;
    }

    public boolean hasTableConstraint(String schema, String table, String constraint) {
        String request = "SELECT constraint_name FROM information_schema.constraint_table_usage WHERE  table_schema= '" + schema + "' and table_name = '" + table + "' and constraint_name='" + constraint + "'";
        ResultSetExtractor<Boolean> rse = new TestBooleanResultSetExtractor();
        QueryPreparedStatementCreator query = new QueryPreparedStatementCreator(request);
        Boolean test = (Boolean)this.jdbcTemplate.query(query, rse);
        return test != null && test;
    }

    public boolean isSchemaExist(String schema) {
        String request = "SELECT schema_name FROM information_schema.schemata WHERE schema_name ='" + schema + "'";
        ResultSetExtractor<Boolean> rse = new TestBooleanResultSetExtractor();
        QueryPreparedStatementCreator query = new QueryPreparedStatementCreator(request);
        Boolean test = (Boolean)this.jdbcTemplate.query(query, rse);
        return test != null && test;
    }

    public void createSchema(String schema) {
        if (!this.isSchemaExist(schema)) {
            String req = "CREATE SCHEMA " + schema;
            this.jdbcTemplate.execute(req);
        }

    }
}
