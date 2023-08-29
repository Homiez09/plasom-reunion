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
        for (User user: users) {
            if (user.isUserName(username)) {
                return user;
            }
        }
        return null;
    }

    public void addUser(String displayName, String username, String password){
        username = username.trim();
        password = password.trim();
        User exist = findUsername(username);
        if(exist == null){
            users.add(new User(displayName, username, password));
        }
    }

    public User login(String username, String password){
        for(User user: users){
            if(user.getUsername().equals(username) && user.validatePassword(password)){
                return user;
            }
        }
        return null;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
