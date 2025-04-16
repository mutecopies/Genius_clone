package org.example.musicapp.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.example.musicapp.models.Artist;
import org.example.musicapp.models.Comment;
import org.example.musicapp.models.Song;
import org.example.musicapp.models.User;

import java.util.List;

public class UserPage {
    private Stage primaryStage;
    private VBox userLayout;
    private User user;
    private List<Song> songs;
    private Song selectedSong;

    public UserPage(Stage primaryStage, User user, List<Song> songs) {
        this.primaryStage = primaryStage;
        this.user = user;
        this.songs = songs;
        if (!songs.isEmpty()) {
            this.selectedSong = songs.get(0); // default selection
        }
        setupUserPage();
    }

    public Scene getScene() {
        return new Scene(userLayout, 800, 600);
    }

    private void setupUserPage() {
        userLayout = new VBox(20);
        userLayout.setPadding(new Insets(40));
        userLayout.setAlignment(Pos.TOP_CENTER);
        userLayout.setStyle("-fx-background-color: #ffffff;");

        Label titleLabel = new Label("Your Profile");
        titleLabel.setFont(Font.font("Arial Black", 28));
        titleLabel.setTextFill(Color.BLACK);

        VBox userInfoBox = new VBox(10);
        userInfoBox.setPadding(new Insets(15));
        userInfoBox.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 12px;");
        userInfoBox.setMaxWidth(400);

        Label nameLabel = styledLabel("👤 Name: " + user.getName());
        Label emailLabel = styledLabel("📧 Email: " + user.getEmail());
        Label ageLabel = styledLabel("🎂 Age: " + user.getAge());

        userInfoBox.getChildren().addAll(nameLabel, emailLabel, ageLabel);

        Label followedArtistsLabel = sectionLabel("🎵 Artists You Follow");
        VBox followedArtistsBox = new VBox(5);
        followedArtistsBox.setMaxWidth(400);

        List<Artist> followed = user.getFollowedArtists();
        if (followed.isEmpty()) {
            followedArtistsBox.getChildren().add(styledLabel("You are not following any artists yet."));
        } else {
            for (Artist artist : followed) {
                followedArtistsBox.getChildren().add(styledLabel("• " + artist.getName()));
            }
        }

        Label commentsLabel = sectionLabel("💬 Your Comments");
        VBox commentsBox = new VBox(8);
        commentsBox.setPadding(new Insets(10));
        commentsBox.setMaxWidth(500);
        commentsBox.setStyle("-fx-background-color: #f9f9f9; -fx-background-radius: 10px;");

        // Song Picker (ComboBox)
        ComboBox<Song> songComboBox = new ComboBox<>();
        songComboBox.getItems().addAll(songs);
        songComboBox.getSelectionModel().selectFirst();
        songComboBox.setOnAction(e -> {
            selectedSong = songComboBox.getValue();
            refreshCommentsSection(commentsBox);
        });

        refreshCommentsSection(commentsBox);

        TextField commentField = new TextField();
        commentField.setPromptText("Enter your comment...");
        commentField.setStyle("-fx-padding: 10px; -fx-border-radius: 5px; -fx-border-color: #ccc;");

        Button submitCommentButton = new Button("Submit Comment");
        submitCommentButton.setStyle("-fx-font-size: 16px; -fx-background-color: #2ecc71; -fx-text-fill: white;");
        submitCommentButton.setOnAction(e -> {
            String commentText = commentField.getText();
            if (!commentText.isEmpty() && selectedSong != null) {
                Comment newComment = new Comment(user, selectedSong, commentText);
                user.getComments().add(newComment);
                commentField.clear();
                refreshCommentsSection(commentsBox);
            }
        });

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 16px; -fx-background-color: #e74c3c; -fx-text-fill: white;");
        backButton.setOnAction(e -> {
            HomePage homePage = new HomePage(primaryStage, user);
            primaryStage.setScene(homePage.getScene());
        });

        userLayout.getChildren().addAll(
                titleLabel,
                userInfoBox,
                followedArtistsLabel,
                followedArtistsBox,
                commentsLabel,
                songComboBox,
                commentsBox,
                commentField,
                submitCommentButton,
                backButton
        );
    }

    private Label sectionLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", 20));
        label.setTextFill(Color.web("#111111"));
        return label;
    }

    private Label styledLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", 16));
        label.setTextFill(Color.web("#333333"));
        return label;
    }

    private void refreshCommentsSection(VBox commentsBox) {
        commentsBox.getChildren().clear();
        List<Comment> userComments = user.getComments();
        for (Comment comment : userComments) {
            if (comment.getSong().equals(selectedSong)) {
                Label commentLabel = new Label("• " + comment.getText() + " (on: " + comment.getSong().getTitle() + ")");
                commentLabel.setWrapText(true);
                commentLabel.setFont(Font.font("Arial", 14));
                commentLabel.setStyle("-fx-padding: 4px;");
                commentsBox.getChildren().add(commentLabel);
            }
        }
        if (commentsBox.getChildren().isEmpty()) {
            commentsBox.getChildren().add(styledLabel("No comments yet for this song."));
        }
    }
}
