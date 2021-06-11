package com.ss.utopia.entity;

import java.util.Objects;

public class User {
    private Integer id;
    private UserRole role;
    private String givenName;
    private String familyName;
    private String username;
    private String email;
    private String password; //hopefully this is hashed
    private String phone;

    public boolean validate() {
        return id != null
                && role != null
                && givenName != null
                && familyName != null
                && username != null
                && email != null
                && password != null
                && phone != null;
    }

    public User() {
    }

    public User(User other) {
        this.id = other.id;
        this.role = other.role;
        this.givenName = other.givenName;
        this.familyName = other.familyName;
        this.username = other.username;
        this.email = other.email;
        this.password = other.password;
        this.phone = other.phone;
    }

    public User(Integer id, UserRole role, String givenName, String familyName, String username, String email, String password, String phone) {
        this.id = id;
        this.role = role;
        this.givenName = givenName;
        this.familyName = familyName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(role, user.role) && Objects.equals(givenName, user.givenName) && Objects.equals(familyName, user.familyName) && Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(phone, user.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, givenName, familyName, username, email, password, phone);
    }

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public UserRole getRole() {
        return role;
    }

    public User setRole(UserRole role) {
        this.role = role;
        return this;
    }

    public String getGivenName() {
        return givenName;
    }

    public User setGivenName(String givenName) {
        this.givenName = givenName;
        return this;
    }

    public String getFamilyName() {
        return familyName;
    }

    public User setFamilyName(String familyName) {
        this.familyName = familyName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }
}
