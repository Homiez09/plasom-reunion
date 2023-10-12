package cs211.project.controllers.component;

import cs211.project.models.*;
import cs211.project.models.collections.EventList;
import cs211.project.models.collections.TeamList;
import cs211.project.models.collections.UserList;
import cs211.project.services.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Popup;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;

public class OwnerEventController {
    @FXML
    TableView tableEvents;
    @FXML
    TableColumn<Event,String> eventNameColumn;
    @FXML
    TableColumn<Event,String> startDateColumn;
    @FXML
    TableColumn<Event,String> endDateColumn;
    @FXML
    TableColumn<Event,String> memberColumn;
    @FXML
    TableColumn<Event,Boolean> statusColumn;
    @FXML
    TableColumn<Event,Void> buttonColumn;
    @FXML
    Button backButton;
    @FXML
    Popup popup;
    private Datasource<EventList> eventListDatasource;
    private JoinEventMap joinEventMap;
    private User currentUser;
    private EventList eventList;
    private TeamList teamList;
    private ObservableList<Node> nodeObservableList;
    private ObservableList<Event> eventObservableList;

    @FXML
    public void initialize() {}
    public void setDataPopup(Popup popup, User user, ObservableList<Node> nodes,ObservableList<Event> events) {
        backButton.setOnAction(e -> popup.hide());
        this.eventListDatasource = new EventListDataSource();
        this.eventList = eventListDatasource.readData();
        Datasource<TeamList> teamListDatasource = new TeamListDataSource("data", "team-list.csv");
        this.teamList = teamListDatasource.readData();
        this.joinEventMap = new JoinEventMap();
        this.currentUser = user;
        this.popup = popup;
        this.nodeObservableList = nodes;
        this.eventObservableList = events;
        ObservableList<Event> observableEventList = FXCollections.observableArrayList(eventList.getOwnerEvent(currentUser));
        showTable(observableEventList);

    }

    private void showTable(ObservableList<Event> observableList) {
        observableList.sort(Comparator.comparing(Event::isEnd));
        // กำหนด column
        eventNameColumn.setReorderable(false);
        startDateColumn.setReorderable(false);
        endDateColumn.setReorderable(false);
        memberColumn.setReorderable(false);
        statusColumn.setReorderable(false);
        buttonColumn.setReorderable(false);
        tableEvents.setPlaceholder(new Label("No Event"));
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDateStart"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDateEnd"));
        memberColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getUserList().getUsers().size()+"");
        });
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("joinEvent"));

        buttonColumn.setCellFactory(param -> new TableCell<>() {
            private final ComboBox<String> comboBox = new ComboBox<>();

            {
                comboBox.getStyleClass().add("owner-event-combobox");
                comboBox.setValue("Action");
                comboBox.getItems().addAll("Edit", "View", "Delete");
                comboBox.setOnAction(event -> {
                    String selectedOption = comboBox.getValue();
                    Event eventToModify = getTableView().getItems().get(getIndex());
                    onComboBoxSelectionChanged(eventToModify, selectedOption,observableList);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(comboBox);
                }
            }
        });

        statusColumn.setCellFactory(column ->new TableCellCenter<>().CellAsBoolean(statusColumn));

        tableEvents.setFixedCellSize(40);
        tableEvents.setItems(observableList);

    }

    public void onComboBoxSelectionChanged(Event eventToModify, String selectedOption,ObservableList<Event> observableList) {
        if (eventToModify != null) {
            if (eventToModify.getEventHostUser().getUserId().equals(currentUser.getUserId())) {
                switch (selectedOption){
                    case"Edit":
                        popup.hide();
                        try {
                            FXRouter.goTo("create-event",currentUser,eventToModify,"my-event");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "View":
                        popup.hide();
                        try {
                            FXRouter.goTo("event",currentUser,eventToModify,"my-event");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "Delete":
                        eventList.getEvents().remove(eventList.findEventById(eventToModify.getEventID()));
                        HashMap<String, UserList> deleteEvent = joinEventMap.readData();
                        deleteEvent.remove(eventToModify.getEventID());

                        String folderPath = eventToModify.getEventImagePath();

                        File fileToDelete = new File(folderPath);
                        // ลบไฟล์
                        if (fileToDelete.exists()) {
                            if (fileToDelete.delete()) {
                                System.err.println("Success Delete");
                            } else {
                                System.err.println("Can't Delete");
                            }
                        } else {
                            System.err.println("Not Found");
                        }
                        observableList.remove(eventToModify);
                        eventObservableList.remove(eventToModify);
                        Node removeNode = null;
                        for (Node node:nodeObservableList) {
                            if (node.getUserData().equals(eventToModify)){
                                removeNode = node;
                            }
                        }
                        nodeObservableList.remove(removeNode);
                        eventListDatasource.writeData(eventList);
                        teamList.removeTeamByEvent(eventToModify);
                        joinEventMap.writeData(deleteEvent);
                        tableEvents.refresh();
                        break;
                }
            }
        }
    }
}
