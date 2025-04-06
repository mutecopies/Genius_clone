package org.example.musicapp.views;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
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
        // Header - Artist Name
        Label nameLabel = new Label("Artist: " + artist.getName());
        nameLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Songs and Albums List
        Label songsAndAlbumsLabel = new Label("Songs and Albums");
        songsAndAlbumsLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        VBox songsAndAlbums = new VBox(10);

        // Displaying the songs the artist created
        for (Song song : artist.getSongs()) {
            Label songLabel = new Label("Song: " + song.getTitle());
            songsAndAlbums.getChildren().add(songLabel);
        }

        // Displaying the albums the artist created
        for (Album album : artist.getAlbums()) {
            Label albumLabel = new Label("Album: " + album.getTitle());
            songsAndAlbums.getChildren().add(albumLabel);
        }

        // Add all elements to the layout
        artistLayout.getChildren().addAll(
                nameLabel,
                songsAndAlbumsLabel,
                songsAndAlbums
        );

        artistLayout.setAlignment(Pos.TOP_CENTER);
        artistLayout.setStyle("-fx-background-color: #f8f8f8;");
    }
}
