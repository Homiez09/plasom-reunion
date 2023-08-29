package cs211.project.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Team{
    private ArrayList<User> memberList = new ArrayList<>();
    private String teamID, teamName, teamDescription, teamImagePath;
    private User teamManager;
    private int maxSlotTeamMember;

    private Event event;

    public Team(String teamName, String teamDescription, String teamImagePath, User teamManager, int maxSlotTeamMember) {
        this.teamID = generateTeamID();
        this.teamName = teamName;
        this.teamDescription = teamDescription;
        this.teamImagePath = teamImagePath;
        this.teamManager = teamManager;
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

    public ArrayList<User> getMemberList() {
        return memberList;
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

    public User getTeamManager() {
        return teamManager;
    }

    public int getMaxSlotTeamMember() {
        return maxSlotTeamMember;
    }

    public void ChangeTeamName(String newName) {
        this.teamName = newName;
    }

    public void ChangeTeamDescription(String newDescription) {
        this.teamDescription = newDescription;
    }

    public void ChangeTeamImagePath(String newImagePath) {
        this.teamImagePath = newImagePath;
    }

    public boolean isName(String teamName) {
        return this.teamName.equals(teamName);
    }
}
