package com.ss.utopia.dao;

import com.ss.utopia.entity.Airplane;
import com.ss.utopia.entity.Flight;
import com.ss.utopia.entity.Route;
import com.ss.utopia.util.SQLUtil;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * DAO object for flight table
 *
 * @see com.ss.utopia.dao.BaseDAO
 * @see com.ss.utopia.entity.Flight
 */
public class FlightDAO extends BaseDAO<Flight> {
    //formatter for adding dates to sql database
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Takes sql connection object, pass connection without auto commits for transactions
     *
     * @param conn SQL connection
     */
    public FlightDAO(Connection conn) {
        super(conn);
    }

    /**
     * Add Flight to flight table, id overwritten
     *
     * @param flight Flight to add
     * @throws SQLException invalid data or server failure
     * @see com.ss.utopia.entity.Flight
     */
    public Flight addFlight(Flight flight) throws SQLException {
        if (!flight.validate()) throw new InvalidParameterException("Missing parameters");
        Integer id = save("INSERT into flight (route_id, airplane_id, departure_time, reserved_seats, seat_price) values(?, ?, ?, ?, ?)",
                new Object[]{
                        flight.getRoute().getId(),
                        flight.getAirplane().getId(),
                        formatter.format(flight.getDepartureTime().withZoneSameInstant(ZoneOffset.UTC)), //always add with UTC time
                        flight.getReservedSeats(),
                        flight.getSeatPrice()});
        return flight.setId(id);
    }

    /**
     * Updates existing flights with id
     *
     * @param newFlight flight information to add, must be fully populated
     * @throws SQLException invalid data or server failure
     */
    public void updateFlight(Flight newFlight) throws SQLException {
        if (!newFlight.validate()) throw new InvalidParameterException("Missing parameters");
        save("UPDATE flight SET route_id = ?, airplane_id = ?, departure_time = ?, reserved_seats = ?, seat_price = ? WHERE id = ?",
                new Object[]{
                        newFlight.getRoute().getId(),
                        newFlight.getAirplane().getId(),
                        formatter.format(newFlight.getDepartureTime().withZoneSameInstant(ZoneOffset.UTC)),
                        newFlight.getReservedSeats(),
                        newFlight.getSeatPrice(),
                        newFlight.getId()});
    }

    /**
     * Search table with parameters
     *
     * @param search must have at least one pair, services responsibility to have correct column names
     * @return List of entity
     * @throws SQLException invalid data or server failure
     */
    public List<Flight> search(LinkedHashMap<String, String> search) throws SQLException {
        if (search.size() == 0) return new ArrayList<>();
        return read(SQLUtil.constructSqlSearch("flight", search.size()), SQLUtil.collapseMap(search));
    }



    /**
     * Delete flight of id
     *
     * @param id id to delete
     * @throws SQLException invalid data or server failure
     */
    public void deleteFlightById(Integer id) throws SQLException {
        save("DELETE FROM flight WHERE id = ?", new Object[]{id});
    }

    /**
     * Delete flight
     *
     * @param a flight to delete
     * @throws SQLException invalid data or server failure
     */
    public void deleteFlight(Flight a) throws SQLException {
        deleteFlightById(a.getId());
    }

    /**
     * Read flight at specified id
     *
     * @param id id to read
     * @return Flight
     * @throws SQLException invalid data or server failure
     * @see com.ss.utopia.entity.Flight
     */
    public Flight readFlightById(Integer id) throws SQLException {
        List<Flight> flights = read("SELECT DISTINCT * from flight WHERE id = ?", new Object[]{id});
        if (flights.size() == 0) return null;
        else return flights.get(0);
    }

    /**
     * Read all flights
     *
     * @return List of Flight
     * @throws SQLException invalid data or server failure
     * @see com.ss.utopia.entity.Flight
     */
    public List<Flight> readAll() throws SQLException {
        return read("Select * FROM flight", new Object[]{});
    }


    @Override
    protected List<Flight> extractData(ResultSet rs) throws SQLException {
        List<Flight> list = new ArrayList<>();
        while (rs.next()) {
            Flight temp = new Flight();
            temp.setId(rs.getInt("id"));
            temp.setRoute(new Route().setId(rs.getInt("route_id")));
            temp.setAirplane(new Airplane().setId(rs.getInt("airplane_id")));
            temp.setDepartureTime(LocalDateTime.parse(rs.getString("departure_time"), formatter).atZone(ZoneId.of("UTC"))); //append UTC time
            temp.setReservedSeats(rs.getInt("reserved_seats"));
            temp.setSeatPrice(rs.getFloat("seat_price"));
            list.add(temp);
        }
        return list;
    }
}
