package org.example.musicapp.views;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import java.util.List;
import org.example.musicapp.models.User;
import org.example.musicapp.models.Artist;
import org.example.musicapp.models.Song;
import org.example.musicapp.models.Album;
import org.example.musicapp.utils.MusicLibrary;

public class HomePage {

    private Stage primaryStage;
    private Object account; // Accepts either User or Artist
    private BorderPane homeLayout;

    public HomePage(Stage primaryStage, Object account) {
        this.primaryStage = primaryStage;
        this.account = account;
        this.homeLayout = new BorderPane();
        setupHomePage();
    }

    public Scene getScene() {
        return new Scene(homeLayout, 800, 600);
    }

    private void setupHomePage() {
        // Header section
        VBox topSection = new VBox(10);
        topSection.setStyle("-fx-background-color: #f39c12; -fx-padding: 10px;");

        HBox header = new HBox(20);

        String name = (account instanceof User) ? ((User) account).getName() : ((Artist) account).getName();
        Label welcomeLabel = new Label("Welcome, " + name);
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

        Button profileButton = new Button("Your Profile");
        profileButton.setStyle("-fx-font-size: 16px; -fx-background-color: #e74c3c; -fx-text-fill: white;");
        profileButton.setOnAction(e -> {
            if (account instanceof Artist) {
                ArtistPage artistPage = new ArtistPage(primaryStage, (Artist) account);
                primaryStage.setScene(artistPage.getScene());
            } else {
                UserPage userPage = new UserPage(primaryStage, (User) account);
                primaryStage.setScene(new Scene(userPage.getUserLayout(), 800, 600));
            }
        });

        header.getChildren().addAll(welcomeLabel, profileButton);
        header.setAlignment(Pos.CENTER_RIGHT);

        // Search bar
        HBox searchBar = new HBox(10);
        TextField searchField = new TextField();
        searchField.setPromptText("Search for artist, song, or album...");
        searchField.setPrefWidth(300);

        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;");

        ListView<String> searchResults = new ListView<>();

        // Trigger search on button click or Enter key press
        searchButton.setOnAction(e -> performSearch(searchField.getText(), searchResults));
        searchField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                performSearch(searchField.getText(), searchResults);
            }
        });

        searchBar.getChildren().addAll(searchField, searchButton);
        searchBar.setAlignment(Pos.CENTER);

        topSection.getChildren().addAll(header, searchBar);

        // Body
        VBox body = new VBox(20);
        body.setStyle("-fx-padding: 20px;");
        body.getChildren().addAll(new Label("🔥 Trending Songs"), searchResults);

        homeLayout.setTop(topSection);
        homeLayout.setCenter(body);
        homeLayout.setStyle("-fx-background-color: #f1c40f;");

    }

    private void performSearch(String query, ListView<String> searchResults) {
        searchResults.getItems().clear();
        query = query.toLowerCase();

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
    }
}
