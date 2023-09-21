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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


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
    private HashMap<String, TeamList> teamHashMapGlobal = joinTeamMap.readData();
    HashMap<String, TeamList> teamHashMap = new HashMap<>();
    private Datasource<EventList> eventListDatasource;
    private EventList eventList;
    private JoinEventMap mapDatasource ;
    private HashMap<String, Set<String>> hashMap; // Collect EventID
    private Set<String> hashSet;// Collect User
    private Event event;

    @FXML
    public void initialize() {
        this.eventListDatasource = new EventListDataSource();
        this.eventList = eventListDatasource.readData();
        this.hashMap = new HashMap<>();
        this.mapDatasource = new JoinEventMap();
        this.hashMap = mapDatasource.readData();
        this.teamListDataSource = new TeamListDataSource("data", "team-list.csv");
        this.teamList = teamListDataSource.readData();
        buttonVisible(true);
    }


    public void onJoinViewAction(ActionEvent actionEvent){
        /* ใช้สำหรับ User ที่เข้าร่วมอีเว้นแล้วและเพื่อไม่ให้เข้าร่วมซํ้า*/
        if (hashMap.containsKey(event.getEventID())) {
            hashSet = hashMap.get(event.getEventID());
        }else {
            hashSet = new HashSet<>();
        }
        /* ใช้สำหรับ User ที่เข้าร่วมอีเว้นแล้วและเพื่อไม่ให้เข้าร่วมซํ้า*/
        if (hashSet.contains(currentUser.getUserId()) || currentUser.getUserId().equals(event.getEventHostUser())){
            try {
                FXRouter.goTo("event",currentUser,event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            eventList.findEvent(event.getEventID()).addMember();
            hashSet.add(currentUser.getUserId());
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
    public void onLeaveEventButton(ActionEvent actionEvent) {
        
        if (hashMap.containsKey(event.getEventID())) {
            hashSet = hashMap.get(event.getEventID());
        }
        eventList.findEvent(event.getEventID()).delMember();
        hashSet.remove(currentUser.getUserId());
        hashMap.put(event.getEventID(), hashSet);

        eventListDatasource.writeData(eventList);
        mapDatasource.writeData(hashMap);
        try {
            FXRouter.goTo("my-events", currentUser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    public void setEvent(Event event) {
        this.event = event;

        hashMap = new HashMap<>();
        mapDatasource = new JoinEventMap();
        hashMap = mapDatasource.readData();

        if (hashMap.containsKey(event.getEventID())) {
            hashSet = hashMap.get(event.getEventID());
        }else {
            hashSet = new HashSet<>();
        }

        buttonVisible(!event.isEnd());
        if (event.isHostEvent(currentUser.getUserId())){

            leaveEventButton.setVisible(false);
        } else if (hashSet.contains(currentUser.getUserId())) {
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
            memberCountLabel.setText(event.getMember()+"");
        }else {
            memberCountLabel.setText(event.getMember() + "/" + event.getSlotMember());
        }

    }

    public void buttonVisible(Boolean is){
        forStaffButton.setVisible(is);
        manageEventButton.setVisible(is);
        leaveEventButton.setVisible(is);
    }

    public void onForStaffButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("select-team", currentUser, event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onManageEventButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("create-event",currentUser,event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onClickCard(MouseEvent mouseEvent) {
        try {
            FXRouter.goTo("event",currentUser,event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    // ลบ Event
//            if (event.getEventHostName().equals(currentUser.getUsername())) {
//        eventList.getEvents().remove(eventList.findEvent(event.getEventID()));
//        teamList.getTeams().removeIf(team -> team.getEventID().equals(event.getEventID())); // ลบทีมใน team-list.csv
//
//        // ลบ user join team ใน join-team.csv
//        for (String username : teamHashMapGlobal.keySet()) { // ตามจำนวนคนใน team
//            TeamList teamList = new TeamList(teamHashMapGlobal.get(username).getTeams());
//            teamList.removeTeamByEvent(event);
//            teamHashMap.put(username, teamList); // ยัด teamList ที่ลบ team ในแต่ละ user เข้าไปใน teamHashMap
//        }
//
//        // สร้าง path
//        String folderPath = event.getEventImagePath();
//        File fileToDelete = new File(folderPath);
//        // ลบไฟล์
//        if (fileToDelete.exists()) {
//
//            if (fileToDelete.delete()) {
//                System.out.println("Succes Delete");
//            } else {
//                System.out.println("Cant Delete");
//            }
//        } else {
//            System.out.println("Not Found");
//        }
//
//        // ลบไฟล์
//        eventListDatasource.writeData(eventList);
//        joinTeamMap.writeData(teamHashMap);
//        teamListDataSource.writeData(teamList);
//
//        try {
//            FXRouter.goTo("my-events", currentUser);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
