package cs211.project.controllers.view.event;

import cs211.project.models.Event;
import cs211.project.models.Activity;
import cs211.project.models.User;
import cs211.project.models.collections.ActivityList;
import cs211.project.models.collections.EventList;
import cs211.project.models.collections.TeamList;
import cs211.project.models.collections.UserList;
import cs211.project.services.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EventPageController {
    @FXML private AnchorPane navbarAnchorPane;
    @FXML private Button editEventButton, joinEventButton,editActivityButton;
    @FXML private Text eventInformationText;
    @FXML private Label eventNameLabel,eventDateLabel,eventLocationLabel,eventTagLabel,currentParticipantsLabel;
    @FXML private VBox teamApplyBox;
    @FXML private ImageView eventImageView;
    @FXML private Tab eventActivityTab;
    @FXML private TableView<Activity> eventActivityTableView;

    private final Event event = (Event) FXRouter.getData2();
    private final User user = (User) FXRouter.getData();
    private final String from = (String) FXRouter.getData3();
    private ActivityList activityList;

    @FXML private void initialize() {
        Datasource<ActivityList> eventActivityDatasource = new ActivityListDataSource("data", "event-activity-list.csv");
        this.activityList = eventActivityDatasource.readData();
        JoinEventMap joinEventMap = new JoinEventMap();
        HashMap<String, UserList> hashMap = joinEventMap.readData();

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
        }else if (from.equals("tile")) {
            try {
                FXRouter.goTo("home",user,null);
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
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

        if (event.getUserList().getUsers().contains(user) || event.isHostEvent(user)){
            try {
                FXRouter.goTo("event",user,event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            event.getUserList().getUsers().add(user);
            JoinEventMap joinEventMap = new JoinEventMap();
            HashMap<String, UserList>  hashMap = joinEventMap.readData();
            hashMap.put(event.getEventID(), event.getUserList());
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
        Image image = new Image("file:" + event.getEventImagePath(), 1925, 1375, false, false);
        if (event.getEventImagePath().equals("null")) {
            String imgpath = "/images/events/event-default.png";
            image = new Image(getClass().getResourceAsStream(imgpath), 1925, 1375, false, false);
        }
        eventImageView.setImage(image);;

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
        JoinTeamMap joinTeamData = new JoinTeamMap();
        HashMap<String,TeamList> joinTeamMap = joinTeamData.readData();
        BanUser banUser = new BanUser();
        HashMap<User, EventList> banList = banUser.readData();
        EventList eventList = banList.get(user);

        editEventButton.setVisible(event.isHostEvent(user));
        editActivityButton.setVisible(event.isHostEvent(user));

        TeamList teamTempCheck = (user != null && joinTeamMap.containsKey(user.getUsername())) ? joinTeamMap.get(user.getUsername()) : new TeamList();
        joinEventButton.setVisible( user != null && event.isJoinEvent() &&
                                    !event.isFull() && !event.isEnd() &&
                                    !event.getUserList().getUsers().contains(user) &&
                                    !event.isHostEvent(user) &&
                                    teamTempCheck.getTeamOfEvent(event).size() == 0
        );
        teamApplyBox.setVisible(user != null &&
                                teamList.getTeamOfEvent(event) != null &&
                                !teamList.getTeamOfEvent(event).isEmpty() &&
                                !event.isHostEvent(user) &&
                                !event.getUserList().getUsers().contains(user)
        );
        eventActivityTab.setDisable((user == null || !event.getUserList().getUsers().contains(user)
                || banList.containsKey(user) && eventList.getEvents().contains(event) )
                && !event.isHostEvent(user));
    }
}
