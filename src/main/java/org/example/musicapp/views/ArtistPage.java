package org.example.musicapp.views;

import javafx.scene.control.Label;  // Import Label
import javafx.scene.layout.VBox;    // Import VBox
import javafx.stage.Stage;

public class ArtistPage {

    private Stage primaryStage;
    private VBox artistLayout;

    public ArtistPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.artistLayout = new VBox(15);
        setupArtistPage();
    }

    public VBox getArtistLayout() {
        return artistLayout;
    }

    private void setupArtistPage() {
        // Add a label to the layout
        artistLayout.getChildren().add(new Label("Welcome to the Artist Page!"));
        // Add more components here (like managing songs, albums, etc.)
    }
}
