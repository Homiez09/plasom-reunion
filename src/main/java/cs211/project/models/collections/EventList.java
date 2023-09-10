package cs211.project.models.collections;

import cs211.project.models.Event;
import cs211.project.services.Datasource;

import java.util.*;

public class EventList {
    private ArrayList<Event> events;
    private Datasource<EventList> datasource;
    private EventList eventList;
    public EventList() {
        events = new ArrayList<>();
    }
    public void addEvent(String eventName, String eventHost, String eventImagePath, String eventDateStart, String eventDateEnd,
                         String eventDescription, String eventLocation) {
        eventName = eventName.trim();
        eventHost = eventHost.trim();
        if (!eventName.equals("") && !eventHost.equals("")){
            events.add(new Event(eventName,eventHost,eventImagePath,eventDateStart,eventDateEnd,eventDescription,eventLocation));
        }
    }
    public void addEvent(String eventName, String eventHost, String eventImagePath, String eventDateStart, String eventDateEnd,
                         String eventDescription, String eventLocation, int slotMember) {
        eventName = eventName.trim();
        eventHost = eventHost.trim();
        if (!eventName.equals("")&& !eventHost.equals("")) {
            events.add(new Event(   eventName,eventHost, eventImagePath, eventDateStart, eventDateEnd,
                                    eventDescription,eventLocation,slotMember));
        }
    }
    public void addEvent(String eventId, String eventHost, String eventName, String imagePath, String eventStart, String eventEnd,
                         String eventDescription, String eventLocation, int member, int slotMember, ActivityList activities,
                         TeamList teams) {
        eventId = eventId.trim();
        eventName = eventName.trim();
        eventHost = eventHost.trim();
        if (!events.contains(eventId) &&!eventName.equals("") && !eventHost.equals("")){
            events.add(new Event(   eventId,eventHost,eventName,imagePath,eventStart,eventEnd,eventDescription,
                                    eventLocation,member,slotMember,activities,teams));
        }
    }

    public Event findEvent(String eventId) {
        for (Event event : events) {
            if (event.getEventID().equals(eventId)) {
                return event;
            }
        }
        return null;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }


}
