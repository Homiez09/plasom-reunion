package cs211.project.services;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LoadTopbarComponent {
    public LoadTopbarComponent(AnchorPane navbarAnchorPane) {
        FXMLLoader navbarComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/topbar.fxml"));
        try {
            AnchorPane navbarComponent = navbarComponentLoader.load();
            navbarAnchorPane.getChildren().add(navbarComponent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
