package com.ss.service;

import com.ss.utopia.db.ConnectionUtil;
import com.ss.utopia.entity.User;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationExecutorTest {

    @Test
    void authenticate() {
        AuthenticationExecutor ae = new AuthenticationExecutor();
        User testUser = ae.authenticate("aa", "aa", null);
        assertEquals(testUser.getRole().getName(), "Administrator");
        assertEquals(testUser.getUsername(), "aa");
        assertEquals(testUser.getPassword(), "aa");
    }
}