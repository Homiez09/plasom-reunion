package cs211.project.controllers.admin;

import cs211.project.models.User;
import cs211.project.models.collections.UserList;
import cs211.project.services.*;
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

    private UserList userList;

    @FXML private void initialize() {
        UserListDataSource datasource = new UserListDataSource("data","user-list.csv");
        userList = datasource.readData();

        profileImageView.setImage(new Image(getClass().getResourceAsStream("/images/profile/default-avatar/default0.png"), 1280, 1280, false, false));
        new CreateProfileCircle(profileImageView, 28);
        new BlockArrowKeyFromTabPane(mainTab);

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

        TableColumn<User, ImageView> profileCol = new TableColumn<>("Profile");
        profileCol.setCellValueFactory(new PropertyValueFactory<>("avatar"));

        TableColumn<User, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<User, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("displayName"));

        TableColumn<User, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<User, String> lastLoginCol = new TableColumn<>("Last Login");
        lastLoginCol.setCellValueFactory(new PropertyValueFactory<>("lastedLogin"));

        userTableView.getColumns().clear();
        userTableView.getColumns().addAll(idCol, profileCol, usernameCol, nameCol, statusCol, lastLoginCol);
        userTableView.getItems().clear();
        userTableView.getItems().addAll(userList.getUsers());

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

}
