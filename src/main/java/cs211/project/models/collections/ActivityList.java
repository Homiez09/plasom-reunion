package cs211.project.models.collections;

import cs211.project.models.EventActivity;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ActivityList {
    private ArrayList<EventActivity> activities;

    public ActivityList() { activities = new ArrayList<>(); }

    public void addActivity(String name, String description, LocalDateTime start, LocalDateTime end) {
        name = name.trim();
        if (!name.equals("")) {
            activities.add(new EventActivity(name,start,end,description));
        }
    }

    public ArrayList<EventActivity> getActivities() {
        return activities;
    }
}
