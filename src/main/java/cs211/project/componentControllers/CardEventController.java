package cs211.project.componentControllers;

import cs211.project.models.*;
import cs211.project.models.collections.UserList;
import cs211.project.services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class CardEventController {
    //-------------------------------------------------------
    @FXML
    Label eventNameLabel,hostUsernameLabel,hostNameLabel,memberCountLabel;
    @FXML
    ImageView eventImage, profileImageView;
    //-------------------------------------------------------
    private Event event;
    private User currentUser = (User) FXRouter.getData();

    @FXML
    private void initialize() {}
    public void setEvent(Event data) {
        this.event = data;
        if (event != null) {
            eventNameLabel.setText(event.getEventName());
            hostUsernameLabel.setText(event.getEventHostUser().getUsername());
            hostNameLabel.setText(event.getEventHostUser().getDisplayName());

            if (event.getSlotMember() == -1) {
                memberCountLabel.setText(event.getUserInEvent()+"");
            }else {
                memberCountLabel.setText(event.getUserInEvent() + "/" + event.getSlotMember());
            }

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

    public void onClickCard(MouseEvent mouseEvent) {
            try {
                FXRouter.goTo("event",currentUser,event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

    }
}
