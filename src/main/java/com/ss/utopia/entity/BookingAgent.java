package com.ss.utopia.entity;

import java.util.Objects;

public class BookingAgent {
    private Booking booking;
    private User agent;

    public boolean validate() {
        return booking != null && agent != null;
    }

    public BookingAgent() {
    }

    public BookingAgent(BookingAgent other) {
        this.booking = other.booking;
        this.agent = other.agent;
    }

    public BookingAgent(Booking booking, User agentId) {
        this.booking = booking;
        this.agent = agentId;
    }

    public Booking getBooking() {
        return booking;
    }

    public BookingAgent setBooking(Booking booking) {
        this.booking = booking;
        return this;
    }

    public User getAgent() {
        return agent;
    }

    public BookingAgent setAgent(User agent) {
        this.agent = agent;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingAgent that = (BookingAgent) o;
        return Objects.equals(booking, that.booking) && Objects.equals(agent, that.agent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(booking, agent);
    }
}
