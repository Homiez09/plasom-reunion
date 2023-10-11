package cs211.project.controllers.view;


import cs211.project.services.FXRouter;
import cs211.project.services.LoadTopBarComponent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class WelcomeController {
    @FXML private AnchorPane topBarAnchorPane;

    @FXML private void initialize() {
        new LoadTopBarComponent(topBarAnchorPane, true);
    }

    @FXML private void onSignInButtonClick()  throws IOException {
        FXRouter.goTo("sign-in");
    }
}
