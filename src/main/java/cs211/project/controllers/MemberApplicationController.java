package cs211.project.controllers;

import cs211.project.models.*;
import cs211.project.models.Team;
import cs211.project.models.User;
import cs211.project.models.collections.*;
import cs211.project.services.FXRouter;
import cs211.project.services.*;

import cs211.project.services.TeamListDataSource;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;
import java.util.HashMap;

public class MemberApplicationController {
    User user = (User) FXRouter.getData();
    Event event = (Event) FXRouter.getData2();
    JoinTeamMap joinTeamMap = new JoinTeamMap();
    TeamListDataSource teamListDataSource = new TeamListDataSource("data", "team-list.csv");

    private String teamName;

    @FXML private ChoiceBox<String> teamChoiceBox;
    private User currentUser;
    private Event currentEvent;
    private HashMap<String,TeamList> teamListHashMap;
    private TeamList joinTeam;
    @FXML private void initialize() {}
    @FXML protected void onBackButtonClick() {
        try {
            FXRouter.goTo("event");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadData(Event event) {
        // todo: ให้โชว์แต่ team ที่สามารถเข้าร่ววมได้ (team.isFull, team ที่เข้าร่วมแล้ว จะไม่โชว์)
        for (Team team : event.getTeamList().getTeams()) {
            teamChoiceBox.getItems().add(team.getTeamName());
        }
    }
    @FXML protected void onConfirmTeamButtonClick() {

        HashMap<String, TeamList> teamListHashMap = joinTeamMap.readData();
        teamName = teamChoiceBox.getValue();

        TeamListDataSource teamListDataSource = new TeamListDataSource("data", "team-list.csv");
        TeamList teamList = teamListDataSource.readData();


        Team team = teamList.findTeamByNameInEvent(teamName, event.getEventID());
        if (user.isBanFromTeam(team.getTeamID())) {
            System.out.println("You are banned from this team");
            return;
        }
        if (team.isFull()) {
            System.out.println("Team is full");
            return;
        }
        team.setRole("Member");
        if(teamListHashMap.get(user.getUsername()) == null) {
            teamListHashMap.put(user.getUsername(), new TeamList());
        }
        for (Team teamCheck : teamListHashMap.get(user.getUsername()).getTeams()) {
            if (teamCheck.getTeamID().equals(team.getTeamID())) {
                System.out.println("Already join this team");
                return;
            }
        }
        teamListHashMap.get(user.getUsername()).getTeams().add(team);
        joinTeamMap.writeData(teamListHashMap);
        try {
            FXRouter.goTo("select-team", currentUser, currentEvent);
            teamListHashMap = joinTeamMap.readData();
            teamName = teamChoiceBox.getValue();
            teamListDataSource = new TeamListDataSource("data", "team-list.csv");
            teamList = teamListDataSource.readData();
            team = teamList.findTeamByNameInEvent(teamName, event.getEventID());
            team.setRole("Member");
            if (teamListHashMap.get(user.getUsername()) == null) {
                teamListHashMap.put(user.getUsername(), new TeamList());
                teamListHashMap.get(user.getUsername()).getTeams().add(team);
            }
            teamListHashMap.get(user.getUsername()).getTeams().add(team);
            joinTeamMap.writeData(teamListHashMap);
            try {
                FXRouter.goTo("select-team", user, event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
