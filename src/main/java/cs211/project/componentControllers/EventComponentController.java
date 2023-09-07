package cs211.project.componentControllers;

import cs211.project.controllers.MyEventController;
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
import java.text.SimpleDateFormat;

public class EventComponentController extends MyEventController {
    @FXML
    Label eventnameLabel,startdateLabel,enddateLabel,memberLabel;
    @FXML
    ImageView eventImageView;
    @FXML
    AnchorPane eventAnchorPane;
    @FXML
    Button staffButton,editButton,viewButton;
    private User currentUser = (User) FXRouter.getData();
    private Event event;
    @FXML
    public void initialize() {



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
    public void onViewAction(ActionEvent actionEvent){
        try {
            FXRouter.goTo("event", currentUser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setEventData(Event event) {
        this.event = event;
        Image image;
        if (event.isEnd()) {
            buttonVisible(false);
        }else {
            buttonVisible(true);
        }
        image = new Image(getClass().getResource("/images/events/event-default.png").toExternalForm());
        try {
            image = new Image("file:"+event.getEventImagePath(),true);
        }catch (Exception e){
            e.printStackTrace();
        }


        eventImageView.setImage(image);
        eventnameLabel.setText(event.getEventName());

        // Date

        startdateLabel.setText(event.getEventDateStart());
        enddateLabel.setText(event.getEventDateEnd());

        memberLabel.setText(event.getMember() + "");
    }

    public void buttonVisible(Boolean is){
        staffButton.setVisible(is);
        editButton.setVisible(is);
    }
}
