package cs211.project.controllers;

import cs211.project.models.Team;
import cs211.project.models.User;
import cs211.project.models.collections.TeamList;
import cs211.project.services.FXRouter;
import cs211.project.services.LoadNavbarComponent;
import cs211.project.services.TeamListDataSource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SelectTeamController {
    @FXML private AnchorPane navbarAnchorPane;
    @FXML private GridPane teamContainer;

    @FXML private ImageView settingImageView, sortImageView, createTeamImageView;

    private User user = (User) FXRouter.getData();
    private TeamList teamList;

    @FXML
    private void initialize() {
        TeamListDataSource datasource = new TeamListDataSource("data", "team-list.csv");
        teamList = datasource.readData();
        new LoadNavbarComponent(user, navbarAnchorPane);
        loadIconImage();

        int row = 0, column = 0;

        // sort teamList
        List<Team> teamListSorted = new ArrayList<>();
        for (HashMap.Entry<String, Team> item : teamList.getTeams().entrySet()) {
            Team team = item.getValue();
            teamListSorted.add(team);
        }

        teamListSorted.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));

        for (Team team : teamListSorted) {
            try {
                FXMLLoader teamBoxLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/team.fxml"));
                AnchorPane teamBoxComponent = teamBoxLoader.load();
                Label teamName = (Label)teamBoxComponent.getChildren().get(4);
                teamName.setText(team.getTeamName());
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
            FXRouter.goTo("host.css-events");
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
    }
}

