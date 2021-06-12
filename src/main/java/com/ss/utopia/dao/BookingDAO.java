package com.ss.utopia.dao;

import com.ss.utopia.entity.Booking;
import com.ss.utopia.util.SQLUtil;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class BookingDAO extends BaseDAO<Booking> {
    /**
     * Takes sql connection object, pass connection without auto commits for transactions
     *
     * @param conn SQL connection
     */
    public BookingDAO(Connection conn) {
        super(conn);
    }

    /**
     * Add Booking to database, overwrite id in booking
     *
     * @param booking booking data, id overwritten
     * @return booking with new id
     */
    public Booking addBooking(Booking booking) throws SQLException {
        if (!booking.validate()) throw new InvalidParameterException("Missing parameters");
        Integer id = save("INSERT INTO booking (is_active, confirmation_code) VALUES(?, ?)",
                new Object[]{booking.getActiveNum(), booking.getConfirmationCode()});
        return booking.setId(id);
    }

    /**
     * Update Booking with id
     *
     * @param booking fully populated
     * @throws SQLException invalid data or server failure
     */
    public void updateBooking(Booking booking) throws SQLException {
        if (!booking.validate()) throw new InvalidParameterException("Missing parameters");
        save("UPDATE booking SET is_active = ?, confirmation_code = ? WHERE id = ?",
                new Object[]{booking.getActiveNum(), booking.getConfirmationCode(), booking.getId()});
    }

    /**
     * Read booking at id
     *
     * @param id id to read
     * @return Booking
     * @throws SQLException invalid data or server failure
     * @see com.ss.utopia.entity.Booking
     */
    public Booking readBookingById(Integer id) throws SQLException {
        List<Booking> bookings = read("SELECT * FROM booking WHERE id = ?", new Object[]{id});
        if (bookings.size() == 0) return null;
        return bookings.get(0);
    }

    /**
     * Search table with parameters
     *
     * @param search must have at least one pair, services responsibility to have correct column names
     * @return List of entity
     * @throws SQLException invalid data or server failure
     */
    public List<Booking> search(LinkedHashMap<String, String> search) throws SQLException {
        if (search.size() == 0) return new ArrayList<>();
        return read(SQLUtil.constructSqlSearch("booking", search.size()), SQLUtil.collapseMap(search));
    }


    /**
     * Delete Booking with id, cascades to all other titles containing 'booking'
     *
     * @param id id to delete
     * @throws SQLException invalid data or server failure
     */
    public void deleteBookingByID(Integer id) throws SQLException {
        save("DELETE FROM booking WHERE id = ?", new Object[]{id});
    }

    /**
     * Delete Booking with id, cascades to all other titles containing 'booking'
     *
     * @param booking booking to delete
     * @throws SQLException invalid data or server failure
     */
    public void deleteBooking(Booking booking) throws SQLException {
        deleteBookingByID(booking.getId());
    }

    /**
     * Read all Bookings
     *
     * @return List of Booking
     * @throws SQLException invalid data or server failure
     */
    public List<Booking> readAll() throws SQLException {
        return read("SELECT * FROM booking", null);
    }


    @Override
    protected List<Booking> extractData(ResultSet rs) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        while (rs.next()) {
            Booking temp = new Booking();
            temp.setId(rs.getInt("id"));
            temp.setActive(rs.getInt("is_active"));
            temp.setConfirmationCode(rs.getString("confirmation_code"));
            bookings.add(temp);
        }
        return bookings;
    }
}
