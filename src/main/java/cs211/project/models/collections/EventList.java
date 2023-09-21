package cs211.project.models.collections;

import cs211.project.models.Event;
import cs211.project.models.Team;
import cs211.project.models.User;
import cs211.project.services.*;

import java.util.*;

public class EventList {
    private ArrayList<Event> events;
    private JoinEventMap eventMapData;
    private HashMap<String,Set<String>> eventHashMap;
    private Set<String> eventSet;
    private Datasource<TeamList> teamListDatasource;
    private TeamList teamList;
    public EventList() {
        events = new ArrayList<>();
    }
    public void createEvent(String eventName, User eventHost, String eventImagePath, String eventTag, String eventDateStart, String eventDateEnd,
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
    public void setTeamData(String eventID){
        teamListDatasource = new TeamListDataSource("data","team-list.csv");
        teamList = teamListDatasource.readData();

        TeamList temp = new TeamList();
        for (Team team:teamList.getTeams()) {
            if (team.getEventID().equals(eventID)){
                temp.addTeam(team);
            }
        }
        findEvent(eventID).setTeamList(temp);
    }
    public void setMemberData(){
        this.eventMapData = new JoinEventMap();
        this.eventHashMap = eventMapData.readData();
        eventSet = new HashSet<>();

        for (Event event:events) {
            if (eventHashMap.containsKey(event.getEventID())){
                eventSet = eventHashMap.get(event.getEventID());
                event.setMember(eventSet.size());
            }

        }
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

    public EventList sortUpcoming(EventList eventList){
        Comparator<Event> comparing = Comparator.comparing(Event::getEventDateStart);
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
    public EventList sortById(EventList eventList){
        Comparator<Event> comparing = Comparator.comparing(Event::getEventID);
        EventList list = new EventList();
        list.getEvents().addAll(eventList.getEvents());
        Collections.sort(list.getEvents(),comparing);

        return list;
    }
    public EventList sortByMember(EventList eventList){
        Comparator<Event> comparing = Comparator.comparing(Event::getMember);
        EventList list = new EventList();
        list.getEvents().addAll(eventList.getEvents());
        Collections.sort(list.getEvents(),comparing);

        return list;
    }
    public EventList sortByTag(EventList eventList){
        Comparator<Event> comparing = Comparator.comparing(Event::getEventTag);
        EventList list = new EventList();
        list.getEvents().addAll(eventList.getEvents());
        Collections.sort(list.getEvents(),comparing);

        return list;
    }
    public EventList getOwner(EventList eventList,User user){
        EventList list = new EventList();
        if (user!=null) {
            for (Event event : eventList.events) {
                if (event.getEventHostUser().getUserId().equals(user.getUserId())) {
                    list.addEvent(event);
                }
            }
        }
        return list;
    }




}
