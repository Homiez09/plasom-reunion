package cs211.project.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class EventActivityTest {

    @Test
    @DisplayName("timeToString test")
    public void testTimetoString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime start = LocalDateTime.parse("20/11/2023 08:00",formatter);
        LocalDateTime end = LocalDateTime.parse("20/11/2023 09:30",formatter);
        EventActivity activity = new EventActivity("event id 001","test name","test description",start,end);
        assertEquals("20/11/2023 09:30",activity.timeToString(activity.getEndTime()));
    }
}