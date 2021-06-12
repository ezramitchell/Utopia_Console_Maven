package com.ss.utopia.dao;

import com.ss.utopia.entity.Airplane;
import com.ss.utopia.entity.Booking;
import com.ss.utopia.entity.Flight;
import com.ss.utopia.entity.FlightBooking;
import com.ss.utopia.util.SQLUtil;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class FlightBookingDAO extends BaseDAO<FlightBooking> {

    /**
     * Takes sql connection object, pass connection without auto commits for transactions
     * Deletions handled by BookingDAO through cascading
     *
     * @param conn SQL connection
     * @see com.ss.utopia.dao.BookingDAO
     */
    public FlightBookingDAO(Connection conn) {
        super(conn);
    }

    /**
     * Add new Flight Booking
     *
     * @param booking booking to add
     * @throws SQLException invalid data or server failure
     */
    public void addFlightBooking(FlightBooking booking) throws SQLException {
        if (!booking.validate()) throw new InvalidParameterException("Missing parameters");
        save("INSERT INTO flight_booking (flight_id, booking_id) VALUES (?, ?)",
                new Object[]{booking.getFlight().getId(), booking.getBooking().getId()});
    }

    /**
     * Search table with parameters
     *
     * @param search must have at least one pair, services responsibility to have correct column names
     * @return List of entity
     * @throws SQLException invalid data or server failure
     */
    public List<FlightBooking> search(LinkedHashMap<String, String> search) throws SQLException {
        if (search.size() == 0) return new ArrayList<>();
        return read(SQLUtil.constructSqlSearch("flight_booking", search.size()), SQLUtil.collapseMap(search));
    }


    /**
     * Updates booking, changes flight only
     *
     * @param booking flightBooking to update, Booking is the addressed key
     * @throws SQLException invalid data or server failure
     */
    public void updateBooking(FlightBooking booking) throws SQLException {
        if (!booking.validate()) throw new InvalidParameterException("Missing parameters");
        save("UPDATE flight_booking SET flight_id = ? WHERE booking_id = ?",
                new Object[]{booking.getFlight().getId(), booking.getBooking().getId()});
    }

    /**
     * Read FlightBooking from flightId
     *
     * @param flightId id to read
     * @return list of Bookings on flight
     * @throws SQLException invalid data or server failure
     */
    public List<FlightBooking> readBookingsByFlightId(Integer flightId) throws SQLException {
        return read("SELECT * FROM flight_booking WHERE flight_id = ?", new Object[]{flightId});
    }

    /**
     * Read FlightBooking from Booking ID
     *
     * @param bookingId id to read
     * @return corresponding flight
     * @throws SQLException invalid data or server failure
     */
    public FlightBooking readFlightBookingByBooking(Integer bookingId) throws SQLException {
        List<FlightBooking> bookings = read("SELECT * FROM flight_booking WHERE booking_id = ?", new Object[]{bookingId});
        if (bookings.size() == 0) return null;
        return bookings.get(0);
    }

    /**
     * Read all Flight Bookings
     *
     * @return List of FLightBookings
     * @throws SQLException invalid data or server failure
     * @see com.ss.utopia.entity.FlightBooking
     */
    public List<FlightBooking> readAll() throws SQLException {
        return read("SELECT * FROM flight_booking", null);
    }


    @Override
    protected List<FlightBooking> extractData(ResultSet rs) throws SQLException {
        List<FlightBooking> flightBookings = new ArrayList<>();
        while (rs.next()) {
            FlightBooking temp = new FlightBooking();
            temp.setFlight(new Flight().setId(rs.getInt("flight_id")));
            temp.setBooking(new Booking().setId(rs.getInt("booking_id")));
            flightBookings.add(temp);
        }
        return flightBookings;
    }
}
