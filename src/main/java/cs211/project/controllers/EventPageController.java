package cs211.project.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class EventPageController {
    @FXML
    private AnchorPane navbarAnchorPane;
    @FXML private void initialize() {
        loadNavbarComponent();
    }

    private void loadNavbarComponent() {
        FXMLLoader navbarComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/navbar.fxml"));
        try {
            AnchorPane navbarComponent = navbarComponentLoader.load();
            navbarAnchorPane.getChildren().add(navbarComponent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
