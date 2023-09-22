package cs211.project.controllers;

import cs211.project.componentControllers.CreateActivityController;
import cs211.project.models.Activity;
import cs211.project.models.Event;
import cs211.project.models.User;
import cs211.project.services.FXRouter;
import cs211.project.services.LoadNavbarComponent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class EditActivityController {
    private User user = (User) FXRouter.getData();
    Event event = (Event) FXRouter.getData2();
    @FXML private AnchorPane navbarAnchorPane,editActivityAnchorPane;
    @FXML private TableView editActivityTableview;
    @FXML private void initialize() {
        new LoadNavbarComponent(user, navbarAnchorPane);
        showTable();
        editActivityTableview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Activity>() {
            @Override
            public void changed(ObservableValue observableValue, Activity oldValue, Activity newValue) {
                if(newValue != null) {
                    FXMLLoader createActivityLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/create-activity.fxml"));
                    try {
                        AnchorPane editActivityPage = createActivityLoader.load();
                        editActivityAnchorPane.getChildren().add(editActivityPage);
                        CreateActivityController createActivityController = createActivityLoader.getController();
                        createActivityController.init(user,event,newValue.getEventID(),newValue.getName());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    public void showTable() {
        TableColumn<Activity,String> nameColumn = new TableColumn<>("Activity name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Activity,String> startTimeColumn = new TableColumn<>("Activity start");
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        TableColumn<Activity,String> endTimeColumn = new TableColumn<>("Activity end");
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        TableColumn<Activity,String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        editActivityTableview.getColumns().clear();
        editActivityTableview.getColumns().add(nameColumn);
        editActivityTableview.getColumns().add(startTimeColumn);
        editActivityTableview.getColumns().add(endTimeColumn);
        editActivityTableview.getColumns().add(descriptionColumn);
        editActivityTableview.getItems().clear();
        nameColumn.setPrefWidth(200);
        startTimeColumn.setPrefWidth(150);
        endTimeColumn.setPrefWidth(150);
        descriptionColumn.prefWidthProperty().bind(editActivityTableview.widthProperty().subtract(nameColumn.widthProperty())
                .subtract(startTimeColumn.widthProperty()).subtract(endTimeColumn.widthProperty()));
        for (Activity activity: event.getActivities().getActivities()) {
            if (event.getEventID().equals(activity.getEventID())) {
                editActivityTableview.getItems().add(activity);
            }
        }
        editActivityTableview.setFixedCellSize(40);
    }
    @FXML protected void onCreateActivityButtonClick() {
        FXMLLoader createActivityLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/create-activity.fxml"));
        try {
            AnchorPane editActivityPage = createActivityLoader.load();
            editActivityAnchorPane.getChildren().add(editActivityPage);
            CreateActivityController createActivityController = createActivityLoader.getController();
            createActivityController.init(user,event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML protected void onBackClick() {
        try {
            FXRouter.goTo("event",user,event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
