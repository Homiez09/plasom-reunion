package cs211.project.models;

import cs211.project.models.collections.UserList;

public class Event {
    private String eventName;
    private String eventImagePath;
    private String eventDateStart;
    private String eventDateEnd;
    private String eventDescription;
    private UserList userList ;
    private int slotMember;

    public Event(String eventName, String eventImagePath, String eventDateStart, String eventDateEnd, String eventDescription) {
        this.eventName = eventName;
        this.eventImagePath = eventImagePath;
        this.eventDateStart = eventDateStart;
        this.eventDateEnd = eventDateEnd;
        this.eventDescription = eventDescription;
    }

    public Event(String eventName, String eventImagePath, String eventDateStart, String eventDateEnd, String eventDescription, int slotMember) {
        this.eventName = eventName;
        this.eventImagePath = eventImagePath;
        this.eventDateStart = eventDateStart;
        this.eventDateEnd = eventDateEnd;
        this.eventDescription = eventDescription;
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

    public UserList getUserList() {
        return userList;
    }

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
                "Name='" + eventName + '\'' +
                ", DateStart='" + eventDateStart + '\'' +
                ", DateEnd='" + eventDateEnd + '\'' +
                ", slotMember=" + slotMember +
                '}';
    }

    public boolean isFull(){
        if(slotMember == userList.getMember()){
            return true;
        }else {
            return false;
        }

    }

}

