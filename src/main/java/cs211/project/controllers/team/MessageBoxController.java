package cs211.project.controllers.team;

import cs211.project.models.Chat;
import cs211.project.models.User;
import cs211.project.services.CreateProfileCircle;
import cs211.project.services.ImagePathFormat;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class MessageBoxController {
    @FXML private Text messageText, messageText2, messageText3;
    @FXML protected Label nameLabel, timeLabel, timeLabel2, timeLabel3;
    @FXML protected ImageView profileImageView;
    @FXML protected AnchorPane otherAnchorPane, midAnchorPane, selfAnchorPane;
    private User user;

    protected void setChat(Chat chat) {
        user = chat.getSender();
        nameLabel.setText(user.getDisplayName());
        setMessageText(chat.getMessage());
        setTime(chat.getTime());

        ImagePathFormat path = new ImagePathFormat(user.getImagePath());
        profileImageView.setImage(new Image(path.toString(), 1280, 1280, false, false));
        new CreateProfileCircle(profileImageView, 22);
    }

    protected void showMessage(int select) {
        switch (select) {
            case 0 -> otherAnchorPane.setVisible(true);
            case 1 -> midAnchorPane.setVisible(true);
            case 2 -> selfAnchorPane.setVisible(true);
        }
    }

    private void setMessageText(String message) {
        messageText.setText(message);
        messageText2.setText(message);
        messageText3.setText(message);
    }

    private void setTime(String time) {
        String timeFormat = time.split(" ")[1];
        timeLabel.setText(timeFormat);
        timeLabel2.setText(timeFormat);
        timeLabel3.setText(timeFormat);
    }
}
