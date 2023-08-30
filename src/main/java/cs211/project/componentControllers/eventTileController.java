package cs211.project.componentControllers;

import cs211.project.models.User;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class eventTileController {
    @FXML private ImageView eventTileImageView;
    @FXML
    Label eventNameLabel,eventDateLabel,eventPlaceLabel;
    private User currentUser = (User) FXRouter.getData();
    @FXML private void initialize() {
        showEventTile();
    }

    private void showEventTile() {
        eventNameLabel.setText("Event Name");
        eventDateLabel.setText("Event Date");
        eventPlaceLabel.setText("Event Place");
        Image image = new Image(getClass().getResource("/images/home/event2.png").toString());
        eventTileImageView.setImage(image);
    }

    @FXML protected void onEventTileClick() {
        try {
            FXRouter.goTo("event",currentUser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


