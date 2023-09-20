package cs211.project.controllers;

import cs211.project.models.User;
import cs211.project.models.collections.UserList;
import cs211.project.services.FXRouter;
import cs211.project.services.UserListDataSource;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Shape;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class SignInController {

    private int page = 0;
    private int maxPage;
    private final int maxPasswordLimit = 27, maxUsernameLimit = 20;


    @FXML
    private Button backButton, nextButton;
    @FXML
    private Shape backCircle, nextCircle;

    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField showPasswordTextField, usernameTextField;

    @FXML
    private ImageView upComingEventsImageView, signBackgroundImageView, upComingEventsBackgroundImageView;
    @FXML
    private ImageView usernameIconView, passwordIconView, visiblePasswordImageView, profileImageView;
    private Image showPasswordImage, hidePasswordImage;

    @FXML
    private Label errorLabel;

    private String password, username;
    private UserListDataSource datasource;
    private UserList userList;
    protected User matchingUsername,user;
    protected String formattedDate;

    @FXML
    void initialize() {
        datasource = new UserListDataSource("data", "user-list.csv");

        userList = datasource.readData();

        eventHandleEnter();
        loadImage();
        showImage(page);
        maxPage = calculateMaxPage();

        maximumLengthField();

        showPasswordTextField.setVisible(false);
        visiblePasswordImageView.setImage(hidePasswordImage);
        updateVisibleButton();

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
            user.setStatus(true);
            user.setLastedLogin(generateLastedLogin());
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
    @FXML private void onNextButtonClick() {
        if (page < maxPage) {
            page++;
        }
        showImage(page);
    }
    @FXML private void onBackButtonClick() {
        if (page > 0) {
            page--;
        }
        showImage(page);
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

    private String generateLastedLogin(){
        LocalDateTime currentDate = LocalDateTime.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yy-MM-dd : hh:mm:ss").withLocale(Locale.US));
        return formattedDate;
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

    private void updateVisibleButton() {
        backButton.setVisible(page > 0);
        backCircle.setVisible(page > 0);
        nextButton.setVisible(page != maxPage);
        nextCircle.setVisible(page != maxPage);
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
    private void showImage(int pageNumber) {
        Image image = new Image(getClass().getResourceAsStream("/images/login/event" + pageNumber + "_test.jpg"));
        upComingEventsImageView.setImage(image);
        updateVisibleButton();
    }
    private int calculateMaxPage() {
        int countImage = 0;
        while (true) {
            String path = "/images/login/event" + countImage + "_test.jpg";
            if (getClass().getResource(path) != null) {
                countImage++;
            } else {
                break;
            }
        }
        return countImage - 1;
    }

}
