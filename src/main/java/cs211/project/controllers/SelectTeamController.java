package cs211.project.controllers;

import at.favre.lib.crypto.bcrypt.BCryptFormatter;
import cs211.project.componentControllers.TeamBox1Controller;
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

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SelectTeamController {
    @FXML private AnchorPane navbarAnchorPane, switchViewAnchorPane, selectTeamAnchorPane;
    @FXML private GridPane teamContainer;

    @FXML private ImageView settingImageView, sortImageView, createTeamImageView, teamBox1ImageView, teamBox2ImageView;
    @FXML private ComboBox settingMenuComboBox, filterMenuComboBox;

    @FXML private CheckBox teamBox1CheckBox, teamBox2CheckBox;
    private String filter = "All";
    private String teamBox ;
    private User user = (User) FXRouter.getData();
    private Event event = (Event) FXRouter.getData2();
    private TeamList teamList;
    private HashMap<String, Team> teamHashMap;

    @FXML
    private void initialize() {
        initMenu();

        TeamListDataSource datasource = new TeamListDataSource("data", "team-list.csv");
        teamList = datasource.readData();
        teamList.getTeamOfEvent(event);
        HashMap<String, Team> teamHashMapAll = teamList.teamHashMap();
        JoinTeamMap datasourceJoinTeamMap = new JoinTeamMap(user, teamHashMapAll);
        teamHashMap = datasourceJoinTeamMap.readData();

        teamBox = "teamBox1";
        teamBoxView(teamBox);

        new LoadNavbarComponent(user, navbarAnchorPane);
        loadIconImage();

    }

    private void teamBoxView(String teamBox) {
        System.out.println(filter);
        int row = 0, column = 0;

        TeamList teamListSort = new TeamList(teamHashMap);
        teamListSort.sortTeamByNewCreatedAt();

        if (filter.equals("Favorite")) teamListSort.filterByBookmark();
        else if (filter.equals("Owner")) teamListSort.filterByRole(filter);
        else if (filter.equals("Leader")) teamListSort.filterByRole(filter);
        else if (filter.equals("Member")) teamListSort.filterByRole(filter);
        else teamListSort.filterByAll();

        for (Team team : teamListSort.getTeams()) {
            if (!team.getEventID().equals(event.getEventID())) continue;
            try {
                FXMLLoader teamBoxLoader1 = new FXMLLoader(getClass().getResource("/cs211/project/views/components/team-box-1.fxml"));
                FXMLLoader teamBoxLoader2 = new FXMLLoader(getClass().getResource("/cs211/project/views/components/team-box-2.fxml"));

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

    @FXML private void onShowSettingMenuClick() {
        settingMenuComboBox.show();

    }

    @FXML private void onShowFilterMenuClick() {
        filterMenuComboBox.show();
    }

    private void initMenu() {
        switchViewAnchorPane.setVisible(false);
        String menu[] = {"Manage Teams", "Switch View"};

        settingMenuComboBox.getItems().addAll(menu);
        settingMenuComboBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue == null) return;
            showBlock((String) newValue);
        });

        String filter[] = {"All", "Favorite", "Owner", "Leader", "Member"};

        filterMenuComboBox.getItems().addAll(filter);
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

    private void showBlock(String select) {
        switch (select) {
            case "Manage Teams":
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
        selectTeamAnchorPane.setEffect(null);
        switchViewAnchorPane.setVisible(false);
        selectTeamAnchorPane.setDisable(false);
    }
}

