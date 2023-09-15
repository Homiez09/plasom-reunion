package cs211.project.controllers;

import cs211.project.models.Event;
import cs211.project.models.User;
import cs211.project.models.collections.EventList;
import cs211.project.services.Datasource;
import cs211.project.services.EventListDataSource;
import cs211.project.services.FXRouter;
import cs211.project.services.LoadNavbarComponent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class HostEventController {
    @FXML
    private AnchorPane navbarAnchorPane;
    private User currentUser;
    private Datasource<EventList> eventDatasource;
    private EventList eventList;
    @FXML
    public void initialize() {
        this.currentUser = (User) FXRouter.getData();
        this.eventDatasource = new EventListDataSource("data","event-list.csv");
        this.eventList = eventDatasource.readData();
        new LoadNavbarComponent(currentUser, navbarAnchorPane);

    }
    public void onBackAction(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("home");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onCreateAction(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("create-event");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onDeleteAction(ActionEvent actionEvent) {
    }

    public void onHistorysAction(ActionEvent actionEvent) {
    }
}
