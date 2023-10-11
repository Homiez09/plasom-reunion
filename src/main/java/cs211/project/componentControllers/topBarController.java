package cs211.project.componentControllers;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;

import java.io.IOException;

public class topBarController {
    @FXML private void initialize() {}

    @FXML private void onBackButtonClick() {
        try {
            FXRouter.goTo("welcome", null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML private void onInstructionButtonClick() {
        System.out.println("Instruction Clicked");
    }

    @FXML private void onCurrentEventButtonClick() {
        try {
            FXRouter.goTo("home", null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML private void onDevProfileButtonClick() {
        try {
            FXRouter.goTo("dev-profile", null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
