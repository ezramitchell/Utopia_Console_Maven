package com.ss.utopia.dao;

import com.ss.utopia.db.ConnectionUtil;
import com.ss.utopia.entity.Booking;
import com.ss.utopia.entity.BookingAgent;
import com.ss.utopia.entity.User;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookingAgentDAOTest {

    @Test
    public void testAddDelete() {
        try (Connection c = ConnectionUtil.getTransaction()) {
            try {
                BookingAgentDAO agentDAO = new BookingAgentDAO(c);
                BookingDAO bookDAO = new BookingDAO(c);
                //add airplane

                Booking b = new Booking(-1, true, "confirmed", "economy");

                bookDAO.addBooking(b); // add booking that payment references
                // necessary so data doesn't influence testing

                BookingAgent bookingAgent = new BookingAgent(new Booking().setId(b.getId()), new User().setId(1));

                //add bookingAgent
                agentDAO.addBookingAgent(bookingAgent);
                //check object exists
                assertNotNull(agentDAO.readBookingAgentById(bookingAgent.getBooking().getId()));


                //update object
                bookingAgent.setAgent(new User().setId(3));
                BookingAgent copy = new BookingAgent(bookingAgent); //copy for comparison

                agentDAO.updateBookingAgent(bookingAgent);

                //read object, check value
                bookingAgent = agentDAO.readBookingAgentById(bookingAgent.getBooking().getId());
                assertNotNull(bookingAgent);
                assertEquals(bookingAgent, copy);

                //delete object
                agentDAO.deleteBookingAgent(bookingAgent);

                assertNull(agentDAO.readBookingAgentById(bookingAgent.getBooking().getId())); //object should be deleted

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
            BookingAgentDAO dao = new BookingAgentDAO(c);
            List<BookingAgent> bookingPayments = dao.readAll();
            assertTrue(bookingPayments.size() > 0);
            for (BookingAgent type : bookingPayments) {
                assertTrue(type.validate());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            fail();
        }
    }

}