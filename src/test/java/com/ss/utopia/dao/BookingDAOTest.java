package com.ss.utopia.dao;

import com.ss.utopia.db.ConnectionUtil;
import com.ss.utopia.entity.Booking;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class BookingDAOTest {

    @Test
    void addUpdateDelete(){
        try(Connection c = ConnectionUtil.getTransaction()){
            try{
                BookingDAO dao = new BookingDAO(c);

                //add object
                Booking b = new Booking(-1, true, "confirm");
                Booking oldB = new Booking(b);

                b = dao.addBooking(b);
                assertNotEquals(oldB.getId(), b.getId());

                //update object
                b.setConfirmationCode("not confirm");
                b.setActive(false);
                oldB = new Booking(b); //copy for comparison

                dao.updateBooking(b);
                b = dao.readBookingById(b.getId());
                assertEquals(oldB, b); //check for lost data

                //delete object
                dao.deleteBooking(b);
                assertNull(dao.readBookingById(b.getId()));

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
    }
}