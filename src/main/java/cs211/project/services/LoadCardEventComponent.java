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
                    CardEventController cardload = loader.getController();
                    cardload.setEvent(event);

                    anchorPane.getChildren().setAll(loaded);
                    AnimateComponent(loaded);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "card-my-event":
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/card-my-event.fxml"));
                    AnchorPane loaded = loader.load();
                    CardMyEventController cardload = loader.getController();
                    cardload.setEvent(event);

                    anchorPane.getChildren().setAll(loaded);
                    AnimateComponent(loaded);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
    }

    //-------------Animate Pop for any AnchorPane-------------\\
    private void AnimateComponent(AnchorPane anchorPane) {
        ScaleTransition scaleIn = new ScaleTransition(Duration.seconds(0.2), anchorPane);
        scaleIn.setToX(1.1);
        scaleIn.setToY(1.1);
        ScaleTransition scaleOut = new ScaleTransition(Duration.seconds(0.2), anchorPane);
        scaleOut.setToX(1);
        scaleOut.setToY(1);
        anchorPane.setOnMouseEntered(event -> {scaleIn.play();});
        anchorPane.setOnMouseExited(event -> {scaleOut.play();});
    }
    //-------------Animate Zoom for any AnchorPane-------------\\
}
