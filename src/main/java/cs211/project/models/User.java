package cs211.project.models;

import at.favre.lib.crypto.bcrypt.BCrypt;
import cs211.project.services.BanTeamMap;

import java.util.*;

public class User implements Comparable<User>{
    private String  username,displayName, password, lastedLogin, imagePath, bio, contactNumber, newImagePath;
    private String  registerDate, userId, role, teamJoined;

    private boolean bookmark;
    private boolean admin, status, showContact;

    @Override
    public int compareTo(User user) {
        return this.getUsername().compareTo(user.getUsername());
    }

    public User(String userId, String displayName, String username, String password, String contactNumber,
                String registerDate, String lastedLogin, String imagePath, String bio,
                boolean status, boolean admin, boolean showContact) {
        this.username = username;
        this.displayName = displayName;
        this.userId = userId;
        this.password = password;
        this.contactNumber = contactNumber;
        this.status = status;
        this.admin = admin;
        this.registerDate = registerDate;
        this.lastedLogin = lastedLogin;
        this.imagePath = imagePath;
        this.showContact = showContact;
        this.bio = bio;
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

    public String getRole() {
        return role;
    }

    public boolean isBookmarked() {
        return bookmark;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setBookmarked(boolean bookmark) {
        this.bookmark = bookmark;
    }

    public String getTeamJoined() {
        return teamJoined;
    }

    public void setTeamJoined(String teamID) {
        this.teamJoined = teamID;
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


    public void setPassword(String password) {
        this.password = BCrypt.withDefaults().hashToString(12, password.toCharArray());
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

    public boolean isBanFromTeam(String teamId) {
        BanTeamMap banTeamMap = new BanTeamMap();
        HashMap<String, Set<String>> banTeamHashMap = banTeamMap.readData();
        if (!banTeamHashMap.containsKey(teamId)) return false;
        else return banTeamHashMap.get(teamId).contains(userId);
    }
}


