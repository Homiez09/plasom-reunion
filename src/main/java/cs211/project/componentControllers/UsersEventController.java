package cs211.project.componentControllers;

import cs211.project.models.*;
import cs211.project.models.collections.UserList;
import cs211.project.services.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Popup;

import java.io.IOException;
import java.lang.reflect.Type;
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
    TableColumn<User,Void> checkboxColumn;
    @FXML
    Button closeButton;
    @FXML
    Popup popup;
    @FXML
    Label eventNameLabel,userSizeLabel;
    private JoinEventMap MapUserJoinEvent;
    private User currentUser;
    private Event currentEvent;




    @FXML
    public void initialize() {}
    public void setDataPopup(Popup popup,Event event) {
        Datasource<UserList> userListDatasource = new UserListDataSource("data", "user-list.csv");
        this.currentEvent = event;
        this.MapUserJoinEvent = new JoinEventMap();

        this.popup = popup;


        ObservableList<User> observableEventList = FXCollections.observableArrayList(currentEvent.getUserList().getUsers());

        showTable(observableEventList);

    }

    private void showTable(ObservableList<User> observableList) {
        // กำหนด column
        TableUsers.setPlaceholder(new Label("No User"));

//        checkboxColumn.setCellValueFactory();
//
        profileColumn.setCellValueFactory(cellData -> {
            User user = cellData.getValue();

            // สร้าง ImageView เพื่อแสดงรูปภาพ
            ImageView imageView = new ImageView();
            imageView.setFitWidth(30); // กำหนดความกว้าง
            imageView.setFitHeight(30); // กำหนดความสูง

            ImagePathFormat pathFormat = new ImagePathFormat(user.getImagePath());
            Image image = new Image(pathFormat.toString());
            imageView.setImage(image);

            return new SimpleObjectProperty<>(imageView);
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

        profileColumn.setCellFactory(column ->new TableCellCenter<>().CellAsImageView(profileColumn));
        usernameColumn.setCellFactory(column -> new TableCellCenter<>().CellAsString(usernameColumn));
        nameColumn.setCellFactory(column ->  new TableCellCenter<>().CellAsString(nameColumn));
        statusColumn.setCellFactory(column ->  new TableCellCenter<>().CellAsString(statusColumn));



        TableUsers.setItems(observableList);

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

    public void onOpenAction(ActionEvent actionEvent) {
    }
    public TableCell<User,String> setCell(TableColumn<User,String> cell ){
        return new TableCell<User, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                }

                setAlignment(Pos.CENTER); // กำหนดให้ข้อมูลแสดงตรงกลาง
            }
        };
    }
}
