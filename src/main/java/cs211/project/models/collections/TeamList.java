package cs211.project.models.collections;

import cs211.project.models.Event;
import cs211.project.models.Team;
import cs211.project.models.User;

import java.util.ArrayList;
import java.util.HashMap;

public class TeamList {
    private HashMap<String, Team> teams;

    public TeamList() {
        teams = new HashMap<>();
    }

    public Team findTeamByName(String teamName) {
        for (Team team: teams.values()) {

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
            Team team = new Team(teamName, maxSlotTeamMember);
            teams.put(team.getTeamID(), team);
        }
    }
    public void addTeam(String teamName, int maxSlotTeamMember, String teamDescription){
        teamName = teamName.trim();
        teamDescription = teamDescription.trim();
        Team exist = findTeamByName(teamName);
        if(exist == null){
            Team team = new Team (teamName, maxSlotTeamMember, teamDescription);
            teams.put(team.getTeamID(), team);
        }
    }

    public void addTeam(String teamName, int maxSlotTeamMember, String teamDescription, String teamImagePath){
        teamName = teamName.trim();
        teamDescription = teamDescription.trim();
        teamImagePath = teamImagePath.trim();
        Team exist = findTeamByName(teamName);
        if(exist == null){
            Team team = new Team (teamName, maxSlotTeamMember, teamDescription, teamImagePath);
            teams.put(team.getTeamID(), team);
        }
    }

    public void addTeam(String teamName, int maxSlotTeamMember, String teamDescription, String teamImagePath, UserList memberList){
        teamName = teamName.trim();
        teamDescription = teamDescription.trim();
        teamImagePath = teamImagePath.trim();
        Team exist = findTeamByName(teamName);
        if(exist == null){
            Team team = new Team (teamName, maxSlotTeamMember, teamDescription, teamImagePath, memberList);
            teams.put(team.getTeamID(), team);
        }
    }

    public void addTeam(String teamID, String teamName, String teamDescription, String teamImagePath, int maxSlotTeamMember){
        teamName = teamName.trim();
        teamDescription = teamDescription.trim();
        teamImagePath = teamImagePath.trim();
        Team exist = findTeamByName(teamID);
        if(exist == null){
            Team team = new Team (teamID, teamName, teamDescription, teamImagePath, maxSlotTeamMember);
            teams.put(team.getTeamID(), team);
        }
    }

    // find teamlist by event id and user correct
    public ArrayList<Team> getTeamOfEvent(Event event) {
        ArrayList<Team> teams = new ArrayList<>();
        // todo: find teamlist by event id and user correct

        return teams;
    }

    public HashMap<String, Team> getTeams() {
        return teams;
    }
}
