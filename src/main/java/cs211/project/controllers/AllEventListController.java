package cs211.project.controllers;

import cs211.project.models.*;
import cs211.project.models.collections.*;
import cs211.project.services.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;


import java.io.IOException;
import java.util.List;

public class AllEventListController {
    @FXML
    AnchorPane navbarAnchorPane;
    @FXML
    ListView mainListView;
    @FXML
    TextField searchbarTextField;
    private User currentUser = (User) FXRouter.getData();
    private Datasource<EventList> eventListDatasource;
    private EventList eventList;
    @FXML
    private void initialize() {
        new LoadNavbarComponent(currentUser, navbarAnchorPane);
        this.eventListDatasource = new EventListDataSource();
        this.eventList = eventListDatasource.readData();



        setMainListView(eventList.suffleEvent(eventList));

        ObservableList<Event> observableEventList = FXCollections.observableArrayList(eventList.getEvents());
        // สร้าง EventList ใหม่
        EventList filteredEventList = new EventList();

        searchbarTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            String filter = newValue.toLowerCase();
            filteredEventList.getEvents().clear(); // ล้าง EventList ในทุกครั้งที่มีการค้นหาใหม่

            if (filter == null || filter.isEmpty()) {
                // ถ้าไม่มีการค้นหาให้แสดงทุก Event
                filteredEventList.getEvents().addAll(observableEventList);
            } else {

                // คัดกรอง Event และเพิ่มเฉพาะ Event ที่ตรงกับคำค้นหา
                for (Event event : observableEventList) {
                    if (event.getEventName().toLowerCase().contains(filter)) {
                        filteredEventList.addEvent(event);
                    }
                }
            }

            setMainListView(filteredEventList);
        });
    }


    private void initCategory(){
        ComboBox box = new ComboBox();
        String category[] = {"Art","Education","Food & Drink","Music","Performance","Seminar","Sport"};
        box.getItems().addAll(category);
    }

    private void initSort(){
        ComboBox box = new ComboBox();
        String sort[] = {"Name","Start","Member","End"};
        box.getItems().addAll(sort);
    }

    private void setMainListView(EventList eventList){
        mainListView.getItems().clear();
        mainListView.getStyleClass().add("event-list");

        for (int i = 0 ; i < eventList.getEvents().size(); i+=4 ) {
            HBox box = new HBox();
            box.setAlignment(Pos.CENTER_LEFT);
            loadEventList(box, eventList.getEvents().subList(i, Math.min(eventList.getEvents().size(), i + 4)));
            mainListView.getItems().add(box);

        }
    }

    private void loadEventList(HBox hbox, List<Event> events) {
        try {
            for (Event event : events) {
                Separator separator = new Separator();
                separator.setOrientation(Orientation.VERTICAL);
                separator.setOpacity(0.0);
                separator.setPrefWidth(24.0);

                AnchorPane anchorPane = new AnchorPane();
                new LoadCardEventComponent(anchorPane,event,"card-event");
                hbox.getChildren().add(separator);
                hbox.getChildren().add(anchorPane);

            }
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException(e);
        }
    }

    //fliter
    public void onAllClick(ActionEvent actionEvent) {
        eventList = eventList.suffleEvent(eventList);

        setMainListView(eventList);
        searchbarTextField.setText("");
    }

    public void onNewClick(ActionEvent actionEvent) {
        eventList = eventList.sortNewEvent(eventList);

        setMainListView(eventList);
        searchbarTextField.setText("");
    }

    public void onUpClick(ActionEvent actionEvent) {
        eventList = eventList.sortUpcoming(eventList);

        setMainListView(eventList);
        searchbarTextField.setText("");
    }

    public void onCreateClick(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("create-event",currentUser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
