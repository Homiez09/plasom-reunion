package cs211.project.models.collections;

import cs211.project.models.Event;
import cs211.project.models.User;
import cs211.project.services.Datasource;
import javafx.event.EventType;

import java.util.*;

public class EventList {
    private ArrayList<Event> events;
    private Datasource<EventList> datasource;
    private EventList eventList;
    public EventList() {
        events = new ArrayList<>();
    }
    public void addEvent(String eventName, User eventHost, String eventImagePath, String eventTag, String eventDateStart, String eventDateEnd,
                         String eventDescription, String eventLocation) {
        eventName = eventName.trim();
        if (!eventName.equals("") && eventHost != null){
            events.add(new Event(eventName,eventHost,eventImagePath,eventTag,
                                eventDateStart,eventDateEnd,eventDescription,eventLocation));
        }
    }
    public void addEvent(String eventName, User eventHost, String eventImagePath,
                         String eventTag, String eventDateStart, String eventDateEnd,
                         String eventDescription, String eventLocation, int slotMember) {
        eventName = eventName.trim();

        if (!eventName.equals("") && eventHost != null) {
            events.add(new Event(   eventName,eventHost, eventImagePath,eventTag, eventDateStart, eventDateEnd,
                                    eventDescription,eventLocation,slotMember));
        }
    }
    public void addEvent(Event event) {
        events.add(event);
    }
    public void addEvent(String eventId, User eventHost, String eventName, String imagePath,
                         String eventTag, String eventStart, String eventEnd,
                         String eventDescription, String eventLocation,
                         int member, int slotMember,String timeStamp,boolean joinEvent,boolean joinTeam) {
        eventId = eventId.trim();
        eventName = eventName.trim();

        Event exist = findEvent(eventId);
        if (exist == null &&!eventName.equals("") && eventHost != null){
            events.add(new Event(   eventId,eventHost,eventName,imagePath,eventTag,eventStart,eventEnd,eventDescription,
                                    eventLocation,member,slotMember,timeStamp,joinEvent,joinTeam));
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


    public int getSizeTotalEvent(){return events.size();}
    public int getSizeCompletedEvent(){
        int count = 0;
        for (Event event:events) {
            if (event.isEnd()) {
                count++;
            }
        }
        return count;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }



}
