package cs211.project.controllers.view;

import cs211.project.models.User;
import cs211.project.services.FXRouter;
import cs211.project.services.LoadNavbarComponent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class SettingPageController {
    @FXML AnchorPane navbarAnchorPane,passwordAnchorPane;
    @FXML Pane mainPane,loginPane;
    @FXML ImageView offImageView,onImageView,switchImageView;
    @FXML ToggleButton toggleButton;
    private final User user = (User) FXRouter.getData();

    @FXML
    private void initialize(){
        hidePane();
        loginPane.setVisible(true);
        new LoadNavbarComponent(user, navbarAnchorPane);
        LoadChangePassword(passwordAnchorPane);

    }

    private void hidePane(){
        loginPane.setVisible(false);
    }

    private void LoadChangePassword(AnchorPane passwordAnchorPane) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/change-password.fxml"));
        try {
            AnchorPane load = fxmlLoader.load();
            passwordAnchorPane.getChildren().add(load);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onLoginButton() {
        hidePane();
        mainPane.setVisible(false);
        loginPane.setVisible(true);

    }

    @FXML
    private void onToggleButton() {
        if (toggleButton.isSelected()) {
            ToggleOn();
        } else {
            ToggleOff();
        }
    }

    @FXML
    private void ToggleOn(){
        offImageView.setVisible(false);
        onImageView.setVisible(true);
        switchImageView.setLayoutX(30);
    }

    @FXML
    private void ToggleOff(){
        offImageView.setVisible(true);
        onImageView.setVisible(false);
        switchImageView.setLayoutX(0);
    }


    @FXML
    private void onLogoutButton() {
        try {
            FXRouter.goTo("welcome");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
