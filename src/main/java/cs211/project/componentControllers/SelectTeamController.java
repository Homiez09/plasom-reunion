package cs211.project.componentControllers;

import cs211.project.controllers.TeamBoxController;
import cs211.project.models.User;
import cs211.project.services.FXRouter;
import cs211.project.services.LoadNavbarComponent;
import javafx.fxml.FXML;
import javafx.scene.layout.*;



public class SelectTeamController {
    @FXML private AnchorPane navbarAnchorPane;
    @FXML private GridPane teamContainer;

    private User user = (User) FXRouter.getData();
    @FXML
    private void initialize() {
        new LoadNavbarComponent(user, navbarAnchorPane);
    }
}
