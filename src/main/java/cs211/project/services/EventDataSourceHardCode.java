package cs211.project.services;

import cs211.project.models.Event;
import cs211.project.models.collections.EventList;

public class EventDataSourceHardCode implements Datasource<EventList> {

    @Override
    public EventList readData() {
        EventList list = new EventList();
        return list;
    }

    @Override
    public void writeData(EventList data) {

    }

}
