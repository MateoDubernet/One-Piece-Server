package com.example.demo.model;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestBooleanResultSetExtractor implements ResultSetExtractor<Boolean>  {
    public Boolean extractData(ResultSet rs) throws SQLException, DataAccessException {
        if (rs != null && rs.last()) {
            rs.close();
            return true;
        } else {
            rs.close();
            return false;
        }
    }
}
