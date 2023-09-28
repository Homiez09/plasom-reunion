package cs211.project.componentControllers;

import cs211.project.models.*;
import cs211.project.models.collections.*;
import cs211.project.services.*;
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
import java.util.HashSet;
import java.util.Set;

public class CardMyEventController {
    @FXML
    Label eventNameLabel,startDateLabel,locationLabel, memberCountLabel,descriptionLabel,hostUserNameLabel,hostDisplayNameLabel;
    @FXML
    ImageView eventImageView,profileImageView;
    @FXML
    AnchorPane eventAnchorPane;
    @FXML
    Button forStaffButton,manageEventButton,leaveEventButton;
    private User currentUser = (User) FXRouter.getData();
    private TeamListDataSource teamListDataSource;
    private TeamList teamList;
    private JoinTeamMap joinTeamMap = new JoinTeamMap();
    private HashMap<String, TeamList> teamHashMap = new HashMap<>();
    private Datasource<EventList> eventListDatasource;
    private EventList eventList;
    private JoinEventMap joinEventDatasource;
    private HashMap<String, Set<String>> joinEventMap; // Collect EventID
    private Set<String> SetUser;// Collect User
    private Event event;

    @FXML
    public void initialize() {
        this.eventListDatasource = new EventListDataSource();
        this.eventList = eventListDatasource.readData();
        this.joinEventMap = new HashMap<>();
        this.joinEventDatasource = new JoinEventMap();
        this.joinEventMap = joinEventDatasource.readData();
        this.teamListDataSource = new TeamListDataSource("data", "team-list.csv");
        this.teamList = new TeamList();
        buttonVisible(true);
    }


    public void onJoinViewAction(ActionEvent actionEvent){
        /* ใช้สำหรับ User ที่เข้าร่วมอีเว้นแล้วและเพื่อไม่ให้เข้าร่วมซํ้า*/
        if (joinEventMap.containsKey(event.getEventID())) {
            SetUser = joinEventMap.get(event.getEventID());
        }else {
            SetUser = new HashSet<>();
        }
        /* ใช้สำหรับ User ที่เข้าร่วมอีเว้นแล้วและเพื่อไม่ให้เข้าร่วมซํ้า*/
        if (SetUser.contains(currentUser.getUserId()) || currentUser.getUserId().equals(event.getEventHostUser())){
            try {
                FXRouter.goTo("event",currentUser,event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {

            SetUser.add(currentUser.getUserId());
            joinEventMap.put(event.getEventID(), SetUser);

            eventListDatasource.writeData(eventList);
            joinEventDatasource.writeData(joinEventMap);
            try {
                FXRouter.goTo("my-events", currentUser);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void onLeaveEventButton(ActionEvent actionEvent) {
        
        if (joinEventMap.containsKey(event.getEventID())) {
            SetUser = joinEventMap.get(event.getEventID());
        }

        SetUser.remove(currentUser.getUserId());
        joinEventMap.put(event.getEventID(), SetUser);

        eventListDatasource.writeData(eventList);
        joinEventDatasource.writeData(joinEventMap);
        try {
            FXRouter.goTo("my-events", currentUser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    public void setEvent(Event event) {
        this.event = event;
        joinEventMap = new HashMap<>();
        joinEventDatasource = new JoinEventMap();
        joinEventMap = joinEventDatasource.readData();
        joinTeamMap = new JoinTeamMap();
        teamHashMap = joinTeamMap.readData();
        teamList = teamHashMap.get(currentUser.getUsername());

        if (joinEventMap.containsKey(event.getEventID())) {
            SetUser = joinEventMap.get(event.getEventID());
        }else {
            SetUser = new HashSet<>();
        }

        buttonVisible(event.isEnd());

        if (event.isHostEvent(currentUser.getUserId())){
            leaveEventButton.setVisible(false);
        }

        for (Team team:teamList.getTeamOfEvent(event)) {
            if (teamHashMap.containsKey(currentUser.getUsername()) && team.getEventID().equals(event.getEventID()) && !event.isHostEvent(currentUser.getUserId())){
                leaveEventButton.setVisible(false);
                manageEventButton.setVisible(false);
            }
        }

        if (event.isHaveUser(currentUser)) {
            forStaffButton.setVisible(false);
            manageEventButton.setVisible(false);
        }


        Image image = new Image("file:" + event.getEventImagePath(), 200, 200, false, false);
        if(event.getEventImagePath().equals("null")){
            String imgpath = "/images/events/event-default.png";
            image = new Image(getClass().getResourceAsStream(imgpath),200,200,false,false);
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
        if (event.getSlotMember() == -1) {
            memberCountLabel.setText(event.getUserInEvent()+"");
        }else {
            memberCountLabel.setText(event.getUserInEvent() + "/" + event.getSlotMember());
        }

    }

    public void buttonVisible(Boolean is){
        forStaffButton.setVisible(!is);
        manageEventButton.setVisible(!is);
        leaveEventButton.setVisible(!is);
    }

    public void onForStaffButton(ActionEvent actionEvent) {
        joinTeamMap = new JoinTeamMap();
        teamHashMap = joinTeamMap.readData();
        if (teamHashMap.containsKey(currentUser.getUsername()) || event.isHostEvent(currentUser.getUserId())){
            try {
                FXRouter.goTo("select-team",currentUser,event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void onManageEventButton(ActionEvent actionEvent) {
        Popup popup = new Popup();
        VBox popupContent = new VBox();
        //----------set up---------------\\
        popupContent.setStyle("-fx-background-color: #F6F4EE;");
        popup.setAutoHide(true);

        //----------set up---------------\\

        VBox box = new VBox();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/users-event.fxml"));
            VBox loaded = loader.load();
            UsersEventController usersEventController = loader.getController();
            usersEventController.setDataPopup(popup,event);
            box.getChildren().setAll(loaded);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        popupContent.getChildren().add(box);

        popup.getContent().addAll(popupContent);


        popup.show(manageEventButton.getScene().getWindow());
    }

    public void onClickCard(MouseEvent mouseEvent) {
            try {
                FXRouter.goTo("event",currentUser,event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


    }
}
