package com.ss.utopia.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class SQLUtilTest {

    @Test
    void constructSqlSearch() {

        String expected = "SELECT * FROM table WHERE ? = ? AND ? = ?";
        assertEquals(expected, SQLUtil.constructSqlSearch("table", 2));

        expected = "SELECT * FROM table WHERE ? = ? AND ? = ? AND ? = ?";
        assertEquals(expected, SQLUtil.constructSqlSearch("table", 3));
    }

    @Test
    void collapseMap() {

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("Key1", "value1");
        map.put("Key2", "value2");

        assertArrayEquals(SQLUtil.collapseMap(map), new Object[]{"Key1", "value1", "Key2", "value2"});
    }
}