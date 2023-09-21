package cs211.project.controllers;

import cs211.project.models.Event;
import cs211.project.models.Team;
import cs211.project.services.FXRouter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;

public class MemberApplicationController {

    @FXML private ChoiceBox<String> teamChoiceBox;
    @FXML private void initialize() {}
    @FXML protected void onBackButtonClick() {
        try {
            FXRouter.goTo("event");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadData(Event event) {
        for (Team team : event.getTeamList().getTeams()) {
            teamChoiceBox.getItems().add(team.getTeamName());
        }
    }
    @FXML protected void onConfirmTeamButtonClick() {}
}
