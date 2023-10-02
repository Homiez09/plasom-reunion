package cs211.project.models;

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
        return status;
    }

    public String toString() {
        return teamID + "," + name + "," + description + "," + startTime + "," + endTime + ","  + status  + "," + activityID;
    }
}
