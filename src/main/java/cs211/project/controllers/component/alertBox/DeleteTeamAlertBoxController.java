package cs211.project.controllers.component.alertBox;

import cs211.project.controllers.view.team.ManageTeamController;
import cs211.project.models.Event;
import cs211.project.models.Team;
import cs211.project.models.User;
import cs211.project.models.collections.TeamList;
import cs211.project.models.collections.UserList;
import cs211.project.services.FXRouter;
import cs211.project.services.JoinTeamMap;
import cs211.project.services.TeamListDataSource;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.HashMap;

public class DeleteTeamAlertBoxController {
    @FXML private ImageView notificationImageView;
    private final User user = (User) FXRouter.getData();
    private final Event event = (Event) FXRouter.getData2();
    private ManageTeamController manageTeamController;
    private final TeamListDataSource teamListDataSource = new TeamListDataSource("data", "team-list.csv");
    private final TeamList teamList = teamListDataSource.readData();
    private final JoinTeamMap joinTeamMap = new JoinTeamMap();
    private HashMap<String, UserList> hashMap;
    private Team team;

    @FXML private void initialize() {
        hashMap = joinTeamMap.roleReadData();
        notificationImageView.setImage(new Image(getClass().getResourceAsStream("/images/icons/alert/question.png")));
    }

    @FXML public void onBackButtonClick(){
        manageTeamController.closeAlertBox();
    }

    @FXML public void onDeleteButtonClick(){
        try {
            teamList.removeTeam(this.team.getTeamID());
            hashMap.remove(this.team.getTeamID());
            teamListDataSource.writeData(teamList);
            joinTeamMap.roleWriteData(hashMap);

            FXRouter.goTo("select-team", user, event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setManageTeamController(ManageTeamController manageTeamController) {
        this.manageTeamController = manageTeamController;
    }
}
