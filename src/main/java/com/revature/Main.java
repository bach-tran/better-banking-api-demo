package com.revature;

import com.revature.controller.AuthenticationController;
import io.javalin.Javalin;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws IOException {
        Javalin app = Javalin.create();

        AuthenticationController authController = new AuthenticationController();
        authController.mapEndpoints(app);

        app.start();
    }

}
