package cs211.project.controllers;

import cs211.project.models.User;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Shape;

import java.io.IOException;


public class SignUpController {


    private int page = 0;
    private int maxPage;
    private final int maxPasswordLimit = 27, maxUsernameLimit = 20, maxDisplayNameLimit = 24;

    @FXML
    private ImageView upComingEventsImageView, signBackgroundImageView, upComingEventsBackgroundImageView;
    @FXML
    private ImageView fullNameIconView, usernameIconView, passwordIconView, confirmPasswordIconView, visiblePasswordImageView,checkBoxPasswordImageView,checkBoxConfirmPasswordImageView;
    private Image showPasswordImage, hidePasswordImage,checkBoxPasswordImage;

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

    private String password, confirmPassword, displayName, username;
    private boolean passwordMatching =false, usernameRequirement = false, displayNameRequirement= false;


    @FXML
    void initialize() {
        loadImage();
        showImage(page);
        maxPage = calculateMaxPage();

        maximumLengthField();

        showFocusRequirementName();
        showFocusRequirementsPassword();

        setTextFieldPasswordVisible(false);
        updateVisibleButton();

        errorLabel.setVisible(false);

    }


    public void onCreateAccountButton() {
        if(validateConfirmation()){
            User user = new User(displayNameTextfield.getText(), usernameTextField.getText(),password);
            try {
                FXRouter.goTo("sign-in", user);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            if(displayNameReq.getStyle().equals(setColorTextFill("red")) || displayName.isEmpty() ){
                displayNameTextfield.setStyle(setColorBorderTextField("red"));
                displayNameRequirement = false;
            }
            if(usernameReq.getStyle().equals(setColorTextFill("red")) || username.isEmpty()){
                usernameTextField.setStyle(setColorBorderTextField("red"));
                usernameRequirement = false;
            }
            if(!passwordMatching){
                setBorderPasswordTextFieldColorRequirement(false);
                setBorderConfirmPasswordTextFieldColorRequirement(false);
                errorLabel.setText("Wrong password requirement. Please try again.");
                errorLabel.setVisible(true);
            }if (!password.equals(confirmPassword)) {
                errorLabel.setText("Those password didn't match. Please try again.");
                setBorderConfirmPasswordTextFieldColorRequirement(false);
                errorLabel.setVisible(true);
            }if(password.equals(confirmPassword)){
                errorLabel.setVisible(false);
            }
        }

    }

    private boolean validateConfirmation(){
        displayName = displayNameTextfield.getText();
        username = usernameTextField.getText();
        password = passwordField.getText();
        confirmPassword = confirmPasswordField.getText();
        if (usernameRequirement && displayNameRequirement && !displayName.isEmpty() && !username.isEmpty()) {
            return (password.equals(confirmPassword) && passwordMatching);
        }
        return false;
    }

    private void checkUsernameRequirement() {
        boolean isValid = true, hasFirstAlphabetic;
        char firstUsernameChar ;

        username = usernameTextField.getText();
        if (!username.isEmpty()){
            firstUsernameChar = username.charAt(0);
            hasFirstAlphabetic = Character.isAlphabetic(firstUsernameChar);
        }else {
            hasFirstAlphabetic = false;
        }
        for (char p : username.toCharArray()) {
            if (!(Character.isDigit(p) || Character.isLowerCase(p) || Character.isUpperCase(p) || "_.".contains(String.valueOf(p)))) {
                isValid = false;
                break;
            }
        }

        if(isValid && hasFirstAlphabetic){
            usernameTextField.setStyle(setColorBorderTextField("black"));
            usernameReq.setStyle(setColorTextFill("black"));
            usernameRequirement = true;
        }else {
            usernameReq.setStyle(setColorTextFill("red"));
            usernameRequirement = false;
        }
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
        passwordLengthReq.setStyle( hasFitLength? setColorTextFill("green") : setColorTextFill("red"));
        passwordUpperCaseReq.setStyle(hasUpperCase ? setColorTextFill("green") : setColorTextFill("red"));
        passwordLowerCaseReq.setStyle(hasLowerCase ? setColorTextFill("green") : setColorTextFill("red"));
        passwordNumReq.setStyle(hasDigit ? setColorTextFill("green") : setColorTextFill("red"));
        passwordSpecialReq.setStyle(hasSpecialCharacter ? setColorTextFill("green") : setColorTextFill("red"));

        if(hasFitLength && hasLowerCase && hasDigit && hasUpperCase && hasSpecialCharacter){
            passwordMatching = true;
            showRequirementPassword(false);
            checkBoxPasswordImageView.setImage(checkBoxPasswordImage);
            checkBoxPasswordImageView.setVisible(true);

            setBorderPasswordTextFieldColorRequirement(true);
            if(password.equals(confirmPassword)){
                showRequirementPassword(false);
                checkBoxConfirmPasswordImageView.setImage(checkBoxPasswordImage);
                checkBoxConfirmPasswordImageView.setVisible(true);
                errorLabel.setVisible(false);
                setBorderConfirmPasswordTextFieldColorRequirement(true);
            }else {
                checkBoxConfirmPasswordImageView.setVisible(false);
                errorLabel.setVisible(false);

            }
        }else{
            checkBoxConfirmPasswordImageView.setVisible(false);
            checkBoxPasswordImageView.setVisible(false);
            passwordMatching = false;
            showRequirementPassword(true);

        }
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

    private void showFocusRequirementName() {
        displayNameReq.setVisible(false);
        displayNameTextfield.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                displayNameReq.setVisible(false);
                showFocusRequirementName();
            } else {
                displayName = displayNameTextfield.getText();
                displayNameReq.setStyle(!displayName.isEmpty() ? setColorTextFill("black") : setColorTextFill("red"));
                displayNameReq.setVisible(true);
            }
        });

        usernameReq.setVisible(false);
        usernameTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                usernameReq.setVisible(false);
                showFocusRequirementName();
            } else {
                usernameReq.setVisible(true);
                checkUsernameRequirement();
            }
        });
    }

    private void showFocusRequirementsPassword() {
        showRequirementPassword(false);
        passwordField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                showRequirementPassword(false);
                showFocusRequirementsPassword();
            }else{
                showRequirementPassword(!passwordMatching);

            }
        });

        showPasswordTextField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                showRequirementPassword(false);
                showFocusRequirementsPassword();
            }else{
                showRequirementPassword(!passwordMatching);
            }
        });
    }


    public void onKeyDisplayName() {
        displayName = displayNameTextfield.getText();
        if(!displayName.isEmpty()){
            displayNameReq.setStyle(setColorTextFill("black"));
            displayNameTextfield.setStyle(setColorBorderTextField("black"));
            displayNameRequirement = true;
        }else{
            displayNameReq.setStyle(setColorTextFill("red"));
            displayNameRequirement = false;
        }

    }

    public void onKeyUsername(){
        username = usernameTextField.getText();
        checkUsernameRequirement();
    }

    public void onKeyHidePassword() {
        password = passwordField.getText();
        showPasswordTextField.setText(password);
        checkPasswordRequirement();
    }

    public void onKeyShowPassword() {
        password = showPasswordTextField.getText();
        passwordField.setText(password);
        checkPasswordRequirement();
    }

    public void onKeyHideConfirmPassword() {
        confirmPassword = confirmPasswordField.getText();
        showConfirmPasswordTextField.setText(confirmPassword);
        checkPasswordRequirement();
    }

    public void onKeyShowConfirmPassword() {
        confirmPassword = showConfirmPasswordTextField.getText();
        confirmPasswordField.setText(confirmPassword);
        checkPasswordRequirement();
    }

    private void setTextFieldPasswordVisible(boolean visible){
        showPasswordTextField.setVisible(visible);
        showConfirmPasswordTextField.setVisible(visible);
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

        passwordField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if(newValue.length() > maxPasswordLimit){
                passwordField.setText(oldValue);
            }
        }));

        showPasswordTextField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if(newValue.length() > maxPasswordLimit){
                showPasswordTextField.setText(oldValue);
            }
        }));

        confirmPasswordField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if(newValue.length() > maxPasswordLimit){
                confirmPasswordField.setText(oldValue);
            }
        }));

        showConfirmPasswordTextField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if(newValue.length() > maxPasswordLimit){
                showConfirmPasswordTextField.setText(oldValue);
            }
        }));
    }

    private String setColorBorderTextField(String color){
        switch (color) {
            case "red" -> color = "-fx-border-color: red";
            case "black" -> color = "-fx-border-color: #413b3b";
        }
        return color;
    }

    private String setColorTextFill(String color){
        switch (color) {
            case "black" -> color = "-fx-text-fill: #413b3b";
            case "red" -> color = "-fx-text-fill: red";
            case "green" -> color = "-fx-text-fill: green";
        }
        return color;
    }

    private void setBorderPasswordTextFieldColorRequirement(boolean value) {
        if(value){
            passwordField.setStyle(setColorBorderTextField("black"));
            showPasswordTextField.setStyle(setColorBorderTextField("black"));
        }else{
            passwordField.setStyle(setColorBorderTextField("red"));
            showPasswordTextField.setStyle(setColorBorderTextField("red"));
        }
    }

    private void setBorderConfirmPasswordTextFieldColorRequirement(boolean value) {
        if(value){
            confirmPasswordField.setStyle(setColorBorderTextField("black"));
            showConfirmPasswordTextField.setStyle(setColorBorderTextField("black"));
        }else{
            confirmPasswordField.setStyle(setColorBorderTextField("red"));
            showConfirmPasswordTextField.setStyle(setColorBorderTextField("red"));
        }
    }

    @FXML
    private void onVisiblePasswordClick() {
        if (visiblePasswordImageView.getImage() == hidePasswordImage) {
            setTextFieldPasswordVisible(true);
            visiblePasswordImageView.setImage(showPasswordImage);
        } else {
            setTextFieldPasswordVisible(false);
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
        fullNameIconView.setImage(usernameIcon);

        Image passwordIcon = new Image(getClass().getResourceAsStream("/images/icons/login/password_field.png"));
        passwordIconView.setImage(passwordIcon);
        confirmPasswordIconView.setImage(passwordIcon);

        checkBoxPasswordImage = new Image(getClass().getResourceAsStream("/images/icons/login/checkbox_password.png"));
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
    protected void onBackClick() {
        try {
            FXRouter.goTo("welcome");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onSignInClick() {
        try {
            FXRouter.goTo("sign-in");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
