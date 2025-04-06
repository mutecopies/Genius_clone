package org.example.musicapp.views;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import org.example.musicapp.models.Song;
import org.example.musicapp.models.User;
import org.example.musicapp.models.Artist;
import org.example.musicapp.models.Role;

public class SongPage {

    private Stage primaryStage;
    private Song currentSong;
    private User currentUser;
    private VBox songLayout;

    public SongPage(Stage primaryStage, Song song, User user) {
        this.primaryStage = primaryStage;
        this.currentSong = song;
        this.currentUser = user;
        this.songLayout = new VBox(20);
        setupSongPage();
    }

    public VBox getSongLayout() {
        return songLayout;
    }

    public Scene getScene() {
        return new Scene(songLayout, 600, 400);
    }

    private void setupSongPage() {
        // Song Title and Artist Name
        Label titleLabel = new Label("Song Title: " + currentSong.getTitle());
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        String artistName = currentSong.getArtists().isEmpty()
                ? "Unknown Artist"
                : currentSong.getArtists().get(0).getName();

        Label artistLabel = new Label("Artist: " + artistName);
        artistLabel.setStyle("-fx-font-size: 16px; -fx-font-style: italic;");

        // Song Lyrics
        Label lyricsLabel = new Label("Lyrics:");
        TextArea lyricsArea = new TextArea(currentSong.getLyrics());
        lyricsArea.setEditable(false);
        lyricsArea.setWrapText(true);
        lyricsArea.setPrefHeight(150);

        // Edit Lyrics (only available for artists)
        if (currentUser.getRole().equals("Artist")) {
            Button editLyricsButton = new Button("Edit Lyrics");
            editLyricsButton.setOnAction(e -> editLyrics());
            songLayout.getChildren().add(editLyricsButton);
        } else if (currentUser.getRole().equals("User")) {
            // Suggest Edit (only available for users)
            Button suggestEditButton = new Button("Suggest Edit");
            suggestEditButton.setOnAction(e -> suggestEdit());
            songLayout.getChildren().add(suggestEditButton);
        }

        // Comments Section
        Label commentsLabel = new Label("Comments:");
        TextArea commentsArea = new TextArea();
        commentsArea.setPromptText("Add your comment...");
        commentsArea.setPrefHeight(100);

        Button commentButton = new Button("Post Comment");
        commentButton.setOnAction(e -> postComment(commentsArea.getText()));

        // Follow Artist Button
        Button followArtistButton = new Button("Follow Artist");
        followArtistButton.setOnAction(e -> followArtist());

        songLayout.getChildren().addAll(
                titleLabel,
                artistLabel,
                lyricsLabel,
                lyricsArea,
                commentsLabel,
                commentsArea,
                commentButton,
                followArtistButton
        );
        songLayout.setAlignment(Pos.CENTER);
        songLayout.setStyle("-fx-background-color: #f0f0f0;");
    }

    private void editLyrics() {
        TextInputDialog editDialog = new TextInputDialog(currentSong.getLyrics());
        editDialog.setTitle("Edit Lyrics");
        editDialog.setHeaderText("Edit the lyrics for " + currentSong.getTitle());
        editDialog.setContentText("New Lyrics:");

        editDialog.showAndWait().ifPresent(newLyrics -> {
            currentSong.setLyrics(newLyrics);
            System.out.println("Lyrics updated: " + newLyrics);
        });
    }

    private void suggestEdit() {
        TextInputDialog suggestDialog = new TextInputDialog();
        suggestDialog.setTitle("Suggest Edit");
        suggestDialog.setHeaderText("Suggest an edit for " + currentSong.getTitle());
        suggestDialog.setContentText("Suggested Edit:");

        suggestDialog.showAndWait().ifPresent(suggestedEdit -> {
            System.out.println("Suggested Edit: " + suggestedEdit);
        });
    }

    private void postComment(String comment) {
        System.out.println("New Comment: " + comment);
        // Save to file or DB if needed
    }

    private void followArtist() {
        if (!currentSong.getArtists().isEmpty()) {
            String name = currentSong.getArtists().get(0).getName();
            System.out.println("Following artist: " + name);
        } else {
            System.out.println("No artist to follow.");
        }
    }
}
