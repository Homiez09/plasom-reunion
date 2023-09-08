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
        for (User exist: users) {
            if (exist.isDisplayName(displayName)) {
                return exist;
            }
        }
        return null;
    }

    public void addUser(String displayName, String username, String password, String imagePath){
        username = username.trim();
        password = password.trim();
        User exist = findUsername(username);
        if(exist == null){
            users.add(new User(displayName, username, password, imagePath));
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

    public ArrayList<User> getUsers() {
        return users;
    }
}
