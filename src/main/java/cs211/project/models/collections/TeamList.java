package cs211.project.models.collections;

import cs211.project.models.Team;
import cs211.project.models.User;

import java.util.ArrayList;

public class TeamList {
    private ArrayList<Team> teams;

    public TeamList() {
        teams = new ArrayList<>();
    }


    public Team findTeamName(String teamName) {
        for (Team team: teams) {
            if (team.isName(teamName)) {
                return team;
            }
        }
        return null;
    }

    public void addTeam(String teamName, String teamDescription, String teamImagePath, User teamManager, int maxSlotTeamMember){
        teamName = teamName.trim();
        Team exist = findTeamName(teamName);
        if(exist == null){
            teams.add(new Team(teamName, teamDescription, teamImagePath, teamManager, maxSlotTeamMember));
        }
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }
}
