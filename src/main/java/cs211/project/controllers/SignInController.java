package cs211.project.controllers;

import cs211.project.models.User;
import cs211.project.models.collections.EventList;
import cs211.project.models.collections.UserList;
import cs211.project.services.EventListDataSource;
import cs211.project.services.FXRouter;
import cs211.project.services.LoadCardEventUpcomingForAuth;
import cs211.project.services.UserListDataSource;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SignInController {
    private final int maxPasswordLimit = 27, maxUsernameLimit = 20;

    @FXML private PasswordField passwordField;
    @FXML private TextField showPasswordTextField, usernameTextField;

    @FXML private ImageView signBackgroundImageView, upComingEventsBackgroundImageView;
    @FXML private ImageView usernameIconView, passwordIconView, visiblePasswordImageView, profileImageView;
    @FXML private Label errorLabel;
    @FXML private AnchorPane upcomingZoneAnchorPane;

    private Image showPasswordImage, hidePasswordImage;
    private String password, username;
    private UserListDataSource datasource;
    private UserList userList;
    protected User matchingUsername,user;

    @FXML
    void initialize() {
        datasource = new UserListDataSource("data", "user-list.csv");
        userList = datasource.readData();

        eventHandleEnter();
        loadImage();

        new LoadCardEventUpcomingForAuth(upcomingZoneAnchorPane);

        maximumLengthField();

        showPasswordTextField.setVisible(false);
        visiblePasswordImageView.setImage(hidePasswordImage);

        errorLabel.setVisible(false);

    }

    private void eventHandleEnter(){
        EventHandler<KeyEvent> enterEventHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    onLoginButton();
                }
            }
        };
        usernameTextField.setOnKeyPressed(enterEventHandler);
        passwordField.setOnKeyPressed(enterEventHandler);
        showPasswordTextField.setOnKeyPressed(enterEventHandler);
    }
    private void maximumLengthField() {
        usernameTextField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue.length() > maxUsernameLimit) {
                usernameTextField.setText(oldValue);
            }
        }));

        passwordField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue.length() > maxPasswordLimit) {
                passwordField.setText(oldValue);
            }
        }));

        showPasswordTextField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue.length() > maxPasswordLimit) {
                showPasswordTextField.setText(oldValue);
            }
        }));

    }

    @FXML private void onLoginButton() {
        username = usernameTextField.getText();
        password = passwordField.getText();
        user = userList.login(username, password);
        matchingUsername = userList.findUsername(username);
        if (user != null) {
            user.updateAfterLogin();
            datasource.writeData(userList);
            try {
                if (user.isAdmin()) {
                    FXRouter.goTo("admin-dashboard", user);
                } else {
                    FXRouter.goTo("home", user);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            if (matchingUsername == null || username.isEmpty() || password.isEmpty()) {
                errorLabel.setText("Incorrect username and password. Please try again.");
                errorLabel.setVisible(true);
            }
            if (matchingUsername != null && (password.isEmpty() || !matchingUsername.validatePassword(password))) {
                errorLabel.setText("Incorrect password. Please try again.");
                errorLabel.setVisible(true);
            }
            setBorderColorTextField();
            resetBorderTextField();
        }
    }

    @FXML private void onVisiblePasswordClick() {
        if (visiblePasswordImageView.getImage() == hidePasswordImage) {
            showPasswordTextField.setVisible(true);
            visiblePasswordImageView.setImage(showPasswordImage);
        } else {
            showPasswordTextField.setVisible(false);
            visiblePasswordImageView.setImage(hidePasswordImage);
        }
    }
    @FXML private void onBackClick() {
        try {
            FXRouter.goTo("welcome");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML private void onSignUpClick() {
        try {
            FXRouter.goTo("sign-up");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private String setColorBorderTextField(String color){
        switch (color) {
            case "red" -> color = "-fx-border-color: red";
            case "black" -> color = "-fx-border-color: #413b3b";
        }
        return color;
    }


    private void setBorderColorTextField(){
        username = usernameTextField.getText();
        password = passwordField.getText();
        usernameTextField.setStyle(username.isEmpty() ? setColorBorderTextField("red") : setColorBorderTextField("black"));
        if(password.isEmpty()){
            passwordField.setStyle(setColorBorderTextField("red"));
            showPasswordTextField.setStyle(setColorBorderTextField("red"));
        }else{
            passwordField.setStyle(setColorBorderTextField("black"));
            showPasswordTextField.setStyle(setColorBorderTextField("black"));
        }

    }
    private void resetBorderTextField(){
        usernameTextField.textProperty().addListener((observableValue, oldValue , newValue) -> {
            if(!newValue.equals(oldValue) ){
                usernameTextField.setStyle("-fx-border-color: #413b3b");
                errorLabel.setVisible(false);
            }
        });

        passwordField.textProperty().addListener((observableValue, oldValue , newValue) -> {
            if(!newValue.equals(oldValue) ){
                passwordField.setStyle("-fx-border-color: #413b3b");
                errorLabel.setVisible(false);
            }
        });

    }

    @FXML private void onKeyHidePassword() {
        password = passwordField.getText();
        showPasswordTextField.setText(password);
    }
    @FXML private void onKeyShowPassword() {
        password = showPasswordTextField.getText();
        passwordField.setText(password);
    }


    private void loadImage() {
        Image upComingBackground = new Image(getClass().getResourceAsStream("/images/backgrounds/login/sign_event_bg1.png"));
        upComingEventsBackgroundImageView.setImage(upComingBackground);

        Image signBackground = new Image(getClass().getResourceAsStream("/images/backgrounds/login/sign_event_bg2.png"));
        signBackgroundImageView.setImage(signBackground);

        Image usernameIcon = new Image(getClass().getResourceAsStream("/images/icons/login/username_field.png"));
        usernameIconView.setImage(usernameIcon);

        Image passwordIcon = new Image(getClass().getResourceAsStream("/images/icons/login/password_field.png"));
        passwordIconView.setImage(passwordIcon);

        Image profileImage = new Image(getClass().getResourceAsStream("/images/profile/sign-in/sign-in-avatar.png"));
        profileImageView.setImage(profileImage);


        showPasswordImage = new Image(getClass().getResourceAsStream("/images/icons/login/show_password.png"));
        hidePasswordImage = new Image(getClass().getResourceAsStream("/images/icons/login/hide_password.png"));
        visiblePasswordImageView.setImage(hidePasswordImage);
    }
}
