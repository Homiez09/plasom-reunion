package cs211.project.models;

import at.favre.lib.crypto.bcrypt.BCrypt;
import cs211.project.models.collections.UserList;
import cs211.project.services.BanTeamMap;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class User implements Comparable<User>{
    private String  username,displayName, password, lastedLogin, imagePath, bio, contactNumber, newImagePath;
    private String  registerDate, userId, role, formattedDate;

    private boolean bookmark;
    private boolean admin, status, showContact;

    @Override
    public int compareTo(User user) {
        return this.getUsername().compareTo(user.getUsername());
    }

    public User(String displayName, String username, String password){
        this.displayName = displayName;
        this.username = username;
        setPassword(password);
        generateUserID();
        this.contactNumber = "";
        this.status = false;
        this.admin = false;
        this.registerDate = generateRegisterDate();
        this.imagePath = generateAvatar();
        this.showContact = false;
        this.bio = "";
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

    private String generateRegisterDate(){
        LocalDate currentDate = LocalDate.now();
        formattedDate = currentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return formattedDate;
    }
    private String generateLastedLogin(){
        LocalDateTime currentDate = LocalDateTime.now();
        formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yy-MM-dd : hh:mm:ss").withLocale(Locale.US));
        return formattedDate;
    }
    private String generateAvatar(){
        String path ;
        int randomAvatar = (int)(Math.random()*10);
        path = "x/images/profile/default-avatar/default" + randomAvatar + ".png";
        return path;
    }
    private void generateUserID() {
        Random random = new Random();
        String id = "user-";
        int ranInt = random.nextInt(1000000);
        String ranText = generateRandomText();
        id = id + ranText + ranInt;
        this.userId = id;
    }
    private String generateRandomText() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder randomText = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            randomText.append(randomChar);
        }

        return randomText.toString();
    }


    public boolean isAdmin(){
        return this.admin;
    }
    public boolean isUserName(String username){
        return this.username.equals(username);
    }
    public boolean isDisplayName(String displayName){
        return this.displayName.equals(displayName);
    }
    public boolean isId(String userId){
        return this.userId.equals(userId);
    }
    public boolean isBookmarked() {
        return bookmark;
    }
    public boolean isShowContact() {
        return showContact;
    }
    public boolean isBanFromTeam(String teamId) {
        BanTeamMap banTeamMap = new BanTeamMap();
        HashMap<String, Set<String>> banTeamHashMap = banTeamMap.readData();
        if (!banTeamHashMap.containsKey(teamId)) return false;
        else return banTeamHashMap.get(teamId).contains(userId);
    }
    public boolean isAlreadyJoinTeam(UserList memberList) {
        if (memberList == null) return false;
        for (User user: memberList.getUsers()) {
            if (user.getUserId().equals(userId)) return true;
        }
        return false;
    }

    public boolean validatePassword(String password){
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
    public String getContactNumber() {
        return contactNumber;
    }
    public String getImagePath() {
        return imagePath;
    }
    public String getNewImagePath() {
        return newImagePath;
    }

    public String getRole() {
        return role;
    }


    public void setRole(String role) {
        this.role = role;
    }
    public void setBookmarked(boolean bookmark) {
        this.bookmark = bookmark;
    }
    public void setShowContact(boolean showContact) {
        this.showContact = showContact;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public void setPassword(String password) {
        this.password = BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }
    public void setNewImagePath(String newImagePath) {
        this.newImagePath = newImagePath;
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
    public void updateAfterLogin(){
        this.lastedLogin = generateLastedLogin();
        this.status = true;
    }




}


