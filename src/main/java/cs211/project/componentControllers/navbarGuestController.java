package cs211.project.componentControllers;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;

import java.io.IOException;

public class navbarGuestController {
    @FXML
    private void initialize() {
    }

    @FXML
    private void onHomeButtonClick() {
        try {
            FXRouter.goTo("home");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void onSignUpButtonClick() {
        try {
            FXRouter.goTo("sign-up");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void onSignInButtonClick() {
        try {
            FXRouter.goTo("sign-in");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}