package cs211.project.controllers;

import cs211.project.componentControllers.eventController;
import cs211.project.models.Event;
import cs211.project.models.User;
import cs211.project.models.collections.EventList;
import cs211.project.services.Datasource;
import cs211.project.services.EventDataSourceHardCode;
import cs211.project.services.FXRouter;
import cs211.project.services.LoadEventComponent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public class MyEventController {
    @FXML
    AnchorPane navbarAnchorPane;
    @FXML
    Label nameLabel;
    @FXML
    ImageView userImageView;
    @FXML
    ListView myeventListView;


    private EventList eventList;
    private Datasource<EventList> datasource;
    private User user = (User) FXRouter.getData();


    @FXML
    public void initialize() {
        loadTopBarComponent();

        EventDataSourceHardCode datasource = new EventDataSourceHardCode();

        if (user != null){
            System.out.println("Test user");
        }

        eventList = datasource.readData();
        int eventListSize = eventList.getEvents().size();


        for (int i = 0; i < eventListSize; i++) {
            Event event = (Event) eventList.getEvents().get(i);
            System.out.println(event.toString());
            if (event != null) {
                AnchorPane anchorPane = new AnchorPane();
                new LoadEventComponent(event, anchorPane);
                myeventListView.getItems().add(anchorPane);
            }
        }
    }


    // top-bar
    private void loadTopBarComponent() {
        FXMLLoader topBarComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/navbar.fxml"));
        try {
            AnchorPane topBarComponent = topBarComponentLoader.load();
            navbarAnchorPane.getChildren().add(topBarComponent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
