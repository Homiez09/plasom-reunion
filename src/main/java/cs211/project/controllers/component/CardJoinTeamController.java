package cs211.project.controllers.component;

import cs211.project.controllers.view.team.JoinTeamController;
import cs211.project.models.Team;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardJoinTeamController {

    @FXML private Label teamNameLabel, descriptionLabel, startDateLabel, endDateLabel, numberOfParticipantsLabel, maxParticipantsCapacityLabel;
    @FXML private ImageView joinTeamImageView, calendarImageView, participantsImageView;
    private Team team;
    private JoinTeamController joinTeamController;

    @FXML private void initialize(){
        loadImageInit();
    }

    public void initializeData() {
        teamNameLabel.setText(team.getTeamName());
        descriptionLabel.setText(team.getTeamDescription());
        startDateLabel.setText(team.getStartDateTime());
        endDateLabel.setText(team.getEndDateTime());
        numberOfParticipantsLabel.setText(String.valueOf(team.getMemberList().getUsers().size()));
        maxParticipantsCapacityLabel.setText(String.valueOf(team.getMaxSlotTeamMember()));
    }

    private void loadImageInit(){
        Image joinTeamIcon = new Image(getClass().getResourceAsStream("/images/icons/join-team/users_group.png"));
        joinTeamImageView.setImage(joinTeamIcon);

        Image calendarIcon = new Image(getClass().getResourceAsStream("/images/icons/join-team/calendar.png"));
        calendarImageView.setImage(calendarIcon);

        Image participantsIcon = new Image(getClass().getResourceAsStream("/images/icons/join-team/users_group_orange.png"));
        participantsImageView.setImage(participantsIcon);
    }

    public void setJoinTeamController(JoinTeamController joinTeamController) {
        this.joinTeamController = joinTeamController;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @FXML public void onJoinTeamClick() {
        joinTeamController.loadAlertBox(team);

    }
}

