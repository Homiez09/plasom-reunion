package cs211.project.services;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LoadTopBarComponent {
    public LoadTopBarComponent(AnchorPane topBarAnchorPane, boolean hideBackButton) {
        FXMLLoader topBarComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/topbar.fxml"));
        try {
            AnchorPane topBarComponent = topBarComponentLoader.load();
            if (hideBackButton) topBarComponent.getChildren().get(0).setVisible(false);
            topBarAnchorPane.getChildren().add(topBarComponent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public LoadTopBarComponent(AnchorPane topBarAnchorPane) {
        this(topBarAnchorPane,false);
    }

}
