package cs211.project.controllers.component;

import cs211.project.models.User;
import cs211.project.services.CreateProfileCircle;
import cs211.project.services.ImagePathFormat;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class UserCardProfileController {
    @FXML private ImageView avatarProfileImageView, contactIconProfileImageView,  frameIconImageView, ellipseIconImageView;
    @FXML private Label bioProfileLabel, displayNameProfileLabel, idProfileLabel, usernameProfileLabel, contactProfileLabel;
    @FXML private AnchorPane hoverShowContactAnchorPane;
    private Image contactIcon, ellipseIcon, frameIcon;

    @FXML private void initialize(){
        loadIconInit();
        hoverShowContactAnchorPane.setVisible(false);
    }

    public void setUser(User user) {
        ImagePathFormat path = new ImagePathFormat(user.getImagePath());
        avatarProfileImageView.setImage(new Image(path.toString(), 1280, 1280, false, false));
        new CreateProfileCircle(avatarProfileImageView, 70);

        displayNameProfileLabel.setText(user.getDisplayName());
        bioProfileLabel.setText(user.getBio());
        idProfileLabel.setText(user.getUserId());
        usernameProfileLabel.setText(user.getUsername());

        if (user.isShowContact()) {
            contactProfileLabel.setText(user.getContactNumber());
            hoverShowContactAnchorPane.setVisible(true);
        }else{
            contactProfileLabel.setText("xxxxxxxxxx");
            hoverShowContactAnchorPane.setVisible(false);
        }
    }


    @FXML void onContactEntered() {
        hoverShowContactAnchorPane.setVisible(true);
    }
    @FXML void onContactExited() {
        hoverShowContactAnchorPane.setVisible(false);
    }


    private void loadIconInit(){
        contactIcon = new Image(getClass().getResourceAsStream("/images/icons/user-profile/iconProfile.png"));
        ellipseIcon = new Image(getClass().getResourceAsStream("/images/icons/user-profile/iconEllipse.png"));
        frameIcon = new Image(getClass().getResourceAsStream("/images/icons/user-profile/iconFrame.png"));

        frameIconImageView.setImage(frameIcon);
        contactIconProfileImageView.setImage(contactIcon);
        ellipseIconImageView.setImage(ellipseIcon);
    }
}
