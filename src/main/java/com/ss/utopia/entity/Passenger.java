package com.ss.utopia.entity;

import java.util.Date;
import java.util.Objects;

public class Passenger {
    private Integer id;
    private Booking booking;
    private String givenName;
    private String familyName;
    private Date dob;
    private String gender;
    private String address;

    public boolean validate() {
        return id != null
                && booking != null
                && givenName != null
                && familyName != null
                && dob != null
                && gender != null
                && address != null;
    }

    public Passenger() {
    }

    public Passenger(Integer id, Booking booking, String givenName, String familyName, Date dob, String gender, String address) {
        this.id = id;
        this.booking = booking;
        this.givenName = givenName;
        this.familyName = familyName;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return Objects.equals(id, passenger.id) && Objects.equals(booking, passenger.booking) && Objects.equals(givenName, passenger.givenName) && Objects.equals(familyName, passenger.familyName) && Objects.equals(dob, passenger.dob) && Objects.equals(gender, passenger.gender) && Objects.equals(address, passenger.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, booking, givenName, familyName, dob, gender, address);
    }

    public Integer getId() {
        return id;
    }

    public Passenger setId(Integer id) {
        this.id = id;
        return this;
    }

    public Booking getBooking() {
        return booking;
    }

    public Passenger setBooking(Booking booking) {
        this.booking = booking;
        return this;
    }

    public String getGivenName() {
        return givenName;
    }

    public Passenger setGivenName(String givenName) {
        this.givenName = givenName;
        return this;
    }

    public String getFamilyName() {
        return familyName;
    }

    public Passenger setFamilyName(String familyName) {
        this.familyName = familyName;
        return this;
    }

    public Date getDob() {
        return dob;
    }

    public Passenger setDob(Date dob) {
        this.dob = dob;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public Passenger setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Passenger setAddress(String address) {
        this.address = address;
        return this;
    }
}
