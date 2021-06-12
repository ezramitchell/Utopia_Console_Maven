package com.ss.utopia.dao;

import com.ss.utopia.db.ConnectionUtil;
import com.ss.utopia.entity.User;
import com.ss.utopia.entity.UserRole;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    @Test
    void addUpdateDelete(){
        try(Connection c = ConnectionUtil.getTransaction()){
            try{
                UserDAO dao = new UserDAO(c);

                //add user
                User user = new User(-1,
                        new UserRole().setId(1)/*Administrator*/,
                        "Ezra",
                        "Mitchell",
                        "ezra1",
                        "ezra@mail.com",
                        "password",
                        "5555555555");
                User oldUser = new User(user); //copy for comparison

                user = dao.addUser(user);
                assertNotEquals(user.getId(), oldUser.getId()); //id should be updated

                //update user
                user.setFamilyName("llehctiM");
                user.setUsername("ezra2");
                user.setEmail("ezra@mailmail.com");

                oldUser = new User(user); //copy for comparison


                dao.updateUser(user);
                user = dao.readUserById(user.getId());

                assertEquals(oldUser, user); //no data lost

                //check if update lost data

                //delete user
                dao.deleteUser(user);
                assertNull(dao.readUserById(user.getId()));

            }catch (SQLException throwable){
                throwable.printStackTrace();
                fail();
            }
            c.rollback();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            fail();
        }
    }

    @Test
    void readAll() {
        try (Connection c = ConnectionUtil.getConnection()) {
            List<User> userList = new UserDAO(c).readAll();
            assertTrue(userList.size() > 0);
            for (User user : userList) {
                assertTrue(user.validate());
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            fail();
        }
    }
}