package cs211.project.models.collections;

import cs211.project.models.Activity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

public class ActivityList {
    protected ArrayList<Activity> activities;

    public ActivityList() { activities = new ArrayList<>(); }

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

    public void swapDate(Activity newDate){
        Activity oldDate = findActivity(newDate.getActivityID());
        oldDate.setStartTime(newDate.getStartTime());
        oldDate.setEndTime(newDate.getEndTime());
    }

    public void changeData(Activity newData) {
        for (int i = 0; i < activities.size(); i++) {
            Activity oldData = activities.get(i);
            if (oldData.getActivityID().equals(newData.getActivityID())) {
                activities.set(i, newData);
                break;
            }
        }
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
                .thenComparing((Activity activity) -> LocalDateTime.parse(activity.getEndTime(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        ActivityList list = new ActivityList();
        list.getActivities().addAll(activityList.getActivities());
        list.getActivities().sort(comparing);

        return list;
    }
}

