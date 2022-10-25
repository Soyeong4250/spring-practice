package com.sping.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class AWSConnectionMaker implements ConnectionMaker {
    private static final Map<String, String> env = System.getenv();
    private static final String dbHost = env.get("DB_HOST");
    private static final String dbUser = env.get("DB_USER");
    private static final String dbPassword = env.get("DB_PASSWORD");

    public AWSConnectionMaker() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbHost, dbUser, dbPassword);
    }
}
