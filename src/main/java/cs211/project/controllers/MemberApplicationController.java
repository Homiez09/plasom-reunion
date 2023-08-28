package cs211.project.controllers;

import cs211.project.services.FXRouter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;

public class MemberApplicationController {
    ObservableList list = FXCollections.observableArrayList();
    @FXML private ChoiceBox<String> teamChoiceBox;
    @FXML private void initialize() {
        loadData();
    }
    @FXML protected void onBackButtonClick() {
        try {
            FXRouter.goTo("event");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadData() {
        list.removeAll(list);
        String a = "Team A";
        String b = "Team B";
        String c = "Team C";
        list.addAll(a,b,c);
        teamChoiceBox.getItems().addAll(list);
    }
    @FXML protected void onConfirmTeamButtonClick() {}
}
