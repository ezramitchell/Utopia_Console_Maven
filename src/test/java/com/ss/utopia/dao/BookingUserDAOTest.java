package com.ss.utopia.dao;

import com.ss.utopia.db.ConnectionUtil;
import com.ss.utopia.entity.Booking;
import com.ss.utopia.entity.BookingUser;
import com.ss.utopia.entity.User;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookingUserDAOTest {

    @Test
    public void testAddDelete() {
        try (Connection c = ConnectionUtil.getTransaction()) {
            try {
                BookingUserDAO userDao = new BookingUserDAO(c);
                BookingDAO bookDAO = new BookingDAO(c);
                //add airplane

                Booking b = new Booking(-1, true, "confirmed");

                bookDAO.addBooking(b); // add booking that payment references
                // necessary so data doesn't influence testing

                BookingUser bookingUser = new BookingUser(new Booking().setId(b.getId()), new User().setId(1));

                //add bookingUser
                userDao.addBookingUser(bookingUser);
                //check object exists
                assertNotNull(userDao.readBookingUserByBooking(bookingUser.getBooking().getId()));


                //update object
                bookingUser.setUser(new User().setId(3));
                BookingUser copy = new BookingUser(bookingUser); //copy for comparison

                userDao.updateBookingUser(bookingUser);

                //read object, check value
                bookingUser = userDao.readBookingUserByBooking(bookingUser.getBooking().getId());
                assertNotNull(bookingUser);
                assertEquals(bookingUser, copy);

                //delete object
                userDao.deleteBookingUser(bookingUser);

                assertNull(userDao.readBookingUserByBooking(bookingUser.getBooking().getId())); //object should be deleted

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
            BookingUserDAO dao = new BookingUserDAO(c);
            List<BookingUser> bookingPayments = dao.readAll();
            assertTrue(bookingPayments.size() > 0);
            for (BookingUser type : bookingPayments) {
                assertTrue(type.validate());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            fail();
        }
    }

}