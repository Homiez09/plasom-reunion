package cs211.project.componentControllers;

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


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
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
    private TeamListDataSource teamListDataSource;
    private TeamList teamList;
    private JoinTeamMap joinTeamMap = new JoinTeamMap();
    private HashMap<String, TeamList> teamHashMapGlobal = joinTeamMap.readData();
    HashMap<String, TeamList> teamHashMap = new HashMap<>();
    private Datasource<EventList> eventListDatasource;
    private EventList eventList;
    private UserEventMap mapDatasource ;
    private HashMap<String, Set<String>> hashMap; // Collect EventID
    private Set<String> hashSet;// Collect User
    private Event event;

    @FXML
    public void initialize() {
        this.eventListDatasource = new EventListDataSource("data","event-list.csv");
        this.eventList = eventListDatasource.readData();
        this.hashMap = new HashMap<>();
        this.mapDatasource = new UserEventMap("data", "join-event.csv");
        this.hashMap = mapDatasource.readData();

        this.teamListDataSource = new TeamListDataSource("data", "team-list.csv");
        this.teamList = teamListDataSource.readData();
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
    public void onJoinViewAction(ActionEvent actionEvent){
        /* ใช้สำหรับ User ที่เข้าร่วมอีเว้นแล้วและเพื่อไม่ให้เข้าร่วมซํ้า*/
        if (hashMap.containsKey(event.getEventID())) {
            hashSet = hashMap.get(event.getEventID());
        }else {
            hashSet = new HashSet<>();
        }
        /* ใช้สำหรับ User ที่เข้าร่วมอีเว้นแล้วและเพื่อไม่ให้เข้าร่วมซํ้า*/
        if (hashSet.contains(currentUser.getUsername())|| currentUser.getUsername().equals(event.getEventHost())){
            try {
                FXRouter.goTo("event",currentUser,event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            eventList.findEvent(event.getEventID()).addMember();
            hashSet.add(currentUser.getUsername());
            hashMap.put(event.getEventID(), hashSet);

            eventListDatasource.writeData(eventList);
            mapDatasource.writeData(hashMap);
            try {
                FXRouter.goTo("my-events", currentUser);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void onDeleteLeaveAction(ActionEvent actionEvent) {
        if (event.getEventHost().equals(currentUser.getUsername())) {
            eventList.getEvents().remove(eventList.findEvent(event.getEventID()));
            teamList.getTeams().removeIf(team -> team.getEventID().equals(event.getEventID())); // ลบทีมใน team-list.csv

            // ลบ user join team ใน join-team.csv
            for (String username : teamHashMapGlobal.keySet()) { // ตามจำนวนคนใน team
                TeamList teamList = new TeamList(teamHashMapGlobal.get(username).getTeams());
                teamList.removeTeamByEvent(event);
                teamHashMap.put(username, teamList); // ยัด teamList ที่ลบ team ในแต่ละ user เข้าไปใน teamHashMap
            }

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
            joinTeamMap.writeData(teamHashMap);
            teamListDataSource.writeData(teamList);

            try {
                FXRouter.goTo("my-events", currentUser);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            if (hashMap.containsKey(event.getEventID())) {
                hashSet = hashMap.get(event.getEventID());
            }
            eventList.findEvent(event.getEventID()).delMember();
            hashSet.remove(currentUser.getUsername());
            hashMap.put(event.getEventID(), hashSet);

            eventListDatasource.writeData(eventList);
            mapDatasource.writeData(hashMap);
            try {
                FXRouter.goTo("my-events", currentUser);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void setEventData(Event event) {
        this.event = event;

        hashMap = new HashMap<>();
        mapDatasource = new UserEventMap("data", "join-event.csv");
        hashMap = mapDatasource.readData();

        if (hashMap.containsKey(event.getEventID())) {
            hashSet = hashMap.get(event.getEventID());
        }else {
            hashSet = new HashSet<>();
        }
        buttonVisible(!event.isEnd());
            //In Event
        if (hashSet.contains(currentUser.getUsername()) && hashMap.containsKey(event.getEventID())){
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
        if (event.isEnd()){
            viewjoinButton.setVisible(false);
            leaveButton.setVisible(false);
            staffButton.setVisible(false);
            editButton.setVisible(false);
        }
        Image image = new Image("file:"+event.getEventImagePath(),200,200,true,true);
        if(event.getEventImagePath().equals("null")){
            String imgpath = "/images/events/event-default.png";
            image = new Image(getClass().getResourceAsStream(imgpath),200,200,false,false);
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
