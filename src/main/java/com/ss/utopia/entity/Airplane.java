package com.ss.utopia.entity;

import java.util.Objects;

public class Airplane {
    private Integer id;
    private AirplaneType airplaneType;


    public boolean validate(){
        return id != null && airplaneType != null;
    }

    public Airplane() {
    }

    public Airplane(Airplane other) {
        this.id = other.id;
        this.airplaneType = other.airplaneType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airplane airplane = (Airplane) o;
        return Objects.equals(id, airplane.id) && Objects.equals(airplaneType, airplane.airplaneType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, airplaneType);
    }

    public Integer getId() {
        return id;
    }

    public Airplane setId(Integer id) {
        this.id = id;
        return this;
    }

    public AirplaneType getAirplaneType() {
        return airplaneType;
    }

    public void setAirplaneType(AirplaneType airplaneType) {
        this.airplaneType = airplaneType;
    }
}
