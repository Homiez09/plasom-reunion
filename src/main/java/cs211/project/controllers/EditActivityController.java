package cs211.project.controllers;

import cs211.project.componentControllers.CreateActivityController;
import cs211.project.models.Activity;
import cs211.project.models.Event;
import cs211.project.models.User;
import cs211.project.models.collections.ActivityList;
import cs211.project.services.ActivityListDataSource;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouter;
import cs211.project.services.LoadNavbarComponent;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.util.Comparator;

public class EditActivityController {
    private final User user = (User) FXRouter.getData();
    private final Event event = (Event) FXRouter.getData2();
    private ObservableList<Activity> activityObservableList;
    private Activity select = null;
    @FXML private AnchorPane navbarAnchorPane,editActivityAnchorPane;
    @FXML private TableView editActivityTableview;
    @FXML private void initialize() {
        new LoadNavbarComponent(user, navbarAnchorPane);
        ActivityList activityList = event.getActivityList();

        activityObservableList = FXCollections.observableList(event.getActivityList().sortActivity(activityList).getActivities());
        editActivityTableview.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 2 ) {
                    Activity activity = (Activity) editActivityTableview.getSelectionModel().getSelectedItem();
                    for (Activity compare:activityObservableList) {
                        if (compare.getActivityID().equals(activity.getActivityID())){
                            select = compare;
                        }
                    }
                    FXMLLoader createActivityLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/create-event-activity.fxml"));
                    try {
                        AnchorPane editActivityPage = createActivityLoader.load();
                        editActivityAnchorPane.getChildren().add(editActivityPage);
                        CreateActivityController createActivityController = createActivityLoader.getController();
                        createActivityController.initEdit(user,event,activityObservableList,activity);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        showTable(activityObservableList);

    }

    private void showTable(ObservableList<Activity> observableList) {
        TableColumn<Activity,String> nameColumn = new TableColumn<>("Activity name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Activity,String> startTimeColumn = new TableColumn<>("Activity start");
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        TableColumn<Activity,String> endTimeColumn = new TableColumn<>("Activity end");
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        TableColumn<Activity,String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        TableColumn<Activity,String> statusColumn = new TableColumn<>("Activity status");
        statusColumn.setCellValueFactory(cellData -> {
            Activity activity = cellData.getValue();
            String status = activity.getActivityStatus();
            return new SimpleStringProperty(status);
        });

        editActivityTableview.setRowFactory(tv -> {
            TableRow<Activity> row = new TableRow<>();

            row.setOnDragDetected(event -> {
                if (!row.isEmpty()) {
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.put(DataFormat.PLAIN_TEXT, row.getIndex());
                    db.setContent(content);
                    event.consume();
                }
            });
            row.setOnDragEntered(event -> {
                row.setStyle("-fx-background-color: #f2f2f2;");
            });

            row.setOnDragOver(event -> {
                if (event.getDragboard().hasContent(DataFormat.PLAIN_TEXT)) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                event.consume();
            });

            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(DataFormat.PLAIN_TEXT)) {
                    int draggedIndex = Integer.parseInt(db.getContent(DataFormat.PLAIN_TEXT).toString());
                    Activity draggedActivity = (Activity) editActivityTableview.getItems().get(draggedIndex);

                    int dropIndex;
                    if (row.isEmpty()) {
                        dropIndex = editActivityTableview.getItems().size();
                    } else {
                        dropIndex = row.getIndex();
                    }

                    // ดึงข้อมูลของวันเริ่มและวันจบของ Activity ที่ถูกลากและวาง
                    String draggedStartDate = draggedActivity.getStartTime();
                    String draggedEndDate = draggedActivity.getEndTime();

                    // ดึงข้อมูลของวันเริ่มและวันจบของ Activity ในแถวที่ถูกลากไป
                    Activity rowActivity = (Activity) editActivityTableview.getItems().get(dropIndex);
                    String rowStartDate = rowActivity.getStartTime();
                    String rowEndDate = rowActivity.getEndTime();

                    // สลับข้อมูลของวันเริ่มและวันจบระหว่าง Activity ที่ถูกลากและวาง และ Activity ในแถวที่ถูกลากไป
                    draggedActivity.setStartTime(rowStartDate);
                    draggedActivity.setEndTime(rowEndDate);

                    rowActivity.setStartTime(draggedStartDate);
                    rowActivity.setEndTime(draggedEndDate);

                    // สลับ Activity ในรายการ
                    editActivityTableview.getItems().set(draggedIndex, rowActivity);
                    editActivityTableview.getItems().set(dropIndex, draggedActivity);

                    event.consume();
                }
            });


            row.setOnDragExited(event -> {
                saveData();
                editActivityTableview.getSelectionModel().clearSelection();
                row.setStyle("");
            });

            return row;
        });

        editActivityTableview.getColumns().clear();
        editActivityTableview.getColumns().add(nameColumn);
        editActivityTableview.getColumns().add(startTimeColumn);
        editActivityTableview.getColumns().add(endTimeColumn);
        editActivityTableview.getColumns().add(statusColumn);
        editActivityTableview.getColumns().add(descriptionColumn);
        editActivityTableview.getItems().clear();
        nameColumn.setPrefWidth(180);
        startTimeColumn.setPrefWidth(120);
        endTimeColumn.setPrefWidth(120);
        statusColumn.setPrefWidth(100);
        descriptionColumn.prefWidthProperty().bind(editActivityTableview.widthProperty().subtract(nameColumn.widthProperty())
                .subtract(startTimeColumn.widthProperty()).subtract(endTimeColumn.widthProperty()).subtract(statusColumn.widthProperty()));

        editActivityTableview.setItems(observableList);
        editActivityTableview.setFixedCellSize(40);
    }

    @FXML private void onCreateActivityButtonClick() {
        FXMLLoader createActivityLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/create-event-activity.fxml"));
        try {
            AnchorPane editActivityPage = createActivityLoader.load();
            editActivityAnchorPane.getChildren().add(editActivityPage);
            CreateActivityController createActivityController = createActivityLoader.getController();
            createActivityController.initCreate(user,event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML private void onBackClick() {
        try {
            FXRouter.goTo("event",user,event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveData(){
        Datasource<ActivityList> datasource = new ActivityListDataSource("data","event-activity-list.csv");
        ActivityList activityList = datasource.readData();
        for (Activity data:activityObservableList
             ) {
            activityList.swapDate(data);
        }
        datasource.writeData(activityList);

    }
}
