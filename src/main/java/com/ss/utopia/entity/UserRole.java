package com.ss.utopia.entity;

import java.util.Objects;

public class UserRole {
    private Integer id;
    private String name;

    public boolean validate() {
        return id != null && name != null;
    }

    public UserRole() {
    }

    public UserRole(UserRole other) {
        this.id = other.id;
        this.name = other.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return Objects.equals(id, userRole.id) && Objects.equals(name, userRole.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public Integer getId() {
        return id;
    }

    public UserRole setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserRole setName(String name) {
        this.name = name;
        return this;
    }
}
