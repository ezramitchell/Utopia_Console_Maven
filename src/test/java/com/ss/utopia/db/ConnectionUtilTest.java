package com.ss.utopia.db;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionUtilTest {

    @Test
    void getConnection() {
        try{
            ConnectionUtil.getConnection();
        } catch (SQLException throwables) {
            fail();
        }
    }

    @Test
    void getTransaction() {
        try{
            Connection c = ConnectionUtil.getTransaction();
            assertFalse(c.getAutoCommit());
        } catch (SQLException throwables) {
            fail();
        }
    }
}