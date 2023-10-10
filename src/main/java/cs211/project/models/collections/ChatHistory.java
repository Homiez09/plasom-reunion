package cs211.project.models.collections;

import cs211.project.models.Chat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ChatHistory {
    private final ArrayList<Chat> chats;

    public ChatHistory() {
        chats = new ArrayList<>();
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
        chats.sort(cmp);
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
