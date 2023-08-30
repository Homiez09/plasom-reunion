package cs211.project.componentControllers;

import cs211.project.models.Event;
import cs211.project.models.User;
import cs211.project.services.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class EventComponentController {
    @FXML
    Label eventnameLabel,dateLabel,memberLabel;
    @FXML
    ImageView eventImageView;
    @FXML
    AnchorPane eventAnchorPane;
    @FXML
    Button leaveButton,staffButton,editButton;
    private User currentUser = (User) FXRouter.getData();
    @FXML
    private void initialize() {


    }

    public void onLeaveAction(ActionEvent actionEvent) {


    }

    public void onStaffAction(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("select-team", currentUser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void onEditAction(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("create-event", currentUser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setEventData(Event event) {
        if (event.isEnd()) {
            buttonVisible(false);
        }else {
            buttonVisible(true);
        }
        eventImageView.setImage(new Image(getClass().getResource(event.getEventImagePath()).toExternalForm()));
        eventnameLabel.setText(event.getEventName());
        dateLabel.setText(event.getEventDateEnd());
        memberLabel.setText(event.getMember() + "");
    }

    public void buttonVisible(Boolean is){
        leaveButton.setVisible(is);
        staffButton.setVisible(is);
        editButton.setVisible(is);
    }
}
