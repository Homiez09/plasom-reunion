package cs211.project.models;

import cs211.project.services.GenerateRandomID;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Chat implements Comparable<Chat> {
    private String messageId;
    private final String message;
    private final User sender;
    private final String groupReceiver;
    private String timestamp;

    public Chat(String messageId, String message, User sender, String groupReceiver, String timestamp) {
        this.messageId = messageId;
        this.message = message;
        this.sender = sender;
        this.groupReceiver = groupReceiver;
        this.timestamp = timestamp;
    }

    public Chat(String message, User sender, String groupReceiver) {
        this("", message, sender, groupReceiver, "");
        this.messageId = generateChatID();
        this.timestamp = currentTimestamp();
    }

    private String currentTimestamp() {
        return String.valueOf(System.currentTimeMillis());
    }

    public String generateChatID() {
        String id = "msg-";
        id += new GenerateRandomID().getRandomText();
        return id;
    }

    public String getTime() {
        Date date = new Date(Long.parseLong(timestamp));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(date);
    }

    public String getMessage() {
        return message;
    }

    public User getSender() {
        return sender;
    }

    public String getGroupReceiver() {
        return groupReceiver;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public int compareTo(Chat chat) {
        return timestamp.compareTo(chat.getTimestamp());
    }

    @Override
    public String toString() {
        return messageId + ","
                + message + ","
                + sender.getUserId() + ","
                + groupReceiver + ","
                + timestamp;
    }
}
