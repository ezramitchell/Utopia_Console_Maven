package com.ss.utopia.dao;

import com.ss.utopia.db.ConnectionUtil;
import com.ss.utopia.entity.Airplane;
import com.ss.utopia.entity.AirplaneType;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AirplaneDAOTest {

    @Test
    public void testAddDelete(){
        try(Connection c = ConnectionUtil.getTransaction()){
            try {
                AirplaneDAO atd = new AirplaneDAO(c);
                //add airplane

                Airplane at = new Airplane(-1, new AirplaneType().setId(1));
                Airplane copy = new Airplane(at);
                at = atd.addAirplane(at); //returns object with created id

                assertNotEquals(copy, at); //primary key should have changed
                assertEquals(copy.getAirplaneType(), at.getAirplaneType()); //other values should not change

                //update object
                at.setAirplaneType(new AirplaneType().setId(4));
                atd.updateAirplane(at);

                //read object, check value
                at = atd.readAirplaneById(at.getId());
                assertNotNull(at);
                assertNotEquals(at, copy);
                assertEquals(4, at.getAirplaneType().getId());

                //delete object
                atd.deleteAirplane(at);

                assertNull(atd.readAirplaneById(at.getId())); //object should be deleted
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
            AirplaneDAO atd = new AirplaneDAO(c);
            List<Airplane> airplanes = atd.readAll();
            assertTrue(airplanes.size() > 0);
            for (Airplane type : airplanes) {
                assertTrue(type.validate());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            fail();
        }
    }

}