package cs211.project.controllers.view.event;

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
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CreateEventController {
    @FXML private AnchorPane navbarAnchorPane;
    @FXML private Label headCreateEventLabel,errorCapacityLabel,errorStartLabel,errorEndLabel,errorTimeLabel,errorJoinStartLabel,
                        errorJoinEndLabel,errorJoinTimeLabel;
    @FXML private ChoiceBox<String> eventTagChoiceBox;
    @FXML private ImageView uploadImageView;
    @FXML private TextField eventNameTextField,eventCapTextField,eventLocationTextField;
    @FXML private TextArea eventDescriptionTextArea;
    @FXML private DatePicker eventStartDatePick,joinStartDatePick,eventEndDatePick,joinEndDatePick;
    @FXML private Spinner<Integer>  eventStartHourSpinner,eventEndHourSpinner,
                                    eventStartMinuteSpinner,eventEndMinuteSpinner,
                                    joinStartHourSpinner, joinEndHourSpinner,
                                    joinStartMinuteSpinner,joinEndMinuteSpinner;
    @FXML private Button submitButton;
    private final String from = (String) FXRouter.getData3();
    private final Event thisEvent = (Event) FXRouter.getData2();
    private final User user = (User) FXRouter.getData();
    private String newEventImagePath = null;
    private Datasource<EventList> eventListDatasource;
    private EventList eventList;

    @FXML private void initialize() {
        this.eventListDatasource = new EventListDataSource();
        this.eventList = eventListDatasource.readData();
        new LoadNavbarComponent(user, navbarAnchorPane);
        setTime(eventStartDatePick);
        setSpinner(eventStartHourSpinner,23);
        setSpinner(eventStartMinuteSpinner,59);
        setTime(eventEndDatePick);
        setSpinner(eventEndHourSpinner,23);
        setSpinner(eventEndMinuteSpinner,59);
        setTime(joinStartDatePick);
        setSpinner(joinStartHourSpinner,23);
        setSpinner(joinStartMinuteSpinner,59);
        setTime(joinEndDatePick);
        setSpinner(joinEndHourSpinner,23);
        setSpinner(joinEndMinuteSpinner,59);
        eventTagChoiceBox.getItems().addAll("Animal","Art","Business","Conference","Education","Food & Drink","Music","Performance","Seminar","Sport","Workshop");
        setPage();
        limitCharacter();
        setOnFocus();
        validateDateTime();
        updateSubmitButtonState();
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
        errorJoinStartLabel.setVisible(false);
        errorJoinEndLabel.setVisible(false);
        errorJoinTimeLabel.setVisible(false);
        if(thisEvent == null) {
            headCreateEventLabel.setText("Create your own event!");
        } else {
            headCreateEventLabel.setText("Edit event");
            showEventDetail(thisEvent);
        }
    }
    @FXML protected void onBackButtonClick() {
        if (thisEvent == null) {
            if (from.equals("my-event")) {
                try {
                    FXRouter.goTo("my-event", user);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else {
                try {
                    FXRouter.goTo("all-events", user);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            try {
                FXRouter.goTo("event",user,thisEvent,from);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML protected void onSubmitBasicInformationClick() {
        eventListDatasource = new EventListDataSource();
        eventList = eventListDatasource.readData();
        updateSubmitButtonState();
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
            String joinStartDate = formatTime(joinStartDatePick,joinStartHourSpinner,joinStartMinuteSpinner);
            String joinEndDate = formatTime(joinEndDatePick,joinEndHourSpinner,joinEndMinuteSpinner);
            int numMember = Integer.parseInt(numMemberString);


            eventList.createEvent(  eventNameString,eventHost,eventImagePath,
                        eventTag,startDate,endDate,
                        eventDescriptionString,eventLocationString,numMember,joinStartDate,joinEndDate);


        } else {
            eventList.changeName(thisEvent,eventNameTextField.getText());
            eventList.changeDescription(thisEvent,eventDescriptionTextArea.getText().replace("\n"," "));
            eventList.changeSlotMember(thisEvent,Integer.parseInt(eventCapTextField.getText()));
            eventList.changeDateStart(thisEvent,formatTime(eventStartDatePick,eventStartHourSpinner,eventStartMinuteSpinner));
            eventList.changeDateEnd(thisEvent,formatTime(eventEndDatePick,eventEndHourSpinner,eventEndMinuteSpinner));
            eventList.changeTag(thisEvent,eventTagChoiceBox.getValue());
            eventList.changeLocation(thisEvent,eventLocationTextField.getText().trim());
            eventList.changeJoinTimeStart(thisEvent,formatTime(joinStartDatePick,joinStartHourSpinner,joinStartMinuteSpinner));
            eventList.changeJoinTimeEnd(thisEvent,formatTime(joinEndDatePick,joinEndHourSpinner,joinEndMinuteSpinner));
            if (newEventImagePath != null) {
                eventList.changeImagePath(thisEvent,newEventImagePath);
            }
        }
        eventListDatasource.writeData(eventList);
        try {
            FXRouter.goTo("my-event",user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void showEventDetail(Event event) {
        LocalDateTime eventStartDateTime = event.getDateStartAsDate();
        LocalDateTime eventEndDateTime = event.getDateEndAsDate();
        LocalDateTime joinStartDateTime = event.getJoinTimeStartAsDate();
        LocalDateTime joinEndDateTime = event.getJoinTimeEndAsDate();

        eventNameTextField.setText(event.getEventName());
        eventLocationTextField.setText(event.getEventLocation());
        eventTagChoiceBox.setValue(event.getEventTag());
        eventDescriptionTextArea.setText(event.getEventDescription());
        eventCapTextField.setText(Integer.toString(event.getSlotMember()));
        eventStartDatePick.setValue(eventStartDateTime.toLocalDate());
        eventStartHourSpinner.getValueFactory().setValue(eventStartDateTime.getHour());
        eventStartMinuteSpinner.getValueFactory().setValue(eventStartDateTime.getMinute());
        joinStartDatePick.setValue(joinStartDateTime.toLocalDate());
        joinStartHourSpinner.getValueFactory().setValue(joinStartDateTime.getHour());
        joinStartMinuteSpinner.getValueFactory().setValue(joinStartDateTime.getMinute());
        eventEndDatePick.setValue(eventEndDateTime.toLocalDate());
        eventEndHourSpinner.getValueFactory().setValue(eventEndDateTime.getHour());
        eventEndMinuteSpinner.getValueFactory().setValue(eventEndDateTime.getMinute());
        joinEndDatePick.setValue(joinEndDateTime.toLocalDate());
        joinEndHourSpinner.getValueFactory().setValue(joinEndDateTime.getHour());
        joinEndMinuteSpinner.getValueFactory().setValue(joinEndDateTime.getMinute());
        Image image = new Image("file:" + event.getEventImagePath(), 1280, 1280, false, false);
        if (event.getEventImagePath().equals("null")) {
            String imgPath = "/images/home/event2.png";
            image = new Image(getClass().getResourceAsStream(imgPath), 1280, 1280, false, false);
        }
        uploadImageView.setImage(image);
        System.out.println();

    }
    private String formatTime(DatePicker datePicker,Spinner<Integer> hour,Spinner<Integer> minute){
        LocalDate datePick;
        int Hour = hour.getValue();
        int Minute = minute.getValue();
        datePick = datePicker.getValue();
        LocalTime Time = LocalTime.of(Hour, Minute);
        LocalDateTime dateTime = datePick.atTime(Time);
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }
    private void setOnFocus() {
        onFocus(eventNameTextField);
        onFocus(eventLocationTextField);
        onFocus(eventTagChoiceBox);
        onFocus(eventDescriptionTextArea);
        onFocus(eventCapTextField);
        onFocus(eventStartDatePick);
        onFocus(eventStartHourSpinner);
        onFocus(eventStartMinuteSpinner);
        onFocus(joinStartDatePick);
        onFocus(joinStartHourSpinner);
        onFocus(joinStartMinuteSpinner);
        onFocus(eventEndDatePick);
        onFocus(eventEndHourSpinner);
        onFocus(eventEndMinuteSpinner);
        onFocus(joinEndDatePick);
        onFocus(joinEndHourSpinner);
        onFocus(joinEndMinuteSpinner);



    }
    private void onFocus(DatePicker datePicker){
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            validateDateTime();
            updateSubmitButtonState();
        });
    }
    private void onFocus(Spinner<Integer> spinner){
        spinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            validateDateTime();
            updateSubmitButtonState();
        });
    }
    private void onFocus(ChoiceBox<String> choiceBox){
        choiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateSubmitButtonState();
        });

    }
    private void onFocus(TextArea textArea){
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            updateSubmitButtonState();
        });
    }
    private void onFocus(TextField textField){
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            validateCap();
            updateSubmitButtonState();
        });
    }
    private void validateDateTime() {
        setTextInvalidDate(errorStartLabel);
        setTextInvalidDate(errorEndLabel);
        setTextInvalidDate(errorJoinStartLabel);
        setTextInvalidDate(errorJoinEndLabel);
        errorJoinTimeLabel.setText("Invalid Time");
        errorTimeLabel.setText("Invalid Time");

        LocalDateTime currentDateTime =  LocalDateTime.now() ;

        int eventStartHourSpinnerValue = eventStartHourSpinner.getValue();
        int eventEndHourSpinnerValue = eventEndHourSpinner.getValue();
        int eventStartMinuteSpinnerValue = eventStartMinuteSpinner.getValue();
        int eventEndMinuteSpinnerValue = eventEndMinuteSpinner.getValue();

        LocalDate eventStartDatePickValue = eventStartDatePick.getValue();
        LocalDate eventEndDatePickValue = eventEndDatePick.getValue();

        int joinStartHourSpinnerValue = joinStartHourSpinner.getValue();
        int joinEndHourSpinnerValue = joinEndHourSpinner.getValue();
        int joinStartMinuteSpinnerValue = joinStartMinuteSpinner.getValue();
        int joinEndMinuteSpinnerValue = joinEndMinuteSpinner.getValue();

        LocalDate joinStartDatePickValue = joinStartDatePick.getValue();
        LocalDate joinEndDatePickValue = joinEndDatePick.getValue();

        LocalTime startTime = LocalTime.now();
        LocalTime endTime = LocalTime.now();
        LocalTime joinStartTime = LocalTime.now();
        LocalTime joinEndTime = LocalTime.now();

        try {
            startTime = LocalTime.of(eventStartHourSpinnerValue, eventStartMinuteSpinnerValue);
            endTime = LocalTime.of(eventEndHourSpinnerValue, eventEndMinuteSpinnerValue);
            joinStartTime = LocalTime.of(joinStartHourSpinnerValue, joinStartMinuteSpinnerValue);
            joinEndTime = LocalTime.of(joinEndHourSpinnerValue, joinEndMinuteSpinnerValue);
        }catch (DateTimeException e){
            System.err.println("valid value");
        }

        boolean isTimeValid = startTime.isAfter(endTime);
        boolean joinIsTimeValid = joinStartTime.isAfter(joinEndTime);

        LocalDateTime startDateTime = LocalDateTime.of(eventStartDatePickValue, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(eventEndDatePickValue, endTime);
        LocalDateTime joinStartDateTime = LocalDateTime.of(joinStartDatePickValue, joinStartTime);
        LocalDateTime joinEndDateTime = LocalDateTime.of(joinEndDatePickValue, joinEndTime);

        errorStartLabel.setVisible(!startDateTime.isAfter(currentDateTime));
        errorEndLabel.setVisible(!endDateTime.isAfter(currentDateTime));
        errorTimeLabel.setVisible( !endDateTime.isAfter(startDateTime) || (isTimeValid && eventStartDatePickValue.equals(eventEndDatePickValue)));

        errorJoinStartLabel.setVisible(!joinStartDateTime.isBefore(joinEndDateTime));
        errorJoinEndLabel.setVisible(!joinEndDateTime.isBefore(endDateTime));
        errorJoinTimeLabel.setVisible( !joinEndDateTime.isAfter(joinStartDateTime) || (joinIsTimeValid && joinStartDatePickValue.equals(joinEndDatePickValue)));


    }
    private void validateCap(){
        if (!eventCapTextField.getText().equals("") && !eventCapTextField.getText().matches("[0-9]+")) {
            errorCapacityLabel.setText("Capacity must be integer only.");
            errorCapacityLabel.setVisible(true);
        }else{
            errorCapacityLabel.setVisible(false);
        }
    }
    private void setTime(DatePicker datePicker){
        datePicker.setValue(LocalDate.now());
    }
    private void setTextInvalidDate(Label label){
        label.setText("Invalid Date");
    }
    private void updateSubmitButtonState(){
        try {
            String text = eventCapTextField.getText();
            int value = Integer.parseInt(text);
            if (value <= 0) {
                errorCapacityLabel.setText("Capacity must be more than zero.");
                errorCapacityLabel.setVisible(true);
            }else {
                errorCapacityLabel.setVisible(false);
            }
        } catch (NumberFormatException ignored) {}
        submitButton.setDisable(eventNameTextField.getText().equals("") || eventDescriptionTextArea.getText().equals("")
                || eventLocationTextField.getText().equals("") || eventTagChoiceBox.getItems().equals("") || eventCapTextField.getText().equals("")
                || errorCapacityLabel.isVisible() || errorStartLabel.isVisible() || errorEndLabel.isVisible()
                || errorTimeLabel.isVisible() || errorJoinStartLabel.isVisible() || errorJoinEndLabel.isVisible() &&errorJoinTimeLabel.isVisible()
        );
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
