package cs211.project.componentControllers;

import at.favre.lib.crypto.bcrypt.BCrypt;
import cs211.project.models.User;
import cs211.project.models.collections.UserList;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouter;
import cs211.project.services.UserListDataSource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.stage.Modality;

public class ChangePassController {
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
        System.out.println(currentUser.getUsername());


    }

    private boolean checkPassword() {
        return BCrypt.verifyer().verify(currentPasswordField.getText().toCharArray(), currentUser.getPassword()).verified;
    }

    private String setPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    private void setNewPassword() {
        String newpass = newPasswordField.getText();
        String renewpass = reNewPasswordField.getText();
        if (newpass.equals(renewpass)) {
            currentUser.setPassword(setPassword(newpass));
        }
    }

    private boolean samePass(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Using Same Password");
        alert.setContentText("Try Use New Password");
        if (currentPasswordField.getText().equals(newPasswordField)){
            alert.showAndWait();
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
        if (samePass()){
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("ยืนยันการเปลี่ยนรหัสผ่าน");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("คุณต้องการเปลี่ยนรหัสผ่านของคุณใช่หรือไม่?");

            // เราจะใช้ lambda expression เพื่อตรวจสอบผลลัพธ์ที่ผู้ใช้เลือก
            confirmationAlert.initModality(Modality.APPLICATION_MODAL);
            confirmationAlert.initOwner(currentPasswordField.getScene().getWindow());

            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK && checkPassword()) {
                    // ผู้ใช้เลือก "ใช่" และรหัสผ่านถูกต้อง
                    // ทำการเปลี่ยนรหัสผ่านและบันทึกข้อมูลที่นี่
                    setNewPassword();
                    userList.findUserId(currentUser.getUserId());
                    userListDatasource.writeData(userList);
                }
            });
        }
    }
}
