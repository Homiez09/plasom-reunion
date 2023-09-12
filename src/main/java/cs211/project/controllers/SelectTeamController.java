package cs211.project.controllers;

import at.favre.lib.crypto.bcrypt.BCryptFormatter;
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
    @FXML private ComboBox settingMenuComboBox;

    @FXML private CheckBox teamBox1CheckBox, teamBox2CheckBox;

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

        JoinTeamMap datasourceJoinTeamMap = new JoinTeamMap(user, teamList);
        teamHashMap = datasourceJoinTeamMap.readData();
        teamBox = "teamBox1";
        teamBoxView(teamBox);

        new LoadNavbarComponent(user, navbarAnchorPane);
        loadIconImage();

    }

    private void teamBoxView(String teamBox){
        int row = 0, column = 0;
        ArrayList<Team> teamListSort = new ArrayList<>(teamHashMap.values());
        teamListSort.sort((team1, team2) -> team2.getCreatedAt().compareTo(team1.getCreatedAt()));
        for (Team team : teamListSort) {
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
                Label teamID = (Label) teamBoxComponent.getChildren().get(0);
                Label teamName = (Label)teamBoxComponent.getChildren().get(5);
                teamID.setText(team.getTeamID());
                teamName.setText(team.getTeamName());
                teamBoxComponent.setUserData(event);
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
            FXRouter.goTo("my-event");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onTeamClick(){
        try {
            FXRouter.goTo("select-team");
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

    private void initMenu() throws IndexOutOfBoundsException {
        String menu[] = {"Manage Teams", "Switch View"};

        settingMenuComboBox.getItems().addAll(menu);
        settingMenuComboBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue == null) return;
            showBlock((String) newValue);
        });
        switchViewAnchorPane.setVisible(false);
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

