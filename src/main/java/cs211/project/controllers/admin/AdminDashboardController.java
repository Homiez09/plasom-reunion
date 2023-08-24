package cs211.project.controllers.admin;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;

public class AdminDashboardController {
    @FXML private TabPane mainTab;
    @FXML private Tab menu1Tab, menu2Tab;
    @FXML private Button menu1, menu2, menu3, menu4;

    @FXML private void initialize() {
        ButtonSelectGraphic(1);
    }

    @FXML protected void onMenuOneClick() {
        mainTab.getSelectionModel().select(menu1Tab);
        ButtonSelectGraphic(1);
    }

    @FXML protected void onMenuTwoClick() {
        mainTab.getSelectionModel().select(menu2Tab);
        ButtonSelectGraphic(2);
    }

    @FXML protected void onMenuThreeClick() {
//        mainTab.getSelectionModel().select(menu3Tab);
        ButtonSelectGraphic(3);
    }

    @FXML protected void onMenuFourClick() {
//        mainTab.getSelectionModel().select(menu4Tab);
        ButtonSelectGraphic(4);
    }

    @FXML protected void onLogoutButtonClick() {
        try {
            FXRouter.goTo("welcome");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void ButtonSelectGraphic(int page) {
        ResetSelectGraphic();
        switch(page) {
            case 1:
                menu1.setStyle("-fx-background-color: #FFE4B8");
                break;
            case 2:
                menu2.setStyle("-fx-background-color: #FFE4B8");
                break;
            case 3:
                menu3.setStyle("-fx-background-color: #FFE4B8");
                break;
            case 4:
                menu4.setStyle("-fx-background-color: #FFE4B8");
                break;
            default:
                ResetSelectGraphic();
                menu1.setStyle("-fx-background-color: #FFE4B8");
                break;
        }
    }

    void ResetSelectGraphic() {
        menu1.setStyle("-fx-background-color: transparent");
        menu2.setStyle("-fx-background-color: transparent");
        menu3.setStyle("-fx-background-color: transparent");
        menu4.setStyle("-fx-background-color: transparent");
    }

}
