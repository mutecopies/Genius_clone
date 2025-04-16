package org.example.musicapp.views;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import org.example.musicapp.models.Artist;
import org.example.musicapp.models.Song;
import org.example.musicapp.models.Album;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CreateAlbumPage {

    private Stage primaryStage;
    private Artist artist;
    private VBox createAlbumLayout;

    // List to hold the newly created songs for the album
    private List<Song> songsInAlbum = new ArrayList<>();

    public CreateAlbumPage(Stage primaryStage, Artist artist) {
        this.primaryStage = primaryStage;
        this.artist = artist;
        setupCreateAlbumPage();
    }

    public Scene getScene() {
        return new Scene(createAlbumLayout, 800, 600);
    }

    private void setupCreateAlbumPage() {
        createAlbumLayout = new VBox(20);
        createAlbumLayout.setPadding(new Insets(20));
        createAlbumLayout.setAlignment(Pos.CENTER);

        // Title
        Label titleLabel = new Label("Create a New Album");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Album title input
        TextField albumTitleField = new TextField();
        albumTitleField.setPromptText("Enter album title");

        // Release date input
        TextField releaseDateField = new TextField();
        releaseDateField.setPromptText("Enter release date (YYYY-MM-DD)");

        // Song title input
        TextField songTitleField = new TextField();
        songTitleField.setPromptText("Enter song title");

        // Lyrics input for the song
        TextArea lyricsField = new TextArea();
        lyricsField.setPromptText("Enter song lyrics");
        lyricsField.setPrefRowCount(4);

        // Add song button
        Button createSongButton = new Button("Add Song to Album");
        createSongButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");

        ListView<String> songListView = new ListView<>();
        songListView.setPrefHeight(150);
        songListView.getItems().add("No songs added yet");

        createSongButton.setOnAction(e -> {
            String songTitle = songTitleField.getText().trim();
            String lyrics = lyricsField.getText().trim();

            if (!songTitle.isEmpty() && !lyrics.isEmpty()) {
                Song newSong = new Song(songTitle, lyrics, new ArrayList<>(), "Unknown", new ArrayList<>(), "Unknown");
                songsInAlbum.add(newSong);

                songListView.getItems().clear();
                for (Song song : songsInAlbum) {
                    songListView.getItems().add(song.getTitle());
                }

                songTitleField.clear();
                lyricsField.clear();
            } else {
                showAlert("Error", "Please enter both song title and lyrics.");
            }
        });

        // Create album button
        Button createAlbumButton = new Button("Create Album");
        createAlbumButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;");
        createAlbumButton.setOnAction(e -> {
            String albumTitle = albumTitleField.getText();
            String releaseDate = releaseDateField.getText();

            if (!albumTitle.isEmpty() && !releaseDate.isEmpty() && !songsInAlbum.isEmpty()) {
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date parsedDate = formatter.parse(releaseDate);

                    Album newAlbum = new Album(albumTitle, artist, parsedDate);
                    for (Song song : songsInAlbum) {
                        newAlbum.addSong(song);
                    }

                    artist.addAlbum(newAlbum);

                    // Go back to artist page
                    ArtistPage artistPage = new ArtistPage(primaryStage, artist);
                    primaryStage.setScene(artistPage.getScene());
                } catch (ParseException ex) {
                    showAlert("Error", "Invalid date format. Use YYYY-MM-DD.");
                }
            } else {
                showAlert("Error", "Please fill in all fields and add at least one song.");
            }
        });

        // Back button
        Button backButton = new Button("← Back to Artist Page");
        backButton.setStyle("-fx-background-color: gray; -fx-text-fill: white;");
        backButton.setOnAction(e -> {
            ArtistPage artistPage = new ArtistPage(primaryStage, artist);
            primaryStage.setScene(artistPage.getScene());
        });

        createAlbumLayout.getChildren().addAll(
                titleLabel,
                albumTitleField,
                releaseDateField,
                songTitleField,
                lyricsField,
                createSongButton,
                songListView,
                createAlbumButton,
                backButton
        );
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
