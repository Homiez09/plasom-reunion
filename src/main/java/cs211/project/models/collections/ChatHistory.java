package cs211.project.models.collections;

import cs211.project.models.Chat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ChatHistory {
    private ArrayList<Chat> chats;

    public ChatHistory() {
        chats = new ArrayList<>();
    }

    public ChatHistory(ArrayList<Chat> chats) {
        this.chats = chats;
    }

    public ChatHistory(ChatHistory chatHistory) {
        chats = new ArrayList<>();
        for (Chat chat: chatHistory.getChats()) {
            chats.add(chat);
        }
    }

    public void add(Chat chat) {
        chats.add(chat);
    }

    public void remove(Chat chat) {
        chats.remove(chat);
    }

    public void sort(){
        Collections.sort(chats);
    }

    public void sort(Comparator<Chat> cmp){
        Collections.sort(chats, cmp);
    }

    public void reverse(Comparator<Chat> cmp){
        Collections.reverse(chats);
    }

    public ArrayList<Chat> getChatByActivityId(String activityId) {
        ArrayList<Chat> chats = new ArrayList<>();
        for (Chat chat: this.chats) {
            if (chat.getGroupReceiver().equals(activityId)) {
                chats.add(chat);
            }
        }
        return chats;
    }

    public ArrayList<Chat> getChats() {
        return chats;
    }
}
