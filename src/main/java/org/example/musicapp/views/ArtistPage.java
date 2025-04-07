package org.example.musicapp.views;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import org.example.musicapp.models.Artist;
import org.example.musicapp.models.Song;
import org.example.musicapp.models.Album;
import org.example.musicapp.views.CreateAlbumPage;
import org.example.musicapp.views.CreateSinglePage;

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
        nameLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Songs section
        Label songsLabel = new Label("Songs");
        songsLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        VBox songList = new VBox(10);
        for (Song song : artist.getSongs()) {
            Label songLabel = new Label("- " + song.getTitle());
            songLabel.setStyle("-fx-text-fill: #34495e;");
            songList.getChildren().add(songLabel);
        }

        // Albums section with clickable links
        Label albumsLabel = new Label("Albums");
        albumsLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        VBox albumList = new VBox(10);
        for (Album album : artist.getAlbums()) {
            Hyperlink albumLink = new Hyperlink(album.getTitle());
            albumLink.setStyle("-fx-text-fill: #3498db;");
            albumLink.setOnAction(e -> {
                AlbumPage albumPage = new AlbumPage(primaryStage, album);
                primaryStage.setScene(albumPage.getScene());
            });
            albumList.getChildren().add(albumLink);
        }

        // Create New Album Button
        Button createAlbumButton = new Button("Create New Album");
        createAlbumButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        createAlbumButton.setOnAction(e -> {
            CreateAlbumPage createAlbumPage = new CreateAlbumPage(primaryStage, artist);
            primaryStage.setScene(new Scene(createAlbumPage.getCreateAlbumLayout(), 800, 600));
        });

        // Create New Single Button
        Button createSingleButton = new Button("Create New Single");
        createSingleButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        createSingleButton.setOnAction(e -> {
            // Open the Create Single Page (form)
            CreateSinglePage createSinglePage = new CreateSinglePage(primaryStage, artist);
            primaryStage.setScene(new Scene(createSinglePage.getCreateSingleLayout(), 800, 600));
        });

        // Layout organization
        VBox songsAndAlbums = new VBox(20, songsLabel, songList, albumsLabel, albumList);
        HBox buttons = new HBox(20, createAlbumButton, createSingleButton);
        buttons.setAlignment(Pos.CENTER);

        // Adding everything to the layout
        artistLayout.getChildren().addAll(
                nameLabel,
                songsAndAlbums,
                buttons // Horizontal buttons layout
        );

        // Set the main layout properties
        artistLayout.setAlignment(Pos.TOP_CENTER);
        artistLayout.setStyle("-fx-background-color: #f8f8f8; -fx-padding: 20px;");
    }
}
