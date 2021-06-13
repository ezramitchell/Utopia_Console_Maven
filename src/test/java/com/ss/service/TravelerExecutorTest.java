package com.ss.service;

import com.ss.utopia.dao.*;
import com.ss.utopia.db.ConnectionUtil;
import com.ss.utopia.entity.*;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class TravelerExecutorTest {

    @Test
    void readBookingsToCity() {
        TravelerExecutor te = new TravelerExecutor();
        for (FlightBooking fb : te.readBookingsToCity(new User().setId(7))) {
            assertTrue(fb.getFlight().getRoute().getDestinationAirport().validate());
            assertTrue(fb.getFlight().getRoute().getOriginAirport().validate());
        }
    }

    @Test
    void updateBookings() {
        TravelerExecutor te = new TravelerExecutor();
        try(Connection c = ConnectionUtil.getConnection()){
            BookingDAO bookingDAO = new BookingDAO(c);
            Booking original = bookingDAO.readBookingById(1);
            //change booking
            te.updateBookings(new Booking(original).setActive(false));
            Booking newBooking = bookingDAO.readBookingById(1);
            assertNotEquals(newBooking, original);
            //put booking back
            te.updateBookings(original);
            newBooking = bookingDAO.readBookingById(1);
            assertEquals(newBooking, original);


        } catch (SQLException throwable) {
            throwable.printStackTrace();
            fail();
        }
    }

    @Test
    void readAllFlightsToCity() {
        TravelerExecutor te = new TravelerExecutor();
        for (Flight fb : te.readAllFlightsToCity()) {
            assertTrue(fb.getRoute().getDestinationAirport().validate());
            assertTrue(fb.getRoute().getOriginAirport().validate());
        }
    }
}