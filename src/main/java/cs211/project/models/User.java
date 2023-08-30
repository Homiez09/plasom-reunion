package cs211.project.models;

import at.favre.lib.crypto.bcrypt.BCrypt;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class User {
    private String  username,displayName, password, status, lastedLogin, imagePath;
    private final String role, registerDate, userid;
    private ImageView avatar; // เอาไว้ return ค่าไปให้ TableView แสดงรูปภาพในหน้า AdminDashboard
    private ArrayList<Event> events = new ArrayList<>();


    public User(String username){
        this.username = username;
        password = null;
        this.role = "user";
        this.userid = generateUserID();
        this.registerDate = generateRegisterDate();
    }
    public User(String displayName, String username, String password) {
        this(username);
        this.displayName = displayName;
        setPassword(password);
        this.status = "offline";
        this.imagePath = generateAvatar();
        this.avatar = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
    }

    public boolean isUserName(String username){
        // to check user verified
        return this.username.equals(username);
    }

    public boolean validatePassword(String password){
        // to check result verified
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), this.password);
        return result.verified;
    }

    private String generateAvatar(){
        int randomAvatar = (int)(Math.random()*10);
        return "/images/profile/default-avatar/default" + randomAvatar + ".png";
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

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Event> getEvents() {
        return this.events;
    }

    public void setPassword(String password){
        this.password = BCrypt.withDefaults().hashToString(12, password.toCharArray());
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

