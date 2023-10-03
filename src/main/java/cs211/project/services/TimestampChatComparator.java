package cs211.project.services;

import cs211.project.models.Chat;

import java.util.Comparator;

public class TimestampChatComparator implements Comparator<Chat> {
    @Override
    public int compare(Chat chat1, Chat chat2) {
        return chat1.getTimestamp().compareTo(chat2.getTimestamp());
    }
}
