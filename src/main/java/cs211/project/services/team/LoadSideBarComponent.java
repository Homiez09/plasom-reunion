package cs211.project.services.team;

import cs211.project.controllers.component.sideBarControllers.SideBarTeamController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LoadSideBarComponent {
    FXMLLoader sideBarComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/side-bar/sidebar-team.fxml"));
    AnchorPane sideBarComponent;

    SideBarTeamController sideBarTeamController;

    public LoadSideBarComponent() {
        try {
            sideBarComponent = sideBarComponentLoader.load();
            sideBarTeamController = sideBarComponentLoader.getController();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public LoadSideBarComponent(AnchorPane sideBarAnchorPane) {
        try {
            sideBarComponent = sideBarComponentLoader.load();
            sideBarAnchorPane.getChildren().add(sideBarComponent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public AnchorPane getSideBarComponent() {
        return sideBarComponent;
    }
    public SideBarTeamController getController() {
        return sideBarTeamController;
    }
}
