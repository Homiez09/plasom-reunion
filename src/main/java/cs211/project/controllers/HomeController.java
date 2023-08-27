package cs211.project.controllers;

import cs211.project.models.User;
import cs211.project.models.collections.UserList;
import cs211.project.services.FXRouter;
import cs211.project.services.LoadNavbarComponent;
import cs211.project.services.UserDataSourceHardCode;
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
        new LoadNavbarComponent(navbarAnchorPane);
        loadEventTileComponent(newEventTileAnchorPane1);
        loadEventTileComponent(newEventTileAnchorPane2);
        loadEventTileComponent(newEventTileAnchorPane3);
        loadEventTileComponent(upEventTileAnchorPane1);
        loadEventTileComponent(upEventTileAnchorPane2);
        loadEventTileComponent(upEventTileAnchorPane3);
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
