package cs211.project.services;

import cs211.project.models.collections.TeamList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamListDataSourceTest {

    @Test
    @DisplayName("create team test")
    public void testWrite() {
        TeamListDataSource datasource = new TeamListDataSource("data", "team-list.csv");
        TeamList teamList = datasource.readData();

        for (int i = 0; i < 10; i++) {
            teamList.addTeam("event-KHO24442", "Team " + i, 5);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(teamList.getTeams());
        datasource.writeData(teamList);
    }

}