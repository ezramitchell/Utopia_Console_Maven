package com.ss.utopia.dao;

import com.ss.utopia.db.ConnectionUtil;
import com.ss.utopia.entity.Booking;
import com.ss.utopia.entity.BookingPayment;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookingPaymentDAOTest {

    @Test
    public void testAddDelete(){




        try(Connection c = ConnectionUtil.getTransaction()){
            try {
                BookingPaymentDAO bpDAO = new BookingPaymentDAO(c);
                BookingDAO bookDAO = new BookingDAO(c);
                //add airplane

                Booking b = new Booking(-1, true, "confirmed", "economy");
                BookingPayment bp = new BookingPayment(b, "id", false);


                bookDAO.addBooking(b); // add booking that payment references
                bpDAO.addBookingPayment(bp);
                //check object exists
                assertNotNull(bpDAO.readBookingPaymentById(bp.getBooking().getId()));


                //update object
                bp.setRefunded(true);
                bp.setStripeId("id2");
                BookingPayment copy = new BookingPayment(bp);

                bpDAO.updateBookingPayment(bp);

                //read object, check value
                bp = bpDAO.readBookingPaymentById(bp.getBooking().getId());
                assertNotNull(bp);
                assertEquals(bp.getStripeId(), copy.getStripeId()); //copy contains full booking object
                assertEquals(bp.getRefunded(), copy.getRefunded());

                //delete object
                bpDAO.deleteBookingPayment(bp);

                assertNull(bpDAO.readBookingPaymentById(bp.getBooking().getId())); //object should be deleted

            } catch (SQLException throwable){
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
    public void testRead(){
        try(Connection c = ConnectionUtil.getConnection()){
            BookingPaymentDAO dao = new BookingPaymentDAO(c);
            List<BookingPayment> bookingPayments = dao.readAll();
            assertTrue(bookingPayments.size() > 0);
            for (BookingPayment type : bookingPayments) {
                assertTrue(type.validate());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            fail();
        }
    }

}