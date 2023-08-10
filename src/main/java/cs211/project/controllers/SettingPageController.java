package cs211.project.controllers;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SettingPageController {
    @FXML
    AnchorPane navbarAnchorPane;
    private void loadTopBarComponent() {
        FXMLLoader topBarComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/navbar.fxml"));
        try {
            AnchorPane topBarComponent = topBarComponentLoader.load();
            navbarAnchorPane.getChildren().add(topBarComponent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    public void initialize(){
        loadTopBarComponent();

    }




    public void onMyEventClicked(MouseEvent mouseEvent) {

    }

    public void onHomeClicked(MouseEvent mouseEvent) {

    }

    public void onLoginSecurityClicked(MouseEvent mouseEvent) {
    }

    public void onInterfaceClick(MouseEvent mouseEvent) {
    }
}
