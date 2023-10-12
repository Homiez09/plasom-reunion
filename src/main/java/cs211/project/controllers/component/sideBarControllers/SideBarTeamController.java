package cs211.project.controllers.component.sideBarControllers;


import cs211.project.controllers.component.teamControllers.manageTeamController.ManageMemberTeamListController;
import cs211.project.models.Event;
import cs211.project.models.Team;
import cs211.project.models.User;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Shape;

import java.io.IOException;

public class SideBarTeamController {

    @FXML Label activityLabel, teamChatLabel, manageTeamLabel;
    @FXML Shape   hoverActivityShape, hoverTeamChatShape, hoverManageTeamShape;
    @FXML ImageView  activityImageView, teamChatImageView, manageTeamImageView;
    private Image  activityIcon, teamChatIcon, manageTeamIcon, hoverActivityIcon, hoverTeamChatIcon, hoverManageTeamIcon;
    private boolean isManageTeamPage = false, isActivityPage = true, isChatPage = false;
    private final User user = (User) FXRouter.getData();
    private final Event event = (Event) FXRouter.getData2();
    private final Team team = (Team) FXRouter.getData3();

    @FXML
    private void initialize() {
        loadInitImage();
    }

    private void loadInitImage() {
        activityIcon = new Image(getClass().getResourceAsStream("/images/icons/team/side-bar/activity.png"));
        hoverActivityIcon = new Image(getClass().getResourceAsStream("/images/icons/team/side-bar/hover_activity.png"));

        teamChatIcon = new Image(getClass().getResourceAsStream("/images/icons/team/side-bar/team_chat.png"));
        hoverTeamChatIcon = new Image(getClass().getResourceAsStream("/images/icons/team/side-bar/hover_team_chat.png"));

        manageTeamIcon = new Image(getClass().getResourceAsStream("/images/icons/team/side-bar/manage_team.png"));
        hoverManageTeamIcon = new Image(getClass().getResourceAsStream("/images/icons/team/side-bar/hover_manage_team.png"));

        setIconHoverInIt();
    }

    private void setIconHoverInIt() {
        activityImageView.setImage(activityIcon);
        teamChatImageView.setImage(teamChatIcon);
        manageTeamImageView.setImage(manageTeamIcon);

        hoverActivityShape.setVisible(false);
        hoverTeamChatShape.setVisible(false);
        hoverManageTeamShape.setVisible(false);
    }

    @FXML private void onActivityEntered() {
        activityImageView.setImage(hoverActivityIcon);
        hoverActivityShape.setVisible(true);
    }

    @FXML private void onActivityExit() {
        if(isActivityPage){
            activityImageView.setImage(hoverActivityIcon);
            hoverActivityShape.setVisible(true);
        }else{
            activityImageView.setImage(activityIcon);
            hoverActivityShape.setVisible(false);
        }
    }

    @FXML private void onTeamChatEntered() {
        teamChatImageView.setImage(hoverTeamChatIcon);
        hoverTeamChatShape.setVisible(true);
        teamChatLabel.setStyle("-fx-text-fill: #F4AD42");
    }

    @FXML private void onTeamChatExit() {
        if(isChatPage){
            teamChatImageView.setImage(hoverTeamChatIcon);
            hoverTeamChatShape.setVisible(true);
            teamChatLabel.setStyle("-fx-text-fill: #F4AD42");
        }else{
            teamChatImageView.setImage(teamChatIcon);
            hoverTeamChatShape.setVisible(false);
            teamChatLabel.setStyle("");
        }
    }

    @FXML private void onManageTeamEntered() {
        manageTeamImageView.setImage(hoverManageTeamIcon);
        hoverManageTeamShape.setVisible(true);
    }

    @FXML private void onManageTeamExit() {
        if(isManageTeamPage){
            manageTeamImageView.setImage(hoverManageTeamIcon);
            hoverManageTeamShape.setVisible(true);
        }else{
            manageTeamImageView.setImage(manageTeamIcon);
            hoverManageTeamShape.setVisible(false);
        }
    }

    @FXML private void onAllTeamsBackClick(){
        try {
            FXRouter.goTo("all-team");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void setHoverActivity(){
        isManageTeamPage = false;
        isActivityPage = true;
        isChatPage = false;
        activityImageView.setImage(hoverActivityIcon);
        hoverActivityShape.setVisible(true);

        teamChatImageView.setImage(teamChatIcon);
        hoverTeamChatShape.setVisible(false);
        teamChatLabel.setStyle("");

        manageTeamImageView.setImage(manageTeamIcon);
        hoverManageTeamShape.setVisible(false);
    }

    public void setHoverChat(){
        isManageTeamPage = false;
        isActivityPage = false;
        isChatPage = true;

        teamChatImageView.setImage(hoverTeamChatIcon);
        hoverTeamChatShape.setVisible(true);
        teamChatLabel.setStyle("-fx-text-fill: #F4AD42");

        activityImageView.setImage(activityIcon);
        hoverActivityShape.setVisible(false);

        manageTeamImageView.setImage(manageTeamIcon);
        hoverManageTeamShape.setVisible(false);
    }

    public void setHoverManageTeam(){
        isManageTeamPage = true;
        isActivityPage = false;

        manageTeamImageView.setImage(hoverManageTeamIcon);
        hoverManageTeamShape.setVisible(true);

        activityImageView.setImage(activityIcon);
        hoverActivityShape.setVisible(false);

        teamChatImageView.setImage(teamChatIcon);
        hoverTeamChatShape.setVisible(false);
        teamChatLabel.setStyle("");
    }


    @FXML private void onActivityClick(){
        try {
            FXRouter.goTo("team-activity",user, event, team);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML private void onManageTeamClick(){
        try {
            FXRouter.goTo("team-manage",user, event, team);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML private void onTeamChatClick(){
        try {
            FXRouter.goTo("team-chat", user, event, team);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

