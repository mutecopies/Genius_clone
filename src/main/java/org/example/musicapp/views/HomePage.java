package org.example.musicapp.views;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import java.util.ArrayList;
import java.util.List;
import org.example.musicapp.models.User;
import org.example.musicapp.models.Artist;
import org.example.musicapp.models.Song;
import org.example.musicapp.models.Album;
import org.example.musicapp.utils.MusicLibrary;

public class HomePage {

    private Stage primaryStage;
    private User user;
    private BorderPane homeLayout;

    public HomePage(Stage primaryStage, User user) {
        this.primaryStage = primaryStage;
        this.user = user;
        this.homeLayout = new BorderPane();
        setupHomePage();
    }

    public Scene getScene() {
        return new Scene(homeLayout, 800, 600);
    }

    private void setupHomePage() {
        // Header with Welcome + Profile + Search
        VBox topSection = new VBox(10);
        topSection.setStyle("-fx-background-color: #f39c12; -fx-padding: 10px;");

        // Header bar (Welcome + Profile Button)
        HBox header = new HBox(20);
        Label welcomeLabel = new Label("Welcome, " + user.getName());
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

        Button userButton = new Button("Your Profile");
        userButton.setStyle("-fx-font-size: 16px; -fx-background-color: #e74c3c; -fx-text-fill: white;");
        userButton.setOnAction(e -> {
            UserPage userPage = new UserPage(primaryStage, user);
            primaryStage.setScene(new Scene(userPage.getUserLayout(), 800, 600));
        });

        header.getChildren().addAll(welcomeLabel, userButton);
        header.setAlignment(Pos.CENTER_RIGHT);

        // Search Bar
        HBox searchBar = new HBox(10);
        TextField searchField = new TextField();
        searchField.setPromptText("Search for artist, song, or album...");
        searchField.setPrefWidth(300);

        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;");

        ListView<String> searchResults = new ListView<>();

        searchButton.setOnAction(e -> {
            String query = searchField.getText().toLowerCase();
            searchResults.getItems().clear();

            // Replace these with actual data from MusicLibrary
            List<Artist> artists = MusicLibrary.getAllArtists();
            List<Album> albums = MusicLibrary.getAllAlbums();
            List<Song> songs = MusicLibrary.getAllSongs();

            for (Artist artist : artists) {
                if (artist.getName().toLowerCase().contains(query)) {
                    searchResults.getItems().add("Artist: " + artist.getName());
                }
            }
            for (Album album : albums) {
                if (album.getTitle().toLowerCase().contains(query)) {
                    searchResults.getItems().add("Album: " + album.getTitle());
                }
            }
            for (Song song : songs) {
                if (song.getTitle().toLowerCase().contains(query)) {
                    searchResults.getItems().add("Song: " + song.getTitle());
                }
            }

            if (searchResults.getItems().isEmpty()) {
                searchResults.getItems().add("No results found.");
            }
        });

        searchBar.getChildren().addAll(searchField, searchButton);
        searchBar.setAlignment(Pos.CENTER);

        topSection.getChildren().addAll(header, searchBar);

        // Body content (Trending Songs + Search Results)
        VBox body = new VBox(20);
        body.setStyle("-fx-padding: 20px;");
        body.getChildren().addAll(new Label("🔥 Trending Songs"), searchResults);

        // Final layout
        homeLayout.setTop(topSection);
        homeLayout.setCenter(body);
        homeLayout.setStyle("-fx-background-color: #f1c40f;");
    }

}
