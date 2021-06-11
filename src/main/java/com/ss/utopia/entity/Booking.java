package com.ss.utopia.entity;

import java.util.Objects;

public class Booking {
    private Integer id;
    private Boolean isActive;
    private String confirmationCode;

    public boolean validate(){
        return id != null && isActive != null && confirmationCode != null;
    }

    public Booking() {
    }

    /**
     * Copy constructor
     * @param other value to copy
     */
    public Booking(Booking other) {
        this.id = other.id;
        this.isActive = other.isActive;
        this.confirmationCode = other.confirmationCode;
    }

    /**
     * This constructor is for constructing entity from user data
     * @param id unnecessary for inserts, specify for updates
     * @param isActive Integer in database, converted to boolean for ease in entity
     * @param confirmationCode code sent to user
     */
    public Booking(Integer id, boolean isActive, String confirmationCode) {
        this.id = id;
        this.isActive = isActive;
        this.confirmationCode = confirmationCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id) && Objects.equals(isActive, booking.isActive) && Objects.equals(confirmationCode, booking.confirmationCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isActive, confirmationCode);
    }

    public Integer getId() {
        return id;
    }

    public Booking setId(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * Don't use this method for database
     * @return active state, nullable
     */
    public Boolean isActive() {
        return isActive;
    }

    /**
     * Use this method for database
     * @return active in form of int
     */
    public Integer getActiveNum(){return isActive ? 1 : 0;}

    public Booking setActive(Boolean active) {
        isActive = active;
        return this;
    }

    public Booking setActive(Integer active){
        isActive = active == 1;
        return this;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public Booking setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
        return this;
    }
}
