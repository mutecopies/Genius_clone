package org.example.musicapp.views;


import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import org.example.musicapp.models.Song;
import org.example.musicapp.models.User;
import org.example.musicapp.models.Artist;
import org.example.musicapp.models.Song;

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

        Label artistLabel = new Label("Artist: " + currentSong.getArtist());
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

        songLayout.getChildren().addAll(titleLabel, artistLabel, lyricsLabel, lyricsArea, commentsLabel, commentsArea, commentButton, followArtistButton);
        songLayout.setAlignment(Pos.CENTER);
        songLayout.setStyle("-fx-background-color: #f0f0f0;");
    }

    private void editLyrics() {
        // Implement the editing functionality for artists
        TextInputDialog editDialog = new TextInputDialog(currentSong.getLyrics());
        editDialog.setTitle("Edit Lyrics");
        editDialog.setHeaderText("Edit the lyrics for " + currentSong.getTitle());
        editDialog.setContentText("New Lyrics:");

        editDialog.showAndWait().ifPresent(newLyrics -> {
            currentSong.setLyrics(newLyrics);
            // You might want to save this edit to a file or database
            System.out.println("Lyrics updated: " + newLyrics);
        });
    }

    private void suggestEdit() {
        // Implement the functionality for suggesting an edit (for users)
        TextInputDialog suggestDialog = new TextInputDialog();
        suggestDialog.setTitle("Suggest Edit");
        suggestDialog.setHeaderText("Suggest an edit for " + currentSong.getTitle());
        suggestDialog.setContentText("Suggested Edit:");

        suggestDialog.showAndWait().ifPresent(suggestedEdit -> {
            // Handle suggested edit, maybe notify the artist
            System.out.println("Suggested Edit: " + suggestedEdit);
        });
    }

    private void postComment(String comment) {
        // Post the user's comment
        System.out.println("New Comment: " + comment);
        // You could save comments to a database or file here
    }

    private void followArtist() {
        // Implement follow artist functionality
        System.out.println("Following artist: " + currentSong.getArtist());
        // You might want to store the follow in a list or database
    }
}
