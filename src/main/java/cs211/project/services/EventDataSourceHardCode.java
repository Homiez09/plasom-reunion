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
        list.createEvent("Mobile expo", "/images/event/event-default.png","12-12-2023","12-12-2023","","KU",888);
        list.createEvent("E-sport Valorant 2", "/images/event/event-default.png","1-2-2023","27-8-2023","","BKK",100);
        list.createEvent("Songskan 3 ", "/images/event/event-default.png","12-4-2023","01-4-2023","","test",50);
        list.createEvent("Commart4", "/images/event/event-default.png","7-7-2023","09-07-2023","","bitec",777);
        list.createEvent("Mobile expo 5", "/images/event/event-default.png","12-10-2023","12-10-2023","","KU",666);
        list.createEvent("Shop 9.9", "/images/event/event-default.png","9-9-2023","9-9-2023","","KU",555);
        list.createEvent("Shop 10.10", "/images/event/event-default.png","10-10-2023","10-10-2023","","KU",155);



        return list;
    }

    @Override
    public void writeData(EventList data) {

    }

}
