package com.ss.utopia.dao;

import com.ss.utopia.entity.Airport;
import com.ss.utopia.entity.Route;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for Route table
 *
 * @see com.ss.utopia.dao.BaseDAO
 * @see com.ss.utopia.entity.Route
 */
public class RouteDAO extends BaseDAO<Route> {

    /**
     * Takes sql connection object, pass connection without auto commits for transactions
     *
     * @param conn SQL connection
     */
    public RouteDAO(Connection conn) {
        super(conn);
    }

    /**
     * Add route to database, route id is overwritten. Route should be fully populated
     *
     * @param route route object, all fields necessary except id
     * @return retrieved Route
     */
    public Route addRoute(Route route) throws SQLException {
        if (!route.validate()) throw new InvalidParameterException("Missing parameters");
        Integer id = save("INSERT INTO route (origin_id, destination_id) VALUES (?, ?)",
                new Object[]{route.getOriginAirport().getIataId(), route.getDestinationAirport().getIataId()});
        return route.setId(id);
    }

    /**
     * Update Route of id, route should be fully populated
     *
     * @param newRoute new route data
     * @param id       id to update
     * @throws SQLException invalid data or server failure
     */
    public void updateRouteById(Route newRoute, Integer id) throws SQLException {
        if (!newRoute.validate()) throw new InvalidParameterException("Missing parameters");
        save("UPDATE route SET origin_id = ?, destination_id = ? WHERE id = ?",
                new Object[]{newRoute.getOriginAirport().getIataId(), newRoute.getDestinationAirport().getIataId(), id});
    }

    /**
     * Update Route
     *
     * @param newRoute fully populated
     * @throws SQLException invalid data or server failure
     */
    public void updateRoute(Route newRoute) throws SQLException {
        updateRouteById(newRoute, newRoute.getId());
    }

    /**
     * Find route specified by id
     *
     * @param id id to search for
     * @return Route matching id
     * @throws SQLException invalid data or server failure
     * @see com.ss.utopia.entity.Route
     */
    public Route readRouteById(Integer id) throws SQLException {
        List<Route> routeList = read("SELECT * FROM route WHERE id = ?", new Object[]{id});
        if (routeList.size() == 0) return null;
        return routeList.get(0);
    }

    /**
     * Read all routes
     *
     * @return list of Route Object
     * @throws SQLException invalid data or server failure
     * @see com.ss.utopia.entity.Route
     */
    public List<Route> readAll() throws SQLException {
        return read("SELECT * FROM route", new Object[]{});
    }

    /**
     * Deletes route of id
     *
     * @param id id to delete
     */
    public void deleteRouteById(Integer id) throws SQLException {
        save("DELETE from route WHERE id = ?", new Object[]{id});
    }

    /**
     * Deletes route
     *
     * @param route route to delete
     */
    public void deleteRoute(Route route) throws SQLException {
        deleteRouteById(route.getId());
    }


    @Override
    protected List<Route> extractData(ResultSet rs) throws SQLException {
        List<Route> routeList = new ArrayList<>();
        while (rs.next()) {
            Route temp = new Route();
            temp.setId(rs.getInt("id"));
            temp.setOriginAirport(new Airport().setIataId(rs.getString("origin_id")));
            temp.setDestinationAirport(new Airport().setIataId(rs.getString("destination_id")));
            routeList.add(temp);
        }
        return routeList;
    }
}
