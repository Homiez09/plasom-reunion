package cs211.project.models;

import cs211.project.models.collections.ActivityList;
import cs211.project.models.collections.TeamList;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Random;

public class Event {
    private final String eventID;
    private User eventHostUser;
    private String eventName;
    private String eventImagePath;
    private String eventTag,eventDateStart, eventDateEnd;
    private String eventDescription, eventLocation;
    private int member = 0 ,slotMember;
    private final String timestamp;
    private boolean joinEvent = false,joinTeam = false;
    private BooleanProperty isSelected ;
    private ActivityList activities;
    private TeamList teamList;
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
        this.member = 0;
        this.slotMember = -1;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        this.isSelected = new SimpleBooleanProperty(false);
        this.activities = new ActivityList();
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
        this.member = 0;
        this.slotMember = slotMember;
        this.isSelected = new SimpleBooleanProperty(false);
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        this.activities = new ActivityList();
        this.joinEvent = true;
        this.joinTeam = true;
    }

    public Event(String eventID,
                 User eventHostUser,
                 String eventName,
                 String eventImagePath,
                 String eventTag,
                 String eventDateStart,
                 String eventDateEnd,
                 String eventDescription,
                 String eventLocation,
                 int member,
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
        this.member = member;
        this.slotMember = slotMember;
        this.eventHostUser = eventHostUser;
        this.timestamp =timestamp;
        this.activities = new ActivityList();
        this.joinEvent = joinEvent;
        this.joinTeam = joinTeam;
    }



    public String getEventID() {return eventID;}
    public User getEventHostUser() {return eventHostUser;}
    public String getEventName() {return eventName;}
    public String getEventImagePath() {return eventImagePath;}
    public String getEventTag() {return eventTag;}
    public String getEventDateStart() {return eventDateStart;}
    public String getEventDateEnd() {return eventDateEnd;}
    public String getEventDescription() {return eventDescription;}
    public int getSlotMember() {return slotMember;}
    public int getMember() {return member;}
    public String getEventLocation() { return eventLocation; }
    public ActivityList getActivityList() { return activities; }
    public TeamList getTeamList() { return teamList; }
    public String getTimestamp() {return timestamp;}
    public boolean isJoinEvent() {return joinEvent;}
    public boolean isJoinTeam() {return joinTeam;}

    public void changeDateStart(String newDate){this.eventDateStart = newDate;}
    public void changeDateEnd(String newDate){this.eventDateEnd = newDate;}
    public void changeName(String newName){this.eventName = newName;}
    public void changeDescription(String newDescription){this.eventDescription = newDescription;}
    public void changeSlotMember(int slotMember){this.slotMember = slotMember;}
    public void changeTag(String newTag) {this.eventTag = newTag;}
    public void changeEventImagePath(String newImagePath) {this.eventImagePath = newImagePath;}
    public void setMember(int n){
        this.member = n;
    }
    public void setTeamList(TeamList teamList){
        this.teamList = teamList;
    }
    public void setActivity(ActivityList activityList) {this.activities = activityList;}
    public void addMember(){if(!isFull())this.member++;}
    public void delMember(){if(member >0) this.member--;}


    private String generateEventID() {
        Random random = new Random();

        String id = "event-";
        int ranInt = random.nextInt(1000000);
        String ranText = generateRandomText();

        id = id + ranText + ranInt;

        return id;
    }
    public boolean isFull(){return slotMember == member;}
    public boolean isUpComming(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime eventDate = LocalDateTime.parse(eventDateStart, formatter);
        LocalDateTime currentTime = LocalDateTime.now();
        if (!eventDate.isBefore(currentTime) && !eventDate.isAfter(currentTime.plusDays(20))) {
            return true;
        }
        return false;
    }

    public boolean isNewEvent() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        // แปลงสตริงวันที่และเวลาเป็น LocalDateTime
        LocalDateTime eventStartDate = LocalDateTime.parse(eventDateStart, formatter);
        LocalDateTime timeStamp = LocalDateTime.parse(timestamp,formatter);
        LocalDateTime currentTime = LocalDateTime.now();

        // คำนวณความต่างของวันระหว่างวันที่เริ่มต้นของอีเวนต์กับวันปัจจุบัน
        long timeStartDiff = ChronoUnit.DAYS.between(eventStartDate, currentTime);
        long timeStampDiff = ChronoUnit.DAYS.between(timeStamp, currentTime);

        // ตรวจสอบว่า Time Stamp ไม่เกิน 7 วันและวันเริ่มต้นไม่เกิน 7 วัน
        return (timeStartDiff <= 7 && timeStampDiff <= 7 );
    }


    public boolean isEnd() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime parsedEventDateEnd = LocalDateTime.parse(eventDateEnd, formatter);

        return currentDateTime.isAfter(parsedEventDateEnd);
    }

    public boolean isHostEvent(String currentUserId) {
        return eventHostUser.getUserId().equals(currentUserId);
    }

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
                +   member + ','
                +   slotMember +','
                +   timestamp+','
                +   joinEvent+','
                +   joinTeam;

    }


}

