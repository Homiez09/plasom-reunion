package cs211.project.controllers.admin;

import cs211.project.models.User;
import cs211.project.models.collections.UserList;
import cs211.project.services.*;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class AdminDashboardController {
    @FXML private TabPane mainTab;
    @FXML private Tab menu1Tab;
    @FXML private Button menu1;
    @FXML private TableView userTableView;
    @FXML private ImageView profileImageView;
    @FXML private ProgressBar eventProgressBar;
    @FXML private Label onlineLabel, offlineLabel, eventLabel;

    private User user = (User) FXRouter.getData();
    UserListDataSource datasource = new UserListDataSource("data","user-list.csv");
    private UserList userList;

    @FXML private void initialize() {
        userList = datasource.readData();

        new CreateProfileCircle(profileImageView, 28);
        new BlockArrowKeyFromTabPane(mainTab);

        showProfile();
        showOnlineUserLabel();
        showOfflineUserLabel();
        showUserTable();
        ButtonSelectGraphic(1);
    }

    @FXML protected void onMenuOneClick() {
        mainTab.getSelectionModel().select(menu1Tab);
        ButtonSelectGraphic(1);
    }

    @FXML protected void onLogoutButtonClick() {
        try {
            FXRouter.goTo("welcome");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void showUserTable() {
        TableColumn<User, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        TableColumn<User, String> profileCol = new TableColumn<>("Profile");
        profileCol.setCellValueFactory(new PropertyValueFactory<>("imagePath"));

        profileCol.setCellFactory(column -> {
            return new TableCell<User, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        ImageView avatar = new ImageView(new Image(getClass().getResourceAsStream(item)));
                        avatar.setFitWidth(35);
                        avatar.setFitHeight(35);
                        new CreateProfileCircle(profileImageView, 28);
                        setGraphic(avatar);
                    }
                }
            };
        });

        TableColumn<User, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<User, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("displayName"));

        TableColumn<User, Boolean> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        statusCol.setCellFactory(column -> {
            return new TableCell<User, Boolean>() {
                @Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item ? "Online" : "Offline");
                    }
                }
            };
        });


        TableColumn<User, String> lastLoginCol = new TableColumn<>("Last Login");
        lastLoginCol.setCellValueFactory(new PropertyValueFactory<>("lastedLogin"));

        userTableView.getColumns().clear();
        userTableView.getColumns().addAll(idCol, profileCol, usernameCol, nameCol, statusCol, lastLoginCol);
        userTableView.getItems().clear();
        userTableView.getItems().addAll(userList.getNotAdminUsers());
        userTableView.getSortOrder().add(lastLoginCol);

    }

    private void showOnlineUserLabel() {
        int online = userList.getOnlineUsers().size();
        onlineLabel.setText(String.valueOf(online));
    }

    private void showOfflineUserLabel() {
        int offline = userList.getNotAdminUsers().size() - userList.getOnlineUsers().size();
        offlineLabel.setText(String.valueOf(offline));
    }

    private void showEventProgressBarAndEventLabel() {

    }

    private void ButtonSelectGraphic(int page) { // Change button graphic when selected
        ResetSelectGraphic();
        switch(page) {
            case 1:
                menu1.setStyle("-fx-background-color: #FFE4B8");
                break;
            default:
                ResetSelectGraphic();
                menu1.setStyle("-fx-background-color: #FFE4B8");
                break;
        }
    }
    private void ResetSelectGraphic() { // Reset all button to default
        menu1.setStyle("-fx-background-color: transparent");
    }

    private void showProfile() {
        String path = (user != null) ? user.getImagePath() : "/images/profile/default-avatar/default0.png";
        profileImageView.setImage(new Image(getClass().getResourceAsStream(path), 1280, 1280, false, false));
        new CreateProfileCircle(profileImageView, 28);
    }
}
