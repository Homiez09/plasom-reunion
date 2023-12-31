package cs211.project.models.collections;

import cs211.project.models.User;
import cs211.project.services.JoinEventMap;

import java.util.*;

public class UserList {
    private final ArrayList<User> users;

    public UserList() {
        users = new ArrayList<>();
    }

    public User findUsername(String username) {
        for (User exist: users) {
            if (exist.isUserName(username) && !username.isEmpty()) {
                return exist;
            }
        }
        return null;
    }

    public User findDisplayName(String displayName) {
        for (User exist: users) {
            if (exist.isDisplayName(displayName)) {
                return exist;
            }
        }
        return null;
    }

    public User findUserId(String userId) {
        for (User exist: users) {
            if (exist.isId(userId)) {
                return exist;
            }
        }
        return null;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void addUser(String userId, String displayName, String username, String password, String contactNumber, String registerDate, String lastedLogin, String imagePath, String bio, boolean status, boolean admin, boolean showContactNumber){
        username = username.trim();
        password = password.trim();
        User newUser = findUsername(username);
        if(newUser == null){
            users.add(new User(userId, displayName, username, password, contactNumber, registerDate, lastedLogin, imagePath, bio, status, admin,showContactNumber));
        }
    }
    public void addUser(String displayName, String username, String password){
        username = username.trim();
        password = password.trim();
        User newUser = findUsername(username);
        if(newUser == null){
            users.add(new User(displayName, username, password));
        }
    }

    public User login(String username, String password){
        for(User exist: users){
            if(exist.getUsername().equals(username) && exist.validatePassword(password) && !username.isEmpty() && !password.isEmpty()){
                return exist;
            }
        }
        return null;
    }

    public void logout(User oldUser) {
        User newUser = findUsername(oldUser.getUsername());
        newUser.setStatus(false);
    }

    public void resetPassword(String username, String newPassword){
        User exist = findUsername(username);
        if(exist != null){
            exist.setPassword(newPassword);
        }
    }

    public void updateUserProfile(String username, String displayName, String contactNumber, String bio, String newImagePath) {
        User exist = findUsername(username);
        if (exist != null) {
            exist.updateProfile(displayName, contactNumber, bio, newImagePath);
        }
    }

    public void updateUserShowContact(String username, boolean showContactNumber){
        User exist = findUsername(username);
        if(exist != null){
            exist.setShowContact(showContactNumber);
        }
    }

    public ArrayList<User> getNotAdminUsers() {
        ArrayList<User> notAdminUsers = new ArrayList<>();
        for (User exist: users) {
            if (!exist.isAdmin()) {
                notAdminUsers.add(exist);
            }
        }
        return notAdminUsers;
    }

    public ArrayList<User> getOnlineUsers() {
        ArrayList<User> onlineUsers = new ArrayList<>();
        for (User exist: users) {
            if (exist.getStatus() && !exist.isAdmin()) {
                onlineUsers.add(exist);
            }
        }
        return onlineUsers;
    }

    public void promoteToLeader(String userID) {
        User userLeader = getLeader();
        if (userLeader != null) userLeader.setRole("Member");

        User user = findUserId(userID);
        user.setRole("Leader");

    }

    public void promoteToMember(String userID) {
        User user = findUserId(userID);
        user.setRole("Member");

    }

    public User getLeader() {
        for (User user : users) {
            if (user.getRole().equals("Leader")) {
                return user;
            }
        }
        return null;
    }

    public HashMap<String, User> userHashMap() {
        HashMap<String, User> userHashMapTemp= new HashMap<>();
        for (User user: users) {
            userHashMapTemp.put(user.getUsername(), user);
        }

        return userHashMapTemp;
    }

    public HashMap<String, User> userIdHashMap() {
        HashMap<String, User> userHashMapTemp= new HashMap<>();
        for (User user: users) {
            userHashMapTemp.put(user.getUserId(), user);
        }

        return userHashMapTemp;
    }
    public ArrayList<User> getUserOfEvent(String event) {
        ArrayList<User> userList = new ArrayList<>();
        HashMap<String, UserList> joinEventMap = new JoinEventMap().readData();

        if (joinEventMap.containsKey(event)){
            userList.addAll(joinEventMap.get(event).getUsers());
        }
        return userList;
    }



    public ArrayList<User> getUsers() {
        return users;
    }

    public void sort(){
        Collections.sort(users);
    }

    public void sort(Comparator<User> cmp){
        users.sort(cmp);
    }

    public void reverse(){
        Collections.reverse(users);
    }
}
