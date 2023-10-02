package cs211.project.controllers.team;

import cs211.project.componentControllers.UserCardProfileController;
import cs211.project.componentControllers.teamControllers.manageTeamController.ManageMemberTeamListController;
import cs211.project.models.Event;
import cs211.project.models.Team;
import cs211.project.models.User;
import cs211.project.models.collections.TeamList;
import cs211.project.models.collections.UserList;
import cs211.project.services.*;
import cs211.project.services.team.LoadSideBarComponent;
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
    private Team team = (Team) FXRouter.getData3();

    @FXML private GridPane memberContainer;
    @FXML AnchorPane navbarAnchorPane, sideBarAnchorPane;
    @FXML private ImageView nameImageView, roleImageView, statusImageView;
    @FXML private AnchorPane userCardProfileAnchorPane, manageTeamExitAnchorPane, manageTeamDisbleAnchorPane;
    protected Image nameIcon, roleIcon, statusIcon;

    TeamListDataSource teamListDataSource = new TeamListDataSource("data", "team-list.csv");
    TeamList teamList = teamListDataSource.readData();;
    JoinTeamMap joinTeamMap = new JoinTeamMap();
    HashMap<String, UserList> hashMap = joinTeamMap.roleReadData();

    String teamID;

    UserListDataSource datasource ;
    UserList userList ;


    boolean nameSort, roleSort, statusSort;

    @FXML private void initialize(){
        new LoadNavbarComponent(user, navbarAnchorPane);
        new LoadSideBarComponent(sideBarAnchorPane);
        datasource = new UserListDataSource("data","user-list.csv");
        userList = datasource.readData();
        loadIcon();
        showUserList(team.getTeamID());
        setManageTeamDisableAnchorPane(false);
    }

    public void showUserList(String teamID) {
        memberContainer.getChildren().clear();
        if (hashMap.isEmpty()) return;
        UserList memberList = hashMap.get(teamID);
        for (User user : memberList.getUsers()) {
            loadManageTeamComponent(user, 0, memberList.getUsers().indexOf(user), teamID);
        }
        this.teamID = teamID;

    }


    private void setManageTeamDisableAnchorPane(boolean isDisable){
        manageTeamDisbleAnchorPane.setDisable(isDisable);
        manageTeamExitAnchorPane.setVisible(isDisable);
        if(isDisable){
            manageTeamDisbleAnchorPane.setEffect(new BoxBlur(6, 5, 2));
        }else {
            manageTeamDisbleAnchorPane.setEffect(null);
        }
    }


    private void loadManageTeamComponent(User user, int col, int row, String teamID) {
        Team team = teamList.findTeamByID(teamID);
        FXMLLoader manageTeamLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/team/manage-team/manage-member-team-list-1.fxml"));
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

                String role = (user.getRole().equals("Leader") ? "Member" : "Leader");
                String[] menuOwnerItems = {"View Profile", "Kick", "Ban", "Promote to " + role};
                String[] menuLeaderItems = {"View Profile","Kick", "Ban"};
                String[] menuMemberItems = {"View Profile"};
                String[] menuItems;

                User userTemp = team.getMemberList().findUserId(this.user.getUserId());
                if(userTemp.getRole().equals("Owner")) {
                    menuItems = (!userTemp.getUserId().equals(user.getUserId()) ? menuOwnerItems : menuMemberItems);
                }else if(userTemp.getRole().equals("Leader") && !user.getRole().equals("Owner")){
                    menuItems = (!userTemp.getUserId().equals(user.getUserId()) ? menuLeaderItems : menuMemberItems);
                }else{
                    menuItems = menuMemberItems;
                }

                menuComboBox.getItems().addAll(menuItems);
                menuComboBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
                    if (newValue == null) return;
                    ManageMemberTeamListController manageMemberTeamListController = manageTeamLoader.getController();
                    if (newValue.equals("Promote to Leader")) {
                        try {
                            manageMemberTeamListController.goTo((String) newValue, teamID, user.getUserId());
                            showUserList(teamID);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if(newValue.equals("Promote to Member")) {
                        try {
                            manageMemberTeamListController.goTo((String) newValue, teamID, user.getUserId());
                            showUserList(teamID);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }else if (newValue.equals("Kick") || newValue.equals("Leave Team")) {
                        try {
                            manageMemberTeamListController.goTo((String) newValue, teamID);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (newValue.equals("Ban")) {
                        try {
                            manageMemberTeamListController.goTo((String) newValue, teamID, user.getUserId());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }else if (newValue.equals("View Profile")){

                        setManageTeamDisableAnchorPane(true);
                        loadUserCardProfileComponent(userCardProfileAnchorPane, user);
                        userCardProfileAnchorPane.setVisible(true);
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

    private void loadUserCardProfileComponent(AnchorPane userCardProfileAnchorPane, User user) {
        try {
            FXMLLoader userCardProfileComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/user-card-profile.fxml"));
            AnchorPane userCardProfileComponent = userCardProfileComponentLoader.load();

            UserCardProfileController userCardProfileController = userCardProfileComponentLoader.getController();
            userCardProfileController.setUser(user);

            userCardProfileAnchorPane.getChildren().add(userCardProfileComponent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML private void onSortNameClicked(){
        if (hashMap.isEmpty()) return;
        UserList memberList = hashMap.get(teamID);
        memberList.sort(new UsernameUserComparator());
        if (nameSort) {
            memberList.reverse(new UsernameUserComparator());
        }
        for (User user : memberList.getUsers()) {
            loadManageTeamComponent(user, 0, memberList.getUsers().indexOf(user), teamID);
        }
        nameSort = !nameSort;
    }
    @FXML private void onSortRoleClicked(){
        if (hashMap.isEmpty()) return;
        UserList memberList = hashMap.get(teamID);
        memberList.sort(new RoleUserComparator());
        if (roleSort) {
            memberList.reverse(new RoleUserComparator());
        }
        for (User user : memberList.getUsers()) {
            loadManageTeamComponent(user, 0, memberList.getUsers().indexOf(user), teamID);
        }
        roleSort = !roleSort;
    }
    @FXML private void onSortStatusClicked(){
        if (hashMap.isEmpty()) return;
        UserList memberList = hashMap.get(teamID);
        memberList.sort(new StatusUserComparator());
        if (statusSort) {
            memberList.reverse(new StatusUserComparator());
        }
        for (User user : memberList.getUsers()) {
            loadManageTeamComponent(user, 0, memberList.getUsers().indexOf(user), teamID);
        }
        statusSort = !statusSort;
    }
    @FXML private void manageTeamAnchorPaneClicked(){
        setManageTeamDisableAnchorPane(false);
        userCardProfileAnchorPane.setVisible(false);
    }

    private void loadIcon(){
        nameIcon = new Image(getClass().getResourceAsStream("/images/icons/team/sortIcon.png"));
        roleIcon = new Image(getClass().getResourceAsStream("/images/icons/team/sortIcon.png"));
        statusIcon = new Image(getClass().getResourceAsStream("/images/icons/team/sortIcon.png"));

        nameImageView.setImage(nameIcon);
        roleImageView.setImage(roleIcon);
        statusImageView.setImage(statusIcon);
    }
}
