package cs211.project.services;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LoadCardEventUpcomingForAuth {
    public LoadCardEventUpcomingForAuth(AnchorPane upcomingZoneAnchorPane) {
        FXMLLoader cardUpcomingLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/card-upcoming.fxml"));
        try {
            AnchorPane cardUpcoming = cardUpcomingLoader.load();
            upcomingZoneAnchorPane.getChildren().add(cardUpcoming);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
