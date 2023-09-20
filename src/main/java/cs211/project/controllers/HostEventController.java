package cs211.project.controllers;

import cs211.project.models.Event;
import cs211.project.models.User;
import cs211.project.models.collections.EventList;
import cs211.project.services.Datasource;
import cs211.project.services.EventListDataSource;
import cs211.project.services.FXRouter;
import cs211.project.services.LoadNavbarComponent;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.io.IOException;

public class HostEventController {
    @FXML
    private AnchorPane navbarAnchorPane;
    @FXML
    private TableView hostEventTableView;

    @FXML
    private TableColumn<Event,String> nameColumn,startColumn,endColumn,createAtColumn,memberColumn;

    @FXML
    private TableColumn<Event, Boolean> selectColumn;
    @FXML
    private TableColumn<Event, Button> manageColumn,teamColumn,activityColumn;
    private User currentUser;
    private Datasource<EventList> eventDatasource;
    private EventList eventList;
    private Event event;

    @FXML
    public void initialize() {
        this.currentUser = (User) FXRouter.getData();
        this.eventDatasource = new EventListDataSource("data","event-list.csv");
        this.eventList = eventDatasource.readData();
        new LoadNavbarComponent(currentUser, navbarAnchorPane);
        goTeamButton();
        goActivityButton();
        goManageButton();



        ShowTable();
    }

    private void ShowTable(){
        hostEventTableView.getItems().clear();

        if(eventList != null){
            ObservableList<Event> hostEventList = FXCollections.observableArrayList();
            for (Event event:eventList.getEvents()) {
                if (event.getEventHostName().equals(currentUser.getUsername())){
                    hostEventList.add(event);
                }
            }

            selectColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().selectedProperty());


            nameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
            startColumn.setCellValueFactory(new PropertyValueFactory<>("eventDateStart"));
            endColumn.setCellValueFactory(new PropertyValueFactory<>("eventDateEnd"));

            memberColumn.setCellValueFactory(cellDataFeatures ->  new SimpleObjectProperty<>(
                    cellDataFeatures.getValue().getSlotMember() == -1 ?
                    cellDataFeatures.getValue().getMember()+"" :
                    cellDataFeatures.getValue().getMember() + "/" + cellDataFeatures.getValue().getSlotMember())
            );

            createAtColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp")); // ตั้งค่า PropertyValueFactory ตามชื่อฟิลด์ของ Event



            hostEventTableView.setItems(hostEventList);
        }


    }
    public void onBackAction(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("my-events",currentUser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onCreateAction(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("create-event",currentUser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void goTeamButton(){
        teamColumn.setCellFactory(new Callback<TableColumn<Event, Button>, TableCell<Event, Button>>() {
            @Override
            public TableCell<Event, Button> call(TableColumn<Event, Button> param) {
                return new TableCell<Event, Button>() {
                    private final Button button = new Button("Select team");
                    {
                        button.setOnAction(event -> {
                            Event selectedEvent = getTableView().getItems().get(getIndex());
                            try {
                                FXRouter.goTo("select-team",currentUser,selectedEvent);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Button item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(button);
                            setAlignment(Pos.CENTER); // จัดแนวปุ่มตรงกลาง
                        }
                    }
                };
            }
        });
    }
    private void goActivityButton(){
        activityColumn.setCellFactory(new Callback<TableColumn<Event, Button>, TableCell<Event, Button>>() {
            @Override
            public TableCell<Event, Button> call(TableColumn<Event, Button> param) {
                return new TableCell<Event, Button>() {

                    private final Button button = new Button("Edit Activity");

                    {
                        button.setOnAction(event -> {
                            Event selectedEvent = getTableView().getItems().get(getIndex());
                            try {
                                FXRouter.goTo("event",currentUser,selectedEvent);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Button item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(button);
                            setAlignment(Pos.CENTER);

                        }
                    }
                };
            }
        });
    }
    private void goManageButton(){
        manageColumn.setCellFactory(new Callback<TableColumn<Event, Button>, TableCell<Event, Button>>() {
            @Override
            public TableCell<Event, Button> call(TableColumn<Event, Button> param) {
                return new TableCell<Event, Button>() {

                    private final Button button = new Button("Manage");

                    {
                        button.setOnAction(event -> {
                            Event selectedEvent = getTableView().getItems().get(getIndex());
                            try {
                                FXRouter.goTo("event",currentUser,selectedEvent);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Button item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(button);
                            setAlignment(Pos.CENTER);

                        }
                    }
                };
            }
        });
    }
    public void onDeleteAction(ActionEvent actionEvent) {
    }

    public void onHistorysAction(ActionEvent actionEvent) {
    }
}
