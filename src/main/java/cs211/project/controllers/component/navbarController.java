package cs211.project.controllers.component;

import cs211.project.models.User;
import cs211.project.models.collections.UserList;
import cs211.project.services.CreateProfileCircle;
import cs211.project.services.FXRouter;

import cs211.project.services.ImagePathFormat;
import cs211.project.services.UserListDataSource;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class navbarController {
    @FXML private ImageView profileImageView;
    @FXML private ComboBox<String> toggleComboBox;
    private final User user = (User) FXRouter.getData();
    private final UserListDataSource datasource = new UserListDataSource("data","user-list.csv");

    @FXML private void initialize() {
        String[] menu = {"profile", "setting", "logout"};

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
            case "profile" -> FXRouter.goTo("user-profile", user);
            case "setting" -> FXRouter.goTo("setting", user);
            case "logout" -> {
                UserList userList = datasource.readData();
                userList.logout(user);
                datasource.writeData(userList);

                FXRouter.goTo("welcome");
            }
        }
    }

    @FXML private void onHomeButtonClick() throws IOException {
        FXRouter.goTo("home", user);
    }

    @FXML private void onEventsButton() throws IOException {
        FXRouter.goTo("my-event", user);
    }

    @FXML private void onToggleProfileMenuClick() {
        toggleComboBox.show();
    }

    private void showProfile() {
        ImagePathFormat path = new ImagePathFormat(user.getImagePath());
        profileImageView.setImage(new Image(path.toString(), 1280, 1280, false, false));
        new CreateProfileCircle(profileImageView, 28);
    }
}