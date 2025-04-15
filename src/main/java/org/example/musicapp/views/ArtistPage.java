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

public class ArtistPage {

    private Stage primaryStage;
    private Artist artist;
    private VBox artistLayout;

    public ArtistPage(Stage primaryStage, Artist artist) {
        this.primaryStage = primaryStage;
        this.artist = artist;
        this.artistLayout = new VBox(20);
        setupArtistProfilePage();
    }

    public Scene getScene() {
        return new Scene(artistLayout, 800, 600);
    }

    private void setupArtistProfilePage() {
        // Back Button to Homepage
        Button backButton = new Button("← Back");
        backButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #3498db; -fx-font-size: 14px;");
        backButton.setOnAction(e -> {
            // Navigate back to the HomePage
            HomePage homepage = new HomePage(primaryStage, artist);
            Scene homepageScene = homepage.getScene();
            primaryStage.setScene(homepageScene);
        });

        HBox backButtonBox = new HBox(backButton);
        backButtonBox.setAlignment(Pos.TOP_LEFT);
        backButtonBox.setPadding(new Insets(10, 0, 0, 10));

        // Header - Artist Name
        Label nameLabel = new Label("Artist: " + artist.getName());
        nameLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Songs section
        Label songsLabel = new Label("Songs");
        songsLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        VBox songList = new VBox(10);
        for (Song song : artist.getSongs()) {
            // Create a label for each song
            Label songLabel = new Label("- " + song.getTitle());
            songLabel.setStyle("-fx-text-fill: #34495e;");

            // Create a TextArea to display song lyrics (initially hidden)
            TextArea lyricsTextArea = new TextArea();
            lyricsTextArea.setText(song.getLyrics());
            lyricsTextArea.setEditable(false);
            lyricsTextArea.setWrapText(true);
            lyricsTextArea.setPrefHeight(100);
            lyricsTextArea.setVisible(false);  // Initially hidden

            // Set an action to display/hide lyrics when the song is clicked
            songLabel.setOnMouseClicked(e -> {
                lyricsTextArea.setVisible(!lyricsTextArea.isVisible());  // Toggle visibility of lyrics
            });

            songList.getChildren().addAll(songLabel, lyricsTextArea); // Add both song title and lyrics area
        }

        // Albums section with clickable links
        Label albumsLabel = new Label("Albums");
        albumsLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        VBox albumList = new VBox(10);
        for (Album album : artist.getAlbums()) {
            Hyperlink albumLink = new Hyperlink(album.getTitle());
            albumLink.setStyle("-fx-text-fill: #3498db;");
            albumLink.setOnAction(e -> {
                // Navigate to the album page when clicked
                AlbumPage albumPage = new AlbumPage(primaryStage, album);
                primaryStage.setScene(albumPage.getScene());
            });
            albumList.getChildren().add(albumLink);
        }

        // Create New Song Button
        Button createSongButton = new Button("Create New Song");
        createSongButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        createSongButton.setOnAction(e -> {
            // Navigate to the Create Song page
            CreateSongPage createSongPage = new CreateSongPage(primaryStage, artist);
            primaryStage.setScene(createSongPage.getScene());
        });

        // Create New Album Button
        Button createAlbumButton = new Button("Create New Album");
        createAlbumButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        createAlbumButton.setOnAction(e -> {
            // Navigate to the Create Album page
            CreateAlbumPage createAlbumPage = new CreateAlbumPage(primaryStage, artist);
            primaryStage.setScene(createAlbumPage.getScene());
        });

        // Add all elements to the layout
        VBox songsAndAlbums = new VBox(20, songsLabel, songList, albumsLabel, albumList, createSongButton, createAlbumButton);
        artistLayout.getChildren().addAll(
                backButtonBox,
                nameLabel,
                songsAndAlbums
        );

        artistLayout.setAlignment(Pos.TOP_CENTER);
        artistLayout.setStyle("-fx-background-color: #f8f8f8; -fx-padding: 20px;");
    }
}
