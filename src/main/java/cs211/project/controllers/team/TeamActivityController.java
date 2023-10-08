package cs211.project.controllers.team;

import cs211.project.componentControllers.sideBarControllers.SideBarTeamController;
import cs211.project.models.*;
import cs211.project.models.collections.ActivityTeamList;
import cs211.project.services.ActivityTeamListDataSource;
import cs211.project.services.FXRouter;
import cs211.project.services.LoadNavbarComponent;
import cs211.project.services.team.LoadSideBarComponent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.function.Predicate;

public class TeamActivityController {
    @FXML private AnchorPane navbarAnchorPane, sideBarAnchorPane, mainActivityAnchorPane, createActivityAnchorPane;
    @FXML private Label activityNameLabel, activityDescriptionLabel, activityStartTimeLabel, activityEndTimeLabel, nameRequirementLabel, errorContinueLabel, dateRequirementLabel, countDescriptionLabel, entriesLabel;
    @FXML private TextField activityNameTextField, searchbarTextField;
    @FXML private TextArea descriptionTextArea;
    @FXML private DatePicker startDatePicker, endDatePicker;
    @FXML private Button createActivityButton, deleteButton;
    @FXML private ComboBox statusComboBox;
    @FXML private TableView activityTableView;
    @FXML private ImageView teamNameReqImageView, chatIconImageView, nextImageView, previousImageView;
    @FXML private Spinner<Integer> startHourSpinner, startMinuteSpinner, endHourSpinner, endMinuteSpinner;
    @FXML private ChoiceBox<String> startDateChoiceBox, endDateChoiceBox;
    @FXML private TableColumn<ActivityTeam, String> nameColumn, startTimeColumn, endTimeColumn, descriptionColumn;
    @FXML private TableColumn<ActivityTeam, Boolean> statusColumn;


    private final User user = (User) FXRouter.getData();
    private final Event event = (Event) FXRouter.getData2();
    private final Team team = (Team) FXRouter.getData3();
    private final String[] time = {"AM", "PM"}, status = {"On going", "Complete"};
    private final int MAX_ACTIVITY_NAME_LIMIT = 35, MAX_DESCRIPTION_LIMIT = 280;
    private final ActivityTeamListDataSource activityTeamListDataSource = new ActivityTeamListDataSource("data", "team-activity.csv");
    private ActivityTeamList activityTeamList;

    protected LocalDateTime startDateTime, endDateTime, beforeStartDateTime;
    private LocalDateTime currentDateTime = LocalDateTime.now();
    protected LocalDate startDate, endDate;
    private SpinnerValueFactory<Integer> startHourSpin, endHourSpin;

    protected String[] startTimeParts = {}, endTimeParts = {}, startParts = {}, endParts = {};
    private String startDateFormat, endDateFormat, description, activityName, beforeEditActivityName;
    protected String formattedCurrentHour, startAmPm, endAmPm, beforeStartDateEditFormat, startDateFromCSV, endDateFromCSV;
    private int beforeEditStartHour, beforeEditStartMinute;
    private int currentMinute, startHour, startMinute, endHour, endMinute, countInit = 0;
    private int current_page = 0, max_page;
    private boolean activityNameRequirement = false, dateValidateRequirement = false, editor;

    private LoadSideBarComponent sideBarAnchorPaneLoad;
    private Image chat = new Image(getClass().getResourceAsStream("/images/icons/activity/chat.png"));
    private Image chat_hover = new Image(getClass().getResourceAsStream("/images/icons/activity/chat_hover.png"));
    private ActivityTeam activityTeamSelect;

    @FXML void initialize(){
        activityTeamList = activityTeamListDataSource.readData();

        new LoadNavbarComponent(user, navbarAnchorPane);
        sideBarAnchorPaneLoad = new LoadSideBarComponent();
        sideBarAnchorPane.getChildren().add(sideBarAnchorPaneLoad.getSideBarComponent());
        setSideBar();

        setPageVisible(true);

        dataInit();
        userInit();
        searchbarInit();
        validateDateTime();
        choiceBoxInit();
        checkDateReq();
        showTable();
        checkButtonNextAndPrevious();
        maximumLengthField();
        showFocusRequirement();
    }

    @FXML
    private void onChatButtonEntered() {
        chatIconImageView.setImage(chat_hover);
    }

    @FXML
    private void onChatButtonExited() {
        chatIconImageView.setImage(chat);
    }

    @FXML
    private void onChatButtonClicked() {
        try {
            FXRouter.goTo("team-chat", user, event, team, activityTeamSelect);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML protected void onSaveActivityButtonClick() {
        activityNameRequirement = false;
        checkActivityNameReq();
        checkDateReq();
        if(activityNameRequirement && dateValidateRequirement && !editor){
            checkDescriptionReq();
            activityTeamList.addActivity(new ActivityTeam(team.getTeamID(), activityNameTextField.getText(), description, startDateFormat, endDateFormat));
            activityTeamListDataSource.writeData(activityTeamList);
            try {
                FXRouter.goTo("team-activity", user, event, team);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else if (activityNameRequirement && dateValidateRequirement){
            checkDescriptionReq();
            updateActivity();
            try {
                FXRouter.goTo("team-activity", user, event, team);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            checkActivityNameReq();
            checkDateReq();
            dateRequirementLabel.setVisible(true);
            errorContinueLabel.setVisible(true);
            if(!dateValidateRequirement){
                dateRequirementLabel.setText("Please specify the correct Activity period.");
            }else{
                dateRequirementLabel.setVisible(false);
            }
        }
    }
    @FXML protected void onDeleteActivityButtonClick() {
        ActivityTeam activityTeam = (ActivityTeam) activityTableView.getSelectionModel().getSelectedItem();
        activityTeamList.getActivities().remove(activityTeam);
        activityTeamListDataSource.writeData(activityTeamList);
        try {
            FXRouter.goTo("team-activity", user, event, team);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML protected void onBackClick() {
        try {
            FXRouter.goTo("team-activity", user, event, team);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML protected void onCreateButtonClick() {
        showCreateActivity();
    }
    @FXML protected void onCancelButtonClick() throws IOException {
        FXRouter.goTo("team-activity", user, event, team);
    }

    @FXML private void onKeyActivityName(){
        checkActivityNameReq();
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

    @FXML private void onNextActivityClicked() {
        if (current_page == max_page - 1) return;
        activityTableView.getSelectionModel().select(++current_page);
        checkButtonNextAndPrevious();
    }

    @FXML private void onPreviousActivityClicked() {
        if (current_page == 0) return;
        activityTableView.getSelectionModel().select(--current_page);
        checkButtonNextAndPrevious();
    }

    private void checkButtonNextAndPrevious() {
        if (current_page == max_page - 1) {
            nextImageView.setVisible(false);
        } else {
            nextImageView.setVisible(true);
        }
        if (current_page == 0) {
            previousImageView.setVisible(false);
        } else {
            previousImageView.setVisible(true);
        }
    }

    protected void setSideBar(){
        SideBarTeamController sideBarTeamController = sideBarAnchorPaneLoad.getController();
        sideBarTeamController.setHoverActivity();
    }


    private void dataInit(){
        dateRequirementLabel.setVisible(false);
        nameRequirementLabel.setVisible(false);
        teamNameReqImageView.setVisible(false);
        errorContinueLabel.setVisible(false);
        errorContinueLabel.setText("Please fill out the information correctly and completely.");

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
        if (editor) {
            ActivityTeam activityTeam = activityTeamList.findActivityByName(activityNameTextField.getText());
            startDateFromCSV = activityTeam.getStartTime();
            endDateFromCSV = activityTeam.getEndTime();

            startParts = startDateFromCSV.split("\\.");
            endParts = endDateFromCSV.split("\\.");

            LocalDate startDate = LocalDate.parse(startParts[0]);
            LocalDate endDate = LocalDate.parse(endParts[0]);
            startDatePicker.setValue(startDate);
            endDatePicker.setValue(endDate);
            startTimeParts = startParts[1].split(":");
            endTimeParts = endParts[1].split(":");

            startHour = Integer.parseInt(startTimeParts[0]);
            endHour = Integer.parseInt(endTimeParts[0]);

            int startHourTemp = Integer.parseInt(startTimeParts[0]);
            int endHourTemp = Integer.parseInt(endTimeParts[0]);
            if (startHour > 11) {
                startDateChoiceBox.setValue("PM");
                startHourTemp -= 12;
            } else {
                startDateChoiceBox.setValue("AM");
            }

            if (Integer.parseInt(endTimeParts[0]) > 11) {
                endDateChoiceBox.setValue("PM");
                endHourTemp -= 12;
            } else {
                endDateChoiceBox.setValue("AM");
            }

            beforeEditStartHour = startHour;
            beforeEditStartMinute = Integer.parseInt(startTimeParts[1]);

            startHourSpinner.getValueFactory().setValue(startHourTemp);
            startMinuteSpinner.getValueFactory().setValue(Integer.parseInt(startTimeParts[1]));

            endHourSpinner.getValueFactory().setValue(endHourTemp);
            endMinuteSpinner.getValueFactory().setValue(Integer.parseInt(endTimeParts[1]));
        }else{
            startDatePicker.setValue(LocalDate.now());
            startHourSpinner.getValueFactory().setValue(Integer.parseInt(formattedCurrentHour));
            currentMinute = currentDateTime.getMinute();
            startMinuteSpinner.getValueFactory().setValue(currentMinute);

            endDatePicker.setValue(LocalDate.now());
            endHourSpinner.getValueFactory().setValue(0);
            endMinuteSpinner.getValueFactory().setValue(0);
        }
    }
    private void choiceBoxInit() {
        startDateChoiceBox.getItems().addAll(time);
        endDateChoiceBox.getItems().addAll(time);

        startDatePicker.getEditor().setDisable(true);
        endDatePicker.getEditor().setDisable(true);
    }

    private void checkDescriptionReq(){
        if(descriptionTextArea.getText().isEmpty()){
            description = "-";
        }else {
            description = descriptionTextArea.getText();
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
    private void checkActivityNameReq() {
        activityName = activityNameTextField.getText();
        if (activityName.isEmpty()) {
            setValidateNameRequirement(false);
            nameRequirementLabel.setText("Incorrect Activity name.");
        } else if (activityTeamList.findActivityByName(activityName) != null && !editor) {
            setValidateNameRequirement(false);
            nameRequirementLabel.setText("Duplicate Activity name. Please use another name.");
        } else if (activityTeamList.findActivityByName(activityName) != null && editor) {
            if (activityTeamList.findActivityByName(beforeEditActivityName) == activityTeamList.findActivityByName(activityName)) {
                setValidateNameRequirement(true);
            } else {
                setValidateNameRequirement(false);
                nameRequirementLabel.setText("Duplicate Activity name. Please use another name.");
            }
        } else{
            setValidateNameRequirement(true);
        }
    }
    private void showFocusRequirement(){
        activityNameTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                checkActivityNameReq();
            }
        });
    }

    private void maximumLengthField(){
        activityNameTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.length() > MAX_ACTIVITY_NAME_LIMIT) {
                activityNameTextField.setText(oldValue);
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

    private void updateActivity(){
        Activity activityTeam = activityTeamList.findActivityByName(beforeEditActivityName);
        activityTeam.setName(activityNameTextField.getText());
        activityTeam.setDescription(description);
        activityTeam.setStartTime(startDateFormat);
        activityTeam.setEndTime(endDateFormat);
        activityTeamListDataSource.writeData(activityTeamList);
    }

    private void userInit() {
        if (user.getRole().equals("Member")) createActivityButton.setVisible(false);
    }
    private void initStatus(ActivityTeam activityTeam) {
        statusComboBox.getItems().addAll(status);
        statusComboBox.getSelectionModel().select((activityTeam.getStatus() ? 1 : 0));
        statusComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            activityTeam.setStatus(newValue.equals("Complete"));
        });
    }

    private void showCreateActivity() {
        statusComboBox.setVisible(false);
        deleteButton.setVisible(false);

        validateDateTime();
        timeInit();
        setPageVisible(false);
    }
    private void showEditActivity() {
        deleteButton.setVisible(true);

        validateDateTime();
        timeInit();
        setPageVisible(false);
    }

    private TableCell<ActivityTeam, String> formatTime() {
        return new TableCell<ActivityTeam, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) setText(null);
                else {
                    String[] parts = item.split("\\.");
                    String[] timeParts = parts[1].split(":");
                    int hour = Integer.parseInt(timeParts[0]);
                    int minute = Integer.parseInt(timeParts[1]);
                    String amPm = "AM";
                    if (hour > 11) {
                        amPm = "PM";
                        if (hour > 12) {
                            hour -= 12;
                        }
                    } else if (hour == 0) {
                        hour = 12;
                    }
                    setText(parts[0] + " " + hour + ":" + String.format("%02d", minute) + " " + amPm);
                }
            }
        };
    }

    private void showTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        startTimeColumn.setCellFactory(column -> formatTime());
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        endTimeColumn.setCellFactory(column -> formatTime());
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setCellFactory(column -> {
            return new TableCell<ActivityTeam, Boolean>() {
                @Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) setText(null);
                    else if (item) setText("Complete");
                    else setText("On going");
                }
            };
        });
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        activityTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ActivityTeam>() {
            @Override
            public void changed(ObservableValue observableValue, ActivityTeam oldValue, ActivityTeam newValue) {
                if(newValue != null) {
                    activityTeamSelect = newValue;
                    current_page = activityTableView.getSelectionModel().getSelectedIndex();
                    countDescriptionLabel.setText(String.valueOf(newValue.getDescription().length()));
                    activityNameLabel.setText(newValue.getName());
                    activityDescriptionLabel.setText(newValue.getDescription());
                    activityStartTimeLabel.setText(newValue.getStartTime());
                    activityEndTimeLabel.setText(newValue.getEndTime());
                    checkButtonNextAndPrevious();
                }
            }
        });

        activityTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 2 && !user.getRole().equals("Member")) {
                    ActivityTeam activityTeam = (ActivityTeam) activityTableView.getSelectionModel().getSelectedItem();
                    setEditActivity(activityTeam);
                    showEditActivity();
                }
            }
        });

        activityTableView.getColumns().clear();
        activityTableView.getColumns().addAll(nameColumn, startTimeColumn, endTimeColumn, statusColumn, descriptionColumn);

        activityTableView.getItems().clear();
        for (ActivityTeam activity : activityTeamList.getActivitiesByTeamID(team.getTeamID())) {
            if (team.getTeamID().equals(activity.getTeamID())) {
                activityTableView.getItems().add(activity);
            }
        }

        max_page = activityTableView.getItems().size();
        if (!activityTableView.getItems().isEmpty()) {
            activityTableView.getSelectionModel().select(0);
        } else {
            chatIconImageView.setVisible(false);
        }

        entriesLabel.setText("Showing " + max_page + " of " + max_page + " entries");

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

        if (startDate != null && endDate != null && startHourSpinner != null && startMinuteSpinner != null && endHourSpinner != null && endMinuteSpinner != null && startDateChoiceBox != null && endDateChoiceBox != null) {
            startHour = startHourSpinner.getValue();
            startMinute = startMinuteSpinner.getValue();
            endHour = endHourSpinner.getValue();
            endMinute = endMinuteSpinner.getValue();

            startAmPm = startDateChoiceBox.getValue();
            endAmPm = endDateChoiceBox.getValue();

            if (startAmPm != null && endAmPm != null) {
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
                endDateTime = LocalDateTime.of(endDate, LocalTime.of(endHour, endMinute));
                startDateTime = LocalDateTime.of(startDate, LocalTime.of(startHour, startMinute));

                startDateFormat = startDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm").withLocale(Locale.US));
                endDateFormat = endDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm").withLocale(Locale.US));

                ++countInit;
                if(editor){
                    beforeStartDateTime = LocalDateTime.of(LocalDate.parse(startParts[0]), LocalTime.of(beforeEditStartHour, beforeEditStartMinute));
                    beforeStartDateEditFormat = beforeStartDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm").withLocale(Locale.US));
                    currentDateTime = beforeStartDateTime;

                    setDateValidateRequirement();
                }else{
                    setDateValidateRequirement();
                }
            }
        }
    }

    private void setEditActivity(ActivityTeam activityTeam) {
        activityNameTextField.setText(activityTeam.getName());
        beforeEditActivityName = activityTeam.getName();
        descriptionTextArea.setText(activityTeam.getDescription());
        initStatus(activityTeam);
        editor = true;
    }
    private void setPageVisible(boolean visible){
        mainActivityAnchorPane.setVisible(visible);
        createActivityAnchorPane.setVisible(!visible);
    }
    private void setDateValidateRequirement(){
        if (startDateTime.isBefore(currentDateTime) || endDateTime.isBefore(startDateTime)) {
            dateValidateRequirement = false;
            if(countInit > 1){
                dateRequirementLabel.setVisible(true);
                dateRequirementLabel.setText("Please specify the correct Activity period.");
            } else {
                dateRequirementLabel.setVisible(false);
            }
        } else {
            dateValidateRequirement = true;
            dateRequirementLabel.setVisible(false);
        }
    }
    private void setValidateNameRequirement(boolean isValidate){
        activityNameRequirement = isValidate;
        teamNameReqImageView.setVisible(isValidate);
        nameRequirementLabel.setVisible(!isValidate);
    }

    private void searchbarInit() {
        searchbarTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            activityTableView.getItems().clear();
            Predicate<ActivityTeam> name = activityTeam -> {
                if (newValue == null || newValue.isEmpty()) return true;
                return activityTeam.getName().toLowerCase().contains(newValue.toLowerCase());
            };
            activityTeamList.getActivitiesByTeamID(team.getTeamID()).stream().filter(name).forEach(activityTeam -> {
                activityTableView.getItems().add(activityTeam);
            });
            entriesLabel.setText("Showing " + activityTableView.getItems().size() + " of " + max_page + " entries");
            activityTableView.getSelectionModel().select(0);
        });
    }
}
