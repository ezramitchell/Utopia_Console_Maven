package com.ss.utopia.entity;

import org.junit.jupiter.api.Test;
import java.security.InvalidParameterException;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This is the only Entity that does any parameter checking because it's table has char(3) fields
 */
class AirportTest {

    @Test
    void setIataId() {
        Airport test = new Airport("AAA", "city");
        assertEquals("AAA", test.getIataId());
        try{
            test.setIataId("ADDD"); // 4 characters should fail
            fail();
        } catch (InvalidParameterException ignored){

        }
    }
}