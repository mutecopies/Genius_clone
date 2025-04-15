package org.example.musicapp.views;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import org.example.musicapp.models.Artist;
import org.example.musicapp.models.Song;
import org.example.musicapp.models.TrendingData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateSongPage {

    private Stage primaryStage;
    private Artist artist;
    private VBox createSongLayout;

    public CreateSongPage(Stage primaryStage, Artist artist) {
        this.primaryStage = primaryStage;
        this.artist = artist;
        this.createSongLayout = new VBox(20);
        setupCreateSongPage();
    }

    public Scene getScene() {
        return new Scene(createSongLayout, 800, 600);
    }

    private void setupCreateSongPage() {
        // Back Button to Artist Page
        Button backButton = new Button("← Back");
        backButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #3498db; -fx-font-size: 14px;");
        backButton.setOnAction(e -> {
            // Navigate back to the Artist Page
            ArtistPage artistPage = new ArtistPage(primaryStage, artist);
            Scene artistPageScene = artistPage.getScene();
            primaryStage.setScene(artistPageScene);
        });

        HBox backButtonBox = new HBox(backButton);
        backButtonBox.setAlignment(Pos.TOP_LEFT);
        backButtonBox.setPadding(new Insets(10, 0, 0, 10));

        // Song Title Input
        Label titleLabel = new Label("Song Title:");
        TextField titleTextField = new TextField();
        titleTextField.setPromptText("Enter song title");

        // Lyrics Input
        Label lyricsLabel = new Label("Song Lyrics:");
        TextArea lyricsTextArea = new TextArea();
        lyricsTextArea.setPromptText("Enter lyrics");

        // Genre Selection
        Label genreLabel = new Label("Genre:");
        ComboBox<String> genreComboBox = new ComboBox<>();
        genreComboBox.getItems().addAll("Pop", "Rock", "Hip-hop", "Jazz", "Classical", "Other");
        genreComboBox.setValue("Pop");

        // Tags Input
        Label tagsLabel = new Label("Tags (comma-separated):");
        TextField tagsTextField = new TextField();
        tagsTextField.setPromptText("Enter tags");

        // Release Date Input
        Label releaseDateLabel = new Label("Release Date:");
        DatePicker releaseDatePicker = new DatePicker();

        // Save Button to create song
        Button saveButton = new Button("Save Song");
        saveButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        saveButton.setOnAction(e -> {
            String title = titleTextField.getText();
            String lyrics = lyricsTextArea.getText();
            String genre = genreComboBox.getValue();
            List<String> tags = new ArrayList<>(Arrays.asList(tagsTextField.getText().split(",")));
            String releaseDate = releaseDatePicker.getValue().toString();

            // Create new song with the updated constructor
            Song newSong = new Song(title, lyrics, new ArrayList<>(List.of(artist)), genre, tags, releaseDate);

            // Add the song to the artist's list of songs (You may need to implement addSong method in Artist class)
            artist.addSong(newSong);

            // Add the song to the trending list
            TrendingData.addSongToTrending(newSong);

            // Display a confirmation message or navigate back to Artist Page
            System.out.println("New song created: " + newSong.getTitle());

            // After saving, navigate back to the Artist Page
            ArtistPage artistPage = new ArtistPage(primaryStage, artist);
            Scene artistPageScene = artistPage.getScene();
            primaryStage.setScene(artistPageScene);
        });

        // Layout for creating song
        createSongLayout.getChildren().addAll(
                backButtonBox,
                titleLabel, titleTextField,
                lyricsLabel, lyricsTextArea,
                genreLabel, genreComboBox,
                tagsLabel, tagsTextField,
                releaseDateLabel, releaseDatePicker,
                saveButton
        );

        createSongLayout.setAlignment(Pos.TOP_CENTER);
        createSongLayout.setStyle("-fx-background-color: #f8f8f8; -fx-padding: 20px;");
    }
}
