package com.ss.utopia.dao;

import com.ss.utopia.db.ConnectionUtil;
import com.ss.utopia.entity.UserRole;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRoleDAOTest {

    @Test
    void readFromId() {
        try (Connection c = ConnectionUtil.getConnection()) {
            UserRoleDAO dao = new UserRoleDAO(c);

            UserRole userRole = dao.readFromId(1);
            assertEquals("Administrator", userRole.getName());

            userRole = dao.readFromId(2);
            assertEquals("Agent", userRole.getName());

            userRole = dao.readFromId(3);
            assertEquals("Traveler", userRole.getName());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            fail();
        }
    }

    @Test
    void readAll() {
        try (Connection c = ConnectionUtil.getConnection()) {
            UserRoleDAO dao = new UserRoleDAO(c);
            List<UserRole> userRoleList = dao.readAll();

            assertEquals(3, userRoleList.size()); //correct size

            for (UserRole role : userRoleList) {
                assertTrue(role.validate()); //populated data
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            fail();
        }
    }
}