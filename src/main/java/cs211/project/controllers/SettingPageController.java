package cs211.project.controllers;

import cs211.project.models.User;
import cs211.project.services.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.awt.Desktop;

public class SettingPageController {
    @FXML
    AnchorPane navbarAnchorPane;
    @FXML
    Pane mainPane,loginPane,themePane,privacyPane,contactPane;
    @FXML
    ImageView offImageView,onImageView,switchImageView;
    @FXML
    PasswordField newPasswordField,renewPasswordField,oldPasswordField;
    @FXML
    ToggleButton toggleButton;
    @FXML
    Hyperlink privalcyHyperlink;

    private User user = (User) FXRouter.getData();

    // top-bar
    private void loadTopBarComponent() {
        FXMLLoader topBarComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/navbar.fxml"));
        try {
            AnchorPane topBarComponent = topBarComponentLoader.load();
            navbarAnchorPane.getChildren().add(topBarComponent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize(){
        hidePane();
        loadTopBarComponent();
    }

    public void hidePane(){
        mainPane.setVisible(true);
        loginPane.setVisible(false);
        themePane.setVisible(false);
        privacyPane.setVisible(false);
        contactPane.setVisible(false);
    }


    // login page
    public void onLoginButton(ActionEvent actionEvent) {
        hidePane();
        mainPane.setVisible(false);
        loginPane.setVisible(true);

    }

    // interface page
    public void onThemeButton(ActionEvent actionEvent) {
        hidePane();
        mainPane.setVisible(false);
        themePane.setVisible(true);

    }

    public void onPrivacyButton(ActionEvent actionEvent) {
        hidePane();
        mainPane.setVisible(false);
        privacyPane.setVisible(true);
        privalcyHyperlink.setText("open");

    }

    @FXML
    private void openDocxFile(ActionEvent actionEvent) {
        File docxFile = new File("src/main/resources/data/Privacy.docx");
        try {
            Desktop.getDesktop().open(docxFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void onContactButton(ActionEvent actionEvent) {
        hidePane();
        mainPane.setVisible(false);
        contactPane.setVisible(true);

    }

    // toggle switch
    public void onToggleButton(ActionEvent actionEvent) {
        if (toggleButton.isSelected()) {
            ToggleOn();
        } else {
            ToggleOff();
        }
    }
    public void ToggleOn(){
        offImageView.setVisible(false);
        onImageView.setVisible(true);
        switchImageView.setLayoutX(30);
    }

    public void ToggleOff(){
        offImageView.setVisible(true);
        onImageView.setVisible(false);
        switchImageView.setLayoutX(0);
    }


    public void onLogoutButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("welcome");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
