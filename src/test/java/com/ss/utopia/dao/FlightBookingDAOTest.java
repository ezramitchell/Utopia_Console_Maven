package com.ss.utopia.dao;

import com.ss.utopia.db.ConnectionUtil;
import com.ss.utopia.entity.Booking;
import com.ss.utopia.entity.Flight;
import com.ss.utopia.entity.FlightBooking;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightBookingDAOTest {

    @Test
    void addUpdateDelete(){
        try(Connection c = ConnectionUtil.getTransaction()) {
            try{
                FlightBookingDAO fbDAO = new FlightBookingDAO(c);
                //needed for both insertion and deletion due to 'unique' constraint
                BookingDAO bDAO = new BookingDAO(c);

                //add item
                Booking b = new Booking(-1, true, "confirm");
                b = bDAO.addBooking(b);

                FlightBooking fb = new FlightBooking(new Flight().setId(3), b);
                fbDAO.addFlightBooking(fb);

                //full check impossible because booking is populated in original object
                assertEquals(fb.getFlight().getId(), fbDAO.readFBId(fb.getBooking().getId()).getFlight().getId());
                //update booking
                FlightBooking oldFb = new FlightBooking(fb); //for comparison
                fb.getFlight().setId(4);

                fbDAO.updateBooking(fb);

                assertNotEquals(oldFb, fbDAO.readFBId(fb.getBooking().getId())); //changed values
                //depopulate booking for equal check
                assertEquals(fb.setBooking(new Booking().setId(b.getId())), fbDAO.readFBId(fb.getBooking().getId())); //check for lost data

                //delete booking
                bDAO.deleteBooking(b);
                assertNull(fbDAO.readFBId(fb.getBooking().getId()));

            }catch (SQLException throwable){
                throwable.printStackTrace();
                fail();
            }
            c.rollback();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            fail();
        }
    }

    @Test
    void readAll() {
        try (Connection c = ConnectionUtil.getConnection()) {
            List<FlightBooking> flightBookings = new FlightBookingDAO(c).readAll();
            assertTrue(flightBookings.size() > 0);
            for (FlightBooking fb : flightBookings) {
                assertTrue(fb.validate()); //fully populated data
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

    }
}