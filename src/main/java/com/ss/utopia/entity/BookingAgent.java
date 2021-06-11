package com.ss.utopia.entity;

import java.util.Objects;

public class BookingAgent {
    private Booking booking;
    private User agentId;

    public boolean validate(){
        return booking != null && agentId != null;
    }

    public BookingAgent() {
    }

    public BookingAgent(Booking booking, User agentId) {
        this.booking = booking;
        this.agentId = agentId;
    }

    public Booking getBooking() {
        return booking;
    }

    public BookingAgent setBooking(Booking booking) {
        this.booking = booking;
        return this;
    }

    public User getAgentId() {
        return agentId;
    }

    public BookingAgent setAgentId(User agentId) {
        this.agentId = agentId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingAgent that = (BookingAgent) o;
        return Objects.equals(booking, that.booking) && Objects.equals(agentId, that.agentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(booking, agentId);
    }
}
