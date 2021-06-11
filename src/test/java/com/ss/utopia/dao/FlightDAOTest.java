package com.ss.utopia.dao;

import com.ss.utopia.db.ConnectionUtil;
import com.ss.utopia.entity.Airplane;
import com.ss.utopia.entity.Flight;
import com.ss.utopia.entity.Route;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightDAOTest {

    @Test
    void addUpdateDelete(){
        try (Connection c = ConnectionUtil.getTransaction()) {
            try{
                FlightDAO dao = new FlightDAO(c);

                //add flight
                Flight f = new Flight(-2,
                        new Route().setId(1),
                        new Airplane().setId(1),
                        ZonedDateTime.now(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")),
                        25,
                        60f);
                Flight oldF = new Flight(f);

                f = dao.addFlight(f);
                assertNotEquals(oldF.getId(), f.getId()); //id should change

                //update flight
                oldF = new Flight(f);
                f.setReservedSeats(34);
                f.setSeatPrice(80f);
                f.getAirplane().setId(2);

                dao.updateFlight(f);
                f = dao.readFlightById(f.getId());


                assertNotEquals(oldF, f);

                //delete flight
                dao.deleteFlight(f);
                assertNull(dao.readFlightById(f.getId()));



            } catch (SQLException throwable){
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
        try(Connection c = ConnectionUtil.getConnection()){
            FlightDAO dao = new FlightDAO(c);
            List<Flight> flightList = dao.readAll();
            for (Flight flight : flightList) {
                assertTrue(flight.validate()); //populated objects
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}