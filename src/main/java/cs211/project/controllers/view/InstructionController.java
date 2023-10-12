package cs211.project.controllers.view;

import cs211.project.services.LoadTopBarComponent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class InstructionController {
    @FXML private AnchorPane topBarAnchorPane;
    @FXML private AnchorPane instructionAnchorPane;
    @FXML private ScrollPane instructionScrollPane;

    @FXML private void initialize() {
        new LoadTopBarComponent(topBarAnchorPane);
        loadInstruction("guest");
    }

    @FXML private void onGuestButtonClick() {
        loadInstruction("guest");
    }
    @FXML private void onUserButtonClick() {
        loadInstruction("user");
    }
    @FXML private void onOrganizerButtonClick() {
       loadInstruction("organizer");
    }
    @FXML private void onTeamButtonClick() {
        loadInstruction("team");
    }

    private void loadInstruction(String page) {
        switch (page) {
            case "guest" :
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/instruction/info-guest.fxml"));
                    AnchorPane loaded = loader.load();
                    instructionAnchorPane.getChildren().setAll(loaded);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "user" :
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/instruction/info-user.fxml"));
                    AnchorPane loaded = loader.load();
                    instructionAnchorPane.getChildren().setAll(loaded);
                }catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "organizer" :
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/instruction/organizer.fxml"));
                    AnchorPane loaded = loader.load();
                    instructionAnchorPane.getChildren().setAll(loaded);
                }catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "team" :
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/instruction/team-staff.fxml"));
                    AnchorPane loaded = loader.load();
                    instructionAnchorPane.getChildren().setAll(loaded);
                }catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
        instructionScrollPane.setVvalue(0);
    }
}
