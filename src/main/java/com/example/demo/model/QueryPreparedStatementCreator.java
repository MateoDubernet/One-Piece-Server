package com.example.demo.model;

import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QueryPreparedStatementCreator implements PreparedStatementCreator {
    private String request;

    private QueryPreparedStatementCreator() {
    }

    public QueryPreparedStatementCreator(String request) {
        this.request = request;
    }

    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement(this.request, 1005, 1007);
        return ps;
    }
}
