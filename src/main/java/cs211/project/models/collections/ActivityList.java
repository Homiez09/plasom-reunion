package cs211.project.models.collections;

import cs211.project.models.EventActivity;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ActivityList {
    private ArrayList<EventActivity> activities;

    public ActivityList() { activities = new ArrayList<>(); }


    public void addActivity(String eventId, String activityName, String activityDescription,LocalDateTime ActivityStart, LocalDateTime ActivityEnd) {
        activityName = activityName.trim();
        if (!activityName.equals("")) {
            activities.add(new EventActivity(eventId,activityName,activityDescription,ActivityStart,ActivityEnd));
        }
    }

    public EventActivity findActivity(String eventId){
        for (EventActivity activity:activities) {
            if (activity.getEventID().equals(eventId)){
                return activity;
            }
        }
        return null;
    }
    public ArrayList<EventActivity> getActivities() {
        return activities;
    }
}

