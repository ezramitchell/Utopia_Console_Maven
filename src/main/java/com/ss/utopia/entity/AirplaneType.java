package com.ss.utopia.entity;

import java.util.Objects;

public class AirplaneType {
    private Integer id;
    private Integer firstCapacity;
    private Integer economyCapacity;
    private Integer businessCapacity;

    public boolean validate() {
        return id != null && firstCapacity != null;
    }

    public AirplaneType() {
    }

    public AirplaneType(AirplaneType other) {
        this.id = other.id;
        this.firstCapacity = other.firstCapacity;
        this.economyCapacity = other.economyCapacity;
        this.businessCapacity = other.businessCapacity;
    }

    public AirplaneType(Integer id, Integer firstCapacity, Integer economyCapacity, Integer businessCapacity) {
        this.id = id;
        this.firstCapacity = firstCapacity;
        this.economyCapacity = economyCapacity;
        this.businessCapacity = businessCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirplaneType that = (AirplaneType) o;
        return Objects.equals(id, that.id) && Objects.equals(firstCapacity, that.firstCapacity) && Objects.equals(economyCapacity, that.economyCapacity) && Objects.equals(businessCapacity, that.businessCapacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstCapacity, economyCapacity, businessCapacity);
    }

    public Integer getId() {
        return id;
    }

    public AirplaneType setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getFirstCapacity() {
        return firstCapacity;
    }

    public AirplaneType setFirstCapacity(Integer firstCapacity) {
        this.firstCapacity = firstCapacity;
        return this;
    }

    public Integer getEconomyCapacity() {
        return economyCapacity;
    }

    public AirplaneType setEconomyCapacity(Integer economyCapacity) {
        this.economyCapacity = economyCapacity;
        return this;
    }

    public Integer getBusinessCapacity() {
        return businessCapacity;
    }

    public AirplaneType setBusinessCapacity(Integer businessCapacity) {
        this.businessCapacity = businessCapacity;
        return this;
    }
}
