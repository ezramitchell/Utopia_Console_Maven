package com.ss.utopia.dao;

import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Base DAO object
 *
 * @param <T>
 */
public abstract class BaseDAO<T> {

    private final Connection conn;

    public BaseDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Execute sql update
     *
     * @param sql  sql to update, use ? to insert values
     * @param args arguments to replace ?'s
     * @throws SQLException invalid data or server failure
     */
    public Integer save(String sql, @Nullable Object[] args) throws SQLException {
        PreparedStatement stm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        if (args != null) {
            int i = 1;
            for (Object arg : args) {
                stm.setObject(i, arg);
                i++;
            }
        }
        stm.executeUpdate();
        ResultSet rs = stm.getGeneratedKeys();

        if (rs.next()) {
            return rs.getInt(1);
        }
        return null;
    }

    /**
     * Execute sql query
     *
     * @param sql  SQL to execute, use ? to insert values
     * @param args arguments to replace ?'s
     * @return List of specified generic
     * @throws SQLException invalid data or server failure
     */
    List<T> read(String sql, @Nullable Object[] args) throws SQLException {
        PreparedStatement stm = conn.prepareStatement(sql);
        if (args != null) {
            int i = 1;
            for (Object arg : args) {
                stm.setObject(i, arg);
                i++;
            }
        }
        return extractData(stm.executeQuery());
    }

    protected String[] getColumnNames(String table) throws SQLException {
        PreparedStatement stm = conn.prepareStatement("SELECT * FROM " + table + " LIMIT 1");
        ResultSetMetaData rsmd = stm.executeQuery().getMetaData();
        List<String> columns = new ArrayList<>();
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
            columns.add(rsmd.getColumnName(i));
        }
        return columns.toArray(new String[0]);
    }

    protected abstract List<T> extractData(ResultSet rs) throws SQLException;
}
