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

    public EventList suffleEvent(EventList eventList){
        EventList list = new EventList();
        Collections.shuffle(events);
        list.getEvents().addAll(eventList.getEvents());

        return list;
    }

    public EventList sortNewEvent(EventList eventList){
        Comparator<Event> comparing = Comparator.comparing(Event::getTimestamp);
        EventList list = new EventList();
        list.getEvents().addAll(eventList.getEvents());
        Collections.sort(list.getEvents(),comparing);

        return list;
    }


    public EventList sortByName(EventList eventList){
        Comparator<Event> comparing = Comparator.comparing(Event::getEventName);
        EventList list = new EventList();
        list.getEvents().addAll(eventList.getEvents());
        Collections.sort(list.getEvents(),comparing);

        return list;
    }

    public EventList sortByMember(EventList eventList){
        Comparator<Event> comparing = Comparator.comparing(Event::getUserInEvent);
        EventList list = new EventList();
        list.getEvents().addAll(eventList.getEvents());
        Collections.sort(list.getEvents(),comparing);
        Collections.reverse(list.getEvents());
        return list;
    }
    public EventList sortByTag(EventList eventList){
        Comparator<Event> comparing = Comparator.comparing(Event::getEventTag);
        EventList list = new EventList();
        list.getEvents().addAll(eventList.getEvents());
        Collections.sort(list.getEvents(),comparing);

        return list;
    }
    public EventList sortByTag(EventList eventList, String tag) {
        Comparator<Event> comparing = Comparator.comparing(Event::getEventTag);
        EventList list = new EventList();
        list.getEvents().addAll(eventList.getEvents());
        Collections.sort(list.getEvents(), comparing);

        EventList filteredList = new EventList();

        for (Event event : list.getEvents()) {
            if (event.getEventTag().equals(tag)) {
                filteredList.getEvents().add(event);
            }
        }

        return filteredList;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public ArrayList<Event> getUserEvent(User user){
        ArrayList<Event> list = new ArrayList<>();
        for (Event event : events) {
            if (event.isHostEvent(user)) {
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

    public ArrayList<Event> getUserInEvent(User user){
        ArrayList<Event> list = new ArrayList<>();
        if (user!=null){
            for (Event event:events){
                if (event.getUserList().getUsers().contains(user)){
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
                if (team.getMemberList().getUsers().contains(user)) {
                    list.add(event);
                }
            }
        }
        return list;
    }

    public ArrayList<Event> getOwnerEvent(User user){
        ArrayList<Event> list = new ArrayList<>();
        for (Event event : events){
            if (event.isHostEvent(user)) {
                list.add(event);
            }
        }
        list.sort(Comparator.comparing(Event::isEnd));
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

    public ArrayList<Event> getUpcomingEvent(){
        ArrayList<Event> list = new ArrayList<>();
        for (Event event:events){
            if (event.isUpComing() && !event.isEnd()){
                list.add(event);
            }
        }
        return  list;
    }

    public ArrayList<Event> getNewEvent(){
        ArrayList<Event> list = new ArrayList<>();
        for (Event event:events){
            if (event.isNewEvent() && !event.isEnd()){
                list.add(event);
            }
        }
        return  list;
    }

}
