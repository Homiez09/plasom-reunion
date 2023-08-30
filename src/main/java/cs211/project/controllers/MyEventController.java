package cs211.project.controllers;

import cs211.project.componentControllers.EventComponentController;
import cs211.project.models.Event;
import cs211.project.models.Team;
import cs211.project.models.User;
import cs211.project.models.collections.EventList;
import cs211.project.services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;


import java.io.IOException;

public class MyEventController {
    @FXML
    AnchorPane navbarAnchorPane;
    @FXML
    Label nameLabel;
    @FXML
    ImageView userImageView;
    @FXML
    ListView myeventListView,historyeventListView;
    @FXML
    Button currentButton;


    private EventList eventList ;
    private Datasource<EventList> datasource;
    private User currentUser = (User) FXRouter.getData();



    @FXML
    public void initialize() {
        new LoadNavbarComponent(currentUser, navbarAnchorPane);
        showInfo();
        EventDataSourceHardCode datasource = new EventDataSourceHardCode();
        eventList = datasource.readData();

        for (Event event : eventList.getEvents()) {
            try {
                FXMLLoader eventComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/event-component.fxml"));
                AnchorPane eventAnchorPaneComponent = eventComponentLoader.load();
                EventComponentController eventComponent = eventComponentLoader.getController();
                eventComponent.setEventData(event);

                if (event.isEnd()) {
                    historyeventListView.getItems().add(eventAnchorPaneComponent);
                }else {
                    myeventListView.getItems().add(eventAnchorPaneComponent);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


   }
    private void showInfo(){
        userImageView.setImage(new Image(getClass().getResource(currentUser.getImagePath()).toExternalForm()));
        nameLabel.setText(currentUser.getDisplayName());

    }


    public void onEditAction(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("user-profile",currentUser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onBackAction(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("home",currentUser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onCreateAction(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("create-event",currentUser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onHistoryAction(ActionEvent actionEvent) {
        currentButton.setVisible(true);
        myeventListView.setVisible(false);
        historyeventListView.setVisible(true);
    }

    public void onCurrentAction(ActionEvent actionEvent) {
        currentButton.setVisible(false);
        historyeventListView.setVisible(false);
        myeventListView.setVisible(true);

    }
}
