package cs211.project.controllers;

import cs211.project.models.User;
import cs211.project.models.collections.UserList;
import cs211.project.services.EventDataSourceHardCode;
import cs211.project.services.FXRouter;
import cs211.project.services.UserDataSourceHardCode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;

import java.io.IOException;
import java.util.Objects;

public class SignInController {

    private int page = 0;
    private int maxPage;
    private Image showPasswordImage, hidePasswordImage;

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

    private UserList userList;
    private User user;
    String password;
//    User user = (User) FXRouter.getData();


    @FXML
    void initialize() {
        UserDataSourceHardCode datasource = new UserDataSourceHardCode();
        userList = datasource.readData();
//        System.out.println(user.getUsername() + user.getPassword());
        loadImage();
        showPasswordTextField.setVisible(false);
        visiblePasswordImageView.setImage(hidePasswordImage);

        maxPage = calculateMaxPage();
        updateVisibleButton();
        showImage(page);
    }


    private void updateVisibleButton() {
        backButton.setVisible(page > 0);
        backCircle.setVisible(page > 0);
        nextButton.setVisible(page != maxPage);
        nextCircle.setVisible(page != maxPage);
    }

    public void onKeyHidePassword(KeyEvent keyEvent) {
        password = passwordField.getText();
        showPasswordTextField.setText(password);
    }

    public void onKeyShowPassword(KeyEvent keyEvent) {
        password = showPasswordTextField.getText();
        passwordField.setText(password);
    }

    @FXML
    private void onVisiblePasswordClick(MouseEvent event) {
        if (visiblePasswordImageView.getImage() == hidePasswordImage) {
            showPasswordTextField.setVisible(true);
            visiblePasswordImageView.setImage(showPasswordImage);
        } else {
            showPasswordTextField.setVisible(false);
            visiblePasswordImageView.setImage(hidePasswordImage);
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

    private void loadImage() {
        Image upComingBackground = new Image(getClass().getResourceAsStream("/images/backgrounds/login/sign_event_bg1.png"));
        upComingEventsBackgroundImageView.setImage(upComingBackground);

        Image signBackground = new Image(getClass().getResourceAsStream("/images/backgrounds/login/sign_evnt_bg2.png"));
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
        String path = "/images/login/event" + pageNumber + "_test.jpg";
        Image image = new Image(getClass().getResourceAsStream(path));
        upComingEventsImageView.setImage(image);
        updateVisibleButton();
    }

    private boolean validateUser() {
        user = userList.findUsername(usernameTextField.getText());
        if(user != null && user.getUsername().equals(usernameTextField.getText()) && user.getPassword().equals(password)){
            return true;
        }else{
            return false;
        }
//        if(usernameTextField.getText().equals(user.getUsername())){
//            profileImageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(user.getImagePath()))));
//        }
//        return usernameTextField.getText().equals(user.getUsername()) && password.equals(user.getPassword());
    }




    public void onLoginButton(ActionEvent actionEvent) {
        if(validateUser()){
            try {
                FXRouter.goTo("home", user);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            return ;
            //todo: error
        }


    }
}
