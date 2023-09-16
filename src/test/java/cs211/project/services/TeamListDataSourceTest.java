package cs211.project.services;

import cs211.project.models.Team;
import cs211.project.models.collections.TeamList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TeamListDataSourceTest {

    @Test
    @DisplayName("create team test")
    public void testWrite() {
        TeamListDataSource datasource = new TeamListDataSource("data", "team-list.csv");
        TeamList teamList = datasource.readData();

        JoinTeamMap joinTeamMap = new JoinTeamMap();
//        HashMap<String, ArrayList<Team>> teamHashMap = joinTeamMap.readData();

        for (int i = 50; i < 55; i++) {
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