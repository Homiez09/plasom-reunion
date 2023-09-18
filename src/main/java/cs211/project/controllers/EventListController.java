package cs211.project.controllers;

import cs211.project.componentControllers.CardEventController;
import cs211.project.models.*;
import cs211.project.models.collections.*;
import cs211.project.services.*;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;


import java.io.IOException;
import java.util.List;

public class EventListController {
    @FXML
    AnchorPane navbarAnchorPane;
    @FXML
    HBox eventListHbox,eventListDownHbox;
    private User currentUser = (User) FXRouter.getData();
    private Datasource<EventList> eventListDatasource;
    private EventList eventList;

    private int currentIndexOfUp = 0;
    @FXML
    private void initialize() {
        new LoadNavbarComponent(currentUser, navbarAnchorPane);
        this.eventListDatasource = new EventListDataSource("data","event-list.csv");
        this.eventList = eventListDatasource.readData();

        // โหลดข้อมูลใน eventListHbox
        loadEventList(eventListHbox, eventList.getEvents().subList(0, Math.min(eventList.getEvents().size(), 3)));

        // โหลดข้อมูลใน eventListDownHbox
        loadEventList(eventListDownHbox, eventList.getEvents().subList(3, eventList.getEvents().size()));
    }
    private void loadEventList(HBox hbox, List<Event> events) {
        try {
            for (Event event : events) {
                Separator separator = new Separator();
                separator.setOrientation(Orientation.VERTICAL);
                separator.setOpacity(0.0);
                separator.setPrefWidth(36.0);

                AnchorPane anchorPane = new AnchorPane();
                loadCardEvent(anchorPane, event);
                hbox.getChildren().add(separator);
                hbox.getChildren().add(anchorPane);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException(e);
        }
    }


    private void loadCardEvent(AnchorPane anchorPane,Event event){
        try {
            FXMLLoader eventTileLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/card-event.fxml"));
            AnchorPane load = eventTileLoader.load();
            CardEventController cardEventController = eventTileLoader.getController();
            cardEventController.setEvent(event);

            anchorPane.getChildren().setAll(load);
            AnimateComponent(load);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onTopLButton(ActionEvent actionEvent) {
    }

    public void onTopRButton(ActionEvent actionEvent) {
    }

    public void onBotLButton(ActionEvent actionEvent) {
    }

    public void onBotRButton(ActionEvent actionEvent) {
    }


    //fliter
    public void onAllClick(ActionEvent actionEvent) {
    }

    public void onNewClick(ActionEvent actionEvent) {
    }

    public void onUpClick(ActionEvent actionEvent) {
    }
    public void onCreateClick(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("create-event",currentUser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //-------------Animate Zoom for any AnchorPane-------------\\
    private void AnimateComponent(AnchorPane anchorPane) {
        ScaleTransition scaleIn = new ScaleTransition(Duration.seconds(0.2), anchorPane);
        scaleIn.setToX(1.1);
        scaleIn.setToY(1.1);
        ScaleTransition scaleOut = new ScaleTransition(Duration.seconds(0.2), anchorPane);
        scaleOut.setToX(1);
        scaleOut.setToY(1);
        anchorPane.setOnMouseEntered(event -> {scaleIn.play();});
        anchorPane.setOnMouseExited(event -> {scaleOut.play();});
    }


    //-------------Animate Zoom for any AnchorPane-------------\\

}
