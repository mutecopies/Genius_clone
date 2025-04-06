package org.example.musicapp.views;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import org.example.musicapp.models.User;
import org.example.musicapp.models.Artist;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginPage {

    private Stage primaryStage;
    private VBox loginLayout;

    public LoginPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.loginLayout = new VBox(15);
        setupLoginPage();
    }

    public VBox getLoginLayout() {
        return loginLayout;
    }

    private void setupLoginPage() {
        // Username and Password fields
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");

        // Login Button
        Button loginButton = new Button("Log In");
        loginButton.setStyle("-fx-font-size: 16px; -fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 10px;");
        loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText()));

        // Sign-Up Button
        Button signUpButton = new Button("Sign Up");
        signUpButton.setOnAction(e -> switchToSignUpPage());

        loginLayout.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginButton, signUpButton);
        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.setStyle("-fx-background-color: #ecf0f1;");
    }

    private void handleLogin(String username, String password) {
        String enteredHashedPassword = hashPassword(password);
        String[] userData = getUserData(username);

        if (userData != null) {
            String storedHashedPassword = userData[3];

            if (storedHashedPassword.equals(enteredHashedPassword)) {
                System.out.println("Logged in as: " + username);
                String name = userData[0];
                int age = Integer.parseInt(userData[1]);
                String email = userData[2];
                String role = userData[4];

                if ("User".equalsIgnoreCase(role)) {
                    // Create a regular user
                    User currentUser = new User(name, age, email, storedHashedPassword, role);

                    // Redirect to HomePage after successful login
                    HomePage homePage = new HomePage(primaryStage, currentUser);
                    primaryStage.setScene(homePage.getScene());
                    primaryStage.show();

                } else if ("Artist".equalsIgnoreCase(role)) {
                    // Create an artist
                    Artist currentArtist = new Artist(name, age, email, storedHashedPassword, role);

                    // Redirect to ArtistPage after successful login
                    ArtistPage artistPage = new ArtistPage(primaryStage, currentArtist);
                    primaryStage.setScene(artistPage.getScene());
                    primaryStage.show();
                }
            } else {
                showAlert("Invalid username or password.");
            }
        } else {
            showAlert("User not found.");
        }
    }

    private void switchToSignUpPage() {
        SignUpPage signUpPage = new SignUpPage(primaryStage);
        Scene signUpScene = new Scene(signUpPage.getSignUpLayout(), 400, 300);
        primaryStage.setScene(signUpScene);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String[] getUserData(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length == 5 && userData[0].equals(username)) {
                    return userData; // [name, age, email, hashedPassword, role]
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
