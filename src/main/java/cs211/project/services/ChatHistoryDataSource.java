package cs211.project.services;

import cs211.project.models.Chat;
import cs211.project.models.User;
import cs211.project.models.collections.ChatHistory;
import cs211.project.models.collections.UserList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ChatHistoryDataSource implements Datasource<ChatHistory> {
    private final String directoryName;
    private final String fileName;

    public ChatHistoryDataSource(String directoryName, String fileName) {
        this.directoryName = directoryName;
        this.fileName = fileName;
        checkFileIsExisted();
    }

    private void checkFileIsExisted() {
        File file = new File(directoryName);
        if (!file.exists()) {
            file.mkdirs();
        }

        String filePath = directoryName + File.separator + fileName;
        file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public ChatHistory readData() {
        ChatHistory chatHistory = new ChatHistory();
        UserListDataSource userListDataSource = new UserListDataSource("data", "user-list.csv");
        UserList userList = userListDataSource.readData();

        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        InputStreamReader inputStreamReader = new InputStreamReader(
                fileInputStream,
                StandardCharsets.UTF_8
        );
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line = "";
        try {
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals("")) continue;
                String[] data = line.split(",");

                String messageID = data[0];
                String message = data[1];
                String sender = data[2];
                String groupReceiver = data[3];
                String timestamp = data[4];

                User user = userList.findUserId(sender);
                chatHistory.add(new Chat(messageID, message, user, groupReceiver, timestamp));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return chatHistory;
    }

    @Override
    public void writeData(ChatHistory data) {
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        // เตรียม object ที่ใช้ในการเขียนไฟล์
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                fileOutputStream,
                StandardCharsets.UTF_8
        );
        BufferedWriter buffer = new BufferedWriter(outputStreamWriter);

        try {
            for (Chat chat : data.getChats()) {
                String line =  chat.toString();

                buffer.append(line);
                buffer.append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                buffer.flush();
                buffer.close();
            }
            catch (IOException e){
                throw new RuntimeException(e);
            }
        }
    }
}
