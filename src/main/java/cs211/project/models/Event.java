package cs211.project.models;

import cs211.project.models.collections.ActivityList;
import cs211.project.models.collections.UserList;
import cs211.project.services.GenerateRandomID;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event implements Comparable<Event>{
    private final String eventID;
    private final User eventHostUser;
    private String eventName;
    private String eventImagePath;
    private String eventTag,eventDateStart, eventDateEnd;
    private String eventDescription;
    private String eventLocation;
    private int slotMember;
    private final String timestamp;
    private boolean joinEvent;
    private String joinTimeStart;
    private String joinTimeEnd;
    private ActivityList activityList;
    private UserList userList;
    public Event(String eventName,
                 User eventHostUser,
                 String eventImagePath,
                 String eventTag,
                 String eventDateStart,
                 String eventDateEnd,
                 String eventDescription,
                 String eventLocation,int slotMember) {
        this.eventID = generateEventID();
        this.eventName = eventName;
        this.eventHostUser = eventHostUser;
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
        this.joinTimeStart = eventDateStart;
        this.joinTimeEnd = eventDateEnd;
        this.joinEvent = isJoinEventNow();
    }

    public Event(String eventName,
                 User eventHostUser,
                 String eventImagePath,
                 String eventTag,
                 String eventDateStart,
                 String eventDateEnd,
                 String eventDescription,
                 String eventLocation,
                 int slotMember,
                 String joinTimeStart,
                 String joinTimeEnd) {
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
        this.joinTimeStart = joinTimeStart;
        this.joinTimeEnd = joinTimeEnd;
        this.joinEvent = isJoinEventNow();
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
                 String joinTimeStart,
                 String joinTimeEnd) {
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
        this.joinTimeStart = joinTimeStart;
        this.joinTimeEnd = joinTimeEnd;
        this.joinEvent = isJoinEventNow();
    }
    //---------------- Read CSV ----------------\\
    public String getEventID() {return eventID;}
    public User getEventHostUser() {return eventHostUser;}
    public String getEventName() {return eventName;}
    public String getEventImagePath() {return eventImagePath;}
    public String getEventTag() {return eventTag;}
    public String getEventDateStart() {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(eventDateStart, inputFormatter);
        return dateTime.format(outputFormatter);
    }
    public String getEventDateEnd() {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(eventDateEnd, inputFormatter);
        return dateTime.format(outputFormatter);
    }
    public String getEventDescription() {return eventDescription;}
    public int getSlotMember() {return slotMember;}
    public int getUserInEvent() {return userList.getUsers().size();}
    public String getEventLocation() { return eventLocation; }
    public ActivityList getActivityList() { return activityList; }
    public UserList getUserList(){return userList;}

    public String getJoinTimeStart() {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(joinTimeStart, inputFormatter);
        return dateTime.format(outputFormatter);
    }

    public String getJoinTimeEnd() {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(joinTimeEnd, inputFormatter);
        return dateTime.format(outputFormatter);
    }
    public LocalDateTime getJoinTimeStartAsDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return  LocalDateTime.parse(joinTimeStart, formatter);    }

    public LocalDateTime getJoinTimeEndAsDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return  LocalDateTime.parse(joinTimeEnd, formatter);    }

    public LocalDateTime getDateStartAsDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return  LocalDateTime.parse(eventDateStart, formatter);
    }
    public LocalDateTime getDateEndAsDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return  LocalDateTime.parse(eventDateEnd, formatter);
    }
    public LocalDateTime getTimestamp(){
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
    public void changeLocation(String newLocation){this.eventLocation = newLocation;}
    public void changeJoinTimeStart(String newTime){this.joinTimeStart = newTime;}
    public void changeJoinTimeEnd(String newTime){this.joinTimeEnd = newTime;}
    public void setActivity(ActivityList activityList) {this.activityList = activityList;}
    public void setUserList(UserList userList){ this.userList = userList;}
    public boolean isFull(){return slotMember == userList.getUsers().size();}

    private String generateEventID() {
        String id = "event-";
        id += new GenerateRandomID().getRandomText();
        return id;
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
    private boolean isJoinEventNow() {
        return LocalDateTime.now().isAfter(getJoinTimeStartAsDate()) && LocalDateTime.now().isBefore(getJoinTimeEndAsDate());
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
                +   eventDescription.replaceAll("\n"," ").replaceAll(",", " ") + ','
                +   eventLocation + ','
                +   slotMember +','
                +   timestamp+','
                +   joinTimeStart+","
                +   joinTimeEnd;

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

