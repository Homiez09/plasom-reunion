package cs211.project.componentControllers;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class navbarController {
    @FXML private ImageView profileImageView;
    @FXML private Pane toggleProfilePane;

    @FXML private void initialize() {
        toggleProfilePane.setVisible(false);
        showProfile();
    }

    private void showProfile() {
        Image profile = new Image(getClass().getResource("/images/profile/develop/ming.jpg").toString(), 1280, 1280, true, false);
        profileImageView.setImage(profile);
        profileImageView.setFitWidth(58);
        profileImageView.setClip(getProfileCircle(profileImageView));
    }

    private Circle getProfileCircle(ImageView profileImageView) {
        Circle circle = new Circle(28);
        circle.setCenterX(profileImageView.getFitWidth() / 2);
        circle.setCenterY(profileImageView.getFitHeight() / 2);
        return circle;
    }

    @FXML protected void onToggleProfileMenuClick() {
        if (toggleProfilePane.isVisible()) {
            toggleProfilePane.setVisible(false);
        } else {
            toggleProfilePane.setVisible(true);
        }
    }
    @FXML protected void onProfileButtonClick() throws IOException {
        FXRouter.goTo("user-profile");
    }
    @FXML protected void onSettingButtonClick() throws IOException {
        FXRouter.goTo("setting");
    }

    @FXML protected void onHomeButtonClick() throws IOException {
        FXRouter.goTo("home");
    }

    @FXML protected void onWelcomeButtonClick() throws IOException {
        FXRouter.goTo("welcome");
    }

    @FXML protected void onAdminButtonClick() throws IOException {
        FXRouter.goTo("admin-dashboard");
    }
}
