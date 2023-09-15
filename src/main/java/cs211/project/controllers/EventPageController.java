package cs211.project.controllers;

import cs211.project.models.Event;
import cs211.project.models.EventActivity;
import cs211.project.models.User;
import cs211.project.models.collections.ActivityList;
import cs211.project.services.ActivityListDataSource;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouter;
import cs211.project.services.LoadNavbarComponent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.io.IOException;

public class EventPageController {
    Event event = (Event) FXRouter.getData2();
    @FXML
    private AnchorPane navbarAnchorPane,staffApplicationAnchorPane;
    @FXML private Button editEventButton;
    @FXML private Label eventNameLabel,eventDateLabel,eventLocationLabel,eventInformationLabel;
    @FXML private VBox teamApplyBox;
    @FXML private ImageView eventImageView;
    @FXML private TableView<EventActivity> eventActivityTableView;
    private Datasource<ActivityList> eventActivityDatasource;
    private ActivityList activityList;
    private Image image;
    @FXML private void initialize() {
        this.eventActivityDatasource = new ActivityListDataSource("data","activity-list.csv");
        this.activityList = eventActivityDatasource.readData();

        new LoadNavbarComponent(user, navbarAnchorPane);
        showEventData();
        staffApplicationAnchorPane.setVisible(false);
        if (user != null && user.getUserId() == event.getEventHost()) {
            editEventButton.setVisible(true);
        } else {
            editEventButton.setVisible(false);
        }
        if (event.getTeams() != null) {
            teamApplyBox.setVisible(true);
        } else {
            teamApplyBox.setVisible(false);
        }
    }

    private User user = (User) FXRouter.getData();
    @FXML protected void onEditButtonClick() {
        try {
            FXRouter.goTo("create-event", user,event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void onBackButtonClick() {
        try {
            FXRouter.goTo("home",user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showEventData() {
        String date = event.getEventDateStart()+" - " + event.getEventDateEnd();
        eventNameLabel.setText(event.getEventName());
        eventDateLabel.setText(date);
        eventLocationLabel.setText(event.getEventLocation());
        eventInformationLabel.setText(event.getEventDescription());
        image = new Image(getClass().getResource("/images/events/event-default.png").toString());
        try {
            image = new Image("file:"+event.getEventImagePath(),true);
        }catch (Exception e){
            e.printStackTrace();
        }
        eventImageView.setImage(image);

        TableColumn<EventActivity,String> nameColumn = new TableColumn<>("Activity name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<EventActivity,String> startTimeColumn = new TableColumn<>("Activity start");
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        TableColumn<EventActivity,String> endTimeColumn = new TableColumn<>("Activity end");
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        TableColumn<EventActivity,String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        eventActivityTableView.getColumns().clear();
        eventActivityTableView.getColumns().add(nameColumn);
        eventActivityTableView.getColumns().add(startTimeColumn);
        eventActivityTableView.getColumns().add(endTimeColumn);
        eventActivityTableView.getColumns().add(descriptionColumn);
        eventActivityTableView.getItems().clear();
        nameColumn.setPrefWidth(200);
        startTimeColumn.setPrefWidth(150);
        endTimeColumn.setPrefWidth(150);
        descriptionColumn.prefWidthProperty().bind(eventActivityTableView.widthProperty().subtract(nameColumn.widthProperty())
                .subtract(startTimeColumn.widthProperty()).subtract(endTimeColumn.widthProperty()));
        for (EventActivity activity: activityList.getActivities()) {
            if (event.getEventID().equals(activity.getEventID())) {
                eventActivityTableView.getItems().add(activity);
            }
        }
    }

    @FXML protected void onApplyStaffButtonClick() {
        if (!staffApplicationAnchorPane.isVisible()) {
            staffApplicationAnchorPane.setVisible(true);
            FXMLLoader staffApplicationLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/member-application.fxml"));
            try {
                AnchorPane staffApplicationWindow = staffApplicationLoader.load();
                staffApplicationAnchorPane.getChildren().add(staffApplicationWindow);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
