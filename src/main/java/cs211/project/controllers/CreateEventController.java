package cs211.project.controllers;

import cs211.project.models.User;
import cs211.project.models.Event;
import cs211.project.models.collections.EventList;
import cs211.project.services.Datasource;
import cs211.project.services.EventListDataSource;
import cs211.project.services.FXRouter;
import cs211.project.services.LoadNavbarComponent;
import javafx.event.ActionEvent;
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
    @FXML private Label headCreateEventLabel,errorCapacityLabel,errorStartLabel,errorEndLabel,errorTimeLabel;
    @FXML private ChoiceBox<String> eventTagChoiceBox;
    @FXML private ImageView uploadImageView;
    @FXML private TextField eventNameTextField,eventCapTextField,eventLocationTextField;
    @FXML private TextArea eventDescriptionTextArea;
    @FXML private DatePicker eventStartDatePick,eventEndDatePick;
    @FXML private Spinner<Integer> eventStartHourSpinner,eventEndHourSpinner, eventStartMinuteSpinner,eventEndMinuteSpinner;
    @FXML private Button submitButton;
    private Event thisEvent = (Event) FXRouter.getData2();
    private User user = (User) FXRouter.getData();
    private String newEventImagePath = null;
    private Datasource<EventList> eventListDatasource;
    private EventList eventList;

    @FXML private   void initialize() {
        this.eventListDatasource = new EventListDataSource();
        this.eventList = eventListDatasource.readData();
        new LoadNavbarComponent(user, navbarAnchorPane);
        setTime(eventStartDatePick);
        setTime(eventEndDatePick);
        setSpinner(eventStartHourSpinner,23);
        setSpinner(eventEndHourSpinner,23);
        setSpinner(eventStartMinuteSpinner,59);
        setSpinner(eventEndMinuteSpinner,59);

        eventTagChoiceBox.getItems().addAll("Animal","Art","Business","Conference","Education","Food & Drink","Music","Performance","Seminar","Sport","Workshop");

        setPage();
        timeCheck();
        limitCharacter();
    }

    // create and edit event
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

    private void setPage() {
        errorStartLabel.setVisible(false);
        errorEndLabel.setVisible(false);
        errorTimeLabel.setVisible(false);
        if(thisEvent == null) {
            headCreateEventLabel.setText("Create your own event!");
        } else {
            headCreateEventLabel.setText("Edit event");
            showEventDetail(thisEvent);
        }
    }

    @FXML protected void onBackButtonClick() {
        if (thisEvent == null) {
            try {
                FXRouter.goTo("all-events",user);
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
        if (!eventCapTextField.getText().equals("") && !eventCapTextField.getText().matches("[0-9]+")) {
            errorCapacityLabel.setText("Capacity must be integer only.");
            errorCapacityLabel.setVisible(true);
            return;
        }
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
            int numMember;
            try {
                numMember = Integer.parseInt(numMemberString);
            }catch (NumberFormatException e){
                numMember = -1;
            }
            if (numMember > 0 ){

                eventList.createEvent(eventNameString,eventHost,eventImagePath,
                        eventTag,startDate,endDate,eventDescriptionString,
                        eventLocationString,numMember);
            }else {
                eventList.createEvent(  eventNameString,eventHost,eventImagePath,
                        eventTag,startDate,endDate,
                        eventDescriptionString,eventLocationString);
            }
            eventListDatasource.writeData(eventList);
        } else {
            eventList.changeName(thisEvent,eventNameTextField.getText());
            eventList.changeDescription(thisEvent,eventDescriptionTextArea.getText().replace("\n"," "));
            eventList.changeSlotMember(thisEvent,Integer.parseInt(eventCapTextField.getText()));
            eventList.changeDateStart(thisEvent,formatTime(eventStartDatePick,eventStartHourSpinner,eventStartMinuteSpinner));
            eventList.changeDateEnd(thisEvent,formatTime(eventEndDatePick,eventEndHourSpinner,eventEndMinuteSpinner));
            eventList.changeTag(thisEvent,eventTagChoiceBox.getValue());
            if (newEventImagePath != null) {
                eventList.changeImagePath(thisEvent,newEventImagePath);
            }
            eventListDatasource.writeData(eventList);
        }

        try {
            FXRouter.goTo("my-event",user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setPageHeader() {
        if(thisEvent == null) {
            headCreateEventLabel.setText("Create your own event!");
        } else {
            headCreateEventLabel.setText("Edit event");
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
        LocalDate datePick;
        int Hour = hour.getValue();
        int Minute = minute.getValue();
        datePick = datePicker.getValue();
        LocalTime Time = LocalTime.of(Hour, Minute);
        LocalDateTime dateTime = datePick.atTime(Time);
        return dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    private void timeCheck() {
        errorStartLabel.setVisible(false);
        errorEndLabel.setVisible(false);
        eventStartDatePick.focusedProperty().addListener((observable, oldValue, newValue) -> {
            validateDate();
        });

        eventEndDatePick.focusedProperty().addListener((observable, oldValue, newValue) -> {
            validateDate();
        });
        eventStartHourSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            validateTime();
        });
        eventStartMinuteSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            validateTime();
        });
        eventEndHourSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            validateTime();
        });
        eventEndMinuteSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            validateTime();
        });
    }

    private void validateDate() {
        errorStartLabel.setText("Invalid Date");
        errorEndLabel.setText("Invalid Date");
        int startHour = eventStartHourSpinner.getValue();
        int endHour = eventEndHourSpinner.getValue();
        int startMinute = eventStartMinuteSpinner.getValue();
        int endMinute = eventEndMinuteSpinner.getValue();
        LocalDateTime currentDateTime = LocalDateTime.now();

        LocalDate startDate = eventStartDatePick.getValue();
        LocalDate endDate = eventEndDatePick.getValue();

        LocalDateTime startTime = LocalDateTime.of(startDate, LocalTime.of(startHour, startMinute));
        LocalDateTime endTime = LocalDateTime.of(endDate, LocalTime.of(endHour, endMinute));

        errorStartLabel.setVisible(!startTime.isAfter(currentDateTime));
        errorEndLabel.setVisible(!endTime.isAfter(currentDateTime));

        submitButton.setDisable(!(startTime.isAfter(currentDateTime) && endTime.isAfter(currentDateTime)));

    }

    private void validateTime() {
        errorTimeLabel.setText("Invalid Time");
        int startHour = eventStartHourSpinner.getValue();
        int endHour = eventEndHourSpinner.getValue();
        int startMinute = eventStartMinuteSpinner.getValue();
        int endMinute = eventEndMinuteSpinner.getValue();

        LocalTime startTime = LocalTime.of(startHour, startMinute);
        LocalTime endTime = LocalTime.of(endHour, endMinute);
        boolean isTimeValid = startTime.isBefore(endTime);

        if (eventStartDatePick.getValue() != null && eventEndDatePick.getValue() != null) {
            LocalDateTime startDate = eventStartDatePick.getValue().atStartOfDay();
            LocalDateTime endDate = eventEndDatePick.getValue().atStartOfDay();
            if (startDate.equals(endDate) ){

                errorTimeLabel.setVisible(!isTimeValid);
                submitButton.setDisable(!isTimeValid);
            }
        }



    }

    private void setTime(DatePicker datePicker){
        datePicker.setValue(LocalDate.now());
    }

    private void limitCharacter() {

        errorCapacityLabel.setVisible(false);
        eventNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (eventNameTextField.getText().length() > 30) {
                eventNameTextField.setText(oldValue);}
        });
        eventLocationTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (eventLocationTextField.getText().length() > 50) {eventLocationTextField.setText(oldValue);}
        });
    }

}
