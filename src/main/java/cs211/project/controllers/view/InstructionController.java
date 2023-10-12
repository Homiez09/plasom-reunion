package cs211.project.controllers.view;

import cs211.project.services.LoadTopBarComponent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class InstructionController {
    @FXML private AnchorPane topBarAnchorPane;
    @FXML private AnchorPane instructionAnchorPane;

    @FXML private void initialize() {
        new LoadTopBarComponent(topBarAnchorPane);
        loadGuest();
    }

    @FXML private void onGuestButtonClick() {
        loadGuest();
    }
    @FXML private void onUserButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/instruction/info-user.fxml"));
            AnchorPane loaded = loader.load();
            instructionAnchorPane.getChildren().setAll(loaded);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML private void onOrganizerButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/instruction/organizer.fxml"));
            AnchorPane loaded = loader.load();
            instructionAnchorPane.getChildren().setAll(loaded);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML private void onTeamButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/instruction/team-staff.fxml"));
            AnchorPane loaded = loader.load();
            instructionAnchorPane.getChildren().setAll(loaded);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadGuest() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/instruction/info-guest.fxml"));
            AnchorPane loaded = loader.load();
            instructionAnchorPane.getChildren().setAll(loaded);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
