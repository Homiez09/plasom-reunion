package cs211.project.models;

import cs211.project.models.collections.UserList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class Team implements Comparable<Team> {
    private String teamID, teamName, teamDescription, teamImagePath, createdAt, eventID;
    private int maxSlotTeamMember;
    private boolean isBookmarked;
    private UserList memberList;
    private String role;

    public Team (String eventID, String teamName, int maxSlotTeamMember) {
        this.teamID = generateTeamID();
        this.teamName = teamName;
        this.teamDescription = "";
        this.teamImagePath = "";
        this.maxSlotTeamMember = maxSlotTeamMember;
        this.createdAt = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "|" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss:SSS"));
        this.eventID = eventID;
        this.isBookmarked = false;
    }

    public Team (String eventID, String teamName, int maxSlotTeamMember, String teamDescription) {
        this(eventID, teamName, maxSlotTeamMember);
        this.teamDescription = teamDescription;
    }

    public Team (String eventID, String teamName, int maxSlotTeamMember, String teamDescription, String teamImagePath) {
        this(eventID, teamName, maxSlotTeamMember, teamDescription);
        this.teamImagePath = teamImagePath;
    }

    public Team (String eventID, String teamName, int maxSlotTeamMember, String teamDescription, String teamImagePath, UserList memberList) {
        this(eventID, teamName, maxSlotTeamMember, teamDescription, teamImagePath);
        this.memberList = memberList;
    }

    public Team (String teamID, String teamName, String teamDescription, String teamImagePath, int maxSlotTeamMember, String createdAt, String eventID) {
        // this constructor is used when loading from database
        this.teamID = teamID;
        this.teamName = teamName;
        this.teamDescription = teamDescription;
        this.teamImagePath = teamImagePath;
        this.maxSlotTeamMember = maxSlotTeamMember;
        this.createdAt = createdAt;
        this.eventID = eventID;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public String getRole() { return role; }

    public boolean isBookmarked() {
        return isBookmarked;
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

    public void setRole(String role) {
        this.role = role;
    }

    public void setBookmarked(boolean bookmarked) {
        this.isBookmarked = bookmarked;
    }

    public void AddMemberToTeam(/* todo: param require */) {
        // todo: add member to team
    }

    public boolean isName(String teamName) {
        return this.teamName.equals(teamName);
    }

    public String generateRandomText(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder randomText = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            randomText.append(randomChar);
        }

        return randomText.toString();
    }

    public String getEventID() {
        return eventID;
    }

    @Override
    public int compareTo(Team team) {
        int s = Integer.parseInt(this.createdAt.replace("|", "").replace(":", "").replace("-", ""));
        int t = Integer.parseInt(team.getCreatedAt().replace("|", "").replace(":", "").replace("-", ""));
        return (int)t - s;
    }

    @Override
    public String toString() {
        return teamID + ","
                + teamName + ","
                + teamDescription + ","
                + teamImagePath + ","
                + maxSlotTeamMember + ","
                + createdAt + ","
                + eventID;
    }
}
