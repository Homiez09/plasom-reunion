package cs211.project.componentControllers;

import cs211.project.models.Event;
import cs211.project.models.User;
import cs211.project.models.collections.EventList;
import cs211.project.services.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class eventController {
    @FXML
    Label eventnameLabel,dateLabel,memberLabel;
    @FXML
    ImageView eventImageView;
    private Event event = (Event) FXRouter.getData() ;
    private User user = (User) FXRouter.getData();
    @FXML
    private void initialize() {
        if (event != null){
            System.out.println("Event test");
            showProfile();
        }else {
            System.out.println("NULL");
        }
    }

    private void showProfile(){
        Image image = new Image(getClass().getResource(event.getEventImagePath()).toString(), 25, 25, true, false);
        eventImageView.setImage(image);
        eventnameLabel.setText(event.getEventName());
        dateLabel.setText(event.getEventDateEnd());
        memberLabel.setText(event.getMember() + "");


    }

    public void onLeaveAction(ActionEvent actionEvent) {
    }

    public void onStaffAction(ActionEvent actionEvent) {
    }

    public void onEditAction(ActionEvent actionEvent) {
    }
}
