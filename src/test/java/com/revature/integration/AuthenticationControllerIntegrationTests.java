package com.revature.integration;

import com.revature.controller.AccountController;
import com.revature.controller.AuthenticationController;
import com.revature.dao.ConnectionUtility;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthenticationControllerIntegrationTests {

    public Connection con;
    public Javalin app;

    @BeforeEach
    public void setup() throws SQLException, IOException {
        con = ConnectionUtility.getConnection();
        ConnectionUtility.populateH2Database(con);

        // Start up server (for every single test)
        app = Javalin.create();
        AuthenticationController authController = new AuthenticationController();
        authController.mapEndpoints(app); // map endpoints in AuthenticationController to the test Javalin object
        AccountController accountController = new AccountController();
        accountController.mapEndpoints(app);
    }

    @AfterEach
    public void clearDb() throws SQLException, IOException {
        ConnectionUtility.clearH2Database(con);
        con.close();
    }

    // Whitebox Integration test (API Test)
    @Test
    public void loginAPITestPositive() {
        JavalinTest.test(app, (server, client) -> {
            Map<String, Object> requestJson = new HashMap<>();
            requestJson.put("username", "admin123");
            requestJson.put("password", "password123");

            Response response = client.post("/login", requestJson);
            int actualResponseStatusCode = response.code();
            String responseBodyJson = response.body().string();

            assertThat(actualResponseStatusCode).isEqualTo(200);
            assertThat(responseBodyJson).isEqualTo("{\"id\":1,\"username\":\"admin123\",\"password\":\"password123\",\"role\":\"admin\"}");
        });
    }

    @Test
    public void loginAPITestInvalidCredentials() {
        JavalinTest.test(app, (server, client) -> {
            Map<String, Object> requestJson = new HashMap<>();
            requestJson.put("username", "invalidusername");
            requestJson.put("password", "invalidpassword");

            Response response = client.post("/login", requestJson);
            int actualResponseStatusCode = response.code();
            String responseBodyJson = response.body().string();

            assertThat(actualResponseStatusCode).isEqualTo(400); // 400 Bad Request
            assertThat(responseBodyJson).isEqualTo("{\"message\":\"Invalid login\"}");
        });
    }

    @Test
    public void loginAPITestUsernameIsNull() {
        JavalinTest.test(app, (server, client) -> {
            Map<String, Object> requestJson = new HashMap<>();
            // leave username out of request
            requestJson.put("password", "invalidpassword");

            Response response = client.post("/login", requestJson);
            int actualResponseStatusCode = response.code();
            String responseBodyJson = response.body().string();

            assertThat(actualResponseStatusCode).isEqualTo(400);
            assertThat(responseBodyJson).isEqualTo("{\"message\":\"username and/or password was not provided!\"}");
        });
    }

    @Test
    public void loginAPITestPasswordIsNull() {
        JavalinTest.test(app, (server, client) -> {
            Map<String, Object> requestJson = new HashMap<>();
            requestJson.put("username", "invalidusername");
            // leave password out of request

            Response response = client.post("/login", requestJson);
            int actualResponseStatusCode = response.code();
            String responseBodyJson = response.body().string();

            assertThat(actualResponseStatusCode).isEqualTo(400);
            assertThat(responseBodyJson).isEqualTo("{\"message\":\"username and/or password was not provided!\"}");
        });
    }

    @Test
    public void loginAPITestUsernameAndPasswordAreNull() {
        JavalinTest.test(app, (server, client) -> {
            Map<String, Object> requestJson = new HashMap<>();
            // leave username out of request
            // leave password out of request

            Response response = client.post("/login", requestJson);
            int actualResponseStatusCode = response.code();
            String responseBodyJson = response.body().string();

            assertThat(actualResponseStatusCode).isEqualTo(400);
            assertThat(responseBodyJson).isEqualTo("{\"message\":\"username and/or password was not provided!\"}");
        });
    }

    @Test
    public void currentUserAPITestLoggedIn() {
        JavalinTest.test(app, (server, client) -> {

            // POST /login
            Map<String, Object> requestJson = new HashMap<>();
            requestJson.put("username", "admin123");
            requestJson.put("password", "password123");

            Response loginResponse = client.post("/login", requestJson);
            String cookie = loginResponse.header("Set-Cookie").split(";")[0]; // JSESSIONID

            // GET /current-user include the JSESSIONID in the Cookie header
            Response currentUserResponse = client.get("/current-user", (builder) -> {
                builder.addHeader("Cookie", cookie);
            });

            assertThat(currentUserResponse.body().string()).isEqualTo("{\"id\":1,\"username\":\"admin123\",\"password\":\"password123\",\"role\":\"admin\"}");
        });
    }

    @Test
    public void currentUserAPITestNotLoggedIn() {
        JavalinTest.test(app, (server, client) -> {
            // GET /current-user include the JSESSIONID in the Cookie header
            Response currentUserResponse = client.get("/current-user");

            assertThat(currentUserResponse.code()).isEqualTo(401);
            assertThat(currentUserResponse.body().string()).isEqualTo("{\"message\":\"User is not logged in!\"}");
        });
    }

}
