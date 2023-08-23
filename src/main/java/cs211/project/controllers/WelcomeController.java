package cs211.project.controllers;


import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class WelcomeController {
    @FXML private AnchorPane topBarAnchorPane;

    @FXML private void initialize() {
        loadTopBarComponent();
    }

    private void loadTopBarComponent() {
        FXMLLoader topBarComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/topbar.fxml"));
        try {
            AnchorPane topBarComponent = topBarComponentLoader.load();
            topBarComponent.getChildren().get(0).setVisible(false);
            topBarAnchorPane.getChildren().add(topBarComponent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML protected void onSignInButtonClick()  throws IOException {
        FXRouter.goTo("sign-in");
    }

    @FXML protected void onSignUpButtonClick() throws IOException {
        FXRouter.goTo("sign-up");
    }
}
