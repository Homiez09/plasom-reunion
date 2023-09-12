package cs211.project.componentControllers;

import cs211.project.models.Event;
import cs211.project.models.Team;
import cs211.project.models.User;
import cs211.project.models.collections.UserList;
import cs211.project.services.FXRouter;
import cs211.project.services.UserListDataSource;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class TeamBox1Controller {

    @FXML private ImageView pointImageView, peopleImageView, roleImageView, activeImageView, faceImageView, bookMarkImageView, manageTeamImageView;
    @FXML private Label numActiveLabel, teamNameLabel, teamIdLabel;
    @FXML private AnchorPane memberShipAnchorPane, participantsAnchorPane;
    @FXML private ComboBox menuDropDown;
    private Image unBookMarkIcon, bookMarkIcon;
    private User user = (User) FXRouter.getData();
    private UserList userList;
    UserListDataSource datasource = new UserListDataSource("data","user-list.csv");

    @FXML private void initialize() {
        userList = datasource.readData();

        initMenu();
        loadIcon();

        memberShipAnchorPane.setVisible(false);
        participantsAnchorPane.setVisible(false);
    }

    @FXML
    private void onBookMarkClick(){
        if(bookMarkImageView.getImage() == unBookMarkIcon) {
            bookMarkImageView.setImage(bookMarkIcon);
        }else {
        bookMarkImageView.setImage(unBookMarkIcon);
        }
    }
    @FXML
    private void onRoleEntered(MouseEvent event){
        memberShipAnchorPane.setVisible(true);
        participantsAnchorPane.setVisible(false);
    }

    @FXML
    private void onRoleExited(MouseEvent event){
        memberShipAnchorPane.setVisible(false);
    }

    @FXML
    private void onParticipantsEntered(MouseEvent event){
        participantsAnchorPane.setVisible(true);
        memberShipAnchorPane.setVisible(false);
    }

    @FXML
    private void onParticipantsExited(MouseEvent event){
        participantsAnchorPane.setVisible(false);
    }

    @FXML
    protected void onMenuDropDownComponentClick() {
        menuDropDown.show();
    }

    private void initMenu() {
        String menuOwner[] = {"Manage Team", "Delete Team"};
        String menuMember[] = {"Manage Team", "Leave Team"};


        menuDropDown.getItems().addAll(menuOwner);
        menuDropDown.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue == null) return;
            try {
                goTo((String) newValue);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void goTo(String page) throws IOException {
        switch(page) {
            case "Manage Team":
                //print user data
                System.out.println(teamIdLabel.getText());
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
        Image roleIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/role/member.png"));
        roleImageView.setImage(roleIcon);
        Image faceIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/face_icon.png"));
        faceImageView.setImage(faceIcon);
        Image peopleIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/people_icon.png"));
        peopleImageView.setImage(peopleIcon);
        Image manageIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/dot_icon.png"));
        manageTeamImageView.setImage(manageIcon);

        bookMarkImageView.setImage(unBookMarkIcon);
    }

}
