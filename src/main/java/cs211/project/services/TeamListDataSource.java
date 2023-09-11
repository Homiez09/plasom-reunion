package cs211.project.services;

import cs211.project.models.Team;
import cs211.project.models.User;
import cs211.project.models.collections.TeamList;
import cs211.project.models.collections.UserList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class TeamListDataSource implements Datasource<TeamList> {
    private String directoryName;
    private String fileName;
    private TeamList teamList;

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
        teamList = new TeamList();
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
                String teamName = data[1];
                String teamDescription = data[2];
                String teamImagePath = data[3];
                int maxSlotTeamMember = Integer.parseInt(data[4]);
                String createdAt = data[5];
                String eventID = data[6];

                teamList.addTeam(teamID, teamName, teamDescription, teamImagePath, maxSlotTeamMember, createdAt, eventID);
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
                String line =  team.getTeamID() + ","
                        + team.getTeamName() + ","
                        + team.getTeamDescription() + ","
                        + team.getTeamImagePath() + ","
                        + team.getMaxSlotTeamMember() + ","
                        + team.getCreatedAt() + ","
                        + team.getEventID();


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
