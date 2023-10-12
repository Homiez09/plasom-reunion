package cs211.project.models.collections;

import cs211.project.models.Event;
import cs211.project.models.Team;
import cs211.project.models.User;
import cs211.project.services.JoinTeamMap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class TeamList {
    private final ArrayList<Team> teams;

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
        teams.addAll(teamList.getTeams());
    }

    public TeamList(ArrayList<Team> teams) {
        this.teams = teams;
    }

    public void addTeam(Team team) {
        teams.add(team);
    }

    public Team addTeam(String eventID, User teamHostUser, String teamName, String teamDescription, String startDate, String endDate, int maxSlotTeamMember) {
        // create team
        teamName = teamName.trim();
        teamDescription = teamDescription.trim();
        Team team = new Team (eventID, teamHostUser, teamName, teamDescription, startDate, endDate, maxSlotTeamMember);
        teams.add(team);
        return team;
    }

    public void addTeam(String teamID, User teamHostUser, String teamName, String teamDescription, String startDate, String endDate, int maxSlotTeamMember, String createdAt, String eventID, UserList memberList) {
        // load from file
        teamName = teamName.trim();
        teamDescription = teamDescription.trim();
        Team team = new Team (teamID, teamHostUser, teamName, teamDescription, startDate, endDate, maxSlotTeamMember, createdAt, eventID, memberList);
        teams.add(team);
    }

    public void updateRole(String teamID, String role) {
        Team teamExist = findTeamByID(teamID);
        if (teamExist != null) {
            teamExist.setRole(role);
        }
    }

    public void updateTeam(String teamID, String teamName, int maxSlotTeamMember, String startDate, String endDate, String description){
        Team teamExist = findTeamByID(teamID);
        if (teamExist != null) {
            teamExist.setTeamName(teamName);
            teamExist.setMaxSlotTeamMember(maxSlotTeamMember);
            teamExist.setStartDate(startDate);
            teamExist.setEndDate(endDate);
            teamExist.setTeamDescription(description);
        }
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
        teams.removeIf(team -> {
            JoinTeamMap joinTeamMap = new JoinTeamMap();
            HashMap<String, UserList> hashMap = joinTeamMap.roleReadData();
            hashMap.remove(team.getTeamID());
            joinTeamMap.roleWriteData(hashMap);
            return team.getEventID().equals(event.getEventID());
        });

    }

    public void removeTeam(String teamID) {
        teams.removeIf(team -> team.getTeamID().equals(teamID));
    }

    public void removeTeam(Team teamSelect) {
        String teamID = teamSelect.getTeamID();
        teams.removeIf(team -> team.getTeamID().equals(teamID));
    }

    public void sortTeamByNewCreatedAt() {
        teams.sort((team1, team2) -> team2.getCreatedAt().compareTo(team1.getCreatedAt()));
    }

    public void sortTeamByOldCreatedAt() {
        teams.sort(Comparator.comparing(Team::getCreatedAt));
    }

    public void sortTeamByNameA() {
        teams.sort(Comparator.comparing(Team::getTeamName));
    }

    public void sortTeamByNameZ() {
        teams.sort(Comparator.comparing(Team::getTeamName).reversed());
    }

    public void filterByAll() {}

    public void filterByRole(String role) {
        if (teams == null) return;
        teams.removeIf(team -> !team.getRole().equals(role));
    }

    public void filterByBookmark() {
        if (teams == null) return;
        teams.removeIf(team -> !team.isBookmarked());
    }

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

    public ArrayList<Team> getTeams() {
        return teams;
    }
}
