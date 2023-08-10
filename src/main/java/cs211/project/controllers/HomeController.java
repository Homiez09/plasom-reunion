package cs211.project.controllers;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;

import java.io.IOException;

public class HomeController {
    @FXML
    private  void initialize() {}

    @FXML
    protected void onSignUpButtonClick()
    {
        try {
            FXRouter.goTo("sign-up");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onSignInButtonClick()
    {
        try {
            FXRouter.goTo("sign-in");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
