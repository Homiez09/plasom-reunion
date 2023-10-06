package cs211.project.componentControllers;

import cs211.project.models.*;
import cs211.project.models.collections.EventList;
import cs211.project.models.collections.UserList;
import cs211.project.services.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Popup;

import java.util.HashMap;


public class UsersEventController extends CardMyEventController{
    @FXML TableView<User> TableUsers;
    @FXML TableColumn<User,String> statusColumn;
    @FXML TableColumn<User,String> nameColumn;
    @FXML TableColumn<User,String> usernameColumn;
    @FXML TableColumn<User, ImageView> profileColumn;
    @FXML TableColumn<User,Void> actionColumn;
    @FXML Button statusButton;
    @FXML Label eventNameLabel,userSizeLabel,statusLabel;
    @FXML Popup popup;
    private JoinEventMap MapUserJoinEvent;
    private Event currentEvent;
    Datasource<EventList> eventListDatasource;
    private EventList eventList;



    @FXML
    public void initialize() {}
    public void setupData(Popup popup, Event event) {
        this.eventListDatasource = new EventListDataSource();
        this.eventList = eventListDatasource.readData();
        this.currentEvent = eventList.findEventById(event.getEventID());
        this.MapUserJoinEvent = new JoinEventMap();
        this.popup = popup;
        ObservableList<User> userObservableList = FXCollections.observableArrayList(currentEvent.getUserList().getUsers());
        eventNameLabel.setText(currentEvent.getEventName());
        userSizeLabel.setText(userObservableList.size()+"");
        statusButton.setText(currentEvent.isJoinEvent() ? "Close" : "Open");
        statusLabel.setText(currentEvent.isJoinEvent() ? "Open" : "Close");
        showTable(userObservableList);
    }

    private void showTable(ObservableList<User> observableList) {
        // กำหนด column
        TableUsers.setPlaceholder(new Label("No User"));


        profileColumn.setCellValueFactory(cellData -> {
            User user = cellData.getValue();
            if (user!=null) {
                // สร้าง ImageView เพื่อแสดงรูปภาพ
                ImageView imageView = new ImageView();
                imageView.setFitWidth(30); // กำหนดความกว้าง
                imageView.setFitHeight(30); // กำหนดความสูง

                ImagePathFormat pathFormat = new ImagePathFormat(user.getImagePath());
                Image image = new Image(pathFormat.toString());
                imageView.setImage(image);

                return new SimpleObjectProperty<>(imageView);
            }
            return null;
        });

        usernameColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getUsername());
        });
        nameColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getDisplayName());
        });
        statusColumn.setCellValueFactory(cellData -> {
            // ในส่วนนี้คุณสามารถกำหนดวิธีการเข้าถึงข้อมูลแบบกำหนดเอง
            User user = cellData.getValue();
            return new SimpleStringProperty(currentEvent.isHaveUser(user)? "Member":"None");
        });

        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final ComboBox<String> comboBox = new ComboBox<>();

            {
                //---------------Custom---------------\\
                comboBox.setStyle(  "-fx-background-color:transparent; -fx-background-insets: 0;" +
                        "-fx-alignment: center; -fx-smooth: true;" +
                        "-fx-content-display: text-only; -fx-arrows-visible: true;");


                //---------------Custom---------------\\

                comboBox.getItems().addAll("Ban Activity", "Kick");

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

        profileColumn.setCellFactory(column ->new TableCellCenter<>().CellAsImageView(profileColumn));
        usernameColumn.setCellFactory(column -> new TableCellCenter<>().CellAsString(usernameColumn));
        nameColumn.setCellFactory(column ->  new TableCellCenter<>().CellAsString(nameColumn));
        statusColumn.setCellFactory(column ->  new TableCellCenter<>().CellAsString(statusColumn));



        TableUsers.setItems(observableList);

    }
    public void onComboBoxSelectionChanged(User user, String selectedOption,ObservableList<User> observableList) {
        if (user != null) {
                switch (selectedOption){
                    case"Ban Activity":


                    case "Kick":
                        HashMap<String, UserList> joinEvent = MapUserJoinEvent.readData();
                        UserList deleteUser = joinEvent.get(currentEvent.getEventID());
                        deleteUser.getUsers().remove(user);
                        observableList.remove(user);
                        joinEvent.put(currentEvent.getEventID(),deleteUser);
                        userSizeLabel.setText(observableList.size()+"");
                        TableUsers.refresh();
                        MapUserJoinEvent.writeData(joinEvent);

                        break;
                }
        }
    }


    public void onStatusButton(ActionEvent actionEvent) {
        currentEvent.setJoinEvent(!currentEvent.isJoinEvent());
        statusButton.setText(currentEvent.isJoinEvent() ? "Close" : "Open");
        statusLabel.setText(currentEvent.isJoinEvent() ? "Open" : "Close");
        eventListDatasource.writeData(eventList);
    }
}
