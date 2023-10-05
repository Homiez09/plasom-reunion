package cs211.project.services;

import cs211.project.models.Chat;
import cs211.project.models.User;
import cs211.project.models.collections.ChatHistory;
import cs211.project.models.collections.UserList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ChatHistoryDataSourceTest {

    @Test
    void readData() {
        ChatHistoryDataSource chatHistoryDataSource = new ChatHistoryDataSource("data","chat-history.csv");
        ChatHistory chatHistory = chatHistoryDataSource.readData();
        ArrayList<Chat> chatHistoryActivity = chatHistory.getChatByActivityId("activity-abc121");
        chatHistoryActivity.sort(new TimestampChatComparator());
        for (Chat chat: chatHistoryActivity) {
            System.out.println(chat.getMessage() + " " + chat.getTime());
        }
    }

    @Test
    void writeData() {
        ChatHistoryDataSource chatHistoryDataSource = new ChatHistoryDataSource("data","chat-history.csv");
        ChatHistory chatHistory = chatHistoryDataSource.readData();
        UserListDataSource userListDataSource = new UserListDataSource("data","user-list.csv");
        UserList userList = userListDataSource.readData();
        User user = userList.findUsername("HomieZ09");
        chatHistory.add(new Chat("fsgsfdgfgdfgdfds",user,"activity-abc121"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        chatHistory.add(new Chat("fdsgfdsgfddfgdfgfdsgsdg",user,"activity-abc121"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        chatHistory.add(new Chat("sdfgsfdgdfgdfgdfgs",user,"activity-abc121"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        chatHistory.add(new Chat("sgfdsgdfgdfgdffg",user,"activity-abc121"));
        chatHistoryDataSource.writeData(chatHistory);
    }
}