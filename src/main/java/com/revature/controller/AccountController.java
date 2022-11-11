package com.revature.controller;

import com.revature.dto.InitialBankAccountInformation;
import com.revature.dto.Message;
import com.revature.model.Account;
import com.revature.model.User;
import com.revature.service.AccountService;
import io.javalin.Javalin;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class AccountController {

    private AccountService accountService = new AccountService();

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
                   // JSON -> InitialBankAccountInformation object
                   InitialBankAccountInformation info = ctx.bodyAsClass(InitialBankAccountInformation.class);

                   Account createdAccount = accountService.addAccount(info.getInitialBalance(), user.getId());

                   ctx.status(201); // created
                   ctx.json(createdAccount);
               }
           }

        });

        app.get("/accounts", (ctx) -> {
            // PSEUDOCODE
            // Get User object for person that is logged in
            HttpSession httpSession = ctx.req().getSession();
            User user = (User) httpSession.getAttribute("user_info");

            // CHeck if it is null, and if it is null, then tell them they are not logged in
            if (user == null) {
                ctx.json(new Message("Not logged in!"));
                ctx.status(401);
            } else {
                // Otherwise, check if they are a customer
                if (user.getRole().equals("customer")) {
                    // if they are a customer, then get all accounts that belong to that customer
                    List<Account> accounts = accountService.getAccountsByUserId(user.getId());
                    ctx.json(accounts);

                } else if (user.getRole().equals("admin")) { // if they're not a customer, check if they are an admin
                    // if they are an admin, get all accounts in the entire bank system
                    List<Account> accounts = accountService.getAllAccounts();
                    ctx.json(accounts);

                } else {
                    // if they're not a customer or an admin, tell them they are not authorized to view accounts
                    ctx.json(new Message("Not a customer or admin!"));
                    ctx.status(401);
                }
            }
        });
    }

}
