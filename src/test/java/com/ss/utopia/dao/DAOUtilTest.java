package com.ss.utopia.dao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DAOUtilTest {

    @Test
    void constructSQLSearchString() {
        String result = DAOUtil.constructSQLSearchString("flight", new String[]{"id", "route_id"});
        assertEquals("SELECT * FROM flight WHERE id = ? AND route_id = ?", result);
    }
}