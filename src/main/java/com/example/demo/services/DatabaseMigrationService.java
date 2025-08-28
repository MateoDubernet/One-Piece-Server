package com.example.demo.services;

import com.example.demo.model.SQLColumn;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public interface DatabaseMigrationService {
    boolean isSchemaExist(String schema);

    void createSchema(String schema);

    boolean hasColumn(String schema, String table, String column);

    Map<String, SQLColumn> getTableColumns(String schema, String table);

    void createColumn(String schema, String table, String column, String dataType);

    void executeSql(String request);

    void updateColumnType(String schema, String table, String column, String dataType);

    void createTableFromResource(String schema, String table);

    void createTableFromResource(String schema, String table, String resourceTablePath);

    void createTable(String schema, String table, InputStream is) throws Exception;

    boolean isTableExist(String schema, String table);

    void createTable(String schema, String table, String request) throws Exception;

    void updateColumnWithUniqueConstraint(String schema, String table, String column);

    boolean isColumnExist(String schema, String table, String column);

    void deleteColumnUniqueConstraint(String schema, String table, String column);

    boolean hasColummnConstraint(String schema, String table, String column);

    boolean hasTableConstraint(String schema, String table, String column);

    void deleteTableConstraint(String schema, String table, String constraint);

    void deleteColumn(String schema, String table, String column);

    void executeSql(InputStream is) throws IOException;

    void executeSqlFromClasspath(String classpathFile) throws IOException;

    void setDatabaseMigrationTablesPath(String path);
}
