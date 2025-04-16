package org.example.musicapp.views;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import org.example.musicapp.models.Album;
import org.example.musicapp.models.Song;
import org.example.musicapp.models.Artist;

import java.text.SimpleDateFormat;

public class AlbumPage {

    private Stage primaryStage;
    private Album album;
    private Artist artist;
    private VBox albumLayout;

    public AlbumPage(Stage primaryStage, Album album, Artist artist) {
        this.primaryStage = primaryStage;
        this.album = album;
        this.artist = artist;
        this.albumLayout = new VBox(20);
        setupAlbumPage();
    }

    public Scene getScene() {
        return new Scene(albumLayout, 800, 600);
    }

    private void setupAlbumPage() {
        // Header - Album Title
        Label titleLabel = new Label("Album: " + album.getTitle());
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Artist Info
        Label artistLabel = new Label("Artist: " + (artist != null ? artist.getName() : "Unknown Artist"));
        artistLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Release Date
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        Label releaseDateLabel = new Label("Release Date: " + formatter.format(album.getReleaseDate()));
        releaseDateLabel.setStyle("-fx-font-size: 16px;");

        // Tracklist
        Label tracklistLabel = new Label("Tracklist");
        tracklistLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        VBox tracklistBox = new VBox(10);
        if (album.getSongs().isEmpty()) {
            Label noSongsLabel = new Label("No songs in this album yet.");
            tracklistBox.getChildren().add(noSongsLabel);
        } else {
            for (Song song : album.getSongs()) {
                Label songLabel = new Label("- " + song.getTitle());
                tracklistBox.getChildren().add(songLabel);
            }
        }

        // Back Button
        Button backButton = new Button("← Back to Artist Page");
        backButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px;");
        backButton.setOnAction(e -> {
            ArtistPage artistPage = new ArtistPage(primaryStage, artist);
            primaryStage.setScene(artistPage.getScene());
        });

        // Layout
        albumLayout.getChildren().addAll(
                titleLabel,
                artistLabel,
                releaseDateLabel,
                tracklistLabel,
                tracklistBox,
                backButton
        );

        albumLayout.setAlignment(Pos.TOP_CENTER);
        albumLayout.setStyle("-fx-background-color: #f8f8f8; -fx-padding: 20px;");
    }
}
