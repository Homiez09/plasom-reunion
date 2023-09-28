package cs211.project.services;


import cs211.project.models.Team;
import cs211.project.models.User;
import cs211.project.models.collections.TeamList;
import cs211.project.models.collections.UserList;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class JoinTeamMap implements Datasource<HashMap<String, TeamList>> {
    private String directoryName = "data";
    private String fileName = "join-team.csv";
    public JoinTeamMap() {
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
    public HashMap<String, TeamList> readData() { // key: username, value: TeamList
        HashMap<String, TeamList> hashMap = new HashMap<>();

        TeamListDataSource teamListDataSource = new TeamListDataSource("data", "team-list.csv");
        HashMap<String, Team> teamHashMap = teamListDataSource.readData().teamHashMap();

        String filePath = directoryName + File.separator + fileName;

        File file = new File(filePath);

        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        InputStreamReader inputStreamReader = new InputStreamReader(
                fileInputStream,
                StandardCharsets.UTF_8
        );

        BufferedReader buffer = new BufferedReader(inputStreamReader);

        String line = "";

        try {
            while ((line = buffer.readLine()) != null) {
                if (line.equals("")) continue;

                String[] data = line.split(",");
                String username = data[0];
                String teamID = data[1];
                String role = data[2].trim();
                boolean isBookmarked = Boolean.parseBoolean(data[3]);

                if (hashMap.containsKey(username)) {
                    TeamList teamList = hashMap.get(username);
                    Team team = teamHashMap.get(teamID);
                    team.setRole(role);
                    team.addMemberToMemberList(username, role);
                    team.setBookmarked(isBookmarked);
                    teamList.addTeam(team);
                    hashMap.put(username, teamList);
                } else {
                    TeamList teamList = new TeamList();
                    Team team = teamHashMap.get(teamID);
                    team.setRole(role);
                    team.addMemberToMemberList(username, role);
                    team.setBookmarked(isBookmarked);
                    teamList.addTeam(team);
                    hashMap.put(username, teamList);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return hashMap;
    }

    @Override
    public void writeData(HashMap<String, TeamList> data) {
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                fileOutputStream,
                StandardCharsets.UTF_8
        );

        BufferedWriter buffer = new BufferedWriter(outputStreamWriter);
        try {
            for (String username : data.keySet()) {
                ArrayList<Team> teamArrayList = data.get(username).getTeams();
                for (Team team : teamArrayList) {
                    buffer.write(username + "," + team.getTeamID() + "," +team.getRole() + "," + team.isBookmarked());
                    buffer.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                buffer.flush();
                buffer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void roleWriteData(HashMap<String, UserList> data) {
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                fileOutputStream,
                StandardCharsets.UTF_8
        );

        BufferedWriter buffer = new BufferedWriter(outputStreamWriter);
        try {
            for (String teamID : data.keySet()) {
                ArrayList<User> userArrayList = data.get(teamID).getUsers();
                for (User user : userArrayList) {
                    buffer.write(user.getUsername() + "," + teamID + "," + user.getRole() + "," + user.isBookmarked());
                    buffer.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                buffer.flush();
                buffer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public HashMap<String, UserList> roleReadData() {
        HashMap<String, UserList> hashMap = new HashMap<>();

        UserListDataSource userListDataSource = new UserListDataSource("data", "user-list.csv");
        HashMap<String, User> userHashMap = userListDataSource.readData().userHashMap(); // key : username, value : User

        String filePath = directoryName + File.separator + fileName;

        File file = new File(filePath);

        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        InputStreamReader inputStreamReader = new InputStreamReader(
                fileInputStream,
                StandardCharsets.UTF_8
        );

        BufferedReader buffer = new BufferedReader(inputStreamReader);

        String line = "";

        try {
            while ((line = buffer.readLine()) != null) {
                if (line.equals("")) continue;

                String[] data = line.split(",");
                String username = data[0];
                String teamID = data[1];
                String role = data[2].trim();
                boolean isBookmarked = Boolean.parseBoolean(data[3]);

                if (hashMap.containsKey(teamID)) {
                    UserList userList = hashMap.get(teamID);
                    User user = userHashMap.get(username);
                    user.setRole(role);
                    user.setBookmarked(isBookmarked);
                    userList.addUser(user);
                    hashMap.put(teamID, userList);
                } else {
                    UserList userList = new UserList();
                    User user = userHashMap.get(username);
                    user.setRole(role);
                    user.setBookmarked(isBookmarked);
                    userList.addUser(user);
                    hashMap.put(teamID, userList);
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return hashMap;
    }
}
