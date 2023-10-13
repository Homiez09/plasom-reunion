package cs211.project.models;

import cs211.project.services.GenerateRandomID;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Activity {
    protected String activityID, name, description, startTime, endTime, eventID;

    // Constructors
    public Activity(String eventId, String activityName, String activityDescription, String activityStart, String activityEnd) {
        this.eventID =eventId;
        this.name =activityName;
        if (activityDescription.charAt(0) == '"' && activityDescription.charAt(activityDescription.length()-1) == '"') {
            this.description = activityDescription;
        } else {
            this.description = "\"" + activityDescription + "\"";}
        this.startTime = activityStart;
        this.endTime = activityEnd;
        this.activityID = generateActivityID();
    }
    public Activity(String eventId, String activityName, String activityDescription, String activityStart, String activityEnd,String activityID) {
        this.eventID =eventId;
        this.name =activityName;
        if (activityDescription.charAt(0) == '"' && activityDescription.charAt(activityDescription.length()-1) == '"') {
            this.description = activityDescription;
        } else {
            this.description = "\"" + activityDescription + "\"";}
        this.startTime = activityStart;
        this.endTime = activityEnd;
        this.activityID = activityID;
    }

    //class method
    public String removeQuote(String text) {
        text = text.substring(1, text.length() - 1);
        return text;
    }
    public String generateActivityID() {
        String id = "act-";
        id += new GenerateRandomID().getRandomText();
        return id;
    }
    public String getActivityStatus() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime start = LocalDateTime.parse(this.getStartTime(),formatter);
        LocalDateTime end = LocalDateTime.parse(this.getEndTime(),formatter);
        if (LocalDateTime.now().isBefore(start)) {return "Upcoming";}
        else if (LocalDateTime.now().isAfter(start) && LocalDateTime.now().isBefore(end)) {return "On-going";}
        else {return "Completed";}
    }

    // Getters and Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return removeQuote(description);
    }
    public void setDescription(String description) {
        this.description = "\""+description+"\"";
    }

    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEventID() {
        return eventID;
    }
    public String getActivityID() {
        return activityID;
    }

    @Override
    public String toString() {
        return    activityID + ','
                + name + ","
                + description + ","
                + startTime + ','
                + endTime  + ","
                + eventID;
    }
}

