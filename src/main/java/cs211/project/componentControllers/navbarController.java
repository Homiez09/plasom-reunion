package cs211.project.componentControllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class navbarController {
    @FXML private ImageView profileImageView;
    @FXML private Pane toggleProfilePane;

    @FXML private void initialize() {
        toggleProfilePane.setVisible(false);
        showProfile();
    }

    private void showProfile() {
        Image profile = new Image(getClass().getResource("/images/profiles-dev/ming.jpg").toString(), 1280, 1280, false, false);
        profileImageView.setImage(profile);
        profileImageView.setFitWidth(58);
        profileImageView.setFitHeight(58);
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
}
