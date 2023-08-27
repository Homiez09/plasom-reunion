package cs211.project.models;
import java.time.LocalDateTime;

public class EventActivity {
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;

    // Constructors
    public EventActivity(String name, LocalDateTime startTime, LocalDateTime endTime, String description) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return time.getHour()+":"+time.getMinute();
    }
}

