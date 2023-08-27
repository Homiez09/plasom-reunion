package cs211.project.models.collections;

import cs211.project.models.Event;
import cs211.project.models.User;

import java.util.ArrayList;

public class UserList {
    private ArrayList<User> users;

    public UserList() {
        users = new ArrayList<>();
    }

    public void addUser(String displayName, String username, String password){
        displayName = displayName.trim();
        username = username.trim();
        password = password.trim();
        if(!username.equals("") && !password.equals("")){
            users.add(new User(displayName,username,password));
        }
    }

    public User findUsername(String username) {
        for (User user: users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

}
