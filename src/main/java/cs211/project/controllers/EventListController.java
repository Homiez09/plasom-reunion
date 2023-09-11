package cs211.project.controllers;

import cs211.project.componentControllers.EventComponentController;
import cs211.project.models.*;
import cs211.project.models.collections.*;
import cs211.project.services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class EventListController {
    @FXML
    AnchorPane navbarAnchorPane;
    @FXML
    Label eventLabel;
    @FXML
    ListView eventsListView,hosteventListView, myeventsListView,historyeventListView;
    @FXML
    Button showallButton,hosteventButton,myeventButton, historyButton;
    private User currentUser ;
    private Datasource<EventList> eventDatasource;
    private EventList eventList;
    private UserEventMap mapDatasource ;
    private HashMap<String, Set<String>> userMap;
    private Set<String> eventSet;

    @FXML
    public void initialize() {
        this.currentUser = (User) FXRouter.getData();
        eventDatasource = new EventListDataSource("data","event-list.csv");
        eventList = eventDatasource.readData();

        this.userMap = new HashMap<>();
        this.eventSet = new HashSet<>();

        showallButton.setVisible(false);
        new LoadNavbarComponent(currentUser, navbarAnchorPane);
        showList(eventList);

    }

    public void showList(EventList eventList) {
        eventsListView.getItems().clear();
        hosteventListView.getItems().clear();
        myeventsListView.getItems().clear();
        historyeventListView.getItems().clear();

        this.mapDatasource = new UserEventMap("data", "user-event.csv");
        this.userMap = mapDatasource.readData();

        if (userMap.containsKey(currentUser.getUsername())) {
            this.eventSet = userMap.get(currentUser.getUsername());
        }

        if(eventList != null){
            for (Event event : eventList.getEvents()) {
                try {
                    FXMLLoader eventComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/event-component.fxml"));
                    AnchorPane eventAnchorPaneComponent = eventComponentLoader.load();
                    EventComponentController eventComponent = eventComponentLoader.getController();
                    eventComponent.setEventData(event);

                    if (event.getEventHost().equals(currentUser.getUsername())) {
                        hosteventListView.getItems().add(eventAnchorPaneComponent);
                    } else if (eventSet.contains(event.getEventID()) && userMap.containsKey(currentUser.getUsername())) {
                        myeventsListView.getItems().add(eventAnchorPaneComponent);
                    }else {
                        if (!event.isEnd()){
                            eventsListView.getItems().add(eventAnchorPaneComponent);
                        }else {
                            historyeventListView.getItems().add(eventAnchorPaneComponent);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public void onBackAction(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("home",currentUser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void onCreateAction(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("create-event",currentUser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
//    public void onDeleteAction(ActionEvent actionEvent) {
//        AnchorPane selectedPane = (AnchorPane) myeventListView.getSelectionModel().getSelectedItem();
//
//        if (selectedPane != null) {
//            // ดึงข้อมูล Event ออกจาก AnchorPane โดยอ้างอิงถึงข้อมูลของ Event ใน AnchorPane
//            Event selectedEvent = (Event) selectedPane.getUserData();
//            // ตรวจสอบว่า selectedEvent ไม่เป็น null ก่อนที่จะลบ Event ออกจาก eventList
//            if (selectedEvent != null) {
//                // ลบ Event ตามที่คุณต้องการ
//                eventList.getEvents().remove(selectedEvent);
//                // บันทึกข้อมูล Event ใหม่ลงในไฟล์
//                eventDatasource.writeData(eventList);
//                // ตรวจสอบว่าข้อมูลถูกบันทึกลงในไฟล์เรียบร้อยแล้ว
//                eventList = eventDatasource.readData(); // อ่านข้อมูล Event จากไฟล์ใหม่
//                if (!eventList.getEvents().contains(selectedEvent)) {
//                    System.out.println("Event deleted successfully");
//                } else {
//                    System.out.println("Failed to delete event");
//                }
//                selectEvent = null; // รีเซ็ตตัวแปร selectEvent เป็น null
//            }
//        }
//        showList(eventList);
//    }
    public void onShowAllButton(ActionEvent actionEvent) {
        showallButton.setVisible(false);
        hosteventButton.setVisible(true);
        myeventButton.setVisible(true);
        historyButton.setVisible(true);
        eventLabel.setText("Show All");
        eventsListView.setVisible(true);
        hosteventListView.setVisible(false);
        myeventsListView.setVisible(false);
        historyeventListView.setVisible(false);
    }
    public void onHostEventsAction(ActionEvent actionEvent) {
        showallButton.setVisible(true);
        hosteventButton.setVisible(false);
        myeventButton.setVisible(true);
        historyButton.setVisible(true);
        eventLabel.setText("Host Events");
        eventsListView.setVisible(false);
        hosteventListView.setVisible(true);
        myeventsListView.setVisible(false);
        historyeventListView.setVisible(false);
    }
    public void onMyEventsAction(ActionEvent actionEvent) {
        showallButton.setVisible(true);
        hosteventButton.setVisible(true);
        myeventButton.setVisible(false);
        historyButton.setVisible(true);
        eventLabel.setText("My Events");
        eventsListView.setVisible(false);
        hosteventListView.setVisible(false);
        myeventsListView.setVisible(true);
        historyeventListView.setVisible(false);
    }
    public void onHistorysAction(ActionEvent actionEvent) {
        showallButton.setVisible(true);
        hosteventButton.setVisible(true);
        myeventButton.setVisible(true);
        historyButton.setVisible(false);
        eventLabel.setText("History Events");
        eventsListView.setVisible(false);
        hosteventListView.setVisible(false);
        myeventsListView.setVisible(false);
        historyeventListView.setVisible(true);
    }




}
