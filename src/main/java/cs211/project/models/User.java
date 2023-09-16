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
    private String  username,displayName, password, lastedLogin, imagePath, bio, contactNumber, newImagePath;
    private String  registerDate, userId;
    private boolean admin, status, showContact;
    private ArrayList<Event> events = new ArrayList<>();

    public User(String userId, String displayName, String username, String password, String contactNumber,
                String registerDate, String lastedLogin, String imagePath,
                boolean status, boolean admin, boolean showContact) {
        this.username = username;
        this.displayName = displayName;
        this.password = password;
        this.lastedLogin = lastedLogin;
        this.imagePath = imagePath;
        this.contactNumber = contactNumber;
        this.registerDate = registerDate;
        this.userId = userId;
        this.bio ="";
        this.admin = admin;
        this.status = status;
        this.showContact = showContact;
    }

    public User(String userId, String displayName, String username, String password, String contactNumber,
                String registerDate, String lastedLogin, String imagePath,
                boolean status, boolean admin, boolean showContact, String bio) {
        this.username = username;
        this.displayName = displayName;
        this.userId = userId;
        this.password = password;
        this.contactNumber = contactNumber;
        this.status = status;
        this.admin = admin;
        this.registerDate = registerDate;
        this.lastedLogin = lastedLogin;
        this.bio = bio;
        this.imagePath = imagePath;
        this.showContact = showContact;
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

    public boolean isId(String userId){
        // to check user ID verified
        return this.userId.equals(userId);
    }


    public boolean validatePassword(String password){
        // to check result verified
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), this.password);
        return result.verified;
    }


    public String getUserId() {
        return userId;
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


    public boolean isShowContact() {
        return showContact;
    }

    public void setShowContact(boolean showContact) {
        this.showContact = showContact;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getNewImagePath() {
        return newImagePath;
    }

    public ArrayList<Event> getEvents() {
        return this.events;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNewImagePath(String newImagePath) {
        this.newImagePath = newImagePath;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void setLastedLogin(String lastedLogin) {
        this.lastedLogin = lastedLogin;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void updateProfile(String displayName, String contactNumber, String bio, String newImagePath) {
        this.displayName = displayName;
        this.contactNumber = contactNumber;
        this.bio = bio;
        setImagePath(newImagePath);
    }



}


