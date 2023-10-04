package cs211.project.models.collections;

import cs211.project.models.Activity;
import cs211.project.models.ActivityTeam;

import java.util.ArrayList;

public class ActivityTeamList {
    protected ArrayList<ActivityTeam> activities;

    public ActivityTeamList() { activities = new ArrayList<>(); }


    public void addActivity(String eventId, String activityName, String activityDescription,String activityStart, String activityEnd) {
        activityName = activityName.trim();
        if (!activityName.equals("")) {
            activities.add(new ActivityTeam(eventId,activityName,activityDescription,activityStart,activityEnd));
        }
    }
    public void addActivity(String eventId, String activityName, String activityDescription,String activityStart, String activityEnd, String activityID, boolean status) {
        activityName = activityName.trim();
        if (!activityName.equals("")) {
            activities.add(new ActivityTeam(eventId,activityName,activityDescription,activityStart,activityEnd,activityID, status));
        }
    }

    public void addActivity(Activity activity) {
        if (activity != null) {
            activities.add((ActivityTeam) activity);
        }
    }

    public void removeActivity(ActivityTeam activityTeam) {
        activities.remove(activityTeam);
    }
    public ActivityTeam findActivity(String activityID){
        for (ActivityTeam activity:activities) {
            if (activity.getActivityID().equals(activityID) ){
                return activity;
            }
        }
        return null;
    }

    public ActivityTeam findActivityByName(String activityName){
        for (ActivityTeam activity:activities) {
            if (activity.getName().equals(activityName) ){
                return activity;
            }
        }
        return null;
    }

    public ActivityTeam findActivityById(String activityId){
        for (ActivityTeam activity:activities) {
            if (activity.getActivityID().equals(activityId) ){
                return activity;
            }
        }
        return null;
    }


    public ArrayList<ActivityTeam> getActivitiesByTeamID(String teamID) {
        ArrayList<ActivityTeam> activityTeams = new ArrayList<>();
        for (ActivityTeam activity : activities) {
            if (activity.getTeamID().equals(teamID)) activityTeams.add(activity);
        }
        return activityTeams;
    }

    public ArrayList<ActivityTeam> getActivities() {
        ArrayList<ActivityTeam> activityTeams = activities;
        return activityTeams;
    }
}

