package cs211.project.componentControllers;

import cs211.project.models.User;
import cs211.project.models.collections.UserList;
import cs211.project.services.FXRouter;
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

public class ChangePasswordController {
    @FXML private PasswordField currentPasswordField, newPasswordField, reNewPasswordField;
    @FXML private TextField currentPasswordTextField, newPasswordTextField, reNewPasswordTextField;
    @FXML private Label errorLabel, passwordLengthReq, passwordLowerCaseReq, passwordNumReq, passwordSpecialReq, passwordUpperCaseReq;
    @FXML private ImageView newPasswordCheckImageView, reNewPasswordCheckImageView, visibleCurrentPasswordImageView, visibleNewPasswordImageView, visibleReNewPasswordImageView;
    @FXML private AnchorPane passwordReq;
    private final int maxPasswordLimit = 27;
    private String currentPassword = "" , newPassword = "", reNewPassword = "";
    private boolean passwordMatching;
    private Image visibleIcon, inVisibleIcon;
    private Image checkBoxPasswordImage;
    private final User user = (User) FXRouter.getData();
    private UserListDataSource datasource;
    private UserList userList;

    @FXML private void initialize() {
        datasource = new UserListDataSource("data", "user-list.csv");
        userList = datasource.readData();

        textFieldAndImageViewInit();
        loadVisibleImageInit();
        maximumLengthField();

        showFocusRequirementsPassword();
        eventHandleEnter();
    }

    private void eventHandleEnter(){
        EventHandler<KeyEvent> enterEventHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    onResetPasswordClick();
                }
            }
        };
        currentPasswordField.setOnKeyPressed(enterEventHandler);
        currentPasswordTextField.setOnKeyPressed(enterEventHandler);

        reNewPasswordField.setOnKeyPressed(enterEventHandler);
        reNewPasswordTextField.setOnKeyPressed(enterEventHandler);

        newPasswordField.setOnKeyPressed(enterEventHandler);
        newPasswordTextField.setOnKeyPressed(enterEventHandler);
    }

    @FXML private void onResetPasswordClick(){
        checkPasswordRequirement();
        getPassword();
        if(user.validatePassword(currentPassword) && passwordMatching){
            userList.resetPassword(user.getUsername(),newPassword);
            User userUpdate = userList.findUsername(user.getUsername());
            datasource.writeData(userList);
            if(user.isAdmin()){
                try {
                    FXRouter.goTo("admin-dashboard", userUpdate);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else{
                try {
                    FXRouter.goTo("setting", userUpdate);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }else{
            errorLabel.setVisible(true);
            resetErrorLabel();
        }
    }

    @FXML private void onVisibleCurrentPasswordClick() {
        getPassword();
        if (currentPasswordField.isVisible()) {
            currentPasswordTextField.setText(currentPassword);
            currentPasswordField.setVisible(false);
            currentPasswordTextField.setVisible(true);
            visibleCurrentPasswordImageView.setImage(inVisibleIcon);
        } else {
            currentPasswordField.setText(currentPassword);
            currentPasswordField.setVisible(true);
            currentPasswordTextField.setVisible(false);
            visibleCurrentPasswordImageView.setImage(visibleIcon);
        }
    }

    @FXML private void onVisibleNewPasswordClick() {
        getPassword();
        if (newPasswordField.isVisible()) {
            newPasswordTextField.setText(newPassword);
            newPasswordField.setVisible(false);
            newPasswordTextField.setVisible(true);
            visibleNewPasswordImageView.setImage(inVisibleIcon);
        } else {
            newPasswordField.setText(newPassword);
            newPasswordField.setVisible(true);
            newPasswordTextField.setVisible(false);
            visibleNewPasswordImageView.setImage(visibleIcon);
        }
    }

    @FXML private void onVisibleReNewPasswordClick() {
        getPassword();
        if (reNewPasswordField.isVisible()) {
            reNewPasswordTextField.setText(reNewPassword);
            reNewPasswordField.setVisible(false);
            reNewPasswordTextField.setVisible(true);
            visibleReNewPasswordImageView.setImage(inVisibleIcon);
        } else {
            reNewPassword = reNewPasswordTextField.getText();
            reNewPasswordField.setText(reNewPassword);
            reNewPasswordField.setVisible(true);
            reNewPasswordTextField.setVisible(false);
            visibleReNewPasswordImageView.setImage(visibleIcon);
        }
    }

    private void textFieldAndImageViewInit() {
        currentPasswordTextField.setVisible(false);
        newPasswordTextField.setVisible(false);
        reNewPasswordTextField.setVisible(false);

        newPasswordCheckImageView.setVisible(false);
        reNewPasswordCheckImageView.setVisible(false);

        passwordReq.setVisible(false);
        errorLabel.setVisible(false);
        errorLabel.setText("Wrong password, Please try again.");
    }

    private void loadVisibleImageInit() {
        visibleIcon = new Image(getClass().getResourceAsStream("/images/icons/login/show_password.png"));
        inVisibleIcon = new Image(getClass().getResourceAsStream("/images/icons/login/hide_password.png"));
        checkBoxPasswordImage = new Image(getClass().getResourceAsStream("/images/icons/login/checkbox_password.png"));

        newPasswordCheckImageView.setImage(checkBoxPasswordImage);
        reNewPasswordCheckImageView.setImage(checkBoxPasswordImage);

        visibleCurrentPasswordImageView.setImage(visibleIcon);
        visibleNewPasswordImageView.setImage(visibleIcon);
        visibleReNewPasswordImageView.setImage(visibleIcon);
    }

    private void maximumLengthField() {
        currentPasswordField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue.length() > maxPasswordLimit) {
                currentPasswordField.setText(oldValue);
            }
        }));
        currentPasswordTextField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue.length() > maxPasswordLimit) {
                currentPasswordTextField.setText(oldValue);
            }
        }));


        newPasswordField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue.length() > maxPasswordLimit) {
                newPasswordField.setText(oldValue);
            }
        }));

        newPasswordTextField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue.length() > maxPasswordLimit) {
                newPasswordTextField.setText(oldValue);
            }
        }));


        reNewPasswordField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue.length() > maxPasswordLimit) {
                reNewPasswordField.setText(oldValue);
            }
        }));

        reNewPasswordTextField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue.length() > maxPasswordLimit) {
                reNewPasswordTextField.setText(oldValue);
            }
        }));
    }

    private void getPassword(){
        if (currentPasswordField.isVisible()) {
            currentPassword = currentPasswordField.getText();
        } else {
            currentPassword = currentPasswordTextField.getText();
        }

        if (newPasswordField.isVisible()) {
            newPassword = newPasswordField.getText();
        } else {
            newPassword = newPasswordTextField.getText();
        }

        if (reNewPasswordField.isVisible()) {
            reNewPassword = reNewPasswordField.getText();
        } else {
            reNewPassword = reNewPasswordTextField.getText();
        }
    }

    private void showFocusRequirementsPassword() {
        passwordReq.setVisible(false);
        newPasswordField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                showFocusRequirementsPassword();
            }
        });

        newPasswordField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                showFocusRequirementsPassword();
            }
        });

        reNewPasswordField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                showFocusRequirementsPassword();
            }
        });

        reNewPasswordField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                showFocusRequirementsPassword();
            }
        });
    }

    private void checkPasswordRequirement() {
        passwordMatching = false;
        boolean hasUpperCase = false, hasLowerCase = false, hasDigit = false, hasSpecialCharacter = false, hasFitLength = false;
        String specialCharacters = "!@#$";
        if (newPassword.length() >= 8 && newPassword.length() <= 20) {
            hasFitLength = true;
        }
        for (char p : newPassword.toCharArray()) {
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
        passwordLengthReq.setStyle(hasFitLength ? setColorTextFill("green") : setColorTextFill("red"));
        passwordUpperCaseReq.setStyle(hasUpperCase ? setColorTextFill("green") : setColorTextFill("red"));
        passwordLowerCaseReq.setStyle(hasLowerCase ? setColorTextFill("green") : setColorTextFill("red"));
        passwordNumReq.setStyle(hasDigit ? setColorTextFill("green") : setColorTextFill("red"));
        passwordSpecialReq.setStyle(hasSpecialCharacter ? setColorTextFill("green") : setColorTextFill("red"));

        if (hasFitLength && hasLowerCase && hasDigit && hasUpperCase && hasSpecialCharacter) {
            newPasswordCheckImageView.setVisible(true);
            passwordReq.setVisible(false);
            if(reNewPassword.equals(newPassword)){
                passwordMatching = true;
                reNewPasswordCheckImageView.setVisible(true);
            }else{
                passwordMatching = false;
                reNewPasswordCheckImageView.setVisible(false);
            }

        }else{
            newPasswordCheckImageView.setVisible(false);
            passwordReq.setVisible(true);
        }
    }

    @FXML private void onKeyHidePassword() {
        getPassword();
        checkPasswordRequirement();
    }

    @FXML
    private void onKeyShowPassword() {
        getPassword();
        checkPasswordRequirement();
    }

    private void resetErrorLabel(){
        currentPasswordTextField.textProperty().addListener((observableValue, oldValue , newValue) -> {
            if(!newValue.equals(oldValue) ){
                errorLabel.setVisible(false);
            }
        });
        currentPasswordField.textProperty().addListener((observableValue, oldValue , newValue) -> {
            if(!newValue.equals(oldValue) ){
                errorLabel.setVisible(false);
            }
        });


        newPasswordTextField.textProperty().addListener((observableValue, oldValue , newValue) -> {
            if(!newValue.equals(oldValue) ){
                errorLabel.setVisible(false);
            }
        });
        newPasswordField.textProperty().addListener((observableValue, oldValue , newValue) -> {
            if(!newValue.equals(oldValue) ){
                errorLabel.setVisible(false);
            }
        });


        reNewPasswordTextField.textProperty().addListener((observableValue, oldValue , newValue) -> {
            if(!newValue.equals(oldValue) ){
                errorLabel.setVisible(false);
            }
        });
        reNewPasswordField.textProperty().addListener((observableValue, oldValue , newValue) -> {
            if(!newValue.equals(oldValue) ){
                errorLabel.setVisible(false);
            }
        });
    }

    private String setColorTextFill(String color){
        switch (color) {
            case "black" -> color = "-fx-text-fill: #413b3b";
            case "red" -> color = "-fx-text-fill: #C84D3D";
            case "green" -> color = "-fx-text-fill: green";
        }
        return color;
    }
}