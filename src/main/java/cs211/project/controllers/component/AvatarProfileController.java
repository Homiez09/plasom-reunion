package cs211.project.controllers.component;

import cs211.project.models.collections.UserList;
import cs211.project.services.UserListDataSource;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Shape;

public class AvatarProfileController {
    UserListDataSource datasource ;
    UserList userList ;

    @FXML private ImageView avatarProfileImageView;
    @FXML private AnchorPane currentAvatarAnchorPane;
    @FXML private Shape hoverAvatarProfile;

    @FXML private void initialize() {
        datasource = new UserListDataSource("data","user-list.csv");
        userList = datasource.readData();

        currentAvatarAnchorPane.setVisible(false);
        hoverAvatarProfile.setVisible(false);
    }

    @FXML private void onDefaultProfileEntered(){
        hoverAvatarProfile.setVisible(true);
    }

    @FXML private void onDefaultProfileExit(){
        hoverAvatarProfile.setVisible(false);
    }

    public void setImage(Image defaultAvatarProfile) {
        avatarProfileImageView.setImage(defaultAvatarProfile);
    }

    public void currentAvatarCheckBox(Boolean currentImage){
        currentAvatarAnchorPane.setVisible(currentImage);
    }
}
