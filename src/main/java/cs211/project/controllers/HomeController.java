package cs211.project.controllers;

import cs211.project.componentControllers.EventTileController;
import cs211.project.models.Activity;
import cs211.project.models.Event;
import cs211.project.models.User;
import cs211.project.models.collections.ActivityList;
import cs211.project.models.collections.EventList;
import cs211.project.services.*;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ScrollPane;
import javafx.util.Duration;

import java.io.IOException;

public class HomeController {
    @FXML private AnchorPane navbarAnchorPane;
    @FXML private AnchorPane upLeftAnchorPane, upRightAnchorPane, upCenterAnchorPane;
    @FXML private AnchorPane newLeftAnchorPane, newRightAnchorPane, newCenterAnchorPane;
    @FXML private ScrollPane newEventScrollPane;
    @FXML private Button newLeftButton, newRightButton,upLeftButton,upRightButton;
    private User user = (User) FXRouter.getData();
    private Datasource<EventList> datasource;
    private Datasource<ActivityList> activityListDatasource;
    private EventList eventList;
    private ActivityList activityList;
    private int currentIndexOfUp = 1;
    private int currnetIndexOfNew = 1;

    @FXML
    private void initialize() {
        datasource = new EventListDataSource("data", "event-list.csv");
        eventList = datasource.readData();
        activityListDatasource = new ActivityListDataSource("data","activity-list.csv");
        activityList = activityListDatasource.readData();
        for (Activity activity : activityList.getActivities()) {
            if (eventList.findEvent(activity.getEventID()) != null) {
                if(eventList.findEvent(activity.getEventID()).getActivities() == null) {}
                eventList.findEvent(activity.getEventID()).getActivities().addActivity(activity);
            }
        }
        new LoadNavbarComponent(user, navbarAnchorPane);
        updateButtonState();

        try {
            //if newEvent
            if (eventList != null && !eventList.getEvents().isEmpty() && currnetIndexOfNew >= 1) {
                loadOldEventTile(newLeftAnchorPane,currnetIndexOfNew);
                loadCurrentEventTile(newCenterAnchorPane,eventList.getEvents().get(currnetIndexOfNew));
                loadNextEventTile(newRightAnchorPane,currnetIndexOfNew);
            }
            //if upcomingEvent
            if (eventList != null && !eventList.getEvents().isEmpty() && currnetIndexOfNew >= 1) {
                loadOldEventTile(upLeftAnchorPane,currentIndexOfUp);
                loadCurrentEventTile(upCenterAnchorPane,eventList.getEvents().get(currentIndexOfUp));
                loadNextEventTile(upRightAnchorPane,currentIndexOfUp);
            }
        }catch (IndexOutOfBoundsException e){
            throw new RuntimeException(e);
        }




    }


    private void loadCurrentEventTile(AnchorPane anchorPane,Event event) {
        try {
            FXMLLoader eventTileLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/event-tile.fxml"));
            AnchorPane load = eventTileLoader.load();
            EventTileController eventTileController = eventTileLoader.getController();
            eventTileController.showEventTile(event);

            anchorPane.getChildren().setAll(load);
            AnimateComponent(load);

            // ตรวจสอบสถานะของปุ่ม "Left" และ "Right"
            updateButtonState();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadOldEventTile(AnchorPane anchorPane,int currentIndex) {
        if (currentIndex <= eventList.getEvents().size()) {
            try {
                FXMLLoader eventTileLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/event-tile.fxml"));
                AnchorPane load = eventTileLoader.load();
                EventTileController eventTileController = eventTileLoader.getController();
                eventTileController.showEventTile(eventList.getEvents().get(currentIndex - 1));

                anchorPane.getChildren().setAll(load);
                AnimateComponent(load);
                updateButtonState();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            // ถ้าไม่มี Event ถัดไป
            anchorPane.getChildren().clear();
        }
    }
    private void loadNextEventTile(AnchorPane anchorPane,int currentIndex) {
        if (currentIndex < eventList.getEvents().size() - 1) {
            try {
                FXMLLoader eventTileLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/event-tile.fxml"));
                AnchorPane load = eventTileLoader.load();
                EventTileController eventTileController = eventTileLoader.getController();
                eventTileController.showEventTile(eventList.getEvents().get(currentIndex + 1));

                anchorPane.getChildren().setAll(load);
                AnimateComponent(load);
                updateButtonState();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            // ถ้าไม่มี Event ถัดไป
            anchorPane.getChildren().clear();
        }
    }

    //-------------Button of upComingEvent-------------\\
    public void onNewLeftButton(ActionEvent actionEvent) {
        if (currnetIndexOfNew > 1) {
            currnetIndexOfNew--;
            loadOldEventTile(newLeftAnchorPane,currnetIndexOfNew);
            loadCurrentEventTile(newCenterAnchorPane,eventList.getEvents().get(currnetIndexOfNew));
            loadNextEventTile(newRightAnchorPane,currnetIndexOfNew);
        }
    }
    public void onNewRightButton(ActionEvent actionEvent) {
        if (currnetIndexOfNew < eventList.getEvents().size()) {
            currnetIndexOfNew++;
            loadOldEventTile(newLeftAnchorPane,currnetIndexOfNew);
            loadCurrentEventTile(newCenterAnchorPane,eventList.getEvents().get(currnetIndexOfNew));
            loadNextEventTile(newRightAnchorPane,currnetIndexOfNew);
        }
    }
    //-------------Button of upComingEvent-------------\\

    // -------------Button of upComingEvent-------------\\
    public void onUpLeftButton(ActionEvent actionEvent) {
        if (currentIndexOfUp > 1) {
            currentIndexOfUp--;
            loadOldEventTile(upLeftAnchorPane,currentIndexOfUp);
            loadCurrentEventTile(upCenterAnchorPane,eventList.getEvents().get(currentIndexOfUp));
            loadNextEventTile(upRightAnchorPane,currentIndexOfUp);
        }
    }
    public void onUpRightButton(ActionEvent actionEvent) {
        if (currentIndexOfUp < eventList.getEvents().size()) {
            currentIndexOfUp++;
            loadOldEventTile(upLeftAnchorPane,currentIndexOfUp);
            loadCurrentEventTile(upCenterAnchorPane,eventList.getEvents().get(currentIndexOfUp));
            loadNextEventTile(upRightAnchorPane,currentIndexOfUp);
        }
    }
    //-------------Button of upComingEvent-------------\\

    private void updateButtonState() {
        // ตรวจสอบสถานะของปุ่ม "Left" และ "Right" แล้วอัปเดต
        newLeftButton.setDisable(currnetIndexOfNew == 1);
        newRightButton.setDisable(currnetIndexOfNew == eventList.getEvents().size() - 1);

        upLeftButton.setDisable(currentIndexOfUp == 1);
        upRightButton.setDisable(currentIndexOfUp == eventList.getEvents().size() - 1);
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

    public void onClickButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("all-events",user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
