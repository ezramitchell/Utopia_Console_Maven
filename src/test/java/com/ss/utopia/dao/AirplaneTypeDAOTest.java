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
        AirplaneType at = new AirplaneType();
        Integer fMaxCap = 60;
        at.setMaxCapacity(fMaxCap);
        Integer fId = at.setId(-1).getId();


        try(Connection c = ConnectionUtil.getTransaction()){
            try {
                AirplaneTypeDAO atd = new AirplaneTypeDAO(c);
                //add airplane
                at = atd.addAirplaneType(at); //returns object with created id

                assertNotEquals(fId, at.getId()); //primary key should have changed
                assertEquals(fMaxCap, at.getMaxCapacity()); //other values should not change

                //update object
                Integer newVal = 70;
                atd.updateAirplaneType(at.setMaxCapacity(newVal));

                //read object, check value
                at = atd.readAirplaneTypeById(at.getId());
                assertNotNull(at);
                assertEquals(newVal, at.getMaxCapacity());

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
            for (AirplaneType type : airplaneTypeList) {
                assertTrue(type.validate());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            fail();
        }
    }

}