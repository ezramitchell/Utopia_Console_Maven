package com.ss.utopia.dao;

import com.ss.utopia.entity.Flight;
import com.ss.utopia.entity.User;
import com.ss.utopia.entity.UserRole;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
     * @throws SQLException invalid data or server failure
     */
    public void updateUser(User user) throws SQLException {
        if (!user.validate()) throw new InvalidParameterException("Missing parameters");
        save("UPDATE user SET role_id = ?, given_name = ?, family_name = ?, username = ?, email = ?, password = ?, phone = ? WHERE id = ?",
                new Object[]{user.getRole().getId(),
                        user.getGivenName(),
                        user.getFamilyName(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getPhone(),
                        user.getId()});
    }

    /**
     * Find User specified by id
     *
     * @param id id to find
     * @return User
     * @throws SQLException invalid data or server failure
     * @see com.ss.utopia.entity.User
     */
    public User readUserById(Integer id) throws SQLException {
        List<User> users = read("SELECT * FROM user WHERE id = ?", new Object[]{id});
        if (users.size() == 0) return null;
        return users.get(0);
    }

    /**
     * For use in admin methods, adminExecutor responsible for correct column names
     * @param params column value pairs
     * @return list of flights
     * @throws SQLException invalid data or server failure
     */
    public List<User> search(LinkedHashMap<String, String> params) throws SQLException {
        return read(
                DAOUtil.constructSQLSearchString("user", params.keySet().toArray(new String[0])),
                params.values().toArray(new Object[0]));
    }

    /**
     * For checking valid column names
     * @return list of columns
     */
    public String[] getColumnNames() throws SQLException {
        return getColumnNames("user");
    }

    /**
     * Get all users of role_id
     *
     * @param typeId role_id to search
     * @return List of User
     * @throws SQLException invalid data or server failure
     * @see User
     */
    public List<User> getUsersByType(Integer typeId) throws SQLException {
        return read("SELECT * FROM user WHERE role_id = ?", new Object[]{typeId});
    }

    /**
     * Get user of username
     * @param username to search
     * @param password to search
     * @return null or User
     * @throws SQLException invalid data or server failure
     */
    public User searchUsername(String username, String password) throws SQLException {
        List<User> users = read("SELECT * FROM user WHERE username = ? AND password = ?", new Object[]{username, password});
        if (users.size() == 0) return null;
        return users.get(0);
    }

    /**
     * Get user of username
     * @param email to search
     * @param password to search
     * @return null or User
     * @throws SQLException invalid data or server failure
     */
    public User searchEmail(String email, String password) throws SQLException {
        List<User> users = read("SELECT * FROM user WHERE email = ? AND password = ?", new Object[]{email, password});
        if (users.size() == 0) return null;
        return users.get(0);
    }



    /**
     * Read all Users
     *
     * @return List of User
     * @throws SQLException invalid data or server failure
     */
    public List<User> readAll() throws SQLException {
        return read("SELECT * FROM user", null);
    }

    /**
     * Delete user at id
     *
     * @param id id to delete
     * @throws SQLException invalid data or server failure
     */
    public void deleteUserByID(Integer id) throws SQLException {
        save("DELETE FROM user WHERE id = ?", new Object[]{id});
    }

    /**
     * Delete user
     *
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
            temp.setEmail(rs.getString("email"));
            temp.setPassword(rs.getString("password"));
            temp.setPhone(rs.getString("phone"));
            users.add(temp);
        }
        return users;
    }
}
