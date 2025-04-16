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
import org.example.musicapp.database.UserDatabase;

public class HomePage {

    private Stage primaryStage;
    private Object account;
    private BorderPane homeLayout;
    private ListView<String> searchResultsListView;

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
        VBox topSection = createTopSection();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(createBodySection());
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent;");

        homeLayout.setTop(topSection);
        homeLayout.setCenter(scrollPane);
        homeLayout.setStyle("-fx-background-color: #f1c40f;");
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
                : ((Admin) account).getName();
        Label welcomeLabel = new Label("Welcome, " + name);
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");

        Button profileButton = new Button("Your Profile");
        profileButton.setStyle("-fx-font-size: 16px; -fx-background-color: #e74c3c; -fx-text-fill: white; -fx-padding: 10px 20px;");
        profileButton.setOnAction(e -> navigateToProfilePage());

        Button logoutButton = new Button("Log Out");
        logoutButton.setStyle("-fx-font-size: 16px; -fx-background-color: #34495e; -fx-text-fill: white; -fx-padding: 10px 20px;");
        logoutButton.setOnAction(e -> navigateToLoginPage());

        header.getChildren().addAll(welcomeLabel, profileButton, logoutButton);
        return header;
    }

    private HBox createSearchBar() {
        HBox searchBar = new HBox(10);
        searchBar.setAlignment(Pos.CENTER);
        searchBar.setStyle("-fx-padding: 10px;");

        TextField searchField = new TextField();
        searchField.setPromptText("Search for artist, song, or album...");
        searchField.setPrefWidth(300);
        searchField.setStyle("-fx-padding: 5px; -fx-font-size: 14px;");

        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-font-size: 16px; -fx-background-color: #2ecc71; -fx-text-fill: white; -fx-padding: 10px 20px;");
        searchButton.setOnAction(e -> performSearch(searchField.getText()));
        searchField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                performSearch(searchField.getText());
            }
        });

        searchBar.getChildren().addAll(searchField, searchButton);
        return searchBar;
    }

    private VBox createBodySection() {
        VBox bodySection = new VBox(30);
        bodySection.setStyle("-fx-padding: 30px;");
        bodySection.setAlignment(Pos.TOP_CENTER);

        // 🔥 Trending Songs
        Label trendingLabel = new Label("🔥 Trending Songs");
        trendingLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        ListView<String> trendingSongsListView = new ListView<>();
        trendingSongsListView.setPrefHeight(120);
        MusicLibrary.getAllSongs().forEach(song -> trendingSongsListView.getItems().add(song.getTitle()));

        // 🔍 Search Results
        Label searchResultsLabel = new Label("🔍 Search Results");
        searchResultsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        searchResultsListView = new ListView<>();
        searchResultsListView.setPrefHeight(150);

        // 🎶 Follow Artists
        Label followArtistsLabel = new Label("🎶 Follow Artists");
        followArtistsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        ListView<Artist> artistListView = new ListView<>();
        artistListView.setPrefHeight(200);
        MusicLibrary.getAllArtists().forEach(artistListView.getItems()::add);

        artistListView.setCellFactory(param -> new ListCell<Artist>() {
            @Override
            protected void updateItem(Artist artist, boolean empty) {
                super.updateItem(artist, empty);
                if (empty || artist == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Button followButton = new Button("Follow");
                    followButton.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-font-size: 14px;");
                    followButton.setOnAction(e -> followArtist(artist));

                    Label artistLabel = new Label(artist.getName());
                    artistLabel.setStyle("-fx-font-size: 14px;");
                    HBox artistBox = new HBox(15, artistLabel, followButton);
                    artistBox.setAlignment(Pos.CENTER_LEFT);
                    setGraphic(artistBox);
                }
            }
        });

        bodySection.getChildren().addAll(
                trendingLabel, trendingSongsListView,
                searchResultsLabel, searchResultsListView,
                followArtistsLabel, artistListView
        );

        return bodySection;
    }

    private void followArtist(Artist artist) {
        if (account instanceof User) {
            User user = (User) account;
            if (!user.getFollowedArtists().contains(artist)) {
                user.getFollowedArtists().add(artist);
                UserDatabase.saveUserData(user);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "You are now following " + artist.getName(), ButtonType.OK);
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "You are already following " + artist.getName(), ButtonType.OK);
                alert.show();
            }
        }
    }

    private void performSearch(String query) {
        searchResultsListView.getItems().clear();
        query = query.toLowerCase();

        List<Artist> artists = MusicLibrary.getAllArtists();
        List<Album> albums = MusicLibrary.getAllAlbums();
        List<Song> songs = MusicLibrary.getAllSongs();

        for (Artist artist : artists) {
            if (artist.getName().toLowerCase().contains(query)) {
                searchResultsListView.getItems().add("Artist: " + artist.getName());
            }
        }

        for (Album album : albums) {
            if (album.getTitle().toLowerCase().contains(query)) {
                searchResultsListView.getItems().add("Album: " + album.getTitle());
            }
        }

        for (Song song : songs) {
            if (song.getTitle().toLowerCase().contains(query)) {
                searchResultsListView.getItems().add("Song: " + song.getTitle());
            }

            // اضافه کردن هنرمندان هر آهنگ
            List<Artist> songArtists = song.getArtists();
            for (Artist artist : songArtists) {
                if (artist.getName().toLowerCase().contains(query)) {
                    searchResultsListView.getItems().add("Artist in Song: " + artist.getName());
                }
            }
        }

        if (searchResultsListView.getItems().isEmpty()) {
            searchResultsListView.getItems().add("No results found.");
        }
    }

    private void navigateToProfilePage() {
        if (account instanceof Artist) {
            ArtistPage artistPage = new ArtistPage(primaryStage, (Artist) account);
            primaryStage.setScene(artistPage.getScene());
        } else if (account instanceof User) {
            List<Song> songs = MusicLibrary.getAllSongs();
            if (!songs.isEmpty()) {
                UserPage userPage = new UserPage(primaryStage, (User) account, songs);
                primaryStage.setScene(userPage.getScene());
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "No songs available to display in the profile page.", ButtonType.OK);
                alert.showAndWait();
            }
        } else if (account instanceof Admin) {
            AdminPage adminPage = new AdminPage(primaryStage, (Admin) account);
            primaryStage.setScene(adminPage.getScene());
        }
    }

    private void navigateToLoginPage() {
        LoginPage loginPage = new LoginPage(primaryStage);
        primaryStage.setScene(loginPage.getScene());
    }
}
