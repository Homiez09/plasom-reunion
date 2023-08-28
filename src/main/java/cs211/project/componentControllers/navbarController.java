package cs211.project.componentControllers;

import cs211.project.models.User;
import cs211.project.services.CreateProfileCircle;
import cs211.project.services.FXRouter;

import javafx.event.ActionEvent;
import javafx.event.EventDispatcher;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;

public class navbarController {
    @FXML private ImageView profileImageView;
    @FXML private Pane toggleProfilePane;
    @FXML private AnchorPane mainNavbarAnchorPane;

    private User user = (User) FXRouter.getData();
    @FXML private void initialize() {
        toggleProfilePane.setVisible(false);
        showProfile();
    }

    private void showProfile() {
        String path = (user != null) ? user.getImagePath() : "/images/profile/sign-in/sign-in-avatar.png";
        profileImageView.setImage(new Image(getClass().getResource(path).toString(), 1280, 1280, false, false));
        new CreateProfileCircle(profileImageView, 28);
    }

    @FXML protected void onToggleProfileMenuClick() {
        if (toggleProfilePane.isVisible()) {
            toggleProfilePane.setVisible(false);
        } else {
            toggleProfilePane.setVisible(true);
        }
    }

    @FXML protected void onProfileButtonClick() throws IOException {
        FXRouter.goTo("user-profile", user);
    }
    @FXML protected void onSettingButtonClick() throws IOException {
        FXRouter.goTo("setting", user);
    }

    @FXML protected void onHomeButtonClick() throws IOException {
        FXRouter.goTo("home", user);
    }

    @FXML protected void onLogOutButtonClick() throws IOException {
        FXRouter.goTo("welcome");
    }

    @FXML protected void onAdminButtonClick() throws IOException {
        FXRouter.goTo("admin-dashboard");
    }

    @FXML public void onMyEventsButton() throws IOException {
        FXRouter.goTo("my-event", user);
    }
}