package cs211.project.controllers;

import cs211.project.models.User;
import cs211.project.services.FXRouter;
import cs211.project.services.LoadNavbarComponent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SettingPageController {
    @FXML
    AnchorPane navbarAnchorPane,passwordAnchorPane;
    @FXML
    Pane mainPane,loginPane,themePane,privacyPane,contactPane;
    @FXML
    ImageView offImageView,onImageView,switchImageView;
    @FXML
    ToggleButton toggleButton;
    @FXML
    Hyperlink privalcyHyperlink;

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
        themePane.setVisible(false);
        privacyPane.setVisible(false);
        contactPane.setVisible(false);
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

    // login page
    @FXML
    private void onLoginButton() {
        hidePane();
        mainPane.setVisible(false);
        loginPane.setVisible(true);

    }

    // interface page
    @FXML
    private void onThemeButton() {
        hidePane();
        mainPane.setVisible(false);
        themePane.setVisible(true);

    }

    @FXML
    private void onPrivacyButton() {
        hidePane();
        mainPane.setVisible(false);
        privacyPane.setVisible(true);
        privalcyHyperlink.setText("open");

    }

    @FXML
    private void openDocxFile() {
        File docxFile = new File("src/main/resources/data/Privacy.docx");
        try {
            Desktop.getDesktop().open(docxFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void onContactButton() {
        hidePane();
        mainPane.setVisible(false);
        contactPane.setVisible(true);

    }

    // toggle switch
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
