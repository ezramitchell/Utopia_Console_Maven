package com.ss.service.admin;

import com.ss.utopia.dao.*;
import com.ss.utopia.db.ConnectionUtil;
import com.ss.utopia.entity.*;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class AdminExecutorSearch {

    public List<User> searchUsers(LinkedHashMap<String, String> params){
        try (Connection c = ConnectionUtil.getConnection()) {

            UserDAO userDAO = new UserDAO(c);
            List<User> users = new ArrayList<>();
            if (params == null || params.size() == 0) users = userDAO.readAll();
            else {
                //test if column names are valid
                String[] columnNames = userDAO.getColumnNames();
                //run query
                if (Arrays.stream(columnNames).toList().containsAll(params.keySet()))
                    users = userDAO.search(params);
            }
            return users;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * Search flights with parameters, null params for read all
     *
     * @param params column_name and value pairs
     * @return List of flights or empty list
     */
    public List<Flight> searchFlights(LinkedHashMap<String, String> params) {
        try (Connection c = ConnectionUtil.getConnection()) {

            FlightDAO flightDAO = new FlightDAO(c);
            List<Flight> flights = new ArrayList<>();
            if (params == null || params.size() == 0) flights = flightDAO.readAll();
            else {
                //test if column names are valid
                String[] columnNames = flightDAO.getColumnNames();
                //run query
                if (Arrays.stream(columnNames).toList().containsAll(params.keySet()))
                    flights = flightDAO.search(params);
            }

            if (flights.size() > 0) { //construct airports
                RouteDAO routeDAO = new RouteDAO(c);
                for (Flight flight : flights) {
                    //populate route
                    flight.setRoute(routeDAO.readRouteById(flight.getRoute().getId()));
                }
            }
            return flights;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * Return flight bookings from user id, populates flight and booking
     *
     * @param id user id to search for
     * @return list of flight booking
     */
    public List<FlightBooking> getUserBookings(Integer id) {
        try (Connection c = ConnectionUtil.getConnection()) {
            //get booking_users
            BookingUserDAO bookingUserDAO = new BookingUserDAO(c);
            List<BookingUser> bookingUsers = bookingUserDAO.readBookingByUser(new User().setId(id));
            if (bookingUsers.size() == 0) return new ArrayList<>();

            //match to bookings
            BookingDAO bookingDAO = new BookingDAO(c);
            List<Booking> bookings = new ArrayList<>();

            for (BookingUser bookingUser : bookingUsers) {
                //get booking
                Booking b = bookingDAO.readBookingById(bookingUser.getBooking().getId());
                bookings.add(b);
            }

            //construct flight bookings from booking list
            return getFlightBookings(c, bookings);

        } catch (SQLException ignored) {
        }
        return new ArrayList<>();
    }

    /**
     * Populates to flight from list of booking objects
     *
     * @param c connection, close after method
     * @param bookings bookings to populate
     * @return list of FLightBooking
     * @see FlightBooking
     * @throws SQLException you done messed up
     */
    @NotNull
    private List<FlightBooking> getFlightBookings(Connection c, List<Booking> bookings) throws SQLException {
        List<FlightBooking> flightBookings = new ArrayList<>();

        FlightBookingDAO flightBookingDAO = new FlightBookingDAO(c);
        FlightDAO flightDAO = new FlightDAO(c);

        for (Booking booking : bookings) {
            FlightBooking fb = flightBookingDAO.readFBId(booking.getId());
            fb.setBooking(booking);
            fb.setFlight(flightDAO.readFlightById(fb.getFlight().getId()));
            flightBookings.add(fb);
        }

        return flightBookings;
    }

    /**
     * get flight booking with parameters
     * @param params column_names, returns nothing if name is invalid or null
     * @return list of FlightBooking
     */
    public List<FlightBooking> searchBookings(LinkedHashMap<String, String> params) {
        try (Connection c = ConnectionUtil.getConnection()) {

            BookingDAO bookingDAO = new BookingDAO(c);
            List<Booking> bookings = new ArrayList<>();

            if (params == null || params.size() == 0) bookings = bookingDAO.readAll();
            else {
                //test if column names are valid
                String[] columnNames = bookingDAO.getColumnNames();
                //run query
                if (Arrays.stream(columnNames).toList().containsAll(params.keySet()))
                    bookings = bookingDAO.search(params);
            }

            //construct flight bookings from booking list
            return getFlightBookings(c, bookings);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new ArrayList<>();
    }


    public List<Airport> searchAirports(LinkedHashMap<String, String> params) {
        try (Connection c = ConnectionUtil.getConnection()) {

            AirportDAO airportDAO = new AirportDAO(c);
            List<Airport> airports = new ArrayList<>();
            if (params == null || params.size() == 0) airports = airportDAO.readAll();
            else {
                //test if column names are valid
                String[] columnNames = airportDAO.getColumnNames();
                //run query
                if (Arrays.stream(columnNames).toList().containsAll(params.keySet()))
                    airports = airportDAO.search(params);
            }
            return airports;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new ArrayList<>();
    }

}
