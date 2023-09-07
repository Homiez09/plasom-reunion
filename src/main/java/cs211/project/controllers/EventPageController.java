package cs211.project.controllers;

import cs211.project.models.User;
import cs211.project.services.FXRouter;
import cs211.project.services.LoadNavbarComponent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.io.IOException;

public class EventPageController {
    @FXML
    private AnchorPane navbarAnchorPane,staffApplicationAnchorPane;
    @FXML private Button editEventButton;
    @FXML private void initialize() {
        new LoadNavbarComponent(user, navbarAnchorPane);
        staffApplicationAnchorPane.setVisible(false);
        if (true) { //condition check if the user is organizer or not
            editEventButton.setVisible(true);
        } else {
            editEventButton.setVisible(false);
        }
    }

    private User user = (User) FXRouter.getData();
    @FXML protected void onEditButtonClick() {
        try {
            FXRouter.goTo("create-event", user); //send event class as second parameter
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void onBackButtonClick(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("home");
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
