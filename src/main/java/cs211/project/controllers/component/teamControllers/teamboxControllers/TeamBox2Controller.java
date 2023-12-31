package cs211.project.controllers.component.teamControllers.teamboxControllers;

import cs211.project.controllers.view.team.AllTeamController;
import cs211.project.models.Event;
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
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.HashMap;

public class TeamBox2Controller {
    @FXML private ImageView peopleImageView, roleImageView, activeImageView, faceImageView, bookMarkImageView, manageTeamImageView;
    @FXML private Label onlineLabel, teamNameLabel, teamIdLabel, roleLabel, bookmarkLabel, participantsLabel;
    @FXML private AnchorPane memberShipAnchorPane;
    @FXML private ComboBox menuDropDown;
    private Image unBookMarkIcon, bookMarkIcon;
    private final User user = (User) FXRouter.getData();
    private final Event event = (Event) FXRouter.getData2();
    private boolean bookmarked = false, initBookMarkCheck = false;
    private final JoinTeamMap joinTeamMap = new JoinTeamMap();
    private HashMap<String, TeamList> teamListHashMap;
    private AllTeamController allTeamController;

    @FXML private void initialize() {
        loadIcon();
        memberShipAnchorPane.setVisible(false);
    }

    @FXML
    private void onBookMarkClick(){
        teamListHashMap = joinTeamMap.readData();
        TeamList teamList = teamListHashMap.get(user.getUsername());
        Team team = teamList.findTeamByID(teamIdLabel.getText());

        if (bookmarked) {
            bookMarkImageView.setImage(unBookMarkIcon);
        } else {
            bookMarkImageView.setImage(bookMarkIcon);

        }
        bookmarked = !bookmarked;

        team.setBookmarked(bookmarked);
        joinTeamMap.writeData(teamListHashMap);
        initBookmark();
    }
    @FXML
    private void onRoleEntered(){
        memberShipAnchorPane.setVisible(true);
    }

    @FXML
    private void onRoleExited(){
        memberShipAnchorPane.setVisible(false);
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

    @FXML
    protected void onMenuDropDownComponentClick() {
        menuDropDown.show();
    }

    public void setTeamData(Team team) {
        teamIdLabel.setText(team.getTeamID());
        teamNameLabel.setText(team.getTeamName());
        roleLabel.setText(team.getRole());
        onlineLabel.setText(String.valueOf(team.getMemberOnline().getUsers().size()));
        participantsLabel.setText(team.getMemberList().getUsers().size() +" / "+team.getMaxSlotTeamMember());

        faceImageView.setOnMouseClicked(e -> {
            try {
                this.user.setRole(team.getRole());
                FXRouter.goTo("team-activity", this.user, this.event, team);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        if (team.getRole().equals("Owner")) {
            menuDropDown.getItems().addAll("Manage Team", "Delete Team");
        } else {
            menuDropDown.getItems().addAll("Manage Team", "Leave Team");
        }

        menuDropDown.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue == null) return;

            try {
                if (newValue.equals("Manage Team")) {
                    allTeamController.startManageTeam(team);
                } else if (newValue.equals("Delete Team")) {
                    deleteTeam();
                } else if (newValue.equals("Leave Team")) {
                    leaveTeam();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        if (team.isBookmarked()) {
            bookMarkImageView.setImage(new Image(getClass().getResourceAsStream("/images/icons/team-box/bookmark/bookmark_icon.png")));
            bookmarkLabel.setText(String.valueOf(team.isBookmarked()));
        } else {
            bookMarkImageView.setImage(new Image(getClass().getResourceAsStream("/images/icons/team-box/bookmark/un_bookmark_icon.png")));
            bookmarkLabel.setText(String.valueOf(team.isBookmarked()));
        }

        roleImageView.setImage(new Image(getClass().getResourceAsStream("/images/icons/team-box/role/" + team.getRole() + ".png")));
    }

    public void setSelectTeamController(AllTeamController allTeamController) {
        this.allTeamController = allTeamController;
    }

    private void initBookmark() {
        if (initBookMarkCheck) return;
        bookmarked = bookmarkLabel.getText().equals("true");
        initBookMarkCheck = true;
    }

    private void deleteTeam(){
        TeamListDataSource dataSource = new TeamListDataSource("data","team-list.csv");
        TeamList teamList = dataSource.readData();
        teamList.removeTeam(teamIdLabel.getText());
        dataSource.writeData(teamList);
    }

    private void leaveTeam(){
        teamListHashMap = joinTeamMap.readData();
        HashMap<String, TeamList> teamHashMap = new HashMap<>();
        for (String username : teamListHashMap.keySet()){
            TeamList teamList = new TeamList(teamListHashMap.get(username));
            if (username.equals(user.getUsername())) {
                teamList.removeTeam(teamIdLabel.getText());
            }teamHashMap.put(username, teamList);
        }joinTeamMap.writeData(teamHashMap);
        menuDropDown.getSelectionModel().clearSelection();
        try {
            FXRouter.goTo("all-team", user, event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadIcon() {
        unBookMarkIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/bookmark/un_bookmark_icon.png"));
        bookMarkIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/bookmark/bookmark_icon.png"));
        Image activeIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/active_icon.png"));
        activeImageView.setImage(activeIcon);
        Image faceIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/face_icon.png"));
        faceImageView.setImage(faceIcon);
        Image peopleIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/people_icon.png"));
        peopleImageView.setImage(peopleIcon);
        Image manageIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/dot_icon.png"));
        manageTeamImageView.setImage(manageIcon);

        bookMarkImageView.setImage(unBookMarkIcon);
    }

}
