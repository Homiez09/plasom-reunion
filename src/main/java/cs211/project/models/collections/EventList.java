package cs211.project.models.collections;

import cs211.project.models.Event;
import cs211.project.services.Datasource;
import cs211.project.services.EventListDataSource;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class EventList {
    private ArrayList<Event> events;
    private Datasource<EventList> datasource;
    private EventList eventList;
    public EventList() {
        events = new ArrayList<>();
    }
    public void createEvent(String eventName, String eventHost, String eventImagePath, String eventDateStart, String eventDateEnd, String eventDescription, String eventLocation) {
        eventName = eventName.trim();
        eventHost = eventHost.trim();
        if (!eventName.equals("") ){
            events.add(new Event(eventName,eventHost,eventImagePath,eventDateStart,eventDateEnd,eventDescription,eventLocation));
        }
    }
    public void createEvent(String eventName,String eventHost, String eventImagePath, String eventDateStart, String eventDateEnd, String eventDescription,String eventLocation, int slotMember) {
        eventName = eventName.trim();
        if (!eventName.equals("")) {
            events.add(new Event(eventName,eventHost, eventImagePath, eventDateStart, eventDateEnd, eventDescription,eventLocation, slotMember));
        }
    }

    public Event findEvent(String name) {
        for (Event event : events) {
            if (event.getEventName().equals(name)) {
                return event;
            }
        }
        return null;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

}
