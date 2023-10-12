package cs211.project.models.collections;

import cs211.project.models.*;
import cs211.project.services.*;

import java.util.*;

public class EventList {
    private final ArrayList<Event> events;

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
                         int slotMember,String timeStamp,boolean joinEvent,String joinTimeStart,String joinTimeEnd) {
        eventId = eventId.trim();
        eventName = eventName.trim();

        Event exist = findEventById(eventId);
        if (exist == null && !eventName.equals("") && eventHost != null){
            events.add(new Event(   eventId,eventHost,eventName,imagePath,eventTag,eventStart,eventEnd,eventDescription,
                                    eventLocation,slotMember,timeStamp,joinEvent,joinTimeStart,joinTimeEnd));
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

    public void changeName(Event event,String change){
        Event changeEvent = findEventById(event.getEventID());
        changeEvent.changeName(change);
    }
    public void changeDescription(Event event,String change){
        Event changeEvent = findEventById(event.getEventID());
        changeEvent.changeDescription(change);
    }
    public void changeImagePath(Event event,String change){
        Event changeEvent = findEventById(event.getEventID());
        changeEvent.changeEventImagePath(change);
    }
    public void changeSlotMember(Event event,int change){
        Event changeEvent = findEventById(event.getEventID());
        changeEvent.changeSlotMember(change);
    }
    public void changeDateStart(Event event,String change){
        Event changeEvent = findEventById(event.getEventID());
        changeEvent.changeDateStart(change);
    }
    public void changeDateEnd(Event event,String change){
        Event changeEvent = findEventById(event.getEventID());
        changeEvent.changeDateEnd(change);
    }
    public void changeTag(Event event,String change){
        Event changeEvent = findEventById(event.getEventID());
        changeEvent.changeTag(change);
    }
    public void changeLocation(Event event,String change){
        Event changeEvent = findEventById(event.getEventID());
        changeEvent.changeLocation(change);
    }
    public void changeStatus(Event event,boolean join){
        Event changeEvent = findEventById(event.getEventID());
        changeEvent.setJoinEvent(join);
    }

    public void changeJoinTimeStart(Event event,String change){
        Event changeEvent = findEventById(event.getEventID());
        changeEvent.changeJoinTimeStart(change);
    }
    public void changeJoinTimeEnd(Event event,String change){
        Event changeEvent = findEventById(event.getEventID());
        changeEvent.changeJoinTimeEnd(change);
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
        return getListCompare(eventList, Comparator.comparing(Event::getTimestamp));
    }

    private EventList getListCompare(EventList eventList, Comparator<Event> cmp) {
        EventList list = new EventList();
        list.getEvents().addAll(eventList.getEvents());
        list.getEvents().sort(cmp);
        Collections.reverse(list.getEvents());

        return list;
    }

    public EventList sortUpcoming(EventList eventList){
        Comparator<Event> comparing = Comparator.comparing(Event::getDateStartAsDate);
        EventList list = new EventList();

        list.getEvents().addAll(eventList.getEvents());
        list.getEvents().sort(comparing);

        return list;
    }

    public EventList sortByName(EventList eventList){
        Comparator<Event> comparing = Comparator.comparing(Event::getEventName);
        EventList list = new EventList();
        list.getEvents().addAll(eventList.getEvents());
        list.getEvents().sort(comparing);

        return list;
    }

    public EventList sortByMember(EventList eventList){
        return getListCompare(eventList, Comparator.comparing(Event::getUserInEvent));
    }

    public EventList sortByTag(EventList eventList, String tag) {
        Comparator<Event> comparing = Comparator.comparing(Event::getEventTag);
        EventList list = new EventList();
        list.getEvents().addAll(eventList.getEvents());
        list.getEvents().sort(comparing);

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
        Datasource<TeamList> teamListDatasource = new TeamListDataSource("data","team-list.csv");
        TeamList teamList = teamListDatasource.readData();

        ArrayList<Event> list = new ArrayList<>();
        for (Event event : events) {
            if (event.isHostEvent(user)) {
                list.add(event);
            } else if (event.getUserList().getUsers().contains(user)) {
                list.add(event);
            } else {
                for (Team team:teamList.getTeams()){
                    if (team.getMemberList().getUsers().contains(user) && event.getEventID().equals(team.getEventID())){
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
        Datasource<TeamList> teamListDatasource = new TeamListDataSource("data","team-list.csv");
        TeamList teamList = teamListDatasource.readData();

        ArrayList<Event> list = new ArrayList<>();
        for (Event event : events){
            for (Team team:teamList.getTeams()) {
                if (team.getMemberList().getUsers().contains(user) && event.getEventID().equals(team.getEventID())) {
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

    public ArrayList<Event> getUpcomingEvent() {
        ArrayList<Event> list = new ArrayList<>();
        for (Event event : events) {
            if (!event.isEnd()) {
                list.add(event);
            }
        }
        list.sort(Comparator.comparing(Event::getDateStartAsDate));
        return list;
    }

    public ArrayList<Event> getNewEvent(){
        ArrayList<Event> list = new ArrayList<>();
        for (Event event:events){
            if (!event.isEnd()){
                list.add(event);
            }
        }
        list.sort(Comparator.comparing(Event::getTimestamp));
        Collections.reverse(list);
        return  list;
    }

    public EventList getAvailableEvent(){
        EventList list = new EventList();
        for (Event event:events){
            if (!event.isEnd()){
                list.getEvents().add(event);
            }
        }
        return list;
    }
}
