package cs211.project.componentControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TeamBoxController {

    @FXML private ImageView pointImageView, peopleImageView, roleImageView, activeImageView, faceImageView, bookMarkImageView, manageTeamImageView;
    @FXML private Label numActiveLabel, teamNameLabel;


    @FXML private void initialize() {
        loadIcon();
    }

    private void loadIcon() {
        Image bookMarkIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/bookmark_icon.png"));
        bookMarkImageView.setImage(bookMarkIcon);
        Image activeIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/active_icon.png"));
        activeImageView.setImage(activeIcon);
        Image roleIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/role/member.png"));
        roleImageView.setImage(roleIcon);
        Image faceIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/face_icon.png"));
        faceImageView.setImage(faceIcon);
        Image peopleIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/people_icon.png"));
        peopleImageView.setImage(peopleIcon);
        Image manageIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/dot_icon.png"));
        manageTeamImageView.setImage(manageIcon);

    }

}
