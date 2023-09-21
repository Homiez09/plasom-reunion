package cs211.project.componentControllers;

import cs211.project.models.Activity;
import cs211.project.models.Event;
import cs211.project.models.User;
import cs211.project.models.collections.ActivityList;
import cs211.project.models.collections.EventList;
import cs211.project.services.ActivityListDataSource;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CreateActivityController {
    private User user;
    private Event event;
    private Activity activity;
    @FXML private Button deleteButton;
    @FXML private TextField activityNameTextField,activityDescriptionTextField;
    @FXML private DatePicker activityStartDatePick,activityEndDatePick;
    @FXML
    private Spinner<Integer> activityStartHourSpinner,activityEndHourSpinner, activityStartMinuteSpinner,activityEndMinuteSpinner;
    private Datasource<ActivityList> activityListDatasource;
    private ActivityList activityList;
    private String oldName;

    public void init(User user,Event event) {
        this.user = user;
        this.event = event;
        this.activity = null;
    }
    public void init(User user,Event event,String eventID,String name) {
        this.user = user;
        this.event = event;
        activity = activityList.findActivity(eventID,name);
    }
    @FXML
    public void initialize() {
        activityListDatasource = new ActivityListDataSource("data","activity-list.csv");
        activityList = activityListDatasource.readData();
        setSpinner(activityStartHourSpinner,23);
        setSpinner(activityEndHourSpinner,23);
        setSpinner(activityStartMinuteSpinner,59);
        setSpinner(activityEndMinuteSpinner,59);
        if (activity != null) {
            showActivity();
            deleteButton.setVisible(true);
            oldName = activity.getName();
        } else {deleteButton.setVisible(false);}
    }
    @FXML protected void onBackClick() {
        try {
            FXRouter.goTo("edit-activity",user,event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML protected void onDeleteButton() {
        activityList.removeActivity(activity);
        event.getActivities().removeActivity(activity);
        activityListDatasource.writeData(activityList);
        try {
            FXRouter.goTo("edit-activity",user,event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML protected void onSaveButton() {
        String activityName = activityNameTextField.getText();
        String activityDescription = activityDescriptionTextField.getText();
        String activityStart = formatTime(activityEndDatePick,activityStartHourSpinner,activityStartMinuteSpinner);
        String activityEnd = formatTime(activityEndDatePick,activityEndHourSpinner,activityEndMinuteSpinner);
        if (activity == null) {
            event.getActivities().addActivity(event.getEventID(),activityName,activityDescription,activityStart,activityEnd);
            activityList.addActivity(event.getEventID(),activityName,activityDescription,activityStart,activityEnd);
        } else {
            activityList.findActivity(event.getEventID(),oldName).setDescription(activityDescription);
            activityList.findActivity(event.getEventID(),oldName).setStartTime(activityStart);
            activityList.findActivity(event.getEventID(),oldName).setEndTime(activityEnd);
            activityList.findActivity(event.getEventID(),oldName).setName(activityName);
        }
        activityListDatasource.writeData(activityList);
        try {
            FXRouter.goTo("edit-activity",user,event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void showActivity() {
        activityNameTextField.setText(activity.getName());
        activityDescriptionTextField.setText(activity.getDescription());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime activityStartDateTime = LocalDateTime.parse(activity.getStartTime(),formatter);
        LocalDateTime activityEndDateTime = LocalDateTime.parse(activity.getEndTime(),formatter);
        activityStartDatePick.setValue(activityStartDateTime.toLocalDate());
        activityStartHourSpinner.getValueFactory().setValue(activityStartDateTime.getHour());
        activityStartMinuteSpinner.getValueFactory().setValue(activityStartDateTime.getMinute());
        activityEndDatePick.setValue(activityEndDateTime.toLocalDate());
        activityEndHourSpinner.getValueFactory().setValue(activityEndDateTime.getHour());
        activityEndMinuteSpinner.getValueFactory().setValue(activityEndDateTime.getMinute());
    }
    private String formatTime(DatePicker datePicker, Spinner<Integer> hour, Spinner<Integer> minute){
        LocalDate DatePick = datePicker.getValue();
        int Hour = hour.getValue();
        int Minute = minute.getValue();
        LocalTime Time = LocalTime.of(Hour, Minute);
        LocalDateTime DateTime = DatePick.atTime(Time);
        return DateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

    }
    private void setSpinner(Spinner<Integer> spinner, int time) {
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,time,0);
        spinner.setValueFactory(valueFactory);
        spinner.setEditable(true);
    }
}
