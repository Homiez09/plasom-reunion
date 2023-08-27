package cs211.project.controllers.admin;

import cs211.project.models.UserFake;
import cs211.project.services.BlockArrowKeyFromTabPane;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.ArrayList;

public class AdminDashboardController {
    @FXML private TabPane mainTab;
    @FXML private Tab menu1Tab, menu2Tab;
    @FXML private Button menu1, menu2, menu3, menu4;
    @FXML private TableView userTableView, adminTableView;

    @FXML private void initialize() {
        new BlockArrowKeyFromTabPane(mainTab);
        showUserTable();
        showAdminTable();
        ButtonSelectGraphic(1);
    }

    @FXML protected void onMenuOneClick() {
        mainTab.getSelectionModel().select(menu1Tab);
        ButtonSelectGraphic(1);
    }

    @FXML protected void onMenuTwoClick() {
        mainTab.getSelectionModel().select(menu2Tab);
        ButtonSelectGraphic(2);
    }

    @FXML protected void onMenuThreeClick() {
//        mainTab.getSelectionModel().select(menu3Tab);
        ButtonSelectGraphic(3);
    }

    @FXML protected void onMenuFourClick() {
//        mainTab.getSelectionModel().select(menu4Tab);
        ButtonSelectGraphic(4);
    }

    @FXML protected void onLogoutButtonClick() {
        try {
            FXRouter.goTo("welcome");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void CreateTableColumn(TableView tableView) {
        TableColumn<UserFake, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<UserFake, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<UserFake, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<UserFake, String> roleCol = new TableColumn<>("Role");
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));

        TableColumn<UserFake, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<UserFake, String> lastLoginCol = new TableColumn<>("Last Login");
        lastLoginCol.setCellValueFactory(new PropertyValueFactory<>("lastLogin"));

        tableView.getColumns().clear();
        tableView.getColumns().addAll(idCol, usernameCol, nameCol, roleCol, statusCol, lastLoginCol);
    }

    private void CreateTableItem(TableView tableView, ArrayList<UserFake> users) {
        tableView.getItems().clear();
        tableView.getItems().addAll(users);
    }
    void showUserTable() {
        // todo : get data from datasource
        ArrayList<UserFake> users = new ArrayList<>();

        users.add(new UserFake("1", "admin", "User", "User", "Offline", "2020-01-01"));
        users.add(new UserFake("2", "user", "User", "User", "Online", "2020-01-01"));
        users.add(new UserFake("3", "user2", "User2", "User", "Offline", "2020-01-01"));
        users.add(new UserFake("4", "user3", "User3", "User", "Offline", "2020-01-01"));

        CreateTableColumn(userTableView);
        CreateTableItem(userTableView, users);
    }

    void showAdminTable() {
        // todo : get data from datasource
        ArrayList<UserFake> users = new ArrayList<>();

        users.add(new UserFake("1", "admin", "User", "Admin", "Online", "2020-01-01"));
        users.add(new UserFake("2", "user", "User", "Admin", "Offline", "2020-01-01"));
        users.add(new UserFake("3", "user2", "User2", "Admin", "Offline", "2020-01-01"));

        CreateTableColumn(adminTableView);
        CreateTableItem(adminTableView, users);

    }

    void ButtonSelectGraphic(int page) { // Change button graphic when selected
        ResetSelectGraphic();
        switch(page) {
            case 1:
                menu1.setStyle("-fx-background-color: #FFE4B8");
                break;
            case 2:
                menu2.setStyle("-fx-background-color: #FFE4B8");
                break;
            case 3:
                menu3.setStyle("-fx-background-color: #FFE4B8");
                break;
            case 4:
                menu4.setStyle("-fx-background-color: #FFE4B8");
                break;
            default:
                ResetSelectGraphic();
                menu1.setStyle("-fx-background-color: #FFE4B8");
                break;
        }
    }
    void ResetSelectGraphic() { // Reset all button to default
        menu1.setStyle("-fx-background-color: transparent");
        menu2.setStyle("-fx-background-color: transparent");
        menu3.setStyle("-fx-background-color: transparent");
        menu4.setStyle("-fx-background-color: transparent");
    }

}
