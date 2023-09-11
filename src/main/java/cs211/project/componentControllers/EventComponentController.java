package cs211.project.componentControllers;

import cs211.project.controllers.EventListController;
import cs211.project.models.*;
import cs211.project.models.collections.*;
import cs211.project.services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EventComponentController extends EventListController {
    @FXML
    Label eventnameLabel,startdateLabel,enddateLabel,memberLabel;
    @FXML
    ImageView eventImageView;
    @FXML
    AnchorPane eventAnchorPane;
    @FXML
    Button staffButton,editButton,viewjoinButton;
    private User currentUser = (User) FXRouter.getData();
    private Datasource<UserList> userListDatasource;
    private UserList userList;
    private Datasource<EventList> eventListDatasource;
    private EventList eventList;
    private UserEventMap mapDatasource ;
    private HashMap<String, Set<String>> userMap;
    private Set<String> eventSet;
    private Event event;

    @FXML
    public void initialize() {
        this.userMap = new HashMap<>();
        this.mapDatasource = new UserEventMap("data", "user-event.csv");
        this.userMap = mapDatasource.readData();

    }


    public void onStaffAction(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("select-team", currentUser, event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void onEditAction(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("create-event", currentUser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void onButtonAction(ActionEvent actionEvent){
        /* ใช้สำหรับ User ที่เข้าร่วมอีเว้นแล้วและเพื่อไม่ให้เข้าร่วมซํ้า*/

        if (userMap.containsKey(currentUser.getUsername())) {
            eventSet = userMap.get(currentUser.getUsername());
        }else {
            eventSet = new HashSet<>();
        }
        /* ใช้สำหรับ User ที่เข้าร่วมอีเว้นแล้วและเพื่อไม่ให้เข้าร่วมซํ้า*/
        if (eventSet.contains(event.getEventID())||currentUser.getUsername().equals(event.getEventHost())){
            try {
                FXRouter.goTo("event",currentUser,event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            eventSet.add(event.getEventID());
            userMap.put(currentUser.getUsername(), eventSet);
            mapDatasource.writeData(userMap);
            try {
                FXRouter.goTo("event-list",currentUser);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }




    }

    public void setEventData(Event event) {
        this.event = event;
        Image image;
        userMap = new HashMap<>();
        mapDatasource = new UserEventMap("data", "user-event.csv");
        userMap = mapDatasource.readData();

        if (userMap.containsKey(currentUser.getUsername())) {
            eventSet = userMap.get(currentUser.getUsername());
        }else {
            eventSet = new HashSet<>();
        }
        buttonVisible(!event.isEnd());

        if (eventSet.contains(event.getEventID())|| currentUser.getUsername().equals(event.getEventHost())){
            viewjoinButton.setText("View");
        }else {
            viewjoinButton.setText("Join");
        }
        image = new Image("file:"+event.getEventImagePath(),true);


        eventImageView.setImage(image);
        eventnameLabel.setText(event.getEventName());

        // Date

        startdateLabel.setText(event.getEventDateStart());
        enddateLabel.setText(event.getEventDateEnd());

        memberLabel.setText(event.getMember() + "");
    }

    public void buttonVisible(Boolean is){
        staffButton.setVisible(is);
        editButton.setVisible(is);
    }
}
