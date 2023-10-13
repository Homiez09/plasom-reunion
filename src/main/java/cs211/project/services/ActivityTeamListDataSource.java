package cs211.project.services;

import cs211.project.models.ActivityTeam;
import cs211.project.models.collections.ActivityTeamList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ActivityTeamListDataSource implements Datasource<ActivityTeamList>{
    private final String directoryName;
    private final String fileName;

    public ActivityTeamListDataSource(String directoryName, String fileName) {
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
    public ActivityTeamList readData() {
        ActivityTeamList activities = new ActivityTeamList();
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
            // ใช้ while loop เพื่ออ่านข้อมูลรอบละบรรทัด
            while ( (line = buffer.readLine()) != null ){
                // ถ้าเป็นบรรทัดว่าง ให้ข้าม
                if (line.equals("")) continue;

                // แยกสตริงด้วย ,
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)",-1);

                // อ่านข้อมูลตาม index แล้วจัดการประเภทของข้อมูลให้เหมาะสม
                String activityID = data[0].trim();
                String activityName = data[1].trim();
                String activityDescription = data[2].trim();
                String activityStart = data[3].trim();
                String activityEnd = data[4].trim();
                boolean status = Boolean.parseBoolean(data[5].trim());
                String teamID = data[6].trim();

                activities.addActivity(teamID, activityName, activityDescription, activityStart, activityEnd, activityID, status);

                // เพิ่มข้อมูลลงใน list
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return activities;
    }

    @Override
    public void writeData(ActivityTeamList data) {
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

            for (ActivityTeam activityTeam : data.getActivities()) {
                String line = activityTeam.toString();

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
