package cs211.project.componentControllers;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class topBarController {
    @FXML private void initialize() {}

    @FXML protected void onBackButtonClick() {
        try {
            FXRouter.goTo("welcome", null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML protected void onInstructionButtonClick() {
        System.out.println("Instruction Clicked");
    }

    @FXML protected void onCurrentEventButtonClick() {
        try {
            FXRouter.goTo("home", null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML protected void onDevProfileButtonClick() {
        try {
            FXRouter.goTo("dev-profile", null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
