package cs211.project.services;


import cs211.project.models.Team;
import cs211.project.models.User;
import cs211.project.models.collections.TeamList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class JoinTeamMap implements Datasource<HashMap<String, Team>> {
    private String directoryName = "data";
    private String fileName = "join-team.csv";
    User user;
    TeamList teamList;
    public JoinTeamMap(User user, TeamList teams) {
        this.user = user;
        this.teamList = teams;
        checkFileIsExisted();
    }

    private void checkFileIsExisted() {
        File file = new File(directoryName);
        if (!file.exists()) {
            file.mkdirs();
        }

        String filePath = directoryName + File.separator + fileName;
        file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public HashMap<String, Team> readData() {
        HashMap<String, Team>  joinTeamMap = new HashMap<>();

        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        InputStreamReader inputStreamReader = new InputStreamReader(
                fileInputStream,
                StandardCharsets.UTF_8
        );
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line = "";
        try {
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals("")) continue;
                String[] data = line.split(",");
                String username = data[0]; // check
                String teamID = data[1]; // key
                String role = data[2]; // value

                if (user.isUserName(username)) {
                    HashMap<String, Team> teams = teamList.teamHashMap();
                    Team team = teams.get(teamID);
                    team.setRole(role);
                    joinTeamMap.put(teamID, team);
                    System.out.println(user.getUsername());
                    System.out.println(username);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return joinTeamMap;
    }

    @Override
    public void writeData(HashMap<String, Team> data) {

    }
}
