package com.ss.utopia.dao;

import com.ss.utopia.entity.Airplane;
import com.ss.utopia.entity.AirplaneType;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * DAO for Airplane table
 *
 * @see BaseDAO
 * @see Airplane
 */
public class AirplaneDAO extends BaseDAO<Airplane> {

    /**
     * Takes sql connection object, pass connection without auto commits for transactions
     *
     * @param conn SQL connection
     */
    public AirplaneDAO(Connection conn) {
        super(conn);
    }

    /**
     * Add airplane to database, airplane id is ignored
     *
     * @param airplane Airplane object, all fields necessary except id
     * @see Airplane
     */
    public Airplane addAirplane(Airplane airplane) throws SQLException {
        if (!airplane.validate()) throw new InvalidParameterException("Missing parameters");
        Integer id = save("INSERT INTO airplane (type_id) VALUES (?)",
                new Object[]{airplane.getAirplaneType().getId()});
        return airplane.setId(id);
    }

    /**
     * Update Airplane of id
     *
     * @param newAirplane new airplane data
     * @throws SQLException invalid data or server failure
     */
    public void updateAirplane(Airplane newAirplane) throws SQLException {
        if (!newAirplane.validate()) throw new InvalidParameterException("Missing parameters");
        save("UPDATE airplane SET type_id = ? WHERE id = ?",
                new Object[]{
                        newAirplane.getAirplaneType().getId(),
                        newAirplane.getId()});
    }

    /**
     * Find airplane specified by id
     *
     * @param id id to search for
     * @return Airplane matching id
     * @throws SQLException invalid data or server failure
     * @see Airplane
     */
    public Airplane readAirplaneById(Integer id) throws SQLException {
        List<Airplane> airplane = read("SELECT * FROM airplane WHERE id = ?",
                new Object[]{id});
        if (airplane.size() == 0) return null;
        return airplane.get(0);
    }


    /**
     * Read all airplanes
     *
     * @return list of Airplane Object
     * @throws SQLException invalid data or server failure
     * @see Airplane
     */
    public List<Airplane> readAll() throws SQLException {
        return read("SELECT * FROM airplane", null);
    }

    /**
     * Deletes airplane of id
     *
     * @param id id to delete
     */
    public void deleteAirplaneById(Integer id) throws SQLException {
        save("DELETE from airplane WHERE id = ?", new Object[]{id});
    }

    /**
     * Deletes airplane
     *
     * @param airplane airplane to delete
     */
    public void deleteAirplane(Airplane airplane) throws SQLException {
        deleteAirplaneById(airplane.getId());
    }


    @Override
    protected List<Airplane> extractData(ResultSet rs) throws SQLException {
        List<Airplane> airplane;
        airplane = new ArrayList<>();
        while (rs.next()) {
            Airplane temp = new Airplane();
            temp.setId(rs.getInt("id"));
            temp.setAirplaneType(new AirplaneType().setId(rs.getInt("type_id")));
            airplane.add(temp);
        }
        return airplane;
    }
}
