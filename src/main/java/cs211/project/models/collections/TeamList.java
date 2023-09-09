package cs211.project.models.collections;

import cs211.project.models.Event;
import cs211.project.models.Team;
import cs211.project.models.User;

import java.util.ArrayList;

public class TeamList {
    private ArrayList<Team> teams;

    public TeamList() {
        teams = new ArrayList<>();
    }


    public Team findTeam(String teamID) {
        for (Team team: teams) {
            if (team.isID(teamID)) {
                return team;
            }
        }
        return null;
    }

    public Team findTeamByName(String teamName) {
        for (Team team: teams) {
            if (team.isName(teamName)) {
                return team;
            }
        }
        return null;
    }

    public void addTeam(String teamName, int maxSlotTeamMember) {
        teamName = teamName.trim();
        Team exist = findTeamByName(teamName);
        if (exist == null) {
            teams.add(new Team(teamName, maxSlotTeamMember));
        }
    }
    public void addTeam(String teamName, int maxSlotTeamMember, String teamDescription){
        teamName = teamName.trim();
        teamDescription = teamDescription.trim();
        Team exist = findTeam(teamName);
        if(exist == null){
            teams.add(new Team (teamName, maxSlotTeamMember, teamDescription));
        }
    }

    public void addTeam(String teamName, int maxSlotTeamMember, String teamDescription, String teamImagePath){
        teamName = teamName.trim();
        teamDescription = teamDescription.trim();
        teamImagePath = teamImagePath.trim();
        Team exist = findTeam(teamName);
        if(exist == null){
            teams.add(new Team (teamName, maxSlotTeamMember, teamDescription, teamImagePath));
        }
    }

    public void addTeam(String teamName, int maxSlotTeamMember, String teamDescription, String teamImagePath, UserList memberList){
        teamName = teamName.trim();
        teamDescription = teamDescription.trim();
        teamImagePath = teamImagePath.trim();
        Team exist = findTeam(teamName);
        if(exist == null){
            teams.add(new Team (teamName, maxSlotTeamMember, teamDescription, teamImagePath, memberList));
        }
    }

    public void addTeam(String teamID, String teamName, String teamDescription, String teamImagePath, int maxSlotTeamMember){
        teamName = teamName.trim();
        teamDescription = teamDescription.trim();
        teamImagePath = teamImagePath.trim();
        Team exist = findTeam(teamID);
        if(exist == null){
            teams.add(new Team (teamID, teamName, teamDescription, teamImagePath, maxSlotTeamMember));
        }
    }

    // find teamlist by event id and user correct
    public ArrayList<Team> getTeamOfEvent(Event event) {
        ArrayList<Team> teams = new ArrayList<>();
        // todo: find teamlist by event id and user correct

        return teams;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }
}
