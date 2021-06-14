package com.ss.service.admin;

import com.ss.utopia.dao.AirportDAO;
import com.ss.utopia.dao.BookingDAO;
import com.ss.utopia.dao.FlightDAO;
import com.ss.utopia.dao.UserDAO;
import com.ss.utopia.db.ConnectionUtil;
import com.ss.utopia.entity.Airport;
import com.ss.utopia.entity.Booking;
import com.ss.utopia.entity.Flight;
import com.ss.utopia.entity.User;

import java.sql.Connection;
import java.sql.SQLException;

public class AdminExecutorUpdates {

    /**
     * Sparsely populated user, populated fields will be updated
     *
     * @param user sparsely populated, id necessary
     * @return true if updated
     */
    public boolean updateUser(User user) {
        try (Connection c = ConnectionUtil.getConnection()) {
            UserDAO userDAO = new UserDAO(c);
            //read user from idea
            User oldUser = userDAO.readUserById(user.getId());
            //merge with new user
            if (user.getRole() != null) oldUser.setRole(user.getRole());
            if (user.getGivenName() != null) oldUser.setGivenName(user.getGivenName());
            if (user.getFamilyName() != null) oldUser.setFamilyName(user.getFamilyName());
            if (user.getUsername() != null) oldUser.setUsername(user.getUsername());
            if (user.getEmail() != null) oldUser.setEmail(user.getEmail());
            if (user.getPassword() != null) oldUser.setPassword(user.getPassword());
            if (user.getPhone() != null) oldUser.setPhone(user.getPhone());
            //update user
            userDAO.updateUser(oldUser);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Sparsely populate airport, populated fields will be updated
     *
     * @param airport  sparsely populated
     * @param targetId id to update
     * @return true if updated
     */
    public boolean updateAirport(Airport airport, String targetId) {
        try (Connection c = ConnectionUtil.getConnection()) {
            AirportDAO airportDAO = new AirportDAO(c);
            //read airport
            Airport oldAirport = airportDAO.readAirportById(targetId);
            //merge with new airport
            if (airport.getIataId() != null) oldAirport.setIataId(airport.getIataId());
            if (airport.getCity() != null) oldAirport.setCity(airport.getCity());
            //update airport
            airportDAO.updateAirportById(oldAirport, targetId);
            return true;
        } catch (SQLException ignored) {
        }
        return false;
    }

    /**
     * Sparsely populated Booking, populated fields will be updated
     *
     * @param booking sparsely populated, id necessary
     * @return true if updated
     */
    public boolean updateBooking(Booking booking) {
        try (Connection c = ConnectionUtil.getConnection()) {
            BookingDAO bookingDAO = new BookingDAO(c);
            //read old booking
            Booking oldBooking = bookingDAO.readBookingById(booking.getId());
            //merge with new booking
            if (booking.getActive() != null) oldBooking.setActive(booking.getActive());
            if (booking.getConfirmationCode() != null) oldBooking.setConfirmationCode(booking.getConfirmationCode());
            if (booking.getSeatType() != null) oldBooking.setSeatType(booking.getSeatType());
            //update booking
            bookingDAO.updateBooking(oldBooking);
            return true;
        } catch (SQLException ignored) {
        }
        return false;
    }

    /**
     * Sparsely populated Flight, populated fields will be updated
     * @param flight sparsely populated, id necessary
     * @return true if updated
     */
    public boolean updateFlight(Flight flight) {
        try (Connection c = ConnectionUtil.getConnection()) {
            FlightDAO flightDAO = new FlightDAO(c);
            //read flight
            Flight oldFlight = flightDAO.readFlightById(flight.getId());
            //merge flights
            if(flight.getRoute() != null) oldFlight.setRoute(flight.getRoute());
            if(flight.getAirplane() != null) oldFlight.setAirplane(flight.getAirplane());
            if(flight.getDepartureTime() != null) oldFlight.setDepartureTime(flight.getDepartureTime());
            if(flight.getReservedSeats() != null) oldFlight.setReservedSeats(flight.getReservedSeats());
            if(flight.getSeatPrice() != null) oldFlight.setSeatPrice(flight.getSeatPrice());
            //update flight
            flightDAO.updateFlight(oldFlight);
            return true;
        } catch (SQLException ignored) {
        }
        return false;
    }
}
