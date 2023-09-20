package cs211.project.componentControllers;

import cs211.project.models.*;
import cs211.project.models.collections.UserList;
import cs211.project.services.Datasource;
import cs211.project.services.UserListDataSource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardEventController {
    //-------------------------------------------------------
    @FXML
    Label eventNameLabel,hostUsernameLabel,hostNameLabel;
    @FXML
    ImageView eventImage,userImg;
    //-------------------------------------------------------

    private Event event;
    private Datasource<UserList> datasource;
    private UserList userList;
    private User userHost ;

    @FXML
    private void initialize() {
        this.datasource = new UserListDataSource("data","user-list.csv");
        this.userList = datasource.readData();




    }
    public void setEvent(Event data){
        this.event = data;
        System.out.println(event.getEventHostName());
        userHost = userList.findUsername(event.getEventHostName());
        eventNameLabel.setText(event.getEventName());
        if (userHost!=null) {
            hostUsernameLabel.setText(userHost.getUsername());
            hostNameLabel.setText(userHost.getDisplayName());
        }


        Image image = new Image("file:"+event.getEventImagePath(),200,200,true,true);
        if(event.getEventImagePath().equals("null")){
            String imgpath = "/images/events/event-default.png";
            image = new Image(getClass().getResourceAsStream(imgpath),200,200,false,false);
        }
        eventImage.setImage(image);

    }
    public void onJoinEventAcition(ActionEvent actionEvent) {
    }

    public void onJoinStaffButton(ActionEvent actionEvent) {
    }
}
