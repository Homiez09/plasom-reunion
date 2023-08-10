package cs211.project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;

public class SignInController {

    private int page = 0;
    private int maxPage;
    private Image showPasswordImage, hidePasswordImage;

    @FXML private Button backButton, nextButton;
    @FXML private Shape backCircle, nextCircle;

    @FXML private PasswordField passwordField;
    @FXML private TextField showPasswordTextField;

    @FXML private ImageView upComingEventsImageView, signBackgroundImageView, upComingEventsBackgroundImageView;
    @FXML private ImageView usernameIconView, passwordIconView, visiblePasswordImageView ,profileImageView;

    String password;

    @FXML
    void initialize() {
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

    public void onKeyHidePassword(KeyEvent keyEvent){
        password = passwordField.getText();
        showPasswordTextField.setText(password);
    }

    public void onKeyShowPassword(KeyEvent keyEvent){
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
        Image upComingBackground = new Image(getClass().getResourceAsStream("/images/login/backgrounds/sign_event_bg1.png"));
        upComingEventsBackgroundImageView.setImage(upComingBackground);

        Image signBackground = new Image(getClass().getResourceAsStream("/images/login/backgrounds/sign_evnt_bg2.png"));
        signBackgroundImageView.setImage(signBackground);

        Image usernameIcon = new Image(getClass().getResourceAsStream("/images/login/icons/username_field.png"));
        usernameIconView.setImage(usernameIcon);

        Image passwordIcon = new Image(getClass().getResourceAsStream("/images/login/icons/password_field.png"));
        passwordIconView.setImage(passwordIcon);

        Image profileImage = new Image(getClass().getResourceAsStream("/images/login/profiles/profile_test1.png"));
        profileImageView.setImage(profileImage);


        showPasswordImage = new Image(getClass().getResourceAsStream("/images/login/icons/show_password.png"));
        hidePasswordImage = new Image(getClass().getResourceAsStream("/images/login/icons/hide_password.png"));
        visiblePasswordImageView.setImage(hidePasswordImage);
    }

    private void showImage(int pageNumber) {
        String path = "/images/login/event" + pageNumber + "_test.jpg";
        Image image = new Image(getClass().getResourceAsStream(path));
        upComingEventsImageView.setImage(image);
        updateVisibleButton();
    }
}
