package cs211.project.componentControllers;

import cs211.project.models.*;
import cs211.project.models.collections.UserList;
import cs211.project.services.CreateProfileCircle;
import cs211.project.services.Datasource;
import cs211.project.services.ImagePathFormat;
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
    ImageView eventImage, profileImageView;
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
    public void setEvent(Event data) {
        this.event = data;
        if (event != null) {
            eventNameLabel.setText(event.getEventName());
            hostUsernameLabel.setText(event.getEventHostUser().getUsername());
            hostNameLabel.setText(event.getEventHostUser().getDisplayName());

            ImagePathFormat pathFormat = new ImagePathFormat(event.getEventHostUser().getImagePath());
            profileImageView.setImage(new Image(pathFormat.toString(), 30, 30, false, false));
            new CreateProfileCircle(profileImageView, 30);

            Image image = new Image("file:" + event.getEventImagePath(), 200, 200, false, false);
            if (event.getEventImagePath().equals("null")) {
                String imgpath = "/images/events/event-default.png";
                image = new Image(getClass().getResourceAsStream(imgpath), 200, 200, false, false);
            }
            eventImage.setImage(image);
        }
    }
    public void onJoinEventAcition(ActionEvent actionEvent) {
    }

    public void onJoinStaffButton(ActionEvent actionEvent) {
    }
}
