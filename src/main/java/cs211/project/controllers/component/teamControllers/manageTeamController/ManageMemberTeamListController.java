package cs211.project.controllers.component.teamControllers.manageTeamController;

import cs211.project.controllers.view.team.ManageTeamController;
import cs211.project.models.collections.TeamList;
import cs211.project.models.collections.UserList;
import cs211.project.services.BanTeamMap;
import cs211.project.services.JoinTeamMap;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ManageMemberTeamListController {

    @FXML private ImageView roleImageView, statusImageView, menuImageView;
    @FXML private Label userIdLabel;
    @FXML private ComboBox menuComboBox;
    private final BanTeamMap banHashMap = new BanTeamMap();
    private final JoinTeamMap joinTeamMap = new JoinTeamMap();
    private ManageTeamComponentController manageTeamComponentController;
    private ManageTeamController manageTeamController;

    @FXML private void initialize() {
        loadImageInit();
    }

    private void loadImageInit(){
        Image roleIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/role/Leader.png"));
        Image statusIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/active_icon.png"));
        Image menuIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/dot_icon.png"));

        roleImageView.setImage(roleIcon);
        statusImageView.setImage(statusIcon);
        menuImageView.setImage(menuIcon);
    }

    public void goTo(String page, String teamID, String userID) throws IOException {
        switch (page) {
            case "Promote to Leader" -> promoteToLeader(teamID, userID);
            case "Promote to Member" -> promoteToMember(teamID, userID);
            case "Leave Team", "Kick" -> kickUser(teamID);
            case "Ban" -> banUser(teamID, userID);
        }
        menuComboBox.getSelectionModel().clearSelection();
        reload(teamID);
    }

    public void goTo(String page, String teamID) throws IOException {
        goTo(page, teamID, null);
    }

    public void promoteToLeader(String teamID, String userID){
        HashMap<String, UserList> userHashMap = joinTeamMap.roleReadData();
        userHashMap.get(teamID).promoteToLeader(userID);
        joinTeamMap.roleWriteData(userHashMap);
    }

    public void promoteToMember(String teamID, String userID){
        HashMap<String, UserList> userHashMap = joinTeamMap.roleReadData();
        userHashMap.get(teamID).promoteToMember(userID);
        joinTeamMap.roleWriteData(userHashMap);
    }

    public void kickUser(String teamID) {
        HashMap<String, TeamList> teamHashMap = joinTeamMap.readData();
        if (teamHashMap.containsKey(userIdLabel.getText())) {
            teamHashMap.get(userIdLabel.getText()).removeTeam(teamID);
        }joinTeamMap.writeData(teamHashMap);
    }

    public void banUser(String teamID, String userID){
        HashMap<String, Set<String>> banTeamHashMap = banHashMap.readData();
        Set<String> set;

        if (!banTeamHashMap.containsKey(teamID)) {
            set = new HashSet<>();
        } else {
            set = banTeamHashMap.get(teamID);
        }
        set.add(userID);

        if (!banTeamHashMap.containsKey(teamID)) banTeamHashMap.put(teamID, set);
        banHashMap.writeData(banTeamHashMap);
        kickUser(teamID);
    }

    public void setManageTeamController(ManageTeamComponentController manageTeamComponentController) {
        this.manageTeamComponentController = manageTeamComponentController;
    }

    public void setManageTeamController(ManageTeamController manageTeamController) {
        this.manageTeamController = manageTeamController;
    }

    private void reload(String teamID) {
        if (manageTeamComponentController != null) {
            manageTeamComponentController.reloadDataHashMap();
            manageTeamComponentController.showUserList(teamID);
        } else {
            manageTeamController.reloadDataHashMap();
            manageTeamController.showUserList(teamID);
        }
    }
}
