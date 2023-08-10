package cs211.project.componentControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class eventTileController {
    @FXML private ImageView eventTileImageView;
    @FXML
    Label eventNameLabel,eventDateLabel,eventPlaceLabel;

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
}


