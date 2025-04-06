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

        // Songs section
        Label songsLabel = new Label("Songs");
        songsLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        VBox songList = new VBox(10);
        for (Song song : artist.getSongs()) {
            Label songLabel = new Label("- " + song.getTitle());
            songList.getChildren().add(songLabel);
        }

        // Albums section with clickable links
        Label albumsLabel = new Label("Albums");
        albumsLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        VBox albumList = new VBox(10);
        for (Album album : artist.getAlbums()) {
            Hyperlink albumLink = new Hyperlink(album.getTitle());
            albumLink.setOnAction(e -> {
                AlbumPage albumPage = new AlbumPage(primaryStage, album);
                primaryStage.setScene(albumPage.getScene());
            });
            albumList.getChildren().add(albumLink);
        }

        VBox songsAndAlbums = new VBox(20, songsLabel, songList, albumsLabel, albumList);

        artistLayout.getChildren().addAll(
                nameLabel,
                songsAndAlbums
        );

        artistLayout.setAlignment(Pos.TOP_CENTER);
        artistLayout.setStyle("-fx-background-color: #f8f8f8; -fx-padding: 20px;");
    }
}
