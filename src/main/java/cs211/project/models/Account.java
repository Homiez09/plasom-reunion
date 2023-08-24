package cs211.project.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Account {
    private String userid;
    private String name;
    private String username;
    private String password;
    private String role;
    private String status;
    private String registerDate;
    private String imagePath;

    public Account(String name, String username, String password, String registerDate,String role) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.registerDate = registerDate;
        this.imagePath = "no-user.jpg";
        this.role = role;
        this.userid = generateRandomUserID();
    }
    public String generateRandomUserID() {
        final int MAX_ID_LENGTH = 16;
        StringBuilder sb = new StringBuilder();

        // ดึงวันปัจจุบัน
        LocalDate currentDate = LocalDate.now();

        // รูปแบบวันที่ในรูป "yyMMdd"
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyMMdd"));

        // ใช้ username และวันที่ปัจจุบันในการสร้าง User ID
        StringBuilder numericText = new StringBuilder();
        for (char c : username.toCharArray()) {
            int numericValue = Character.getNumericValue(c);
            if (numericValue != -1) {  // ถ้าไม่ใช่ตัวอักษรไม่แปลง
                numericText.append(numericValue);
            } else {
                numericText.append(c);
            }
        }

        // ความยาวของ User ID ห้ามเกิน 10 ตัว
        int totalLength = formattedDate.length() + numericText.length();
        if (totalLength > MAX_ID_LENGTH) {
            int excessLength = totalLength - MAX_ID_LENGTH;
            numericText.delete(numericText.length() - excessLength, numericText.length());
        }

        sb.append(formattedDate);
        sb.append(numericText);

        return sb.toString();

    }

    public String getUserid() {
        return userid;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getStatus() {
        return status;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void ChangeName(String changeName) {
        this.name = changeName;
    }
    public void ChangePassword(String changePassword,String oldPassword){
        if (isOldPassword(oldPassword)){
            this.password = changePassword;
        }
    }
    public boolean isOldPassword(String password){
        return this.password.equals(password);
    }


    // Maybe need?
    public void SetStatusOn(){
        this.status = "On";
    }

    public void SetStatusOff(){
        this.status = "Off";
    }
}
