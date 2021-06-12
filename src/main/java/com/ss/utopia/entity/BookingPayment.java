package com.ss.utopia.entity;

import java.util.Objects;

public class BookingPayment {
    private Booking booking;
    private String stripeId;
    private Boolean refunded;

    public boolean validate() {
        return booking != null && stripeId != null && refunded != null;
    }

    public BookingPayment() {
    }

    public BookingPayment(BookingPayment other) {
        this.booking = other.booking;
        this.stripeId = other.stripeId;
        this.refunded = other.refunded;
    }

    public BookingPayment(Booking booking, String stripeId, Boolean refunded) {
        this.booking = booking;
        this.stripeId = stripeId;
        this.refunded = refunded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingPayment that = (BookingPayment) o;
        return Objects.equals(booking, that.booking) && Objects.equals(stripeId, that.stripeId) && Objects.equals(refunded, that.refunded);
    }

    @Override
    public int hashCode() {
        return Objects.hash(booking, stripeId, refunded);
    }

    public Booking getBooking() {
        return booking;
    }

    public BookingPayment setBooking(Booking booking) {
        this.booking = booking;
        return this;
    }

    public Boolean getRefunded() {
        return refunded;
    }

    public String getStripeId() {
        return stripeId;
    }

    public BookingPayment setStripeId(String stripeId) {
        this.stripeId = stripeId;
        return this;
    }

    public Boolean isRefunded() {
        return refunded;
    }

    public BookingPayment setRefunded(Boolean refunded) {
        this.refunded = refunded;
        return this;
    }

    public BookingPayment setRefunded(Integer refunded) {
        this.refunded = refunded == 1;
        return this;
    }

    public Integer getRefundedInt(){
        return refunded ? 1 : 0;
    }
}
