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

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;


public class UsersEventController {
    @FXML
    TableView TableUsers;
    @FXML
    TableColumn<User,String> statusColumn;
    @FXML
    TableColumn<User,String> nameColumn;
    @FXML
    TableColumn<User,String> usernameColumn;
    @FXML
    TableColumn<User, ImageView> profileColumn;
    @FXML
    TableColumn<User,Void> actionColumn;
    @FXML
    Button closeButton;
    @FXML
    Popup popup;
    @FXML
    Label eventNameLabel,userSizeLabel;
    private JoinEventMap MapUserJoinEvent;
    private User currentUser;
    private Event currentEvent;
    private ObservableList<User> userObservableList;



    @FXML
    public void initialize() {}
    public void setDataPopup(Popup popup,Event event) {
        Datasource<EventList> eventListDatasource = new EventListDataSource();
        EventList eventList = eventListDatasource.readData();
        currentEvent = eventList.findEvent(event.getEventID());
        this.MapUserJoinEvent = new JoinEventMap();

        this.popup = popup;
        userObservableList = FXCollections.observableArrayList(currentEvent.getUserList().getUsers());
        eventNameLabel.setText(currentEvent.getEventName());
        userSizeLabel.setText(userObservableList.size()+"");

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
                        "-fx-content-display: text-only;");


                comboBox.setButtonCell(new ListCell<>() {
                    {
                        setText("...");
                    }
                });
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
                        popup.hide();
                        try {
                            FXRouter.goTo("create-event",currentUser);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "Kick":
                        System.out.println();
                        HashMap<String, Set<String>> joinEvent = MapUserJoinEvent.readData();
                        Set<String> deleteUser = joinEvent.get(currentEvent.getEventID());
                        deleteUser.remove(user.getUserId());
                        observableList.remove(user);
                        joinEvent.put(currentEvent.getEventID(),deleteUser);
                        userSizeLabel.setText(observableList.size()+"");
                        TableUsers.refresh();

                        MapUserJoinEvent.writeData(joinEvent);

                        break;
                }
        }

    }
    public void onCloseAction(ActionEvent actionEvent) {
    }

    public void onOpenAction(ActionEvent actionEvent) {
    }


}
