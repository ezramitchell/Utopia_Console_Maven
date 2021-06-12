package com.ss.utopia.util;

import java.util.LinkedHashMap;
import java.util.StringJoiner;

public class SQLUtil {

    public static String constructSqlSearch(String table, int searchParams){
        StringJoiner joiner = new StringJoiner(" AND ");
        for (int i = 0; i < searchParams; i++) {
            joiner.add("? = ?");
        }
        return String.format("SELECT * FROM %s WHERE %s", table, joiner);
    }

    public static Object[] collapseMap(LinkedHashMap<String, String> map){
        String[] keys = map.keySet().toArray(new String[0]);
        String[] values = map.values().toArray(new String[0]);

        String[] alt = new String[map.size() * 2];
        int keyI = 0;
        int valI = 0;
        for (int i = 0; i < alt.length; i++) {
            if(i % 2 == 0){
                alt[i] = keys[keyI];
                keyI++;
            } else{
                alt[i] = values[valI];
                valI++;
            }
        }

        return alt;
    }
}
