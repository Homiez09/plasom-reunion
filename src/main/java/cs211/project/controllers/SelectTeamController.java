package cs211.project.controllers;

import cs211.project.componentControllers.AvatarProfileController;
import cs211.project.componentControllers.CreateTeamController;
import cs211.project.models.Team;
import cs211.project.models.User;
import cs211.project.models.Event;
import cs211.project.models.collections.TeamList;
import cs211.project.services.FXRouter;
import cs211.project.services.JoinTeamMap;
import cs211.project.services.LoadNavbarComponent;
import cs211.project.services.TeamListDataSource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Shape;

import java.io.IOException;
import java.util.HashMap;

public class SelectTeamController {
    @FXML private AnchorPane createTeamAnchorPane, navbarAnchorPane, switchViewAnchorPane, selectTeamAnchorPane, manageTeamsAnchorPane, manageTeamAnchorPane;
    @FXML private GridPane teamContainer, managerContainer;
    @FXML private ImageView settingImageView, sortImageView, createTeamImageView, teamBox1ImageView, teamBox2ImageView;
    @FXML private ComboBox settingMenuComboBox, filterMenuComboBox;
    @FXML private CheckBox teamBox1CheckBox, teamBox2CheckBox;
    @FXML private Label menu1Label, menu2Label;
    @FXML private Shape selectMenu1, selectMenu2;
    private String filter = "All";
    private String teamBox ;
    private User user = (User) FXRouter.getData();
    private Event event = (Event) FXRouter.getData2();
    JoinTeamMap joinTeamMap = new JoinTeamMap();
    HashMap<String, TeamList> teamHashMap;

    Image settingHover = new Image(getClass().getResourceAsStream("/images/icons/select-team/setting_icon_hover.png"));
    Image setting = new Image(getClass().getResourceAsStream("/images/icons/select-team/setting_icon.png"));
    @FXML
    private void initialize() {
        teamBox = "teamBox1";

        initMenu();
        initCreateTeamPage();
        initManageTeam();

        teamBoxView(teamBox);
        manageTeamSelectMenuGraphic(1);


        new LoadNavbarComponent(user, navbarAnchorPane);
        loadIconImage();

    }

    @FXML
    private void onMyEventClick(){
        try {
            FXRouter.goTo("my-events", user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onTeamClick(){
        try {
            FXRouter.goTo("select-team", user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onCreateTeamButtonClick() {
        createTeamAnchorPane.setVisible(true);
        selectTeamAnchorPane.setDisable(false);
        selectTeamAnchorPane.setEffect(new BoxBlur(6, 5, 2));
    }


    @FXML private void onShowSettingMenuClick() {
        settingMenuComboBox.show();
    }

    @FXML private void onShowFilterMenuClick() {
        filterMenuComboBox.show();
    }

    @FXML
    private void onTeamBox1CheckBoxClick() {
        if (teamBox1CheckBox.isSelected()) {
            teamBox2CheckBox.setSelected(false);
            teamBox = "teamBox1";
            teamBoxView(teamBox);
        } else if (!teamBox2CheckBox.isSelected()) {
            teamBox = "teamBox2";
            teamBoxView(teamBox);
        }
    }

    @FXML
    private void onTeamBox2CheckBoxClick() {
        if (teamBox2CheckBox.isSelected()) {
            teamBox1CheckBox.setSelected(false);
            teamBox = "teamBox2";
            teamBoxView(teamBox);
        } else if (!teamBox1CheckBox.isSelected()) {
            teamBox = "teamBox1";
            teamBoxView(teamBox);
        }
    }

    @FXML
    private void onBackClick(){
        // Don't chnage;
        selectTeamAnchorPane.setEffect(null);
        selectTeamAnchorPane.setDisable(false);

        // add here
        switchViewAnchorPane.setVisible(false);
        manageTeamsAnchorPane.setVisible(false);
        teamBoxView(teamBox);
    }

    @FXML
    protected void onSettingEntered() {
        settingImageView.setImage(settingHover);
    }

    @FXML
    protected void onSettingExited() {
        settingImageView.setImage(setting);
    }

    private void initCreateTeamPage(){
        FXMLLoader createTeamAnchorPaneLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/create-team.fxml"));
        try {
            AnchorPane createTeamAnchorPaneComponent = createTeamAnchorPaneLoader.load();
            createTeamAnchorPane.getChildren().add(createTeamAnchorPaneComponent);
            createTeamAnchorPane.setVisible(false);
            selectTeamAnchorPane.setDisable(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void initManageTeam() {
        FXMLLoader manageTeamAnchorPaneLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/team/manage-team/manage-team.fxml"));
        try {
            AnchorPane manageTeamAnchorPaneComponent = manageTeamAnchorPaneLoader.load();
            manageTeamAnchorPane.getChildren().add(manageTeamAnchorPaneComponent);
            manageTeamAnchorPane.setVisible(false);
            selectTeamAnchorPane.setDisable(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void teamBoxView(String teamBox) {
        teamHashMap = joinTeamMap.readData();
        int row = 0, column = 0, row2 = 0;

        TeamList teamListSort = (teamHashMap.get(user.getUsername()) != null) ? new TeamList(teamHashMap.get(user.getUsername())) : new TeamList();

        if (teamListSort != null) teamListSort.sortTeamByNewCreatedAt();

        if (filter.equals("Favorite")) {
            teamListSort.filterByBookmark();
        }
        else if (filter.equals("Owner") || filter.equals("Leader") || filter.equals("Member")) {
            teamListSort.filterByRole(filter);
        } else {
            teamListSort.filterByAll();
        }

        for (Team team : teamListSort.getTeams()) {
            if (!team.getEventID().equals(event.getEventID())) continue;
            try {
                FXMLLoader teamBoxLoader1 = new FXMLLoader(getClass().getResource("/cs211/project/views/components/team-box/team-box-1.fxml"));
                FXMLLoader teamBoxLoader2 = new FXMLLoader(getClass().getResource("/cs211/project/views/components/team-box/team-box-2.fxml"));

                AnchorPane teamBoxComponent;
                if ("teamBox1".equals(teamBox)) {
                    teamBox1CheckBox.setSelected(true);
                    teamBox2CheckBox.setSelected(false);
                    teamBoxComponent = teamBoxLoader1.load();
                } else {
                    teamBox1CheckBox.setSelected(false);
                    teamBox2CheckBox.setSelected(true);
                    teamBoxComponent = teamBoxLoader2.load();
                }

                loadTeamBoxComponent(team, 0, row2++);

                Label teamID = (Label) teamBoxComponent.getChildren().get(1);
                Label teamName = (Label)teamBoxComponent.getChildren().get(6);
                ImageView bookMarkImageView = (ImageView) teamBoxComponent.getChildren().get(5);
                ImageView roleImageView = (ImageView) teamBoxComponent.getChildren().get(7);
                AnchorPane memberShipAnchorPane = (AnchorPane) teamBoxComponent.getChildren().get(12);
                ComboBox menuDropDown = (ComboBox) teamBoxComponent.getChildren().get(0);
                Label roleLabel = (Label) memberShipAnchorPane.getChildren().get(2);
                Label bookmarkLabel = (Label) teamBoxComponent.getChildren().get(14);

                teamID.setText(team.getTeamID());
                teamName.setText(team.getTeamName());
                roleLabel.setText(team.getRole());

                if (team.getRole().equals("Owner")) {
                    menuDropDown.getItems().addAll("Manage Team", "Delete Team");
                } else {
                    menuDropDown.getItems().addAll("Manage Team", "Leave Team");
                }

                if (team.isBookmarked()) {
                    bookMarkImageView.setImage(new Image(getClass().getResourceAsStream("/images/icons/team-box/bookmark/bookmark_icon.png")));
                    bookmarkLabel.setText(String.valueOf(team.isBookmarked()));
                } else {
                    bookMarkImageView.setImage(new Image(getClass().getResourceAsStream("/images/icons/team-box/bookmark/unbookmark_icon.png")));
                    bookmarkLabel.setText(String.valueOf(team.isBookmarked()));
                }

                roleImageView.setImage(new Image(getClass().getResourceAsStream("/images/icons/team-box/role/" + team.getRole() + ".png")));

                if (column == 4) {
                    column = 0;
                    row++;
                }
                GridPane.setMargin(teamBoxComponent,new Insets(18,18,37,33));
                teamContainer.add(teamBoxComponent, ++column, row);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadIconImage(){
        Image settingIcon = new Image(getClass().getResourceAsStream("/images/icons/select-team/setting_icon.png"));
        settingImageView.setImage(settingIcon);
        Image sortIcon = new Image(getClass().getResourceAsStream("/images/icons/select-team/sort_icon.png"));
        sortImageView.setImage(sortIcon);
        Image createTeamIcon = new Image(getClass().getResourceAsStream("/images/icons/select-team/create_icon.png"));
        createTeamImageView.setImage(createTeamIcon);
        Image teamBox1 = new Image(getClass().getResourceAsStream("/images/icons/team-box/switch-view/teambox_1.png"));
        teamBox1ImageView.setImage(teamBox1);
        Image teamBox2 = new Image(getClass().getResourceAsStream("/images/icons/team-box/switch-view/teambox_2.png"));
        teamBox2ImageView.setImage(teamBox2);
    }

    private void initMenu() {
        switchViewAnchorPane.setVisible(false);
        manageTeamsAnchorPane.setVisible((false));
        String menu[] = {"Manage Teams", "Switch View"};

        settingMenuComboBox.getItems().addAll(menu);
        settingMenuComboBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue == null) return;
            showBlock((String) newValue);
        });

        String filters[] = {"All", "Favorite", "Owner", "Leader", "Member"};
        filterMenuComboBox.getItems().addAll(filters);
        filterMenuComboBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue == null) return;
            filterTeam((String) newValue);
        });
    }

    private void filterTeam(String selectFilter) {
        filter = selectFilter;
        teamContainer.getChildren().clear();
        teamBoxView(teamBox);
        filterMenuComboBox.getSelectionModel().clearSelection();
    }

    public void showManageTeam() {
        this.manageTeamAnchorPane.setVisible(true);
        this.selectTeamAnchorPane.setDisable(false);
    }

    private void showBlock(String select) {
        switch (select) {
            case "Manage Teams":
                teamBoxView(teamBox);
                manageTeamsAnchorPane.setVisible(true);
                selectTeamAnchorPane.setEffect(new BoxBlur(6, 5, 2));
                selectTeamAnchorPane.setDisable(true);
                break;
            case "Switch View":
                switchViewAnchorPane.setVisible(true);
                selectTeamAnchorPane.setEffect(new BoxBlur(6, 5, 2));
                selectTeamAnchorPane.setDisable(true);
                break;
        }
        // this code will show warning, but it's work (IndexOutOfBoundsException) [todo : fix this warning]
        settingMenuComboBox.getSelectionModel().clearSelection();
    }

    private void loadTeamBoxComponent(Team team, int col, int row) {
        FXMLLoader teamBoxLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/team/manage-teams/team-box-list.fxml"));
        AnchorPane teamBoxComponent;
        if (team != null) {
            try {
                teamBoxComponent = teamBoxLoader.load();
                Label teamName = (Label)teamBoxComponent.getChildren().get(0);
                HBox HBoxRole = (HBox)teamBoxComponent.getChildren().get(1);
                ImageView roleImageView = (ImageView)HBoxRole.getChildren().get(0);
                Label role = (Label)HBoxRole.getChildren().get(1);
                HBox HBoxPeople = (HBox) teamBoxComponent.getChildren().get(2);
                Label people = (Label) HBoxPeople.getChildren().get(0);
                ImageView bookmarkImageView = (ImageView) teamBoxComponent.getChildren().get(3);
                ImageView menuImageView = (ImageView) teamBoxComponent.getChildren().get(4);
                Label bookmarkLabel = (Label) teamBoxComponent.getChildren().get(6);
                Label teamIdLabel = (Label) teamBoxComponent.getChildren().get(7);

                teamName.setText(team.getTeamName());
                role.setText(team.getRole());
                roleImageView.setImage(new Image(getClass().getResourceAsStream("/images/icons/team-box/role/" + team.getRole() + ".png")));
                role.setText(team.getRole());
                people.setText(String.valueOf(2));
                bookmarkLabel.setText(String.valueOf(team.isBookmarked()));
                teamIdLabel.setText(team.getTeamID());

                String bookmark = team.isBookmarked() ? "bookmark_icon" : "unbookmark_icon";
                bookmarkImageView.setImage(new Image(getClass().getResourceAsStream("/images/icons/team-box/bookmark/" + bookmark +".png")));
                menuImageView.setImage(new Image(getClass().getResourceAsStream("/images/icons/team-box/dot_icon.png")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return;
        }

        GridPane.setMargin(teamBoxComponent,new Insets(0,0,5,0));

        managerContainer.add(teamBoxComponent, col, row);
    }

    private void manageTeamSelectMenuGraphic(int page) {
        initManageTeamSelectMenuGraphic();
        String selectColor = "-fx-text-fill: #413B3B;";
        switch (page) {
            case 1:
                menu1Label.setStyle(selectColor);
                selectMenu1.setVisible(true);

                break;
            case 2:
                menu2Label.setStyle(selectColor);
                selectMenu2.setVisible(true);
                break;
        }
    }

    private void initManageTeamSelectMenuGraphic() {
        String style = "";
        menu1Label.setStyle(style);
        selectMenu1.setVisible(false);
        menu2Label.setStyle(style);
        selectMenu2.setVisible(false);
    }
}


