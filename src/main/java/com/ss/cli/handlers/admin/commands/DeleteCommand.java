package com.ss.cli.handlers.admin.commands;

import com.ss.cli.handlers.Command;
import com.ss.cli.handlers.CommandUtil;
import com.ss.cli.handlers.ConsoleHandler;
import com.ss.cli.handlers.ExitHandler;
import com.ss.service.admin.AdminExecutorDeletes;

import java.util.LinkedHashMap;

public class DeleteCommand {

    public Command createDelete() {
        return args -> {
            LinkedHashMap<String, String> params = CommandUtil.constructArgs(args);
            //first argument is table, only one table can be searched at a time
            String[] keys = params.keySet().toArray(new String[0]);

            String firstKey;
            if (keys.length == 0) firstKey = "invalid";
            else firstKey = keys[0];
            params.remove(firstKey);

            ConsoleHandler returnVal;
            switch (firstKey) {
                case "flight" -> returnVal = deleteFlight(params);
                case "booking" -> returnVal = deleteBooking(params);
                case "airport" -> returnVal = deleteAirport(params);
                case "traveler", "agent" -> returnVal = deleteUser(params);
                default -> {
                    System.err.println("No table specified");
                    returnVal = new ExitHandler(ExitHandler.ExitType.FAILED);
                }
            }
            return returnVal;
        };
    }

    /*
    -traveler
        -id value
    -agent
        -id value
    -airport
        -iata_id value
    -booking
        -id value
    -flight
        -id value
     */

    private ConsoleHandler deleteUser(LinkedHashMap<String, String> params) {
        //look for -id
        if (params.get("id") == null) {
            System.err.println("Missing id");
            return new ExitHandler(ExitHandler.ExitType.FAILED);
        }

        try {
            int id = Integer.parseInt(params.get("id"));
            if (new AdminExecutorDeletes().deleteUser(id)) {
                System.out.println("Delete successful");
                return new ExitHandler(ExitHandler.ExitType.NOTHING);
            }
        } catch (Exception ignored) {
        }
        System.err.println("Delete failed");
        return new ExitHandler(ExitHandler.ExitType.FAILED);
    }

    private ConsoleHandler deleteAirport(LinkedHashMap<String, String> params) {
        //look for -id
        if (params.get("id") == null) {
            System.err.println("Missing id");
            return new ExitHandler(ExitHandler.ExitType.FAILED);
        }
        if (new AdminExecutorDeletes().deleteAirport(params.get("id"))) {
            System.out.println("Delete successful");
            return new ExitHandler(ExitHandler.ExitType.NOTHING);
        }
        System.err.println("Delete failed");
        return new ExitHandler(ExitHandler.ExitType.FAILED);
    }

    private ConsoleHandler deleteBooking(LinkedHashMap<String, String> params) {
        //look for -id
        if (params.get("id") == null) {
            System.err.println("Missing id");
            return new ExitHandler(ExitHandler.ExitType.FAILED);
        }

        try {
            int id = Integer.parseInt(params.get("id"));
            if (new AdminExecutorDeletes().deleteBooking(id)) {
                System.out.println("Delete successful");
                return new ExitHandler(ExitHandler.ExitType.NOTHING);
            }
        } catch (Exception ignored) {
        }
        System.err.println("Delete failed");
        return new ExitHandler(ExitHandler.ExitType.FAILED);
    }

    private ConsoleHandler deleteFlight(LinkedHashMap<String, String> params) {
        //look for -id
        if (params.get("id") == null) {
            System.err.println("Missing id");
            return new ExitHandler(ExitHandler.ExitType.FAILED);
        }

        try {
            int id = Integer.parseInt(params.get("id"));
            if (new AdminExecutorDeletes().deleteFlight(id)) {
                System.out.println("Delete successful");
                return new ExitHandler(ExitHandler.ExitType.NOTHING);
            }
        } catch (Exception ignored) {
        }
        System.err.println("Delete failed");
        return new ExitHandler(ExitHandler.ExitType.FAILED);
    }
}
