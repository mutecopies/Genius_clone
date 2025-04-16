package org.example.musicapp.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.example.musicapp.database.ArtistDatabase;
import org.example.musicapp.models.Admin;
import org.example.musicapp.models.Artist;

public class AdminPage {

    private final Stage primaryStage;
    private final Admin admin;

    public AdminPage(Stage primaryStage, Admin admin) {
        this.primaryStage = primaryStage;
        this.admin = admin;
    }

    public Scene getScene() {
        // Root container with padding and centered alignment
        VBox root = new VBox(20);
        root.setPadding(new Insets(30, 20, 20, 20));
        root.setAlignment(Pos.TOP_CENTER);  // Center the content in the middle of the screen

        // Create top section with navigation buttons
        HBox topSection = createTopSection();

        // Title label with admin dashboard title
        Label title = new Label("Admin Dashboard");
        title.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Label for pending artist registrations
        Label pendingLabel = new Label("Pending Artist Registrations:");
        pendingLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #34495e;");

        // ChoiceBox for selecting pending artists
        ChoiceBox<Artist> pendingArtistBox = new ChoiceBox<>();
        pendingArtistBox.getItems().addAll(ArtistDatabase.getPendingArtists());

        pendingArtistBox.setConverter(new StringConverter<Artist>() {
            @Override
            public String toString(Artist artist) {
                return (artist != null) ? artist.getName() : "";
            }

            @Override
            public Artist fromString(String name) {
                return ArtistDatabase.getArtistByName(name);
            }
        });

        // Buttons for approving and rejecting artists
        Button approveButton = new Button("Approve");
        Button rejectButton = new Button("Reject");

        styleButton(approveButton, "#27ae60");
        styleButton(rejectButton, "#e74c3c");

        approveButton.setOnAction(e -> {
            Artist selected = pendingArtistBox.getValue();
            if (selected != null) {
                ArtistDatabase.approveArtist(selected);
                admin.approveArtistRegistration(selected);
                pendingArtistBox.getItems().remove(selected);
            }
        });

        rejectButton.setOnAction(e -> {
            Artist selected = pendingArtistBox.getValue();
            if (selected != null) {
                ArtistDatabase.rejectArtist(selected);
                pendingArtistBox.getItems().remove(selected);
            }
        });

        // Add all components to root VBox
        root.getChildren().addAll(topSection, title, pendingLabel, pendingArtistBox, approveButton, rejectButton);

        // Wrap everything in a ScrollPane for smooth scrolling
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);  // Make the content fit the width of the screen
        scrollPane.setFitToHeight(true); // Allow scrolling if content overflows vertically

        // Set the scene with the scrollable root container
        return new Scene(scrollPane, 800, 600);
    }

    private HBox createTopSection() {
        // Horizontal box for top section with buttons aligned to the right
        HBox topSection = new HBox(20);
        topSection.setAlignment(Pos.CENTER_RIGHT);
        topSection.setStyle("-fx-background-color: #f39c12; -fx-padding: 10px 20px;");

        // Welcome label
        Label welcomeLabel = new Label("Welcome, Admin: " + admin.getName());
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Buttons for navigating back to homepage, profile page, and logout
        Button backButton = new Button("← Back to Homepage");
        styleButton(backButton, "#2980b9");
        backButton.setOnAction(e -> goToHomepage());

        Button profileButton = new Button("Your Profile");
        styleButton(profileButton, "#e67e22");
        profileButton.setOnAction(e -> navigateToProfilePage());

        Button logoutButton = new Button("Log Out");
        styleButton(logoutButton, "#34495e");
        logoutButton.setOnAction(e -> logout());

        // Add all buttons to the top section
        topSection.getChildren().addAll(welcomeLabel, backButton, profileButton, logoutButton);

        return topSection;
    }

    private void styleButton(Button button, String color) {
        // Apply consistent styling to buttons
        button.setStyle("-fx-font-size: 16px; -fx-background-color: " + color + "; -fx-text-fill: white; -fx-padding: 8px 15px; -fx-border-radius: 5px;");
    }

    private void navigateToProfilePage() {
        // TODO: Implement navigation to admin profile page if needed
        System.out.println("Navigating to Admin Profile Page...");
    }

    private void logout() {
        // Navigate back to login page
        System.out.println("Logging out and going back to Login Page...");
        primaryStage.setScene(new LoginPage(primaryStage).getScene());
    }

    private void goToHomepage() {
        // Navigate to homepage
        System.out.println("Navigating to Homepage...");
        primaryStage.setScene(new HomePage(primaryStage, admin).getScene());
    }
}
