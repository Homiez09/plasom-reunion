package cs211.project.services;

import cs211.project.models.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LoadTopbarComponent {
    public LoadTopbarComponent(User user, AnchorPane topBarAnchorPane, boolean hideBackButton) {
        FXMLLoader topBarComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/topbar.fxml"));
        try {
            AnchorPane topBarComponent = topBarComponentLoader.load();
            if (hideBackButton) topBarComponent.getChildren().get(0).setVisible(false);
            topBarAnchorPane.getChildren().add(topBarComponent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public LoadTopbarComponent(AnchorPane topBarAnchorPane) {
        this(null, topBarAnchorPane,false);
    }

    public LoadTopbarComponent(AnchorPane topBarAnchorPane, boolean hideBackButton) {
        this(null, topBarAnchorPane, hideBackButton);
    }
}
