package com.ss.utopia.dao;

import com.ss.utopia.entity.Airport;
import com.ss.utopia.entity.Flight;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * DAO for Airport table
 *
 * @see com.ss.utopia.dao.BaseDAO
 * @see com.ss.utopia.entity.Airport
 */
public class AirportDAO extends BaseDAO<Airport> {
    /**
     * Takes sql connection object, pass connection without auto commits for transactions
     *
     * @param conn SQL Connection
     */
    public AirportDAO(Connection conn) {
        super(conn);
    }

    /**
     * Inserts new Airport into airport table
     *
     * @param airport Airport object, both fields necessary
     * @return airport object that was inserted
     * @throws SQLException invalid data or server failure
     */
    public Airport addAirport(Airport airport) throws SQLException {
        if (!airport.validate()) throw new InvalidParameterException("Missing parameters");
        save("INSERT into airport Values(?, ?)", new Object[]{airport.getIataId(), airport.getCity()});
        return airport;
    }

    /**
     * Change Airport with id to new Airport
     *
     * @param newAirport to change to, fully populated
     * @param id         row to change
     * @throws SQLException invalid data or server failure
     */
    public void updateAirportById(Airport newAirport, String id) throws SQLException {
        if (!newAirport.validate()) throw new InvalidParameterException("Missing parameters");
        save("UPDATE airport SET iata_id = ?, city = ? WHERE iata_id = ?",
                new Object[]{newAirport.getIataId(), newAirport.getCity(), id});
    }

    /**
     * Change Airport with id to new Airport
     *
     * @param airport airport to update, fully populated
     * @throws SQLException invalid data or server failure
     */
    public void updateAirport(Airport airport) throws SQLException {
        updateAirportById(airport, airport.getIataId());
    }

    /**
     * For use in admin methods, adminExecutor responsible for correct column names
     * @param params column value pairs
     * @return list of flights
     * @throws SQLException invalid data or server failure
     */
    public List<Airport> search(LinkedHashMap<String, String> params) throws SQLException {
        return read(
                DAOUtil.constructSQLSearchString("airport", params.keySet().toArray(new String[0])),
                params.values().toArray(new Object[0]));
    }

    /**
     * For checking valid column names
     * @return list of columns
     */
    public String[] getColumnNames() throws SQLException {
        return getColumnNames("airport");
    }


    /**
     * Deletes airport of id
     *
     * @param id id to delete
     * @throws SQLException invalid data or server failure
     */
    public void deleteAirportById(String id) throws SQLException {
        save("DELETE FROM airport WHERE iata_id = ?", new Object[]{id});
    }

    /**
     * Deletes Airport object from table
     *
     * @param a airport to delete
     * @throws SQLException invalid data or server failure
     */
    public void deleteAirport(Airport a) throws SQLException {
        deleteAirportById(a.getIataId());
    }

    /**
     * Get airport object from id
     *
     * @param id airport id to retrieve
     * @return Airport
     * @throws SQLException invalid data or server failure
     * @see com.ss.utopia.entity.Airport
     */
    public Airport readAirportById(String id) throws SQLException {
        List<Airport> airports = read("SELECT DISTINCT * from airport WHERE iata_id = ?", new Object[]{id});
        if (airports.size() == 0) return null;
        else return airports.get(0);
    }

    /**
     * Reads all airport objects
     *
     * @return List of Airports
     * @throws SQLException invalid data or server failure
     * @see com.ss.utopia.entity.Airport
     */
    public List<Airport> readAll() throws SQLException {
        return read("Select * FROM airport", new Object[]{});
    }


    @Override
    protected List<Airport> extractData(ResultSet rs) throws SQLException {
        List<Airport> list = new ArrayList<>();
        while (rs.next()) {
            Airport temp = new Airport();
            temp.setIataId(rs.getString("iata_id"));
            temp.setCity(rs.getString("city"));
            list.add(temp);
        }
        return list;
    }
}
