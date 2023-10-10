package cs211.project.models;

import cs211.project.models.collections.ActivityList;
import cs211.project.models.collections.UserList;
import cs211.project.services.GenerateRandomID;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event implements Comparable<Event>{
    private final String eventID;
    private final User eventHostUser;
    private String eventName;
    private String eventImagePath;
    private String eventTag,eventDateStart, eventDateEnd;
    private String eventDescription;
    private final String eventLocation;
    private int slotMember;
    private final String timestamp;
    private boolean joinEvent;
    private final boolean joinTeam;
    private ActivityList activityList;
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
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        this.activityList = new ActivityList();
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
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        this.activityList = new ActivityList();
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
    public String getEventDateStart() {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(eventDateStart, inputFormatter);
        return dateTime.format(outputFormatter);}
    public String getEventDateEnd() {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(eventDateEnd, inputFormatter);
        return dateTime.format(outputFormatter);}
    public String getEventDescription() {return eventDescription;}
    public int getSlotMember() {return slotMember;}
    public int getUserInEvent() {return userList.getUsers().size();}
    public String getEventLocation() { return eventLocation; }
    public ActivityList getActivityList() { return activityList; }
    public UserList getUserList(){return userList;}
    public String getTimestamp() {return timestamp;}
    public LocalDateTime getDateStartAsDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return  LocalDateTime.parse(eventDateStart, formatter);

    }
    public LocalDateTime getDateEndAsDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        return  LocalDateTime.parse(eventDateEnd, formatter);

    }
    public LocalDateTime getTimestampAsDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        return  LocalDateTime.parse(timestamp, formatter);

    }

    public boolean isJoinEvent() {return joinEvent;}
    public void changeDateStart(String newDate){this.eventDateStart = newDate;}
    public void changeDateEnd(String newDate){this.eventDateEnd = newDate;}
    public void changeName(String newName){this.eventName = newName;}
    public void changeDescription(String newDescription){this.eventDescription = newDescription;}
    public void changeSlotMember(int slotMember){this.slotMember = slotMember;}
    public void changeTag(String newTag) {this.eventTag = newTag;}
    public void changeEventImagePath(String newImagePath) {this.eventImagePath = newImagePath;}
    public void setActivity(ActivityList activityList) {this.activityList = activityList;}
    public void setUserList(UserList userList){ this.userList = userList;}
    public void setJoinEvent (boolean joinEvent){this.joinEvent = joinEvent;}
    public boolean isFull(){return slotMember == userList.getUsers().size();}

    private String generateEventID() {
        String id = "event-";
        id += new GenerateRandomID().getRandomText();
        return id;
    }

    public boolean isUpComing() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime eventDate = LocalDateTime.parse(eventDateStart, formatter);
        LocalDateTime currentTime = LocalDateTime.now();
        return eventDate.isAfter(currentTime);
    }

    public boolean isEnd() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime parsedEventDateEnd = LocalDateTime.parse(eventDateEnd, formatter);
        return currentDateTime.isAfter(parsedEventDateEnd);
    }

    public boolean isHostEvent(User user) {
        return eventHostUser.equals(user);
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

