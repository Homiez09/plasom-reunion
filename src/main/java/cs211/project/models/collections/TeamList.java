package cs211.project.models.collections;

import cs211.project.models.Event;
import cs211.project.models.Team;
import cs211.project.models.User;


import java.util.ArrayList;
import java.util.HashMap;

public class TeamList {
    private ArrayList<Team> teams;

    public TeamList() {
        teams = new ArrayList<>();
    }

    public TeamList(HashMap<String, Team> teamHashMap) {
        teams = new ArrayList<>();
        for (String key: teamHashMap.keySet()) {
            teams.add(teamHashMap.get(key));
        }
    }

    public TeamList(ArrayList<Team> teams) {
        this.teams = teams;
    }

    public Team findTeamByName(String teamName) {
        for (Team team: teams) {
            if (team.isName(teamName)) {
                return team;
            }
        }
        return null;
    }

    public void addTeam(Team team) {
        teams.add(team);
    }

    public void addTeam(String eventID, String teamName, int maxSlotTeamMember) {
        teamName = teamName.trim();
        Team exist = findTeamByName(teamName);
        if (exist == null) {
            Team team = new Team(eventID, teamName, maxSlotTeamMember);
            teams.add(team);
        }
    }
    public void addTeam(String eventID, String teamName, int maxSlotTeamMember, String teamDescription){
        teamName = teamName.trim();
        teamDescription = teamDescription.trim();
        Team exist = findTeamByName(teamName);
        if(exist == null){
            Team team = new Team (eventID, teamName, maxSlotTeamMember, teamDescription);
            teams.add(team);
        }
    }

    public void addTeam(String eventID, String teamName, int maxSlotTeamMember, String teamDescription, String teamImagePath){
        teamName = teamName.trim();
        teamDescription = teamDescription.trim();
        teamImagePath = teamImagePath.trim();
        Team exist = findTeamByName(teamName);
        if(exist == null){
            Team team = new Team (eventID, teamName, maxSlotTeamMember, teamDescription, teamImagePath);
            teams.add(team);
        }
    }

    public void addTeam(String eventID, String teamName, int maxSlotTeamMember, String teamDescription, String teamImagePath, UserList memberList){
        teamName = teamName.trim();
        teamDescription = teamDescription.trim();
        teamImagePath = teamImagePath.trim();
        Team exist = findTeamByName(teamName);
        if(exist == null){
            Team team = new Team (eventID, teamName, maxSlotTeamMember, teamDescription, teamImagePath, memberList);
            teams.add(team);
        }
    }

    public void addTeam(String teamID, String teamName, String teamDescription, String teamImagePath, int maxSlotTeamMember, String createdAt, String eventID){
        teamName = teamName.trim();
        teamDescription = teamDescription.trim();
        teamImagePath = teamImagePath.trim();
        Team exist = findTeamByName(teamID);
        if(exist == null){
            Team team = new Team (teamID, teamName, teamDescription, teamImagePath, maxSlotTeamMember, createdAt, eventID);
            teams.add(team);
        }
    }

    // find teamlist by event id and user correct
    public ArrayList<Team> getTeamOfEvent(Event event) {
        String eventID = event.getEventID();
        ArrayList<Team> teamOfEvent = new ArrayList<>();
        for (Team team: teams) {
            if (team.getEventID().equals(eventID)) {
                teamOfEvent.add(team);
            }
        }
        return teamOfEvent;
    }

    public HashMap<String, Team> teamHashMap() {
        HashMap<String, Team> teamHashMapTemp= new HashMap<>();
        for (Team team: teams) {
            teamHashMapTemp.put(team.getTeamID(), team);
        }

        return teamHashMapTemp;
    }

    public void sortTeamByNewCreatedAt() { // from new to old
        teams.sort((team1, team2) -> team2.getCreatedAt().compareTo(team1.getCreatedAt()));
    }

    public void sortTeamByOldCreatedAt() { // from old to new
        teams.sort((team1, team2) -> team1.getCreatedAt().compareTo(team2.getCreatedAt()));
    }

    public void filterByAll() {
        return;
    }

    public void filterByRole(String role) {
        teams.removeIf(team -> !team.getRole().equals(role));
    }

    public void filterByBookmark() {
        teams.removeIf(team -> !team.isBookmarked());
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }
}
