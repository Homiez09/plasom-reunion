package cs211.project.services;

import cs211.project.models.User;
import cs211.project.models.collections.UserList;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class JoinEventMap implements Datasource<HashMap<String, UserList>>{
    private final String directoryName = "data";
    private final String fileName = "join-event.csv";
    private HashMap<String,UserList> hashMap;
    public JoinEventMap(){
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
    public HashMap<String, UserList> readData() {
        hashMap = new HashMap<>();
        UserList userList ;

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
            Datasource<UserList> userListDatasource = new UserListDataSource("data", "user-list.csv");
            UserList list = userListDatasource.readData();
            while ((line = buffer.readLine()) != null) {
                if (line.equals("")) continue;
                    String[] data = line.split(",");
                    String event = data[0].trim();
                    if (!hashMap.containsKey(event)) {
                        userList = new UserList();
                    }else {
                        userList = hashMap.get(event);
                    }
                    if (!userList.getUsers().contains(list.findUserId(data[1].trim()))) {
                        userList.addUser(list.findUserId(data[1].trim()));
                    }
                    hashMap.put(event , userList);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return hashMap;
    }

    @Override
    public void writeData(HashMap<String, UserList> data) {
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
            for (Map.Entry<String, UserList> entry : data.entrySet()) {
                String eventID = entry.getKey();
                UserList userList = entry.getValue();
                    for (User user : userList.getUsers()) {
                        String line = eventID + "," + user.getUserId();
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