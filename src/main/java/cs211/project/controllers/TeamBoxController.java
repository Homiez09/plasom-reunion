package cs211.project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TeamBoxController {

    @FXML private ImageView peopleImageView, roleImageView, activeImageView, faceImageView,bookMarkImageView;

    @FXML private Label numActiveLabel, teamNameLabel;

    @FXML private void initialize(){
        loadIcon();
    }

    private void loadIcon(){
        Image bookMarkIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/bookmark_icon.png"));
        bookMarkImageView.setImage(bookMarkIcon);
        Image activeIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/active_icon.png"));
        activeImageView.setImage(activeIcon);
        Image dotIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/dot_icon.png"));
        activeImageView.setImage(dotIcon);
        Image faceIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/face_icon.png"));
        faceImageView.setImage(faceIcon);
        Image peopleIcon = new Image(getClass().getResourceAsStream("/images/icons/team-box/people_icon.png"));
        peopleImageView.setImage(peopleIcon);
    }
}
