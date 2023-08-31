package cs211.project.controllers;


import cs211.project.services.FXRouter;
import cs211.project.services.LoadTopbarComponent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class WelcomeController {
    @FXML private AnchorPane topBarAnchorPane;

    @FXML private void initialize() {
        new LoadTopbarComponent(topBarAnchorPane, true);
    }

    @FXML protected void onSignInButtonClick()  throws IOException {
        FXRouter.goTo("sign-in");
    }
}
