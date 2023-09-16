package cs211.project.services;

import cs211.project.models.Event;
import cs211.project.models.User;
import cs211.project.models.collections.EventList;
import cs211.project.models.collections.UserList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class UserListDataSource implements Datasource<UserList> {
    private String directoryName;
    private String fileName;

    private Datasource<UserList> datasource;
    private UserList userList;

    public UserListDataSource(String directoryName, String fileName){
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
    public UserList readData() {
        userList = new UserList();
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
            // ใช้ while loop เพื่ออ่านข้อมูลรอบละบรรทัด
            while ( (line = buffer.readLine()) != null ){
                // ถ้าเป็นบรรทัดว่าง ให้ข้าม
                if (line.equals("")) continue;

                // แยกสตริงด้วย ,
                String[] data = line.split(", DISPLAY-NAME : |, PASSWORD : |, USERNAME : |, CONTACT_NUMBER : |, REGISTER_DATE : |, LASTED_LOGIN : |, IMAGE_PATH : |, STATUS : |, ADMIN : |, SHOW_CONTACT : |, BIO : |, EVENT :");

                // อ่านข้อมูลตาม index แล้วจัดการประเภทของข้อมูลให้เหมาะสม
                String userId = data[0];
                String displayName = data[1].trim();
                String username = data[2].trim();
                String password = data[3].trim();
                String imagePath = data[7];
                String registerDate = data[5];
                String contactNumber = data[4];
                String lastedLogin = data[6];
                String bio = data[11];
                boolean showContactNumber = Boolean.parseBoolean(data[10]);
                boolean status = Boolean.parseBoolean(data[8]);
                boolean admin = Boolean.parseBoolean(data[9]);

                userList.addUser(userId, displayName, username, password, contactNumber, registerDate, lastedLogin, imagePath, status, admin, showContactNumber, bio);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return userList;
    }

    @Override
    public void writeData(UserList data) {
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

            for (User user : data.getUsers()) {

                String line =   user.getUserId() + ", DISPLAY-NAME : "
                        + user.getDisplayName() + ", USERNAME : "
                        + user.getUsername() + ", PASSWORD : "
                        + user.getPassword() + ", CONTACT_NUMBER : "
                        + user.getContactNumber() + ", REGISTER_DATE : "
                        + user.getRegisterDate() + ", LASTED_LOGIN : "
                        + user.getLastedLogin() + ", IMAGE_PATH : "
                        + user.getImagePath() + ", STATUS : "
                        + user.getStatus() + ", ADMIN : "
                        + user.isAdmin() + ", SHOW_CONTACT : "
                        + user.isShowContact() + ", BIO : "
                        + user.getBio() ;



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
