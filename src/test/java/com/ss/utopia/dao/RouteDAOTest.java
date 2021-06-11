package com.ss.utopia.dao;

import com.ss.utopia.db.ConnectionUtil;
import com.ss.utopia.entity.Airport;
import com.ss.utopia.entity.Route;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RouteDAOTest {

    @Test
    void addUpdateDelete(){
        //airport validation should be done in services
        Route r = new Route(-1, new Airport().setIataId("DDD"), new Airport().setIataId("AAA"));

        try(Connection c = ConnectionUtil.getTransaction()){
            try{
                RouteDAO dao = new RouteDAO(c);

                //add new element, id should be changed
                Route oldRoute = new Route(r);
                r = dao.addRoute(r);

                assertNotEquals(oldRoute.getId(), r.getId());
                assertEquals(oldRoute.getDestinationAirport(), r.getDestinationAirport());
                assertEquals(oldRoute.getOriginAirport(), r.getOriginAirport());
                //Update object

                r.setOriginAirport(new Airport().setIataId("CCC"));
                r.setDestinationAirport(new Airport().setIataId("DDD"));
                oldRoute = new Route(r);

                dao.updateRoute(r);
                r = dao.readRouteById(r.getId());

                //make sure update happened
                assertEquals(oldRoute.getOriginAirport(), r.getOriginAirport());
                assertEquals(oldRoute.getDestinationAirport(), r.getDestinationAirport());

                //Delete object
                dao.deleteRoute(r);
                //Read should be null
                assertNull(dao.readRouteById(r.getId()));

            }catch (SQLException exception){
                exception.printStackTrace();
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
            RouteDAO dao = new RouteDAO(c);
            List<Route> routes = dao.readAll();
            for (Route route : routes) {
                assertTrue(route.validate()); //fully populated objects
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            fail();
        }
    }
}