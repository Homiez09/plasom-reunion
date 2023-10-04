package cs211.project.models.collections;

import cs211.project.models.*;
import cs211.project.services.*;

import java.util.*;

public class EventList {
    private ArrayList<Event> events;
    private JoinEventMap eventMapData;
    private HashMap<String, UserList> joinEvent;
    private UserList userList;
    private JoinTeamMap teamMapData;
    private HashMap<String,TeamList> joinTeam;
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
                         int slotMember,String timeStamp,boolean joinEvent,boolean joinTeam) {
        eventId = eventId.trim();
        eventName = eventName.trim();

        Event exist = findEvent(eventId);
        if (exist == null &&!eventName.equals("") && eventHost != null){
            events.add(new Event(   eventId,eventHost,eventName,imagePath,eventTag,eventStart,eventEnd,eventDescription,
                                    eventLocation,slotMember,timeStamp,joinEvent,joinTeam));
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

    public void sort(){
        Collections.sort(events);
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public ArrayList<Event> getCurrentEvent(User user){
        ArrayList<Event> list = new ArrayList<>();
        teamMapData = new JoinTeamMap();
        teamList = teamMapData.readData().get(user.getUsername());
        for (Team team:teamList.getTeams()){
            System.out.println(team.getMemberList().getUsers().contains(user));
        }

        for (Event event : events) {
            System.out.println(event.getTeamList());
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
        if (user!=null){
            for (Event event:events){
                for (User u : event.getUserList().getUsers()){
                    if (u.equals(user)){
                        list.add(event);
                    }
                }
            }
        }
        return list;
    }

    public ArrayList<Event> getOwner(User user){
        if (user!=null) {
            events.removeIf(event -> !event.getEventHostUser().getUserId().equals(user.getUserId()));
        }
        return events;
    }

    public ArrayList<Event> getComplete(User user){
        eventMapData = new JoinEventMap();
        joinEvent = eventMapData.readData();



        return  events;
    }



}
