package cs211.project.models.collections;

import cs211.project.models.Activity;
import cs211.project.models.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ActivityList {
    protected ArrayList<Activity> activities;

    public ActivityList() { activities = new ArrayList<>(); }
    public ActivityList(ArrayList<Activity> activityList) {activities = activityList;}

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
    public ArrayList<Activity> getActivities() {
        return activities;
    }
    public ArrayList<Activity> getActivityOfEvent(String eventID) {
        ArrayList<Activity> activityOfEvent = new ArrayList<>();
        for (Activity activity : activities) {
            if (activity.getEventID().equals(eventID)) {
                activityOfEvent.add(activity);
            }
        }
        return activityOfEvent;
    }
    public ActivityList sortActivity(ActivityList activityList){
        Comparator<Activity> comparing = Comparator
                .comparing((Activity activity) -> LocalDateTime.parse(activity.getStartTime(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
                .thenComparing((Activity activity) -> LocalDateTime.parse(activity.getStartTime(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        ActivityList list = new ActivityList();
        list.getActivities().addAll(activityList.getActivities());
        Collections.sort(list.getActivities(),comparing);

        return list;
    }
}

