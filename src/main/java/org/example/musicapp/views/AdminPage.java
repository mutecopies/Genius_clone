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
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        HBox topSection = createTopSection();

        Label title = new Label("Admin Dashboard");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label pendingLabel = new Label("Pending Artist Registrations:");

        ChoiceBox<Artist> pendingArtistBox = new ChoiceBox<>();
        pendingArtistBox.getItems().addAll(ArtistDatabase.getPendingArtists());

        pendingArtistBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Artist artist) {
                return (artist != null) ? artist.getName() : "";
            }

            @Override
            public Artist fromString(String name) {
                return ArtistDatabase.getArtistByName(name);
            }
        });

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

        root.getChildren().addAll(topSection, title, pendingLabel, pendingArtistBox, approveButton, rejectButton);
        return new Scene(root, 800, 600);
    }

    private HBox createTopSection() {
        HBox topSection = new HBox(20);
        topSection.setAlignment(Pos.CENTER_RIGHT);
        topSection.setStyle("-fx-background-color: #f39c12; -fx-padding: 10px;");

        Label welcomeLabel = new Label("Welcome, Admin: " + admin.getName());
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

        Button backButton = new Button("← Back to Homepage");
        styleButton(backButton, "#2980b9");
        backButton.setOnAction(e -> goToHomepage());

        Button profileButton = new Button("Your Profile");
        styleButton(profileButton, "#e67e22");
        profileButton.setOnAction(e -> navigateToProfilePage());

        Button logoutButton = new Button("Log Out");
        styleButton(logoutButton, "#34495e");
        logoutButton.setOnAction(e -> logout());

        topSection.getChildren().addAll(welcomeLabel, backButton, profileButton, logoutButton);
        return topSection;
    }

    private void styleButton(Button button, String color) {
        button.setStyle("-fx-font-size: 16px; -fx-background-color: " + color + "; -fx-text-fill: white;");
    }

    private void navigateToProfilePage() {
        System.out.println("Navigating to Admin Profile Page...");
        // TODO: Add real profile page later if needed
    }

    private void logout() {
        System.out.println("Logging out and going back to Login Page...");
        primaryStage.setScene(new LoginPage(primaryStage).getScene());
    }

    private void goToHomepage() {
        System.out.println("Navigating to Homepage...");
        primaryStage.setScene(new HomePage(primaryStage, admin).getScene());
    }
}
