package cs211.project.services;

import cs211.project.models.Chat;
import cs211.project.models.collections.ChatHistory;

public class UploadMessageThread implements Runnable {
    private final ChatHistoryDataSource chatHistoryDataSource = new ChatHistoryDataSource("data", "chat-history.csv");
    private final ChatHistory chatHistory = chatHistoryDataSource.readData();

    @Override
    public void run() {}

    public void uploadMessage(Chat chat) {
        chatHistory.add(chat);
        chatHistoryDataSource.writeData(chatHistory);
    }
}
