package cs211.project.models.collections;

import cs211.project.models.*;

import java.util.*;

public class EventList {
    private ArrayList<Event> events;

    public EventList() {
        events = new ArrayList<>();
    }

    public void createEvent(String eventName, User eventHost, String eventImagePath,
                            String eventTag, String eventDateStart, String eventDateEnd,
                            String eventDescription, String eventLocation) {
        eventName = eventName.trim();
        if (!eventName.equals("") && eventHost != null){
            events.add(new Event(eventName,eventHost,eventImagePath,eventTag,
                                eventDateStart,eventDateEnd,eventDescription,eventLocation));
        }
    }
    public void createEvent(String eventName, User eventHost, String eventImagePath,
                            String eventTag, String eventDateStart, String eventDateEnd,
                            String eventDescription, String eventLocation, int slotMember) {
        eventName = eventName.trim();

        if (!eventName.equals("") && eventHost != null) {
            events.add(new Event(   eventName,eventHost, eventImagePath,eventTag, eventDateStart, eventDateEnd,
                                    eventDescription,eventLocation,slotMember));
        }
    }
    public void addEvent(String eventId, User eventHost, String eventName, String imagePath,
                         String eventTag, String eventStart, String eventEnd,
                         String eventDescription, String eventLocation,
                         int slotMember,String timeStamp,boolean joinEvent,boolean joinTeam) {
        eventId = eventId.trim();
        eventName = eventName.trim();

        Event exist = findEventById(eventId);
        if (exist == null && !eventName.equals("") && eventHost != null){
            events.add(new Event(   eventId,eventHost,eventName,imagePath,eventTag,eventStart,eventEnd,eventDescription,
                                    eventLocation,slotMember,timeStamp,joinEvent,joinTeam));
        }
    }

    public void addEvent(Event event){events.add(event);}

    public Event findEventById(String eventId) {
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

    public void sort(){
        Collections.sort(events);
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public ArrayList<Event> getUserEventAll(User user){
        ArrayList<Event> list = new ArrayList<>();
        for (Event event : events) {
            if (event.isHostEvent(user.getUserId())) {
                list.add(event);
            } else if (event.getUserList().getUsers().contains(user)) {
                list.add(event);
            } else {
                for (Team team:event.getTeamList().getTeams()){
                    if (team.getMemberList().getUsers().contains(user)){
                        list.add(event);
                    }
                }
            }
        }
        return list;
    }

    public ArrayList<Event> getUserEvent(User user){
        ArrayList<Event> list = new ArrayList<>();
        for (Event event : events) {
            if (event.isHostEvent(user.getUserId()) && !event.isEnd()) {
                list.add(event);
            } else if (event.getUserList().getUsers().contains(user) && !event.isEnd()) {
                list.add(event);
            } else {
                for (Team team:event.getTeamList().getTeams()){
                    if (team.getMemberList().getUsers().contains(user) && !event.isEnd()){
                        list.add(event);
                    }
                }
            }
        }
        return list;
    }

    public ArrayList<Event> getUserInEvent(User user){
        ArrayList<Event> list = new ArrayList<>();
        if (user!=null){
            for (Event event:events){
                if (event.getUserList().getUsers().contains(user) && !event.isEnd()){
                    list.add(event);
                }
            }
        }
        return list;
    }

    public ArrayList<Event> getTeamEvent(User user){
        ArrayList<Event> list = new ArrayList<>();
        for (Event event : events){
            for (Team team:event.getTeamList().getTeams()) {
                if (team.getMemberList().getUsers().contains(user) && !event.isEnd()) {
                    list.add(event);
                }
            }
        }
        return list;
    }

    public ArrayList<Event> getOwnerEvent(User user){
        ArrayList<Event> list = new ArrayList<>();
        for (Event event : events){
            if (event.isHostEvent(user.getUserId()) && !event.isEnd()) {
                list.add(event);
            }
        }
        return list;
    }

    public ArrayList<Event> getCompleteEvent(User user){
        ArrayList<Event> remove = new ArrayList<>();
        ArrayList<Event> list = getUserEvent(user);
        for (Event event: getUserEvent(user)){
            if (!event.isEnd()){
                remove.add(event);
            }
        }

        list.removeAll(remove);


        return  list;
    }

    public EventList getUpcomingEvent(){
        EventList list = new EventList();
        for (Event event:events){
            if (event.isUpComing() && !event.isEnd()){
                list.addEvent(event);
            }
        }
        return  list;
    }

    public EventList getNewEvent(){
        EventList list = new EventList();
        for (Event event:events){
            if (event.isNewEvent() && !event.isEnd()){
                list.addEvent(event);
            }
        }
        return  list;
    }

}
