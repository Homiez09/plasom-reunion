package cs211.project.controllers.component.teamControllers.teamboxControllers;

import cs211.project.controllers.view.team.AllTeamController;
import cs211.project.models.Event;
import cs211.project.models.Team;
import cs211.project.models.User;
import cs211.project.models.collections.TeamList;
import cs211.project.services.FXRouter;
import cs211.project.services.JoinTeamMap;
import cs211.project.services.TeamListDataSource;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;

public class TeamBox1Controller {
    @FXML private ImageView roleImageView, peopleImageView, activeImageView, faceImageView, bookMarkImageView, manageTeamImageView;
    @FXML private Label  teamIdLabel, teamNameLabel, roleLabel, onlineLabel, participantsLabel, bookmarkLabel;
    @FXML private AnchorPane memberShipAnchorPane, participantsAnchorPane;
    @FXML private ComboBox menuDropDown;
    private Image unBookMarkIcon, bookMarkIcon;
    private final User user = (User) FXRouter.getData();
    private final Event event = (Event) FXRouter.getData2();
    private boolean bookmarked = false, initBookMarkCheck = false;
    JoinTeamMap joinTeamMap = new JoinTeamMap();
    HashMap<String, TeamList> teamListHashMap;
    TeamList teamList;
    AllTeamController allTeamController;

    @FXML private void initialize() {
        loadIcon();

        memberShipAnchorPane.setVisible(false);
        participantsAnchorPane.setVisible(false);
    }

    @FXML
    private void onBookMarkClick(){
        teamListHashMap = joinTeamMap.readData();
        teamList = teamListHashMap.get(user.getUsername());
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
        participantsAnchorPane.setVisible(false);
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

    @FXML private void onParticipantsEntered(){
        participantsAnchorPane.setVisible(true);
        memberShipAnchorPane.setVisible(false);
    }

    @FXML private void onParticipantsExited(){
        participantsAnchorPane.setVisible(false);
    }

    @FXML
    protected void onMenuDropDownComponentClick() {
        menuDropDown.show();
    }

    public void setSelectTeamController(AllTeamController allTeamController) {
        this.allTeamController = allTeamController;
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

    private void initBookmark() {
        if (initBookMarkCheck) return;
        bookmarked = bookmarkLabel.getText().equals("true");
        initBookMarkCheck = true;
    }

    private void deleteTeam(){
        HashMap<String, TeamList> teamHashMap = joinTeamMap.readData();
        TeamListDataSource dataSource = new TeamListDataSource("data","team-list.csv");
        TeamList teamList = dataSource.readData();

        Team team = teamList.findTeamByID(teamIdLabel.getText());
        teamList.removeTeam(team);

        for (User user : team.getMemberList().getUsers()) {
            teamHashMap.get(user.getUsername()).removeTeam(teamIdLabel.getText());
        }

        joinTeamMap.writeData(teamHashMap);
        dataSource.writeData(teamList);

        allTeamController.teamBoxView("teamBox1");
    }

    private void leaveTeam(){
        HashMap<String, TeamList> teamHashMap = joinTeamMap.readData();
        if (teamHashMap.containsKey(user.getUsername())) {
            teamHashMap.get(user.getUsername()).removeTeam(teamIdLabel.getText());
        }joinTeamMap.writeData(teamHashMap);
        allTeamController.teamBoxView("teamBox1");
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
        AnimateImageView(manageTeamImageView);

        bookMarkImageView.setImage(unBookMarkIcon);
    }

    private void AnimateImageView(ImageView imageView) {
        ScaleTransition hoverImageView = new ScaleTransition(Duration.seconds(0.01), imageView);
        hoverImageView.setToX(1.2);
        hoverImageView.setToY(1.2);
        ScaleTransition defaultImageView = new ScaleTransition(Duration.seconds(0.01), imageView);
        defaultImageView.setToX(1);
        defaultImageView.setToY(1);
        imageView.setOnMouseEntered(event -> {hoverImageView.play();});
        imageView.setOnMouseExited(event -> {defaultImageView.play();});
    }
}
