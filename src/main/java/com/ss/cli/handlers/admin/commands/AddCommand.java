package com.ss.cli.handlers.admin.commands;

import com.ss.cli.handlers.Command;
import com.ss.cli.handlers.CommandUtil;
import com.ss.cli.handlers.ConsoleHandler;
import com.ss.cli.handlers.ExitHandler;
import com.ss.service.admin.AdminExecutorAdds;
import com.ss.utopia.entity.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class AddCommand {
    Scanner input;

    public AddCommand(Scanner input) {
        this.input = input;
    }
    /*
    Arg list
    -flight
    -booking
    -airport
    -traveler
    -agent
     */

    public Command createAdd() {
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
                case "flight" -> returnVal = addFlight(params);
                case "booking" -> returnVal = addBooking(params);
                case "airport" -> returnVal = addAirport(params);
                case "traveler" -> returnVal = addUser(params, 3);
                case "agent" -> returnVal = addUser(params, 2);
                default -> {
                    System.err.println("No table specified");
                    returnVal = new ExitHandler(ExitHandler.ExitType.FAILED);
                }
            }
            return returnVal;
        };
    }

    private ConsoleHandler addUser(LinkedHashMap<String, String> params, int i) {
        //construct user data
        User user = new User();
        user.setId(-1);
        user.setRole(new UserRole().setId(i));

        System.out.println("Enter user data, confirmation at end");
        System.out.println("Enter given name:");
        user.setGivenName(input.nextLine());

        System.out.println("Enter family name:");
        user.setFamilyName(input.nextLine());

        System.out.println("Enter username:");
        user.setUsername(input.nextLine());

        System.out.println("Enter password:");
        user.setPassword(input.nextLine());

        System.out.println("Enter phone:");
        user.setPhone(input.nextLine());

        System.out.println("Commit user? y/n");
        if (!input.nextLine().equals("y")) return new ExitHandler(ExitHandler.ExitType.NOTHING);

        //add user
        user = new AdminExecutorAdds().addUser(user);
        if (user == null) return new ExitHandler(ExitHandler.ExitType.FAILED);
        System.out.println("Add successful, id is " + user.getId());
        return new ExitHandler(ExitHandler.ExitType.NOTHING);
    }

    private ConsoleHandler addAirport(LinkedHashMap<String, String> params) {
        //construct airport
        Airport airport = new Airport();

        System.out.println("Enter iata_id");
        airport.setIataId(input.nextLine());

        System.out.println("Enter city");
        airport.setCity(input.nextLine());

        System.out.println("Commit airport? y/n");
        if (!input.nextLine().equals("y")) return new ExitHandler(ExitHandler.ExitType.NOTHING);

        //add airport
        airport = new AdminExecutorAdds().addAirport(airport);
        if(airport == null) return new ExitHandler(ExitHandler.ExitType.FAILED);
        System.out.println("Add successful, id is " + airport.getIataId());
        return new ExitHandler(ExitHandler.ExitType.NOTHING);
    }

    private ConsoleHandler addBooking(LinkedHashMap<String, String> params) {
        int flightId;
        String seatType;
        String confirmation;
        int userId;
        int agentId;
        Passenger passenger;

        try{
            System.out.println("Enter flight id");
            flightId = Integer.parseInt(input.nextLine());

            System.out.println("Enter seat type");
            seatType = input.nextLine();

            System.out.println("Enter confirmation code:");
            confirmation = input.nextLine();

            System.out.println("Enter user id");
            userId = Integer.parseInt(input.nextLine());

            System.out.println("Enter agent id, id 4 is unattributed");
            agentId = Integer.parseInt(input.nextLine());

            passenger = new Passenger();
            passenger.setId(-1);
            passenger.setBooking(new Booking());

            System.out.println("Enter passengers given name:");
            passenger.setGivenName(input.nextLine());

            System.out.println("Enter passengers family name:");
            passenger.setFamilyName(input.nextLine());

            System.out.println("Enter passengers gender:");
            passenger.setGender(input.nextLine());

            System.out.println("Enter passengers address");
            passenger.setAddress(input.nextLine());
        } catch (NumberFormatException e) {
            return new ExitHandler(ExitHandler.ExitType.FAILED);
        }

        System.out.println("Commit booking? y/n");
        if(!input.nextLine().equals("y")) return new ExitHandler(ExitHandler.ExitType.NOTHING);

        Booking b = new AdminExecutorAdds().addBooking(flightId, seatType, confirmation, userId, agentId, passenger);
        System.out.println("Add successful, id is " + b.getId());
        if(b == null) return new ExitHandler(ExitHandler.ExitType.FAILED);
        return new ExitHandler(ExitHandler.ExitType.NOTHING);
    }

    private ConsoleHandler addFlight(LinkedHashMap<String, String> params) {
        //construct flight
        Flight flight = new Flight();
        flight.setId(-1);
        flight.setReservedSeats(0);
        try {
            System.out.println("Enter route_id");
            flight.setRoute(new Route().setId(Integer.parseInt(input.nextLine())));

            System.out.println("Enter airplane_id");
            flight.setAirplane(new Airplane().setId(Integer.parseInt(input.nextLine())));

            System.out.println("Enter departure time, yyyy-MM-dd HH:mm:ss, military time");
            flight.setDepartureTime(
                    LocalDateTime.parse(input.nextLine(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).atZone(ZoneId.of("UTC")));

            System.out.println("Enter seat price:");
            flight.setSeatPrice(Float.parseFloat(input.nextLine()));
        } catch (Exception e) {
            return new ExitHandler(ExitHandler.ExitType.FAILED);
        }

        System.out.println("Commit flight? y/n");
        if (!input.nextLine().equals("y")) return new ExitHandler(ExitHandler.ExitType.NOTHING);

        //add flight
        flight = new AdminExecutorAdds().addFlight(flight);
        if(flight == null) return new ExitHandler(ExitHandler.ExitType.FAILED);
        System.out.println("Add successful, id is " + flight.getId());
        return new ExitHandler(ExitHandler.ExitType.NOTHING);
    }
}
