package cs211.project.componentControllers.teamControllers.manageTeamController;

import cs211.project.componentControllers.teamboxControllers.TeamBox1Controller;
import cs211.project.models.Event;
import cs211.project.models.Team;
import cs211.project.models.User;
import cs211.project.models.collections.TeamList;
import cs211.project.models.collections.UserList;
import cs211.project.services.FXRouter;
import cs211.project.services.JoinTeamMap;
import cs211.project.services.TeamListDataSource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
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

    TeamListDataSource teamListDataSource = new TeamListDataSource("data", "team-list.csv");

    TeamList teamList = teamListDataSource.readData();;

    String teamID;

    @FXML private void initialize(){}

    public void showUserList(String teamID) {
        JoinTeamMap joinTeamMap = new JoinTeamMap();
        HashMap<String, UserList> hashMap = joinTeamMap.roleReadData();

        if (hashMap.isEmpty()) return;
        UserList memberList = hashMap.get(teamID);
        for (User user : memberList.getUsers()) {
            loadManageTeamComponent(user, 0, memberList.getUsers().indexOf(user), teamID);
        }
        this.teamID = teamID;

    }

    private void loadManageTeamComponent(User user, int col, int row, String teamID) {
        System.out.println(teamID);
        Team team = teamList.findTeamByID(teamID);
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
                ComboBox menuComboBox = (ComboBox) manageTeamComponent.getChildren().get(9);

                nameLabel.setText(user.getUsername());
                roleLabel.setText(user.getRole());
                idLabel.setText(user.getUsername());
                statusLabel.setText((user.getStatus())? "online" : "offline");
                roleImageView.setImage(new Image(getClass().getResourceAsStream("/images/icons/team-box/role/" + user.getRole() + ".png")));
                menuImageView.setImage(new Image(getClass().getResourceAsStream("/images/icons/team-box/dot_icon.png")));
                statusImageView.setImage(new Image(getClass().getResourceAsStream("/images/icons/login/status/" + statusLabel.getText() + "_active.png")));

                String[] menuOwnerItems = {"Promote to Leader", "Kick", "Ban"};
                String[] menuLeaderItems = {"Kick", "Ban"};
                String[] menuMemberItems = {"Leave Team"};


                menuComboBox.getItems().addAll((this.user.getUserId().equals(team.getTeamHostUser().getUserId())) ? menuOwnerItems : menuMemberItems);

                menuComboBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
                    if (newValue == null) return;
                    ManageMemberTeamListController manageMemberTeamListController = manageTeamLoader.getController();
                    if (newValue.equals("Promote to Leader")) {
                        try {
                            manageMemberTeamListController.goTo((String) newValue, teamID);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (newValue.equals("Kick") || newValue.equals("Leave Team")) {
                        try {
                            manageMemberTeamListController.goTo((String) newValue, teamID);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (newValue.equals("Ban")) {
                        try {
                            manageMemberTeamListController.goTo((String) newValue, teamID);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    showUserList(teamID);
                });

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
