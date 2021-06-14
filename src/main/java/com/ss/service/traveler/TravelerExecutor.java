package com.ss.service.traveler;

import com.ss.utopia.dao.*;
import com.ss.utopia.db.ConnectionUtil;
import com.ss.utopia.entity.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TravelerExecutor {


    /**
     * Retrieves all bookings made by user, populates all the way to Airport
     *
     * @param user bookings to get
     * @return List of FlightBooking almost fully populated
     */
    public List<FlightBooking> readBookingsToCity(User user) {
        try (Connection c = ConnectionUtil.getConnection()) {
            //get booking_users
            BookingUserDAO bookingUserDAO = new BookingUserDAO(c);
            List<BookingUser> bookingUsers = bookingUserDAO.readBookingByUser(user);
            if (bookingUsers.size() == 0) return new ArrayList<>();

            //match to bookings
            BookingDAO bookingDAO = new BookingDAO(c);
            List<FlightBooking> bookings = new ArrayList<>();

            //populate to route
            FlightBookingDAO flightBookingDAO = new FlightBookingDAO(c);
            FlightDAO flightDAO = new FlightDAO(c);
            RouteDAO routeDAO = new RouteDAO(c);
            AirportDAO airportDAO = new AirportDAO(c);

            for (BookingUser bookingUser : bookingUsers) {
                //get booking
                Booking b = bookingDAO.readBookingById(bookingUser.getBooking().getId());
                //populate Flight Booking
                FlightBooking fb = flightBookingDAO.readFBId(b.getId());
                //populate booking
                fb.setBooking(b);
                //populate Flight
                fb.setFlight(flightDAO.readFlightById(fb.getFlight().getId()));
                //populate Route
                fb.getFlight().setRoute(routeDAO.readRouteById(fb.getFlight().getRoute().getId()));
                //populate citys
                fb.getFlight().getRoute().setOriginAirport(
                        airportDAO.readAirportById(fb.getFlight().getRoute().getOriginAirport().getIataId()));
                fb.getFlight().getRoute().setDestinationAirport(
                        airportDAO.readAirportById(fb.getFlight().getRoute().getDestinationAirport().getIataId()));

                bookings.add(fb);
            }

            return bookings;

        } catch (SQLException ignored) {
        }
        return new ArrayList<>();
    }

    /**
     * Update booking, uses booking_id
     *
     * @param booking booking to update
     * @return true if successful
     */
    public boolean updateBookings(Booking... booking) {
        try (Connection c = ConnectionUtil.getTransaction()) {
            try {
                BookingDAO bookingDAO = new BookingDAO(c);
                for (Booking b : booking) {
                    bookingDAO.updateBooking(b);
                }
                c.commit();
            } catch (SQLException ignored) {
                c.rollback();
                return false;
            }
            return true;
        } catch (SQLException throwables) {
            return false;
        }
    }

    public List<Flight> readAllFlightsToCity() {
        try(Connection c = ConnectionUtil.getConnection()){
            List<Flight> flights = new FlightDAO(c).readAll();
            List<Route> routes = new RouteDAO(c).readAll();
            List<Airport> airports = new AirportDAO(c).readAll();

            //match airports to routes
            for (Airport airport : airports) {
                String id = airport.getIataId();
                routes = routes.stream().peek(r -> {
                    if(r.getOriginAirport().getIataId().equals(id))
                        r.setOriginAirport(airport);
                    if(r.getDestinationAirport().getIataId().equals(id))
                        r.setDestinationAirport(airport);
                }).toList();
            }

            //match routes to flights
            for (Route route : routes) {
                int id = route.getId();
                flights = flights.stream().peek(f -> {
                    if(f.getRoute().getId() == id)
                        f.setRoute(route);
                }).toList();
            }

            //return flights
            return flights;


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new ArrayList<>();
    }

    public boolean bookFlight(Flight flight, User user, Passenger p, String seatType) {
        try(Connection c = ConnectionUtil.getTransaction()){
            try{
                FlightBookingDAO flightBookingDAO = new FlightBookingDAO(c);
                BookingDAO bookingDAO = new BookingDAO(c);
                BookingUserDAO bookingUserDAO = new BookingUserDAO(c);
                BookingPaymentDAO bookingPaymentDAO = new BookingPaymentDAO(c);
                PassengerDAO passengerDAO = new PassengerDAO(c);

                Booking b = bookingDAO.addBooking(new Booking(-1, true, "confirmed", seatType));
                flightBookingDAO.addFlightBooking(new FlightBooking(flight, b));
                bookingUserDAO.addBookingUser(new BookingUser(b, user));
                bookingPaymentDAO.addBookingPayment(new BookingPayment(b, "2340", false));
                passengerDAO.addPassenger(p.setBooking(b));

                c.commit();
                return true;
            }catch(SQLException ignored){
                c.rollback();
                return false;
            }
        } catch (SQLException ignored) {
            return false;
        }
    }
}
