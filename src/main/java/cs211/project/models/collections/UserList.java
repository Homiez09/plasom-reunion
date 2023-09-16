package cs211.project.models.collections;

import cs211.project.models.Event;
import cs211.project.models.User;

import java.util.ArrayList;

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
        System.out.println(displayName);
        for (User exist: users) {
            System.out.println();
            if (exist.isDisplayName(displayName)) {
                return exist;
            }
        }
        return null;
    }

    public void addUser(String userId, String displayName, String username, String password, String contactNumber, String registerDate, String lastedLogin, String imagePath, boolean status, boolean admin, boolean showContactNumber, String bio){
        username = username.trim();
        password = password.trim();
        User newUser = findUsername(username);
        if(newUser == null){
            users.add(new User(userId, displayName, username, password, contactNumber, registerDate, lastedLogin, imagePath, status, admin,showContactNumber, bio));
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


    public ArrayList<User> getUsers() {
        return users;
    }
}
