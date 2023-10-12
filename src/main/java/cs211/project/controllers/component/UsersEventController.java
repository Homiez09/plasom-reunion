package cs211.project.controllers.component;

import cs211.project.models.*;
import cs211.project.models.collections.EventList;
import cs211.project.models.collections.UserList;
import cs211.project.services.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;

public class UsersEventController extends CardMyEventController{
    @FXML private TableView<User> tableUsers;
    @FXML private TableColumn<User,String> statusColumn, nameColumn, usernameColumn;
    @FXML private TableColumn<User, ImageView> profileColumn;
    @FXML private TableColumn<User,Void> actionColumn;
    @FXML private Button statusButton;
    @FXML private Label eventNameLabel,userSizeLabel,statusLabel,totalLabel,inLabel;

    private JoinEventMap MapUserJoinEvent;
    private Event currentEvent;
    private ObservableList<User> userObservableList;
    private ObservableList<User> banUserObservableList;

    public void setupData(Event event) {
        this.currentEvent = event;
        this.MapUserJoinEvent = new JoinEventMap();
        BanUser data = new BanUser();
        HashMap<User,EventList> getBan = data.readData();
        UserList banList = new UserList();
        for (User user: getBan.keySet()){
            banList.getUsers().add(user);
        }
        userObservableList = FXCollections.observableArrayList(event.getUserList().getUsers());
        banUserObservableList = FXCollections.observableArrayList(banList.getUsers());

        eventNameLabel.setText(currentEvent.getEventName());
        if (event.getSlotMember()  > 0) {
            userSizeLabel.setText(userObservableList.size() + "");
            totalLabel.setText(event.getSlotMember() + "");
        }else {
            totalLabel.setVisible(false);
            inLabel.setVisible(false);
        }

        statusButton.setText(currentEvent.isJoinEvent() ? "Close" : "Open");
        statusLabel.setText(currentEvent.isJoinEvent() ? "Open" : "Close");
        showTable(userObservableList);
    }

    private void showTable(ObservableList<User> observableList) {
        // กำหนด column

        tableUsers.setPlaceholder(new Label("No User"));
        profileColumn.setReorderable(false);
        usernameColumn.setReorderable(false);
        nameColumn.setReorderable(false);
        statusColumn.setReorderable(false);
        actionColumn.setReorderable(false);

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
                new CreateProfileCircle(imageView,15);
                return new SimpleObjectProperty<>(imageView);
            }
            return null;
        });
        usernameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDisplayName()));
        statusColumn.setCellValueFactory(cellData -> {
            User user = cellData.getValue();
            BanUser data = new BanUser();
            HashMap<User,EventList> getBan = data.readData();
            EventList findEvent = getBan.get(user);
            if (findEvent == null){
                findEvent = new EventList();
            }
            return new SimpleStringProperty(!banUserObservableList.contains(user) && !findEvent.getEvents().contains(currentEvent)? "Member":"Ban");
        });
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final ComboBox<String> comboBox = new ComboBox<>();

            {
                comboBox.getStyleClass().add("users-event-combobox");
                comboBox.setValue("Action");
                comboBox.getItems().addAll("Ban","UnBan", "Kick");

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
        tableUsers.setFixedCellSize(40);
        tableUsers.setItems(observableList);

    }

    private void onComboBoxSelectionChanged(User user, String selectedOption,ObservableList<User> observableList) {
        if (user != null) {
            BanUser data = new BanUser();
            HashMap<User,EventList> getBan = data.readData();
            EventList list;
            HashMap<String, UserList> joinEvent = MapUserJoinEvent.readData();
            UserList deleteUser = joinEvent.get(currentEvent.getEventID());
                switch (selectedOption){
                    case"Ban":
                        if (getBan.containsKey(user)) {
                            list = getBan.get(user);
                        }else {
                            list = new EventList();

                        }
                        if (!list.getEvents().contains(currentEvent)) {
                            list.getEvents().add(currentEvent);
                        }
                        banUserObservableList.add(user);
                        getBan.put(user,list);
                        data.writeData(getBan);
                        tableUsers.refresh();
                        break;
                    case"UnBan":
                        try {
                            list = getBan.get(user);
                            list.getEvents().remove(currentEvent);
                            banUserObservableList.remove(user);
                            getBan.put(user, list);
                            data.writeData(getBan);
                            tableUsers.refresh();
                        }catch (NullPointerException e){
                            System.err.println("Not Have in list");
                        }
                        break;
                    case "Kick":
                        data = new BanUser();
                        getBan = data.readData();
                        list = getBan.get(user);
                        if (list != null) {
                            list.getEvents().remove(currentEvent);
                            getBan.put(user, list);
                        }
                        data.writeData(getBan);
                        deleteUser.getUsers().remove(user);
                        observableList.remove(user);
                        joinEvent.put(currentEvent.getEventID(),deleteUser);
                        userSizeLabel.setText(observableList.size()+"");
                        MapUserJoinEvent.writeData(joinEvent);
                        tableUsers.refresh();
                        break;
                }
        }
    }

    @FXML private void onStatusButton() {
        Datasource<EventList> eventListDatasource = new EventListDataSource();
        EventList eventList = eventListDatasource.readData();
        eventList.changeStatus(currentEvent,!currentEvent.isJoinEvent());
        currentEvent.setJoinEvent(!currentEvent.isJoinEvent());
        statusButton.setText(currentEvent.isJoinEvent() ? "Close" : "Open");
        statusLabel.setText(currentEvent.isJoinEvent() ? "Open" : "Close");
        eventListDatasource.writeData(eventList);
    }


}
