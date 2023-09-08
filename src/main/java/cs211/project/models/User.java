package cs211.project.models;

import at.favre.lib.crypto.bcrypt.BCrypt;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class User {
    private String  username,displayName, password, status, lastedLogin, imagePath, bio, contactNumber;
    private String role, registerDate, userid;
    private ImageView avatar; // เอาไว้ return ค่าไปให้ TableView แสดงรูปภาพในหน้า AdminDashboard
    private ArrayList<Event> events = new ArrayList<>();


    public User(String username){
        this.username = username;
        password = null;


    }

    public User(String displayName, String username, String password, String imagePath) {
        this(username);
        this.displayName = displayName;
        this.password = password;
        this.status = "offline";
        this.role = "user";
        this.userid = generateUserID();
        this.registerDate = generateRegisterDate();
        this.bio = "";
        this.imagePath = imagePath;
        this.contactNumber = "";
        this.avatar = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
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



    private String generateRegisterDate(){
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return formattedDate;
    }

    private String generateUserID() {

        final int MAX_ID_LENGTH = 16;
        StringBuilder sb = new StringBuilder();

        // formatted date & time now
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyMMdd"));
        String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("hhmmss"));

        // create UID by using local time&date
        StringBuilder numericText = new StringBuilder();
        for (char c : username.toCharArray()) {
            int numericValue = Character.getNumericValue(c);
            if (numericValue != -1) {
                numericText.append(numericValue);
            } else {
                numericText.append(c);
            }
        }
        // maximum length is 16
        int totalLength = formattedDate.length() + formattedTime.length() + numericText.length();
        if (totalLength > MAX_ID_LENGTH) {
            int excessLength = totalLength - MAX_ID_LENGTH;
            numericText.delete(numericText.length() - excessLength, numericText.length());
        }
        sb.append(formattedDate);
        sb.append(formattedTime);
        sb.append(numericText);

        return sb.toString();

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

    public String getRole() {
        return role;
    }

    public String getBio() {
        return bio;
    }

    public String getStatus() {
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

    public void setStatus(String status) {
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


}


