package cs211.project.componentControllers;

import cs211.project.models.Event;
import cs211.project.services.LoadNavbarComponent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CardEventController {

    @FXML
    Label eventnameLabel,descriptionLabel,dateLabel,memberLabel;
    @FXML
    ImageView eventImage;
    private Event event;

    @FXML
    private void initialize() {



    }
    public void setEvent(Event data){
        this.event = data;

        eventnameLabel.setText(event.getEventName());
        // สร้าง SimpleDateFormat สำหรับการแปลงรูปแบบวันที่และเวลา
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMM d", Locale.ENGLISH);

        try {
            Date startDate = inputFormat.parse(event.getEventDateStart());
            String formattedDate = outputFormat.format(startDate);
            dateLabel.setText("Start | " + formattedDate);
        } catch (ParseException e) {
            System.out.println("cant");
        }
        descriptionLabel.setWrapText(true);
        descriptionLabel.setTextOverrun(OverrunStyle.CENTER_WORD_ELLIPSIS );
        descriptionLabel.setText(event.getEventDescription());

        if (event.getSlotMember() != -1){
            memberLabel.setText(event.getMember()+"/"+ event.getSlotMember());
        }else {
            memberLabel.setText(event.getMember()+"");
        }
        Image image = new Image("file:"+event.getEventImagePath(),200,200,true,true);
        if(event.getEventImagePath().equals("null")){
            String imgpath = "/images/events/event-default.png";
            image = new Image(getClass().getResourceAsStream(imgpath),200,200,false,false);
        }
        eventImage.setImage(image);

    }
    public void onJoinEventAcition(ActionEvent actionEvent) {
    }

    public void onJoinStaffButton(ActionEvent actionEvent) {
    }
}
