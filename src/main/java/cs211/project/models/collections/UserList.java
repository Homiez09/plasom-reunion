package cs211.project.models.collections;

import cs211.project.models.Event;
import cs211.project.models.Team;
import cs211.project.models.User;
import cs211.project.services.JoinEventMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class UserList {
    private ArrayList<User> users;

    public UserList() {
        users = new ArrayList<>();
    }

    public User findUsername(String username) {
        for (User exist: users) {
            if (exist.isUserName(username)) {
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


    public User login(String username, String password){
        for(User exist: users){
            if(exist.getUsername().equals(username) && exist.validatePassword(password)){
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

    public HashMap<String, User> userHashMap() {
        HashMap<String, User> userHashMapTemp= new HashMap<>();
        for (User user: users) {
            userHashMapTemp.put(user.getUsername(), user);
        }

        return userHashMapTemp;
    }
    public ArrayList<User> getUserOfEvent(Event event) {
        String eventID = event.getEventID();
        ArrayList<User> userList = new ArrayList<>();
        HashMap<String, Set<String>> joinEventMap = new JoinEventMap().readData();
        Set<String> setUser = joinEventMap.get(eventID);

        if (setUser != null) {
            for (String userID : setUser) {
                if (joinEventMap.containsKey(eventID)){
                    userList.add(findUserId(userID));
                }
            }
        }

        return userList;
    }


    public ArrayList<User> getUsers() {
        return users;
    }
}
