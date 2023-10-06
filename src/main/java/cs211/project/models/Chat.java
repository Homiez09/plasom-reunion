package cs211.project.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Chat implements Comparable<Chat> {
    private String messageId; // random * unique
    private String message; // msg
    private User sender; // user id
    private String groupReceiver; // activity id
    private String timestamp; // random

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
        Random random = new Random();

        String id = "msg-";
        int randomInt = random.nextInt(1000000);

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder randomText = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            randomText.append(randomChar);
        }

        id = id + randomText + randomInt;

        return id;
    }

    public String getTime() {
        Date date = new Date(Long.parseLong(timestamp));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(date);
    }

    public String getMessageId() {
        return messageId;
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
