package cs211.project.models;
import java.time.LocalDateTime;

public class EventActivity {
    private String name,eventID;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;

    // Constructors


    public EventActivity(String eventId, String activityName, String activityDescription,LocalDateTime activityStart, LocalDateTime activityEnd) {
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public  String timeToString(LocalDateTime time) {
        String day = String.format("%02d",time.getDayOfMonth());
        String month = String.format("%02d",time.getMonthValue());
        String year = String.format("%04d",time.getYear());
        String hour = String.format("%02d",time.getHour());
        String minute = String.format("%02d",time.getMinute());
        return day+"/"+month+"/"+year+" "+hour+":"+minute;
    }

    public String getEventID() {
        return eventID;
    }

    @Override
    public String toString() {
        return    eventID + ','
                + name + ",\""
                + description + "\","
                + timeToString(startTime) + ','
                + timeToString(endTime) ;
    }
}

