package cs211.project.controllers;

import cs211.project.componentControllers.CardEventController;
import cs211.project.models.*;
import cs211.project.models.collections.*;
import cs211.project.services.*;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

public class EventListController {
    @FXML
    AnchorPane navbarAnchorPane;
    @FXML
    ListView mainListView;
    @FXML
    TextField searchbarTextField;
    private User currentUser = (User) FXRouter.getData();
    private Datasource<EventList> eventListDatasource;
    private EventList eventList;
    private LoadCardEventComponent loadCardEventComponent;

    private int currentIndexOfUp = 0;
    @FXML
    private void initialize() {
        new LoadNavbarComponent(currentUser, navbarAnchorPane);
        this.eventListDatasource = new EventListDataSource("data", "event-list.csv");
        this.eventList = eventListDatasource.readData();



        setMainListView(eventList);

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

            // เรียกใช้เมธอดที่คุณต้องการในกรณีที่ EventList มีการเปลี่ยนแปลง
            setMainListView(filteredEventList);
        });
    }


    private void setMainListView(EventList eventList){
        mainListView.getItems().clear();
        int i ;
        for (i = 0 ; i < eventList.getEvents().size(); i+=3 ) {
            HBox box = new HBox();
            box.setAlignment(Pos.CENTER_LEFT);
            loadEventList(box, eventList.getEvents().subList(i, Math.min(eventList.getEvents().size(), i + 3)));
            mainListView.getItems().add(box);

        }
    }

    private void loadEventList(HBox hbox, List<Event> events) {
        try {
            for (Event event : events) {
                Separator separator = new Separator();
                separator.setOrientation(Orientation.VERTICAL);
                separator.setOpacity(0.0);
                separator.setPrefWidth(34.0);

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
        setMainListView(eventListDatasource.readData());
        searchbarTextField.setText("");
    }

    public void onNewClick(ActionEvent actionEvent) {
        Comparator<Event> comparator = Comparator.comparing(Event::getTimestamp);
        eventList.getEvents().sort(comparator);
        setMainListView(eventList);
        searchbarTextField.setText("");
    }

    public void onUpClick(ActionEvent actionEvent) {
        Comparator<Event> comparator = (event1, event2) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            // แปลงสตริงวันที่และเวลาเป็น LocalDateTime
            LocalDateTime eventDate1 = LocalDateTime.parse(event1.getEventDateStart(), formatter);
            LocalDateTime eventDate2 = LocalDateTime.parse(event2.getEventDateStart(), formatter);

            LocalDateTime currentDate = LocalDateTime.now();

            // คำนวณความต่างของวันระหว่างวันปัจจุบันกับวันเริ่มต้นของ Event
            long daysDifference1 = ChronoUnit.DAYS.between(eventDate1, currentDate);
            long daysDifference2 = ChronoUnit.DAYS.between(eventDate2, currentDate);

            // ถ้าวันเริ่มต้นมากกว่า 21 วัน ให้ Event 1 อยู่อันดับแรก
            if (daysDifference1 > 21 && daysDifference2 <= 21) {
                return -1;
            }
            // ถ้าวันเริ่มต้นมากกว่า 21 วัน ให้ Event 2 อยู่อันดับแรก
            else if (daysDifference1 <= 21 && daysDifference2 > 21) {
                return 1;
            }
            // ในกรณีอื่น ๆ ให้เรียงตามวันเริ่มต้นแบบปกติ
            else {
                return eventDate1.compareTo(eventDate2);
            }
        };

        eventList.getEvents().sort(comparator);
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
