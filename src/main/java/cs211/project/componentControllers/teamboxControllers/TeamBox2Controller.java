package cs211.project.componentControllers.teamboxControllers;

import cs211.project.models.Team;
import cs211.project.models.User;
import cs211.project.models.collections.TeamList;
import cs211.project.services.FXRouter;
import cs211.project.services.JoinTeamMap;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.HashMap;

public class TeamBox2Controller {

    @FXML private ImageView pointImageView, peopleImageView, roleImageView, activeImageView, faceImageView, bookMarkImageView, manageTeamImageView;
    @FXML private Label numActiveLabel, teamNameLabel, teamIdLabel, roleLabel, bookmarkLabel;
    @FXML private AnchorPane memberShipAnchorPane, participantsAnchorPane;
    @FXML private ComboBox menuDropDown;
    private Image unBookMarkIcon, bookMarkIcon;
    private User user = (User) FXRouter.getData();
    private boolean bookmarked = false, initBookMarkCheck = false;
    JoinTeamMap joinTeamMap = new JoinTeamMap();
    HashMap<String, TeamList> teamListHashMap;
    TeamList teamList;
    @FXML private void initialize() {
        initMenu();
        loadIcon();

        memberShipAnchorPane.setVisible(false);
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
    private void onRoleEntered(MouseEvent event){
        memberShipAnchorPane.setVisible(true);
    }

    @FXML
    private void onRoleExited(MouseEvent event){
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

    private void initMenu() {
        menuDropDown.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue == null) return;
            try {
                goTo((String) newValue);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void initBookmark() {
        if (initBookMarkCheck) return;
        if (bookmarkLabel.getText().equals("true")) {
            bookmarked = true;
        } else {
            bookmarked = false;
        }
        initBookMarkCheck = true;
    }

    private void goTo(String page) throws IOException {
        switch(page) {
            case "Manage Team":
                //print user data
                System.out.println(teamIdLabel.getText());
                System.out.println(bookmarkLabel.getText());
                break;
            case "Delete Team":
                //todo : do something
                break;
            case "Leave Team":
                //todo : do something
                break;
        }
        menuDropDown.getSelectionModel().clearSelection();
    }

    private void loadIcon() {
        unBookMarkIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/bookmark/unbookmark_icon.png"));
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
