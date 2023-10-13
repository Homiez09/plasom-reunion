package cs211.project.controllers.view.team;

import cs211.project.controllers.component.CardJoinTeamController;
import cs211.project.controllers.component.alertBox.JoinTeamAlertBoxController;
import cs211.project.models.Event;
import cs211.project.models.Team;
import cs211.project.models.User;
import cs211.project.models.collections.TeamList;
import cs211.project.services.FXRouter;
import cs211.project.services.JoinTeamMap;
import cs211.project.services.LoadNavbarComponent;
import cs211.project.services.TeamListDataSource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.HashMap;

public class JoinTeamController {
    @FXML private AnchorPane navbarAnchorPane, staffButtonAnchorPane, alertBoxAnchorPane, joinTeamAnchorPane;
    @FXML private GridPane teamContainer;
    @FXML private ImageView staffImageView, searchImageView;
    @FXML private ComboBox sortComboBox;
    @FXML private Button onSortButton;
    @FXML private Label  teamCountLabel;
    @FXML private TextField searchTextField;

    private final String[] sortType = {"A-Z","Z-A","recent", "oldest"};
    private String sortComboBoxValue = "A-Z";
    private final int MAX_SEARCH_TEAM_NAME_LIMIT = 40;

    private final User user = (User) FXRouter.getData();
    private final Event event = (Event) FXRouter.getData2();
    JoinTeamMap joinTeamMap = new JoinTeamMap();
    HashMap<String, TeamList> teamListHashMap = joinTeamMap.readData();
    TeamListDataSource teamListDataSource = new TeamListDataSource("data", "team-list.csv");
    TeamList teamList = teamListDataSource.readData();

    @FXML private void initialize(){
        new LoadNavbarComponent(user, navbarAnchorPane);

        setStaffButtonVisible();

        loadImageInit();
        loadCardTeamInit();
        loadSortType();

        maximumLengthField();
    }

    private void loadImageInit(){
        searchImageView.setImage(new Image(getClass().getResourceAsStream("/images/icons/join-team/search_bar.png")));
        staffImageView.setImage(new Image(getClass().getResourceAsStream("/images/icons/join-team/users_group.png")));
    }
    private void loadSortType(){
        sortComboBox.getItems().addAll(sortType);
        sortComboBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue == null) return;
            setSortType((String) newValue);
        });
    }
    public void loadAlertBox(Team team) {
        FXMLLoader alertBoxLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/alert-box/join-team-alert-box.fxml"));
        AnchorPane alertBoxComponent;
        try {
            alertBoxComponent = alertBoxLoader.load();
            JoinTeamAlertBoxController joinTeamAlertBoxController = alertBoxLoader.getController();
            joinTeamAlertBoxController.setTeam(team);
            joinTeamAlertBoxController.validateTeam();
            joinTeamAlertBoxController.validateButton();

            alertBoxAnchorPane.getChildren().add(alertBoxComponent);

            joinTeamAnchorPane.setEffect(new BoxBlur(6, 5, 2));
            joinTeamAnchorPane.setDisable(true);
            alertBoxAnchorPane.setVisible(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadCardTeamInit() {
        setSortType();
        teamCountLabel.setText(String.valueOf(teamList.getTeamOfEvent(event).size()));
        for (Team teamCheck : teamList.getTeamOfEvent(event)) {
            FXMLLoader joinTeamCardLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/card-join-team.fxml"));
            AnchorPane joinTeamCardComponent;
            try {
                joinTeamCardComponent = joinTeamCardLoader.load();
                CardJoinTeamController cardJoinTeamController = joinTeamCardLoader.getController();

                cardJoinTeamController.setJoinTeamController(this);
                cardJoinTeamController.setTeam(teamCheck);
                cardJoinTeamController.initializeData();

                GridPane.setMargin(joinTeamCardComponent,new Insets(20,10,15,0));
                teamContainer.add(joinTeamCardComponent, 0, teamContainer.getRowCount());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void setSortType(String selectSortType) {
        sortComboBoxValue = selectSortType;
        teamContainer.getChildren().clear();
        loadCardTeamInit();
        sortComboBox.getSelectionModel().clearSelection();
    }

    private void setSortType(){
        switch (sortComboBoxValue) {
            case "A-Z" -> {
                onSortButton.setText("Sort By: A-Z");
                teamList.sortTeamByNameA();
            }
            case "Z-A" -> {
                onSortButton.setText("Sort By: Z-A");
                teamList.sortTeamByNameZ();
            }
            case "recent" -> {
                onSortButton.setText("Sort By: recent");
                teamList.sortTeamByNewCreatedAt();
            }
            case "oldest" -> {
                onSortButton.setText("Sort By: oldest");
                teamList.sortTeamByOldCreatedAt();
            }
        }
    }

    private void setStaffButtonVisible(){
        if (teamListHashMap.containsKey(user.getUsername())) {
            TeamList teamList = teamListHashMap.get(user.getUsername());
            staffButtonAnchorPane.setVisible(teamList.getTeamOfEvent(event).size() != 0);
        }


    }


    @FXML private void onSortByButtonClick() {
        sortComboBox.show();
        joinTeamAnchorPane.setVisible(true);
        sortComboBox.setVisible(true);
    }

    @FXML private void onJoinTeamAnchorPaneClick(){
        joinTeamAnchorPane.setVisible(false);
        sortComboBox.setVisible(false);
    }

    @FXML void onBackClick() {
        try {
            FXRouter.goTo("event",user,event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML void onStaffClick() {
        try {
            FXRouter.goTo("all-team",user,event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML private void onKeySearch() {
        String keyword = searchTextField.getText();
        teamContainer.getChildren().clear();
        for (Team teamCheck : teamList.getTeamOfEvent(event)) {
            if (teamCheck.getTeamName().toLowerCase().contains(keyword.toLowerCase())) {
                FXMLLoader joinTeamCardLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/card-join-team.fxml"));
                AnchorPane joinTeamCardComponent;
                try {
                    joinTeamCardComponent = joinTeamCardLoader.load();
                    CardJoinTeamController cardJoinTeamController = joinTeamCardLoader.getController();

                    cardJoinTeamController.setJoinTeamController(this);
                    cardJoinTeamController.setTeam(teamCheck);
                    cardJoinTeamController.initializeData();

                    GridPane.setMargin(joinTeamCardComponent, new Insets(20, 10, 15, 0));
                    teamContainer.add(joinTeamCardComponent, 0, teamContainer.getRowCount());
                    teamCountLabel.setText(String.valueOf(teamContainer.getRowCount()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                teamCountLabel.setText(String.valueOf(teamContainer.getRowCount()));
            }
        }
    }

    private void maximumLengthField(){
        searchTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.length() > MAX_SEARCH_TEAM_NAME_LIMIT) {
                searchTextField.setText(oldValue);
            }
        });
    }


}
