package com.revature.service;

import com.revature.dao.UserDAO;
import com.revature.exception.LoginException;
import com.revature.model.User;

import java.sql.SQLException;

public class UserService {

    private UserDAO userDao = new UserDAO();
    public User login(String username, String password) throws SQLException {
        User user = userDao.findUserByUsernameAndPassword(username, password);

        if (user == null) {
            throw new LoginException("Invalid login");
        } else {
            return user;
        }
    }

}
