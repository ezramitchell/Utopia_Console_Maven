package com.ss.service.admin;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class AdminExecutorSearchTest {

    @Test
    void searchUsers() {
        //basic search testing
        assertTrue(
                new AdminExecutorSearch().searchUsers(
                        new LinkedHashMap<>()).stream().peek(user -> assertTrue(user.validate())).toList().size() > 0
        );
    }

    @Test
    void searchFlights() {
        assertTrue(
                new AdminExecutorSearch().searchFlights(
                        new LinkedHashMap<>()).stream().peek(user -> assertTrue(user.validate())).toList().size() > 0
        );
    }

    @Test
    void getUserBookings() {
        assertTrue(
                new AdminExecutorSearch().getUserBookings(
                        7).stream().peek(user -> assertTrue(user.validate())).toList().size() > 0
        );
    }

    @Test
    void searchBookings() {
        assertTrue(
                new AdminExecutorSearch().searchBookings(
                        new LinkedHashMap<>()).stream().peek(user -> assertTrue(user.validate())).toList().size() > 0
        );
    }

    @Test
    void searchAirports() {
        assertTrue(
                new AdminExecutorSearch().searchAirports(
                        new LinkedHashMap<>()).stream().peek(user -> assertTrue(user.validate())).toList().size() > 0
        );
    }
}