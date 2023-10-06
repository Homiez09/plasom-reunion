package cs211.project.controllers;

import cs211.project.models.User;
import cs211.project.models.Event;
import cs211.project.models.collections.EventList;
import cs211.project.services.Datasource;
import cs211.project.services.EventListDataSource;
import cs211.project.services.FXRouter;
import cs211.project.services.LoadNavbarComponent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
//import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CreateEventController {
    @FXML private AnchorPane navbarAnchorPane;
    @FXML private Label headCreateEventLabel;
    @FXML private ChoiceBox<String> eventTagChoiceBox;
    @FXML private ImageView uploadImageView;
    @FXML private TextField eventNameTextField,eventCapTextField,eventLocationTextField;
    @FXML private TextArea eventDescriptionTextArea;
    @FXML private DatePicker eventStartDatePick,eventEndDatePick;
    @FXML
    private Spinner<Integer> eventStartHourSpinner,eventEndHourSpinner, eventStartMinuteSpinner,eventEndMinuteSpinner;
    private Event thisEvent = (Event) FXRouter.getData2();
    private String newEventImagePath = null;
    private final User user = (User) FXRouter.getData();
    private Datasource<EventList> eventListDatasource;
    private EventList eventList;
    @FXML  void initialize() {
        this.eventListDatasource = new EventListDataSource();
        this.eventList = eventListDatasource.readData();
        new LoadNavbarComponent(user, navbarAnchorPane);
        setSpinner(eventStartHourSpinner,23);
        setSpinner(eventEndHourSpinner,23);
        setSpinner(eventStartMinuteSpinner,59);
        setSpinner(eventEndMinuteSpinner,59);

        eventTagChoiceBox.getItems().addAll("Animal","Art","Business","Conference","Education","Food & Drink","Music","Performance","Sport","Workshop");
        CheckDate();
        setPageHeader();
        if (thisEvent != null) {
            showEventDetail(thisEvent);
        }

    }
    @FXML protected void handleUploadButton(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        // SET FILECHOOSER INITIAL DIRECTORY
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        // DEFINE ACCEPTABLE FILE EXTENSION
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images PNG JPG", "*.png", "*.jpg", "*.jpeg"));
        // GET FILE FROM FILECHOOSER WITH JAVAFX COMPONENT WINDOW
        Node source = (Node) event.getSource();
        File file = chooser.showOpenDialog(source.getScene().getWindow());
        if (file != null){
            try {
                // CREATE FOLDER IF NOT EXIST
                File destDir = new File("images/events");
                if (!destDir.exists()) destDir.mkdirs();
                // RENAME FILE
                String[] fileSplit = file.getName().split("\\.");
                String filename = LocalDate.now() + "_"+System.currentTimeMillis() + "."
                        + fileSplit[fileSplit.length - 1];
                Path target = FileSystems.getDefault().getPath(
                        destDir.getAbsolutePath()+System.getProperty("file.separator")+filename
                );
                // COPY WITH FLAG REPLACE FILE IF FILE IS EXIST
                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING );
                // SET NEW FILE PATH TO IMAGE
                uploadImageView.setImage(new Image(target.toUri().toString()));
                this.newEventImagePath = destDir.toString().replace("\\", "/") + "/" + filename;


            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
    }
    private void setSpinner(Spinner<Integer> spinner, int time) {
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,time,0);
        spinner.setValueFactory(valueFactory);
        spinner.setEditable(true);
    }
    private void setPageHeader() {
        if(thisEvent == null) {
            headCreateEventLabel.setText("Create your own event!");
        } else {
            headCreateEventLabel.setText("Edit event");
        }
    }
    @FXML protected void onBackButtonClick() {
        if (thisEvent == null) {
            try {
                FXRouter.goTo("my-event",user);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                FXRouter.goTo("event",user,thisEvent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML protected void onSubmitBasicInformationClick() {
        eventListDatasource = new EventListDataSource();
        eventList = eventListDatasource.readData();
        if (thisEvent == null) {
            String eventNameString = eventNameTextField.getText().trim();
            User eventHost = user;
            String eventTag = eventTagChoiceBox.getValue();
            String startDate = formatTime(eventStartDatePick,eventStartHourSpinner,eventStartMinuteSpinner);
            String endDate = formatTime(eventEndDatePick,eventEndHourSpinner,eventEndMinuteSpinner);
            String eventDescriptionString = eventDescriptionTextArea.getText().replaceAll("\n"," ");
            String eventLocationString = eventLocationTextField.getText().trim();
            String numMemberString = eventCapTextField.getText().trim();
            String eventImagePath = newEventImagePath;

            if (!numMemberString.equals("")){

                int numMember = Integer.parseInt(numMemberString);
                eventList.createEvent(  eventNameString,eventHost,eventImagePath,
                                        eventTag,startDate,endDate,eventDescriptionString,
                                        eventLocationString,numMember);
            }else {
                eventList.createEvent(  eventNameString,eventHost,eventImagePath,
                                        eventTag,startDate,endDate,
                                        eventDescriptionString,eventLocationString);
            }
            //add event
            eventListDatasource.writeData(eventList);
        } else {
            eventList.findEventById(thisEvent.getEventID()).changeName(eventNameTextField.getText());
            eventList.findEventById(thisEvent.getEventID()).changeDescription(eventDescriptionTextArea.getText().replace("\n","\\n"));
            eventList.findEventById(thisEvent.getEventID()).changeSlotMember(Integer.parseInt(eventCapTextField.getText()));
            eventList.findEventById(thisEvent.getEventID()).changeDateStart(formatTime(eventStartDatePick,eventStartHourSpinner,eventStartMinuteSpinner));
            eventList.findEventById(thisEvent.getEventID()).changeDateEnd(formatTime(eventEndDatePick,eventEndHourSpinner,eventEndMinuteSpinner));
            eventList.findEventById(thisEvent.getEventID()).changeTag(eventTagChoiceBox.getValue());
            if (newEventImagePath != null) {
                eventList.findEventById(thisEvent.getEventID()).changeEventImagePath(newEventImagePath);
            }
            eventListDatasource.writeData(eventList);
        }

        try {
            FXRouter.goTo("my-event",user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showEventDetail(Event event) {
        eventNameTextField.setText(event.getEventName());
        eventLocationTextField.setText(event.getEventLocation());
        eventTagChoiceBox.setValue(event.getEventTag());
        eventDescriptionTextArea.setText(event.getEventDescription());
        eventCapTextField.setText(Integer.toString(event.getSlotMember()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime eventStartDateTime = LocalDateTime.parse(event.getEventDateStart(),formatter);
        LocalDateTime eventEndDateTime = LocalDateTime.parse(event.getEventDateEnd(),formatter);
        eventStartDatePick.setValue(eventStartDateTime.toLocalDate());
        eventStartHourSpinner.getValueFactory().setValue(eventStartDateTime.getHour());
        eventStartMinuteSpinner.getValueFactory().setValue(eventStartDateTime.getMinute());
        eventEndDatePick.setValue(eventEndDateTime.toLocalDate());
        eventEndHourSpinner.getValueFactory().setValue(eventEndDateTime.getHour());
        eventEndMinuteSpinner.getValueFactory().setValue(eventEndDateTime.getMinute());
        uploadImageView.setImage(new Image("file:"+event.getEventImagePath(),300,300,true,true));
    }

    private String formatTime(DatePicker datePicker,Spinner<Integer> hour,Spinner<Integer> minute){
        LocalDate DatePick = datePicker.getValue();
        int Hour = hour.getValue();
        int Minute = minute.getValue();
        LocalTime Time = LocalTime.of(Hour, Minute);
        LocalDateTime DateTime = DatePick.atTime(Time);
        return DateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

    }

    private void CheckDate() {
        SettingCheckDate(eventStartDatePick);
        SettingCheckDate(eventEndDatePick);
    }
    private void SettingCheckDate(DatePicker datePicker){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Date Error");
        alert.setHeaderText("Invalid Date Format");
        datePicker.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean lostFocus, Boolean onFocus) {

                if(!onFocus && !datePicker.getEditor().getText().isEmpty()){
                    try {
                        LocalDate selectedDate = datePicker.getValue();
                        LocalDate currentDate = LocalDate.now();

                        if (selectedDate.isBefore(currentDate)) {
                            // ถ้าวันที่ที่ผู้ใช้เลือกน้อยกว่าวันปัจจุบัน
                            // คุณสามารถทำการแจ้งเตือนหรือทำการแก้ไขค่าใน eventStartDatePick ตามที่คุณต้องการ
                            alert.showAndWait();
                            datePicker.setValue(null);
                            datePicker.getEditor().setText("");
                        }
                    } catch (Exception e) {
                        alert.showAndWait();
                        datePicker.setValue(null);
                        datePicker.getEditor().setText("");
                    }
                }
            }
        });
    }
}
