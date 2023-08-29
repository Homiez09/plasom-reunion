package cs211.project.controllers;

import cs211.project.models.User;
import cs211.project.models.Event;
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
import java.time.format.DateTimeFormatter;

public class CreateEventController {
    @FXML private AnchorPane navbarAnchorPane;
    @FXML private Label headCreateEventLabel;
    @FXML private ChoiceBox<String> eventTagChoiceBox;
    @FXML private ImageView eventImageView;
    @FXML private TextField eventNameTextField,eventCapTextField,eventLocationTextField,
            teamNameTextField,teamMemberCapTextField,activityNameTextField;
    @FXML private TextArea eventDescriptionTextArea,activityDescriptionTextArea;
    @FXML private DatePicker eventStartDatePick,eventEndDatePick,activityStartDatePick,activityEndDatePick;
    @FXML private TableView<String> createTeamTableView,createActivityTableView;
    @FXML
    private Spinner<Integer> eventStartHourSpinner,eventEndHourSpinner,activityStartHourSpinner,activityEndHourSpinner,
            eventStartMinuteSpinner,eventEndMinuteSpinner,activityStartMinuteSpinner,activityEndMinuteSpinner;
    private Event thisEvent = (Event) FXRouter.getData();
    String newEventImagePath;
    private User user = (User) FXRouter.getData();
    @FXML  void initialize() {
        new LoadNavbarComponent(user, navbarAnchorPane);

        setSpinner(eventStartHourSpinner,24);
        setSpinner(eventEndHourSpinner,24);
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
                File destDir = new File("images");
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
                eventImageView.setImage(new Image(target.toUri().toString()));
                this.newEventImagePath = destDir+"/"+filename;
            } catch (IOException e) {
                e.printStackTrace();
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

    @FXML protected void onSubmitBasicInformationClick() {
        if (thisEvent == null) {
            String eventNameString = eventNameTextField.getText().trim();
            String startDate = eventStartDatePick.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String endDate = eventEndDatePick.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String eventDescriptionString = eventDescriptionTextArea.getText();
            String eventLocationString = eventLocationTextField.getText().trim();
            String numMemberString = eventCapTextField.getText().trim();
            int numMember = Integer.parseInt(numMemberString);
            //add event
        }
    }

    @FXML protected void onAddActivityButtonClick() {
        if (thisEvent == null) {
            String activityNameString = activityNameTextField.getText().trim();
            String activityDescriptionString = activityDescriptionTextArea.getText().trim();
            String activityStartDate = activityStartDatePick.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String activityEndDate = activityEndDatePick.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String activityStartTime = activityStartHourSpinner.getValue() + " : " + activityStartMinuteSpinner.getValue();
            String activityEndTime = activityEndHourSpinner.getValue() + " : " + activityEndMinuteSpinner.getValue();
            // add to activity list
        }
    }
    @FXML protected void onAddTeamButtonClick() {
        if (thisEvent == null) {
            String teamNameString = teamNameTextField.getText().trim();
            String teamCapString = teamMemberCapTextField.getText().trim();
            int numTeamMember = Integer.parseInt(teamCapString);
            // add to team list
        }
    }
}
