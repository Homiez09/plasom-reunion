package cs211.project.componentControllers;

import cs211.project.models.*;
import cs211.project.models.collections.EventList;
import cs211.project.models.collections.TeamList;
import cs211.project.services.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Popup;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;


public class OwnerEventController {
    @FXML
    TableView TableEvent;
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
    private Datasource<TeamList> teamListDatasource;
    private JoinEventMap joinEventMap;
    private User currentUser;
    private EventList eventList;
    private TeamList teamList;

    @FXML
    public void initialize() {}
    public void setDataPopup(Popup popup,User user) {
        backButton.setOnAction(e -> popup.hide());
        this.eventListDatasource = new EventListDataSource();
        this.eventList = eventListDatasource.readData();
        this.teamListDatasource = new TeamListDataSource("data", "team-list.csv");
        this.teamList = teamListDatasource.readData();
        this.joinEventMap = new JoinEventMap();
        this.currentUser = user;
        this.popup = popup;


        ObservableList<Event> observableEventList = FXCollections.observableArrayList(eventList.getOwner(currentUser));
        showTable(observableEventList);

    }

    private void showTable(ObservableList<Event> observableList) {
        // กำหนด column
        TableEvent.setPlaceholder(new Label("No Event"));
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
                //---------------Custom---------------\\
                comboBox.setStyle(  "-fx-background-color:transparent; -fx-background-insets: 0;" +
                                    "-fx-alignment: center; -fx-smooth: true;" +
                        "-fx-content-display: text-only;");


                comboBox.setButtonCell(new ListCell<>() {
                    {
                        setText("...");
                    }
                });
                //---------------Custom---------------\\

                comboBox.getItems().addAll("Manage", "View", "Delete");

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

        TableEvent.setItems(observableList);

    }

    public void onComboBoxSelectionChanged(Event eventToModify, String selectedOption,ObservableList<Event> observableList) {
        if (eventToModify != null) {
            if (eventToModify.getEventHostUser().getUserId().equals(currentUser.getUserId())) {
                switch (selectedOption){
                    case"Manage":
                        popup.hide();
                        try {
                            FXRouter.goTo("create-event",currentUser,eventToModify);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "View":
                        popup.hide();
                        try {
                            FXRouter.goTo("event",currentUser,eventToModify);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "Delete":
                        eventList.getEvents().remove(eventList.findEvent(eventToModify.getEventID()));
                        HashMap<String, Set<String>> deleteEvent = joinEventMap.readData();
                        deleteEvent.remove(eventToModify.getEventID());

                        String folderPath = eventToModify.getEventImagePath();

                        File fileToDelete = new File(folderPath);
                        // ลบไฟล์
                        if (fileToDelete.exists()) {

                            if (fileToDelete.delete()) {
                                System.err.println("Succes Delete");
                            } else {
                                System.err.println("Cant Delete");
                            }
                        } else {
                            System.err.println("Not Found");
                        }
                        observableList.remove(eventToModify);
                        eventListDatasource.writeData(eventList);
                        teamList.removeTeamByEvent(eventToModify);
                        joinEventMap.writeData(deleteEvent);
                        TableEvent.refresh();
                        break;
                }
            }
        }
    }

}
