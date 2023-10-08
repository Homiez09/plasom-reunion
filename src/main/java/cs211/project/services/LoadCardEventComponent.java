package cs211.project.services;

import cs211.project.componentControllers.*;
import cs211.project.models.Event;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;

public class LoadCardEventComponent {
    public LoadCardEventComponent(AnchorPane anchorPane, Event event,String card){
        switch (card){
            case "card-event":
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/card-event.fxml"));
                    AnchorPane loaded = loader.load();
                    CardEventController loadCard = loader.getController();
                    loadCard.setEvent(event);
                    anchorPane.getChildren().setAll(loaded);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "card-my-event":
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/card-my-event.fxml"));
                    AnchorPane loaded = loader.load();
                    CardMyEventController loadCard = loader.getController();
                    loadCard.setEventData(event);
                    anchorPane.getChildren().setAll(loaded);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
    }

}
