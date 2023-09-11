package cs211.project.componentControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class TeamBox1Controller {

    @FXML private ImageView pointImageView, peopleImageView, roleImageView, activeImageView, faceImageView, bookMarkImageView, manageTeamImageView;
    @FXML private Label numActiveLabel, teamNameLabel;
    @FXML private AnchorPane memberShipAnchorPane, participantsAnchorPane;
    private Image unBookMarkIcon, bookMarkIcon;


    @FXML private void initialize() {

        loadIcon();

        memberShipAnchorPane.setVisible(false);
        participantsAnchorPane.setVisible(false);
    }

    @FXML
    private void onBookMarkClick(){
        if(bookMarkImageView.getImage() == unBookMarkIcon) {
            bookMarkImageView.setImage(bookMarkIcon);
        }else {
        bookMarkImageView.setImage(unBookMarkIcon);
        }
    }
    @FXML
    private void onRoleEntered(MouseEvent event){
        memberShipAnchorPane.setVisible(true);
        participantsAnchorPane.setVisible(false);
    }

    @FXML
    private void onRoleExited(MouseEvent event){
        memberShipAnchorPane.setVisible(false);
    }

    @FXML
    private void onParticipantsEntered(MouseEvent event){
        participantsAnchorPane.setVisible(true);
        memberShipAnchorPane.setVisible(false);
    }

    @FXML
    private void onParticipantsExited(MouseEvent event){
        participantsAnchorPane.setVisible(false);
    }

    private void loadIcon() {
        unBookMarkIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/bookmark/unbookmark_icon.png"));
        bookMarkIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/bookmark/bookmark_icon.png"));
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

        bookMarkImageView.setImage(unBookMarkIcon);


    }

}
