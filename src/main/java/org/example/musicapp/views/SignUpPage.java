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
    private Scene signUpScene;

    public SignUpPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.signUpLayout = new VBox(15);
        setupSignUpPage();
        this.signUpScene = new Scene(signUpLayout, 400, 350);
    }

    public Scene getScene() {
        return signUpScene;
    }

    private void setupSignUpPage() {
        Label titleLabel = new Label("Sign Up");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #2c3e50;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        TextField ageField = new TextField();
        ageField.setPromptText("Age");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        ComboBox<String> roleBox = new ComboBox<>();
        roleBox.getItems().addAll("User", "Artist", "Admin");
        roleBox.setPromptText("Select Role");
        roleBox.getSelectionModel().selectFirst(); // Default to "User" to avoid null

        Button signUpButton = new Button("Sign Up");
        signUpButton.setStyle("-fx-font-size: 14px; -fx-background-color: #2ecc71; -fx-text-fill: white;");
        signUpButton.setOnAction(e -> handleSignUp(
                usernameField.getText().trim(),
                ageField.getText().trim(),
                emailField.getText().trim(),
                passwordField.getText(),
                roleBox.getValue()
        ));

        Button backButton = new Button("Back to Login");
        backButton.setStyle("-fx-font-size: 12px; -fx-background-color: #bdc3c7;");
        backButton.setOnAction(e -> switchToLoginPage());

        signUpLayout.getChildren().addAll(
                titleLabel,
                usernameField, ageField, emailField, passwordField, roleBox,
                signUpButton, backButton
        );
        signUpLayout.setAlignment(Pos.CENTER);
        signUpLayout.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 30px;");
    }

    private void handleSignUp(String username, String ageText, String email, String password, String role) {
        if (username.isEmpty() || ageText.isEmpty() || email.isEmpty() || password.isEmpty() || role == null || role.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please fill all the fields and select a role.");
            return;
        }

        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
            showAlert(Alert.AlertType.ERROR, "Invalid email format.");
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageText);
            if (age <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Please enter a valid age.");
            return;
        }

        if (userExists(username)) {
            showAlert(Alert.AlertType.ERROR, "Username already taken.");
            return;
        }

        // Restrict admin sign-up to a specific email
        if ("Admin".equals(role) && !email.equalsIgnoreCase("admin@example.com")) {
            showAlert(Alert.AlertType.ERROR, "Only the email 'admin@example.com' is allowed to register as Admin.");
            return;
        }

        String hashedPassword = hashPassword(password);
        if (hashedPassword == null) {
            showAlert(Alert.AlertType.ERROR, "Failed to hash password.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
            String userData = username + "," + age + "," + email + "," + hashedPassword + "," + role;
            writer.write(userData);
            writer.newLine();
            showAlert(Alert.AlertType.INFORMATION, "Sign-up successful! You can now log in.");
            switchToLoginPage();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Failed to save user.");
        }
    }

    private boolean userExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.trim().split(",");
                if (userData.length >= 1 && userData[0].equalsIgnoreCase(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            // File might not exist yet, which is fine
        }
        return false;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes());
            StringBuilder hex = new StringBuilder();
            for (byte b : hashedBytes) {
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
        alert.setTitle(type == Alert.AlertType.INFORMATION ? "Success" : "Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void switchToLoginPage() {
        LoginPage loginPage = new LoginPage(primaryStage);
        primaryStage.setScene(loginPage.getScene());
    }
}
