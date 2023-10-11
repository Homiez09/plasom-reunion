package cs211.project.controllers.component.teamControllers.manageTeamsController;

import cs211.project.controllers.view.team.SelectTeamController;
import cs211.project.models.Team;
import cs211.project.models.User;
import cs211.project.models.collections.TeamList;
import cs211.project.services.FXRouter;
import cs211.project.services.JoinTeamMap;
import cs211.project.services.TeamListDataSource;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;

public class ManageTeamsBoxController {
    @FXML private ImageView bookMarkImageView, roleImageView, menuImageView;
    @FXML private Label teamNameLabel, roleLabel, teamSizeLabel;
    @FXML private ComboBox menuDropDown;
    private final User user = (User) FXRouter.getData();
    private boolean bookmarked = false, initBookMarkCheck = false;
    private Image unBookMarkIcon, bookMarkIcon;
    private JoinTeamMap joinTeamMap = new JoinTeamMap();
    private HashMap<String, TeamList> teamListHashMap;
    private TeamList teamList;
    private Team team;
    private SelectTeamController selectTeamController;

    @FXML
    private void initialize() {
        unBookMarkIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/bookmark/un_bookmark_icon.png"));
        bookMarkIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/bookmark/bookmark_icon.png"));
    }

    @FXML
    private void onBookMarkClick(){
        teamListHashMap = joinTeamMap.readData();
        teamList = teamListHashMap.get(user.getUsername());

        if (bookmarked) {
            bookMarkImageView.setImage(unBookMarkIcon);
        } else {
            bookMarkImageView.setImage(bookMarkIcon);
        }

        bookmarked = !bookmarked;

        Team teamTemp = teamList.findTeamByID(team.getTeamID());
        teamTemp.setBookmarked(bookmarked);
        joinTeamMap.writeData(teamListHashMap);
        initBookmark();
    }

    @FXML
    private void onBookMarkEntered() {
        initBookmark();
        if(bookmarked) {
            bookMarkImageView.setImage(unBookMarkIcon);
        } else {
            bookMarkImageView.setImage(bookMarkIcon);
        }
    }

    @FXML
    private void onBookMarkExited() {
        if(bookmarked) {
            bookMarkImageView.setImage(bookMarkIcon);
        } else {
            bookMarkImageView.setImage(unBookMarkIcon);
        }
    }

    public void setSelectTeamController(SelectTeamController selectTeamController) {
        this.selectTeamController = selectTeamController;
    }

    public void setTeamData(Team team) {
        this.team = team;
        setData();
    }

    private void setData() {
        teamNameLabel.setText(team.getTeamName());
        roleImageView.setImage(new Image(getClass().getResourceAsStream("/images/icons/team-box/role/" + team.getRole() + ".png")));
        roleLabel.setText(team.getRole());
        teamSizeLabel.setText(String.valueOf(team.getMemberList().getUsers().size())+ " / " + String.valueOf(team.getMaxSlotTeamMember()));

        String bookmark = team.isBookmarked() ? "bookmark_icon" : "un_bookmark_icon";
        bookMarkImageView.setImage(new Image(getClass().getResourceAsStream("/images/icons/team-box/bookmark/" + bookmark +".png")));
        menuImageView.setImage(new Image(getClass().getResourceAsStream("/images/icons/team-box/dot_icon.png")));

        if (team.getRole().equals("Owner")) {
            menuDropDown.getItems().addAll("Manage Team", "Delete Team");
        } else {
            menuDropDown.getItems().addAll("Manage Team", "Leave Team");
        }

        menuDropDown.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue == null) return;

            try {
                if (newValue.equals("Manage Team")) {
                    selectTeamController.startManageTeam(team);
                } else if (newValue.equals("Delete Team")) {
                    deleteTeam();
                } else if (newValue.equals("Leave Team")) {
                    leaveTeam();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        menuImageView.setOnMouseEntered(e -> {
            menuImageView.setScaleX(1.1);
            menuImageView.setScaleY(1.1);
        });

        menuImageView.setOnMouseExited(e -> {
            menuImageView.setScaleX(1);
            menuImageView.setScaleY(1);
        });
    }

    private void deleteTeam(){
        HashMap<String, TeamList> teamHashMap = joinTeamMap.readData();
        TeamListDataSource dataSource = new TeamListDataSource("data","team-list.csv");
        TeamList teamList = dataSource.readData();

        Team teamTemp = teamList.findTeamByID(team.getTeamID());
        teamList.removeTeam(teamTemp);

        for (User user : team.getMemberList().getUsers()) {
            teamHashMap.get(user.getUsername()).removeTeam(team.getTeamID());
        }

        joinTeamMap.writeData(teamHashMap);
        dataSource.writeData(teamList);

        selectTeamController.teamBoxView("teamBox1");
    }

    private void leaveTeam(){
        HashMap<String, TeamList> teamHashMap = joinTeamMap.readData();
        if (teamHashMap.containsKey(user.getUsername())) {
            teamHashMap.get(user.getUsername()).removeTeam(team.getTeamID());
        }joinTeamMap.writeData(teamHashMap);
        selectTeamController.teamBoxView("teamBox1");
    }

    private void initBookmark() {
        if (initBookMarkCheck) return;
        if (team.isBookmarked()) {
            bookmarked = true;
        } else {
            bookmarked = false;
        }
        initBookMarkCheck = true;
    }

}
