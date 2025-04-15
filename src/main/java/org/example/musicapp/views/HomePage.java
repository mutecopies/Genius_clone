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
import org.example.musicapp.models.Admin;
import org.example.musicapp.models.Song;
import org.example.musicapp.models.Album;
import org.example.musicapp.utils.MusicLibrary;

public class HomePage {

    private Stage primaryStage;
    private Object account; // Accepts User, Artist, or Admin
    private BorderPane homeLayout;

    public HomePage(Stage primaryStage, Object account) {
        this.primaryStage = primaryStage;
        this.account = account;
        this.homeLayout = new BorderPane();
        initializeHomePage();
    }

    public Scene getScene() {
        return new Scene(homeLayout, 800, 600);
    }

    private void initializeHomePage() {
        // Top Section (Header + Search Bar)
        VBox topSection = createTopSection();

        // Body Section (Trending Songs + Search Results)
        VBox bodySection = createBodySection();

        // Setup the layout
        homeLayout.setTop(topSection);
        homeLayout.setCenter(bodySection);
        homeLayout.setStyle("-fx-background-color: #f1c40f;"); // Background color for the page
    }

    private VBox createTopSection() {
        VBox topSection = new VBox(10);
        topSection.setStyle("-fx-background-color: #f39c12; -fx-padding: 10px;");

        HBox header = createHeader();

        HBox searchBar = createSearchBar();

        topSection.getChildren().addAll(header, searchBar);
        return topSection;
    }

    private HBox createHeader() {
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_RIGHT);

        String name = (account instanceof User) ? ((User) account).getName()
                : (account instanceof Artist) ? ((Artist) account).getName()
                : ((Admin) account).getName(); // Handle Admin here
        Label welcomeLabel = new Label("Welcome, " + name);
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

        Button profileButton = new Button("Your Profile");
        styleButton(profileButton, "#e74c3c");
        profileButton.setOnAction(e -> navigateToProfilePage());

        Button logoutButton = new Button("Log Out");
        styleButton(logoutButton, "#34495e");
        logoutButton.setOnAction(e -> navigateToLoginPage());

        header.getChildren().addAll(welcomeLabel, profileButton, logoutButton);
        return header;
    }

    private void styleButton(Button button, String color) {
        button.setStyle("-fx-font-size: 16px; -fx-background-color: " + color + "; -fx-text-fill: white;");
    }

    private void navigateToProfilePage() {
        if (account instanceof Artist) {
            ArtistPage artistPage = new ArtistPage(primaryStage, (Artist) account);
            primaryStage.setScene(artistPage.getScene());
        } else if (account instanceof User) {
            UserPage userPage = new UserPage(primaryStage, (User) account);
            primaryStage.setScene(new Scene(userPage.getUserLayout(), 800, 600));
        } else if (account instanceof Admin) {
            AdminPage adminPage = new AdminPage(primaryStage, (Admin) account); // Admin page navigation
            primaryStage.setScene(adminPage.getScene());
        }
    }

    private void navigateToLoginPage() {
        LoginPage loginPage = new LoginPage(primaryStage);
        primaryStage.setScene(loginPage.getScene());
    }

    private HBox createSearchBar() {
        HBox searchBar = new HBox(10);
        searchBar.setAlignment(Pos.CENTER);

        TextField searchField = new TextField();
        searchField.setPromptText("Search for artist, song, or album...");
        searchField.setPrefWidth(300);

        Button searchButton = new Button("Search");
        styleButton(searchButton, "#2ecc71");

        ListView<String> searchResults = new ListView<>();

        // Trigger search on button click or Enter key press
        searchButton.setOnAction(e -> performSearch(searchField.getText(), searchResults));
        searchField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                performSearch(searchField.getText(), searchResults);
            }
        });

        searchBar.getChildren().addAll(searchField, searchButton);
        return searchBar;
    }

    private VBox createBodySection() {
        VBox bodySection = new VBox(20);
        bodySection.setStyle("-fx-padding: 20px;");

        Label trendingLabel = new Label("🔥 Trending Songs");
        trendingLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // ListView to display trending songs
        ListView<String> trendingSongsListView = new ListView<>();
        trendingSongsListView.setPrefHeight(200);

        // Fetch trending songs and display them
        List<Song> trendingSongs = MusicLibrary.getAllSongs();  // Get trending songs
        for (Song song : trendingSongs) {
            trendingSongsListView.getItems().add(song.getTitle());
        }

        bodySection.getChildren().addAll(trendingLabel, trendingSongsListView);

        return bodySection;
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
