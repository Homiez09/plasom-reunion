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
        teamList.addTeam("Team 1", 5);
        teamList.addTeam("Team 2", 5, "Team 2 description");
        teamList.addTeam("Team 3", 5, "Team 3 description", "Team 3 image path");
        datasource.writeData(teamList);
    }

}