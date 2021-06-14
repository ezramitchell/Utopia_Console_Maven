package com.ss.utopia.dao;

import java.util.StringJoiner;

class DAOUtil {

    public static String constructSQLSearchString(String table, String[] columns) {
        String prefix = "SELECT * FROM " + table + " WHERE ";
        //for each param add "column_name = ?"
        StringJoiner joiner = new StringJoiner(" AND ", prefix, "");
        for (String s : columns) {
            joiner.add(s + " = ?");
        }
        return joiner.toString();
    }
}
