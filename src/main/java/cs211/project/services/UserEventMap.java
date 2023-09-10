package cs211.project.services;

import cs211.project.models.*;
import cs211.project.models.collections.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class UserEventMap implements Datasource<HashMap<String,Set<String>>>{
    private String directoryName;
    private String fileName;
    private Datasource<EventList> eventListDatasource;
    private EventList eventList;
    private Event event;
    private Datasource<UserList> userListDatasource;
    private UserList userList;
    private Set<String> eventSet;
    private HashMap<String,Set<String>> hashMap;

    

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
    public HashMap<String, Set<String>> readData() {
        hashMap = new HashMap<>();
        eventSet = new HashSet<>();

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
                    String cleanedData = line.replace("[", "").replace("]", "");
                    String[] data = cleanedData.split(",");
                    String user = data[0].trim();
                    for (int i = 1 ; i <data.length;i++) {
                        eventSet.add(data[i].trim());
                    }

                    hashMap.put(user,eventSet);


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

            for (Map.Entry<String, Set<String>> entry : data.entrySet()) {
                String line ="";
                String user = entry.getKey();
                Set<String> eventList = entry.getValue();
                line = user+","+eventList;
                buffer.write(line);
                buffer.newLine();
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