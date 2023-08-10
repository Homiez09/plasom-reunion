package cs211.project.models;

import java.util.UUID;

public class Account {
    private String userid;
    private String name;
    private String username;
    private String password;
    private String role;
    private String status;
    private String lastlogin;
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
    public static String generateRandomUserID() {
        UUID uuid = UUID.randomUUID();
        String randomUserID = uuid.toString();
        return randomUserID;
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

    public String getLastlogin() {
        return lastlogin;
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
