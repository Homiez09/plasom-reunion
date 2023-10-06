package cs211.project.models;

import cs211.project.models.collections.ActivityTeamList;
import cs211.project.models.collections.UserList;
import cs211.project.services.ActivityTeamListDataSource;
import cs211.project.services.UserListDataSource;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Team implements Comparable<Team> {
    private String teamID, teamName, teamDescription, createdAt, eventID, startDate, endDate;
    private int maxSlotTeamMember;
    private boolean isBookmarked;
    private UserList memberList = new UserList();
    private String role;
    private User teamHostUser;
    private ActivityTeamList activities;
    private UserListDataSource userListDataSource = new UserListDataSource("data", "user-list.csv");
    private UserList userList = userListDataSource.readData();

    public Team (String eventID, User teamHostUser, String teamName, String description, String startDate, String endDate, int maxSlotTeamMember) {
        this.teamID = generateTeamID();
        this.teamHostUser = teamHostUser;
        this.teamName = teamName;
        this.teamDescription = (description.isEmpty()) ? "-" : description;
        this.startDate = formatStringToTimestamp(startDate);
        this.endDate = formatStringToTimestamp(endDate);
        this.maxSlotTeamMember = maxSlotTeamMember;
        this.createdAt = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "|" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss:SSS"));
        this.eventID = eventID;
        this.isBookmarked = false;
        loadActivities();
    }

    public Team (String teamID, User teamHostUser, String teamName, String teamDescription, String startDate, String endDate, int maxSlotTeamMember, String createdAt, String eventID, UserList memberList) {
        // this constructor is used when loading from database
        this.teamID = teamID;
        this.teamHostUser = teamHostUser;
        this.teamName = teamName;
        this.teamDescription = teamDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxSlotTeamMember = maxSlotTeamMember;
        this.createdAt = createdAt;
        this.eventID = eventID;
        this.memberList = memberList;
        loadActivities();
    }

    public boolean addMemberToMemberList(String username) {
        User userExist = memberList.findUsername(username); // check user is exist in member list
        if (userExist == null) {
            User user = userList.findUsername(username);
            memberList.addUser(user);
            return true;
        }
        return false;
    }

    // go to test file to see how to use this method
    public String formatTimestampToString(String timestamp) { // param require team.getStartDate() or team.getEndDate()
        long time = Long.parseLong(timestamp);
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd.HH:mm");
        String formattedDate = sdf.format(date);
        return formattedDate; // if you want for get date and time pattern yyyy-MM-dd.HH:mm:ss (split with .)
    }

    private String formatStringToTimestamp(String date) {
        String data[] = date.split("[\\-\\.\\:]");
        LocalDateTime futureDate = LocalDateTime.of(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4])); // Year, Month, Day, Hour, Minute
        Instant instant = futureDate.atZone(ZoneId.of("UTC+7")).toInstant();
        long timestamp = instant.toEpochMilli();
        return String.valueOf(timestamp);
    }

    public String getStartDateTime(){
        String startDateFormatted =  formatTimestampToString(startDate);
        int year, month, day, hour, minute;
        year = Integer.parseInt(startDateFormatted.substring(0,4))-543;
        month = Integer.parseInt(startDateFormatted.substring(5,7));
        day = Integer.parseInt(startDateFormatted.substring(8,10));
        hour = Integer.parseInt(startDateFormatted.substring(11,13));
        minute = Integer.parseInt(startDateFormatted.substring(14,16));

        LocalDateTime startDateTime = LocalDateTime.of(year, month, day, hour, minute);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy | hh:mm a", Locale.ENGLISH);
        startDateFormatted = startDateTime.format(dateTimeFormatter);

        return startDateFormatted;
    }

    public String getEndDateTime(){
        String endDateFormatted =  formatTimestampToString(endDate);
        int year, month, day, hour, minute;
        year = Integer.parseInt(endDateFormatted.substring(0,4))-543;
        month = Integer.parseInt(endDateFormatted.substring(5,7));
        day = Integer.parseInt(endDateFormatted.substring(8,10));
        hour = Integer.parseInt(endDateFormatted.substring(11,13));
        minute = Integer.parseInt(endDateFormatted.substring(14,16));

        LocalDateTime endDateTime = LocalDateTime.of(year, month, day, hour, minute);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy | hh:mm a", Locale.ENGLISH);
        endDateFormatted = endDateTime.format(dateTimeFormatter);

        return endDateFormatted;
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

    public UserList getMemberOnline() {
        UserList userList = new UserList();
        for (User user : memberList.getUsers()) {
            if (user.getStatus()) {
                userList.addUser(user);
            }
        }
        return userList;
    }

    public void loadActivities() {
        ActivityTeamListDataSource activityTeamListDataSource = new ActivityTeamListDataSource("data", "team-activity.csv");
        ActivityTeamList activityTeamList = activityTeamListDataSource.readData();
        activityTeamList.getActivitiesByTeamID(teamID);
        this.activities = activityTeamList;
    }

    public boolean isFull() {
        return memberList.getUsers().size() >= maxSlotTeamMember; // return true if full
    }

    public boolean isClose() {
        return Long.parseLong(endDate) < System.currentTimeMillis();
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

    public String getEventID() {
        return eventID;
    }

    public User getTeamHostUser() {
        return teamHostUser;
    }
    public String getStartDate() { // return timestamp (millisecond)
        return startDate;
    }

    public String getEndDate() { // return timestamp (millisecond)
        return endDate;
    }

    public ArrayList<ActivityTeam> getActivities() {
        return activities.getActivities();
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public boolean isName(String teamName) {
        return this.teamName.equals(teamName);
    }


    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setTeamDescription(String teamDescription) {
        this.teamDescription = teamDescription;
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

    public void setMemberList(UserList userList) {
        if (userList == null) return;
        this.memberList = userList;
    }

    public void setStartDate(String startDate) { // format : yyyy-MM-dd.HH:mm:ss
        this.startDate = formatStringToTimestamp(startDate);
    }

    public void setEndDate(String endDate) { // format : yyyy-MM-dd.HH:mm:ss
        this.endDate = formatStringToTimestamp(endDate);
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
                + teamHostUser.getUserId() + ","
                + teamName + ","
                + teamDescription + ","
                + startDate + ","
                + endDate + ","
                + maxSlotTeamMember + ","
                + createdAt + ","
                + eventID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        return teamID.equals(team.teamID);
    }

    @Override
    public int hashCode() {
        return teamID.hashCode();
    }
}
