package com.ss.utopia.entity;

import java.util.Objects;

public class BookingGuest {
    private Booking booking;
    private String contactEmail;
    private String contactPhone;

    public boolean validate() {
        return booking != null && contactEmail != null && contactPhone != null;
    }

    public BookingGuest() {
    }

    public BookingGuest(Booking booking, String contactEmail, String contactPhone) {
        this.booking = booking;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingGuest that = (BookingGuest) o;
        return Objects.equals(booking, that.booking) && Objects.equals(contactEmail, that.contactEmail) && Objects.equals(contactPhone, that.contactPhone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(booking, contactEmail, contactPhone);
    }


    public Booking getBooking() {
        return booking;
    }

    public BookingGuest setBooking(Booking booking) {
        this.booking = booking;
        return this;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public BookingGuest setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
        return this;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public BookingGuest setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
        return this;
    }
}
