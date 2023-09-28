package cs211.project.componentControllers;

import cs211.project.models.*;
import cs211.project.models.collections.UserList;
import cs211.project.services.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Popup;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;


public class UserListEventController {
    @FXML
    TableView TableEvent;
    @FXML
    TableColumn<User,String> eventNameColumn;
    @FXML
    TableColumn<User,String> startDateColumn;
    @FXML
    TableColumn<User,String> endDateColumn;
    @FXML
    TableColumn<User,String> memberColumn;
    @FXML
    TableColumn<User,Boolean> statusColumn;
    @FXML
    TableColumn<User,Void> buttonColumn;
    @FXML
    Button closeButton;
    @FXML
    Popup popup;
    @FXML
    Label eventNameLabel,userSizeLabel;
    private Datasource<UserList> userListDatasource;
    private JoinEventMap MapUserJoinEvent;
    private User currentUser;
    private Event currentEvent;




    @FXML
    public void initialize() {}
    public void setDataPopup(Popup popup,User user) {
        this.userListDatasource = new UserListDataSource("data","user-list.csv");

        this.MapUserJoinEvent = new JoinEventMap();
        this.currentUser = user;
        this.popup = popup;


        ObservableList<User> observableEventList = FXCollections.observableArrayList(currentEvent.getUserList().getUsers());
        showTable(observableEventList);

    }

    private void showTable(ObservableList<User> observableList) {
        // กำหนด column
        TableEvent.setPlaceholder(new Label("No User"));
        eventNameColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().toString());
        });
        startDateColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().toString());
        });
        endDateColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().toString());
        });
        memberColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().toString());
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

                comboBox.getItems().addAll("Ban Activity", "Delete");

                comboBox.setOnAction(event -> {
                    String selectedOption = comboBox.getValue();
                    User user = getTableView().getItems().get(getIndex());
                    onComboBoxSelectionChanged(user, selectedOption,observableList);
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
    public void onComboBoxSelectionChanged(User user, String selectedOption,ObservableList<User> observableList) {
        if (user != null) {
            if (user.getStatus()) {
                switch (selectedOption){
                    case"Ban Activity":
                        popup.hide();
                        try {
                            FXRouter.goTo("create-event",currentUser,user);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "Delete":

                        HashMap<String, Set<String>> joinEvent = MapUserJoinEvent.readData();
                        Set<String> deleteUser = joinEvent.get(currentEvent.getEventID());
                        observableList.remove(user);
                        deleteUser.remove(user.getUserId());
                        joinEvent.put(currentEvent.getEventID(),deleteUser);
                        MapUserJoinEvent.writeData(joinEvent);

                        break;
                }
            }
        }
    }
    public void onCloseAction(ActionEvent actionEvent) {
    }

}
