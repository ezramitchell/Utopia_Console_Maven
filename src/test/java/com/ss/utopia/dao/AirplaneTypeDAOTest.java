package com.ss.utopia.dao;

import com.ss.utopia.db.ConnectionUtil;
import com.ss.utopia.entity.AirplaneType;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AirplaneTypeDAOTest {

    @Test
    public void testAddDelete(){



        try(Connection c = ConnectionUtil.getTransaction()){
            try {
                AirplaneTypeDAO atd = new AirplaneTypeDAO(c);
                //add airplane

                AirplaneType at = new AirplaneType(-1, 20, 20, 20);
                AirplaneType copy = new AirplaneType(at);
                at = atd.addAirplaneType(at); //returns object with created id

                assertNotEquals(copy, at); //primary key should have changed
                assertEquals(copy.getFirstCapacity(), at.getFirstCapacity()); //other values should not change

                //update object
                at.setFirstCapacity(40);
                atd.updateAirplaneType(at);

                //read object, check value
                at = atd.readAirplaneTypeById(at.getId());
                assertNotNull(at);
                assertEquals(40, at.getFirstCapacity());

                //delete object
                atd.deleteAirplaneType(at);

                assertNull(atd.readAirplaneTypeById(at.getId())); //object should be deleted
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
            AirplaneTypeDAO atd = new AirplaneTypeDAO(c);
            List<AirplaneType> airplaneTypeList = atd.readAll();
            assertTrue(airplaneTypeList.size() > 0);
            for (AirplaneType type : airplaneTypeList) {
                assertTrue(type.validate());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            fail();
        }
    }

}