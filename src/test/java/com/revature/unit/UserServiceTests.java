package com.revature.unit;

import com.revature.dao.UserDAO;
import com.revature.exception.LoginException;
import com.revature.model.User;
import com.revature.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Provides Jupiter with additional functionalities coming with Mockito
public class UserServiceTests {

    @Mock // Create a mock object
    UserDAO mockUd;

    @InjectMocks // When we run tests, it will automatically go and instantiate a UserService object AND inject any mock dependency
    UserService us;

    @Test
    public void loginPositiveTest() throws SQLException, IOException {
        // Pretending here that there is a user in the database w/ id 100, username user123, password password, and role customer
        when(mockUd.findUserByUsernameAndPassword(eq("user123"), eq("password")))
                .thenReturn(new User(100, "user123", "password", "customer"));

        User actual = us.login("user123", "password");
        User expected = new User(100, "user123", "password", "customer");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void loginNegativeTestInvalidCredentials() throws SQLException, IOException {
        // Pretending that there's no users in the database
        when(mockUd.findUserByUsernameAndPassword(any(), any()))
                .thenReturn(null);

        Assertions.assertThrows(LoginException.class, () -> {
            us.login("abc123", "12345");
        });
    }

}
