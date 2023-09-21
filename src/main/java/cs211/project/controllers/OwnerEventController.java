package cs211.project.controllers;

import cs211.project.models.*;
import cs211.project.models.collections.EventList;
import cs211.project.services.Datasource;
import cs211.project.services.EventListDataSource;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Popup;


public class OwnerEventController {
    @FXML
    TableView TableEvent;
    @FXML
    Button backButton;
    Popup popup;
    Datasource<EventList> datasource;
    User currentuser;
    EventList eventList;
    Event event;


    @FXML
    public void initialize(){
        backButton.setOnAction(e -> popup.hide());
        this.datasource = new EventListDataSource("data","event-list.csv");
        this.eventList = datasource.readData();
        showTable(eventList);

    }

    private void showTable(EventList eventList) {
        // กำหนด column

        TableColumn<Event, String> eventName = new TableColumn<>("Event Name");
        eventName.setCellValueFactory(new PropertyValueFactory<>("eventName"));

        // กำหนด column และใช้ฟังก์ชัน helper เพื่อแปลงค่า int เป็น double
        TableColumn<Event, String> eventDateStart = new TableColumn<>("Start");
        eventDateStart.setCellValueFactory(new PropertyValueFactory<>("eventDateStart"));

        // กำหนด column และใช้ฟังก์ชัน helper เพื่อแปลงค่า int เป็น double
        TableColumn<Event, String> eventDateEnd = new TableColumn<>("End");
        eventDateEnd.setCellValueFactory(new PropertyValueFactory<>("eventDateEnd"));

        // กำหนด column และใช้ฟังก์ชัน helper เพื่อแปลงค่า int เป็น double
        TableColumn<Event, Integer> member = new TableColumn<>("People");
        member.setCellValueFactory(new PropertyValueFactory<>("member"));

        // กำหนด column และใช้ฟังก์ชัน helper เพื่อแปลงค่า int เป็น double
        TableColumn<Event, Boolean> statusEvent = new TableColumn<>("Status");
        statusEvent.setCellValueFactory(new PropertyValueFactory<>("joinEvent"));

        // ล้าง column เดิมทั้งหมดที่มีอยู่ใน table แล้วเพิ่ม column ใหม่
        TableEvent.getColumns().clear();
        TableEvent.getColumns().addAll(eventName, eventDateStart, eventDateEnd, member, statusEvent);

        TableEvent.getItems().clear();

        // ใส่ข้อมูลทั้งหมดไปแสดงใน TableView
        TableEvent.getItems().addAll(eventList.getEvents());
    }
    public void setDataPopup(Popup popup) {
        this.popup = popup;
    }
    public void setDataUser(User user){
        this.currentuser = user;
    }
}
