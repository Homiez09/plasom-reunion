package cs211.project.controllers;

import cs211.project.services.LoadTopBarComponent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ProfileDeveloperController {
    @FXML private Label nameLabel, nameLabel1, nameLabel2, nameLabel3;
    @FXML private Label nickLabel, nickLabel1, nickLabel2, nickLabel3;
    @FXML private Label studentIdLabel, studentIdLabel1, studentIdLabel2, studentIdLabel3;
    @FXML private ImageView profileImageView, profileImageView1, profileImageView2, profileImageView3;

    @FXML private AnchorPane topBarAnchorPane;

    @FXML private void initialize() {
        new LoadTopBarComponent(topBarAnchorPane);
        showProfile();
    }

    private void showProfile() {
        Image profile = new Image(getClass().getResourceAsStream("/images/profile/develop/ging.png"), 600, 0, true, false);
        nameLabel.setText("จินดามณี ศรีหะรัญ");
        nickLabel.setText("กุ๊งกิ๊ง");
        studentIdLabel.setText("6510405385");
        profileImageView.setImage(profile);

        Image profile1 = new Image(getClass().getResourceAsStream("/images/profile/develop/phum.jpg"), 600, 0, true, false);
        nameLabel1.setText("ภูมิระพี เสริญวณิชกุล");
        nickLabel1.setText("ภูมิ");
        studentIdLabel1.setText("6510405750");
        profileImageView1.setImage(profile1);

        Image profile2 = new Image(getClass().getResourceAsStream("/images/profile/develop/man.jpg"), 600, 0, true, false);
        nameLabel2.setText("ปิยะ กองศรี");
        nickLabel2.setText("แมน");
        studentIdLabel2.setText("6510450666");
        profileImageView2.setImage(profile2);

        Image profile3 = new Image(getClass().getResourceAsStream("/images/profile/develop/ming.jpg"), 600, 0, true, false);
        nameLabel3.setText("ปุญญิศา ธัญญพงษ์");
        nickLabel3.setText("หมิง");
        studentIdLabel3.setText("6510450674");
        profileImageView3.setImage(profile3);
    }
}
