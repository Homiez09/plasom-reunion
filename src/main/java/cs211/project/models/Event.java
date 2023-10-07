package cs211.project.models;

import cs211.project.models.collections.ActivityList;
import cs211.project.models.collections.TeamList;
import cs211.project.models.collections.UserList;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Random;

public class Event implements Comparable<Event>{
    private final String eventID;
    private final User eventHostUser;
    private String eventName;
    private String eventImagePath;
    private String eventTag,eventDateStart, eventDateEnd;
    private String eventDescription, eventLocation;
    private int slotMember;
    private final String timestamp;
    private boolean joinEvent = false,joinTeam = false;
    private BooleanProperty isSelected ;
    private ActivityList activityList;
    private TeamList teamList;
    private UserList userList;
    public Event(String eventName,
                 User eventHostUser,
                 String eventImagePath,
                 String eventTag,
                 String eventDateStart,
                 String eventDateEnd,
                 String eventDescription,
                 String eventLocation) {
        this.eventID = generateEventID();
        this.eventName = eventName;
        this.eventHostUser = eventHostUser;
        this.eventImagePath = eventImagePath;
        this.eventTag = eventTag;
        this.eventDateStart = eventDateStart;
        this.eventDateEnd = eventDateEnd;
        this.eventDescription = eventDescription;
        this.eventLocation = eventLocation;
        this.slotMember = -1;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        this.isSelected = new SimpleBooleanProperty(false);
        this.activityList = new ActivityList();
        this.teamList = new TeamList();
        this.userList = new UserList();
        this.joinEvent = true;
        this.joinTeam = true;
    }

    public Event(String eventName,
                 User eventHostUser,
                 String eventImagePath,
                 String eventTag,
                 String eventDateStart,
                 String eventDateEnd,
                 String eventDescription,
                 String eventLocation,
                 int slotMember) {
        this.eventID = generateEventID();
        this.eventHostUser = eventHostUser;
        this.eventName = eventName;
        this.eventImagePath = eventImagePath;
        this.eventTag = eventTag;
        this.eventDateStart = eventDateStart;
        this.eventDateEnd = eventDateEnd;
        this.eventDescription = eventDescription;
        this.eventLocation = eventLocation;
        this.slotMember = slotMember;
        this.isSelected = new SimpleBooleanProperty(false);
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        this.activityList = new ActivityList();
        this.teamList = new TeamList();
        this.userList = new UserList();
        this.joinEvent = true;
        this.joinTeam = true;
    }
    //---------------- Read CSV ----------------\\
    public Event(String eventID,
                 User eventHostUser,
                 String eventName,
                 String eventImagePath,
                 String eventTag,
                 String eventDateStart,
                 String eventDateEnd,
                 String eventDescription,
                 String eventLocation,
                 int slotMember,
                 String timestamp,
                 boolean joinEvent,
                 boolean joinTeam) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventImagePath = eventImagePath;
        this.eventTag = eventTag;
        this.eventDateStart = eventDateStart;
        this.eventDateEnd = eventDateEnd;
        this.eventDescription = eventDescription;
        this.eventLocation = eventLocation;
        this.slotMember = slotMember;
        this.eventHostUser = eventHostUser;
        this.timestamp =timestamp;
        this.activityList = new ActivityList();
        this.teamList = new TeamList();
        this.userList = new UserList();
        this.joinEvent = joinEvent;
        this.joinTeam = joinTeam;
    }
    //---------------- Read CSV ----------------\\
    public String getEventID() {return eventID;}
    public User getEventHostUser() {return eventHostUser;}
    public String getEventName() {return eventName;}
    public String getEventImagePath() {return eventImagePath;}
    public String getEventTag() {return eventTag;}
    public String getEventDateStart() {return eventDateStart;}
    public String getEventDateEnd() {return eventDateEnd;}
    public String getEventDescription() {return eventDescription;}
    public int getSlotMember() {return slotMember;}
    public int getUserInEvent() {return userList.getUsers().size();}
    public String getEventLocation() { return eventLocation; }
    public ActivityList getActivityList() { return activityList; }
    public TeamList getTeamList() { return teamList; }
    public UserList getUserList(){return userList;}
    public String getTimestamp() {return timestamp;}
    public LocalDateTime getDateStartAsDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        return  LocalDateTime.parse(eventDateStart, formatter);

    }
    public LocalDateTime getDateEndAsDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        return  LocalDateTime.parse(eventDateEnd, formatter);

    }
    public LocalDateTime getTimestampAsDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        return  LocalDateTime.parse(timestamp, formatter);

    }

    public boolean isJoinEvent() {return joinEvent;}
    public boolean isJoinTeam() {return joinTeam;}
    public void changeDateStart(String newDate){this.eventDateStart = newDate;}
    public void changeDateEnd(String newDate){this.eventDateEnd = newDate;}
    public void changeName(String newName){this.eventName = newName;}
    public void changeDescription(String newDescription){this.eventDescription = newDescription;}
    public void changeSlotMember(int slotMember){this.slotMember = slotMember;}
    public void changeTag(String newTag) {this.eventTag = newTag;}
    public void changeEventImagePath(String newImagePath) {this.eventImagePath = newImagePath;}
    public void setTeamList(TeamList teamList){
        this.teamList = teamList;
    }
    public void setActivity(ActivityList activityList) {this.activityList = activityList;}
    public void setUserList(UserList userList){ this.userList = userList;}
    public void setJoinEvent (boolean joinEvent){this.joinEvent = joinEvent;}
    public boolean isFull(){return slotMember == userList.getUsers().size();}

    private String generateEventID() {
        Random random = new Random();

        String id = "event-";
        int ranInt = random.nextInt(1000000);
        String ranText = generateRandomText();

        id = id + ranText + ranInt;

        return id;
    }
    public boolean isUpComing() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime eventDate = LocalDateTime.parse(eventDateStart, formatter);
        LocalDateTime currentTime = LocalDateTime.now();

        long eventDateDiff = ChronoUnit.DAYS.between(currentTime, eventDate);

        return eventDateDiff >= 0;
    }

    public boolean isNewEvent() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        LocalDateTime timeStamp = LocalDateTime.parse(timestamp, formatter);
        LocalDateTime currentTime = LocalDateTime.now();

        long eventDateDiff = ChronoUnit.DAYS.between(timeStamp, currentTime);

        return eventDateDiff <= 7;
    }

    public boolean isEnd() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime parsedEventDateEnd = LocalDateTime.parse(eventDateEnd, formatter);

        return currentDateTime.isAfter(parsedEventDateEnd);
    }
    public boolean isHostEvent(User user) {
        return eventHostUser.equals(user);
    }
    public boolean isHaveUser(User user){return eventHostUser.equals(user);}
    private String generateRandomText() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder randomText = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            randomText.append(randomChar);
        }
        return randomText.toString();
    }

    @Override
    public String toString() {
        return      eventID + ','
                +   eventHostUser.getUserId() + ','
                +   eventName + ','
                +   eventImagePath + ','
                +   eventTag + ','
                +   eventDateStart + ','
                +   eventDateEnd + ','
                +   eventDescription.replaceAll("\n"," ") + ','
                +   eventLocation + ','
                +   slotMember +','
                +   timestamp+','
                +   joinEvent+','
                +   joinTeam;

    }

    @Override
    public int compareTo(Event o) {
        return eventName.compareTo(o.eventName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        return eventID.equals(event.eventID);
    }

    @Override
    public int hashCode() {
        return eventID.hashCode();
    }
}

