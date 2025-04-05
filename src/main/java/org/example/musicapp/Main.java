package org.example.musicapp;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.musicapp.views.LoginPage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        LoginPage loginPage = new LoginPage(primaryStage);
        Scene loginScene = new Scene(loginPage.getLoginLayout(), 400, 300);

        primaryStage.setTitle("Geniuspp");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
