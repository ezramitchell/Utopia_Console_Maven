package com.ss.utopia.entity;

import java.util.Objects;

public class BookingPayment {
    private Integer bookingId;
    private String stripeId;
    private Boolean refunded;

    public boolean validate() {
        return bookingId != null && stripeId != null && refunded != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingPayment that = (BookingPayment) o;
        return Objects.equals(bookingId, that.bookingId) && Objects.equals(stripeId, that.stripeId) && Objects.equals(refunded, that.refunded);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId, stripeId, refunded);
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public BookingPayment setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
        return this;
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
}
