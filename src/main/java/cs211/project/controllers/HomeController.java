package cs211.project.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class HomeController {
    @FXML private AnchorPane navbarAnchorPane;
    @FXML private AnchorPane newEventTileAnchorPane1,newEventTileAnchorPane2,newEventTileAnchorPane3;
    @FXML private AnchorPane upEventTileAnchorPane1,upEventTileAnchorPane2,upEventTileAnchorPane3;

    @FXML
    private  void initialize() {

        loadNavbarComponent();
        loadEventTileComponent(newEventTileAnchorPane1);
        loadEventTileComponent(newEventTileAnchorPane2);
        loadEventTileComponent(newEventTileAnchorPane3);
        loadEventTileComponent(upEventTileAnchorPane1);
        loadEventTileComponent(upEventTileAnchorPane2);
        loadEventTileComponent(upEventTileAnchorPane3);
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

    private void loadEventTileComponent(AnchorPane eventTile) {
        FXMLLoader eventTileLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/event-tile.fxml"));
        try {
            AnchorPane eventTileComponent = eventTileLoader.load();
            eventTile.getChildren().add(eventTileComponent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
