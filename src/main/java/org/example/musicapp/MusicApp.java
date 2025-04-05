import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;

public class MusicApp extends Application {

    // Sample data for Songs and Artists
    private ObservableList<Song> songs = FXCollections.observableArrayList(
            new Song("Song 1", 100),
            new Song("Song 2", 250),
            new Song("Song 3", 150)
    );

    private ObservableList<Artist> followedArtists = FXCollections.observableArrayList(
            new Artist("Artist 1"),
            new Artist("Artist 2")
    );

    private ObservableList<Album> albums = FXCollections.observableArrayList(
            new Album("Album 1", "2024-01-01", FXCollections.observableArrayList(
                    new Song("Song 1", 100),
                    new Song("Song 2", 150)
            ))
    );

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Account Page (Styled)
        Button goToAlbumButton = new Button("Go to Album Page");
        Button goToSongButton = new Button("Go to Song Page");
        Button goToChartButton = new Button("View Top Songs");
        Button goToFollowedArtistsButton = new Button("View Followed Artists");

        // Style Buttons
        styleButton(goToAlbumButton);
        styleButton(goToSongButton);
        styleButton(goToChartButton);
        styleButton(goToFollowedArtistsButton);

        // Add welcome message
        Label welcomeLabel = new Label("Welcome to the Music App");
        welcomeLabel.setFont(Font.font("Arial", 24));
        welcomeLabel.setTextFill(Color.web("#3498db"));

        // Navigation Buttons for the Account Page
        VBox accountPageLayout = new VBox(15, welcomeLabel, goToAlbumButton, goToSongButton, goToChartButton, goToFollowedArtistsButton);
        accountPageLayout.setPrefSize(400, 250);
        accountPageLayout.setAlignment(Pos.CENTER);
        accountPageLayout.setStyle("-fx-background-color: #ecf0f1;");

        Scene accountPageScene = new Scene(accountPageLayout);

        // Event Handlers for Button Actions
        goToAlbumButton.setOnAction(e -> switchSceneToAlbumPage(primaryStage));
        goToSongButton.setOnAction(e -> switchSceneToSongPage(primaryStage));
        goToChartButton.setOnAction(e -> switchSceneToChartPage(primaryStage));
        goToFollowedArtistsButton.setOnAction(e -> switchSceneToFollowedArtistsPage(primaryStage));

        primaryStage.setScene(accountPageScene);
        primaryStage.setTitle("Music Application");
        primaryStage.show();
    }

    private void switchSceneToAlbumPage(Stage stage) {
        // Styled Album Page
        GridPane albumGrid = new GridPane();
        albumGrid.setHgap(20);
        albumGrid.setVgap(20);
        albumGrid.setStyle("-fx-background-color: #f1c40f;");
        albumGrid.setAlignment(Pos.CENTER);

        int row = 0;
        for (Album album : albums) {
            Button albumButton = new Button(album.getTitle());
            albumButton.setPrefWidth(200);
            albumButton.setStyle("-fx-font-size: 16px; -fx-background-color: #2980b9; -fx-text-fill: white;");
            albumButton.setOnAction(e -> showAlbumDetails(stage, album));

            albumGrid.add(albumButton, 0, row);
            row++;
        }

        Scene albumPageScene = new Scene(albumGrid, 400, 300);
        stage.setScene(albumPageScene);
    }

    private void showAlbumDetails(Stage stage, Album album) {
        // Styled Album Details
        Label albumLabel = new Label("Album: " + album.getTitle());
        albumLabel.setFont(Font.font("Arial", 22));
        albumLabel.setTextFill(Color.web("#2c3e50"));

        Label releaseDateLabel = new Label("Release Date: " + album.getReleaseDate());
        releaseDateLabel.setFont(Font.font("Arial", 16));

        ListView<Song> songListView = new ListView<>(album.getSongs());
        songListView.setStyle("-fx-font-size: 16px;");
        songListView.setOnMouseClicked(e -> {
            Song selectedSong = songListView.getSelectionModel().getSelectedItem();
            showSongDetails(stage, selectedSong);
        });

        VBox albumDetailsLayout = new VBox(15, albumLabel, releaseDateLabel, songListView);
        albumDetailsLayout.setAlignment(Pos.CENTER);
        albumDetailsLayout.setStyle("-fx-background-color: #ecf0f1;");

        Scene albumDetailsScene = new Scene(albumDetailsLayout, 500, 400);
        stage.setScene(albumDetailsScene);
    }

    private void switchSceneToSongPage(Stage stage) {
        // Styled Song Page
        ListView<Song> songListView = new ListView<>(songs);
        songListView.setStyle("-fx-font-size: 16px;");
        songListView.setOnMouseClicked(e -> {
            Song selectedSong = songListView.getSelectionModel().getSelectedItem();
            showSongDetails(stage, selectedSong);
        });

        VBox songPageLayout = new VBox(15, new Label("Songs List"));
        songPageLayout.setAlignment(Pos.CENTER);
        songPageLayout.setStyle("-fx-background-color: #ecf0f1;");
        songPageLayout.getChildren().add(songListView);

        Scene songPageScene = new Scene(songPageLayout, 500, 400);
        stage.setScene(songPageScene);
    }

    private void showSongDetails(Stage stage, Song song) {
        // Styled Song Details
        Label songLabel = new Label("Song: " + song.getTitle());
        songLabel.setFont(Font.font("Arial", 22));
        songLabel.setTextFill(Color.web("#2c3e50"));

        Label viewCountLabel = new Label("Views: " + song.getViewCount());
        viewCountLabel.setFont(Font.font("Arial", 16));

        VBox songDetailsLayout = new VBox(15, songLabel, viewCountLabel);
        songDetailsLayout.setAlignment(Pos.CENTER);
        songDetailsLayout.setStyle("-fx-background-color: #ecf0f1;");

        Scene songDetailsScene = new Scene(songDetailsLayout, 400, 300);
        stage.setScene(songDetailsScene);
    }

    private void switchSceneToChartPage(Stage stage) {
        // Styled Chart Page
        ListView<Song> chartListView = new ListView<>(songs);
        chartListView.setStyle("-fx-font-size: 16px;");
        chartListView.setCellFactory(param -> new ListCell<Song>() {
            @Override
            protected void updateItem(Song item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getTitle() + " - " + item.getViewCount() + " views");
                }
            }
        });

        VBox chartPageLayout = new VBox(15, new Label("Top Songs Chart"));
        chartPageLayout.setAlignment(Pos.CENTER);
        chartPageLayout.setStyle("-fx-background-color: #ecf0f1;");
        chartPageLayout.getChildren().add(chartListView);

        Scene chartPageScene = new Scene(chartPageLayout, 500, 400);
        stage.setScene(chartPageScene);
    }

    private void switchSceneToFollowedArtistsPage(Stage stage) {
        // Styled Followed Artists Page
        ListView<Artist> artistListView = new ListView<>(followedArtists);
        artistListView.setStyle("-fx-font-size: 16px;");
        artistListView.setOnMouseClicked(e -> {
            Artist selectedArtist = artistListView.getSelectionModel().getSelectedItem();
            showFollowedArtistDetails(stage, selectedArtist);
        });

        VBox followedArtistsPageLayout = new VBox(15, new Label("Followed Artists"));
        followedArtistsPageLayout.setAlignment(Pos.CENTER);
        followedArtistsPageLayout.setStyle("-fx-background-color: #ecf0f1;");
        followedArtistsPageLayout.getChildren().add(artistListView);

        Scene followedArtistsPageScene = new Scene(followedArtistsPageLayout, 500, 400);
        stage.setScene(followedArtistsPageScene);
    }

    private void showFollowedArtistDetails(Stage stage, Artist artist) {
        // Show New Releases by Artist (Styled)
        Label artistLabel = new Label("Artist: " + artist.getName());
        artistLabel.setFont(Font.font("Arial", 22));
        artistLabel.setTextFill(Color.web("#2c3e50"));

        ListView<Song> newReleasesListView = new ListView<>(songs); // Replace with actual new releases by artist

        VBox followedArtistDetailsLayout = new VBox(15, artistLabel, newReleasesListView);
        followedArtistDetailsLayout.setAlignment(Pos.CENTER);
        followedArtistDetailsLayout.setStyle("-fx-background-color: #ecf0f1;");

        Scene followedArtistDetailsScene = new Scene(followedArtistDetailsLayout, 500, 400);
        stage.setScene(followedArtistDetailsScene);
    }

    // Helper Method to Style Buttons
    private void styleButton(Button button) {
        button.setStyle("-fx-font-size: 16px; -fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 10px;");
    }

    // Model Classes
    public static class Song {
        private String title;
        private int viewCount;

        public Song(String title, int viewCount) {
            this.title = title;
            this.viewCount = viewCount;
        }

        public String getTitle() {
            return title;
        }

        public int getViewCount() {
            return viewCount;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    public static class Album {
        private String title;
        private String releaseDate;
        private ObservableList<Song> songs;

        public Album(String title, String releaseDate, ObservableList<Song> songs) {
            this.title = title;
            this.releaseDate = releaseDate;
            this.songs = songs;
        }

        public String getTitle() {
            return title;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public ObservableList<Song> getSongs() {
            return songs;
        }
    }

    public static class Artist {
        private String name;

        public Artist(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
