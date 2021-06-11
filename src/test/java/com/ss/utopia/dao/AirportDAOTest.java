package com.ss.utopia.dao;

import com.ss.utopia.db.ConnectionUtil;
import com.ss.utopia.entity.AirplaneType;
import com.ss.utopia.entity.Airport;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AirportDAOTest {

    @Test
    void addUpdateDelete(){
        //create Airport object with sample data
        Airport a = new Airport("XXX", "CityX");
        try(Connection c = ConnectionUtil.getTransaction()) {
            try{
                AirportDAO dao = new AirportDAO(c);
                a = dao.addAirport(a);
                //primary key should be unchanged
                assertEquals("XXX", a.getIataId());

                //Update object
                String newCity = a.setCity("CityY").getCity();

                dao.updateAirportById(a, a.getIataId());

                //read object and check value
                a = dao.readAirportById(a.getIataId());
                assertEquals(newCity, a.getCity());

                //delete object
                dao.deleteAirport(a);

                //read should be null
                assertNull(dao.readAirportById(a.getIataId()));

            }catch (SQLException e){
                e.printStackTrace();
                fail();
            }

            c.rollback();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            fail();
        }
    }

    @Test
    void readAll() {
        try(Connection c = ConnectionUtil.getConnection()){
            AirportDAO atd = new AirportDAO(c);
            List<Airport> airports = atd.readAll();
            for (Airport type : airports) {
                assertTrue(type.validate()); //every object fully populated
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            fail();
        }
    }
}