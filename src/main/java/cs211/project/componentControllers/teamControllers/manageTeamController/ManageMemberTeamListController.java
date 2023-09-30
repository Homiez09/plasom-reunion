package cs211.project.componentControllers.teamControllers.manageTeamController;

import cs211.project.models.Event;
import cs211.project.models.Team;
import cs211.project.models.User;
import cs211.project.models.collections.TeamList;
import cs211.project.models.collections.UserList;
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
    @FXML private Label nameLabel, roleLabel, statusLabel, menuLabel, userIdLabel;
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

    public void goTo(String page, String teamID) throws IOException {
        switch(page) {
            case "Promote to Leader":
                // todo : manage team
                break;
            case "Leave Team":

            case "Kick":
                kickUser(teamID);
                break;
            case "Ban":
                banUser(teamID);
                break;
        }
        menuComboBox.getSelectionModel().clearSelection();
    }

    public void kickUser(String teamID) {
        HashMap<String, TeamList> teamHashMap = joinTeamMap.readData();
        if (teamHashMap.containsKey(userIdLabel.getText())) {
            teamHashMap.get(userIdLabel.getText()).removeTeam(teamID);
        }joinTeamMap.writeData(teamHashMap);
    }

    public void banUser(String teamID) {
        kickUser(teamID);
    }
}
