package org.example.musicapp.views;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Parent;  // Import Parent class
import org.example.musicapp.models.Artist;
import org.example.musicapp.models.Song;
import java.util.Arrays;
import java.util.List;

public class CreateSinglePage {

    private Stage primaryStage;
    private Artist artist;
    private VBox createSingleLayout;

    public CreateSinglePage(Stage primaryStage, Artist artist) {
        this.primaryStage = primaryStage;
        this.artist = artist;
        this.createSingleLayout = new VBox(20);
        setupCreateSinglePage();
    }

    public Scene getScene() {
        return new Scene(createSingleLayout, 800, 600);
    }

    public Parent getCreateSingleLayout() {
        return createSingleLayout;  // Return the layout as Parent
    }

    private void setupCreateSinglePage() {
        // Header - Create Single
        Label createSingleLabel = new Label("Create a New Single");
        createSingleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Song Title field
        Label titleLabel = new Label("Song Title:");
        TextField titleField = new TextField();
        titleField.setPromptText("Enter the title of the single");

        // Song Lyrics field
        Label lyricsLabel = new Label("Song Lyrics:");
        TextArea lyricsArea = new TextArea();
        lyricsArea.setPromptText("Enter the lyrics");

        // Song Genre field
        Label genreLabel = new Label("Song Genre:");
        TextField genreField = new TextField();
        genreField.setPromptText("Enter the genre");

        // Song Tags field
        Label tagsLabel = new Label("Song Tags (comma separated):");
        TextField tagsField = new TextField();
        tagsField.setPromptText("Enter tags");

        // Song Release Date field
        Label releaseDateLabel = new Label("Release Date:");
        TextField releaseDateField = new TextField();
        releaseDateField.setPromptText("Enter release date");

        // Add Song Button
        Button addSongButton = new Button("Create Song");
        addSongButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        addSongButton.setOnAction(e -> {
            String title = titleField.getText();
            String lyrics = lyricsArea.getText();
            String genre = genreField.getText();
            String releaseDate = releaseDateField.getText();

            // Parse tags as a list of strings
            List<String> tags = Arrays.asList(tagsField.getText().split(","));

            if (!title.isEmpty() && !lyrics.isEmpty() && !genre.isEmpty() && !releaseDate.isEmpty()) {
                // Call the createSingle method from Artist
                artist.createSingle(title, lyrics, genre, tags, releaseDate);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Song created successfully!", ButtonType.OK);
                alert.showAndWait();
            }
        });

        // Layout organization
        createSingleLayout.getChildren().addAll(
                createSingleLabel,
                titleLabel,
                titleField,
                lyricsLabel,
                lyricsArea,
                genreLabel,
                genreField,
                tagsLabel,
                tagsField,
                releaseDateLabel,
                releaseDateField,
                addSongButton
        );

        createSingleLayout.setAlignment(Pos.TOP_CENTER);
        createSingleLayout.setStyle("-fx-background-color: #f8f8f8; -fx-padding: 20px;");
    }
}
