package cs211.project.services;

import cs211.project.models.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class LoadNavbarComponent {
    public LoadNavbarComponent(User user, AnchorPane navbarAnchorPane) {
        FXMLLoader navbarComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/navbar.fxml"));
        try {
            AnchorPane navbarComponent = navbarComponentLoader.load();
            navbarAnchorPane.getChildren().add(navbarComponent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public LoadNavbarComponent(AnchorPane navbarAnchorPane) {
        this(null, navbarAnchorPane);
    }
}
