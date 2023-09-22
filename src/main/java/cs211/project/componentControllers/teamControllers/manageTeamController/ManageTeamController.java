package cs211.project.componentControllers.teamControllers.manageTeamController;

import cs211.project.models.Event;
import cs211.project.models.User;
import cs211.project.models.collections.UserList;
import cs211.project.services.FXRouter;
import cs211.project.services.JoinTeamMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.HashMap;

public class ManageTeamController {
    private User user = (User) FXRouter.getData();
    private Event event = (Event) FXRouter.getData2();

    @FXML private GridPane memberContainer;

    JoinTeamMap joinTeamMap = new JoinTeamMap();
    HashMap<String, UserList> userListHashMap;
    UserList userList;


    @FXML private void initialize(){}

    public void showUserList(String teamID) {
        JoinTeamMap joinTeamMap = new JoinTeamMap();
        HashMap<String, UserList> hashMap = joinTeamMap.roleReadData();
        if (hashMap.isEmpty()) return;
        UserList memberList = hashMap.get(teamID);
        for (User user : memberList.getUsers()) {
            loadManageTeamComponent(user, 0, memberList.getUsers().indexOf(user));
        }

    }

    private void loadManageTeamComponent(User user, int col, int row) {
        FXMLLoader manageTeamLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/team/manage-team/manage-member-team-list.fxml"));
        AnchorPane manageTeamComponent;
        if (user != null) {
            try {
                manageTeamComponent = manageTeamLoader.load();
                Label nameLabel = (Label) manageTeamComponent.getChildren().get(0);
                AnchorPane roleAnchorPane = (AnchorPane) manageTeamComponent.getChildren().get(1);
                ImageView roleImageView = (ImageView) roleAnchorPane.getChildren().get(0);
                Label roleLabel = (Label) roleAnchorPane.getChildren().get(1);
                ImageView menuImageView = (ImageView) manageTeamComponent.getChildren().get(2);
                Label idLabel = (Label) manageTeamComponent.getChildren().get(4);
                Label statusLabel = (Label) manageTeamComponent.getChildren().get(5);
                ImageView statusImageView = (ImageView) manageTeamComponent.getChildren().get(6);

                nameLabel.setText(user.getUsername());
                roleLabel.setText(user.getRole());
                idLabel.setText(user.getUserId());
                statusLabel.setText((user.getStatus())? "online" : "offline");
                roleImageView.setImage(new Image(getClass().getResourceAsStream("/images/icons/team-box/role/" + user.getRole() + ".png")));
                menuImageView.setImage(new Image(getClass().getResourceAsStream("/images/icons/team-box/dot_icon.png")));
                statusImageView.setImage(new Image(getClass().getResourceAsStream("/images/icons/login/status/" + statusLabel.getText() + "_active.png")));


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return;
        }
        GridPane.setMargin(manageTeamComponent,new Insets(0,0,5,0));
        memberContainer.add(manageTeamComponent, col, row);
    }


    @FXML private void onBackClick() {
        try {
            FXRouter.goTo("select-team", user, event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
