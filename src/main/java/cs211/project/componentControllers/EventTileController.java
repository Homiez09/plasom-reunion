package cs211.project.componentControllers;

import cs211.project.models.Event;
import cs211.project.models.User;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class EventTileController {
    @FXML private ImageView eventTileImageView;
    @FXML private StackPane imageStackPane;
    @FXML
    Label eventNameLabel,eventDateLabel,eventPlaceLabel,eventMemberLabel;
    private User currentUser = (User) FXRouter.getData();
    private Image image;
    private  Event thisEvent;
    @FXML private void initialize() {

    }

    public void showEventTile(Event event) {
        thisEvent = event;

        if (event!=null) {
            eventNameLabel.setText(event.getEventName());
            eventDateLabel.setText(event.getEventDateStart());
            eventPlaceLabel.setText(event.getEventLocation());
            if (event.getSlotMember() == -1) {
                eventMemberLabel.setText(event.getUserInEvent()+"");
            }else {
                eventMemberLabel.setText(event.getUserInEvent() + "/" + event.getSlotMember());
            }
            image = new Image("file:"+event.getEventImagePath(),1280,1280,false,false);
            if(event.getEventImagePath().equals("null")){
                String imgpath = "/images/events/event-default-auth.png";
                image = new Image(getClass().getResourceAsStream(imgpath),1280,1280,false,false);
            }
            eventTileImageView.setImage(image);
            eventTileImageView.setFitWidth(150);
            eventTileImageView.setFitHeight(150);
            eventTileImageView.setPreserveRatio(true);
            Region transparentBackground = new Region();
            transparentBackground.setStyle("-fx-background-color: transparent;");
            imageStackPane.getChildren().addAll(transparentBackground);

        }
    }


    @FXML protected void onEventTileClick() {
        try {
            FXRouter.goTo("event",currentUser,thisEvent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
