package cs211.project.componentControllers;

import cs211.project.models.*;
import cs211.project.models.collections.*;
import cs211.project.services.*;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class EventComponentController {
    @FXML
    Label eventnameLabel,startdateLabel,enddateLabel,memberLabel,tagLabel;
    @FXML
    ImageView eventImageView;
    @FXML
    AnchorPane eventAnchorPane;
    @FXML
    Button staffButton,editButton,viewjoinButton,leaveButton;
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
        this.eventListDatasource = new EventListDataSource("data","event-list.csv");
        this.eventList = eventListDatasource.readData();
        this.userMap = new HashMap<>();
        this.mapDatasource = new UserEventMap("data", "join-event.csv");
        this.userMap = mapDatasource.readData();
    }

    public void onStaffAction(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("select-team", currentUser);
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
    public void onJoinViewAction(ActionEvent actionEvent){
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
            eventList.findEvent(event.getEventID()).addMember();
            eventSet.add(event.getEventID());
            userMap.put(currentUser.getUsername(), eventSet);

            eventListDatasource.writeData(eventList);
            mapDatasource.writeData(userMap);
            try {
                FXRouter.goTo("event-list", currentUser);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void onDeleteLeaveAction(ActionEvent actionEvent) {

        if (event.getEventHost().equals(currentUser.getUsername())){
            eventList.getEvents().remove(eventList.findEvent(event.getEventID()));
            // สร้าง path
            String folderPath = event.getEventImagePath();
            File fileToDelete = new File(folderPath);
            // ลบไฟล์
            if (fileToDelete.exists()) {

                if (fileToDelete.delete()) {
                    System.out.println("Succes Delete");
                } else {
                    System.out.println("Cant Delete");
                }
            } else {
                System.out.println("Not Found");
            }
            // ลบไฟล์
            eventListDatasource.writeData(eventList);

            try {
                FXRouter.goTo("event-list", currentUser);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            if (userMap.containsKey(currentUser.getUsername())) {
                eventSet = userMap.get(currentUser.getUsername());
            }
            eventList.findEvent(event.getEventID()).delMember();
            eventSet.remove(event.getEventID());
            userMap.put(currentUser.getUsername(),eventSet);

            eventListDatasource.writeData(eventList);
            mapDatasource.writeData(userMap);
            try {
                FXRouter.goTo("my-events", currentUser);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void setEventData(Event event) {
        this.event = event;
        String imgpath = "/images/events/event-default.png";
        Image image = new Image(getClass().getResourceAsStream(imgpath),200,200,true,true);
        userMap = new HashMap<>();
        mapDatasource = new UserEventMap("data", "join-event.csv");
        userMap = mapDatasource.readData();

        if (userMap.containsKey(currentUser.getUsername())) {
            eventSet = userMap.get(currentUser.getUsername());
        }else {
            eventSet = new HashSet<>();
        }
        buttonVisible(!event.isEnd());
            //In Event
        if (eventSet.contains(event.getEventID())|| currentUser.getUsername().equals(event.getEventHost())){
            viewjoinButton.setText("View");
            leaveButton.setVisible(true);
        }else {// Join Event
            viewjoinButton.setText("Join");
            leaveButton.setVisible(false);
        }// As Host Event
        if (event.getEventHost().equals(currentUser.getUsername())){
            leaveButton.setText("Delete");
            viewjoinButton.setVisible(true);
            staffButton.setVisible(true);
            editButton.setVisible(true);
        }
        if(!event.getEventImagePath().isEmpty()){
            image = new Image("file:"+event.getEventImagePath(),200,200,true,true);
        }
        eventImageView.setImage(image);
        eventnameLabel.setText(event.getEventName());
        // Date
        startdateLabel.setText(event.getEventDateStart());
        enddateLabel.setText(event.getEventDateEnd());
        if (event.getSlotMember() == -1) {
            memberLabel.setText(event.getMember()+"");
        }else {
            memberLabel.setText(event.getMember() + "/" + event.getSlotMember());
        }
        tagLabel.setText(event.getEventTag());
    }
    public void buttonVisible(Boolean is){
        staffButton.setVisible(is);
        editButton.setVisible(is);
        viewjoinButton.setVisible(is);
        leaveButton.setVisible(is);
    }


}
