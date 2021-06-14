package com.ss.cli.handlers.admin.commands;

import com.ss.cli.handlers.Command;
import com.ss.cli.handlers.CommandUtil;
import com.ss.cli.handlers.ConsoleHandler;
import com.ss.cli.handlers.ExitHandler;
import com.ss.service.admin.AdminExecutorDeletes;
import com.ss.service.admin.AdminExecutorSearch;
import com.ss.service.admin.AdminExecutorUpdates;
import com.ss.utopia.entity.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;

public class UpdateCommand {

    public Command createUpdate() {
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
                case "flight" -> returnVal = updateFlight(params);
                case "booking" -> returnVal = updateBooking(params);
                case "airport" -> returnVal = updateAirport(params);
                case "traveler", "agent" -> returnVal = updateUser(params);
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
        -id     value
        -given_name     value
        -family_name    value
        -username   value
        -email      value
        -password   value
        -phone      value
    -agent
        -id     value
        -given_name     value
        -family_name    value
        -username   value
        -email      value
        -password   value
        -phone      value
    -airport
        -id     id to update
        -iata_id     new id
        -city   "city"
    -booking
        -id     value
        -active     1 or 0
        -confirmation   value
        -seat_type  value
    -flight
        -id     value
        -route  route_id
        -airplane   id
        -departure      yyyy-MM-dd HH:mm:ss local timezone
     */

    private ConsoleHandler updateBooking(LinkedHashMap<String, String> params) {
        //look for -id
        if (params.get("id") == null) {
            System.err.println("Missing id");
            return new ExitHandler(ExitHandler.ExitType.FAILED);
        }

        try {
            int id = Integer.parseInt(params.get("id")); //create booking
            Booking booking = new Booking();
            booking.setId(id);
            /*
            -id     value
            -active     1 or 0
            -confirmation   value
            -seat_type  value
             */
            //add update parameters
            if(params.get("active") != null) booking.setActive(Integer.parseInt(params.get("active")));
            if(params.get("confirmation") != null) booking.setConfirmationCode(params.get("confirmation"));
            if(params.get("seat_type") != null) booking.setSeatType(params.get("seat_type"));
            //update booking
            if(new AdminExecutorUpdates().updateBooking(booking)){
                System.out.println("Update succeeded");
                return new ExitHandler(ExitHandler.ExitType.NOTHING);
            }
        } catch (Exception ignored) {
        }
        System.err.println("Update failed");
        return new ExitHandler(ExitHandler.ExitType.FAILED);
    }

    private ConsoleHandler updateAirport(LinkedHashMap<String, String> params) {
        //look for -id
        if (params.get("id") == null) {
            System.err.println("Missing id");
            return new ExitHandler(ExitHandler.ExitType.FAILED);
        }
        //create airport
        Airport airport = new Airport();
        /*
        -id     id to update
        -iata_id     new id
        -city   "city"
         */
        //add update parameters
        if(params.get("iata_id") != null) airport.setIataId(params.get("iata_id"));
        if(params.get("city") != null) airport.setCity(params.get("city"));
        //update airport
        if(new AdminExecutorUpdates().updateAirport(airport, params.get("id"))) {
            System.out.println("Update succeeded");
            return new ExitHandler(ExitHandler.ExitType.NOTHING);
        }
        System.err.println("Update failed");
        return new ExitHandler(ExitHandler.ExitType.FAILED);
    }

    private ConsoleHandler updateUser(LinkedHashMap<String, String> params) {
        //look for -id
        if (params.get("id") == null) {
            System.err.println("Missing id");
            return new ExitHandler(ExitHandler.ExitType.FAILED);
        }

        try {
            int id = Integer.parseInt(params.get("id")); //create user
            User user = new User();
            user.setId(id);
            /*
            -id value
            -given_name     value
            -family_name    value
            -username   value
            -email      value
            -password   value
            -phone      value
             */
            //add update parameters
            if(params.get("given_name") != null) user.setGivenName(params.get("given_name"));
            if(params.get("family_name") != null) user.setFamilyName(params.get("family_nam"));
            if(params.get("username") != null) user.setUsername(params.get("username"));
            if(params.get("email") != null) user.setEmail(params.get("email"));
            if(params.get("password") != null) user.setPassword(params.get("password"));
            if(params.get("phone") != null) user.setPhone(params.get("phone"));
            //update user
            if(new AdminExecutorUpdates().updateUser(user)){
                System.out.println("Update succeeded");
                return new ExitHandler(ExitHandler.ExitType.NOTHING);
            }
        } catch (Exception ignored) {
        }
        System.err.println("Update failed");
        return new ExitHandler(ExitHandler.ExitType.FAILED);
    }

    private ConsoleHandler updateFlight(LinkedHashMap<String, String> params) {
        //look for -id
        if (params.get("id") == null) {
            System.err.println("Missing id");
            return new ExitHandler(ExitHandler.ExitType.FAILED);
        }

        try {
            int id = Integer.parseInt(params.get("id")); //create flight
            Flight flight = new Flight();
            flight.setId(id);
            /*
            -id     value
            -route  route_id
            -airplane   id
            -departure      yyyy-MM-dd HH:mm:ss local timezone
            -seat_price
             */
            //add update parameters
            if(params.get("route") != null) flight.setRoute(new Route().setId(Integer.parseInt(params.get("route"))));
            if(params.get("airplane") != null) flight.setAirplane(new Airplane().setId(Integer.parseInt(params.get("airplane"))));
            if(params.get("departure") != null){
                flight.setDepartureTime(LocalDateTime.parse(
                        params.get("departure"),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                ).atZone(ZoneId.of("UTC")));
            }
            if(params.get("seat_price") != null) flight.setSeatPrice(Float.parseFloat(params.get("seat_price")));
            //update flight
            if(new AdminExecutorUpdates().updateFlight(flight)){
                System.out.println("Update succeeded");
                return new ExitHandler(ExitHandler.ExitType.NOTHING);
            }
        } catch (Exception ignored) {
        }
        System.err.println("Update failed");
        return new ExitHandler(ExitHandler.ExitType.FAILED);
    }
}
