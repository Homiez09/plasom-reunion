package cs211.project.controllers;

import cs211.project.models.Event;
import cs211.project.models.Team;
import cs211.project.models.User;
import cs211.project.models.collections.TeamList;
import cs211.project.services.FXRouter;
import cs211.project.services.JoinTeamMap;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;
import java.util.HashMap;

public class MemberApplicationController {

    @FXML private ChoiceBox<String> teamChoiceBox;
    private User currentUser;
    private Event currentEvent;
    private JoinTeamMap joinTeamMap;
    private HashMap<String,TeamList> teamListHashMap;
    private TeamList teamList;
    private TeamList joinTeam;
    @FXML private void initialize() {}
    @FXML protected void onBackButtonClick() {
        try {
            FXRouter.goTo("event");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadData(Event event, User user ) {
        this.currentUser = user;
        this.currentEvent = event;

        for (Team team : event.getTeamList().getTeams()) {
            teamChoiceBox.getItems().add(team.getTeamName());
        }
    }
    @FXML protected void onConfirmTeamButtonClick() {
        this.joinTeamMap = new JoinTeamMap();
        this.teamListHashMap = joinTeamMap.readData();
        this.teamList = currentEvent.getTeamList();
        this.joinTeam = new TeamList();
        for (Team team:teamList.getTeams()) {

            if (teamChoiceBox.getItems().equals(team.getTeamName())){
                joinTeam.getTeams().add(team);
                break;
            }

        }
        teamListHashMap.put(currentUser.getUsername(),joinTeam);
        joinTeamMap.writeData(teamListHashMap);


        try {
            FXRouter.goTo("select-team",currentUser, currentEvent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
