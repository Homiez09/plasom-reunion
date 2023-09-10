package cs211.project.componentControllers;

import cs211.project.controllers.MyEventController;
import cs211.project.models.*;
import cs211.project.models.collections.*;
import cs211.project.services.*;
import cs211.project.services.UserListDataSource;
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
import java.util.Set;
import java.util.TreeSet;

public class EventComponentController extends MyEventController {
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
    private HashMap<User, Set<Event>> userMap;
    private Set<Event> treeSet;
    private Event event;

    @FXML
    public void initialize() {
        userListDatasource = new UserListDataSource("data","user-list.csv");
        userList = userListDatasource.readData();

        mapDatasource = new UserEventMap("data","user-event.csv");
        userMap = mapDatasource.readData();








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
    public void onButtonAction(ActionEvent actionEvent){
        treeSet = new HashSet<>();
        mapDatasource = new UserEventMap("data","user-event.csv");
        userMap = mapDatasource.readData();

        if(!userMap.containsKey(currentUser)){
            userMap.put(currentUser,treeSet);
            System.out.println(userMap.get(currentUser));
        }

        userMap.put(currentUser,treeSet);
        System.out.println(userMap.get(currentUser));
        mapDatasource.writeData(userMap);




    }

    public void setEventData(Event event) {
        this.event = event;
        Image image;
        if (event.isEnd()) {
            buttonVisible(false);
        }else {
            buttonVisible(true);
        }
        if (currentUser.getEvents().contains(event)){
            viewjoinButton.setText("View");
        }else {
            viewjoinButton.setText("Join");
        }
        image = new Image(getClass().getResource("/images/events/event-default.png").toExternalForm());
        try {
            image = new Image("file:"+event.getEventImagePath(),true);
        }catch (Exception e){
            e.printStackTrace();
        }


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
