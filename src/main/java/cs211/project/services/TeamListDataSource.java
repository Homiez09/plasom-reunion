package cs211.project.services;

import cs211.project.models.Team;
import cs211.project.models.User;
import cs211.project.models.collections.TeamList;
import cs211.project.models.collections.UserList;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class TeamListDataSource implements Datasource<TeamList> {
    private final String directoryName;
    private final String fileName;
    private final JoinTeamMap joinTeamMap = new JoinTeamMap();


    public TeamListDataSource(String directoryName, String fileName) {
        this.directoryName = directoryName;
        this.fileName = fileName;
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
    public TeamList readData() {
        TeamList teamList = new TeamList();

        UserListDataSource userListDataSource = new UserListDataSource("data", "user-list.csv");
        UserList userList = userListDataSource.readData();

        HashMap<String, UserList> joinTeamMapHashMap = joinTeamMap.roleReadData();

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
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line = "";
        try {
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals("")) continue;
                String[] data = line.split(",");
                String teamID = data[0];
                User teamHostUser = userList.findUserId(data[1].trim());
                String teamName = data[2];
                String teamDescription = data[3];
                String startDate = data[4];
                String endDate = data[5];
                int maxSlotTeamMember = Integer.parseInt(data[6]);
                String createdAt = data[7];
                String eventID = data[8];
                if (joinTeamMapHashMap.get(teamID)==null) {
                    joinTeamMapHashMap.put(teamID, new UserList());
                }

                teamList.addTeam(teamID, teamHostUser,teamName, teamDescription, startDate, endDate, maxSlotTeamMember, createdAt, eventID, joinTeamMapHashMap.get(teamID));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return teamList;
    }

    @Override
    public void writeData(TeamList data) {
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        // เตรียม object ที่ใช้ในการเขียนไฟล์
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
            for (Team team : data.getTeams()) {
                String line =  team.toString();

                buffer.append(line);
                buffer.append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                buffer.flush();
                buffer.close();
            }
            catch (IOException e){
                throw new RuntimeException(e);
            }
        }
    }
}
