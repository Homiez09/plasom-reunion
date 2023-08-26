package cs211.project.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class UserProfileController {

    @FXML private ImageView iconProfileImageView, imageChangeView, ellipseIconImageView, frameIconImageView, profileIconImageView, visiblePasswordImageView, fullNameIconImageView, contactIconImageView, passwordIconImageView;
    @FXML private AnchorPane navbarAnchorPane;
    @FXML private TextField fullNameTextField, showPasswordTextField, contactNumberTextField;
    @FXML private TextArea bioTextArea;
    @FXML private PasswordField passwordField;
    @FXML private Button editButton, cancelButton, saveButton;
    @FXML private Label passwordLabel,countBioLabel,maximumCountBioLabel,bioProfileLabel;
    private Image showPasswordImage, hidePasswordImage;
    private String password,bioText,previousBioText,previousBioCount;

    @FXML
    private void initialize() {
        loadImage();
        loadNavbarComponent();
        loadPasswordFieldAndButtonProfile();
        loadIconImageProfile();
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
        bioTextArea.setText(previousBioText);
        countBioLabel.setText(previousBioCount);

    }

    @FXML
    protected void onSaveButtonClick() {
        if (bioText.length() <= 280) {
            loadPasswordFieldAndButtonProfile();
            loadIconImageProfile();
            previousBioText = bioText;
            previousBioCount = String.valueOf((int) previousBioText.length());
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
        fullNameTextField.setEditable(editable);
        contactNumberTextField.setEditable(editable);
        bioTextArea.setEditable(editable);
        setStylesEditable(true);
    }

    private void setStylesEditable(boolean stylesEditable) {
        if (stylesEditable) {
            fullNameTextField.getStyleClass().clear();
            contactNumberTextField.getStyleClass().clear();
            bioTextArea.getStyleClass().clear();
            fullNameTextField.getStyleClass().add("profile-text-field2");
            contactNumberTextField.getStyleClass().add("profile-text-field2");
            bioTextArea.getStyleClass().add("profile-text-area");
            fullNameTextField.getStyleClass().add("font");
            contactNumberTextField.getStyleClass().add("font");
            bioTextArea.getStyleClass().add("font");
        } else {
            fullNameTextField.getStyleClass().clear();
            contactNumberTextField.getStyleClass().clear();
            bioTextArea.getStyleClass().clear();
            fullNameTextField.getStyleClass().add("profile-text-field");
            contactNumberTextField.getStyleClass().add("profile-text-field");
            bioTextArea.getStyleClass().add("profile-text-area2");
            fullNameTextField.getStyleClass().add("font");
            contactNumberTextField.getStyleClass().add("font");
            bioTextArea.getStyleClass().add("font");
        }
    }

    private void loadNavbarComponent() {
        try {
            FXMLLoader navbarComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/navbar.fxml"));
            AnchorPane navbarComponent = navbarComponentLoader.load();
            navbarAnchorPane.getChildren().add(navbarComponent);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load navbar component", e);
        }
    }

    private void loadImage() {
        Image iconProfile = new Image(getClass().getResourceAsStream("/images/profiles-user/icons/iconProfile.png"));
        iconProfileImageView.setImage(iconProfile);

        Image ellipseIcon = new Image(getClass().getResourceAsStream("/images/profiles-user/icons/iconEllipse.png"));
        ellipseIconImageView.setImage(ellipseIcon);

        Image frameIcon = new Image(getClass().getResourceAsStream("/images/profiles-user/icons/iconFrame.png"));
        frameIconImageView.setImage(frameIcon);

        Image profileIcon = new Image(getClass().getResourceAsStream("/images/login/profiles/profile_test1.png"));
        profileIconImageView.setImage(profileIcon);

        Image fullNameIcon = new Image(getClass().getResourceAsStream("/images/login/icons/username_field.png"));
        fullNameIconImageView.setImage(fullNameIcon);

        Image passwordIcon = new Image(getClass().getResourceAsStream("/images/login/icons/password_field.png"));
        passwordIconImageView.setImage(passwordIcon);

        Image contactIcon = new Image(getClass().getResourceAsStream("/images/login/icons/contact_field.png"));
        contactIconImageView.setImage(contactIcon);

        Image changeImageIcon = new Image(getClass().getResourceAsStream("/images/login/icons/change_image.png"));
        imageChangeView.setImage(changeImageIcon);

        showPasswordImage = new Image(getClass().getResourceAsStream("/images/login/icons/show_password.png"));
        hidePasswordImage = new Image(getClass().getResourceAsStream("/images/login/icons/hide_password.png"));

    }

    public void onKeyBioCountText(KeyEvent event){
        if (saveButton.isVisible()){
            bioText = bioTextArea.getText();
            countBioLabel.setText(String.valueOf((int) bioText.length()));
            if(bioText.length() >= 280){
                if(bioText.length() > 280){
                    countBioLabel.setStyle("-fx-text-fill: red ");
                }
                bioTextArea.setEditable(false);
                if(event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE){
                    if(bioText.length() <= 280){
                        countBioLabel.setStyle("");
                    }bioTextArea.setEditable(true);
                }
            }else{
                bioTextArea.setEditable(true);

            }
        }else{
            bioTextArea.setEditable(false);
        }
    }



}

