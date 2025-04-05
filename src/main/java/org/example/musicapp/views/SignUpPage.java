package org.example.musicapp.views;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignUpPage {

    private Stage primaryStage;
    private VBox signUpLayout;

    public SignUpPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.signUpLayout = new VBox(15);
        setupSignUpPage();
    }

    public VBox getSignUpLayout() {
        return signUpLayout;
    }

    private void setupSignUpPage() {
        // Fields
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        TextField ageField = new TextField();
        ageField.setPromptText("Age");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        // Role Selection
        ComboBox<String> roleBox = new ComboBox<>();
        roleBox.getItems().addAll("User", "Artist");
        roleBox.setPromptText("Select Role");

        Button signUpButton = new Button("Sign Up");
        signUpButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String ageText = ageField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();
            String role = roleBox.getValue();

            if (username.isEmpty() || ageText.isEmpty() || email.isEmpty() || password.isEmpty() || role == null) {
                showAlert(Alert.AlertType.ERROR, "Please fill all fields.");
                return;
            }

            int age;
            try {
                age = Integer.parseInt(ageText);
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid age.");
                return;
            }

            if (userExists(username)) {
                showAlert(Alert.AlertType.ERROR, "Username already taken.");
                return;
            }

            String hashedPassword = hashPassword(password);

            // Save to file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
                String userLine = username + "," + age + "," + email + "," + hashedPassword + "," + role;
                writer.write(userLine);
                writer.newLine();
                showAlert(Alert.AlertType.INFORMATION, "Sign-up successful!");
                switchToLoginPage();
            } catch (IOException ex) {
                ex.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Failed to save user.");
            }
        });

        // Back to login
        Button backButton = new Button("Back to Login");
        backButton.setOnAction(e -> switchToLoginPage());

        signUpLayout.getChildren().addAll(usernameField, ageField, emailField, passwordField, roleBox, signUpButton, backButton);
        signUpLayout.setAlignment(Pos.CENTER);
        signUpLayout.setStyle("-fx-background-color: #ecf0f1;");
    }

    private boolean userExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.trim().split(",");
                if (userData.length > 0 && userData[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            // If file doesn't exist, assume user doesn't exist yet
        }
        return false;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void switchToLoginPage() {
        LoginPage loginPage = new LoginPage(primaryStage);
        Scene loginScene = new Scene(loginPage.getLoginLayout(), 400, 300);
        primaryStage.setScene(loginScene);
    }
}
