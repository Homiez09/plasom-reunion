package cs211.project.models;

import cs211.project.models.collections.ActivityList;
import cs211.project.models.collections.TeamList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event {
    private String eventName;
    private String eventImagePath;
    private String eventDateStart;
    private String eventDateEnd;
    private String eventDescription;
    private String eventLocation;
    private int member = 0;
    private int slotMember;
    private ActivityList activities;
    private TeamList teams;

    public Event() {
    }
    public Event(String eventName, String eventImagePath, String eventDateStart, String eventDateEnd, String eventDescription,String eventLocation) {
        this.eventName = eventName;
        this.eventImagePath = eventImagePath;
        this.eventDateStart = eventDateStart;
        this.eventDateEnd = eventDateEnd;
        this.eventDescription = eventDescription;
        this.eventLocation = eventLocation;
    }

    public Event(String eventName, String eventImagePath, String eventDateStart, String eventDateEnd, String eventDescription,String eventLocation, int slotMember) {
        this.eventName = eventName;
        this.eventImagePath = eventImagePath;
        this.eventDateStart = eventDateStart;
        this.eventDateEnd = eventDateEnd;
        this.eventDescription = eventDescription;
        this.eventLocation = eventLocation;
        this.slotMember = slotMember;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventImagePath() {
        return eventImagePath;
    }

    public String getEventDateStart() {
        return eventDateStart;
    }

    public String getEventDateEnd() {
        return eventDateEnd;
    }

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

    @Override
    public String toString() {
        return "Event{" +
                "eventName='" + eventName + '\'' +
                ", eventImagePath='" + eventImagePath + '\'' +
                ", eventDateStart='" + eventDateStart + '\'' +
                ", eventDateEnd='" + eventDateEnd + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                ", eventLocation='" + eventLocation + '\'' +
                ", member=" + member +
                ", slotMember=" + slotMember +
                '}';
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy");
        LocalDate parsedEventDateEnd = LocalDate.parse(eventDateEnd, formatter);

        LocalDateTime endOfDayParsedEventDateEnd = parsedEventDateEnd.atStartOfDay();

        return currentDateTime.isAfter(endOfDayParsedEventDateEnd);
    }

}

