package cs211.project.controllers;

import cs211.project.componentControllers.EventComponentController;
import cs211.project.models.Event;
import cs211.project.models.User;
import cs211.project.models.collections.EventList;
import cs211.project.services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;

public class MyEventController {
    @FXML
    AnchorPane navbarAnchorPane;
    @FXML
    Label nameLabel,bioLabel;
    @FXML
    ImageView userImageView;
    @FXML
    ListView myeventListView,historyeventListView;
    @FXML
    Button currentButton, deleteButton;
    private Datasource<EventList> datasource;
    private EventList eventList;
    private Event selectEvent;
    private User currentUser = (User) FXRouter.getData();



    @FXML
    public void initialize() {
        datasource = new EventListDataSource("data","event-list.csv");
        eventList = datasource.readData();
        new LoadNavbarComponent(currentUser, navbarAnchorPane);
        showInfo();
        showList(eventList);






    }
    private void showInfo(){
        userImageView.setImage(new Image(getClass().getResource(currentUser.getImagePath()).toExternalForm()));
        nameLabel.setText(currentUser.getDisplayName());
        bioLabel.setText(currentUser.getBio());

    }


    public void onEditAction(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("user-profile",currentUser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void showList(EventList eventList) {
        myeventListView.getItems().clear();
        for (Event event : eventList.getEvents()) {
            try {
                FXMLLoader eventComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/event-component.fxml"));
                AnchorPane eventAnchorPaneComponent = eventComponentLoader.load();
                EventComponentController eventComponent = eventComponentLoader.getController();
                eventComponent.setEventData(event);
                // ตั้งค่าข้อมูล Event ให้กับ AnchorPane
                eventAnchorPaneComponent.setUserData(event);
                if (event.isEnd()) {
                    historyeventListView.getItems().add(eventAnchorPaneComponent);
                } else {
                    myeventListView.getItems().add(eventAnchorPaneComponent);
                }
            } catch (IOException e) {
                e.printStackTrace();
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

    public void onDeleteAction(ActionEvent actionEvent) {
        AnchorPane selectedPane = (AnchorPane) myeventListView.getSelectionModel().getSelectedItem();

        if (selectedPane != null) {
            // ดึงข้อมูล Event ออกจาก AnchorPane โดยอ้างอิงถึงข้อมูลของ Event ใน AnchorPane
            Event selectedEvent = (Event) selectedPane.getUserData();
            // ตรวจสอบว่า selectedEvent ไม่เป็น null ก่อนที่จะลบ Event ออกจาก eventList
            if (selectedEvent != null) {
                // ลบ Event ตามที่คุณต้องการ
                eventList.getEvents().remove(selectedEvent);
                // บันทึกข้อมูล Event ใหม่ลงในไฟล์
                datasource.writeData(eventList);
                // ตรวจสอบว่าข้อมูลถูกบันทึกลงในไฟล์เรียบร้อยแล้ว
                eventList = datasource.readData(); // อ่านข้อมูล Event จากไฟล์ใหม่
                if (!eventList.getEvents().contains(selectedEvent)) {
                    System.out.println("Event deleted successfully");
                } else {
                    System.out.println("Failed to delete event");
                }
                selectEvent = null; // รีเซ็ตตัวแปร selectEvent เป็น null
            }
        }
        showList(eventList);
    }

    public void onHistoryAction(ActionEvent actionEvent) {
        currentButton.setVisible(true);
        myeventListView.setVisible(false);
        historyeventListView.setVisible(true);
        deleteButton.setVisible(false);
    }

    public void onCurrentAction(ActionEvent actionEvent) {
        currentButton.setVisible(false);
        historyeventListView.setVisible(false);
        myeventListView.setVisible(true);
        deleteButton.setVisible(true);
    }


}
