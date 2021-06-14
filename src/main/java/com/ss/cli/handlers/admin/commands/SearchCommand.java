package com.ss.cli.handlers.admin.commands;

import com.ss.cli.handlers.Command;
import com.ss.cli.handlers.CommandUtil;
import com.ss.cli.handlers.ConsoleHandler;
import com.ss.cli.handlers.ExitHandler;
import com.ss.service.admin.AdminExecutorSearch;
import com.ss.utopia.entity.Airport;
import com.ss.utopia.entity.Flight;
import com.ss.utopia.entity.FlightBooking;
import com.ss.utopia.entity.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class SearchCommand {

    /*
            Arguments list
            -flight     Specifies table to search
                -id value
                -route_id value
                -airplane_id value
                -seat_price value
            -booking
                -user user_id
                -id value
                -is_active
                -seat_type value
            -airport
                -iata_id value
                -city value
            -traveler
                -id value
                -username value
                -phone value
                -email value
            -agent
                -id value
                -email value
                -phone value
                -email value
             */

    /**
     * Constructs search command
     *
     * @return new search command
     */
    public static Command createSearch() {
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
                case "flight" -> returnVal = searchFlights(params);
                case "booking" -> returnVal = searchBooking(params);
                case "airport" -> returnVal = searchAirport(params);
                case "traveler" -> returnVal = searchUser(params, 3);
                case "agent" -> returnVal = searchUser(params, 2);
                default -> {
                    System.err.println("No table specified");
                    returnVal = new ExitHandler(ExitHandler.ExitType.FAILED);
                }
            }
            return returnVal;
        };
    }

    private static ConsoleHandler searchUser(LinkedHashMap<String, String> params, int roleID) {
        List<String> validKeys = Arrays.stream(new String[]{"id", "username", "email", "phone"}).toList();
        //remove any invalid parameters
        String[] invalidKeys = params.keySet().stream().filter(s -> !validKeys.contains(s)).toArray(String[]::new);
        for (String invalidKey : invalidKeys) {
            params.remove(invalidKey);
        }
        //remove any nulls
        params.values().removeAll(Collections.singleton(null));

        params.put("role_id", String.valueOf(roleID));

        List<User> travelers = new AdminExecutorSearch().searchUsers(params);

        //print search
        for (User user : travelers) {
            System.out.printf("Id: %s, Name: %s %s, Username: %s, Email %s, Phone %s%n",
                    user.getId(),
                    user.getGivenName(), user.getFamilyName(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPhone());
        }

        return new ExitHandler(ExitHandler.ExitType.NOTHING);
    }

    /**
     * Function to search airports
     *
     * @param params params passed by user
     * @return ConsoleHandler with exit code
     */
    private static ConsoleHandler searchAirport(LinkedHashMap<String, String> params) {
        List<String> validKeys = Arrays.stream(new String[]{"iata_id", "city"}).toList();
        //remove any invalid parameters
        String[] invalidKeys = params.keySet().stream().filter(s -> !validKeys.contains(s)).toArray(String[]::new);
        for (String invalidKey : invalidKeys) {
            params.remove(invalidKey);
        }
        //remove any nulls
        params.values().removeAll(Collections.singleton(null));

        List<Airport> airports = new AdminExecutorSearch().searchAirports(params);

        //print airports
        for (Airport airport : airports) {
            System.out.printf("Iata Id: %s, City: %s%n",
                    airport.getIataId(),
                    airport.getCity());
        }

        return new ExitHandler(ExitHandler.ExitType.NOTHING);
    }

    /**
     * Function to search bookings, validates parameters and passes to correct admin service
     *
     * @param params parameters passed by user
     * @return ExitHandler
     */
    private static ConsoleHandler searchBooking(LinkedHashMap<String, String> params) {
        List<String> validKeys = Arrays.stream(new String[]{"id", "is_active", "seat_type"}).toList();
        //remove any invalid parameters
        String[] invalidKeys = params.keySet().stream().filter(s -> !validKeys.contains(s)).toArray(String[]::new);
        for (String invalidKey : invalidKeys) {
            params.remove(invalidKey);
        }
        //remove any null keys
        if (params.containsKey("is_active") && params.get("is_active") == null)
            params.replace("is_active", "1"); //add value to active flag

        params.values().removeAll(Collections.singleton(null));

        List<FlightBooking> bookings;
        //search by user id, replace user with booking_id

        if (params.get("user") != null && params.get("id") == null) {
            try {
                //get bookings by user id
                bookings = new AdminExecutorSearch().getUserBookings(Integer.parseInt(params.get("user")));
                //filter by other params
                if (params.get("is_active") != null)
                    bookings = bookings.stream().filter(b -> b.getBooking().isActive()).toList();
                if (params.get("seat_type") != null)
                    bookings = bookings.stream().filter(b -> b.getBooking().getSeatType().equals(params.get("type"))).toList();
            } catch (NumberFormatException ignored) {
                return new ExitHandler(ExitHandler.ExitType.FAILED);
            }
        } else {//sql search with parameters
            params.remove("user");
            bookings = new AdminExecutorSearch().searchBookings(params);
        }

        //print bookings
        for (FlightBooking booking : bookings) {
            System.out.printf("Flight: %s, Route: %s, Departure date: %s, Booking id: %s, Booking active: %s%n",
                    booking.getFlight().getId(),
                    booking.getFlight().getRoute().getId(),
                    booking.getFlight().getDepartureTime().toString(),
                    booking.getBooking().getId(),
                    booking.getBooking().getActive().toString());
        }

        return new ExitHandler(ExitHandler.ExitType.NOTHING);
    }

    private static ConsoleHandler searchFlights(LinkedHashMap<String, String> params) {
        List<String> validKeys = Arrays.stream(new String[]{"id", "route_id", "airplane_id", "seat_price"}).toList();
        //remove any invalid parameters
        String[] invalidKeys = params.keySet().stream().filter(s -> !validKeys.contains(s)).toArray(String[]::new);
        for (String invalidKey : invalidKeys) {
            params.remove(invalidKey);
        }
        //remove any nulls
        params.values().removeAll(Collections.singleton(null));

        List<Flight> flights = new AdminExecutorSearch().searchFlights(params.size() == 0 ? null : params);


        if (flights.size() == 0) System.out.println("No search results");

        //print flights
        for (Flight flight : flights) {
            System.out.printf("%s -> %s Id: %s, Route id: %s, Seat price: %.2f%n",
                    flight.getRoute().getOriginAirport().getIataId(),
                    flight.getRoute().getDestinationAirport().getIataId(),
                    flight.getId(), flight.getRoute().getId(), flight.getSeatPrice());
        }

        return new ExitHandler(ExitHandler.ExitType.NOTHING);
    }
}
