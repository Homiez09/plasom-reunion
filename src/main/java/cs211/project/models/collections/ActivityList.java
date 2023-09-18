package cs211.project.models.collections;

import cs211.project.models.Activity;

import java.util.ArrayList;

public class ActivityList {
    private ArrayList<Activity> activities;

    public ActivityList() { activities = new ArrayList<>(); }


    public void addActivity(String eventId, String activityName, String activityDescription,String ActivityStart, String ActivityEnd) {
        activityName = activityName.trim();
        if (!activityName.equals("")) {
            activities.add(new Activity(eventId,activityName,activityDescription,ActivityStart,ActivityEnd));
        }
    }

    public void addActivity(Activity activity) {
        if (activity != null) {
            activities.add(activity);
        }
    }

    public Activity findActivity(String eventId){
        for (Activity activity:activities) {
            if (activity.getEventID().equals(eventId)){
                return activity;
            }
        }
        return null;
    }
    public ArrayList<Activity> getActivities() {
        return activities;
    }
}

