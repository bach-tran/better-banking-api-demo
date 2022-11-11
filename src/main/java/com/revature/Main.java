package com.revature;

import com.revature.controller.AccountController;
import com.revature.controller.AuthenticationController;
import io.javalin.Javalin;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws IOException {
        Javalin app = Javalin.create((config) -> {
            config.plugins.enableCors((cors) -> {
                cors.add(it -> {
                    it.defaultScheme = "http";
                    it.allowHost("127.0.0.1:5500");
                    it.allowHost("localhost:5500");
                    it.allowCredentials = true;
                });
            });
        });

        AuthenticationController authController = new AuthenticationController();
        authController.mapEndpoints(app);

        AccountController accountController = new AccountController();
        accountController.mapEndpoints(app);

        app.start();
    }

}
