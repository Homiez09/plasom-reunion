package cs211.project.controllers.component;

import cs211.project.models.*;
import cs211.project.models.collections.*;
import cs211.project.services.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;

public class CardMyEventController {
    @FXML Label eventNameLabel,startDateLabel,locationLabel,descriptionLabel,hostUserNameLabel,hostDisplayNameLabel;
    @FXML ImageView eventImageView,profileImageView;
    @FXML AnchorPane eventAnchorPane;
    @FXML Button staffButton, manageUserButton,leaveEventButton;
    private final User currentUser = (User) FXRouter.getData();
    private EventList eventList;
    private JoinEventMap joinEventDatasource;
    private HashMap<String, UserList> joinEventMap; // Collect EventID
    private UserList userList;// Collect User
    private Event currentEvent;

    @FXML
    private void initialize() {
        setupDataPage();
    }

    private void setupDataPage(){
        Datasource<EventList> eventListDatasource = new EventListDataSource();
        this.eventList = eventListDatasource.readData();
        this.joinEventMap = new HashMap<>();
        this.joinEventDatasource = new JoinEventMap();
        this.joinEventMap = joinEventDatasource.readData();
    }

    @FXML
    private void onLeaveEventButton() {
        if (joinEventMap.containsKey(currentEvent.getEventID())) {
            userList = joinEventMap.get(currentEvent.getEventID());
        }
        currentEvent.getUserList().getUsers().remove(currentUser);
        joinEventMap.put(currentEvent.getEventID(), currentEvent.getUserList());
        joinEventDatasource.writeData(joinEventMap);
        try {
            FXRouter.goTo("my-event",currentUser,"card");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setEventData(Event event) {
        setupDataPage();
        buttonVisible(true);
        if (event !=null) {
            currentEvent = eventList.findEventById(event.getEventID());
            Datasource<TeamList> teamListDatasource = new TeamListDataSource("data","team-list.csv");
            TeamList teamList = teamListDatasource.readData();

            buttonVisible(event.isEnd());
            if (event.getUserList().getUsers().contains(currentUser)) {
                staffButton.setVisible(false);
                manageUserButton.setVisible(false);
            }
            if (event.getEventHostUser().equals(currentUser)) {
                leaveEventButton.setVisible(false);
            }

            for (Team team : teamList.getTeams()) {
                if (    team.getMemberList().getUsers().contains(currentUser) &&
                        !currentEvent.isHostEvent(currentUser) &&
                        event.getEventID().equals(team.getEventID())) {
                    manageUserButton.setVisible(false);
                    leaveEventButton.setVisible(false);
                }
            }

            new BorderImagView(eventImageView).setSquareClip(14);
            Image image = new Image("file:" + event.getEventImagePath(), 1280, 1280, false, false);
            if (event.getEventImagePath().equals("null")) {
                String imgPath = "/images/events/event-default-auth.png";
                image = new Image(getClass().getResourceAsStream(imgPath), 1280, 1280, false, false);
            }
            eventImageView.setImage(image);
            eventNameLabel.setText(event.getEventName());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd/MM/yyyy | hh:mm:ss a", Locale.ENGLISH);

            startDateLabel.setText(event.getDateStartAsDate().format(formatter));
            locationLabel.setText(event.getEventLocation());

            ImagePathFormat pathFormat = new ImagePathFormat(event.getEventHostUser().getImagePath());
            profileImageView.setImage(new Image(pathFormat.toString(), 1280, 1280, false, false));
            new CreateProfileCircle(profileImageView, 15);

            hostUserNameLabel.setText(event.getEventHostUser().getUsername());
            hostDisplayNameLabel.setText(event.getEventHostUser().getDisplayName());
            String description = event.getEventDescription().replaceAll("\n", " ");
            descriptionLabel.setText(description);
        }
    }

    private void buttonVisible(Boolean is){
        staffButton.setVisible(!is);
        manageUserButton.setVisible(!is);
        leaveEventButton.setVisible(!is);
    }

    @FXML
    private void onStaffButton() {
        JoinTeamMap joinTeamMap = new JoinTeamMap();
        HashMap<String, TeamList> teamHashMap = joinTeamMap.readData();
        if (teamHashMap.containsKey(currentUser.getUsername()) || currentEvent.isHostEvent(currentUser)){
            try {
                FXRouter.goTo("all-team",currentUser, currentEvent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void onManageUserButton() {
        Popup popup = new Popup();
        VBox popupContent = new VBox();
        popup.setAutoHide(true);
        VBox box = new VBox();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/users-event.fxml"));
            VBox loaded = loader.load();
            UsersEventController usersEventController = loader.getController();
            usersEventController.setupData(currentEvent);
            box.getChildren().setAll(loaded);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        popupContent.getChildren().add(box);
        popup.getContent().addAll(popupContent);
        popup.show(manageUserButton.getScene().getWindow());

    }

    @FXML
    private void onClickCard() {
        try {
            FXRouter.goTo("event",currentUser, currentEvent,"my-event");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
