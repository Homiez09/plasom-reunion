package cs211.project.componentControllers;

import at.favre.lib.crypto.bcrypt.BCrypt;
import cs211.project.models.User;
import cs211.project.models.collections.UserList;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouter;
import cs211.project.services.UserListDataSource;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Modality;

public class ChangePassController {
    @FXML
    Label currentErrorLabel,newErrorLabel,reNewErrorLabel;
    @FXML
    PasswordField currentPasswordField, newPasswordField, reNewPasswordField;
    private Datasource<UserList> userListDatasource;
    private UserList userList;
    private User currentUser;

    @FXML
    public void initialize() {
        this.currentUser = (User) FXRouter.getData();
        this.userListDatasource = new UserListDataSource("data", "user-list.csv");
        this.userList = userListDatasource.readData();
        hideErrorLabel();

        currentPasswordField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            validateCurrentPassword(newValue);
            currentErrorLabel.setVisible(!newValue.isEmpty());
        });

        newPasswordField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            validateNewPassword(newValue);
            newErrorLabel.setVisible(!newValue.isEmpty());

        });

        reNewPasswordField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            validateReNewPassword(newValue);
            reNewErrorLabel.setVisible(!newValue.isEmpty());

        });

    }


    private void validateCurrentPassword(String value) {
        // เช็ครหัสผ่านเดิมที่ผู้ใช้ป้อน
        boolean isValid = checkPassword();
        if (!isValid && !value.isEmpty()) {
            currentErrorLabel.setText("รหัสผ่านปัจจุบันไม่ถูกต้อง");
        } else {
            currentErrorLabel.setText("");
        }
    }

    private void validateNewPassword(String value) {
        // เช็ครหัสผ่านใหม่ที่ผู้ใช้ป้อน
        if (value.length() < 8 && !value.isEmpty()) {
            newErrorLabel.setText("รหัสผ่านต้องมีความยาวอย่างน้อย 8 ตัวอักษร");
        } else {
            newErrorLabel.setText("");
        }
    }

    private void validateReNewPassword(String value) {
        // เช็ครหัสผ่านใหม่อีกครั้งที่ผู้ใช้ป้อน
        if (!value.equals(newPasswordField.getText()) && !value.isEmpty()) {
            reNewErrorLabel.setText("รหัสผ่านใหม่ไม่ตรงกับรหัสผ่านใหม่อีกครั้ง");
        } else {
            reNewErrorLabel.setText("");
        }
    }
    private boolean checkPassword() {
        return BCrypt.verifyer().verify(currentPasswordField.getText().toCharArray(), currentUser.getPassword()).verified;
    }
    private boolean checkNewPassword(){
        String newpass = newPasswordField.getText();
        String renewpass = reNewPasswordField.getText();
    return newpass.equals(renewpass);
    }
    private String setPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    private void setNewPassword(User user) {
        String newpass = newPasswordField.getText();
        user.setPassword(setPassword(newpass));

    }

    private boolean samePassword(){
        if (currentPasswordField.getText().equals(newPasswordField.getText())&& !currentPasswordField.getText().isEmpty()){
            newPasswordField.setText("");
            reNewPasswordField.setText("");
            return false;
        }
        return true;
    }
    public void onCancelAction(ActionEvent actionEvent) {
        currentPasswordField.setText("");
        newPasswordField.setText("");
        reNewPasswordField.setText("");
    }

    public void onChangeAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        if (!samePassword()) {
            if (checkNewPassword()) {
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("ยืนยันการเปลี่ยนรหัสผ่าน");
                confirmationAlert.setHeaderText(null);
                confirmationAlert.setContentText("คุณต้องการเปลี่ยนรหัสผ่านของคุณใช่หรือไม่?");

                // เราจะใช้ lambda expression เพื่อตรวจสอบผลลัพธ์ที่ผู้ใช้เลือก
                confirmationAlert.initModality(Modality.APPLICATION_MODAL);
                confirmationAlert.initOwner(currentPasswordField.getScene().getWindow());

                confirmationAlert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK && checkPassword()) {
                        Alert alertinfo = new Alert(Alert.AlertType.INFORMATION);
                        alertinfo.setContentText("Success");
                        User user = userList.findUserId(currentUser.getUserId());
                        setNewPassword(user);
                        userListDatasource.writeData(userList);
                        alertinfo.showAndWait();
                    }

                });
            }else {
                alert.setTitle("Password not correct");
                alert.setContentText("Try Again");
                alert.showAndWait();
            }
        }else {
            alert.setTitle("Using Same Password");
            alert.setContentText("Try Use New Password");
            alert.showAndWait();
        }
    }
    private void hideErrorLabel(){
        currentErrorLabel.setVisible(false);
        reNewErrorLabel.setVisible(false);
        newErrorLabel.setVisible(false);
    }
}
