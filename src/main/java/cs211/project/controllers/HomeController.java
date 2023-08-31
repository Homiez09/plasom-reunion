package cs211.project.controllers;

import cs211.project.componentControllers.EventTileController;
import cs211.project.models.Event;
import cs211.project.models.User;
import cs211.project.models.collections.EventList;
import cs211.project.services.EventDataSourceHardCode;
import cs211.project.services.FXRouter;
import cs211.project.services.LoadNavbarComponent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class HomeController {
    @FXML private AnchorPane navbarAnchorPane;
    @FXML private AnchorPane newEventTileAnchorPane1,newEventTileAnchorPane2,newEventTileAnchorPane3;
    @FXML private AnchorPane upEventTileAnchorPane1,upEventTileAnchorPane2,upEventTileAnchorPane3;

    private User user = (User) FXRouter.getData();
    EventDataSourceHardCode datasource = new EventDataSourceHardCode();
    private EventList eventList = datasource.readData();

    @FXML
    private  void initialize() {
        new LoadNavbarComponent(user, navbarAnchorPane);
        loadEventTileComponent(newEventTileAnchorPane1,eventList.getIndex(0));
        loadEventTileComponent(newEventTileAnchorPane2,eventList.getIndex(1));
        loadEventTileComponent(newEventTileAnchorPane3,eventList.getIndex(2));
        loadEventTileComponent(upEventTileAnchorPane1,eventList.getIndex(3));
        loadEventTileComponent(upEventTileAnchorPane2,eventList.getIndex(4));
        loadEventTileComponent(upEventTileAnchorPane3,eventList.getIndex(5));
    }



    private void loadEventTileComponent(AnchorPane eventTile, Event event) {
        FXMLLoader eventTileLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/event-tile.fxml"));
        try {
            AnchorPane eventTileComponent = eventTileLoader.load();
            EventTileController eventTileController = eventTileLoader.getController();
            eventTileController.showEventTile(event);
            eventTile.getChildren().add(eventTileComponent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
