package com.ss.utopia.dao;

import com.ss.utopia.entity.User;
import com.ss.utopia.entity.UserRole;

import javax.management.relation.Role;
import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends BaseDAO<User> {

    /**
     * Takes sql connection object, pass connection without auto commits for transactions
     *
     * @param conn SQL connection
     */
    public UserDAO(Connection conn) {
        super(conn);
    }

    /**
     * Add User to database, given user id is ignored and updated to generated id, fully populated
     *
     * @param user user data (id overwritten)
     * @return User with generated id
     * @throws SQLException invalid data or server failure
     */
    public User addUser(User user) throws SQLException {
        if (!user.validate()) throw new InvalidParameterException("Missing parameters");
        Integer id = save("INSERT INTO user (role_id, given_name, family_name, username, email, password, phone)" +
                        " VALUES (?, ?, ?, ?, ?, ?, ?)",
                new Object[]{user.getRole().getId(),
                        user.getGivenName(),
                        user.getFamilyName(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getPhone()});
        return user.setId(id);
    }

    /**
     * Update user at id
     *
     * @param user data to write, must be populated
     * @param id   alternate id
     * @throws SQLException invalid data or server failure
     */
    public void updateUserAtId(User user, Integer id) throws SQLException {
        if (!user.validate()) throw new InvalidParameterException("Missing parameters");
        save("UPDATE user SET role_id = ?, given_name = ?, family_name = ?, username = ?, email = ?, password = ?, phone = ? WHERE id = ?",
                new Object[]{user.getRole().getId(),
                        user.getGivenName(),
                        user.getFamilyName(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getPhone()});
    }

    /**
     * Update user
     *
     * @param user user to update, must be fully populated
     * @throws SQLException invalid data or server failure
     */
    public void updateUser(User user) throws SQLException {
        updateUserAtId(user, user.getId());
    }

    /**
     * Find User specified by id
     * @param id id to find
     * @return User
     * @see com.ss.utopia.entity.User
     * @throws SQLException invalid data or server failure
     */
    public User readUserById(Integer id) throws SQLException{
        List<User> users = read("SELECT * FROM user WHERE id = ?", new Object[]{id});
        if(users.size() == 0) return null;
        return users.get(0);
    }

    /**
     * Read all Users
     * @return List of User
     * @throws SQLException invalid data or server failure
     */
    public List<User> readAll() throws SQLException {
        return read("SELECT * FROM user", null);
    }

    /**
     * Delete user at id
     * @param id id to delete
     * @throws SQLException invalid data or server failure
     */
    public void deleteUserByID(Integer id) throws SQLException {
        save("DELETE FROM user WHERE id = ?", new Object[]{id});
    }

    /**
     * Delete user
     * @param user user to delete
     * @throws SQLException invalid data or server failure
     */
    public void deleteUser(User user) throws SQLException {
        deleteUserByID(user.getId());
    }


    @Override
    protected List<User> extractData(ResultSet rs) throws SQLException {
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            User temp = new User();
            temp.setId(rs.getInt("id"));
            temp.setRole(new UserRole().setId(rs.getInt("role_id")));
            temp.setGivenName(rs.getString("given_name"));
            temp.setFamilyName(rs.getString("family_name"));
            temp.setUsername(rs.getString("username"));
            temp.setPassword(rs.getString("password"));
            temp.setPhone(rs.getString("phone"));
            users.add(temp);
        }
        return users;
    }
}
