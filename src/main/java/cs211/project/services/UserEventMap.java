package cs211.project.services;

import cs211.project.models.*;
import cs211.project.models.collections.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class UserEventMap implements Datasource<HashMap<User,Set<Event>>>{
    private String directoryName;
    private String fileName;
    private Datasource<EventList> eventListDatasource;
    private EventList eventList;
    private Event event;
    private Datasource<UserList> userListDatasource;
    private UserList userList;
    private Set<Event> eventSet;
    private HashMap<User,Set<Event>> hashMap;

    

    public UserEventMap(String directoryName, String fileName) {
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
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public HashMap<User, Set<Event>> readData() {
        hashMap = new HashMap<>();
        eventSet = new HashSet<>();
        
        eventListDatasource = new EventListDataSource("data", "event-list.csv");
        eventList = eventListDatasource.readData();

        userListDatasource = new UserListDataSource("data", "user-list.csv");
        userList = userListDatasource.readData();

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
                if (!line.isEmpty()) {
                    String[] data = line.split(",");
                    User user = userList.findUsername(data[0].trim());
                    String[] parts = data[1].split("\\[|,|\\]");
                    for (String id:parts) {
                        Event event = eventList.findEvent(id);
                        eventSet.add(event);
                    }

                    hashMap.put(user,eventSet);

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return hashMap;
    }

    @Override
    public void writeData(HashMap<User, Set<Event>> data) {
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
            String line ="";

            for (Map.Entry<User, Set<Event>> entry : data.entrySet()) {
                User user = entry.getKey();
                Set<Event> eventList = entry.getValue();


                line = user.getUsername()+","+ eventList;
            }
            buffer.write(line);
            buffer.newLine();


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