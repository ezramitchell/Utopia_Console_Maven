package com.ss.utopia.dao;

import com.ss.utopia.db.ConnectionUtil;
import com.ss.utopia.entity.Booking;
import com.ss.utopia.entity.BookingGuest;
import com.ss.utopia.entity.User;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookingGuestDAOTest {

    @Test
    public void testAddDelete() {
        try (Connection c = ConnectionUtil.getTransaction()) {
            try {
                BookingGuestDAO userDao = new BookingGuestDAO(c);
                BookingDAO bookDAO = new BookingDAO(c);
                //add airplane

                Booking b = new Booking(-1, true, "confirmed");

                bookDAO.addBooking(b); // add booking that guest references
                // necessary so data doesn't influence testing

                BookingGuest bookingUser = new BookingGuest(
                        new Booking().setId(b.getId()), "email22", "pohen");

                //add bookingUser
                userDao.addBookingGuest(bookingUser);
                //check object exists
                assertNotNull(userDao.readBookingGuestById(bookingUser.getBooking().getId()));


                //update object
                bookingUser.setContactPhone("phone13");
                BookingGuest copy = new BookingGuest(bookingUser); //copy for comparison

                userDao.updateBookingGuest(bookingUser);

                //read object, check value
                bookingUser = userDao.readBookingGuestById(bookingUser.getBooking().getId());
                assertNotNull(bookingUser);
                assertEquals(bookingUser, copy);
                
                //delete object
                userDao.deleteBookingGuest(bookingUser);

                assertNull(userDao.readBookingGuestById(bookingUser.getBooking().getId())); //object should be deleted

            } catch (SQLException throwable) {
                throwable.printStackTrace();
                fail();
            }

            c.rollback();
        } catch (SQLException throwable) {
            System.out.println("Connection failed");
            fail();
        }
    }

    @Test
    public void testRead() {
        try (Connection c = ConnectionUtil.getConnection()) {
            BookingGuestDAO dao = new BookingGuestDAO(c);
            List<BookingGuest> bookingPayments = dao.readAll();
            assertTrue(bookingPayments.size() > 0);
            for (BookingGuest type : bookingPayments) {
                assertTrue(type.validate());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            fail();
        }
    }

}