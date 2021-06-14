package com.ss.cli.handlers.traveler;

import com.ss.cli.handlers.ConsoleHandler;
import com.ss.cli.handlers.ExitHandler;
import com.ss.service.traveler.TravelerExecutor;
import com.ss.utopia.entity.*;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TravelerHandler extends ConsoleHandler {

    private final TravelerExecutor te;
    private final User user;
    private List<FlightBooking> currentBookings;

    public TravelerHandler(Scanner input, User user) {
        super(input);
        te = new TravelerExecutor();
        this.user = user;
        addCommands();
    }

    @Override
    public void init() {
        System.out.println("Intermediate menu, cancel and book from here");
        viewTickets(new String[]{});
    }

    private void addCommands(){
        registerCommand("book/book_ticket", this::bookFlight, """
                book/book_ticket\tBook new ticket
                \tOptions: none""");

        registerCommand("cancel/cancel_ticket", this::cancelBooking, """
                cancel/cancel_ticket\tCancel existing ticket
                \tOptions: -id value:id""");

        registerCommand("view/view_tickets", this::viewTickets, """
                view/view_tickets\tView current bookings
                \tOptions: -all\tsee past bookings""");

        registerCommand("flights/view_flights", this::viewFlights, """
                flights/view_flights\tView all flights
                \tOptions: none""");
    }

    private ConsoleHandler viewTickets(String[] args){
        //get all bookings with user id
        currentBookings = te.readBookingsToCity(user);
        if(!Arrays.stream(args).toList().contains("-all")){
            currentBookings = currentBookings.stream().filter(fb -> fb.getBooking().isActive()).toList();
        }

        //display bookings
        for (FlightBooking currentBooking : currentBookings) {
            System.out.printf("%s) %s, %s -> %s, %s%n",
                    currentBooking.getBooking().getId(),
                    currentBooking.getFlight().getRoute().getOriginAirport().getIataId(),
                    currentBooking.getFlight().getRoute().getOriginAirport().getCity(),
                    currentBooking.getFlight().getRoute().getDestinationAirport().getIataId(),
                    currentBooking.getFlight().getRoute().getDestinationAirport().getCity());
        }

        return new ExitHandler(ExitHandler.ExitType.NOTHING);
    }

    private ConsoleHandler cancelBooking(String[] args){
        currentBookings = te.readBookingsToCity(user);
        int index;
        //optional id parameter for one line command
        if((index = Arrays.stream(args).toList().indexOf("-id")) != -1){
             if(index + 1 < args.length){
                 try{
                     int id = Integer.parseInt(args[index + 1]); //get id
                     Booking b = currentBookings.stream().filter(
                             booking -> booking.getBooking().getId() == id).findFirst().get().getBooking(); //find current booking of id
                     if(te.updateBookings(b.setActive(false))) //update booking
                        return new ExitHandler(ExitHandler.ExitType.NOTHING); //success
                     else return new ExitHandler(ExitHandler.ExitType.FAILED); //fail
                 } catch (Exception e) {
                     return new ExitHandler(ExitHandler.ExitType.FAILED);
                 }
             }
        } else{
            System.out.println("Enter id of booking to cancel:");
            try{
                int id = Integer.parseInt(input.nextLine());
                Booking b = currentBookings.stream().filter(
                        booking -> booking.getBooking().getId() == id).findFirst().get().getBooking(); //get current booking at id
                if(te.updateBookings(b.setActive(false)))
                    return new ExitHandler(ExitHandler.ExitType.NOTHING); //success
            } catch (Exception ignored) { }
        }
        return new ExitHandler(ExitHandler.ExitType.FAILED); //fail
    }

    private ConsoleHandler viewFlights(String[] args){
        //get all flights
        List<Flight> flights = te.readAllFlightsToCity();


        System.out.println("Your bookings");
        //print all flights
        for (Flight flight : flights) {
            //TODO add check on departure time so old flights don't appear
            System.out.printf(" %s, %s -> %s, %s $%.2f%n",
                    flight.getRoute().getOriginAirport().getIataId(),
                    flight.getRoute().getOriginAirport().getCity(),
                    flight.getRoute().getDestinationAirport().getIataId(),
                    flight.getRoute().getDestinationAirport().getCity(),
                    flight.getSeatPrice());
        }

        return new ExitHandler(ExitHandler.ExitType.NOTHING);
    }

    private ConsoleHandler bookFlight(String[] args){
        //get all flights
        List<Flight> flights = te.readAllFlightsToCity();

        //print all flights
        int index = 1;
        for (Flight flight : flights) {
            //TODO add check on departure time so old flights don't appear
            System.out.printf("%s) %s, %s -> %s, %s $%.2f%n",
                    index,
                    flight.getRoute().getOriginAirport().getIataId(),
                    flight.getRoute().getOriginAirport().getCity(),
                    flight.getRoute().getDestinationAirport().getIataId(),
                    flight.getRoute().getDestinationAirport().getCity(),
                    flight.getSeatPrice());
            index++;
        }

        //get chosen flight
        System.out.println("Enter id of flight");
        try{
            int id = Integer.parseInt(input.nextLine());
            if(id > index) return new ExitHandler(ExitHandler.ExitType.FAILED);

            //get seat type
            System.out.println("Enter seat type, pricing displayed is economy:");
            String seatType = input.nextLine();

            //get passenger information
            System.out.println("Passenger information:");
            Passenger passenger = new Passenger();
            System.out.println("Enter passengers given name:");
            passenger.setGivenName(input.nextLine());
            System.out.println("Enter passengers family name:");
            passenger.setFamilyName(input.nextLine());
            System.out.println("Enter passengers gender, N/A alright:");
            passenger.setGender(input.nextLine());
            System.out.println("Enter payment address:");
            passenger.setAddress(input.nextLine());

            passenger.setId(-1);

            if(te.bookFlight(flights.get(id - 1), user, passenger, seatType))
                return new ExitHandler(ExitHandler.ExitType.NOTHING);
        } catch (Exception ignored) {}
        return new ExitHandler(ExitHandler.ExitType.FAILED);
    }
}
