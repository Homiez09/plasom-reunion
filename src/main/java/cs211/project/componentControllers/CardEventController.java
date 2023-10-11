package cs211.project.componentControllers;

import cs211.project.models.*;
import cs211.project.services.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class CardEventController {
    //-------------------------------------------------------
    @FXML Label eventNameLabel,hostUsernameLabel,hostNameLabel,memberCountLabel;
    @FXML ImageView eventImage, profileImageView;
    //-------------------------------------------------------
    private Event event;
    private final User currentUser = (User) FXRouter.getData();
    @FXML
    private void initialize() {}

    public void setEvent(Event data) {
        this.event = data;
        if (event != null) {
            eventNameLabel.setText(event.getEventName());
            hostUsernameLabel.setText("@" + event.getEventHostUser().getUsername());
            hostNameLabel.setText(event.getEventHostUser().getDisplayName());
            if (event.isJoinEvent()) {
                if (!event.isFull()) {
                    if (event.getSlotMember() == -1) {
                        memberCountLabel.setText(event.getUserInEvent() + "");
                    } else {
                        memberCountLabel.setText(event.getUserInEvent() + "/" + event.getSlotMember());
                    }
                }else {
                    memberCountLabel.setText("Full");
                }
            }else {
                memberCountLabel.setText("Closed");
            }

            ImagePathFormat pathFormat = new ImagePathFormat(event.getEventHostUser().getImagePath());
            profileImageView.setImage(new Image(pathFormat.toString(), 500, 500, false, false));
            new CreateProfileCircle(profileImageView, 15);

            Image image = new Image("file:" + event.getEventImagePath(), 1440, 1800, false, false);
            if (event.getEventImagePath().equals("null")) {
                String imgPath = "/images/events/event-default-auth.png";
                image = new Image(getClass().getResourceAsStream(imgPath), 1440, 1800, false, false);
            }
            new BorderImagView(eventImage).setRecClip(20);
            eventImage.setImage(image);
        }
    }

    @FXML private void onClickCard() {
            try {
                FXRouter.goTo("event",currentUser,event,"card-event");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

    }
}
