package cs211.project.models.collections;

import cs211.project.models.Activity;
import cs211.project.models.Event;

import java.util.ArrayList;

public class ActivityList {
    private ArrayList<Activity> activities;

    public ActivityList() { activities = new ArrayList<>(); }


    public void addActivity(String eventId, String activityName, String activityDescription,String activityStart, String activityEnd) {
        activityName = activityName.trim();
        if (!activityName.equals("")) {
            activities.add(new Activity(eventId,activityName,activityDescription,activityStart,activityEnd));
        }
    }
    public void addActivity(String eventId, String activityName, String activityDescription,String activityStart, String activityEnd,String activityID) {
        activityName = activityName.trim();
        if (!activityName.equals("")) {
            activities.add(new Activity(eventId,activityName,activityDescription,activityStart,activityEnd,activityID));
        }
    }

    public void addActivity(Activity activity) {
        if (activity != null) {
            activities.add(activity);
        }
    }

    public void removeActivity(String activityID) {
        if (activityID != null) {
            activities.remove(findActivity(activityID));
        }
    }
    public Activity findActivity(String activityID){
        for (Activity activity:activities) {
            if (activity.getActivityID().equals(activityID) ){
                return activity;
            }
        }
        return null;
    }
    public ArrayList<Activity> getActivitiesOfEvent(Event event){
        return activities;
    }
    public ArrayList<Activity> getActivities() {
        return activities;
    }
}

