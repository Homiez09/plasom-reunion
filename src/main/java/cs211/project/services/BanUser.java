package cs211.project.services;

import cs211.project.models.Event;
import cs211.project.models.collections.EventList;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


public class BanUser implements Datasource<HashMap<String, EventList>>{
    private final String directoryName = "data";
    private final String fileName = "ban-user.csv";
    private HashMap<String,EventList> hashMap ;

    public BanUser() {
        checkFileIsExisted();
    }
    // ตรวจสอบว่ามีไฟล์ให้อ่านหรือไม่ ถ้าไม่มีให้สร้างไฟล์เปล่า
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
    public HashMap<String, EventList> readData() {
        hashMap = new HashMap<>();
        EventList banList ;
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
            Datasource<EventList> eventListDatasource = new EventListDataSource();
            EventList list = eventListDatasource.readData();
            while ((line = buffer.readLine()) != null) {
                if (line.equals("")) continue;
                String[] data = line.split(",");
                String userID= data[0].trim();

                if (!hashMap.containsKey(userID)) {
                    banList = new EventList();
                }else {
                    banList = hashMap.get(userID);
                }
                String eventId = data[1].trim();
                if (!banList.getEvents().contains(list.findEventById(eventId))) {
                    banList.getEvents().add(list.findEventById(eventId));
                }
                hashMap.put(userID, banList);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return hashMap;
    }

    @Override
    public void writeData(HashMap<String, EventList> data) {
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
            for (Map.Entry<String, EventList> entry : data.entrySet()) {
                String user = entry.getKey();
                EventList banList = entry.getValue();

                    for (Event eventId : banList.getEvents()) {
                        String line = user + "," + eventId.getEventID();
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
