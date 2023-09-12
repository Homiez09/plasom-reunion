package cs211.project.componentControllers;

import cs211.project.models.Event;
import cs211.project.models.User;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class EventTileController {
    @FXML private ImageView eventTileImageView;
    @FXML
    Label eventNameLabel,eventDateLabel,eventPlaceLabel;
    private User currentUser = (User) FXRouter.getData();
    private Image image;
    @FXML private void initialize() {

    }

    public void showEventTile(Event event) {
        String imgpath = "/images/events/event-default.png";
        image = new Image(getClass().getResourceAsStream(imgpath),300,300,false,false);
        if (event!=null) {
            eventNameLabel.setText(event.getEventName());
            eventDateLabel.setText(event.getEventDateStart());
            eventPlaceLabel.setText(event.getEventLocation());
            if(!event.getEventImagePath().isEmpty()){
                image = new Image("file:"+event.getEventImagePath(),300,300,true,true);
            }
            eventTileImageView.setImage(image);
        }
    }

    @FXML protected void onEventTileClick() {
        try {
            FXRouter.goTo("event",currentUser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


