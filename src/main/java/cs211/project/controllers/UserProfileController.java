package cs211.project.controllers;

import cs211.project.models.User;
import cs211.project.models.collections.UserList;
import cs211.project.services.FXRouter;
import cs211.project.services.LoadNavbarComponent;
import cs211.project.services.UserDataSourceHardCode;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class UserProfileController {

    @FXML private ImageView iconProfileImageView, imageChangeView, ellipseIconImageView, frameIconImageView, profileIconImageView, visiblePasswordImageView, fullNameIconImageView, contactIconImageView, passwordIconImageView;
    @FXML private AnchorPane navbarAnchorPane;
    @FXML private TextField displayNameTextField, showPasswordTextField, contactNumberTextField;
    @FXML private TextArea bioTextArea;
    @FXML private PasswordField passwordField;
    @FXML private Button editButton, cancelButton, saveButton;
    @FXML private Label passwordLabel,countBioLabel,maximumCountBioLabel,usernameLabel,idLabel,bioProfileLabel,displayNameProfileLabel,usernameProfileLabel,idProfileLabel;
    private Image showPasswordImage, hidePasswordImage;
    private String password,bioText,previousBioText,previousBioCount, username, displayName, userId, contactNumber;

    private final int MAX_PASSWORD_LIMIT = 27,  MAX_DISPLAY_NAME_LIMIT = 24, MAX_CONTACT_LIMIT = 10, MAX_BIO_LIMIT = 300;
    private User user = (User) FXRouter.getData();
    private UserList userList;
    @FXML
    private void initialize() {
        UserDataSourceHardCode datasource = new UserDataSourceHardCode();
        userList = datasource.readData();

        userData();
        maximumLengthField();

        loadImage();

        new LoadNavbarComponent(user, navbarAnchorPane);
        loadPasswordFieldAndButtonProfile();
        loadIconImageProfile();
    }

    private void userData(){
        username = user.getUsername();
        displayName = user.getDisplayName();
        bioText = user.getBio();

        userId = user.getUserid();
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
        if (previousBioCount == null || previousBioCount.equals("0")) {
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
        displayNameTextField.setText(displayName);
        contactNumberTextField.setText(contactNumber);
        bioTextArea.setText(previousBioText);
        countBioLabel.setText(previousBioCount);
        user.setBio(previousBioText);

    }

    @FXML
    public void onSaveButtonClick() {
        if (bioText.length() <= 280) {
            displayName = displayNameTextField.getText();
            contactNumber = contactNumberTextField.getText();
            bioText = bioTextArea.getText();

            loadPasswordFieldAndButtonProfile();
            loadIconImageProfile();

            user.setBio(bioText);
            user.setDisplayName(displayName);
            user.setContactNumber(contactNumber);

            displayNameProfileLabel.setText(displayName);
            bioProfileLabel.setText(bioText);
        } else {
            saveButton.setCancelButton(true);
            countBioLabel.setStyle("-fx-text-fill: red");
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

    @FXML
    private void onVisiblePasswordClick(MouseEvent event) {
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

