package com.ss.cli.handlers;

import java.util.LinkedHashMap;

public class CommandUtil {
    public static LinkedHashMap<String, String> constructArgs(String[] args) {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        String lastParam = null;
        //iterate values in args
        for (String arg : args) {
            if (arg.charAt(0) == '-') {//if starts with a dash add to keys
                arg = arg.replace("-", "");
                params.put(arg, null);
                lastParam = arg;
            } else if (lastParam != null) {//add non dash value as param to previous dash
                params.replace(lastParam, arg);
                lastParam = null;
            }
        }
        return params;
    }
}
