package com.ss.utopia.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String username = "root";
    private static final String password = "Ragnarok"; //localhost only
    private static final String url = "jdbc:mysql://localhost:3306/utopia";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public static Connection getTransaction() throws SQLException {
        Connection conn = DriverManager.getConnection(url, username, password);
        conn.setAutoCommit(false);
        return conn;
    }
}
