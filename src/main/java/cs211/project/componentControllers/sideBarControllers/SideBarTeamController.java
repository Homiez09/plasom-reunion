package cs211.project.componentControllers.sideBarControllers;


import cs211.project.models.Event;
import cs211.project.models.Team;
import cs211.project.models.User;
import cs211.project.models.collections.TeamList;
import cs211.project.services.FXRouter;
import cs211.project.services.JoinTeamMap;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Shape;

import java.io.IOException;
import java.util.HashMap;

public class SideBarTeamController {
    @FXML
    Label dashBoardLabel, homeLabel, activityLabel, teamChatLabel, manageTeamLabel;
    @FXML
    Shape hoverDashBoardShape, hoverHomeShape, hoverActivityShape, hoverTeamChatShape, hoverManageTeamShape;
    @FXML
    ImageView dashBoardImageView, homeImageView, activityImageView, teamChatImageView, manageTeamImageView;
    Image dashBoardIcon, homeIcon, activityIcon, teamChatIcon, manageTeamIcon, hoverDashBoardIcon, hoverHomeIcon, hoverActivityIcon, hoverTeamChatIcon, hoverManageTeamIcon;

    JoinTeamMap joinTeamMap = new JoinTeamMap();
    HashMap<String, TeamList> teamHashMap;

    User user = (User) FXRouter.getData();
    Event event = (Event) FXRouter.getData2();
    Team team = (Team) FXRouter.getData3();


    @FXML
    private void initialize() {
        loadInitImage();
    }


    private void loadInitImage() {
        dashBoardIcon = new Image(getClass().getResourceAsStream("/images/icons/team/side-bar/dashboard.png"));
        hoverDashBoardIcon = new Image(getClass().getResourceAsStream("/images/icons/team/side-bar/hover_dashboard.png"));

        homeIcon = new Image(getClass().getResourceAsStream("/images/icons/team/side-bar/home.png"));
        hoverHomeIcon = new Image(getClass().getResourceAsStream("/images/icons/team/side-bar/hover_home.png"));

        activityIcon = new Image(getClass().getResourceAsStream("/images/icons/team/side-bar/activity.png"));
        hoverActivityIcon = new Image(getClass().getResourceAsStream("/images/icons/team/side-bar/hover_activity.png"));

        teamChatIcon = new Image(getClass().getResourceAsStream("/images/icons/team/side-bar/team_chat.png"));
        hoverTeamChatIcon = new Image(getClass().getResourceAsStream("/images/icons/team/side-bar/hover_team_chat.png"));

        manageTeamIcon = new Image(getClass().getResourceAsStream("/images/icons/team/side-bar/manage_team.png"));
        hoverManageTeamIcon = new Image(getClass().getResourceAsStream("/images/icons/team/side-bar/hover_manage_team.png"));

        setIconHoverInIt();
    }

    private void setIconHoverInIt() {
        dashBoardImageView.setImage(dashBoardIcon);
        homeImageView.setImage(homeIcon);
        activityImageView.setImage(activityIcon);
        teamChatImageView.setImage(teamChatIcon);
        manageTeamImageView.setImage(manageTeamIcon);

        hoverDashBoardShape.setVisible(false);
        hoverHomeShape.setVisible(false);
        hoverActivityShape.setVisible(false);
        hoverTeamChatShape.setVisible(false);
        hoverManageTeamShape.setVisible(false);
    }


    @FXML
    private void onDashBoardEntered() {
        dashBoardImageView.setImage(hoverDashBoardIcon);
        hoverDashBoardShape.setVisible(true);
    }

    @FXML
    private void onDashBoardExit() {
        dashBoardImageView.setImage(dashBoardIcon);
        hoverDashBoardShape.setVisible(false);
    }

    @FXML
    private void onHomeEntered() {
        homeImageView.setImage(hoverHomeIcon);
        hoverHomeShape.setVisible(true);
        homeLabel.setStyle("-fx-text-fill: #F4AD42");
    }

    @FXML
    private void onHomeExit() {
        homeImageView.setImage(homeIcon);
        hoverHomeShape.setVisible(false);
        homeLabel.setStyle("");
    }

    @FXML
    private void onActivityEntered() {
        activityImageView.setImage(hoverActivityIcon);
        hoverActivityShape.setVisible(true);
    }

    @FXML
    private void onActivityExit() {
        activityImageView.setImage(activityIcon);
        hoverActivityShape.setVisible(false);
    }

    @FXML
    private void onTeamChatEntered() {
        teamChatImageView.setImage(hoverTeamChatIcon);
        hoverTeamChatShape.setVisible(true);
        teamChatLabel.setStyle("-fx-text-fill: #F4AD42");
    }

    @FXML
    private void onTeamChatExit() {
        teamChatImageView.setImage(teamChatIcon);
        hoverTeamChatShape.setVisible(false);
        teamChatLabel.setStyle("");
    }

    @FXML
    private void onManageTeamEntered() {
        manageTeamImageView.setImage(hoverManageTeamIcon);
        hoverManageTeamShape.setVisible(true);
    }

    @FXML
    private void onManageTeamExit() {
        manageTeamImageView.setImage(manageTeamIcon);
        hoverManageTeamShape.setVisible(false);
    }

    @FXML private void onAllTeamsBackClick(){
        try {
            FXRouter.goTo("select-team");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML private void onDashBoardClick(){

    }

    @FXML private void onHomeClick(){

    }

    @FXML private void onTeamChatClick(){
        try {
            FXRouter.goTo("team-chat", user, event, team);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML private void onActivityClick(){
        try {
            FXRouter.goTo("team-activity", user, event, team);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML private void onManageTeamClick(){
        try {
            FXRouter.goTo("manage-team", user, event, team);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

