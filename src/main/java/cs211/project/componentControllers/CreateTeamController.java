package cs211.project.componentControllers;

import cs211.project.models.Event;
import cs211.project.models.User;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CreateTeamController {
    @FXML
    private AnchorPane firstPage, secondPage;
    @FXML
    private ChoiceBox<String> startDateChoiceBox, endDateChoiceBox;
    @FXML
    private DatePicker startDatePicker, endDatePicker;
    @FXML
    private Spinner<Integer> startHourSpinner, startMinuteSpinner, endHourSpinner, endMinuteSpinner, numMemberSpinner;
    @FXML
    private Label dateRequirementLabel, summaryPeriodLabel, errorContinueLabel, nameRequirementLabel;
    @FXML
    private TextField teamNameTextField;
    @FXML private TextArea descriptionTextArea;

    private String teamName, description;
    protected int currentMinute, startHour, startMinute, endHour, endMinute;

    protected String[] time = {"AM", "PM"};

    private LocalDateTime currentDateTime = LocalDateTime.now();
    protected LocalDateTime startDateTime, endDateTime;
    protected LocalDate startDate, endDate;
    protected String formattedCurrentHour, startAmPm, endAmPm;

    protected int numMember;
    private SpinnerValueFactory<Integer> startHourSpin, endHourSpin, numMemberSpin;

    protected int countInit = 0;

    private final int MAX_TEAM_NAME_LIMIT = 30, MAX_DESCRIPTION_LIMIT = 280;

    User user = (User) FXRouter.getData();
    Event event = (Event) FXRouter.getData2();

    @FXML
    private void initialize() {
        dataInit();

        validateDateTime();
        choiceBoxInit();
        timeInit();

        validatePeriod();
        checkDateReq();

        maximumLengthField();
    }

    private void setSecondPage(){
        numMemberRequire();
    }
    private void numMemberRequire(){
        numMemberSpin = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,1000000000);
        numMemberSpinner.setValueFactory(numMemberSpin);
    }

    @FXML private void onCancelButtonClick() throws IOException {
        FXRouter.goTo("select-team", user, event);
    }

    @FXML private void onContinueButtonClick(){
        dataInit();
        setSecondPage();

        firstPage.setVisible(false);
        secondPage.setVisible(true);
    }

    @FXML private void onCreateTeamButtonClick(){

    }

    @FXML private void onBackButtonClick(){
        firstPage.setVisible(true);
        secondPage.setVisible(false);
    }

    private void maximumLengthField(){
        teamNameTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.length() > MAX_TEAM_NAME_LIMIT) {
                teamNameTextField.setText(oldValue);
            }
        });
        descriptionTextArea.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.length() > MAX_DESCRIPTION_LIMIT) {
                descriptionTextArea.setText(oldValue);
            }
            descriptionTextArea.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    descriptionTextArea.setText(oldValue);
                }
            });
        });

    }

    private void dataInit(){
        secondPage.setVisible(false);
        errorContinueLabel.setVisible(false);
        dateRequirementLabel.setVisible(false);
        summaryPeriodLabel.setVisible(false);
        nameRequirementLabel.setVisible(false);
    }

    private void timeInit() {
        formattedCurrentHour = currentDateTime.format(DateTimeFormatter.ofPattern("hh").withLocale(Locale.US));
        if (currentDateTime.getHour() > 11) {
            startDateChoiceBox.setValue("PM");
            endDateChoiceBox.setValue("PM");
        } else {
            startDateChoiceBox.setValue("AM");
            endDateChoiceBox.setValue("AM");
        }
        startDatePicker.setValue(LocalDate.now());
        startHourSpinner.getValueFactory().setValue(Integer.parseInt(formattedCurrentHour));
        currentMinute = currentDateTime.getMinute();
        startMinuteSpinner.getValueFactory().setValue(currentMinute);
    }

    private void choiceBoxInit() {
        startDateChoiceBox.getItems().addAll(time);
        endDateChoiceBox.getItems().addAll(time);

        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());
        startDatePicker.getEditor().setDisable(true);
        endDatePicker.getEditor().setDisable(true);
    }

    private void validateDateTime() {
        startDateChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (startDateChoiceBox.getValue().equals("PM")) {
                startHourSpin = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12);
            } else {
                startHourSpin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 11);
            }
            startHourSpinner.setValueFactory(startHourSpin);
        });

        endDateChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (endDateChoiceBox.getValue().equals("PM")) {
                endHourSpin = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12);
            } else {
                endHourSpin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 11);
            }
            endHourSpinner.setValueFactory(endHourSpin);
        });

        SpinnerValueFactory<Integer> minute = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        SpinnerValueFactory<Integer> endMinute = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);

        startMinuteSpinner.setValueFactory(minute);
        endMinuteSpinner.setValueFactory(endMinute);
    }

    private void validatePeriod() {

        startDate = startDatePicker.getValue();
        endDate = endDatePicker.getValue();

        startHour = startHourSpinner.getValue();
        startMinute = startMinuteSpinner.getValue();
        endHour = endHourSpinner.getValue();
        endMinute = endMinuteSpinner.getValue();

        startAmPm = startDateChoiceBox.getValue();
        endAmPm = endDateChoiceBox.getValue();

        if (startAmPm.equals("PM")) {
            if (startHour != 12) {
                startHour += 12;
            }
        } else if (startHour == 12) {
            startHour = 0;
        }

        if (endAmPm.equals("PM")) {
            if (endHour != 12) {
                endHour += 12;
            }
        } else if (endHour == 12) {
            endHour = 0;
        }

        currentDateTime = LocalDateTime.now().withSecond(0).withNano(0);
        startDateTime = LocalDateTime.of(startDate, LocalTime.of(startHour, startMinute));
        endDateTime = LocalDateTime.of(endDate, LocalTime.of(endHour, endMinute));

        countInit++;
        if (startDateTime.isBefore(currentDateTime) || endDateTime.isBefore(startDateTime)) {
            if(countInit > 1){
                dateRequirementLabel.setVisible(true);
                dateRequirementLabel.setText("Please specify the correct Team period.");
            }else{
                dateRequirementLabel.setVisible(false);
            }summaryPeriodLabel.setVisible(false);

        } else {
            dateRequirementLabel.setVisible(false);
            summaryPeriodLabel.setVisible(true);
            DateTimeFormatter dateFormatted = DateTimeFormatter.ofPattern("MMMM, dd, yyyy 'from' hh:mm a");
            String formattedStartDate = startDateTime.format(dateFormatted.withLocale(Locale.ENGLISH));
            String formattedEndDate = endDateTime.format(dateFormatted.withLocale(Locale.ENGLISH));
            summaryPeriodLabel.setText("This team will take place on " + formattedStartDate + " until " + formattedEndDate);
        }
    }

    private void checkDateReq(){
        startDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> validatePeriod());
        endDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> validatePeriod());
        startHourSpinner.valueProperty().addListener((observable, oldValue, newValue) -> validatePeriod());
        startMinuteSpinner.valueProperty().addListener((observable, oldValue, newValue) -> validatePeriod());
        endHourSpinner.valueProperty().addListener((observable, oldValue, newValue) -> validatePeriod());
        endMinuteSpinner.valueProperty().addListener((observable, oldValue, newValue) -> validatePeriod());
        startDateChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> validatePeriod());
        endDateChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> validatePeriod());
    }

}


