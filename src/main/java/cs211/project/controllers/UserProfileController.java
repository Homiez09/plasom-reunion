package cs211.project.controllers;

import cs211.project.componentControllers.AvatarProfileController;
import cs211.project.models.User;
import cs211.project.models.collections.UserList;
import cs211.project.services.CreateProfileCircle;
import cs211.project.services.FXRouter;
import cs211.project.services.LoadNavbarComponent;
import cs211.project.services.UserListDataSource;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class UserProfileController {
    @FXML private ImageView userUploadAvatarImageView, deviceProfileButtonImageView, contactIconProfileImageView, avatarChangeView, ellipseIconImageView, frameIconImageView, avatarProfileImageView, visiblePasswordImageView, displayNameIconImageView, contactIconImageView, passwordIconImageView, defaultAvatarImageView, deviceAvatarImageView;
    @FXML private AnchorPane hoverShowContactAnchorPane, hoverAvatarChangeAnchorPane, navbarAnchorPane, changeAvatarAnchorPane, defaultAvatarAnchorPane, deviceAvatarAnchorPane;
    @FXML private TextField displayNameTextField, showPasswordTextField, contactNumberTextField;
    @FXML private TextArea bioTextArea;
    @FXML private PasswordField passwordField;
    @FXML private Button editButton, cancelButton, saveButton;

    @FXML private Label idLabel, usernameLabel, passwordLabel, countBioLabel, maximumCountBioLabel, defaultAvatarLabel, deviceAvatarLabel;
    @FXML private Label idProfileLabel, usernameProfileLabel, displayNameProfileLabel, contactProfileLabel, bioProfileLabel;
    @FXML private Label  displayNameReq, contactNumberReq, passwordReq;

    @FXML private GridPane defaultAvatarContainer;
    @FXML private Shape defaultAvatarHover, deviceAvatarHover, defaultAvatarLine, deviceAvatarLine;
    @FXML private CheckBox contactCheckBox;

    private Image  deviceAvatarButtonIcon, hoverDeviceAvatarButtonIcon, avatarIcon, hoverAvatarIcon, deviceAvatarIcon, hoverDeviceAvatarIcon, clickAvatarIcon, clickDeviceAvatarIcon;
    private Image  showPasswordImage, hidePasswordImage;
    private String displayName, password, contactNumber, bioText, previousDisplayName, previousContactNumber , previousBioText;
    private Boolean displayNameRequirement = false ,isValid = false, isValidContactNumber = false;

    protected String userId ,username, previousBioCount, imagePath;
    protected int row = 0, column = 0;

    private final int MAX_PASSWORD_LIMIT = 27,  MAX_DISPLAY_NAME_LIMIT = 24, MAX_CONTACT_LIMIT = 10, MAX_BIO_LIMIT = 300;
    private final User user = (User) FXRouter.getData();

    UserListDataSource datasource ;
    UserList userList ;

    @FXML
    private void initialize() throws IOException {
        new LoadNavbarComponent(user, navbarAnchorPane);

        datasource = new UserListDataSource("data","user-list.csv");
        userList = datasource.readData();

        userDataInit();

        maximumLengthField();
        setRequirementLabel();
        showFocusRequirement();

        loadImage();

        loadPasswordFieldAndButtonProfile();
        loadIconImageProfile();

        changeAvatarPopUp();
        checkCurrentTab();

        eventHandleEnter();

        showContact();

    }

    private void userDataInit(){
        username = user.getUsername();
        displayName = user.getDisplayName();
        bioText = user.getBio();
        userId = user.getUserId();
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
        contactProfileLabel.setText(contactNumber);
    }

    private void eventHandleEnter(){
        EventHandler<KeyEvent> enterEventHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    onSaveButtonClick();
                }
            }
        };
        displayNameTextField.setOnKeyPressed(enterEventHandler);
        contactNumberTextField.setOnKeyPressed(enterEventHandler);
        bioTextArea.setOnKeyPressed(enterEventHandler);
        passwordField.setOnKeyPressed(enterEventHandler);
        showPasswordTextField.setOnKeyPressed(enterEventHandler);
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
    public void setRequirementLabel(){
        displayNameReq.setVisible(false);
        contactNumberReq.setVisible(false);
        passwordReq.setVisible(false);
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
        displayNameIconImageView.setVisible(false);
        contactIconImageView.setVisible(false);
        avatarChangeView.setVisible(false);
    }
    @FXML private void loadIconImageEditProfile(){
        visiblePasswordImageView.setVisible(true);
        visiblePasswordImageView.setImage(hidePasswordImage);
        passwordIconImageView.setVisible(true);
        displayNameIconImageView.setVisible(true);
        contactIconImageView.setVisible(true);
        avatarChangeView.setVisible(true);
    }

    @FXML private void onContactEntered(){
        if(contactCheckBox.isSelected()){
            hoverShowContactAnchorPane.setVisible(true);
            user.setShowContact(true);
        }else{
            hoverShowContactAnchorPane.setVisible(false);
            user.setShowContact(false);
        }
    }
    @FXML private void onContactExited(){
        hoverShowContactAnchorPane.setVisible(false);
    }

    @FXML private void onDefaultAvatarEntered(){
        setDefaultAvatarHover("true");
        checkCurrentTab();
    }
    @FXML private void onDefaultAvatarExited(){
        setDefaultAvatarHover("false");
        checkCurrentTab();
    }

    @FXML private void onDeviceAvatarEntered(){
        checkCurrentTab();
        setDeviceAvatarHover("true");
    }
    @FXML private void onDeviceAvatarExited(){
        checkCurrentTab();
        setDeviceAvatarHover("false");
    }

    @FXML private void onHoverAvatarChangeEntered(){
        hoverAvatarChangeAnchorPane.setVisible(avatarChangeView.isVisible());
    }
    @FXML private void onHoverAvatarChangeExited(){
        hoverAvatarChangeAnchorPane.setVisible(false);
    }

    @FXML private void onUploadAvatarEntered(){
        deviceProfileButtonImageView.setImage(hoverDeviceAvatarButtonIcon);
    }
    @FXML private void onUploadAvatarExited(){
        deviceProfileButtonImageView.setImage(deviceAvatarButtonIcon);
    }

    @FXML private void onContactCheckBox(){
        user.setShowContact(contactCheckBox.isSelected());
        userList.updateUserShowContact(user.getUsername(), user.isShowContact());
        datasource.writeData(userList);
    }
    @FXML private void onBackClick(){
        changeAvatarAnchorPane.setVisible(false);
    }
    @FXML private void onAvatarChangeViewClick(){
        changeAvatarAnchorPane.setVisible(true);
    }
    @FXML private void onDefaultAvatarClick(){
        deviceAvatarAnchorPane.setVisible(false);
        defaultAvatarAnchorPane.setVisible(true);

        deviceAvatarLine.setVisible(false);
        deviceAvatarImageView.setImage(deviceAvatarIcon);
        deviceAvatarLabel.setStyle("-fx-text-fill: '' ");

        checkCurrentTab();
        onShowDefaultAvatar();
    }
    @FXML private void onDeviceAvatarClick(){
        deviceAvatarAnchorPane.setVisible(true);
        defaultAvatarAnchorPane.setVisible(false);

        defaultAvatarLine.setVisible(false);
        defaultAvatarImageView.setImage(avatarIcon);
        defaultAvatarLabel.setStyle("-fx-text-fill: '' ");

        checkCurrentTab();
        onShowDeviceAvatar();
    }
    @FXML private void onEditProfileButtonClick() {
        hoverAvatarChangeAnchorPane.setDisable(false);

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
        defaultAvatarContainerProfile();
        contactCheckBox.setVisible(false);

    }
    @FXML private void onCancelButtonClick() {
        contactCheckBox.setVisible(true);
        hoverAvatarChangeAnchorPane.setDisable(true);
        loadPasswordFieldAndButtonProfile();
        loadIconImageProfile();

        passwordField.setText("");
        showPasswordTextField.setText("");
        password = null;

        displayNameTextField.setText(previousDisplayName);
        contactNumberTextField.setText(previousContactNumber);
        bioTextArea.setText(previousBioText);

        displayNameProfileLabel.setText(previousDisplayName);
        bioProfileLabel.setText(previousBioText);

        user.setNewImagePath(null);

        try {
            File destDir = new File("images/user-avatars");
            if (destDir.exists() && destDir.isDirectory()) {
                File[] files = destDir.listFiles();
                if (files != null) {
                    for (File existingImage : files) {
                        if (existingImage.isFile() && existingImage.getName().startsWith("temp_" + user.getUserId() + "_" + user.getUsername() + ".")) {
                            existingImage.delete();
                        }
                    }
                }
            }
            FXRouter.goTo("user-profile");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML private void onVisiblePasswordClick() {
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
    @FXML public void onSaveButtonClick() {
        validateData();
        if (isValid) {
            contactCheckBox.setVisible(true);

            loadPasswordFieldAndButtonProfile();
            loadIconImageProfile();

            imagePath = user.getNewImagePath();
            if (imagePath == null) {
                imagePath = user.getImagePath();
            } else {
                if (imagePath.startsWith("/")) {
                    imagePath = imagePath.substring(1);
                }
            }

            File directory = new File("images/user-avatars");

            if (directory.exists() && directory.isDirectory() && user.getNewImagePath() != null) {
                File[] files = directory.listFiles();

                for (File file : files) {
                    String newName = file.getName();
                    if (newName.startsWith(user.getUserId())) {
                        File newFile = new File(directory, newName);
                        if (newFile.exists()) {
                            newFile.delete();
                        }
                    } else if (newName.startsWith("temp_")){
                        newName = newName.replaceFirst("temp_", "");
                        File newFile = new File(directory, newName);
                        file.renameTo(newFile);
                    }
                }
            }

            try {
                File destDir = new File("images/user-avatars");
                if (destDir.exists() && destDir.isDirectory()) {
                    File[] existingImages = destDir.listFiles();
                    if (existingImages != null) {
                        for (File existingImage : existingImages) {
                            if (existingImage.isFile() && existingImage.getName().startsWith("temp_" + user.getUserId() + "_" + user.getUsername() + ".")) {
                                existingImage.delete();
                            }
                        }
                    }
                }
                FXRouter.goTo("user-profile");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            user.setImagePath(imagePath);

            userList.updateUserProfile(user.getUsername(), displayName, contactNumber, bioText, imagePath);
            User userUpdate = userList.findUsername(user.getUsername());

            password = null;

            datasource.writeData(userList);
            new LoadNavbarComponent(userUpdate, navbarAnchorPane);
            try {
                hoverAvatarChangeAnchorPane.setDisable(true);
                FXRouter.goTo("user-profile", userUpdate);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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

    @FXML private void onUploadAvatarButton(ActionEvent avatar) {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images (PNG, JPG, GIF)", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        Node source = (Node) avatar.getSource();
        File file = chooser.showOpenDialog(source.getScene().getWindow());
        if (file != null) {
            try {
                File destDir = new File("images/user-avatars");
                if (!destDir.exists()) {
                    destDir.mkdirs();
                }
                String[] fileSplit = file.getName().split("\\.");
                String extension = fileSplit[fileSplit.length - 1];
                String filename = "temp_" + user.getUserId() + "_" + user.getUsername() + "." + extension;
                String path = "images/user-avatars/" + filename.substring(5);

                File[] existingImages = destDir.listFiles();
                if (existingImages != null) {
                    for (File existingImage : existingImages) {
                        if (existingImage.isFile() && existingImage.getName().startsWith("temp_" + user.getUserId() + "_" + user.getUsername() + ".")) {
                            existingImage.delete();
                        }
                    }
                }
                Path target = FileSystems.getDefault().getPath(destDir.getAbsolutePath() + System.getProperty("file.separator") + filename);

                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
                Image avatarImage = new Image(target.toUri().toString(), 1280, 1280, false, false);

                userUploadAvatarImageView.setImage(avatarImage);
                avatarProfileImageView.setImage(avatarImage);
                user.setNewImagePath(path);

                datasource.writeData(userList);
            } catch (IOException e) {
                e.printStackTrace();
            }
            defaultAvatarContainerProfile();
        }
    }

    @FXML private void onKeyHidePassword() {
        password = passwordField.getText();
        showPasswordTextField.setText(password);
    }
    @FXML private void onKeyShowPassword() {
        password = showPasswordTextField.getText();
        passwordField.setText(password);
    }
    @FXML private void onKeyDisplayName(){
        checkDisplayNameReq();
    }
    @FXML private void onKeyContactNumber(){
        checkContactNumberReq();
    }
    @FXML private void onKeyBioCountText(){
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

    @FXML private void onHandleDragOver(DragEvent dragEvent) {
        if (dragEvent.getDragboard().hasFiles()) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }
    @FXML private void onHandleDrop(DragEvent dragEvent) throws FileNotFoundException {
        List<File> files = dragEvent.getDragboard().getFiles();
        if (!files.isEmpty()) {
            File file = files.get(0);
            String fileName = file.getName();
            if (fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".gif")) {
                try {
                    File destDir = new File("images/user-avatars");
                    if (!destDir.exists()) {destDir.mkdirs();
                    }
                    String[] fileSplit = fileName.split("\\.");
                    String filename = "temp_" + user.getUserId() + "_" + user.getUsername() + "." + fileSplit[fileSplit.length - 1];
                    String path = "images/user-avatars/" + filename.substring(5);
                    File[] existingImages = destDir.listFiles();
                    if (existingImages != null) {
                        for (File existingImage : existingImages) {
                            if (existingImage.isFile() && existingImage.getName().startsWith("temp_" + user.getUserId() + "_" + user.getUsername() + ".")) {
                                existingImage.delete();
                            }
                        }
                    }
                    Path target = FileSystems.getDefault().getPath(destDir.getAbsolutePath() + System.getProperty("file.separator") + filename);
                    Files.deleteIfExists(target);
                    Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);

                    Image avatarImage = new Image(target.toUri().toString(), 1280, 1280, false, false);

                    userUploadAvatarImageView.setImage(avatarImage);
                    avatarProfileImageView.setImage(avatarImage);

                    user.setNewImagePath(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        dragEvent.setDropCompleted(true);
        dragEvent.consume();
        defaultAvatarContainerProfile();

    }


    private void loadImage() {
        Image contactIconProfile = new Image(getClass().getResourceAsStream("/images/icons/user-profile/iconProfile.png"));
        contactIconProfileImageView.setImage(contactIconProfile);

        Image ellipseIcon = new Image(getClass().getResourceAsStream("/images/icons/user-profile/iconEllipse.png"));
        ellipseIconImageView.setImage(ellipseIcon);

        Image frameIcon = new Image(getClass().getResourceAsStream("/images/icons/user-profile/iconFrame.png"));
        frameIconImageView.setImage(frameIcon);


        boolean defaultImageCheck = false;
        String path = user.getNewImagePath() == null ? user.getImagePath() : user.getNewImagePath();
        if (path.startsWith("x")) {
            path = path.substring(1);
            defaultImageCheck = true;
        }

        Image profileIcon = new Image((defaultImageCheck) ? getClass().getResource(path).toString() : "file:" + path, 1280, 1280, false, false);
        avatarProfileImageView.setImage(profileIcon);
        userUploadAvatarImageView.setImage(profileIcon);
        new CreateProfileCircle(avatarProfileImageView, 71);
        new CreateProfileCircle(userUploadAvatarImageView, 71);


        Image displayNameIcon = new Image(getClass().getResourceAsStream("/images/icons/login/username_field.png"));
        displayNameIconImageView.setImage(displayNameIcon);

        Image passwordIcon = new Image(getClass().getResourceAsStream("/images/icons/login/password_field.png"));
        passwordIconImageView.setImage(passwordIcon);

        Image contactIcon = new Image(getClass().getResourceAsStream("/images/icons/login/contact_field.png"));
        contactIconImageView.setImage(contactIcon);

        Image changeImageIcon = new Image(getClass().getResourceAsStream("/images/icons/login/change_image.png"));
        avatarChangeView.setImage(changeImageIcon);


        deviceAvatarButtonIcon = new Image(getClass().getResourceAsStream("/images/icons/user-profile/device/white_device.png"));
        hoverDeviceAvatarButtonIcon = new Image(getClass().getResourceAsStream("/images/icons/user-profile/device/hover_action_device.png"));
        deviceProfileButtonImageView.setImage(deviceAvatarButtonIcon);

        avatarIcon = new Image(getClass().getResourceAsStream("/images/icons/user-profile/avatar/avatar.png"));
        hoverAvatarIcon = new Image(getClass().getResourceAsStream("/images/icons/user-profile/avatar/hover_avatar.png"));
        clickAvatarIcon = new Image(getClass().getResourceAsStream("/images/icons/user-profile/avatar/click_avatar.png"));

        deviceAvatarIcon = new Image(getClass().getResourceAsStream("/images/icons/user-profile/device/device.png"));
        hoverDeviceAvatarIcon = new Image(getClass().getResourceAsStream("/images/icons/user-profile/device/hover_device.png"));
        clickDeviceAvatarIcon = new Image(getClass().getResourceAsStream("/images/icons/user-profile/device/click_device.png"));

        showPasswordImage = new Image(getClass().getResourceAsStream("/images/icons/login/show_password.png"));
        hidePasswordImage = new Image(getClass().getResourceAsStream("/images/icons/login/hide_password.png"));


    }
    private void changeAvatarPopUp(){
        defaultAvatarContainerProfile();

        setDefaultAvatarHover("false");
        setDeviceAvatarHover("false");

        changeAvatarAnchorPane.setVisible(false);
        hoverAvatarChangeAnchorPane.setDisable(true);
        hoverAvatarChangeAnchorPane.setVisible(false);
        hoverShowContactAnchorPane.setVisible(false);
    }
    private void checkCurrentTab(){
        if(defaultAvatarAnchorPane.isVisible()){
            onShowDefaultAvatar();
        }else{
            onShowDeviceAvatar();
        }
    }
    private void defaultAvatarContainerProfile() {
        for (int i = 0; i < 10; i++) {
            String defaultAvatarProfilePath = "/images/profile/default-avatar/default" + i + ".png";
            Image defaultAvatarProfile = new Image(getClass().getResourceAsStream(defaultAvatarProfilePath), 1280, 1280, false, false);
            FXMLLoader avatarProfileLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/avatar-profile.fxml"));
            try {
                AnchorPane avatarProfileComponent = avatarProfileLoader.load();
                AvatarProfileController avatarProfileController = avatarProfileLoader.getController();
                avatarProfileController.setImage(defaultAvatarProfile);
                avatarProfileComponent.setOnMouseClicked(event -> {
                    user.setNewImagePath("x" + defaultAvatarProfilePath);
                    avatarProfileImageView.setImage(new Image(getClass().getResourceAsStream(defaultAvatarProfilePath.toString()),1280, 1280, false, false));
                    defaultAvatarContainerProfile();
                    loadImage();
                });

                AnchorPane selectImageAnchorPane = (AnchorPane) avatarProfileComponent.getChildren().get(2);
                Label avatarIdLabel = (Label) selectImageAnchorPane.getChildren().get(3);
                avatarIdLabel.setText(String.valueOf(i));

                if(user.getNewImagePath() == null && user.getImagePath().equals("x" + defaultAvatarProfilePath)){
                    avatarProfileController.currentAvatarCheckBox(true);
                }else avatarProfileController.currentAvatarCheckBox(user.getNewImagePath() != null && user.getNewImagePath().equals("x" + defaultAvatarProfilePath));

                if (column == 5) {
                    column = 0;
                    row++;
                }
                GridPane.setMargin(avatarProfileComponent, new Insets(20, 17, 20, 5));
                defaultAvatarContainer.add(avatarProfileComponent, ++column, row);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        row = 0;
        column = 0;
    }
    private void setDefaultAvatarHover(String hover){
        if (hover.equals("false")) {
            defaultAvatarHover.setVisible(false);
            defaultAvatarLine.setVisible(false);
            defaultAvatarLabel.setStyle("");
            defaultAvatarImageView.setImage(avatarIcon);
        } else {
            defaultAvatarHover.setVisible(true);
            defaultAvatarLabel.setStyle("-fx-text-fill: #6B6B6B");
            defaultAvatarImageView.setImage(hoverAvatarIcon);
        }
    }
    private void setDeviceAvatarHover(String hover){
        if (hover.equals("false")) {
            deviceAvatarHover.setVisible(false);
            deviceAvatarLine.setVisible(false);
            deviceAvatarLabel.setStyle("");
            deviceAvatarImageView.setImage(deviceAvatarIcon);
        }else{
            deviceAvatarHover.setVisible(true);
            deviceAvatarLabel.setStyle("-fx-text-fill: #6B6B6B");
            deviceAvatarImageView.setImage(hoverDeviceAvatarIcon);
        }
    }
    private void onShowDefaultAvatar(){
        defaultAvatarLine.setVisible(true);
        defaultAvatarImageView.setImage(clickAvatarIcon);
        defaultAvatarLabel.setStyle("-fx-text-fill: #F90");
    }
    private void onShowDeviceAvatar(){
        deviceAvatarLine.setVisible(true);
        deviceAvatarImageView.setImage(clickDeviceAvatarIcon);
        deviceAvatarLabel.setStyle("-fx-text-fill: #F90");
    }

    private void showContact(){
        if(user.isShowContact()){
            if(user.getContactNumber().isEmpty()){
                contactCheckBox.setDisable(true);
                contactCheckBox.setSelected(false);
            }else{
                contactCheckBox.setDisable(false);
                contactCheckBox.setSelected(true);
                contactProfileLabel.setText(user.getContactNumber());
            }
        }else{
            contactCheckBox.setSelected(false);
        }
    }
    private void validateData(){
        checkDisplayNameReq();
        checkContactNumberReq();
        isValid = displayNameRequirement && isValidContactNumber && bioTextArea.getText().length() <= 280 && checkValidatePassword();
    }
    private boolean checkValidatePassword(){
        if(password != null){
            return user.validatePassword(password);
        }else {
            return false;
        }
    }
    private void checkDisplayNameReq(){
        displayNameRequirement = false;
        displayName = displayNameTextField.getText();

        if(!displayName.isEmpty() && ((userList.findDisplayName(displayName) != null && user.isDisplayName(displayName)) || (userList.findDisplayName(displayName) == null) || user.isDisplayName(displayName))){
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
            }else if (userList.findDisplayName(displayName) != null && !user.isDisplayName(displayName)){
                displayNameReq.setText("Duplicate display name. Please use another display name.");
            }else {
                displayNameReq.setVisible(false);
            }
        }
    }
    private void checkContactNumberReq(){
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

    private String setColorTextFill(String color){
        switch (color) {
            case "black" -> color = "-fx-text-fill: #413b3b";
            case "red" -> color = "-fx-text-fill: red";
        }
        return color;
    }

}

