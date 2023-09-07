package cs211.project.models;

import cs211.project.models.collections.ActivityList;
import cs211.project.models.collections.TeamList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

public class Event {
    private String eventID;
    private String eventName;
    private String eventImagePath;
    private String eventDateStart;
    private String eventDateEnd;
    private String eventDescription;
    private String eventLocation;
    private int member = 0 ;
    private int slotMember;
    private String eventHost;
    private ActivityList activities;
    private TeamList teams;

    public Event() {
    }
    public Event(String eventName,String eventHost, String eventImagePath, String eventDateStart, String eventDateEnd, String eventDescription,String eventLocation) {
        this.eventID = generateEventID();
        this.eventName = eventName;
        this.eventHost = eventHost;
        this.eventImagePath = eventImagePath;
        this.eventDateStart = eventDateStart;
        this.eventDateEnd = eventDateEnd;
        this.eventDescription = eventDescription;
        this.eventLocation = eventLocation;
    }

    public Event(String eventName,String eventHost, String eventImagePath, String eventDateStart, String eventDateEnd, String eventDescription,String eventLocation, int slotMember) {
        this.eventID = generateEventID();
        this.eventHost = eventHost;
        this.eventName = eventName;
        this.eventImagePath = eventImagePath;
        this.eventDateStart = eventDateStart;
        this.eventDateEnd = eventDateEnd;
        this.eventDescription = eventDescription;
        this.eventLocation = eventLocation;
        this.slotMember = slotMember;

    }
    public String getEventID() {return eventID;}
    public String getEventName() {
        return eventName;
    }

    public String getEventImagePath() {
        return eventImagePath;
    }

    public String getEventDateStart() {return eventDateStart;}
    public String getEventDateEnd() {return eventDateEnd;}
    public String getEventDescription() {
        return eventDescription;
    }

    public int getSlotMember() {
        return slotMember;
    }

    public int getMember() {
        return member;
    }

    public String getEventLocation() { return eventLocation; }

    public ActivityList getActivities() { return activities; }
    public String getEventHost() {return eventHost;}
    public TeamList getTeams() { return teams; }

    public void changeDateStart(String newDate){
        this.eventDateStart = newDate;
    }
    public void changeDateEnd(String newDate){
        this.eventDateEnd = newDate;
    }

    public void changeName(String newName){
        this.eventName = newName;
    }

    public void changeDescription(String newDescription){
        this.eventDescription = newDescription;
    }
    public void changeSlotMember(int slotMember){
        this.slotMember = slotMember;
    }

    public static String generateEventID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public boolean isFull(){
        if(slotMember == member){
            return true;
        }else {
            return false;
        }

    }
    public boolean isEnd() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime parsedEventDateEnd = LocalDateTime.parse(eventDateEnd, formatter);

        return currentDateTime.isAfter(parsedEventDateEnd);
    }



}

