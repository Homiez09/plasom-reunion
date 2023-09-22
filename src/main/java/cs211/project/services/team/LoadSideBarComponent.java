package cs211.project.services.team;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LoadSideBarComponent {
    public LoadSideBarComponent(AnchorPane sideBarAnchorPane) {
        try {
            FXMLLoader sideBarComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/side-bar/sidebar-team.fxml"));
            AnchorPane sideBarComponent = sideBarComponentLoader.load();
            sideBarAnchorPane.getChildren().add(sideBarComponent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
