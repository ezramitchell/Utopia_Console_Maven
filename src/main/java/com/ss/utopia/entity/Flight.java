package com.ss.utopia.entity;

import java.time.ZonedDateTime;
import java.util.Objects;

public class Flight {
    private Integer id;
    private Route route;
    private Airplane airplane;
    private ZonedDateTime departureTime;
    private Integer reservedSeats;
    private Float seatPrice;

    public boolean validate() {
        return id != null
                && route != null
                && airplane != null
                && departureTime != null
                && reservedSeats != null
                && seatPrice != null;
    }

    public Flight() {
    }

    public Flight(Flight other) {
        this.id = other.id;
        this.route = other.route;
        this.airplane = other.airplane;
        this.departureTime = other.departureTime;
        this.reservedSeats = other.reservedSeats;
        this.seatPrice = other.seatPrice;
    }

    public Flight(Integer id, Route route, Airplane airplane, ZonedDateTime departureTime, Integer reservedSeats, Float seatPrice) {
        this.id = id;
        this.route = route;
        this.airplane = airplane;
        this.departureTime = departureTime;
        this.reservedSeats = reservedSeats;
        this.seatPrice = seatPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(id, flight.id) && Objects.equals(route, flight.route) && Objects.equals(airplane, flight.airplane) && Objects.equals(departureTime, flight.departureTime) && Objects.equals(reservedSeats, flight.reservedSeats) && Objects.equals(seatPrice, flight.seatPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, route, airplane, departureTime, reservedSeats, seatPrice);
    }

    public Integer getId() {
        return id;
    }

    public Flight setId(Integer id) {
        this.id = id;
        return this;
    }

    public Route getRoute() {
        return route;
    }

    public Flight setRoute(Route route) {
        this.route = route;
        return this;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public Flight setAirplane(Airplane airplane) {
        this.airplane = airplane;
        return this;
    }

    public ZonedDateTime getDepartureTime() {
        return departureTime;
    }

    public Flight setDepartureTime(ZonedDateTime departureTime) {
        this.departureTime = departureTime;
        return this;
    }

    public Integer getReservedSeats() {
        return reservedSeats;
    }

    public Flight setReservedSeats(Integer reservedSeats) {
        this.reservedSeats = reservedSeats;
        return this;
    }

    public Float getSeatPrice() {
        return seatPrice;
    }

    public Flight setSeatPrice(Float seatPrice) {
        this.seatPrice = seatPrice;
        return this;
    }
}
