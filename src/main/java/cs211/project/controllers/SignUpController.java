package cs211.project.controllers;

import cs211.project.models.User;
import cs211.project.services.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;

import java.io.IOException;

import static java.awt.SystemColor.text;

public class SignUpController {
    private int page = 0;
    private int maxPage;

    @FXML
    private Button backButton, nextButton;
    @FXML
    private Shape backCircle, nextCircle, passwordRequireBox, passwordRequireBoxLabel;

    @FXML
    private Label passwordRequireLabel, passwordLengthReq, passwordUpperCaseReq, passwordLowerCaseReq, passwordSpecialReq, passwordNumReq, usernameReq, displayNameReq, errorLabel;

    @FXML
    private PasswordField passwordField, confirmPasswordField;
    @FXML
    private TextField showPasswordTextField, showConfirmPasswordTextField, displayNameTextfield, usernameTextField;

    @FXML
    private ImageView upComingEventsImageView, signBackgroundImageView, upComingEventsBackgroundImageView;
    @FXML
    private ImageView fullNameIconView, usernameIconView, passwordIconView, confirmPasswordIconView, visiblePasswordImageView,checkBoxPasswordImageView,checkBoxConfirmPasswordImageView;
    private Image showPasswordImage, hidePasswordImage,checkBoxPasswordIcon;

    private String password, confirmPassword, displayName, username;
    private boolean passwordMatching =false;
    private int maxDisplayNameLimit = 24,maxUsernameLimit = 20,maxPasswordLimit = 30;


    @FXML
    void initialize() {
        maximumLengthField();
        showFocusRequirementName();
        showFocusRequirementsPassword();
        loadImage();
        setTextFieldVisible(false);
        updateVisibleButton();
        showImage(page);
        maxPage = calculateMaxPage();
        errorLabel.setVisible(false);

    }
    private void showFocusRequirementName() {
        displayNameReq.setVisible(false);
        usernameReq.setVisible(false);

        displayNameTextfield.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                displayNameReq.setVisible(false);
                showFocusRequirementName();
            } else {
                displayName = displayNameTextfield.getText();
                displayNameReq.setStyle(!displayName.isEmpty() ? "-fx-text-fill: #413b3b" : "-fx-text-fill: red");
                displayNameReq.setVisible(true);
            }
        });

        usernameTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {

            if (!newValue) {
                usernameReq.setVisible(false);
                showFocusRequirementName();
            } else {
                username = usernameTextField.getText();
                usernameReq.setStyle(!username.isEmpty() ? "-fx-text-fill: #413b3b" : "-fx-text-fill: red");
                boolean isValid = true;
                for(char p:username.toCharArray()){
                    if(!(Character.isDigit(p) || Character.isLowerCase(p) || Character.isUpperCase(p) || "_.".contains(String.valueOf(p))) ){
                        isValid = false;
                        break;
                    }
                }
                usernameReq.setStyle((!username.isEmpty()) && isValid ? "-fx-text-fill: #413b3b" : "-fx-text-fill: red");
                usernameReq.setVisible(true);
            }
        });
    }

    public void onKeyDisplayName(KeyEvent event) {
        displayName = displayNameTextfield.getText();
        displayNameReq.setStyle(!displayName.isEmpty() ? "-fx-text-fill: #413b3b" : "-fx-text-fill: red");
    }

    public void onKeyUsername(KeyEvent event){
        username = usernameTextField.getText();
        usernameReq.setStyle(!username.isEmpty() ? "-fx-text-fill: #413b3b" : "-fx-text-fill: red");
        boolean isValid = true;
        for(char p:username.toCharArray()){
            if(!(Character.isDigit(p) || Character.isLowerCase(p) || Character.isUpperCase(p) || "_.".contains(String.valueOf(p))) ){
                isValid = false;
                break;
            }

        }
        usernameReq.setStyle((!username.isEmpty()) && isValid ? "-fx-text-fill: #413b3b" : "-fx-text-fill: red");
    }

    private void maximumLengthField(){
        displayNameTextfield.textProperty().addListener((observableValue, oldValue , newValue) -> {
            if(newValue.length() > maxDisplayNameLimit){
                displayNameTextfield.setText(oldValue);
            }
        });

        usernameTextField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if(newValue.length() > maxUsernameLimit){
                usernameTextField.setText(oldValue);
            }
        }));

        showPasswordTextField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if(newValue.length() > maxPasswordLimit){
                passwordField.setText(oldValue);
                showPasswordTextField.setText(oldValue);
            }
        }));

        showConfirmPasswordTextField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if(newValue.length() > maxPasswordLimit){
                confirmPasswordField.setText(oldValue);
                showConfirmPasswordTextField.setText(oldValue);
            }
        }));
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
    protected void onSignInClick() {
        try {
            FXRouter.goTo("sign-in");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onBackClick() {
        try {
            FXRouter.goTo("welcome");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showFocusRequirementsPassword() {
        showRequirementPassword(false);
        passwordField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                showRequirementPassword(false);
                showFocusRequirementsPassword();
            }else{
                showRequirementPassword(true);
            }
        });

        showPasswordTextField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                showRequirementPassword(false);
                showFocusRequirementsPassword();
            }else{
                showRequirementPassword(true);
            }
        });

    }

    private void showRequirementPassword(boolean require){
        passwordRequireBoxLabel.setVisible(require);
        passwordRequireBox.setVisible(require);
        passwordRequireLabel.setVisible(require);
        passwordLengthReq.setVisible(require);
        passwordUpperCaseReq.setVisible(require);
        passwordLowerCaseReq.setVisible(require);
        passwordSpecialReq.setVisible(require);
        passwordNumReq.setVisible(require);
    }


    public void onKeyHidePassword(KeyEvent keyEvent) {
        password = passwordField.getText();
        showPasswordTextField.setText(password);
        checkPasswordRequirement();
    }

    public void onKeyShowPassword(KeyEvent keyEvent) {
        password = showPasswordTextField.getText();
        passwordField.setText(password);
        checkPasswordRequirement();
    }


    private void checkPasswordRequirement() {
        boolean hasUpperCase = false, hasLowerCase = false, hasDigit = false, hasSpecialCharacter = false , hasFitLength = false;
        String specialCharacters = "!@#$";

        if(password.length() >= 8 && password.length() <= 20){
            hasFitLength = true;
        }
        for (char p : password.toCharArray()) {
            if (Character.isUpperCase(p)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(p)) {
                hasLowerCase = true;
            } else if (Character.isDigit(p)) {
                hasDigit = true;
            } else if (specialCharacters.contains(String.valueOf(p))) {
                hasSpecialCharacter = true;
            }
        }

        passwordLengthReq.setStyle( hasFitLength? "-fx-text-fill: #70C050" : "-fx-text-fill: red");
        passwordUpperCaseReq.setStyle(hasUpperCase ? "-fx-text-fill: #70C050" : "-fx-text-fill: red");
        passwordLowerCaseReq.setStyle(hasLowerCase ? "-fx-text-fill: #70C050" : "-fx-text-fill: red");
        passwordNumReq.setStyle(hasDigit ? "-fx-text-fill: #70C050" : "-fx-text-fill: red");
        passwordSpecialReq.setStyle(hasSpecialCharacter ? "-fx-text-fill: #70C050" : "-fx-text-fill: red");

        if(hasFitLength && hasLowerCase && hasDigit && hasUpperCase && hasSpecialCharacter){
            passwordMatching = true;
            showRequirementPassword(false);
            checkBoxPasswordImageView.setImage(checkBoxPasswordIcon);
            checkBoxPasswordImageView.setVisible(true);
            if(password.equals(confirmPassword)){
                checkBoxConfirmPasswordImageView.setVisible(true);
                checkBoxConfirmPasswordImageView.setImage(checkBoxPasswordIcon);
                errorLabel.setVisible(false);
            }else {
                checkBoxConfirmPasswordImageView.setVisible(false);
                errorLabel.setVisible(true);
            }
        }else{
            checkBoxConfirmPasswordImageView.setVisible(false);
            checkBoxPasswordImageView.setVisible(false);
            passwordMatching = false;
            showRequirementPassword(true);
        }


    }


    public void onKeyHideConfirmPassword(KeyEvent keyEvent) {
        confirmPassword = confirmPasswordField.getText();
        showConfirmPasswordTextField.setText(confirmPassword);
        checkPasswordRequirement();
    }

    public void onKeyShowConfirmPassword(KeyEvent keyEvent) {
        confirmPassword = showConfirmPasswordTextField.getText();
        confirmPasswordField.setText(confirmPassword);
        checkPasswordRequirement();
    }

    private void setTextFieldVisible(boolean visible){
        showPasswordTextField.setVisible(visible);
        showConfirmPasswordTextField.setVisible(visible);
    }

    @FXML
    private void onVisiblePasswordClick(MouseEvent event) {
        if (visiblePasswordImageView.getImage() == hidePasswordImage) {
            setTextFieldVisible(true);
            visiblePasswordImageView.setImage(showPasswordImage);
        } else {
            setTextFieldVisible(false);
            visiblePasswordImageView.setImage(hidePasswordImage);

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

        checkBoxPasswordIcon = new Image(getClass().getResourceAsStream("/images/icons/login/checkbox_password.png"));
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

    private boolean validatePasswordConfirmation(){
        return (password.equals(confirmPassword) && passwordMatching);
    }

    public void onCreateAccountButton(ActionEvent actionEvent) {
        if(validatePasswordConfirmation()){
            User user = new User(displayNameTextfield.getText(), usernameTextField.getText(),password);
            try {
                FXRouter.goTo("sign-in", user);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            errorLabel.setVisible(true);
        }

    }

}
