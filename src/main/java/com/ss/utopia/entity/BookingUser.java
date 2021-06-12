package com.ss.utopia.entity;

import java.util.Objects;

public class BookingUser {
    private Booking booking;
    private User user;

    public boolean validate() {
        return user != null && booking != null;
    }

    public BookingUser() {
    }

    public BookingUser(BookingUser other) {
        this.booking = other.booking;
        this.user = other.user;
    }

    public BookingUser(Booking booking, User user) {
        this.booking = booking;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingUser that = (BookingUser) o;
        return Objects.equals(booking, that.booking) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(booking, user);
    }

    public Booking getBooking() {
        return booking;
    }

    public BookingUser setBooking(Booking booking) {
        this.booking = booking;
        return this;
    }

    public User getUser() {
        return user;
    }

    public BookingUser setUser(User user) {
        this.user = user;
        return this;
    }
}
