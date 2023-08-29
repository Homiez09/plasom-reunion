package cs211.project.services;

import cs211.project.models.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LoadEventComponent {
    public LoadEventComponent(Event event, AnchorPane eventAnchorPane) {
        FXMLLoader eventComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/event-component.fxml"));
        try {
            AnchorPane eventComponent = eventComponentLoader.load();
            eventAnchorPane.getChildren().add(eventComponent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public LoadEventComponent(AnchorPane eventAnchorPane) {
        this(null, eventAnchorPane);
    }

}
