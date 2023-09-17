package cs211.project.componentControllers;

import cs211.project.models.collections.UserList;
import cs211.project.services.UserListDataSource;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Shape;

import java.io.IOException;

public class AvatarProfileController {
    UserListDataSource datasource ;
    UserList userList ;

    @FXML private ImageView avatarProfileImageView;
    @FXML protected AnchorPane currentAvatarAnchorPane;
    @FXML private Shape hoverAvatarProfile;

    @FXML private void initialize() throws IOException {
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
