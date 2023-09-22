package cs211.project.componentControllers.teamControllers.menu;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;


public class LeaderMenuController {

    @FXML private AnchorPane bannedMenu, kickOutMenu, leaderMenu, memberMenu, roleMenu, viewProfileMenu, roleMenuAnchorPane, menuAnchorPane;
    @FXML private Rectangle hoverBanned, hoverKickOut, hoverLeader, hoverMember, hoverRole, hoverViewProfile;
    @FXML private CheckBox leaderRoleCheckBox, memberRoleCheckBox;
    @FXML private ImageView leaderImageView, memberImageView;


    @FXML private void initialize() {
        loadImageInit();
    }

    private void loadImageInit(){
        Image leaderIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/role/Leader.png"));
        Image memberIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/role/Member.png"));

        leaderImageView.setImage(leaderIcon);
        memberImageView.setImage(memberIcon);
    }
}
