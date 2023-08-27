package cs211.project.services;

import cs211.project.models.Event;
import cs211.project.models.collections.EventList;

public class EventDataSourceHardCode implements Datasource<EventList> {

    @Override
    public EventList readData() {
        EventList list = new EventList();
        list.addEvent("E-sport Valorant", "imgpath1","01-02-2023","27/8/2023","",100);
        list.addEvent("Songskan", "imgpath","12/04/2023","16/4/2023","",50);
        list.addEvent("Commart", "imgpath","07/07/2023","09-07-2023","",1000);
        list.addEvent("Commart", "imgpath","12/12/2023","12-12-2023","",1000);

        return list;
    }

    @Override
    public void writeData(EventList data) {

    }

    public void writeData(Event data) {

    }
}
