module com.example.cargame {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.media;

    opens com.example.cargame to javafx.fxml;
    exports com.example.cargame;
}