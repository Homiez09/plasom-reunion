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
            String userId = user.getUserId();
            BanUser data = new BanUser();
            HashMap<String,EventList> getBan = data.readData();
            EventList list = getBan.get(userId);
            if (list == null) {
                list = new EventList();
            }
            return new SimpleStringProperty(!list.getEvents().contains(currentEvent)? "Member":"Ban");
        });

        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final ComboBox<String> comboBox = new ComboBox<>();

            {
                //---------------Custom---------------\\
                comboBox.setStyle(  "-fx-background-color:transparent; -fx-background-insets: 0;" +
                        "-fx-alignment: center; -fx-smooth: true;" +
                        "-fx-content-display: text-only; -fx-arrows-visible: true;");


                //---------------Custom---------------\\

                comboBox.getItems().addAll("Ban", "Kick");

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
            BanUser data = new BanUser();
            HashMap<String,EventList> getBan = data.readData();
            EventList list;
            String userId = user.getUserId();
                switch (selectedOption){
                    case"Ban":
                        System.out.println(getBan.keySet());
                        System.out.println(getBan.containsKey(userId));
                        if (getBan.containsKey(userId)) {
                            list = getBan.get(userId);
                        }else {
                            list = new EventList();

                        }
                        if (!list.getEvents().contains(currentEvent)) {
                            list.getEvents().add(currentEvent);
                        }

                        getBan.put(userId,list);
                        data.writeData(getBan);
                        break;
                    case "Kick":
                        HashMap<String, UserList> joinEvent = MapUserJoinEvent.readData();
                        UserList deleteUser = joinEvent.get(currentEvent.getEventID());
                        data = new BanUser();
                        getBan = data.readData();
                        list = getBan.get(userId);
                        if (list != null) {
                            list.getEvents().remove(currentEvent);
                            getBan.put(userId, list);
                        }
                        data.writeData(getBan);
                        deleteUser.getUsers().remove(user);
                        observableList.remove(user);
                        joinEvent.put(currentEvent.getEventID(),deleteUser);
                        userSizeLabel.setText(observableList.size()+"");

                        MapUserJoinEvent.writeData(joinEvent);
                        TableUsers.refresh();
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
