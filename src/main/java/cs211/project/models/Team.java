package cs211.project.models;

import cs211.project.models.collections.ActivityTeamList;
import cs211.project.models.collections.UserList;
import cs211.project.services.ActivityTeamListDataSource;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class Team implements Comparable<Team> {
    private final String teamID, createdAt, eventID;
    private String teamName, teamDescription, startDate, endDate;
    private int maxSlotTeamMember;
    private boolean isBookmarked;
    private UserList memberList = new UserList();
    private String role;
    private final User teamHostUser;

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

    public String formatTimestampToString(String timestamp) {
        long time = Long.parseLong(timestamp);
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd.HH:mm");
        return sdf.format(date);
    }

    private String formatStringToTimestamp(String date) {
        String[] data = date.split("[-.:]");
        LocalDateTime futureDate = LocalDateTime.of(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4])); // Year, Month, Day, Hour, Minute
        Instant instant = futureDate.atZone(ZoneId.of("UTC+7")).toInstant();
        long timestamp = instant.toEpochMilli();
        return String.valueOf(timestamp);
    }

    private String getDateTime(String date) {
        String dateFormatted =  formatTimestampToString(date);
        int year, month, day, hour, minute;
        year = Integer.parseInt(dateFormatted.substring(0,4));
        month = Integer.parseInt(dateFormatted.substring(5,7));
        day = Integer.parseInt(dateFormatted.substring(8,10));
        hour = Integer.parseInt(dateFormatted.substring(11,13));
        minute = Integer.parseInt(dateFormatted.substring(14,16));

        LocalDateTime startDateTime = LocalDateTime.of(year, month, day, hour, minute);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy | hh:mm a", Locale.ENGLISH);
        dateFormatted = startDateTime.format(dateTimeFormatter);

        return dateFormatted;
    }

    public String getStartDateTime(){
        return getDateTime(startDate);
    }

    public String getEndDateTime() {
        return getDateTime(endDate);
    }

    private String generateTeamID() {
        final int MAX_ID_LENGTH = 16;
        StringBuilder sb = new StringBuilder();

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("HHmmss"));

        int randomNum = (int)(Math.random()*1000);

        sb.append(formattedDate);
        sb.append(formattedTime);
        sb.append(randomNum);

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
    }

    public boolean isFull() {
        return memberList.getUsers().size() >= maxSlotTeamMember;
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

    public void setStartDate(String startDate) {
        this.startDate = formatStringToTimestamp(startDate);
    }

    public void setEndDate(String endDate) {
        this.endDate = formatStringToTimestamp(endDate);
   }

    @Override
    public int compareTo(Team team) {
        int s = Integer.parseInt(this.createdAt.replace("|", "").replace(":", "").replace("-", ""));
        int t = Integer.parseInt(team.getCreatedAt().replace("|", "").replace(":", "").replace("-", ""));
        return t - s;
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
