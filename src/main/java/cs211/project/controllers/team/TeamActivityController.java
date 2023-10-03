package cs211.project.controllers.team;

import cs211.project.models.*;
import cs211.project.models.collections.ActivityTeamList;
import cs211.project.services.ActivityTeamListDataSource;
import cs211.project.services.FXRouter;
import cs211.project.services.LoadNavbarComponent;
import cs211.project.services.team.LoadSideBarComponent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TeamActivityController {
    @FXML private AnchorPane navbarAnchorPane, sideBarAnchorPane, mainActivityAnchorPane, createActivityAnchorPane;
    @FXML private Button createActivityButton;
    @FXML private TableView activityTableView;
    @FXML private TextField activityNameTextField;
    @FXML private TextArea descriptionTextArea;
    @FXML private Button deleteButton;
    @FXML private ChoiceBox<String> startDateChoiceBox, endDateChoiceBox;
    @FXML private DatePicker startDatePicker, endDatePicker;
    @FXML private Spinner<Integer> startHourSpinner, startMinuteSpinner, endHourSpinner, endMinuteSpinner;
    @FXML private TableColumn<ActivityTeam, String> nameColumn, startTimeColumn, endTimeColumn, descriptionColumn;
    @FXML private TableColumn<ActivityTeam, Boolean> statusColumn;
    @FXML private Label activityNameLabel, activityDescriptionLabel, activityStartTimeLabel, activityEndTimeLabel;
    private final User user = (User) FXRouter.getData();
    private final Event event = (Event) FXRouter.getData2();
    private final Team team = (Team) FXRouter.getData3();
    private ActivityTeamListDataSource activityTeamListDataSource = new ActivityTeamListDataSource("data", "team-activity.csv");
    private ActivityTeamList activityTeamList;

    private final String[] time = {"AM", "PM"};
    private LocalDateTime currentDateTime = LocalDateTime.now();
    private int currentMinute, startHour, startMinute, endHour, endMinute;
    private String formattedCurrentHour, startAmPm, endAmPm, startDateFormat, endDateFormat;
    private SpinnerValueFactory<Integer> startHourSpin, endHourSpin;

    @FXML void initialize(){
        activityTeamList = activityTeamListDataSource.readData();
        new LoadNavbarComponent(user, navbarAnchorPane);
        new LoadSideBarComponent(sideBarAnchorPane);

        mainActivityAnchorPane.setVisible(true);
        createActivityAnchorPane.setVisible(false);
        initUser();
        showTable();
    }

    @FXML
    protected void onCreateActivityButtonClick() {
        activityTeamList.addActivity(new ActivityTeam(team.getTeamID(), activityNameTextField.getText(), descriptionTextArea.getText(), startDateFormat, endDateFormat));
        activityTeamListDataSource.writeData(activityTeamList);
        try {
            FXRouter.goTo("team-activity", user, event, team);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onDeleteActivityButtonClick() {
        ActivityTeam activityTeam = (ActivityTeam) activityTableView.getSelectionModel().getSelectedItem();
        activityTeamList.getActivities().remove(activityTeam);
        for (ActivityTeam activity : activityTeamList.getActivities()) {
            System.out.println(activity.getName());
        }
        activityTeamListDataSource.writeData(activityTeamList);
        try {
            FXRouter.goTo("team-activity", user, event, team);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onBackClick() {
        try {
            FXRouter.goTo("team-activity", user, event, team);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onKeyTeamName() {}

    @FXML
    protected void onKeyDescriptionCountText() {}

    @FXML
    protected void onCreateButtonClick() {
        showCreateActivity();
    }

    @FXML
    protected void onCancelButtonClick() throws IOException {
        FXRouter.goTo("team-activity", user, event, team);
    }

    private void initUser() {
        if (user.getRole().equals("Member")) createActivityButton.setVisible(false);
    }

    private void showCreateActivity() {
        validateDateTime();
        timeInit();
        choiceBoxInit();
        deleteButton.setVisible(false);
        mainActivityAnchorPane.setVisible(false);
        createActivityAnchorPane.setVisible(true);
    }

    private void setEditActivity(ActivityTeam activityTeam) {
        String startTime = activityTeam.getStartTime();
        String endTime = activityTeam.getEndTime();

        activityNameTextField.setText(activityTeam.getName());
        descriptionTextArea.setText(activityTeam.getDescription());
    }

    private void showEditActivity() {
        deleteButton.setVisible(true);
        mainActivityAnchorPane.setVisible(false);
        createActivityAnchorPane.setVisible(true);
    }

    private void showTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
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
                    activityNameLabel.setText(newValue.getName());
                    activityDescriptionLabel.setText(newValue.getDescription());
                    activityStartTimeLabel.setText(newValue.getStartTime());
                    activityEndTimeLabel.setText(newValue.getEndTime());
                    setEditActivity(newValue);
                    if (!user.getRole().equals("Member")) showEditActivity();
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

    private void choiceBoxInit() {
        startDateChoiceBox.getItems().addAll(time);
        endDateChoiceBox.getItems().addAll(time);

        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());
        startDatePicker.getEditor().setDisable(true);
        endDatePicker.getEditor().setDisable(true);
    }
}
