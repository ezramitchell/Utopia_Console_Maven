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
                BookingUserDAO agentDAO = new BookingUserDAO(c);
                BookingDAO bookDAO = new BookingDAO(c);
                //add airplane

                Booking b = new Booking(-1, true, "confirmed");

                bookDAO.addBooking(b); // add booking that payment references
                // necessary so data doesn't influence testing

                BookingUser bookingAgent = new BookingUser(new Booking().setId(b.getId()), new User().setId(1));

                //add bookingAgent
                agentDAO.addBookingUser(bookingAgent);
                //check object exists
                assertNotNull(agentDAO.readBookingUserById(bookingAgent.getBooking().getId()));


                //update object
                bookingAgent.setUser(new User().setId(3));
                BookingUser copy = new BookingUser(bookingAgent); //copy for comparison

                agentDAO.updateBookingUser(bookingAgent);

                //read object, check value
                bookingAgent = agentDAO.readBookingUserById(bookingAgent.getBooking().getId());
                assertNotNull(bookingAgent);
                assertEquals(bookingAgent, copy);

                //delete object
                agentDAO.deleteBookingUser(bookingAgent);

                assertNull(agentDAO.readBookingUserById(bookingAgent.getBooking().getId())); //object should be deleted

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