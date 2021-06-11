package com.ss.utopia.entity;

import java.security.InvalidParameterException;
import java.util.Objects;

public class Airport {
    private String iataId;
    private String city;

    public boolean validate(){
        return iataId != null && city != null;
    }

    public Airport() {
    }

    public Airport(String iataId, String city) {
        setIataId(iataId);
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport = (Airport) o;
        return Objects.equals(iataId, airport.iataId) && Objects.equals(city, airport.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iataId, city);
    }

    public String getIataId() {
        return iataId;
    }

    public Airport setIataId(String iataId) {
        if(iataId.length() != 3) throw new InvalidParameterException("Invalid id");
        this.iataId = iataId;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Airport setCity(String city) {
        this.city = city;
        return this;
    }
}
