package com.ss.utopia.dao;

import com.ss.utopia.entity.Airplane;
import com.ss.utopia.entity.Airport;
import com.ss.utopia.entity.Booking;
import com.ss.utopia.entity.BookingPayment;
import com.ss.utopia.util.SQLUtil;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * DAO for BookingPayment table
 *
 * @see BaseDAO
 * @see BookingPayment
 */
public class BookingPaymentDAO extends BaseDAO<BookingPayment> {

    /**
     * Takes sql connection object, pass connection without auto commits for transactions
     *
     * @param conn SQL connection
     */
    public BookingPaymentDAO(Connection conn) {
        super(conn);
    }

    /**
     * Add bookingPayment to database, BookingPayment should be fully populated
     *
     * @param bookingPayment bookingPayment object all fields necessary
     */
    public void addBookingPayment(BookingPayment bookingPayment) throws SQLException {
        if (!bookingPayment.validate()) throw new InvalidParameterException("Missing parameters");
        save("INSERT INTO booking_payment (booking_id, stripe_id, refunded) VALUES (?, ?, ?)",
                new Object[]{bookingPayment.getBooking().getId(),
                        bookingPayment.getStripeId(),
                        bookingPayment.getRefundedInt()});
    }

    /**
     * Update BookingPayment with id, bookingPayment should be fully populated
     *
     * @param newBookingPayment new bookingPayment data, id used for update
     * @throws SQLException invalid data or server failure
     */
    public void updateBookingPayment(BookingPayment newBookingPayment) throws SQLException {
        if (!newBookingPayment.validate()) throw new InvalidParameterException("Missing parameters");
        save("UPDATE booking_payment SET stripe_id = ?, refunded = ? WHERE booking_id = ?",
                new Object[]{newBookingPayment.getStripeId(),
                        newBookingPayment.getRefundedInt(),
                        newBookingPayment.getBooking().getId()});
    }


    /**
     * Find bookingPayment specified by booking_id
     *
     * @param id id to search for
     * @return BookingPayment matching id
     * @throws SQLException invalid data or server failure
     * @see BookingPayment
     */
    public BookingPayment readBookingPaymentById(Integer id) throws SQLException {
        List<BookingPayment> bookingPaymentList = read("SELECT * FROM booking_payment WHERE booking_id = ?", new Object[]{id});
        if (bookingPaymentList.size() == 0) return null;
        return bookingPaymentList.get(0);
    }

    /**
     * Read all bookingPayments
     *
     * @return list of BookingPayment Object
     * @throws SQLException invalid data or server failure
     * @see BookingPayment
     */
    public List<BookingPayment> readAll() throws SQLException {
        return read("SELECT * FROM booking_payment", null);
    }

    /**
     * Search table with parameters
     *
     * @param search must have at least one pair, services responsibility to have correct column names
     * @return List of entity
     * @throws SQLException invalid data or server failure
     */
    public List<BookingPayment> search(LinkedHashMap<String, String> search) throws SQLException {
        if (search.size() == 0) return new ArrayList<>();
        return read(SQLUtil.constructSqlSearch("booking_payment", search.size()), SQLUtil.collapseMap(search));
    }


    /**
     * Deletes bookingPayment of booking_id
     *
     * @param id id to delete
     */
    public void deleteBookingPaymentById(Integer id) throws SQLException {
        save("DELETE from booking_payment WHERE booking_id = ?", new Object[]{id});
    }

    /**
     * Deletes bookingPayment
     *
     * @param bookingPayment bookingPayment to delete
     */
    public void deleteBookingPayment(BookingPayment bookingPayment) throws SQLException {
        deleteBookingPaymentById(bookingPayment.getBooking().getId());
    }


    @Override
    protected List<BookingPayment> extractData(ResultSet rs) throws SQLException {
        List<BookingPayment> bList = new ArrayList<>();
        while (rs.next()) {
            BookingPayment temp = new BookingPayment();
            temp.setBooking(new Booking().setId(rs.getInt("booking_id")));
            temp.setStripeId(rs.getString("stripe_id"));
            temp.setRefunded(rs.getInt("refunded"));
            bList.add(temp);
        }
        return bList;
    }
}
