package com.revature.controller;

import com.revature.dto.Message;
import com.revature.model.User;
import io.javalin.Javalin;
import jakarta.servlet.http.HttpSession;

public class AccountController {

    public void mapEndpoints(Javalin app) {
        // Adding bank accounts should only be for Users with the customer role
        app.post("/accounts", (ctx) -> {
           HttpSession httpSession = ctx.req().getSession();
           User user = (User) httpSession.getAttribute("user_info");

           if (user == null) {
               ctx.json(new Message("Not logged in!"));
               ctx.status(401); // Unauthorized
           } else {
               if (!user.getRole().equals("customer")) {
                   ctx.json(new Message("Not a customer!"));
                   ctx.status(401); // Unauthorized
               } else { // A user that is logged in w/ the role of customer

               }
           }

        });
    }

}
