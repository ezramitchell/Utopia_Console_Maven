package com.ss.utopia.dao;

import com.ss.utopia.entity.User;
import com.ss.utopia.entity.UserRole;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This table only needs read methods
 */
public class UserRoleDAO extends BaseDAO<UserRole> {


    public UserRoleDAO(Connection conn) {
        super(conn);
    }

    public UserRole readFromId(Integer id) throws SQLException {
        List<UserRole> userRoles = read("SELECT * FROM user_role WHERE id = ?", new Object[]{id});
        if (userRoles.size() == 0) return null;
        return userRoles.get(0);
    }

    public UserRole readFromUser(User user) throws SQLException {
        return readFromId(user.getId());
    }

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
