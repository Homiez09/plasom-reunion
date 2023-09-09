package cs211.project.models;

import cs211.project.models.collections.UserList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Team{
    private String teamID, teamName, teamDescription, teamImagePath;
    private int maxSlotTeamMember;
    private UserList memberList;

    public Team (String teamName, int maxSlotTeamMember) {
        this.teamID = generateTeamID();
        this.teamName = teamName;
        this.maxSlotTeamMember = maxSlotTeamMember;
    }

    public Team (String teamName, int maxSlotTeamMember, String teamDescription) {
        this(teamName, maxSlotTeamMember);
        this.teamDescription = teamDescription;
    }

    public Team (String teamName, int maxSlotTeamMember, String teamDescription, String teamImagePath) {
        this(teamName, maxSlotTeamMember, teamDescription);
        this.teamImagePath = teamImagePath;
    }

    public Team (String teamName, int maxSlotTeamMember, String teamDescription, String teamImagePath, UserList memberList) {
        this(teamName, maxSlotTeamMember, teamDescription, teamImagePath);
        this.memberList = memberList;
    }

    public Team (String teamID, String teamName, String teamDescription, String teamImagePath, int maxSlotTeamMember) {
        // this constructor is used when loading from database
        this.teamID = teamID;
        this.teamName = teamName;
        this.teamDescription = teamDescription;
        this.teamImagePath = teamImagePath;
        this.maxSlotTeamMember = maxSlotTeamMember;
    }

    private String generateTeamID() {
        final int MAX_ID_LENGTH = 16;
        StringBuilder sb = new StringBuilder();

        // formatted date & time now
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("HHmmss"));

        // random number
        int randomNum = (int)(Math.random()*1000);

        // append to StringBuilder
        sb.append(formattedDate);
        sb.append(formattedTime);
        sb.append(randomNum);

        // substring to MAX_ID_LENGTH
        String result = sb.toString();
        if(result.length() > MAX_ID_LENGTH){
            result = result.substring(0, MAX_ID_LENGTH);
        }

        return result;
    }

    public String getTeamID() {
        return teamID;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamDescription() {
        return teamDescription;
    }

    public String getTeamImagePath() {
        return teamImagePath;
    }

    public int getMaxSlotTeamMember() {
        return maxSlotTeamMember;
    }

    public UserList getMemberList() {
        return memberList;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setTeamDescription(String teamDescription) {
        this.teamDescription = teamDescription;
    }

    public void setTeamImagePath(String teamImagePath) {
        this.teamImagePath = teamImagePath;
    }

    public void setMaxSlotTeamMember(int maxSlotTeamMember) {
        this.maxSlotTeamMember = maxSlotTeamMember;
    }

    public void AddMemberToTeam(/* todo: param require */) {
        // todo: add member to team
    }

    public boolean isID(String teamID) {
        return this.teamID.equals(teamID);
    }

    public boolean isName(String teamName) {
        return this.teamName.equals(teamName);
    }
}
