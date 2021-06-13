package com.ss.service;

import com.ss.utopia.dao.UserDAO;
import com.ss.utopia.dao.UserRoleDAO;
import com.ss.utopia.db.ConnectionUtil;
import com.ss.utopia.entity.User;

import java.sql.Connection;
import java.sql.SQLException;

public class AuthenticationExecutor {

    /**
     * Authenticate with either username or email, to use email username should be null
     *
     * @param username username
     * @param password password
     * @param email    email
     * @return populated User object or null if not authenticated
     */
    public User authenticate(String username, String password, String email) {
        if (password == null) return null;

        try (Connection c = ConnectionUtil.getConnection()) {
            UserDAO userDAO = new UserDAO(c);
            User user = null;
            if (username != null) { //try to authenticate with username
                user = userDAO.searchUsername(username, password);
            } else if (email != null) { //try to authenticate with email
                user = userDAO.searchEmail(email, password);
            }

            if (user != null) {
                //construct user_role object
                UserRoleDAO userRoleDAO = new UserRoleDAO(c);
                return user.setRole(userRoleDAO.readFromUser(user)); //add role to user
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
