package cs211.project.models.collections;

import cs211.project.models.Event;
import cs211.project.models.Team;

import java.util.ArrayList;

public class TeamList {
    private ArrayList<Team> teams;

    public TeamList() {
        teams = new ArrayList<>();
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
            Team team = new Team(teamName, maxSlotTeamMember);
            teams.add(team);
        }
    }
    public void addTeam(String teamName, int maxSlotTeamMember, String teamDescription){
        teamName = teamName.trim();
        teamDescription = teamDescription.trim();
        Team exist = findTeamByName(teamName);
        if(exist == null){
            Team team = new Team (teamName, maxSlotTeamMember, teamDescription);
            teams.add(team);
        }
    }

    public void addTeam(String teamName, int maxSlotTeamMember, String teamDescription, String teamImagePath){
        teamName = teamName.trim();
        teamDescription = teamDescription.trim();
        teamImagePath = teamImagePath.trim();
        Team exist = findTeamByName(teamName);
        if(exist == null){
            Team team = new Team (teamName, maxSlotTeamMember, teamDescription, teamImagePath);
            teams.add(team);
        }
    }

    public void addTeam(String teamName, int maxSlotTeamMember, String teamDescription, String teamImagePath, UserList memberList){
        teamName = teamName.trim();
        teamDescription = teamDescription.trim();
        teamImagePath = teamImagePath.trim();
        Team exist = findTeamByName(teamName);
        if(exist == null){
            Team team = new Team (teamName, maxSlotTeamMember, teamDescription, teamImagePath, memberList);
            teams.add(team);
        }
    }

    public void addTeam(String teamID, String teamName, String teamDescription, String teamImagePath, int maxSlotTeamMember, String createdAt){
        teamName = teamName.trim();
        teamDescription = teamDescription.trim();
        teamImagePath = teamImagePath.trim();
        Team exist = findTeamByName(teamID);
        if(exist == null){
            Team team = new Team (teamID, teamName, teamDescription, teamImagePath, maxSlotTeamMember, createdAt);
            teams.add(team);
        }
    }

    // find teamlist by event id and user correct
    public ArrayList<Team> getTeamOfEvent(Event event) {
        ArrayList<Team> teams = new ArrayList<>();
        // todo: find teamlist by event id and user correct

        return teams;
    }

    public void sortTeamByNewCreatedAt() { // from new to old
        teams.sort((team1, team2) -> team2.getCreatedAt().compareTo(team1.getCreatedAt()));
    }

    public void sortTeamByOldCreatedAt() { // from old to new
        teams.sort((team1, team2) -> team1.getCreatedAt().compareTo(team2.getCreatedAt()));
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }
}
