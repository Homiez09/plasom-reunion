package cs211.project.services;

import cs211.project.models.Event;
import cs211.project.models.collections.EventList;

public class EventDataSourceHardCode implements Datasource<EventList> {

    @Override
    public EventList readData() {
        EventList list = new EventList();
        list.createEvent("E-sport Valorant", "/images/event/event-default.png","01-02-2023","27-8-2023","","BKK",100);
        list.createEvent("Songskan", "/images/event/event-default.png","12-04-2023","16-4-2023","","test",50);
        list.createEvent("Commart", "/images/event/event-default.png","07-07-2023","09-07-2023","","bitec",1000);
        list.createEvent("Mobile expo", "/images/event/event-default.png","12-12-2023","12-12-2023","","KU",1000);
        list.createEvent("E-sport Valorant 2", "/images/event/event-default.png","01-02-2023","27-8-2023","","BKK",100);
        list.createEvent("Songskan 3 ", "/images/event/event-default.png","12-04-2023","1-4-2023","","test",50);
        list.createEvent("Commart4", "/images/event/event-default.png","07-07-2023","09-07-2023","","bitec",1000);
        list.createEvent("Mobile expo 5", "/images/event/event-default.png","12-12-2023","12-12-2023","","KU",1000);


        return list;
    }

    @Override
    public void writeData(EventList data) {

    }

}
