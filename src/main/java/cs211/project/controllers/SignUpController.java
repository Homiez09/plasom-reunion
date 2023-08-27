package cs211.project.controllers;

import cs211.project.services.FXRouter;
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

public class SignUpController {
    private int page = 0;
    private int maxPage;

    @FXML
    private Button backButton, nextButton;
    @FXML private Shape backCircle, nextCircle;

    @FXML private PasswordField passwordField,confirmPasswordField;
    @FXML private TextField showPasswordTextField,showConfirmPasswordTextField;

    @FXML private ImageView upComingEventsImageView, signBackgroundImageView, upComingEventsBackgroundImageView;
    @FXML private ImageView fullNameIconView, usernameIconView, passwordIconView, confirmPasswordIconView, visiblePasswordImageView, visibleConfirmPasswordImageView;
    private Image showPasswordImage, hidePasswordImage;
    private String password, confirmPassword;

    @FXML
    void initialize() {
        loadImage();
        showPasswordTextField.setVisible(false);
        visiblePasswordImageView.setImage(hidePasswordImage);
        showConfirmPasswordTextField.setVisible(false);
        visibleConfirmPasswordImageView.setImage(hidePasswordImage);

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
    protected void onSignInClick(){
        try {
            FXRouter.goTo("sign-in");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onBackClick(){
        try {
            FXRouter.goTo("welcome");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onKeyHidePassword(KeyEvent keyEvent) {
        password = passwordField.getText();
        showPasswordTextField.setText(password);
    }

    public void onKeyShowPassword(KeyEvent keyEvent) {
        password = showPasswordTextField.getText();
        passwordField.setText(password);
    }

    public void onKeyHideConfirmPassword(KeyEvent keyEvent) {
        confirmPassword = confirmPasswordField.getText();
        showConfirmPasswordTextField.setText(confirmPassword);
    }

    public void onKeyShowConfirmPassword(KeyEvent keyEvent) {
        confirmPassword = showConfirmPasswordTextField.getText();
        confirmPasswordField.setText(confirmPassword);
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
    private void onVisibleConfirmPasswordClick(MouseEvent event) {
        if (visibleConfirmPasswordImageView.getImage() == hidePasswordImage) {
            showConfirmPasswordTextField.setVisible(true);
            visibleConfirmPasswordImageView.setImage(showPasswordImage);
        } else {
            showConfirmPasswordTextField.setVisible(false);
            visibleConfirmPasswordImageView.setImage(hidePasswordImage);
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
        fullNameIconView.setImage(usernameIcon);

        Image passwordIcon = new Image(getClass().getResourceAsStream("/images/icons/login/password_field.png"));
        passwordIconView.setImage(passwordIcon);
        confirmPasswordIconView.setImage(passwordIcon);

        showPasswordImage = new Image(getClass().getResourceAsStream("/images/icons/login/show_password.png"));
        hidePasswordImage = new Image(getClass().getResourceAsStream("/images/icons/login/hide_password.png"));

        visiblePasswordImageView.setImage(hidePasswordImage);
        visibleConfirmPasswordImageView.setImage(hidePasswordImage);
    }

    private void showImage(int pageNumber) {
        String path = "/images/login/event" + pageNumber + "_test.jpg";
        Image image = new Image(getClass().getResourceAsStream(path));
        upComingEventsImageView.setImage(image);
        updateVisibleButton();
    }

    public void onCreateAccountButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("home");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
