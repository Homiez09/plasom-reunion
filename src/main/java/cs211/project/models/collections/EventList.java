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
    public void addEvent(String eventName, String eventHost, String eventImagePath,String eventTag, String eventDateStart, String eventDateEnd,
                         String eventDescription, String eventLocation) {
        eventName = eventName.trim();
        eventHost = eventHost.trim();
        if (!eventName.equals("") && !eventHost.equals("")){
            events.add(new Event(eventName,eventHost,eventImagePath,eventTag,
                                eventDateStart,eventDateEnd,eventDescription,eventLocation));
        }
    }
    public void addEvent(String eventName, String eventHost, String eventImagePath,
                         String eventTag, String eventDateStart, String eventDateEnd,
                         String eventDescription, String eventLocation, int slotMember) {
        eventName = eventName.trim();
        eventHost = eventHost.trim();
        if (!eventName.equals("")&& !eventHost.equals("")) {
            events.add(new Event(   eventName,eventHost, eventImagePath,eventTag, eventDateStart, eventDateEnd,
                                    eventDescription,eventLocation,slotMember));
        }
    }
    public void addEvent(String eventId, String eventHost, String eventName, String imagePath,
                         String eventTag, String eventStart, String eventEnd,
                         String eventDescription, String eventLocation,
                         int member, int slotMember,
                         ActivityList activities,
                         TeamList teams) {
        eventId = eventId.trim();
        eventName = eventName.trim();
        eventHost = eventHost.trim();
        Event exist = findEvent(eventId);
        if (exist == null &&!eventName.equals("") && !eventHost.equals("")){
            events.add(new Event(   eventId,eventHost,eventName,imagePath,eventTag,eventStart,eventEnd,eventDescription,
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
    public void removeEvent(Event event){
        for (Event delete : events) {
            if (delete.getEventID().equals(event.getEventID())) {
                events.remove(event);
            }
        }
    }

    public ArrayList<Event> getEvents() {
        return events;
    }



}
