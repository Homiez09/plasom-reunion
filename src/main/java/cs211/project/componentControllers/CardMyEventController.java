package cs211.project.componentControllers;

import cs211.project.models.*;
import cs211.project.models.collections.*;
import cs211.project.services.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;


import java.io.IOException;
import java.util.HashMap;

public class CardMyEventController {
    @FXML
    Label eventNameLabel,startDateLabel,locationLabel, memberCountLabel,descriptionLabel,hostUserNameLabel,hostDisplayNameLabel;
    @FXML
    ImageView eventImageView,profileImageView;
    @FXML
    AnchorPane eventAnchorPane;
    @FXML
    Button forStaffButton, manageUserButton,leaveEventButton;
    private User currentUser = (User) FXRouter.getData();
    private TeamList teamList;
    private JoinTeamMap joinTeamMap = new JoinTeamMap();
    private HashMap<String, TeamList> teamHashMap = new HashMap<>();
    private Datasource<EventList> eventListDatasource;
    private EventList eventList;
    private JoinEventMap joinEventDatasource;
    private HashMap<String, UserList> joinEventMap; // Collect EventID
    private UserList userList;// Collect User
    private Event currentEvent;

    @FXML
    private void initialize() {
        setupDataPage();
        buttonVisible(true);
    }

    private void setupDataPage(){
        this.eventListDatasource = new EventListDataSource();
        this.eventList = eventListDatasource.readData();
        this.joinEventMap = new HashMap<>();
        this.joinEventDatasource = new JoinEventMap();
        this.joinEventMap = joinEventDatasource.readData();
    }

    @FXML
    private void onLeaveEventButton(ActionEvent actionEvent) {
        if (joinEventMap.containsKey(currentEvent.getEventID())) {
            userList = joinEventMap.get(currentEvent.getEventID());
        }
        userList.getUsers().remove(currentUser);
        joinEventMap.put(currentEvent.getEventID(), userList);
        joinEventDatasource.writeData(joinEventMap);

    }

    public void setEventData(Event event) {
        setupDataPage();
        if (event !=null) {
            currentEvent = eventList.findEventById(event.getEventID());

            buttonVisible(event.isEnd());
            if (event.getUserList().getUsers().contains(currentUser)) {
                forStaffButton.setVisible(false);
                manageUserButton.setVisible(false);
            }
            if (event.getEventHostUser().equals(currentUser)) {
                leaveEventButton.setVisible(false);
            }
            for (Team team : event.getTeamList().getTeams()) {
                if (team.getMemberList().getUsers().contains(currentUser) && !currentEvent.isHostEvent(currentUser)) {
                    manageUserButton.setVisible(false);
                    leaveEventButton.setVisible(false);
                }
            }


            Image image = new Image("file:" + event.getEventImagePath(), 200, 200, false, false);
            if (event.getEventImagePath().equals("null")) {
                String imgPath = "/images/events/event-default-auth.png";
                image = new Image(getClass().getResourceAsStream(imgPath), 200, 200, false, false);
            }
            eventImageView.setImage(image);
            eventNameLabel.setText(event.getEventName());
            startDateLabel.setText(event.getEventDateStart());
            locationLabel.setText(event.getEventLocation());

            ImagePathFormat pathFormat = new ImagePathFormat(event.getEventHostUser().getImagePath());
            profileImageView.setImage(new Image(pathFormat.toString(), 30, 30, false, false));
            new CreateProfileCircle(profileImageView, 32);

            hostUserNameLabel.setText(event.getEventHostUser().getUsername());
            hostDisplayNameLabel.setText(event.getEventHostUser().getDisplayName());
            String descrip = event.getEventDescription().replaceAll("\n", " ");
            descriptionLabel.setText(descrip);
            /*if (event.getSlotMember() == -1) {
                memberCountLabel.setText(userObservableList.size() + "");
            } else {
                memberCountLabel.setText(userObservableList.size() + "/" + event.getSlotMember());
            }*/
        }
    }

    private void buttonVisible(Boolean is){
        forStaffButton.setVisible(!is);
        manageUserButton.setVisible(!is);
        leaveEventButton.setVisible(!is);
    }
    @FXML
    private void onForStaffButton(ActionEvent actionEvent) {
        joinTeamMap = new JoinTeamMap();
        teamHashMap = joinTeamMap.readData();
        if (teamHashMap.containsKey(currentUser.getUsername()) || currentEvent.isHostEvent(currentUser)){
            try {
                FXRouter.goTo("select-team",currentUser, currentEvent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    private void onManageUserButton(ActionEvent actionEvent) {
        Popup popup = new Popup();
        VBox popupContent = new VBox();
        popup.setAutoHide(true);
        VBox box = new VBox();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/users-event.fxml"));
            VBox loaded = loader.load();
            UsersEventController usersEventController = loader.getController();
            usersEventController.setupData(popup, currentEvent);
            box.getChildren().setAll(loaded);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        popupContent.getChildren().add(box);
        popup.getContent().addAll(popupContent);
        popup.show(manageUserButton.getScene().getWindow());

    }
    @FXML
    private void onClickCard(MouseEvent mouseEvent) {
            try {
                FXRouter.goTo("event",currentUser, currentEvent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


    }


}
