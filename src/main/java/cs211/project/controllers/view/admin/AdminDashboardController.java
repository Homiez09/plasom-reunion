package cs211.project.controllers.view.admin;

import cs211.project.models.User;
import cs211.project.models.collections.EventList;
import cs211.project.models.collections.UserList;
import cs211.project.services.*;
import cs211.project.services.team.LoadUserCardProfileComponent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.text.DecimalFormat;

import java.io.IOException;

public class AdminDashboardController {
    @FXML private TabPane mainTab;
    @FXML private Tab menu1Tab, menu2Tab;
    @FXML private TableView userTableView;
    @FXML private ProgressBar eventProgressBar;
    @FXML private Label onlineLabel, offlineLabel, eventLabel, percentLabel, totalLabel;
    @FXML private TableColumn<User, String> idTableCol, profileTableCol, usernameTableCol, nameTableCol, lastLoginTableCol;
    @FXML private TableColumn<User, Boolean> statusTableCol;
    @FXML private AnchorPane changePasswordAnchorPane, userCardProfileAnchorPane, adminDashBoardAnchorPane, hoverAdminDashBoardAnchorPane;
    @FXML private ImageView logoutImageView, offlineImageView, onlineImageView, menu1ImageView, menu2ImageView;
    @FXML private Circle hoverMenu1Shape, hoverMenu2Shape;
    @FXML private ProgressIndicator offlineIndicator, onlineIndicator;
    private Image menu1Icon, menu2Icon, hoverMenu1Icon, hoverMenu2Icon;
    private final User user = (User) FXRouter.getData();
    private final UserListDataSource datasource = new UserListDataSource("data","user-list.csv");
    private final DecimalFormat df = new DecimalFormat("0.00");
    private UserList userList;


    @FXML private void initialize() {
        userList = datasource.readData();

        new BlockArrowKeyFromTabPane(mainTab);

        LoadChangePasswordComponent();
        showEventProgressBarAndEventLabel();
        showOnlineUserLabel();
        showOfflineUserLabel();
        showUserTable();

        loadIconInit();
        userProfileView();
    }

    @FXML private void onAdminDashBoardDisableClick(){
        userCardProfileAnchorPane.setVisible(false);
        adminDashBoardAnchorPane.setDisable(false);
        adminDashBoardAnchorPane.setEffect(null);
        hoverAdminDashBoardAnchorPane.setVisible(false);
    }

    @FXML protected void onLogoutClick() {
        try {
            userList = datasource.readData();
            userList.logout(user);
            datasource.writeData(userList);
            FXRouter.goTo("welcome");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML protected void onMenuOneClick() {
        mainTab.getSelectionModel().select(menu1Tab);
        checkHover();

    }

    @FXML protected void onMenuTwoClick() {
        mainTab.getSelectionModel().select(menu2Tab);
        checkHover();
    }

    @FXML void onMenuOneEntered() {
        if (!mainTab.getSelectionModel().getSelectedItem().equals(menu1Tab)) {
            menu1ImageView.setImage(hoverMenu1Icon);
            hoverMenu1Shape.setVisible(true);
        }else{
            menu2ImageView.setImage(menu2Icon);
            hoverMenu2Shape.setVisible(false);
        }
    }

    @FXML void onMenuOneExit() {
        if(mainTab.getSelectionModel().getSelectedItem().equals(menu2Tab)) {
            menu1ImageView.setImage(menu1Icon);
            hoverMenu1Shape.setVisible(false);
        }
    }

    @FXML void onMenuTwoEntered() {
        if (!mainTab.getSelectionModel().getSelectedItem().equals(menu2Tab)) {
            menu2ImageView.setImage(hoverMenu2Icon);
            hoverMenu2Shape.setVisible(true);
        }else{
            menu1ImageView.setImage(menu1Icon);
            hoverMenu1Shape.setVisible(false);
        }
    }

    @FXML void onMenuTwoExit() {
        if(mainTab.getSelectionModel().getSelectedItem().equals(menu1Tab)){
            menu2ImageView.setImage(menu2Icon);
            hoverMenu2Shape.setVisible(false);
        }
    }

    private void LoadChangePasswordComponent() {
        FXMLLoader navbarComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/change-password.fxml"));
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
        userTableView.getItems().clear();

        userTableView.getColumns().addAll(idTableCol, profileTableCol, usernameTableCol, nameTableCol, statusTableCol, lastLoginTableCol);
        userTableView.getItems().addAll(userList.getNotAdminUsers());

        userTableView.getSortOrder().add(lastLoginTableCol);
    }

    private void showOnlineUserLabel() {
        int online = userList.getOnlineUsers().size();
        onlineLabel.setText(String.valueOf(online));
        onlineIndicator.setProgress((double) online / userList.getNotAdminUsers().size());
    }

    private void showOfflineUserLabel() {
        int offline = userList.getNotAdminUsers().size() - userList.getOnlineUsers().size();
        offlineLabel.setText(String.valueOf(offline));
        offlineIndicator.setProgress((double) offline / userList.getNotAdminUsers().size());
    }

    private void showEventProgressBarAndEventLabel() {
        EventList eventList = new EventListDataSource().readData();
        int sizeTotalEvent = eventList.getSizeTotalEvent();
        int sizeCompletedEvent = eventList.getSizeCompletedEvent();
        double percent = (double) sizeCompletedEvent / sizeTotalEvent * 100;
        eventLabel.setText(String.valueOf(sizeCompletedEvent));
        totalLabel.setText(String.valueOf(sizeTotalEvent));
        eventProgressBar.setProgress(percent/100);
        percentLabel.setText(df.format(percent) + "%");
    }

    private void loadIconInit(){
        onlineImageView.setImage(new Image(getClass().getResourceAsStream("/images/icons/login/status/online_active.png")));
        offlineImageView.setImage(new Image(getClass().getResourceAsStream("/images/icons/login/status/offline_active.png")));
        logoutImageView.setImage(new Image(getClass().getResourceAsStream("/images/icons/admin-sidebar/logout.png")));

        menu1Icon = new Image(getClass().getResourceAsStream("/images/icons/admin-sidebar/user_log.png"));
        hoverMenu1Icon = new Image(getClass().getResourceAsStream("/images/icons/admin-sidebar/hover_user_log.png"));

        menu2Icon = new Image(getClass().getResourceAsStream("/images/icons/admin-sidebar/setting.png"));
        hoverMenu2Icon = new Image(getClass().getResourceAsStream("/images/icons/admin-sidebar/hover_setting.png"));

        menu1ImageView.setImage(hoverMenu1Icon);
        menu2ImageView.setImage(menu2Icon);

        hoverMenu1Shape.setVisible(true);
        checkHover();
    }

    private void checkHover(){
        if(mainTab.getSelectionModel().getSelectedItem().equals(menu1Tab)){
            menu2ImageView.setImage(menu2Icon);
            hoverMenu1Shape.setVisible(true);
            hoverMenu2Shape.setVisible(false);
        }else{
            menu1ImageView.setImage(menu1Icon);
            hoverMenu2Shape.setVisible(true);
            hoverMenu1Shape.setVisible(false);
        }
    }

    private void userProfileView(){
        userTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                User selectedUser = (User) newValue;
                userCardProfileAnchorPane.setVisible(true);
                adminDashBoardAnchorPane.setDisable(true);
                adminDashBoardAnchorPane.setEffect(new BoxBlur(6, 5, 2));
                hoverAdminDashBoardAnchorPane.setVisible(true);

                new LoadUserCardProfileComponent(userCardProfileAnchorPane, selectedUser);
            }
        });
    }
}
