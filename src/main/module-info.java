module org.example.musicapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.musicapp to javafx.fxml;
    exports org.example.musicapp;
}