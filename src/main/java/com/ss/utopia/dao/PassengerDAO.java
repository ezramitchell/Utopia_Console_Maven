package com.ss.utopia.dao;

import com.ss.utopia.entity.Booking;
import com.ss.utopia.entity.Passenger;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * DAO for Passenger table
 *
 * @see BaseDAO
 * @see Passenger
 */
public class PassengerDAO extends BaseDAO<Passenger> {

    /**
     * Takes sql connection object, pass connection without auto commits for transactions
     *
     * @param conn SQL connection
     */
    public PassengerDAO(Connection conn) {
        super(conn);
    }

    /**
     * Add passenger to database, Passenger should be fully populated
     *
     * @param passenger passenger object all fields necessary
     */
    public Passenger addPassenger(Passenger passenger) throws SQLException {
        if (!passenger.validate()) throw new InvalidParameterException("Missing parameters");
        Integer id = save("INSERT INTO passenger (booking_id, given_name, family_name, gender, address) " +
                        "VALUES (?, ?, ?, ?, ?)",
                new Object[]{passenger.getBooking().getId(),
                        passenger.getGivenName(),
                        passenger.getFamilyName(),
                        passenger.getGender(),
                        passenger.getAddress()});
        return passenger.setId(id);
    }

    /**
     * Update Passenger with booking_id, passenger should be fully populated
     *
     * @param passenger new passenger data, id used for update
     * @throws SQLException invalid data or server failure
     */
    public void updatePassenger(Passenger passenger) throws SQLException {
        if (!passenger.validate()) throw new InvalidParameterException("Missing parameters");
        save("UPDATE passenger SET " +
                        "booking_id = ?, " +
                        "given_name = ?, " +
                        "family_name = ?, " +
                        "gender = ?," +
                        "address = ? " +
                        "WHERE id = ?",
                new Object[]{passenger.getBooking().getId(),
                        passenger.getGivenName(),
                        passenger.getFamilyName(),
                        passenger.getGender(),
                        passenger.getAddress(),
                        passenger.getId()});
    }

    /**
     * Find passenger specified by id
     *
     * @param id id to search for
     * @return Passenger matching id
     * @throws SQLException invalid data or server failure
     * @see Passenger
     */
    public Passenger readPassengerById(Integer id) throws SQLException {
        List<Passenger> passengerList = read("SELECT * FROM passenger WHERE id = ?", new Object[]{id});
        if (passengerList.size() == 0) return null;
        return passengerList.get(0);
    }

    /**
     * Find passenger specified by booking_id
     *
     * @param id id to search for
     * @return Passenger matching id
     * @throws SQLException invalid data or server failure
     * @see Passenger
     */
    public Passenger readPassengerByBookingId(Integer id) throws SQLException {
        List<Passenger> passengerList = read("SELECT * FROM passenger WHERE booking_id = ?", new Object[]{id});
        if (passengerList.size() == 0) return null;
        return passengerList.get(0);
    }

    /**
     * Read all passengers
     *
     * @return list of Passenger Object
     * @throws SQLException invalid data or server failure
     * @see Passenger
     */
    public List<Passenger> readAll() throws SQLException {
        return read("SELECT * FROM passenger", null);
    }


    /**
     * Deletes passenger of booking_id
     *
     * @param id id to delete
     */
    public void deletePassengerById(Integer id) throws SQLException {
        save("DELETE from passenger WHERE id = ?", new Object[]{id});
    }

    /**
     * Deletes passenger
     *
     * @param passenger passenger to delete
     */
    public void deletePassenger(Passenger passenger) throws SQLException {
        deletePassengerById(passenger.getBooking().getId());
    }


    @Override
    protected List<Passenger> extractData(ResultSet rs) throws SQLException {
        List<Passenger> passengers = new ArrayList<>();
        while (rs.next()) {
            Passenger temp = new Passenger();
            temp.setId(rs.getInt("id"));
            temp.setBooking(new Booking().setId(rs.getInt("booking_id")));
            temp.setGivenName(rs.getString("given_name"));
            temp.setFamilyName(rs.getString("family_name"));
            temp.setGender(rs.getString("gender"));
            temp.setAddress(rs.getString("address"));
            passengers.add(temp);
        }
        return passengers;
    }
}
