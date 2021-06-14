package com.ss.service.admin;

import com.ss.utopia.dao.*;
import com.ss.utopia.db.ConnectionUtil;
import com.ss.utopia.entity.*;

import java.sql.Connection;
import java.sql.SQLException;

public class AdminExecutorAdds {

    public Flight addFlight(Flight flight) {
        try (Connection c = ConnectionUtil.getTransaction()) {
            try {
                FlightDAO flightDAO = new FlightDAO(c);
                flight = flightDAO.addFlight(flight);
                c.commit();
                return flight;
            } catch (SQLException ignored) {
                c.rollback();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null; //failure
    }

    public Booking addBooking(Integer flightId, String seatType, String confirmation, Integer userId, Integer agentId, Passenger passenger) {
        try (Connection c = ConnectionUtil.getTransaction()) {
            try {
                BookingDAO bookingDAO = new BookingDAO(c);
                FlightBookingDAO flightBookingDAO = new FlightBookingDAO(c);
                FlightDAO flightDAO = new FlightDAO(c);
                BookingUserDAO bookingUserDAO = new BookingUserDAO(c);
                PassengerDAO passengerDAO = new PassengerDAO(c);
                BookingPaymentDAO bookingPaymentDAO = new BookingPaymentDAO(c);


                //check flight not full
                AirplaneDAO airplaneDAO = new AirplaneDAO(c);
                AirplaneTypeDAO airplaneTypeDAO = new AirplaneTypeDAO(c);

                Flight f = flightDAO.readFlightById(flightId);
                AirplaneType type =
                        airplaneTypeDAO.readAirplaneTypeById(airplaneDAO.readAirplaneById(f.getAirplane().getId())
                                .getAirplaneType().getId());

                if(f.getReservedSeats() > type.getBusinessCapacity() + type.getFirstCapacity() + type.getEconomyCapacity()){
                    return null; //nothing has been added yet
                }

                //book flight
                Booking b = bookingDAO.addBooking(new Booking(-1, true, "idk", seatType));
                bookingUserDAO.addBookingUser(new BookingUser(b, new User().setId(userId)));
                bookingPaymentDAO.addBookingPayment(new BookingPayment(b, "id", false));
                flightBookingDAO.addFlightBooking(new FlightBooking(new Flight().setId(flightId), b));
                passengerDAO.addPassenger(passenger.setBooking(b));
                if(agentId != null)
                    new BookingAgentDAO(c).addBookingAgent(new BookingAgent(b, new User().setId(agentId)));

                c.commit();
                return b;
            } catch (SQLException ignored) {
                c.rollback();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public Airport addAirport(Airport airport) {
        try (Connection c = ConnectionUtil.getTransaction()) {
            try {
                AirportDAO airportDAO = new AirportDAO(c);
                Airport a = airportDAO.addAirport(airport);
                c.commit();
                return a;
            } catch (SQLException ignored) {
                c.rollback();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public User addUser(User user) {
        try (Connection c = ConnectionUtil.getTransaction()) {
            try {
                UserDAO userDAO = new UserDAO(c);
                user = userDAO.addUser(user);
                c.commit();
                return user;
            } catch (SQLException ignored) {
                c.rollback();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
