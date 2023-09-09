package cs211.project.controllers;

import cs211.project.models.User;
import cs211.project.models.collections.UserList;
import cs211.project.services.FXRouter;
import cs211.project.services.LoadNavbarComponent;
import cs211.project.services.UserListDataSource;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class UserProfileController {

    @FXML private ImageView iconProfileImageView, imageChangeView, ellipseIconImageView, frameIconImageView, profileIconImageView, visiblePasswordImageView, fullNameIconImageView, contactIconImageView, passwordIconImageView;
    @FXML private AnchorPane navbarAnchorPane;
    @FXML private TextField displayNameTextField, showPasswordTextField, contactNumberTextField;
    @FXML private TextArea bioTextArea;
    @FXML private PasswordField passwordField;
    @FXML private Button editButton, cancelButton, saveButton;
    @FXML private Label passwordLabel,countBioLabel,maximumCountBioLabel,usernameLabel,idLabel,bioProfileLabel,displayNameProfileLabel,usernameProfileLabel,idProfileLabel;
    @FXML private Label displayNameReq, contactNumberReq, passwordReq;

    private Image showPasswordImage, hidePasswordImage;
    private String displayName, password, contactNumber, bioText, previousDisplayName, previousContactNumber , previousBioText, previousBioCount ;

    private Boolean displayNameRequirement = false ,isValid = false, isValidContactNumber = false;

    private final int MAX_PASSWORD_LIMIT = 27,  MAX_DISPLAY_NAME_LIMIT = 24, MAX_CONTACT_LIMIT = 10, MAX_BIO_LIMIT = 300;
    private final User user = (User) FXRouter.getData();

    UserListDataSource datasource ;
    UserList userList ;

    @FXML
    private void initialize() {
        datasource = new UserListDataSource("data","user-list.csv");
        userList = datasource.readData();

        userData();
        maximumLengthField();
        setRequirementLabel();

        loadImage();

        showFocusRequirement();

        new LoadNavbarComponent(user, navbarAnchorPane);
        loadPasswordFieldAndButtonProfile();
        loadIconImageProfile();
    }

    public void setRequirementLabel(){
        displayNameReq.setVisible(false);
        contactNumberReq.setVisible(false);
        passwordReq.setVisible(false);
    }

    private void userData(){
        String username = user.getUsername();
        displayName = user.getDisplayName();
        bioText = user.getBio();

        String userId = user.getUserId();
        contactNumber = user.getContactNumber();

        usernameLabel.setText(username);
        usernameProfileLabel.setText(username);

        displayNameTextField.setText(displayName);
        displayNameProfileLabel.setText(displayName);

        bioTextArea.setText(bioText);
        bioProfileLabel.setText(bioText);

        idLabel.setText(userId);
        idProfileLabel.setText(userId);

        contactNumberTextField.setText(contactNumber);
    }

    private void maximumLengthField(){
        displayNameTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.length() > MAX_DISPLAY_NAME_LIMIT) {
                displayNameTextField.setText(oldValue);
            }
        });

        contactNumberTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.length() > MAX_CONTACT_LIMIT) {
                contactNumberTextField.setText(oldValue);
            }
        });

        bioTextArea.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.length() > MAX_BIO_LIMIT) {
                bioTextArea.setText(oldValue);
            }
        });

        passwordField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.length() > MAX_PASSWORD_LIMIT) {
                passwordField.setText(oldValue);
            }
        });

        showPasswordTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.length() > MAX_PASSWORD_LIMIT) {
                showPasswordTextField.setText(oldValue);
            }
        });
    }


    @FXML private void loadPasswordFieldAndButtonProfile() {
        displayNameReq.setVisible(false);
        passwordReq.setVisible(false);
        contactNumberReq.setVisible(false);
        passwordField.setVisible(false);
        showPasswordTextField.setVisible(false);
        passwordLabel.setVisible(false);
        setEditableFields(false);
        setStylesEditable(false);
        editButton.setVisible(true);
        cancelButton.setVisible(false);
        saveButton.setVisible(false);
        countBioLabel.setVisible(false);
        maximumCountBioLabel.setVisible(false);
    }

    @FXML private void loadPasswordFieldAndButtonEditProfile() {
        passwordField.setVisible(true);
        showPasswordTextField.setVisible(false);
        passwordLabel.setVisible(true);
        setEditableFields(true);
        setStylesEditable(true);
        editButton.setVisible(false);
        cancelButton.setVisible(true);
        saveButton.setVisible(true);
        countBioLabel.setVisible(true);
        maximumCountBioLabel.setVisible(true);
    }

    @FXML private void loadIconImageProfile(){
        visiblePasswordImageView.setVisible(false);
        passwordIconImageView.setVisible(false);
        fullNameIconImageView.setVisible(false);
        contactIconImageView.setVisible(false);
        imageChangeView.setVisible(false);
    }

    @FXML private void loadIconImageEditProfile(){
        visiblePasswordImageView.setVisible(true);
        visiblePasswordImageView.setImage(hidePasswordImage);
        passwordIconImageView.setVisible(true);
        fullNameIconImageView.setVisible(true);
        contactIconImageView.setVisible(true);
        imageChangeView.setVisible(true);
    }

    @FXML
    private void onEditProfileButtonClick() {
        loadPasswordFieldAndButtonEditProfile();
        loadIconImageEditProfile();

        previousBioText = bioTextArea.getText();
        previousBioCount = String.valueOf(previousBioText.length());

        previousDisplayName = displayNameTextField.getText();
        previousContactNumber = contactNumberTextField.getText();

        if (previousBioCount.equals("0")) {
            previousBioCount = "0";
            countBioLabel.setText("0");
        } else {
            countBioLabel.setText(previousBioCount);
        }
        countBioLabel.setStyle("");
    }

    @FXML
    protected void onCancelButtonClick() {
        loadPasswordFieldAndButtonProfile();
        loadIconImageProfile();

        passwordField.setText("");
        showPasswordTextField.setText("");

        displayNameTextField.setText(previousDisplayName);
        contactNumberTextField.setText(previousContactNumber);
        bioTextArea.setText(previousBioText);
        user.updateProfile(previousDisplayName, previousContactNumber, previousBioText);
    }


    @FXML
    public void onSaveButtonClick() {
        validateData();
        if (isValid) {
            displayNameProfileLabel.setText(displayName);
            bioProfileLabel.setText(bioText);

            loadPasswordFieldAndButtonProfile();
            loadIconImageProfile();
            passwordReq.setVisible(false);
            passwordField.setText("");
            showPasswordTextField.setText("");


            userList.updateUserProfile(user.getUsername(), displayName, contactNumber, bioText);

            datasource.writeData(userList);
        } else {
            contactNumberReq.setText("Incorrect contact number entered.");
            contactNumberReq.setStyle(setColorTextFill("red"));
            contactNumberReq.setVisible(!isValidContactNumber);
            saveButton.setCancelButton(true);
            countBioLabel.setStyle(bioTextArea.getText().length() <= 280 ? setColorTextFill("black") : setColorTextFill("red"));
            if(password == null){
                passwordReq.setVisible(true);
                passwordReq.setStyle(setColorTextFill("red"));
                passwordReq.setText("Enter your password to save changes to your profile.");
            }else if(!user.validatePassword(password)){
                passwordReq.setVisible(true);
                passwordReq.setStyle(setColorTextFill("red"));
                passwordReq.setText("Wrong password. Please Try Again.");
            }else{
                passwordReq.setVisible(false);
            }
        }
    }

    public void onKeyHidePassword() {
        password = passwordField.getText();
        showPasswordTextField.setText(password);
    }

    public void onKeyShowPassword() {
        password = showPasswordTextField.getText();
        passwordField.setText(password);
    }

    public void validateData(){
        checkDisplayNameReq();
        checkContactNumberReq();
        isValid = displayNameRequirement && isValidContactNumber && bioTextArea.getText().length() <= 280 && checkValidatePassword();
    }

    public boolean checkValidatePassword(){
        if(password != null){
            return user.validatePassword(password);
        }else {
            return false;
        }
    }


    @FXML
    private void onVisiblePasswordClick() {
        if (visiblePasswordImageView.getImage() == hidePasswordImage) {
            passwordField.setVisible(false);
            showPasswordTextField.setVisible(true);
            visiblePasswordImageView.setImage(showPasswordImage);
        } else {
            passwordField.setVisible(true);
            showPasswordTextField.setVisible(false);
            visiblePasswordImageView.setImage(hidePasswordImage);
        }
    }

    private void showFocusRequirement() {
        displayNameTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                checkDisplayNameReq();
            }
        });

        displayNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            displayNameProfileLabel.setText(newValue);
        });

        contactNumberTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                checkContactNumberReq();
            }
        });

        contactNumberTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            checkContactNumberReq();
        });

        bioTextArea.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                bioProfileLabel.setText(bioTextArea.getText());
            }
        });

        bioTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            bioProfileLabel.setText(newValue);
        });
    }


    public void onKeyDisplayName(){
        checkDisplayNameReq();
    }

    public void checkDisplayNameReq(){
        displayNameRequirement = false;
        User currentUser = userList.findUsername(user.getUsername());
        displayName = displayNameTextField.getText();
        if(!displayName.isEmpty() && ((userList.findDisplayName(displayName) != null && currentUser.isDisplayName(displayName)) || (userList.findDisplayName(displayName) == null) || currentUser.isDisplayName(displayName))){
            displayNameReq.setVisible(false);
            displayNameReq.setStyle(setColorTextFill("black"));
            displayNameTextField.setStyle("-fx-border-color: #413b3b");
            displayNameRequirement = true;
        }else{
            displayNameRequirement = false;
            displayNameReq.setVisible(true);
            displayNameReq.setStyle(setColorTextFill("red"));
            if(displayName.isEmpty()){
                displayNameReq.setText("Incorrect display name entered.");
            }else if (userList.findDisplayName(displayName) != null && !currentUser.isDisplayName(displayName)){
                displayNameReq.setText("Duplicate display name. Please use another display name.");
            }else {
                displayNameReq.setVisible(false);
            }
        }
    }


    public void checkContactNumberReq(){
        isValidContactNumber = false;
        boolean isDigit = false,  isLength = false;
        contactNumber = contactNumberTextField.getText();
        if(contactNumber.length() == 10){
            isLength = true;
        }
        for(char c : contactNumber.toCharArray()){
            if(Character.isDigit(c)){
                isDigit = true;
            }else{
                isDigit = false;
                break;
            }
        }isValidContactNumber = isDigit && isLength || contactNumber.length() == 0;

        if(!isValidContactNumber){
            contactNumberReq.setVisible(true);
            contactNumberReq.setText("Incorrect contact number entered.");
            contactNumberReq.setStyle(setColorTextFill("red"));
        }else{
            contactNumberReq.setVisible(false);
        }

    }

    public void onKeyContactNumber(){
        checkContactNumberReq();
    }

    private String setColorTextFill(String color){
        switch (color) {
            case "black" -> color = "-fx-text-fill: #413b3b";
            case "red" -> color = "-fx-text-fill: red";

        }
        return color;
    }


    private void setEditableFields(boolean editable) {
        displayNameTextField.setEditable(editable);
        contactNumberTextField.setEditable(editable);
        bioTextArea.setEditable(editable);
        setStylesEditable(true);
    }

    private void setStylesEditable(boolean stylesEditable) {
        if (stylesEditable) {
            displayNameTextField.getStyleClass().clear();
            contactNumberTextField.getStyleClass().clear();
            bioTextArea.getStyleClass().clear();
            displayNameTextField.getStyleClass().add("profile-text-field2");
            contactNumberTextField.getStyleClass().add("profile-text-field2");
            bioTextArea.getStyleClass().add("profile-text-area");
            displayNameTextField.getStyleClass().add("font");
            contactNumberTextField.getStyleClass().add("font");
            bioTextArea.getStyleClass().add("font");
        } else {
            displayNameTextField.getStyleClass().clear();
            contactNumberTextField.getStyleClass().clear();
            bioTextArea.getStyleClass().clear();
            displayNameTextField.getStyleClass().add("profile-text-field");
            contactNumberTextField.getStyleClass().add("profile-text-field");
            bioTextArea.getStyleClass().add("profile-text-area2");
            displayNameTextField.getStyleClass().add("font");
            contactNumberTextField.getStyleClass().add("font");
            bioTextArea.getStyleClass().add("font");
        }
    }

    private void loadImage() {
        Image iconProfile = new Image(getClass().getResourceAsStream("/images/icons/user-profile/iconProfile.png"));
        iconProfileImageView.setImage(iconProfile);

        Image ellipseIcon = new Image(getClass().getResourceAsStream("/images/icons/user-profile/iconEllipse.png"));
        ellipseIconImageView.setImage(ellipseIcon);

        Image frameIcon = new Image(getClass().getResourceAsStream("/images/icons/user-profile/iconFrame.png"));
        frameIconImageView.setImage(frameIcon);

        Image profileIcon = new Image(getClass().getResourceAsStream(user.getImagePath()));
        profileIconImageView.setImage(profileIcon);

        Image fullNameIcon = new Image(getClass().getResourceAsStream("/images/icons/login/username_field.png"));
        fullNameIconImageView.setImage(fullNameIcon);

        Image passwordIcon = new Image(getClass().getResourceAsStream("/images/icons/login/password_field.png"));
        passwordIconImageView.setImage(passwordIcon);

        Image contactIcon = new Image(getClass().getResourceAsStream("/images/icons/login/contact_field.png"));
        contactIconImageView.setImage(contactIcon);

        Image changeImageIcon = new Image(getClass().getResourceAsStream("/images/icons/login/change_image.png"));
        imageChangeView.setImage(changeImageIcon);

        showPasswordImage = new Image(getClass().getResourceAsStream("/images/icons/login/show_password.png"));
        hidePasswordImage = new Image(getClass().getResourceAsStream("/images/icons/login/hide_password.png"));

    }

    public void onKeyBioCountText(){
        if (saveButton.isVisible()) {
            bioText = bioTextArea.getText();
            countBioLabel.setText(String.valueOf((int) bioText.length()));
            if (bioText.length() >= 280) {
                if (bioText.length() > 280) {
                    countBioLabel.setStyle("-fx-text-fill: red ");
                } else {
                    countBioLabel.setStyle("");
                }
            }else {
                countBioLabel.setStyle("");
            }
        }
    }



}

