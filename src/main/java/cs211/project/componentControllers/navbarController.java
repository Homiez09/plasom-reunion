package cs211.project.componentControllers;

import cs211.project.models.User;
import cs211.project.services.CreateProfileCircle;
import cs211.project.services.FXRouter;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class navbarController {
    @FXML private ImageView profileImageView;
    @FXML private ComboBox<String> toggleComboBox;

    private User user = (User) FXRouter.getData();
    @FXML private void initialize() {
        String menu[] = {"profile", "setting", "logout"};

        toggleComboBox.getItems().addAll(menu);
        toggleComboBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            try {
                goTo(newValue);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        showProfile();
    }

    private void goTo(String page) throws IOException {
        switch(page) {
            case "profile":
                FXRouter.goTo("user-profile", user);
                break;
            case "setting":
                FXRouter.goTo("setting", user);
                break;
            case "logout":
                FXRouter.goTo("welcome");
                break;
        }
    }

    @FXML protected void onHomeButtonClick() throws IOException {
        FXRouter.goTo("home", user);
    }

    @FXML protected void onAdminButtonClick() throws IOException {
        FXRouter.goTo("admin-dashboard");
    }

    @FXML public void onMyEventsButton() throws IOException {
        FXRouter.goTo("my-event", user);
    }

    @FXML protected void onToggleProfileMenuClick() {
        toggleComboBox.show();
    }

    private void showProfile() {
        String path = (user != null) ? user.getImagePath() : "/images/profile/sign-in/sign-in-avatar.png";
        profileImageView.setImage(new Image(getClass().getResourceAsStream(path), 1280, 1280, false, false));
        new CreateProfileCircle(profileImageView, 28);
    }
}