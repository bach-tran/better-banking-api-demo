package com.revature;

import com.revature.controller.AuthenticationController;
import io.javalin.Javalin;

public class Main {

    public static void main(String[] args) {
        Javalin app = Javalin.create();

        AuthenticationController authController = new AuthenticationController();
        authController.mapEndpoints(app);

        app.start();
    }

}
