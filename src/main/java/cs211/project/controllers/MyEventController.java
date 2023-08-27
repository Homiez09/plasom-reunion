package cs211.project.controllers;

import cs211.project.models.Event;
import cs211.project.models.collections.EventList;
import cs211.project.services.Datasource;
import cs211.project.services.EventDataSourceHardCode;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MyEventController {
    @FXML
    AnchorPane navbarAnchorPane;
    @FXML
    Label usernameLabel;
    @FXML
    ImageView userImageView;
    @FXML
    ListView myeventListView;

    private EventList eventList;
    private Event selectEvent;
    private Datasource<EventList> datasourcecsv;


    @FXML
    public void initialize(){
        loadTopBarComponent();
        EventDataSourceHardCode datasource = new EventDataSourceHardCode();
        eventList = datasource.readData();
        showlist(eventList);
        myeventListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Event>() {
            @Override
            public void changed(ObservableValue<? extends Event> observableValue, Event oldValue, Event newValue) {
                if (newValue == null){
                    selectEvent = null;
                }else {
                    selectEvent = newValue;
                }
            }
        });


    }

    private void showlist(EventList eventList){
        loadTopBarComponent();
        myeventListView.getItems().clear();
        myeventListView.getItems().addAll(eventList.getEvents());

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
