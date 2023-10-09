package cs211.project.componentControllers.teamControllers.manageTeamController;

import cs211.project.models.Team;
import cs211.project.models.User;
import cs211.project.services.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class UnbanUserTeamController {
    @FXML private ListView<HBox> userListView;
    @FXML private Button unbanButton;

    private Team team;
    private BanTeamMap banTeamMap = new BanTeamMap();
    private UserListDataSource userListDataSource = new UserListDataSource("data", "user-list.csv");
    private HashMap<String, User> userIdHashMap = userListDataSource.readData().userIdHashMap();
    private HashMap<String, Set<String>> hashMap = banTeamMap.readData();
    Set<String> userListID;
    private String userSelected;

    @FXML private void initialize() {
        checkSelected();
    }

    @FXML private void onButtonClicked() {
        userListID.remove(userSelected);
        banTeamMap.writeData(hashMap);
        showList();
    }

    @FXML private void onBackButtonClicked() throws IOException {
        FXRouter.goTo("team-manage");
    }

    public void setup(Team team) {
        this.team = team;
        showList();
    }

    private void checkSelected(){
        if (userSelected == null) {
            unbanButton.setDisable(true);
        } else {
            unbanButton.setDisable(false);
        }
    }

    private void showList(){
        checkSelected();
        userListView.getItems().clear();
        userListID = (hashMap.containsKey(team.getTeamID())) ? hashMap.get(team.getTeamID()) : null;
        if (userListID == null) return;

        userListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String userID = (String) newValue.getProperties().get("userID");
            userSelected = userID;
            checkSelected();
        });

        for (String userID : userListID) {
            User user = userIdHashMap.get(userID);

            HBox hBox = new HBox();
            ImageView imageView = new ImageView();
            Label label = new Label();

            label.setText(user.getUsername());

            ImagePathFormat path = new ImagePathFormat(user.getImagePath());
            imageView.setImage(new Image(path.toString(), 50, 50, false, false));
            hBox.setStyle("-fx-spacing: 10; -fx-alignment: center;");
            hBox.getProperties().put("userID", userID);
            hBox.getChildren().addAll(imageView, label);

            userListView.getItems().add(hBox);
        }
    }
}
