package com.ss.service.admin;

import com.ss.utopia.dao.AirportDAO;
import com.ss.utopia.dao.BookingDAO;
import com.ss.utopia.dao.FlightDAO;
import com.ss.utopia.dao.UserDAO;
import com.ss.utopia.db.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class AdminExecutorDeletes {

    /**
     * Deletes user at id, admin method
     *
     * @param userId id to delete
     * @return true if delete succeeded
     */
    public boolean deleteUser(Integer userId) {
        try (Connection c = ConnectionUtil.getConnection()) {
            UserDAO userDAO = new UserDAO(c);
            userDAO.deleteUserByID(userId);

            return true;
        } catch (SQLException ignored) {
        }
        return false;
    }

    /**
     * Delete airport with iata_id
     *
     * @param airportId id to delete
     * @return true if deleted
     */
    public boolean deleteAirport(String airportId) {
        try (Connection c = ConnectionUtil.getConnection()) {
            AirportDAO airportDAO = new AirportDAO(c);
            airportDAO.deleteAirportById(airportId);
            return true;
        } catch (SQLException ignored) {
        }
        return false;
    }

    /**
     * Delete booking at id
     *
     * @param bookingId id to delete
     * @return true if deleted
     */
    public boolean deleteBooking(Integer bookingId) {
        try (Connection c = ConnectionUtil.getConnection()) {
            BookingDAO bookingDAO = new BookingDAO(c);
            bookingDAO.deleteBookingByID(bookingId);
            return true;
        } catch (SQLException ignored) {
        }
        return false;
    }

    /**
     * Delete flight at id
     * @param flightId id to delete
     * @return true if deleted
     */
    public boolean deleteFlight(Integer flightId){
        try (Connection c = ConnectionUtil.getConnection()) {
            FlightDAO flightDAO = new FlightDAO(c);
            flightDAO.deleteFlightById(flightId);
            return true;
        } catch (SQLException ignored) {
        }
        return false;
    }
}
