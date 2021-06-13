package com.ss.utopia.dao;

import com.ss.utopia.entity.User;
import com.ss.utopia.entity.UserRole;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * This table only needs read methods
 */
public class UserRoleDAO extends BaseDAO<UserRole> {

    /**
     * Takes sql connection object, pass connection without auto commits for transactions
     *
     * @param conn SQL connection
     */
    public UserRoleDAO(Connection conn) {
        super(conn);
    }

    /**
     * Read UserRole from id
     * @param id id to read
     * @return UserRole
     * @throws SQLException invalid data or server failure
     * @see com.ss.utopia.entity.UserRole
     */
    public UserRole readFromId(Integer id) throws SQLException {
        List<UserRole> userRoles = read("SELECT * FROM user_role WHERE id = ?", new Object[]{id});
        if (userRoles.size() == 0) return null;
        return userRoles.get(0);
    }

    /**
     * Utility method for easier reading
     * @param user user to get role from
     * @return UserRole
     * @throws SQLException invalid data or server failure
     */
    public UserRole readFromUser(User user) throws SQLException {
        return readFromId(user.getRole().getId());
    }

    /**
     *  Read all UserRoles
     * @return List of UserRole
     * @throws SQLException invalid data or server failure
     */
    public List<UserRole> readAll() throws SQLException {
        return read("SELECT * FROM user_role", null);
    }

    @Override
    protected List<UserRole> extractData(ResultSet rs) throws SQLException {
        List<UserRole> userRoles = new ArrayList<>();
        while (rs.next()) {
            UserRole temp = new UserRole();
            temp.setId(rs.getInt("id"));
            temp.setName(rs.getString("name"));
            userRoles.add(temp);
        }
        return userRoles;
    }
}
