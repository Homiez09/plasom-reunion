package cs211.project.services;

import cs211.project.controllers.component.CardEventController;
import cs211.project.controllers.component.CardMyEventController;
import cs211.project.controllers.component.EventTileController;
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
                        AnimateComponent(anchorPane);
                    }catch (IOException e) {
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
                    }catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "tile-event":
                    try {
                        FXMLLoader eventTileLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/event-tile-new.fxml"));
                        AnchorPane load = eventTileLoader.load();
                        EventTileController eventTileController = eventTileLoader.getController();
                        eventTileController.showEventTile(event);
                        anchorPane.getChildren().setAll(load);
                        AnimateComponent(load);

                    }catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
        }
    }
    private void AnimateComponent(AnchorPane anchorPane) {
        ScaleTransition scaleIn = new ScaleTransition(Duration.seconds(0.2), anchorPane);
        ScaleTransition scaleOut = new ScaleTransition(Duration.seconds(0.2), anchorPane);
        scaleIn.setToX(1.1);
        scaleIn.setToY(1.1);
        scaleOut.setToX(1);
        scaleOut.setToY(1);
        anchorPane.setOnMouseEntered(event -> {scaleIn.play();});
        anchorPane.setOnMouseExited(event -> {scaleOut.play();});
    }
}
