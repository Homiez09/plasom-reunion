package cs211.project.controllers.admin;

import cs211.project.models.User;
import cs211.project.models.collections.EventList;
import cs211.project.models.collections.UserList;
import cs211.project.services.*;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.text.DecimalFormat;

import java.io.IOException;

public class AdminDashboardController {
    @FXML private TabPane mainTab;
    @FXML private Tab menu1Tab, menu2Tab;
    @FXML private Button menu1, menu2;
    @FXML private TableView userTableView;
    @FXML private ProgressBar eventProgressBar;
    @FXML private Label onlineLabel, offlineLabel, eventLabel, percentLabel, totalLabel;
    @FXML private TableColumn<User, String> idTableCol, profileTableCol, usernameTableCol, nameTableCol, lastLoginTableCol;
    @FXML private TableColumn<User, Boolean> statusTableCol;
    @FXML private AnchorPane changePasswordAnchorPane;

    private User user = (User) FXRouter.getData();
    private UserListDataSource datasource = new UserListDataSource("data","user-list.csv");
    private UserList userList;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    @FXML private void initialize() {
        userList = datasource.readData();

        new BlockArrowKeyFromTabPane(mainTab);

        LoadChangePasswordComponent();
        showEventProgressBarAndEventLabel();
        showOnlineUserLabel();
        showOfflineUserLabel();
        showUserTable();
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

    @FXML protected void onLogoutButtonClick() {
        try {
            userList = datasource.readData();
            userList.logout(user);
            datasource.writeData(userList);
            FXRouter.goTo("welcome");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void LoadChangePasswordComponent() {
        FXMLLoader navbarComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/changepass.fxml"));
        try {
            AnchorPane changePassComponent = navbarComponentLoader.load();
            changePasswordAnchorPane.getChildren().add(changePassComponent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showUserTable() {
        idTableCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        profileTableCol.setCellValueFactory(new PropertyValueFactory<>("imagePath"));

        profileTableCol.setCellFactory(column -> {
            return new TableCell<User, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        ImagePathFormat pathFormat = new ImagePathFormat(item);
                        ImageView avatar = new ImageView(new Image(pathFormat.toString(), 1280, 1280, false, false));

                        avatar.setFitWidth(35);
                        avatar.setFitHeight(35);

                        avatar.setClip(CreateProfileCircle.getProfileCircle(avatar, 17));

                        setGraphic(avatar);
                    }
                }
            };
        });

        usernameTableCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        nameTableCol.setCellValueFactory(new PropertyValueFactory<>("displayName"));

        statusTableCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        statusTableCol.setCellFactory(column -> {
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

        lastLoginTableCol.setCellValueFactory(new PropertyValueFactory<>("lastedLogin"));

        userTableView.getColumns().clear();
        userTableView.getColumns().addAll(idTableCol, profileTableCol, usernameTableCol, nameTableCol, statusTableCol, lastLoginTableCol);
        userTableView.getItems().clear();
        userTableView.getItems().addAll(userList.getNotAdminUsers());
        userTableView.getSortOrder().add(lastLoginTableCol);
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
        EventList eventList = new EventListDataSource().readData();
        int sizeTotalEvent = eventList.getSizeTotalEvent();
        int sizeCompletedEvent = eventList.getSizeCompletedEvent();
        double percent = (double) sizeCompletedEvent / sizeTotalEvent * 100;
        eventLabel.setText(String.valueOf(sizeCompletedEvent));
        totalLabel.setText("Total: " + String.valueOf(sizeTotalEvent));
        eventProgressBar.setProgress(percent/100);
        percentLabel.setText(String.valueOf(df.format(percent)) + "%");
    }

    private void ButtonSelectGraphic(int page) { // Change button graphic when selected
        ResetSelectGraphic();
        String style = "-fx-background-color: #FFE4B8";
        switch(page) {
            case 1:
                menu1.setStyle(style);
                break;
            case 2:
                menu2.setStyle(style);
                break;
            default:
                break;
        }
    }
    private void ResetSelectGraphic() { // Reset all button to default
        String style = "";
        menu1.setStyle(style);
        menu2.setStyle(style);
    }
}
