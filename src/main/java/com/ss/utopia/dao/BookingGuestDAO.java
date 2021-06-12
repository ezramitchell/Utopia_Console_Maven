package com.ss.utopia.dao;

import com.ss.utopia.entity.Airplane;
import com.ss.utopia.entity.Booking;
import com.ss.utopia.entity.BookingGuest;
import com.ss.utopia.util.SQLUtil;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * DAO for BookingGuest table
 *
 * @see BaseDAO
 * @see BookingGuest
 */
public class BookingGuestDAO extends BaseDAO<BookingGuest> {

    /**
     * Takes sql connection object, pass connection without auto commits for transactions
     *
     * @param conn SQL connection
     */
    public BookingGuestDAO(Connection conn) {
        super(conn);
    }

    /**
     * Add bookingGuest to database, BookingGuest should be fully populated
     *
     * @param bookingGuest bookingGuest object all fields necessary
     */
    public void addBookingGuest(BookingGuest bookingGuest) throws SQLException {
        if (!bookingGuest.validate()) throw new InvalidParameterException("Missing parameters");
        save("INSERT INTO booking_guest (booking_id, contact_email, contact_phone) VALUES (?, ?, ?)",
                new Object[]{bookingGuest.getBooking().getId(),
                        bookingGuest.getContactEmail(),
                        bookingGuest.getContactPhone()});
    }

    /**
     * Update BookingGuest with booking_id, bookingAgent should be fully populated
     *
     * @param newBookingGuest new bookingAgent data, id used for update
     * @throws SQLException invalid data or server failure
     */
    public void updateBookingGuest(BookingGuest newBookingGuest) throws SQLException {
        if (!newBookingGuest.validate()) throw new InvalidParameterException("Missing parameters");
        save("UPDATE booking_guest SET contact_email = ?, contact_phone = ? WHERE booking_id = ?",
                new Object[]{newBookingGuest.getContactEmail(),
                        newBookingGuest.getContactPhone(),
                        newBookingGuest.getBooking().getId()});
    }

    /**
     * Search table with parameters
     *
     * @param search must have at least one pair, services responsibility to have correct column names
     * @return List of entity
     * @throws SQLException invalid data or server failure
     */
    public List<BookingGuest> search(LinkedHashMap<String, String> search) throws SQLException {
        if (search.size() == 0) return new ArrayList<>();
        return read(SQLUtil.constructSqlSearch("booking_guest", search.size()), SQLUtil.collapseMap(search));
    }



    /**
     * Find bookingAgent specified by booking_id
     *
     * @param id id to search for
     * @return BookingGuest matching id
     * @throws SQLException invalid data or server failure
     * @see BookingGuest
     */
    public BookingGuest readBookingGuestById(Integer id) throws SQLException {
        List<BookingGuest> bookingAgentList = read("SELECT * FROM booking_guest WHERE booking_id = ?", new Object[]{id});
        if (bookingAgentList.size() == 0) return null;
        return bookingAgentList.get(0);
    }

    /**
     * Read all bookingAgents
     *
     * @return list of BookingGuest Object
     * @throws SQLException invalid data or server failure
     * @see BookingGuest
     */
    public List<BookingGuest> readAll() throws SQLException {
        return read("SELECT * FROM booking_guest", null);
    }

    /**
     * Deletes bookingAgent of booking_id
     *
     * @param id id to delete
     */
    public void deleteBookingGuestById(Integer id) throws SQLException {
        save("DELETE from booking_guest WHERE booking_id = ?", new Object[]{id});
    }

    /**
     * Deletes bookingAgent
     *
     * @param bookingAgent bookingAgent to delete
     */
    public void deleteBookingGuest(BookingGuest bookingAgent) throws SQLException {
        deleteBookingGuestById(bookingAgent.getBooking().getId());
    }


    @Override
    protected List<BookingGuest> extractData(ResultSet rs) throws SQLException {
        List<BookingGuest> bList = new ArrayList<>();
        while (rs.next()) {
            BookingGuest temp = new BookingGuest();
            temp.setBooking(new Booking().setId(rs.getInt("booking_id")));
            temp.setContactEmail(rs.getString("contact_email"));
            temp.setContactPhone(rs.getString("contact_phone"));
            bList.add(temp);
        }
        return bList;
    }
}
