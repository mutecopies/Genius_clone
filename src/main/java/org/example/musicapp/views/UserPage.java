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
            this.selectedSong = songs.get(0); // Default selection
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

        VBox userInfoBox = createUserInfoBox();
        VBox followedArtistsBox = createFollowedArtistsBox();
        VBox commentsBox = createCommentsBox();

        ComboBox<Song> songComboBox = createSongComboBox(commentsBox);

        TextField commentField = createCommentField();
        Button submitCommentButton = createSubmitCommentButton(commentField, commentsBox);

        Button backButton = createBackButton();

        userLayout.getChildren().addAll(
                titleLabel,
                userInfoBox,
                sectionLabel("🎵 Artists You Follow"),
                followedArtistsBox,
                sectionLabel("💬 Your Comments"),
                songComboBox,
                commentsBox,
                commentField,
                submitCommentButton,
                backButton
        );

        // Wrap the userLayout in a ScrollPane to enable scrolling
        ScrollPane scrollPane = new ScrollPane(userLayout);
        scrollPane.setFitToHeight(true);  // Fit to the height of the screen
        scrollPane.setFitToWidth(true);   // Fit to the width of the screen

        userLayout.setStyle("-fx-background-color: #ffffff;");
        userLayout.setPrefWidth(800);

        // Setting the scene with the scrollable user layout
        Scene scene = new Scene(scrollPane, 800, 600);
        primaryStage.setScene(scene);
    }

    private VBox createUserInfoBox() {
        VBox userInfoBox = new VBox(10);
        userInfoBox.setPadding(new Insets(15));
        userInfoBox.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 12px;");
        userInfoBox.setMaxWidth(400);

        Label nameLabel = styledLabel("👤 Name: " + user.getName());
        Label emailLabel = styledLabel("📧 Email: " + user.getEmail());
        Label ageLabel = styledLabel("🎂 Age: " + user.getAge());

        userInfoBox.getChildren().addAll(nameLabel, emailLabel, ageLabel);

        return userInfoBox;
    }

    private VBox createFollowedArtistsBox() {
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

        return followedArtistsBox;
    }

    private VBox createCommentsBox() {
        VBox commentsBox = new VBox(8);
        commentsBox.setPadding(new Insets(10));
        commentsBox.setMaxWidth(500);
        commentsBox.setStyle("-fx-background-color: #f9f9f9; -fx-background-radius: 10px;");
        refreshCommentsSection(commentsBox);
        return commentsBox;
    }

    private ComboBox<Song> createSongComboBox(VBox commentsBox) {
        ComboBox<Song> songComboBox = new ComboBox<>();
        songComboBox.getItems().addAll(songs);
        songComboBox.getSelectionModel().selectFirst();
        songComboBox.setOnAction(e -> {
            selectedSong = songComboBox.getValue();
            refreshCommentsSection(commentsBox);
        });
        return songComboBox;
    }

    private TextField createCommentField() {
        TextField commentField = new TextField();
        commentField.setPromptText("Enter your comment...");
        commentField.setStyle("-fx-padding: 10px; -fx-border-radius: 5px; -fx-border-color: #ccc;");
        return commentField;
    }

    private Button createSubmitCommentButton(TextField commentField, VBox commentsBox) {
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
        return submitCommentButton;
    }

    private Button createBackButton() {
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 16px; -fx-background-color: #e74c3c; -fx-text-fill: white;");
        backButton.setOnAction(e -> {
            HomePage homePage = new HomePage(primaryStage, user);
            primaryStage.setScene(homePage.getScene());
        });
        return backButton;
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
            commentsBox.getChildren().add(styledLabel(selectedSong.getLyrics()));
        }
    }
}

