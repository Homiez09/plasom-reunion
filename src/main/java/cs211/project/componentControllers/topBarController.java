package cs211.project.componentControllers;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class topBarController {
    @FXML Button backButton;
    @FXML private void initialize() {
        System.out.println(backButton.getProperties());
    }

    @FXML protected void onBackButtonClick() {
        System.out.println("Back Clicked");
        try {
            FXRouter.goTo("welcome");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML protected void onInstructionButtonClick() {
        System.out.println("Instruction Clicked");
    }

    @FXML protected void onCurrentEventButtonClick() {

        try {
            FXRouter.goTo("home-guest");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML protected void onDevProfileButtonClick() {
        try {
            FXRouter.goTo("dev-profile");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
