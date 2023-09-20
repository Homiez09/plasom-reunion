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

    public TeamList(TeamList teamList) {
        teams = new ArrayList<>();
        for (Team team: teamList.getTeams()) {
            teams.add(team);
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

    public Team findTeamByID(String teamID) {
        for (Team team: teams) {
            if (team.getTeamID().equals(teamID)) {
                return team;
            }
        }
        return null;
    }

    public Team findTeamByNameInEvent(String teamName, String eventID) {
        for (Team team: teams) {
            if (team.isName(teamName) && team.getEventID().equals(eventID)) {
                return team;
            }
        }
        return null;
    }

    public void removeTeamByEvent(Event event) {
        teams.removeIf(team -> team.getEventID().equals(event.getEventID()));
    }

    public void removeTeam(String teamID) {
        teams.removeIf(team -> team.getTeamID().equals(teamID));
    }

    public void addTeam(Team team) {
        teams.add(team);
    }

    public void addTeam(String eventID, String teamName, String startDate, String endDate, int maxSlotTeamMember, String teamDescription){
        teamName = teamName.trim();
        teamDescription = teamDescription.trim();
        Team exist = findTeamByName(teamName);
        if(exist == null){
            Team team = new Team (eventID, teamName, startDate, endDate, maxSlotTeamMember, teamDescription);
            teams.add(team);
        }
    }

    public void addTeam(String teamID, String teamName, String teamDescription, String startDate, String endDate, int maxSlotTeamMember, String createdAt, String eventID){
        // load from file
        teamName = teamName.trim();
        teamDescription = teamDescription.trim();
        Team team = new Team (teamID, teamName, teamDescription, startDate, endDate, maxSlotTeamMember, createdAt, eventID);
        teams.add(team);
    }

    public void sortTeamByNewCreatedAt() { // from new to old
        teams.sort((team1, team2) -> team2.getCreatedAt().compareTo(team1.getCreatedAt()));
    }

    public void sortTeamByOldCreatedAt() { // from old to new
        teams.sort((team1, team2) -> team1.getCreatedAt().compareTo(team2.getCreatedAt()));
    }

    public void filterByAll() {
        if (teams == null) return;
        return;
    }

    public void filterByRole(String role) {
        if (teams == null) return;
        teams.removeIf(team -> !team.getRole().equals(role));
    }

    public void filterByBookmark() {
        if (teams == null) return;
        teams.removeIf(team -> !team.isBookmarked());
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

    public ArrayList<Team> getTeams() {
        return teams;
    }
}
