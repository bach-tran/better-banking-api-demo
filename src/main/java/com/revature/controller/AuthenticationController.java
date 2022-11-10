package com.revature.controller;

import com.revature.dto.LoginCredentials;
import com.revature.dto.Message;
import com.revature.exception.LoginException;
import com.revature.model.User;
import com.revature.service.UserService;
import io.javalin.Javalin;
import jakarta.servlet.http.HttpSession;

public class AuthenticationController {

    private UserService userService = new UserService();
    public void mapEndpoints(Javalin app) {
        app.post("/login", (ctx) -> {
            LoginCredentials credentials = ctx.bodyAsClass(LoginCredentials.class);

            if (credentials.getUsername() == null || credentials.getPassword() == null) {
                ctx.result("username and/or password was not provided!");
                ctx.status(400);
            } else {

                try {
                    User user = userService.login(credentials.getUsername(), credentials.getPassword());

                    // Create an HttpSession and associate the user object with that session
                    // HttpSessions are used to track which client is sending a particular request
                    HttpSession httpSession = ctx.req().getSession();
                    httpSession.setAttribute("user_info", user);

                    ctx.json(user);
                } catch (LoginException e) {
                    ctx.status(400);
                    ctx.json(new Message(e.getMessage()));
                }

            }
        });

        app.get("/current-user", (ctx) -> {
           HttpSession httpSession = ctx.req().getSession();
           User loggedInUser = (User) httpSession.getAttribute("user_info");

           if (loggedInUser == null) {
               ctx.json(new Message("User is not logged in!"));
           } else {
               ctx.json(loggedInUser); // Object -> JSON -> Response body
           }
        });
    }

}
