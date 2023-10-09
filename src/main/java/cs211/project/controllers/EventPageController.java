package cs211.project.controllers;

import cs211.project.models.Event;
import cs211.project.models.Activity;
import cs211.project.models.User;
import cs211.project.models.collections.ActivityList;
import cs211.project.models.collections.TeamList;
import cs211.project.models.collections.UserList;
import cs211.project.services.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

public class EventPageController {
    @FXML private AnchorPane navbarAnchorPane;
    @FXML private StackPane imageStackPane;
    @FXML private Button editEventButton, joinEventButton,editActivityButton;
    @FXML private Text eventInformationText;
    @FXML private Label eventNameLabel,eventDateLabel,eventLocationLabel,eventTagLabel,currentParticipantsLabel;
    @FXML private VBox teamApplyBox;
    @FXML private ImageView eventImageView;
    @FXML private Tab eventActivityTab;
    @FXML private TableView<Activity> eventActivityTableView;
    private Event event = (Event) FXRouter.getData2();
    private User user = (User) FXRouter.getData();
    private String from = (String) FXRouter.getData3();
    private Datasource<ActivityList> eventActivityDatasource;
    private ActivityList activityList;
    private HashMap<String, UserList> hashMap;
    private JoinEventMap joinEventMap;
    private UserList userList;

    @FXML private void initialize() {
        this.eventActivityDatasource = new ActivityListDataSource("data","event-activity-list.csv");
        this.activityList = eventActivityDatasource.readData();
        this.joinEventMap = new JoinEventMap();
        this.hashMap = joinEventMap.readData();

        new LoadNavbarComponent(user, navbarAnchorPane);
        initButton();
        showEventData();
    }

    // button to another page
    @FXML private void onEditButtonClick() {
        try {
            FXRouter.goTo("create-event", user,event,"my-event");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML private void onBackButtonClick() {
        if (from.equals("my-event")) {
            try {
                FXRouter.goTo("my-event", user, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            try {
                FXRouter.goTo("all-events",user,null);
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML private void onEditActivityButtonClick() {
        try {
            FXRouter.goTo("edit-event-activity",user,event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML private void onApplyStaffButtonClick(){
        try {
            FXRouter.goTo("join-team", user, event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML private void onJoinEventAction() {
        /* ใช้สำหรับ User ที่เข้าร่วมอีเว้นแล้วและเพื่อไม่ให้เข้าร่วมซํ้า*/
        if (hashMap.containsKey(event.getEventID())) {
            userList = hashMap.get(event.getEventID());
        }else {
            userList = new UserList();
        }
        /* ใช้สำหรับ User ที่เข้าร่วมอีเว้นแล้วและเพื่อไม่ให้เข้าร่วมซํ้า*/

        if (userList.getUsers().contains(user) || user.getUserId().equals(event.getEventHostUser().getUserId())){
            try {
                FXRouter.goTo("event",user,event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            userList.getUsers().add(user);
            hashMap.put(event.getEventID(), userList);
            joinEventMap.writeData(hashMap);
            try {
                FXRouter.goTo("my-event", user,"event");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    // set up page
    private void showEventData() {
        String date = event.getEventDateStart()+" - " + event.getEventDateEnd();
        eventNameLabel.setText(event.getEventName());
        eventDateLabel.setText(date);
        eventLocationLabel.setText(event.getEventLocation());
        eventInformationText.setText(event.getEventDescription());
        eventInformationText.setWrappingWidth(568);
        eventTagLabel.setText(event.getEventTag());
        if (event.getSlotMember() == -1) {
            currentParticipantsLabel.setText(event.getUserInEvent()+"");
        }else {
            currentParticipantsLabel.setText(event.getUserInEvent() + "/" + event.getSlotMember());
        }
        Image image = new Image("file:" + event.getEventImagePath(), 300, 350, true, false);
        if (event.getEventImagePath().equals("null")) {
            String imgpath = "/images/events/event-default.png";
            image = new Image(getClass().getResourceAsStream(imgpath), 300, 350, true, false);
        }
        eventImageView.setImage(image);
        eventImageView.setFitWidth(300);
        eventImageView.setFitHeight(350);
        eventImageView.setPreserveRatio(true);
        Region transparentBackground = new Region();
        transparentBackground.setStyle("-fx-background-color: transparent;");
        imageStackPane.getChildren().addAll(transparentBackground);

        // activity table
        TableColumn<Activity,String> nameColumn = new TableColumn<>("Activity name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Activity,String> startTimeColumn = new TableColumn<>("Activity start");
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        TableColumn<Activity,String> endTimeColumn = new TableColumn<>("Activity end");
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        TableColumn<Activity,String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        TableColumn<Activity,String> statusColumn = new TableColumn<>("Activity status");
        statusColumn.setCellValueFactory(cellData -> {
            Activity activity = cellData.getValue();
            String status = activity.getActivityStatus();
            return new SimpleStringProperty(status);
        });
        eventActivityTableView.getColumns().clear();
        eventActivityTableView.getColumns().add(nameColumn);
        eventActivityTableView.getColumns().add(startTimeColumn);
        eventActivityTableView.getColumns().add(endTimeColumn);
        eventActivityTableView.getColumns().add(statusColumn);
        eventActivityTableView.getColumns().add(descriptionColumn);
        eventActivityTableView.getItems().clear();
        nameColumn.setPrefWidth(180);
        startTimeColumn.setPrefWidth(120);
        endTimeColumn.setPrefWidth(120);
        statusColumn.setPrefWidth(100);
        descriptionColumn.prefWidthProperty().bind(eventActivityTableView.prefWidthProperty().subtract(nameColumn.widthProperty())
                .subtract(startTimeColumn.widthProperty()).subtract(endTimeColumn.widthProperty()).subtract(statusColumn.widthProperty()));
        for (Activity activity: activityList.sortActivity(activityList).getActivityOfEvent(event.getEventID())) {
            eventActivityTableView.getItems().add(activity);
        }
        eventActivityTableView.setFixedCellSize(40);
    }

    private void initButton(){
        Datasource<TeamList> teamListDatasource = new TeamListDataSource("data","team-list.csv");
        TeamList teamList = teamListDatasource.readData();

        editEventButton.setVisible(event.isHostEvent(user));
        editActivityButton.setVisible(event.isHostEvent(user));
        joinEventButton.setVisible( event.isJoinEvent() &&
                                    !event.isFull() &&
                                    !event.getUserList().getUsers().contains(user) &&
                                    !event.isHostEvent(user));
        teamApplyBox.setVisible(user != null && teamList.getTeamOfEvent(event) != null &&
                teamList.getTeamOfEvent(event).size() > 0 && !event.isHostEvent(user));
        eventActivityTab.setDisable((user == null || !event.getUserList().getUsers().contains(user)) && !event.isHostEvent(user));
    }
}
