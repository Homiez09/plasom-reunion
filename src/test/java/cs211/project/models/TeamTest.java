package cs211.project.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    @Test
    void setStartDate() {
        Team team = new Team("1", "1", "2023-10-10.10:11", "2023-10-12.10:11", 5);
        team.setStartDate("2021-04-01.12:50");
        System.out.println(team.getStartDate()); // timestamp (millisecond)
        System.out.println(team.formatTimestampToString(team.getStartDate())); // date (yyyy-MM-dd.HH:mm:ss)
    }
}