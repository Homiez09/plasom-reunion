package cs211.project.controllers;

import cs211.project.models.Event;
import cs211.project.models.Activity;
import cs211.project.models.User;
import cs211.project.models.collections.ActivityList;
import cs211.project.models.collections.EventList;
import cs211.project.services.*;
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
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class EventPageController {
    Event event = (Event) FXRouter.getData2();
    @FXML
    private AnchorPane navbarAnchorPane,staffApplicationAnchorPane;
    @FXML private StackPane imageStackPane;
    @FXML private Button editEventButton, joinEventButton,editActivityButton;
    @FXML private Text eventInformationText;
    @FXML private Label eventNameLabel,eventDateLabel,eventLocationLabel,eventTagLabel,currentParticipantsLabel;
    @FXML private VBox teamApplyBox;
    @FXML private ImageView eventImageView;
    @FXML private TableView<Activity> eventActivityTableView;
    private Datasource<ActivityList> eventActivityDatasource;
    private Datasource<EventList> eventDatasource;
    private EventList eventList;
    private ActivityList activityList;

    private Image image;
    private HashMap<String , Set<String>> hashMap;
    private JoinEventMap joinEventMap;
    private Set<String> set;
    private User user = (User) FXRouter.getData();

    @FXML private void initialize() {
        this.eventActivityDatasource = new ActivityListDataSource("data","activity-list.csv");
        this.activityList = eventActivityDatasource.readData();
        this.eventDatasource = new EventListDataSource();
        this.eventList = eventDatasource.readData();
        this.joinEventMap = new JoinEventMap();
        this.hashMap = joinEventMap.readData();

        new LoadNavbarComponent(user, navbarAnchorPane);
        initButton();
        showEventData();
        staffApplicationAnchorPane.setVisible(false);

    }

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

    @FXML protected void onEditActivityButtonClick() {
        try {
            FXRouter.goTo("edit-activity",user,event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void showEventData() {
        String date = event.getEventDateStart()+" - " + event.getEventDateEnd();
        eventNameLabel.setText(event.getEventName());
        eventDateLabel.setText(date);
        eventLocationLabel.setText(event.getEventLocation());
        eventInformationText.setText(event.getEventDescription());
        eventInformationText.setWrappingWidth(568);
        eventTagLabel.setText(event.getEventTag());
        if (event.getSlotMember() == -1) {
            currentParticipantsLabel.setText(event.getMember()+"");
        }else {
            currentParticipantsLabel.setText(event.getMember() + "/" + event.getSlotMember());
        }
        Image image = new Image("file:" + event.getEventImagePath(), 300, 350, false, false);
        if (event.getEventImagePath().equals("null")) {
            String imgpath = "/images/events/event-default.png";
            image = new Image(getClass().getResourceAsStream(imgpath), 300, 350, false, false);
        }
        eventImageView.setImage(image);
        eventImageView.setFitWidth(300);
        eventImageView.setFitHeight(350);
        eventImageView.setPreserveRatio(true);
        Region transparentBackground = new Region();
        transparentBackground.setStyle("-fx-background-color: transparent;");
        imageStackPane.getChildren().addAll(transparentBackground);

        TableColumn<Activity,String> nameColumn = new TableColumn<>("Activity name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Activity,String> startTimeColumn = new TableColumn<>("Activity start");
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        TableColumn<Activity,String> endTimeColumn = new TableColumn<>("Activity end");
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        TableColumn<Activity,String> descriptionColumn = new TableColumn<>("Description");
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
        for (Activity activity: activityList.getActivities()) {
            if (event.getEventID().equals(activity.getEventID())) {
                eventActivityTableView.getItems().add(activity);
            }
        }
        eventActivityTableView.setFixedCellSize(40);
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

    public void onJoinEventAcition(ActionEvent actionEvent) {


        /* ใช้สำหรับ User ที่เข้าร่วมอีเว้นแล้วและเพื่อไม่ให้เข้าร่วมซํ้า*/
        if (hashMap.containsKey(event.getEventID())) {
            set = hashMap.get(event.getEventID());
        }else {
            set = new HashSet<>();
        }
        /* ใช้สำหรับ User ที่เข้าร่วมอีเว้นแล้วและเพื่อไม่ให้เข้าร่วมซํ้า*/
        if (set.contains(user.getUserId()) || user.getUserId().equals(event.getEventHostUser().getUserId())){
            try {
                FXRouter.goTo("event",user,event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            eventList.findEvent(event.getEventID()).addMember();
            set.add(user.getUserId());
            hashMap.put(event.getEventID(), set);

            eventDatasource.writeData(eventList);
            joinEventMap.writeData(hashMap);
            try {
                FXRouter.goTo("my-events", user);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void initButton(){
        if (user != null && user.getUserId().equals(event.getEventHostUser().getUserId())) {
            editEventButton.setVisible(true);
        } else {
            editEventButton.setVisible(false);
        }
        if (hashMap.containsKey(event.getEventID())) {
            set = hashMap.get(event.getEventID());
        }else {
            set = new HashSet<>();
        }
        if (user == null || set.contains(user.getUserId())|| event.getEventHostUser().getUserId().equals(user.getUserId())){
            joinEventButton.setVisible(false);
        }else {
            joinEventButton.setVisible(true);
        }
        if (event.getTeamList() != null) {
            teamApplyBox.setVisible(true);
        } else {
            teamApplyBox.setVisible(false);
        }
    }
}
