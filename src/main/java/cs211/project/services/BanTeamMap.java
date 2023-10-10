package cs211.project.services;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class BanTeamMap implements Datasource<HashMap<String,Set<String>>>{
    private final String directoryName = "data";
    private final String fileName = "ban-team.csv";

    public BanTeamMap(){
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
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public HashMap<String, Set<String>> readData() {
        HashMap<String, Set<String>> hashMap = new HashMap<>();
        Set<String> set;

        String filePath = directoryName + File.separator + fileName;

        File file = new File(filePath);

        // เตรียม object ที่ใช้ในการอ่านไฟล์
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
                String teamID = data[0].trim();
                String userID = data[1].trim();

                if (!hashMap.containsKey(teamID)) {
                    set = new HashSet<>();
                }else {
                    set = hashMap.get(teamID);
                }

                set.add(userID);

                hashMap.put(teamID , set);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return hashMap;
    }

    @Override
    public void writeData(HashMap<String, Set<String>> data) {
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
            for (String teamID : data.keySet()) {
                Set<String> userList = data.get(teamID);
                for (String user:userList){
                    String line = teamID + "," + user;
                    buffer.write(line);
                    buffer.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                buffer.flush();
                buffer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}