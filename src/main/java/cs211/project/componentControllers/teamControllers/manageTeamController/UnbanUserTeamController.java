package cs211.project.componentControllers.teamControllers.manageTeamController;

import cs211.project.models.Team;
import cs211.project.services.BanTeamMap;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class UnbanUserTeamController {
    @FXML private ListView<String> userListView;

    private Team team;
    private BanTeamMap banTeamMap = new BanTeamMap();
    private HashMap<String, Set<String>> hashMap = banTeamMap.readData();

    @FXML private void onButtonClicked() {

    }

    @FXML private void onBackButtonClicked() throws IOException {
        FXRouter.goTo("team-manage");
    }

    public void setup(Team team) {
        this.team = team;
        showList();
    }

    private void showList(){
        userListView.getItems().clear();
        Set<String> userListID = (hashMap.containsKey(team.getTeamID())) ? hashMap.get(team.getTeamID()) : null;
        if (userListID == null) return;

        // select item in listview
        userListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Selected item: " + newValue);
        });


        for (String userID : userListID) {
            userListView.getItems().add(userID);
        }
    }
}
