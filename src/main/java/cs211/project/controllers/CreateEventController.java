package cs211.project.controllers;

import cs211.project.models.User;
import cs211.project.models.Event;
import cs211.project.models.collections.EventList;
import cs211.project.services.Datasource;
import cs211.project.services.EventListDataSource;
import cs211.project.services.FXRouter;
import cs211.project.services.LoadNavbarComponent;
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
    @FXML private TextField eventNameTextField,eventCapTextField,eventLocationTextField,
            teamNameTextField,teamMemberCapTextField,activityNameTextField;
    @FXML private TextArea eventDescriptionTextArea,activityDescriptionTextArea;
    @FXML private DatePicker eventStartDatePick,eventEndDatePick,activityStartDatePick,activityEndDatePick;
    @FXML private TableView<String> createTeamTableView,createActivityTableView;
    @FXML
    private Spinner<Integer> eventStartHourSpinner,eventEndHourSpinner,activityStartHourSpinner,activityEndHourSpinner,
            eventStartMinuteSpinner,eventEndMinuteSpinner,activityStartMinuteSpinner,activityEndMinuteSpinner;
    private Event thisEvent = (Event) FXRouter.getData2();
    private String newEventImagePath;
    private User user = (User) FXRouter.getData();
    private Datasource<EventList> eventListDatasource;
    private EventList eventList;
    @FXML  void initialize() {
        eventListDatasource = new EventListDataSource("data","event-list.csv");
        eventList = eventListDatasource.readData();
        new LoadNavbarComponent(user, navbarAnchorPane);

        setSpinner(eventStartHourSpinner,23);
        setSpinner(eventEndHourSpinner,23);
        setSpinner(eventStartMinuteSpinner,59);
        setSpinner(eventEndMinuteSpinner,59);
        setSpinner(activityStartHourSpinner,24);
        setSpinner(activityEndHourSpinner,24);
        setSpinner(activityStartMinuteSpinner,59);
        setSpinner(activityEndMinuteSpinner,59);

        eventTagChoiceBox.getItems().addAll("Art","Music","Sport");
        setPageHeader();
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
                FXRouter.goTo("event",user);
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
        eventListDatasource = new EventListDataSource("data","event-list.csv");
        eventList = eventListDatasource.readData();
        if (thisEvent == null) {
            String eventNameString = eventNameTextField.getText().trim();
            String eventHost = user.getUsername();
            String eventTag = eventTagChoiceBox.getValue();
            String startDate = formatTime(eventStartDatePick,eventStartHourSpinner,eventStartMinuteSpinner);
            String endDate = formatTime(eventEndDatePick,eventEndHourSpinner,eventEndMinuteSpinner);
            String eventDescriptionString = eventDescriptionTextArea.getText();
            String eventLocationString = eventLocationTextField.getText().trim();
            String numMemberString = eventCapTextField.getText().trim();
            if (newEventImagePath == null) {
                this.newEventImagePath = "images/events/event-default.png";
            }
            if (!numMemberString.equals("")){
                int numMember = Integer.parseInt(numMemberString);
                thisEvent = new Event(  eventNameString,eventHost,newEventImagePath,
                        eventTag,startDate,endDate,
                        eventDescriptionString,eventLocationString,numMember);
            }else {
                thisEvent = new Event(  eventNameString,eventHost,newEventImagePath,
                        eventTag,startDate,endDate,
                        eventDescriptionString,eventLocationString);
            }
            //add event


            eventList.getEvents().add(thisEvent);
            eventListDatasource.writeData(eventList);
        }
        try {
            FXRouter.goTo("event-list");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private String formatTime(DatePicker datePicker,Spinner<Integer> hour,Spinner<Integer> minute){
        LocalDate DatePick = datePicker.getValue();
        int Hour = hour.getValue();
        int Minute = minute.getValue();
        LocalTime Time = LocalTime.of(Hour, Minute);
        LocalDateTime DateTime = DatePick.atTime(Time);
        String Date = DateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        return Date;

    }
    @FXML protected void onAddActivityButtonClick() {
        if (thisEvent != null) {
            eventListDatasource = new EventListDataSource("data","event-list.csv");
            eventList = eventListDatasource.readData();

            String activityNameString = activityNameTextField.getText().trim();
            String activityDescriptionString = activityDescriptionTextArea.getText().trim();
            String startDateTime = formatTime(activityStartDatePick,activityStartHourSpinner,activityStartMinuteSpinner);
            String endDateTime = formatTime(activityEndDatePick,activityEndHourSpinner,activityEndMinuteSpinner);
            // add to activity list
//            thisEvent.getActivities().addActivity(activityNameString,activityDescriptionString,startDateTime,endDateTime);


//            thisEvent.getActivities().addActivity(thisEvent.getEventID(),activityNameString,activityDescriptionString,startDateTime,endDateTime);
        }
    }
    @FXML protected void onAddTeamButtonClick() {
        if (thisEvent != null) {
            String teamNameString = teamNameTextField.getText().trim();
            String teamCapString = teamMemberCapTextField.getText().trim();
            int numTeamMember = Integer.parseInt(teamCapString);
            // add to team list
            //thisEvent.getTeams().addTeam();
        }
    }
}
