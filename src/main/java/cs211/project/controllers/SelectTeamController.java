package cs211.project.controllers;

import cs211.project.models.User;
import cs211.project.services.FXRouter;
import cs211.project.services.LoadNavbarComponent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;


public class SelectTeamController {
    @FXML private AnchorPane navbarAnchorPane;
    @FXML private GridPane teamContainer;
    private User user = (User) FXRouter.getData();
    @FXML
    private void initialize(){
        new LoadNavbarComponent(user, navbarAnchorPane);
    }
}
