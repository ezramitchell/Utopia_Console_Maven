package com.ss.utopia.entity;

import java.util.Objects;

public class Route {
    private Integer id;
    private Airport originAirport;
    private Airport destinationAirport;

    public boolean validate() {
        return id != null && originAirport != null && destinationAirport != null;
    }

    public Route() {
    }

    public Route(Route other) {
        this.id = other.id;
        this.originAirport = other.originAirport;
        this.destinationAirport = other.destinationAirport;
    }

    public Route(Integer id, Airport originAirport, Airport destinationAirport) {
        this.id = id;
        this.originAirport = originAirport;
        this.destinationAirport = destinationAirport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Objects.equals(id, route.id) && Objects.equals(originAirport, route.originAirport) && Objects.equals(destinationAirport, route.destinationAirport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, originAirport, destinationAirport);
    }

    public Integer getId() {
        return id;
    }

    public Route setId(Integer id) {
        this.id = id;
        return this;
    }

    public Airport getOriginAirport() {
        return originAirport;
    }

    public Route setOriginAirport(Airport originAirport) {
        this.originAirport = originAirport;
        return this;
    }

    public Airport getDestinationAirport() {
        return destinationAirport;
    }

    public Route setDestinationAirport(Airport destinationAirport) {
        this.destinationAirport = destinationAirport;
        return this;
    }
}
