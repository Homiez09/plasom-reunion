package cs211.project.services;

import cs211.project.models.Team;
import cs211.project.models.User;
import cs211.project.models.collections.TeamList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

class JoinTeamMapTest {

    @Test
    public void testReadData() {
        JoinTeamMap joinTeamMap = new JoinTeamMap();
        HashMap<String, TeamList> hashMap = joinTeamMap.readData();

        for (String key: hashMap.keySet()) {
            System.out.println(key);
            for (Team team: hashMap.get(key).getTeams()) {
                System.out.println(team.getTeamName());
            }
        }
    }

//    @Test
//    public void testWriteData() {
//        JoinTeamMap joinTeamMap = new JoinTeamMap();
////        HashMap<String, ArrayList<Team>> hashMap = joinTeamMap.readData();
//        ArrayList<Team> teams;
//        teams = hashMap.get("mingmmie");
//        if (teams == null) {
//            hashMap.put("mingmmie", new ArrayList<>());
//            teams = hashMap.get("mingmmie");
//            teams.add(new Team("2023091203454856","Team 5","","",5,"2023-09-12|03:45:48:198","event-ByN72059"));
//        } else {
//            // todo : เช็ค id ซ้ำกัน
//            teams.add(new Team("2023091203454856","Team 5","","",5,"2023-09-12|03:45:48:198","event-ByN72059"));
//        }
//        joinTeamMap.writeData(hashMap);
//    }
}