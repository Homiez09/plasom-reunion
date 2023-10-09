package cs211.project.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ActivityTeam extends Activity {
    protected String teamID;
    protected boolean status;

    public ActivityTeam(String teamId, String activityName, String activityDescription, String activityStart, String activityEnd, String activityID, boolean status) {
        super("", activityName, activityDescription, activityStart, activityEnd, activityID);
        this.teamID = teamId;
        this.status = status;

    }

    public ActivityTeam(String teamId, String activityName, String activityDescription, String activityStart, String activityEnd) {
        super("", activityName, activityDescription, activityStart, activityEnd);
        this.teamID = teamId;
    }

    public String getTeamID() {
        return teamID;
    }

    public boolean getStatus() {
        if (status) return true;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm");
        LocalDateTime end = LocalDateTime.parse(this.getEndTime(),formatter);
        return LocalDateTime.now().isAfter(end);
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return teamID + "," + name + "," + description + "," + startTime + "," + endTime + ","  + status  + "," + activityID;
    }
}
