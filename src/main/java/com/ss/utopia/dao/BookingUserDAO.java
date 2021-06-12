package com.ss.utopia.dao;

import com.ss.utopia.entity.Airplane;
import com.ss.utopia.entity.Booking;
import com.ss.utopia.entity.BookingUser;
import com.ss.utopia.entity.User;
import com.ss.utopia.util.SQLUtil;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * DAO for BookingUser table
 *
 * @see BaseDAO
 * @see BookingUser
 */
public class BookingUserDAO extends BaseDAO<BookingUser> {

    /**
     * Takes sql connection object, pass connection without auto commits for transactions
     *
     * @param conn SQL connection
     */
    public BookingUserDAO(Connection conn) {
        super(conn);
    }

    /**
     * Add bookingUser to database, BookingUser should be fully populated
     *
     * @param bookingUser bookingUser object all fields necessary
     */
    public void addBookingUser(BookingUser bookingUser) throws SQLException {
        if (!bookingUser.validate()) throw new InvalidParameterException("Missing parameters");
        save("INSERT INTO booking_user (booking_id, user_id) VALUES (?, ?)",
                new Object[]{bookingUser.getBooking().getId(), bookingUser.getUser().getId()});
    }

    /**
     * Update BookingUser with booking_id, bookingUser should be fully populated
     *
     * @param newBookingUser new bookingUser data, id used for update
     * @throws SQLException invalid data or server failure
     */
    public void updateBookingUser(BookingUser newBookingUser) throws SQLException {
        if (!newBookingUser.validate()) throw new InvalidParameterException("Missing parameters");
        save("UPDATE booking_user SET user_id = ? WHERE booking_id = ?",
                new Object[]{newBookingUser.getUser().getId(), newBookingUser.getBooking().getId()});
    }


    /**
     * Find bookingUser specified by booking_id
     *
     * @param id id to search for
     * @return BookingUser matching id
     * @throws SQLException invalid data or server failure
     * @see BookingUser
     */
    public BookingUser readBookingUserById(Integer id) throws SQLException {
        List<BookingUser> bookingUserList = read("SELECT * FROM booking_user WHERE booking_id = ?", new Object[]{id});
        if (bookingUserList.size() == 0) return null;
        return bookingUserList.get(0);
    }

    /**
     * Read all bookingUsers
     *
     * @return list of BookingUser Object
     * @throws SQLException invalid data or server failure
     * @see BookingUser
     */
    public List<BookingUser> readAll() throws SQLException {
        return read("SELECT * FROM booking_user", null);
    }

    /**
     * Deletes bookingUser of booking_id
     *
     * @param id id to delete
     */
    public void deleteBookingUserById(Integer id) throws SQLException {
        save("DELETE from booking_user WHERE booking_id = ?", new Object[]{id});
    }

    /**
     * Search table with parameters
     *
     * @param search must have at least one pair, services responsibility to have correct column names
     * @return List of entity
     * @throws SQLException invalid data or server failure
     */
    public List<BookingUser> search(LinkedHashMap<String, String> search) throws SQLException {
        if (search.size() == 0) return new ArrayList<>();
        return read(SQLUtil.constructSqlSearch("booking_user", search.size()), SQLUtil.collapseMap(search));
    }


    /**
     * Deletes bookingUser
     *
     * @param bookingUser bookingUser to delete
     */
    public void deleteBookingUser(BookingUser bookingUser) throws SQLException {
        deleteBookingUserById(bookingUser.getBooking().getId());
    }


    @Override
    protected List<BookingUser> extractData(ResultSet rs) throws SQLException {
        List<BookingUser> bList = new ArrayList<>();
        while (rs.next()) {
            BookingUser temp = new BookingUser();
            temp.setBooking(new Booking().setId(rs.getInt("booking_id")));
            temp.setUser(new User().setId(rs.getInt("user_id")));
            bList.add(temp);
        }
        return bList;
    }
}
