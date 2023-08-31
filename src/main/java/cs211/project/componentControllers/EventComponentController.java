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
import java.util.Date;

public class EventComponentController extends MyEventController {
    @FXML
    Label eventnameLabel,dateLabel,memberLabel;
    @FXML
    ImageView eventImageView;
    @FXML
    AnchorPane eventAnchorPane;
    @FXML
    Button staffButton,editButton;
    private User currentUser = (User) FXRouter.getData();
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

    public void setEventData(Event event) {
        if (event.isEnd()) {
            buttonVisible(false);
        }else {
            buttonVisible(true);
        }
        if (!event.getEventImagePath().equals("")){
            eventImageView.setImage(new Image(getClass().getResource(event.getEventImagePath()).toExternalForm()));
        }else {
            eventImageView.setImage(new Image(getClass().getResource("/images/event/event-default.png").toExternalForm()));

        }
        eventnameLabel.setText(event.getEventName());

        // Date

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String eventDateStr = event.getEventDateEnd();
        try {
            String formattedDate = dateFormat.format(dateFormat.parse(eventDateStr));
            dateLabel.setText(formattedDate);
        } catch (Exception e) {
            // กรณีที่ไม่สามารถแปลงเป็นวันที่ได้
            dateLabel.setText("Invalid date");
        }

        memberLabel.setText(event.getMember() + "");
    }
    private void clear(){

        eventImageView.setImage(new Image(getClass().getResource("/images/event/event-default.png").toExternalForm()));
        eventnameLabel.setText("");
        dateLabel.setText("");
        memberLabel.setText("");

    }
    public void buttonVisible(Boolean is){
        staffButton.setVisible(is);
        editButton.setVisible(is);
    }
}
