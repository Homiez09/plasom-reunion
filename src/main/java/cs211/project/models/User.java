package cs211.project.models;

import at.favre.lib.crypto.bcrypt.BCrypt;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class User {
    private String  username,displayName, password, lastedLogin, imagePath, bio, contactNumber;
    private String  registerDate, userid;
    private boolean admin, status;
    private ImageView avatar; // เอาไว้ return ค่าไปให้ TableView แสดงรูปภาพในหน้า AdminDashboard
    private ArrayList<Event> events = new ArrayList<>();


    public User(String username){
        this.username = username;
        password = null;


    }

    public User( String userid, String displayName, String username, String password, String imagePath, String registerDate, String lastedLogin, boolean status, boolean admin) {
        this(username);
        this.displayName = displayName;
        this.password = password;
        this.status = status;
        this.admin = admin;
        this.userid = userid;
        this.registerDate = registerDate;
        this.lastedLogin = lastedLogin;
        this.bio = "";
        this.imagePath = imagePath;
        this.contactNumber = "";
        this.avatar = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
    }


    public boolean isAdmin(){
        return this.admin;
    }
    public boolean isUserName(String username){
        // to check user verified
        return this.username.equals(username);
    }

    public boolean isDisplayName(String displayName){
        // to check display name verified
        return this.displayName.equals(displayName);
    }

    public boolean validatePassword(String password){
        // to check result verified
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), this.password);
        return result.verified;
    }



    public String getUserid() {
        return userid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getBio() {
        return bio;
    }

    public boolean getStatus() {
        return status;
    }

    public String getLastedLogin() {
        return lastedLogin;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<Event> getEvents() {
        return this.events;
    }


    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void setAvatar(ImageView avatar) {
        this.avatar = avatar;
    }


    public void setLastedLogin(String lastedLogin) {
        this.lastedLogin = lastedLogin;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void joinEvent(Event event){
        if(event.getEventName().equals("")){
            this.events.add(event);
        }
    }
    public ImageView getAvatar(){
        this.avatar.setFitWidth(35);
        this.avatar.setFitHeight(35);
        return avatar;
    }

    public String generateRandomText(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder randomText = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            randomText.append(randomChar);
        }

        return randomText.toString();
    }

}


