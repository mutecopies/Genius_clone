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

        // Song title input (for song creation)
        TextField songTitleField = new TextField();
        songTitleField.setPromptText("Enter song title");

        // Button to create a new song
        Button createSongButton = new Button("Add Song to Album");
        createSongButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");

        // ListView to display the songs added to the album
        ListView<String> songListView = new ListView<>();
        songListView.setPrefHeight(150);
        songListView.getItems().add("No songs added yet");

        // When the "Add Song to Album" button is clicked
        createSongButton.setOnAction(e -> {
            // Create a new song
            Song newSong = new Song(songTitleField.getText(), "", new ArrayList<>(), "Unknown", new ArrayList<>(), "Unknown");
            songsInAlbum.add(newSong);

            // Add song to the ListView to show the added songs
            songListView.getItems().clear();
            for (Song song : songsInAlbum) {
                songListView.getItems().add(song.getTitle());
            }

            // Clear song title field for next song creation
            songTitleField.clear();
        });

        // Create album button
        Button createAlbumButton = new Button("Create Album");
        createAlbumButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        createAlbumButton.setOnAction(e -> {
            String albumTitle = albumTitleField.getText();
            String releaseDate = releaseDateField.getText();

            if (!albumTitle.isEmpty() && !releaseDate.isEmpty() && !songsInAlbum.isEmpty()) {
                try {
                    // Parse the release date
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date parsedReleaseDate = formatter.parse(releaseDate);

                    // Create a new album with the provided details and add the songs
                    Album newAlbum = new Album(albumTitle, artist, parsedReleaseDate);
                    for (Song song : songsInAlbum) {
                        newAlbum.addSong(song);
                    }

                    artist.addAlbum(newAlbum);  // Add the album to the artist

                    // Navigate back to artist profile after album creation
                    ArtistPage artistPage = new ArtistPage(primaryStage, artist);
                    primaryStage.setScene(artistPage.getScene());
                } catch (ParseException parseException) {
                    showAlert("Error", "Invalid date format. Please use YYYY-MM-DD.");
                }
            } else {
                showAlert("Error", "Please fill in all fields and add at least one song.");
            }
        });

        // Add elements to the layout
        createAlbumLayout.getChildren().addAll(
                titleLabel, albumTitleField, releaseDateField, songTitleField, createSongButton, songListView, createAlbumButton
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
