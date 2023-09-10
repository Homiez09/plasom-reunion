package cs211.project.models;
import java.time.LocalDateTime;

public class EventActivity {
    private String name,eventID,startTime,endTime;
    private String description;

    // Constructors


    public EventActivity(String eventId, String activityName, String activityDescription,String activityStart, String activityEnd) {
        this.eventID =eventId;
        this.name =activityName;
        this.description = activityDescription;
        this.startTime = activityStart;
        this.endTime = activityEnd;
    }

    public String removeQuote(String text) {
        text = text.substring(1, text.length() - 1);
        return text;
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

    @Override
    public String toString() {
        return    eventID + ','
                + name + ",\""
                + description + "\","
                + startTime + ','
                + endTime ;
    }
}

