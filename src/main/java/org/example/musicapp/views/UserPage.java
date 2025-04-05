package org.example.musicapp.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.example.musicapp.models.Artist;
import org.example.musicapp.models.Comment;
import org.example.musicapp.models.User;

import java.util.List;

public class UserPage {
    private Stage primaryStage;
    private VBox userLayout;
    private User user;

    public UserPage(Stage primaryStage, User user) {
        this.primaryStage = primaryStage;
        this.user = user;
        setupUserPage();
    }

    public VBox getUserLayout() {
        return userLayout;
    }

    private void setupUserPage() {
        userLayout = new VBox(30);
        userLayout.setPadding(new Insets(40));
        userLayout.setAlignment(Pos.TOP_CENTER);
        userLayout.setStyle("-fx-background-color: #ffffff;");

        // Header
        Label titleLabel = new Label("Your Profile");
        titleLabel.setFont(Font.font("Arial Black", 28));
        titleLabel.setTextFill(Color.BLACK);

        // User Info Section
        VBox userInfoBox = new VBox(10);
        userInfoBox.setPadding(new Insets(15));
        userInfoBox.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 12px;");
        userInfoBox.setMaxWidth(400);

        Label nameLabel = styledLabel("👤 Name: " + user.getName());
        Label emailLabel = styledLabel("📧 Email: " + user.getEmail());
        Label ageLabel = styledLabel("🎂 Age: " + user.getAge());

        userInfoBox.getChildren().addAll(nameLabel, emailLabel, ageLabel);

        // Followed Artists Section
        Label followedArtistsLabel = sectionLabel("🎵 Followed Artists");
        ListView<String> followedArtistsList = new ListView<>();
        followedArtistsList.setMaxHeight(100);
        followedArtistsList.setMaxWidth(400);
        for (Artist artist : user.getFollowedArtists()) {
            followedArtistsList.getItems().add(artist.getName());
        }

        // Comments Section
        Label commentsLabel = sectionLabel("💬 Your Comments");
        VBox commentsBox = new VBox(8);
        commentsBox.setPadding(new Insets(10));
        commentsBox.setMaxWidth(500);
        commentsBox.setStyle("-fx-background-color: #f9f9f9; -fx-background-radius: 10px;");

        List<Comment> userComments = user.getComments();
        if (userComments != null && !userComments.isEmpty()) {
            for (Comment comment : userComments) {
                Label commentLabel = new Label("• " + comment.getText() + "  (on: " + comment.getSong().getTitle() + ")");
                commentLabel.setWrapText(true);
                commentLabel.setFont(Font.font("Arial", 14));
                commentLabel.setStyle("-fx-padding: 4px;");
                commentsBox.getChildren().add(commentLabel);
            }
        } else {
            commentsBox.getChildren().add(styledLabel("No comments yet."));
        }

        // Add everything to layout
        userLayout.getChildren().addAll(
                titleLabel,
                userInfoBox,
                followedArtistsLabel,
                followedArtistsList,
                commentsLabel,
                commentsBox
        );
    }

    // Helper for section titles
    private Label sectionLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", 20));
        label.setTextFill(Color.web("#111111"));
        return label;
    }

    // Helper for styled normal text
    private Label styledLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", 16));
        label.setTextFill(Color.web("#333333"));
        return label;
    }
}
