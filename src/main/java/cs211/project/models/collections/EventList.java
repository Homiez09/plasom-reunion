package cs211.project.models.collections;

import cs211.project.models.Event;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class EventList<T> implements Iterable {
    private ArrayList<Event> events;

    public EventList() {
        events = new ArrayList<>();
    }
    public void addEvent(String eventName, String eventImagePath, String eventDateStart, String eventDateEnd, String eventDescription) {
        eventName = eventName.trim();
        if (!eventName.equals("") ){
            events.add(new Event(eventName,eventImagePath,eventDateStart,eventDateEnd,eventDescription));
        }
    }

    public void addEvent(String eventName, String eventImagePath, String eventDateStart, String eventDateEnd, String eventDescription, int slotMember) {
        eventName = eventName.trim();
        eventDateStart = eventDateStart.trim();
        eventDateEnd = eventDateEnd.trim();
        if (!eventName.equals("") && eventDateStart.equals("") && eventDateEnd.equals("") ){
            events.add(new Event(eventName,eventImagePath,eventDateStart,eventDateEnd,eventDescription,slotMember));
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

    public int size(){
        int count =0;
        for (Event event : events)
            count++;
        return count;
    }


    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator spliterator() {
        return Iterable.super.spliterator();
    }
}
