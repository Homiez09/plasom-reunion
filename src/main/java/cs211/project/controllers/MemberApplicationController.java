package cs211.project.controllers;

import cs211.project.models.Event;
import cs211.project.models.Team;
import cs211.project.models.User;
import cs211.project.models.collections.TeamList;
import cs211.project.services.FXRouter;
import cs211.project.services.JoinTeamMap;
import cs211.project.services.TeamListDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;
import java.util.HashMap;

public class MemberApplicationController {
    TeamListDataSource datasource;
    TeamList teamList;
    User user = (User) FXRouter.getData();
    Event event = (Event) FXRouter.getData2();
    JoinTeamMap joinTeamMap = new JoinTeamMap();
    TeamListDataSource teamListDataSource = new TeamListDataSource("data", "team-list.csv");

    private String teamName;

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
        if (team.isFull()) {
            System.out.println("Team is full");
            return;
        }
        team.setRole("Member");
        if(teamListHashMap.get(user.getUsername()) == null) {
            teamListHashMap.put(user.getUsername(), new TeamList());
        }
        for (Team teamCheck : teamListHashMap.get(user.getUsername()).getTeams()) {
            if (teamCheck.getTeamID().equals(team.getTeamName())) {
                System.out.println("Already join this team");
                return;
            }
        }
        teamListHashMap.get(user.getUsername()).getTeams().add(team);
        joinTeamMap.writeData(teamListHashMap);
        try {
            FXRouter.goTo("select-team", user, event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
