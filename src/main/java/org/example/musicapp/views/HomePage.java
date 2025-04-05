package org.example.musicapp.views;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import org.example.musicapp.models.User;

public class HomePage {

    private Stage primaryStage;
    private User user;
    private BorderPane homeLayout;

    public HomePage(Stage primaryStage, User user) {
        this.primaryStage = primaryStage;
        this.user = user;
        this.homeLayout = new BorderPane();
        setupHomePage();
    }

    public Scene getScene() {
        return new Scene(homeLayout, 800, 600);
    }

    private void setupHomePage() {
        // Header - username in the top right
        HBox header = new HBox(20);
        Label welcomeLabel = new Label("Welcome, " + user.getName());
        Button userButton = new Button("Your Profile");
        userButton.setStyle("-fx-font-size: 16px; -fx-background-color: #e74c3c; -fx-text-fill: white; -fx-padding: 10px 20px;");

        // Add debug log to ensure button is clicked
        userButton.setOnAction(e -> {
            System.out.println("Your Profile button clicked!");
            showUserStatus();
        });

        header.getChildren().addAll(welcomeLabel, userButton);
        header.setAlignment(Pos.TOP_RIGHT);
        header.setStyle("-fx-background-color: #f39c12; -fx-padding: 10px;");

        // Body - "Trending Songs" and "Albums" (Mockup)
        VBox body = new VBox(20);
        Label trendingLabel = new Label("Trending Songs");
        trendingLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        body.getChildren().add(trendingLabel);

        // Mockup for trending songs (replace with actual data later)
        for (int i = 1; i <= 5; i++) {
            Label songLabel = new Label("Song " + i);
            body.getChildren().add(songLabel);
        }

        // Set up main layout
        homeLayout.setTop(header);
        homeLayout.setCenter(body);
        homeLayout.setStyle("-fx-background-color: #f1c40f;");
    }

    private void showUserStatus() {
        // Stylish User Profile Popup
        Dialog<Void> profileDialog = new Dialog<>();
        profileDialog.setTitle("Your Profile");
        profileDialog.setHeaderText("Hello, " + user.getName() + "!");

        // Styling the content of the profile card
        VBox profileContent = new VBox(20);
        profileContent.setAlignment(Pos.CENTER);
        profileContent.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 20px; -fx-background-radius: 15px; -fx-border-radius: 15px;");

        // Profile Image (Placeholder)
        ImageView profileImageView = new ImageView(new Image("https://www.w3schools.com/w3images/avatar2.png"));
        profileImageView.setFitWidth(100);
        profileImageView.setFitHeight(100);
        profileImageView.setStyle("-fx-border-radius: 50%;");

        // User Information Labels
        Label nameLabel = new Label("Name: " + user.getName());
        Label emailLabel = new Label("Email: " + user.getEmail());
        Label ageLabel = new Label("Age: " + user.getAge());

        // Style the labels
        nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        emailLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");
        ageLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");

        // Adding the profile picture and labels to the content box
        profileContent.getChildren().addAll(profileImageView, nameLabel, emailLabel, ageLabel);

        // Buttons (Optional, e.g., Edit Profile)
        Button editProfileButton = new Button("Edit Profile");
        editProfileButton.setStyle("-fx-font-size: 14px; -fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-border-radius: 5px;");
        profileContent.getChildren().add(editProfileButton);

        // Set the content and show the dialog
        profileDialog.getDialogPane().setContent(profileContent);
        profileDialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        // This will ensure the dialog is shown on the correct thread
        profileDialog.showAndWait();
    }

}
