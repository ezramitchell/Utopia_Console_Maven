package com.ss.utopia.dao;

import com.ss.utopia.db.ConnectionUtil;
import com.ss.utopia.entity.Booking;
import com.ss.utopia.entity.Passenger;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PassengerDAOTest {

    @Test
    public void testAddDelete() {
        try (Connection c = ConnectionUtil.getTransaction()) {
            try {
                PassengerDAO userDao = new PassengerDAO(c);
                BookingDAO bookDAO = new BookingDAO(c);
                //add airplane

                Booking b = new Booking(-1, true, "confirmed", "economy");

                bookDAO.addBooking(b); // add booking that guest references
                // necessary so data doesn't influence testing

                Passenger bookingUser = new Passenger(
                        -1,
                        new Booking().setId(b.getId()),
                        "name",
                        "last",
                        "m",
                        "address");

                //add bookingUser
                userDao.addPassenger(bookingUser);
                //check object exists
                assertNotNull(userDao.readPassengerByBookingId(bookingUser.getBooking().getId()));


                //update object
                bookingUser.setGender("f");
                bookingUser.setAddress("new Address");
                Passenger copy = new Passenger(bookingUser); //copy for comparison

                userDao.updatePassenger(bookingUser);

                //read object, check value
                bookingUser = userDao.readPassengerByBookingId(bookingUser.getBooking().getId());
                assertNotNull(bookingUser);
                assertEquals(bookingUser, copy);
                
                //delete object
                bookDAO.deleteBookingByID(bookingUser.getBooking().getId());

                assertNull(userDao.readPassengerById(bookingUser.getId())); //object should be deleted

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
            PassengerDAO dao = new PassengerDAO(c);
            List<Passenger> bookingPayments = dao.readAll();
            assertTrue(bookingPayments.size() > 0);
            for (Passenger type : bookingPayments) {
                assertTrue(type.validate());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            fail();
        }
    }

}