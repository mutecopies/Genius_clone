package org.example.musicapp.views;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import org.example.musicapp.models.Album;
import org.example.musicapp.models.Song;

public class AlbumPage {
    private Stage primaryStage;
    private Album album;
    private VBox albumLayout;

    public AlbumPage(Stage primaryStage, Album album) {
        this.primaryStage = primaryStage;
        this.album = album;
        this.albumLayout = new VBox(15);
        setupAlbumPage();
    }

    public Scene getScene() {
        return new Scene(albumLayout, 800, 600);
    }

    private void setupAlbumPage() {
        Label titleLabel = new Label("Album: " + album.getTitle());
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label releaseDateLabel = new Label("Release Date: " + album.getReleaseDate());
        releaseDateLabel.setStyle("-fx-font-size: 16px;");

        Label songsLabel = new Label("Tracklist:");
        songsLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        VBox songList = new VBox(10);
        int trackNumber = 1;
        for (Song song : album.getSongs()) {
            Label songLabel = new Label(trackNumber + ". " + song.getTitle());
            songList.getChildren().add(songLabel);
            trackNumber++;
        }

        albumLayout.getChildren().addAll(titleLabel, releaseDateLabel, songsLabel, songList);
        albumLayout.setAlignment(Pos.TOP_CENTER);
        albumLayout.setStyle("-fx-background-color: #f8f8f8; -fx-padding: 20px;");
    }
}
