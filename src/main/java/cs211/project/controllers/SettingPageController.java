package cs211.project.controllers;

import cs211.project.services.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class SettingPageController {
    @FXML
    AnchorPane navbarAnchorPane;
    @FXML
    Pane mainPane,loginsercurityPane,interfacePane;

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
        mainPane.setVisible(true);
        loadTopBarComponent();

    }

    private void ShowPaneInterface(){
        mainPane.setVisible(false);
        loginsercurityPane.setVisible(false);
        interfacePane.setVisible(true);
    }
    private void ShowPaneLogin(){
        mainPane.setVisible(false);
        interfacePane.setVisible(false);
        loginsercurityPane.setVisible(true);
    }


    public void onInterfaceButton(ActionEvent actionEvent) {
        ShowPaneInterface();
    }

    public void onLoginButton(ActionEvent actionEvent) {
        ShowPaneLogin();
    }
}
