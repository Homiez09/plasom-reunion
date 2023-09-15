package cs211.project.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class EventActivityTest {

    @Test
    @DisplayName("timeToString test")
    public void testTimeToString() {
        EventActivity activity = new EventActivity("event id 001","test name","test description","20/11/2023 08:00","20/11/2023 09:30");
        assertEquals("20/11/2023 09:30",activity.getEndTime());
    }
}