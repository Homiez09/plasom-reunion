package cs211.project.controllers.view.team;

import cs211.project.controllers.component.teamControllers.manageTeamController.ManageTeamComponentController;

import cs211.project.controllers.component.teamControllers.manageTeamsController.ManageTeamsBoxController;
import cs211.project.controllers.component.teamControllers.teamboxControllers.TeamBox1Controller;
import cs211.project.controllers.component.teamControllers.teamboxControllers.TeamBox2Controller;
import cs211.project.models.Team;
import cs211.project.models.User;
import cs211.project.models.Event;
import cs211.project.models.collections.TeamList;
import cs211.project.services.FXRouter;
import cs211.project.services.JoinTeamMap;
import cs211.project.services.LoadNavbarComponent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Shape;

import java.io.IOException;
import java.util.HashMap;

public class AllTeamController {
    @FXML private AnchorPane createTeamAnchorPane, navbarAnchorPane, switchViewAnchorPane, selectTeamAnchorPane, manageTeamsAnchorPane, manageTeamAnchorPane;
    @FXML private GridPane teamContainer, managerContainer;
    @FXML private ImageView joinTeamImageView, settingImageView, sortImageView, createTeamImageView, teamBox1ImageView, teamBox2ImageView;
    @FXML private ComboBox settingMenuComboBox, filterMenuComboBox;
    @FXML private CheckBox teamBox1CheckBox, teamBox2CheckBox;
    @FXML private Label menu1Label;
    @FXML private Shape selectMenu1;
    @FXML private Button createButton, joinButton;

    private final User user = (User) FXRouter.getData();
    private final Event event = (Event) FXRouter.getData2();
    private final JoinTeamMap joinTeamMap = new JoinTeamMap();
    private String filter = "All";
    private String teamBox ;
    private final String[] menu = {"Manage Teams", "Switch View"};
    private final String[] filters = {"All", "Favorite", "Owner", "Leader", "Member"};
    private final Image create = new Image(getClass().getResourceAsStream("/images/icons/select-team/create_icon.png"));
    private final Image createHover = new Image(getClass().getResourceAsStream("/images/icons/select-team/create_icon_hover.png"));
    private final Image setting = new Image(getClass().getResourceAsStream("/images/icons/select-team/setting_icon.png"));
    private final Image settingHover = new Image(getClass().getResourceAsStream("/images/icons/select-team/setting_icon_hover.png"));

    String teamSelectedComponentID = "";
    @FXML
    private void initialize() {
        teamBox = "teamBox1";

        initMenu();
        initButton();
        initCreateTeamPage();
        manageTeamAnchorPane.setVisible(false);

        teamBoxView(teamBox);
        manageTeamSelectMenuGraphic();

        new LoadNavbarComponent(user, navbarAnchorPane);
        loadIconImage();

    }

    @FXML
    private void onMyEventClick(){
        try {
            FXRouter.goTo("my-event", user);
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

    @FXML
    private void onJoinTeamButtonClick() {
        try {
            FXRouter.goTo("join-team", user, event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        // Don't change;
        selectTeamAnchorPane.setEffect(null);
        selectTeamAnchorPane.setDisable(false);

        // add here
        switchViewAnchorPane.setVisible(false);
        manageTeamsAnchorPane.setVisible(false);
        teamBoxView(teamBox);
    }

    @FXML
    private void onSettingEntered() {
        settingImageView.setImage(settingHover);
    }

    @FXML
    private void onSettingExited() {
        settingImageView.setImage(setting);
    }

    private void initButton() {
        if (user.getUserId().equals(event.getEventHostUser().getUserId())) createButton.setVisible(true);
        else joinButton.setVisible(true);

        createButton.setOnMouseEntered(event -> createTeamImageView.setImage(createHover));
        createButton.setOnMouseExited(event -> createTeamImageView.setImage(create));
        joinButton.setOnMouseEntered(event -> joinTeamImageView.setImage(createHover));
        joinButton.setOnMouseExited(event -> joinTeamImageView.setImage(create));
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
            ManageTeamComponentController manageTeamController = manageTeamAnchorPaneLoader.getController();
            manageTeamController.showUserList(teamSelectedComponentID);
            manageTeamAnchorPane.setVisible(false);
            selectTeamAnchorPane.setDisable(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void teamBoxView(String teamBox) {
        HashMap<String, TeamList> teamHashMap = joinTeamMap.readData();
        int row = 0, column = 0, row2 = 0;

        TeamList teamListSort = (teamHashMap.get(user.getUsername()) != null) ? new TeamList(teamHashMap.get(user.getUsername())) : new TeamList();
        teamListSort.sortTeamByNewCreatedAt();

        if (filter.equals("Favorite")) {
            teamListSort.filterByBookmark();
        }
        else if (filter.equals("Owner") || filter.equals("Leader") || filter.equals("Member")) {
            teamListSort.filterByRole(filter);
        } else {
            teamListSort.filterByAll();
        }

        teamContainer.getChildren().clear();
        managerContainer.getChildren().clear();

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
                    TeamBox1Controller teamBox1Controller = teamBoxLoader1.getController();
                    teamBox1Controller.setTeamData(team);
                    teamBox1Controller.setSelectTeamController(this);
                } else {
                    teamBox1CheckBox.setSelected(false);
                    teamBox2CheckBox.setSelected(true);
                    teamBoxComponent = teamBoxLoader2.load();
                    TeamBox2Controller teamBox2Controller = teamBoxLoader2.getController();
                    teamBox2Controller.setTeamData(team);
                    teamBox2Controller.setSelectTeamController(this);
                }

                loadTeamBoxComponent(team, 0, row2++);

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

    public void startManageTeam(Team team) {
        manageTeamSelectMenuGraphic();

        manageTeamAnchorPane.setVisible(true);
        selectTeamAnchorPane.setEffect(new BoxBlur(6, 5, 2));
        selectTeamAnchorPane.setDisable(true);
        teamSelectedComponentID = team.getTeamID();
        initManageTeam();
        showManageTeam();
    }

    private void loadIconImage(){
        Image settingIcon = new Image(getClass().getResourceAsStream("/images/icons/select-team/setting_icon.png"));
        Image sortIcon = new Image(getClass().getResourceAsStream("/images/icons/select-team/sort_icon.png"));
        Image teamBox1 = new Image(getClass().getResourceAsStream("/images/icons/team-box/switch-view/team_box_1.png"));
        Image teamBox2 = new Image(getClass().getResourceAsStream("/images/icons/team-box/switch-view/team_box_2.png"));

        settingImageView.setImage(settingIcon);
        sortImageView.setImage(sortIcon);
        teamBox1ImageView.setImage(teamBox1);
        teamBox2ImageView.setImage(teamBox2);
        createTeamImageView.setImage(create);
        joinTeamImageView.setImage(create);
    }

    private void initMenu() {
        switchViewAnchorPane.setVisible(false);
        manageTeamsAnchorPane.setVisible((false));

        settingMenuComboBox.getItems().addAll(menu);
        settingMenuComboBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue == null) return;
            showBlock((String) newValue);
        });

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
            case "Manage Teams" -> {
                teamBoxView(teamBox);
                manageTeamsAnchorPane.setVisible(true);
                selectTeamAnchorPane.setEffect(new BoxBlur(6, 5, 2));
                selectTeamAnchorPane.setDisable(true);
            }
            case "Switch View" -> {
                switchViewAnchorPane.setVisible(true);
                selectTeamAnchorPane.setEffect(new BoxBlur(6, 5, 2));
                selectTeamAnchorPane.setDisable(true);
            }
        }

        settingMenuComboBox.getSelectionModel().clearSelection();
    }

    private void loadTeamBoxComponent(Team team, int col, int row) {
        FXMLLoader teamBoxLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/team/manage-teams/team-box-list.fxml"));
        AnchorPane teamBoxComponent;
        if (team != null) {
            try {
                teamBoxComponent = teamBoxLoader.load();
                ManageTeamsBoxController manageTeamsBoxController = teamBoxLoader.getController();
                manageTeamsBoxController.setSelectTeamController(this);
                manageTeamsBoxController.setTeamData(team);
                manageTeamsAnchorPane.getChildren().add(teamBoxComponent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return;
        }

        GridPane.setMargin(teamBoxComponent,new Insets(0,0,5,0));

        managerContainer.add(teamBoxComponent, col, row);
    }

    private void manageTeamSelectMenuGraphic() {
        initManageTeamSelectMenuGraphic();
        String selectColor = "-fx-text-fill: #413B3B;";
        menu1Label.setStyle(selectColor);
        selectMenu1.setVisible(true);
    }

    private void initManageTeamSelectMenuGraphic() {
        String style = "";
        menu1Label.setStyle(style);
        selectMenu1.setVisible(false);
    }
}


