package com.ss.utopia.entity;

import java.util.Objects;

public class FlightBooking {
    private Flight flight;
    private Booking booking;

    public boolean validate(){
        return flight != null && booking != null;
    }

    public FlightBooking() {
    }

    public FlightBooking(Flight flight, Booking booking) {
        this.flight = flight;
        this.booking = booking;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightBooking that = (FlightBooking) o;
        return Objects.equals(flight, that.flight) && Objects.equals(booking, that.booking);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flight, booking);
    }

    public Flight getFlight() {
        return flight;
    }

    public FlightBooking setFlight(Flight flight) {
        this.flight = flight;
        return this;
    }

    public Booking getBooking() {
        return booking;
    }

    public FlightBooking setBooking(Booking booking) {
        this.booking = booking;
        return this;
    }
}
