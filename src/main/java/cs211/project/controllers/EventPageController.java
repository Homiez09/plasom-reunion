package cs211.project.controllers;

import cs211.project.models.User;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class EventPageController {
    @FXML
    private AnchorPane navbarAnchorPane,staffApplicationAnchorPane;
    @FXML private void initialize() {
        loadNavbarComponent();
        staffApplicationAnchorPane.setVisible(false);
    }

    private User user = (User) FXRouter.getData();

    private void loadNavbarComponent() {
        FXMLLoader navbarComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/navbar.fxml"));
        try {
            AnchorPane navbarComponent = navbarComponentLoader.load();
            navbarAnchorPane.getChildren().add(navbarComponent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML protected void onApplyStaffButtonClick() {
        if (!staffApplicationAnchorPane.isVisible()) {
            staffApplicationAnchorPane.setVisible(true);
            FXMLLoader staffApplicationLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/member-application.fxml"));
            try {
                AnchorPane staffApplicationWindow = staffApplicationLoader.load();
                staffApplicationAnchorPane.getChildren().add(staffApplicationWindow);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
