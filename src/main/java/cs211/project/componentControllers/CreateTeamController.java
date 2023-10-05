package cs211.project.componentControllers;

import cs211.project.models.Event;
import cs211.project.models.Team;
import cs211.project.models.User;
import cs211.project.models.collections.TeamList;
import cs211.project.services.FXRouter;
import cs211.project.services.JoinTeamMap;
import cs211.project.services.TeamListDataSource;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;

public class CreateTeamController {
    @FXML private Spinner<Integer> startHourSpinner, startMinuteSpinner, endHourSpinner, endMinuteSpinner, numMemberSpinner;
    @FXML private ChoiceBox<String> startDateChoiceBox, endDateChoiceBox;
    @FXML private DatePicker startDatePicker, endDatePicker;
    @FXML private AnchorPane firstPage, secondPage;
    @FXML private TextField teamNameTextField;
    @FXML private TextArea descriptionTextArea;
    @FXML private Label countDescriptionLabel,dateRequirementLabel, summaryPeriodLabel, errorContinueLabel, nameRequirementLabel;
    @FXML private ImageView teamNameReqImageView;

    protected String formattedCurrentHour, startAmPm, endAmPm, startDateFormat, endDateFormat;
    protected String teamName, description;

    protected String[] time = {"AM", "PM"};

    private LocalDateTime currentDateTime = LocalDateTime.now();
    protected LocalDateTime startDateTime, endDateTime;
    protected LocalDate startDate, endDate;

    private SpinnerValueFactory<Integer> startHourSpin, endHourSpin;
    protected SpinnerValueFactory<Integer> numMemberSpin;

    private final int MAX_TEAM_NAME_LIMIT = 50, MAX_DESCRIPTION_LIMIT = 280;
    protected int currentMinute, startHour, startMinute, endHour, endMinute;
    protected int countInit = 0;

    private boolean teamNameRequirement = false, dateValidateRequirement = false;


    TeamListDataSource datasource;
    TeamList teamList;

    User user = (User) FXRouter.getData();
    Event event = (Event) FXRouter.getData2();

    JoinTeamMap joinTeamMap = new JoinTeamMap();
    TeamListDataSource teamListDataSource = new TeamListDataSource("data", "team-list.csv");

    @FXML
    private void initialize() {
        datasource = new TeamListDataSource("data","team-list.csv");
        teamList = datasource.readData();

        dataInit();

        validateDateTime();
        choiceBoxInit();
        timeInit();

        validatePeriod();
        checkDateReq();

        maximumLengthField();
        showFocusRequirement();
    }

    private void setSecondPage(){
        numMemberReq();
    }

    private void showFocusRequirement(){
        teamNameTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                checkTeamNameReq();
            }
        });
    }
    private void checkTeamNameReq(){
        teamName = teamNameTextField.getText();
        teamNameRequirement = !teamName.isEmpty() && teamList.findTeamByNameInEvent(teamName, event.getEventID()) == null;
        if(teamName.isEmpty()){
            teamNameReqImageView.setVisible(false);
            nameRequirementLabel.setVisible(true);
            nameRequirementLabel.setText("Incorrect Team name.");
        }else if(teamList.findTeamByNameInEvent(teamName, event.getEventID()) != null){
            teamNameReqImageView.setVisible(false);
            nameRequirementLabel.setVisible(true);
            nameRequirementLabel.setText("Duplicate Team name. Please use another name.");
        }else{
            teamNameReqImageView.setVisible(true);
            nameRequirementLabel.setVisible(false);
        }
    }
    private void numMemberReq(){
        numMemberSpin = new SpinnerValueFactory.IntegerSpinnerValueFactory(2,1000000000);
        numMemberSpinner.setValueFactory(numMemberSpin);
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
        errorContinueLabel.setVisible(false);
        dateValidateRequirement = false;
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

        startDateFormat = startDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm").withLocale(Locale.US));
        endDateFormat = endDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm").withLocale(Locale.US));


        countInit++;
        if (startDateTime.isBefore(currentDateTime) || endDateTime.isBefore(startDateTime)) {
            if(countInit > 1){
                dateRequirementLabel.setVisible(true);
                dateRequirementLabel.setText("Please specify the correct Team period.");
                dateValidateRequirement = false;
            }else{
                dateValidateRequirement = false;
                dateRequirementLabel.setVisible(false);
            }summaryPeriodLabel.setVisible(false);
        } else {
            dateValidateRequirement = true;
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

    @FXML private void onKeyTeamName(){
        checkTeamNameReq();
    }
    @FXML protected void onKeyDescriptionCountText(){
            description = descriptionTextArea.getText();
            countDescriptionLabel.setText(String.valueOf((int) description.length()));
            if (description.length() >= 280) {
                if (description.length() > 280) {
                    countDescriptionLabel.setStyle("-fx-text-fill: red ");
                } else {
                    countDescriptionLabel.setStyle("");
                }
            }else {
                countDescriptionLabel.setStyle("");
            }
    }


    @FXML private void onCancelButtonClick() throws IOException {
        teamNameTextField.setText("");
        descriptionTextArea.setText("");

        FXRouter.goTo("select-team", user, event);
    }
    @FXML private void onContinueButtonClick(){
        checkTeamNameReq();
        checkDateReq();
        if(teamNameRequirement && dateValidateRequirement){
            dataInit();
            setSecondPage();

            firstPage.setVisible(false);
            secondPage.setVisible(true);
        }else{
            checkTeamNameReq();
            checkDateReq();
            dateRequirementLabel.setVisible(true);
            summaryPeriodLabel.setVisible(false);
            errorContinueLabel.setVisible(true);
            if(!dateValidateRequirement){
                dateRequirementLabel.setText("Please specify the correct Team period.");
            }else{
                dateRequirementLabel.setVisible(false);
            }
        }
    }
    @FXML protected void onCreateTeamButtonClick() throws IOException {
        checkTeamNameReq();
        if(teamNameRequirement) {
            HashMap<String, TeamList> teamListHashMap = joinTeamMap.readData();

            TeamList teamList = teamListDataSource.readData();
            Team team = teamList.addTeam(event.getEventID(), user, teamNameTextField.getText(), descriptionTextArea.getText(), startDateFormat, endDateFormat, numMemberSpinner.getValue());

            if (teamListHashMap.containsKey(user.getUsername())) {
                TeamList teamListJoin = new TeamList(teamListHashMap.get(user.getUsername()));
                teamListJoin.addTeam(team);
                teamListJoin.updateRole(team.getTeamID(), "Owner");
                teamListHashMap.put(user.getUsername(), teamListJoin);
            } else {
                TeamList teamListJoin = new TeamList();
                teamListJoin.addTeam(team);
                teamListJoin.updateRole(team.getTeamID(), "Owner");
                teamListHashMap.put(user.getUsername(), teamListJoin);
            }

            teamListDataSource.writeData(teamList);
            joinTeamMap.writeData(teamListHashMap);
            FXRouter.goTo("select-team", user, event);
        }else{
            checkTeamNameReq();
        }
    }
    @FXML private void onBackButtonClick(){
        firstPage.setVisible(true);
        secondPage.setVisible(false);
    }



    private void dataInit(){
        secondPage.setVisible(false);
        errorContinueLabel.setVisible(false);
        dateRequirementLabel.setVisible(false);
        summaryPeriodLabel.setVisible(false);
        nameRequirementLabel.setVisible(false);
        teamNameReqImageView.setVisible(false);

        Image checkBoxIcon = new Image(getClass().getResourceAsStream("/images/icons/team/create-team/check_box.png"));
        teamNameReqImageView.setImage(checkBoxIcon);
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
}


