package cs211.project.services;

import cs211.project.models.*;
import cs211.project.models.collections.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class EventListDataSource implements Datasource<EventList> {
    private String directoryName;
    private String fileName;
    private Datasource<ActivityList> activityListDatasource;
    private Datasource<TeamList> teamListDatasource;
    private EventList eventList;
    private ActivityList activityList;
    private TeamList teamList;
    public EventListDataSource(String directoryName, String fileName) {
        this.directoryName = directoryName;
        this.fileName = fileName;
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
    public EventList readData() {
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
            eventList = new EventList();
            // ใช้ while loop เพื่ออ่านข้อมูลรอบละบรรทัด
            while ( (line = buffer.readLine()) != null ){
                // ถ้าเป็นบรรทัดว่าง ให้ข้าม
                if (line.equals("")) continue;

                // แยกสตริงด้วย ,
                String[] data = line.split(",");

                // อ่านข้อมูลตาม index แล้วจัดการประเภทของข้อมูลให้เหมาะสม

                String eventId = data[0].trim();
                String eventHost = data[1].trim();
                String eventName = data[2].trim();
                String imagePath = data[3].trim();
                String eventTag = data[4].trim();
                String eventStart = data[5].trim();
                String eventEnd = data[6].trim();
                String eventDescription = data[7].trim();
                String eventLocation = data[8].trim();
                int member = Integer.parseInt(data[9].trim());
                int slotmember = Integer.parseInt(data[10].trim());
                String timeStamp = data[11].trim();

                eventList.addEvent(     eventId,eventHost, eventName, imagePath,eventTag, eventStart, eventEnd,
                                        eventDescription, eventLocation, member, slotmember,timeStamp);


                // เพิ่มข้อมูลลงใน list
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return eventList;
    }
    @Override
    public void writeData(EventList data) {
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
            // สร้าง csv
            for (Event event : data.getEvents()) {
                String line = event.toString();

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
