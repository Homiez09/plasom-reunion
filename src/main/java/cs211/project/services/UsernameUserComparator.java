package cs211.project.services;

import cs211.project.models.User;

import java.util.Comparator;

public class UsernameUserComparator implements Comparator<User> {
    @Override
    public int compare(User user1, User user2) {
        return user1.getUsername().compareTo(user2.getUsername());
    }
}