package com.ss.utopia.dao;

import com.ss.utopia.entity.Booking;
import com.ss.utopia.entity.BookingAgent;
import com.ss.utopia.entity.User;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for BookingAgent table
 *
 * @see BaseDAO
 * @see BookingAgent
 */
public class BookingAgentDAO extends BaseDAO<BookingAgent> {

    /**
     * Takes sql connection object, pass connection without auto commits for transactions
     *
     * @param conn SQL connection
     */
    public BookingAgentDAO(Connection conn) {
        super(conn);
    }

    /**
     * Add bookingAgent to database, BookingAgent should be fully populated
     *
     * @param bookingAgent bookingAgent object all fields necessary
     */
    public void addBookingAgent(BookingAgent bookingAgent) throws SQLException {
        if (!bookingAgent.validate()) throw new InvalidParameterException("Missing parameters");
        save("INSERT INTO booking_agent (booking_id, agent_id) VALUES (?, ?)",
                new Object[]{bookingAgent.getBooking().getId(), bookingAgent.getAgent().getId()});
    }

    /**
     * Update BookingAgent with booking_id, bookingAgent should be fully populated
     *
     * @param newBookingAgent new bookingAgent data, id used for update
     * @throws SQLException invalid data or server failure
     */
    public void updateBookingAgent(BookingAgent newBookingAgent) throws SQLException {
        if (!newBookingAgent.validate()) throw new InvalidParameterException("Missing parameters");
        save("UPDATE booking_agent SET agent_id = ? WHERE booking_id = ?",
                new Object[]{newBookingAgent.getAgent().getId(), newBookingAgent.getBooking().getId()});
    }


    /**
     * Find bookingAgent specified by booking_id
     *
     * @param id id to search for
     * @return BookingAgent matching id
     * @throws SQLException invalid data or server failure
     * @see BookingAgent
     */
    public BookingAgent readBookingAgentById(Integer id) throws SQLException {
        List<BookingAgent> bookingAgentList = read("SELECT * FROM booking_agent WHERE booking_id = ?", new Object[]{id});
        if (bookingAgentList.size() == 0) return null;
        return bookingAgentList.get(0);
    }

    /**
     * Read all bookingAgents
     *
     * @return list of BookingAgent Object
     * @throws SQLException invalid data or server failure
     * @see BookingAgent
     */
    public List<BookingAgent> readAll() throws SQLException {
        return read("SELECT * FROM booking_agent", null);
    }

    /**
     * Deletes bookingAgent of booking_id
     *
     * @param id id to delete
     */
    public void deleteBookingAgentById(Integer id) throws SQLException {
        save("DELETE from booking_agent WHERE booking_id = ?", new Object[]{id});
    }

    /**
     * Deletes bookingAgent
     *
     * @param bookingAgent bookingAgent to delete
     */
    public void deleteBookingAgent(BookingAgent bookingAgent) throws SQLException {
        deleteBookingAgentById(bookingAgent.getBooking().getId());
    }


    @Override
    protected List<BookingAgent> extractData(ResultSet rs) throws SQLException {
        List<BookingAgent> bList = new ArrayList<>();
        while (rs.next()) {
            BookingAgent temp = new BookingAgent();
            temp.setBooking(new Booking().setId(rs.getInt("booking_id")));
            temp.setAgent(new User().setId(rs.getInt("agent_id")));
            bList.add(temp);
        }
        return bList;
    }
}
