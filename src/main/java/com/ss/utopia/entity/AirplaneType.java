package com.ss.utopia.entity;

import java.util.Objects;

public class AirplaneType {
    private Integer id;
    private Integer maxCapacity;

    public boolean validate(){
        return id != null && maxCapacity != null;
    }

    public AirplaneType() {
    }

    public AirplaneType(AirplaneType other) {
        this.id = other.id;
        this.maxCapacity = other.maxCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirplaneType that = (AirplaneType) o;
        return Objects.equals(id, that.id) && Objects.equals(maxCapacity, that.maxCapacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, maxCapacity);
    }

    public Integer getId() {
        return id;
    }

    public AirplaneType setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public AirplaneType setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
        return this;
    }
}
