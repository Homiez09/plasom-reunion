package cs211.project.services;

import cs211.project.models.Team;
import cs211.project.models.collections.TeamList;

public class TeamDataSourceHardCode implements Datasource<TeamList> {
    @Override
    public TeamList readData() {
        TeamList list = new TeamList();
        list.addTeam("Team1", "Team1", "imgpath1", null, 5);
        list.addTeam("Team2", "Team2", "imgpath2", null, 5);
        list.addTeam("Team3", "Team3", "imgpath3", null, 5);
        list.addTeam("Team4", "Team4", "imgpath4", null, 5);

        return list;
    }

    @Override
    public void writeData(TeamList data) {

    }

    public void writeData(Team data) {

    }
}
