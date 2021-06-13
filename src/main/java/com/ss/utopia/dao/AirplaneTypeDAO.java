package com.ss.utopia.dao;

import com.ss.utopia.entity.AirplaneType;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * DAO for AirplaneType table
 *
 * @see BaseDAO
 * @see AirplaneType
 */
public class AirplaneTypeDAO extends BaseDAO<AirplaneType> {

    /**
     * Takes sql connection object, pass connection without auto commits for transactions
     *
     * @param conn SQL connection
     */
    public AirplaneTypeDAO(Connection conn) {
        super(conn);
    }

    /**
     * Add airplaneType to database, airplaneType id is ignored
     *
     * @param airplaneType AirplaneType object, all fields necessary except id
     * @see com.ss.utopia.entity.AirplaneType
     */
    public AirplaneType addAirplaneType(AirplaneType airplaneType) throws SQLException {
        if (!airplaneType.validate()) throw new InvalidParameterException("Missing parameters");
        Integer id = save("INSERT INTO airplane_type (first_capacity, economy_capacity, business_capacity) VALUES (?, ?, ?)",
                new Object[]{airplaneType.getFirstCapacity(), airplaneType.getEconomyCapacity(), airplaneType.getBusinessCapacity()});
        return airplaneType.setId(id);
    }

    /**
     * Update AirplaneType of id
     *
     * @param newAirplaneType new airplaneType data
     * @throws SQLException invalid data or server failure
     */
    public void updateAirplaneType(AirplaneType newAirplaneType) throws SQLException {
        if (!newAirplaneType.validate()) throw new InvalidParameterException("Missing parameters");

        save("UPDATE airplane_type SET first_capacity = ?, economy_capacity = ?, business_capacity = ? WHERE id = ?",
                new Object[]{newAirplaneType.getFirstCapacity(),
                        newAirplaneType.getEconomyCapacity(),
                        newAirplaneType.getBusinessCapacity(),
                        newAirplaneType.getId()});
    }

    /**
     * Find airplaneType specified by id
     *
     * @param id id to search for
     * @return AirplaneType matching id
     * @throws SQLException invalid data or server failure
     * @see AirplaneType
     */
    public AirplaneType readAirplaneTypeById(Integer id) throws SQLException {
        List<AirplaneType> airplaneType = read("SELECT * FROM airplane_type WHERE id = ?",
                new Object[]{id});
        if (airplaneType.size() == 0) return null;
        return airplaneType.get(0);
    }

    /**
     * Read all airplaneTypes
     *
     * @return list of AirplaneType Object
     * @throws SQLException invalid data or server failure
     * @see AirplaneType
     */
    public List<AirplaneType> readAll() throws SQLException {
        return read("SELECT * FROM airplane_type", null);
    }

    /**
     * Deletes airplaneType of id
     *
     * @param id id to delete
     */
    public void deleteAirplaneTypeById(Integer id) throws SQLException {
        save("DELETE from airplane_type WHERE id = ?", new Object[]{id});
    }

    /**
     * Deletes airplaneType
     *
     * @param airplaneType airplaneType to delete
     */
    public void deleteAirplaneType(AirplaneType airplaneType) throws SQLException {
        deleteAirplaneTypeById(airplaneType.getId());
    }



    @Override
    protected List<AirplaneType> extractData(ResultSet rs) throws SQLException {
        List<AirplaneType> airplaneType;
        airplaneType = new ArrayList<>();
        while (rs.next()) {
            AirplaneType temp = new AirplaneType();
            temp.setId(rs.getInt("id"));
            temp.setFirstCapacity(rs.getInt("first_capacity"));
            temp.setEconomyCapacity(rs.getInt("economy_capacity"));
            temp.setBusinessCapacity(rs.getInt("business_capacity"));
            airplaneType.add(temp);
        }
        return airplaneType;
    }
}
