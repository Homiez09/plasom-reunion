package cs211.project.componentControllers.teamControllers.manageTeamController;

import cs211.project.models.Event;
import cs211.project.models.User;
import cs211.project.models.collections.TeamList;
import cs211.project.services.FXRouter;
import cs211.project.services.JoinTeamMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.HashMap;

public class ManageMemberTeamListController {
    private User user = (User) FXRouter.getData();
    private Event event = (Event) FXRouter.getData2();

    @FXML private ImageView roleImageView, statusImageView, menuImageView;
    @FXML private Label nameLabel, roleLabel, statusLabel, menuLabel;
    @FXML private ComboBox menuComboBox;
    protected Image roleIcon, statusIcon, menuIcon;

    JoinTeamMap joinTeamMap = new JoinTeamMap();
    HashMap<String, TeamList> teamListHashMap;
    TeamList teamList;

    @FXML private void initialize() {
        loadImageInit();
    }

    private void loadImageInit(){
        roleIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/role/Leader.png"));
        statusIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/active_icon.png"));
        menuIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/dot_icon.png"));

        roleImageView.setImage(roleIcon);
        statusImageView.setImage(statusIcon);
        menuImageView.setImage(menuIcon);
    }



}
