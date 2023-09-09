package cs211.project.controllers;

import cs211.project.componentControllers.EventTileController;
import cs211.project.models.Event;
import cs211.project.models.User;
import cs211.project.models.collections.EventList;
import cs211.project.services.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class HomeController {
    @FXML private AnchorPane navbarAnchorPane;
    @FXML private AnchorPane newEventTileAnchorPane1,newEventTileAnchorPane2,newEventTileAnchorPane3;
    @FXML private AnchorPane upEventTileAnchorPane1,upEventTileAnchorPane2,upEventTileAnchorPane3;

    private User user = (User) FXRouter.getData();
    private Datasource<EventList> datasource;
    private EventList eventList;

    @FXML
    private  void initialize() {
        datasource = new EventListDataSource("data","event-list.csv");
        eventList = datasource.readData();
        new LoadNavbarComponent(user, navbarAnchorPane);
        if (eventList != null) {
            for (int i = 0 ; i < eventList.getEvents().size() - 5 ;i++) {
                try {
                    loadEventTileComponent(newEventTileAnchorPane1, eventList.getEvents().get(i));
                    loadEventTileComponent(newEventTileAnchorPane2, eventList.getEvents().get(i+1));
                    loadEventTileComponent(newEventTileAnchorPane3, eventList.getEvents().get(i+2));
                    loadEventTileComponent(upEventTileAnchorPane1, eventList.getEvents().get(i+3));
                    loadEventTileComponent(upEventTileAnchorPane2, eventList.getEvents().get(i+4));
                    loadEventTileComponent(upEventTileAnchorPane3, eventList.getEvents().get(i+5));
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

            }

        }

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
