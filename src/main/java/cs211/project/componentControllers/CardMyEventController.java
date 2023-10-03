package cs211.project.componentControllers;

import cs211.project.models.*;
import cs211.project.models.collections.*;
import cs211.project.services.*;
import javafx.beans.value.ObservableValueBase;
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
    Button forStaffButton,manageEventButton,leaveEventButton;
    private User currentUser = (User) FXRouter.getData();
    private TeamListDataSource teamListDataSource;
    private TeamList teamList;
    private JoinTeamMap joinTeamMap = new JoinTeamMap();
    private HashMap<String, TeamList> teamHashMap = new HashMap<>();
    private Datasource<EventList> eventListDatasource;
    private EventList eventList;
    private JoinEventMap joinEventDatasource;
    private HashMap<String, UserList> joinEventMap; // Collect EventID
    private UserList userList;// Collect User
    private Event event;

    @FXML
    public void initialize() {
        setupPage();
        buttonVisible(true);
    }

    private void setupPage(){
        this.eventListDatasource = new EventListDataSource();
        this.eventList = eventListDatasource.readData();
        this.joinEventMap = new HashMap<>();
        this.joinEventDatasource = new JoinEventMap();
        this.joinEventMap = joinEventDatasource.readData();
        this.teamListDataSource = new TeamListDataSource("data", "team-list.csv");
        this.teamList = new TeamList();
    }

    public void onJoinViewAction(ActionEvent actionEvent){

    }
    @FXML
    private void onLeaveEventButton(ActionEvent actionEvent) {
        
        if (joinEventMap.containsKey(event.getEventID())) {
            userList = joinEventMap.get(event.getEventID());
        }

        userList.getUsers().remove(currentUser);
        joinEventMap.put(event.getEventID(), userList);

        eventListDatasource.writeData(eventList);
        joinEventDatasource.writeData(joinEventMap);
        try {
            FXRouter.goTo("my-events", currentUser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setEventData(Event event) {
        this.event = event;
        joinEventMap = new HashMap<>();
        joinEventDatasource = new JoinEventMap();
        joinEventMap = joinEventDatasource.readData();
        joinTeamMap = new JoinTeamMap();
        teamHashMap = joinTeamMap.readData();
        teamList = teamHashMap.get(currentUser.getUsername());

        if (joinEventMap.containsKey(event.getEventID())) {
            userList = joinEventMap.get(event.getEventID());
        }else {
            userList = new UserList();
        }

        buttonVisible(event.isEnd());

        if (event.isHostEvent(currentUser.getUserId())){
            leaveEventButton.setVisible(false);
        }

        if (teamHashMap.containsKey(currentUser.getUsername()) && teamList.getTeamOfEvent(event) != null && !event.isHostEvent(currentUser.getUserId())){
                leaveEventButton.setVisible(false);
                manageEventButton.setVisible(false);
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
    @FXML
    private void onForStaffButton(ActionEvent actionEvent) {
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
    @FXML
    private void onManageUserButton(ActionEvent actionEvent) {
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

    public void updateUi(){

    }
}
