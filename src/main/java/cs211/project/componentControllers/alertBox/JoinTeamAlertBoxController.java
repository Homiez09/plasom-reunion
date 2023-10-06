package cs211.project.componentControllers.alertBox;

import cs211.project.models.Event;
import cs211.project.models.Team;
import cs211.project.models.User;
import cs211.project.models.collections.TeamList;
import cs211.project.services.FXRouter;
import cs211.project.services.JoinTeamMap;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.HashMap;

public class JoinTeamAlertBoxController {

    @FXML private AnchorPane correctAnchorPane, wrongAnchorPane, staffButtonAnchorPane;

    @FXML private ImageView correctImageView, wrongImageView, staffImageView;

    @FXML private Button correctOkButtonClick, errorOkButtonClick;

    @FXML private Label notificationLabel;

    private final User user = (User) FXRouter.getData();
    private final Event event = (Event) FXRouter.getData2();
    private final JoinTeamMap joinTeamMap = new JoinTeamMap();
    protected Image correctIcon, wrongIcon, staffIcon;

    private boolean isValidate;
    Team team;


    @FXML private void initialize(){
        loadImageInit();
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    private void loadImageInit(){
        correctIcon = new Image(getClass().getResourceAsStream("/images/icons/alert/correct.png"));
        correctImageView.setImage(correctIcon);

        wrongIcon = new Image(getClass().getResourceAsStream("/images/icons/alert/wrong.png"));
        wrongImageView.setImage(wrongIcon);

        staffIcon = new Image(getClass().getResourceAsStream("/images/icons/join-team/users_group.png"));
        staffImageView.setImage(staffIcon);
    }

    public void validateTeam(){
        if(user.isBanFromTeam(team.getTeamID())){
            notificationLabel.setText("Your access has been restricted (banned).");
        }else if(team.isFull()){
            notificationLabel.setText("The maximum team capacity has been reached.");
        }else if(user.isAlreadyJoinTeam(team.getMemberList())){
            notificationLabel.setText("You have already joined the team.");
        }else if(team.isClose()){
            notificationLabel.setText("This team is no longer accepting.");
        }else{
            joinTeam();
            notificationLabel.setText("You have successfully joined.");
            isValidate = true;
            return;
       }
        isValidate = false;
    }

    private void joinTeam(){
        HashMap<String, TeamList> teamListHashMap = joinTeamMap.readData();
        if (teamListHashMap.containsKey(user.getUsername())) {
            TeamList teamListJoin = new TeamList(teamListHashMap.get(user.getUsername()));
            teamListJoin.addTeam(team);
            teamListJoin.updateRole(team.getTeamID(), "Member");
            teamListHashMap.put(user.getUsername(), teamListJoin);
        } else {
            TeamList teamListJoin = new TeamList();
            teamListJoin.addTeam(team);
            teamListJoin.updateRole(team.getTeamID(), "Member");
            teamListHashMap.put(user.getUsername(), teamListJoin);
        }
        joinTeamMap.writeData(teamListHashMap);
    }

    public void validateButton(){
        if(isValidate){
            correctAnchorPane.setVisible(true);
            wrongAnchorPane.setVisible(false);

            errorOkButtonClick.setVisible(false);
            correctOkButtonClick.setVisible(true);
            staffButtonAnchorPane.setVisible(true);
        }else{
            correctAnchorPane.setVisible(false);
            wrongAnchorPane.setVisible(true);

            errorOkButtonClick.setVisible(true);
            correctOkButtonClick.setVisible(false);
            staffButtonAnchorPane.setVisible(false);
        }
    }


    @FXML void onOkButtonClick() {
        try {
            FXRouter.goTo("join-team",user,event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML void onStaffClick() {
        try {
            FXRouter.goTo("select-team",user,event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
