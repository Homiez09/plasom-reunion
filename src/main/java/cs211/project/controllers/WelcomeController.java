package cs211.project.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class WelcomeController {
    @FXML private AnchorPane topBarAnchorPane;

    @FXML private void initialize() {
        loadTopBarComponent();
    }

    private void loadTopBarComponent() {
        FXMLLoader topBarComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/topbar.fxml"));
        try {
            AnchorPane topBarComponent = topBarComponentLoader.load();
            topBarAnchorPane.getChildren().add(topBarComponent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
