package cs211.project.controllers;

import cs211.project.models.User;
import cs211.project.models.collections.UserList;
import cs211.project.services.FXRouter;
import cs211.project.services.UserListDataSource;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Shape;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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


    @FXML
    void initialize() {
        datasource = new UserListDataSource("data", "user-list.csv");
        userList = datasource.readData();


        loadImage();
        showImage(page);
        maxPage = calculateMaxPage();

        maximumLengthField();

        showPasswordTextField.setVisible(false);
        visiblePasswordImageView.setImage(hidePasswordImage);
        updateVisibleButton();

        errorLabel.setVisible(false);

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

    public void onLoginButton() {
        username = usernameTextField.getText();
        password = passwordField.getText();
        User user = userList.login(username, password);
        User matchingUsername = userList.findUsername(username);
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


    @FXML
    protected void onNextButtonClick() {
        if (page < maxPage) {
            page++;
        }
        showImage(page);
    }

    @FXML
    protected void onBackButtonClick() {
        if (page > 0) {
            page--;
        }
        showImage(page);
    }

    @FXML
    protected void onBackClick() {
        try {
            FXRouter.goTo("welcome");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onSignUpClick() {
        try {
            FXRouter.goTo("sign-up");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateLastedLogin(){
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
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

    public void onKeyHidePassword() {
        password = passwordField.getText();
        showPasswordTextField.setText(password);
    }

    public void onKeyShowPassword() {
        password = showPasswordTextField.getText();
        passwordField.setText(password);
    }

    @FXML
    private void onVisiblePasswordClick() {
        if (visiblePasswordImageView.getImage() == hidePasswordImage) {
            showPasswordTextField.setVisible(true);
            visiblePasswordImageView.setImage(showPasswordImage);
        } else {
            showPasswordTextField.setVisible(false);
            visiblePasswordImageView.setImage(hidePasswordImage);
        }
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
