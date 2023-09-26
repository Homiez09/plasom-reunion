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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AllEventListController {
    @FXML AnchorPane navbarAnchorPane;
    @FXML ListView mainListView;
    @FXML TextField searchbarTextField;
    @FXML ComboBox categoryComboBox;
    @FXML Label tagComboBox;
    //---------------------------------------------------
    private User currentUser = (User) FXRouter.getData();
    private Datasource<EventList> eventListDatasource;
    private EventList eventList;
    @FXML
    private void initialize() {
        new LoadNavbarComponent(currentUser, navbarAnchorPane);
        this.eventListDatasource = new EventListDataSource();
        this.eventList = eventListDatasource.readData();
        initCategory();
        initSort();


        setMainListView(eventList.suffleEvent(eventList));
        if (categoryComboBox.getValue() == null){
            getBySearchbar(eventList);
        }
        getByCategory(eventList);










    }

    private void getByCategory(EventList ListEvent){
        ObservableList<Event> observableEventList = FXCollections.observableArrayList(ListEvent.getEvents());
        EventList filteredEventList = new EventList();

        categoryComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldTag, newTag) -> {
            filteredEventList.getEvents().clear(); // ล้าง EventList ในทุกครั้งที่มีการเลือก Tag ใหม่

            if (newTag != null) {
                updateUI();
                for (Event event : observableEventList) {
                    if (event.getEventTag().contains(newTag.toString())) {
                        filteredEventList.addEvent(event);
                    }
                }
            } else {
                filteredEventList.getEvents().addAll(observableEventList);
            }
            getBySearchbar(filteredEventList);
            if (searchbarTextField.getText().isEmpty()){
                setMainListView(filteredEventList);
            }


        });

    }

    private void getBySearchbar(EventList ListSearch){
        ObservableList<Event> observableEventList = FXCollections.observableArrayList(ListSearch.getEvents());
        EventList filteredEventList = new EventList();

        searchbarTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            String filter = newValue.toLowerCase();
            filteredEventList.getEvents().clear();

            if (filter == null || filter.isEmpty()) {
                // ถ้าไม่มีการค้นหาให้แสดงทุก Event โดยไม่คำนึงถึง Tag
                filteredEventList.getEvents().addAll(observableEventList);
            } else {
                // คัดกรอง Event และเพิ่มเฉพาะ Event ที่ตรงกับคำค้นหา (ชื่อเหตุการณ์)
                for (Event event : observableEventList) {
                    if (event.getEventName().toLowerCase().contains(filter)) {
                        filteredEventList.addEvent(event);
                    }
                }
            }
            setMainListView(filteredEventList);
        });
    }

    private void updateUI(){
        if (categoryComboBox.getValue() != null) {
            tagComboBox.setText(categoryComboBox.getValue().toString());
        }
    }

    private void initCategory(){
        String category[] = {"Art","Education","Food & Drink","Music","Performance","Seminar","Sport"};
        categoryComboBox.getItems().addAll(category);
        tagComboBox.setText("");
        updateUI();
    }

    private void initSort(){
        String sort[] = {"Name","Start","Member","End"};

    }
/*

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
*/

    private void setMainListView(EventList eventList) {
        mainListView.getItems().clear();
        mainListView.getStyleClass().add("event-list");

        List<HBox> eventBoxes = IntStream.range(0, eventList.getEvents().size())
                .boxed()
                .collect(Collectors.groupingBy(i -> i / 4))
                .values()
                .stream()
                .map(indices -> {
                    HBox box = new HBox();
                    box.setAlignment(Pos.CENTER_LEFT);
                    loadEventList(box, indices.stream()
                            .map(eventList.getEvents()::get)
                            .collect(Collectors.toList()));
                    return box;
                })
                .collect(Collectors.toList());

        mainListView.getItems().addAll(eventBoxes);
    }

    private void loadEventList(HBox hbox, List<Event> events) {
        events.forEach(event -> {
            Separator separator = new Separator();
            separator.setOrientation(Orientation.VERTICAL);
            separator.setOpacity(0.0);
            separator.setPrefWidth(24.0);

            AnchorPane anchorPane = new AnchorPane();
            new LoadCardEventComponent(anchorPane, event, "card-event");
            hbox.getChildren().addAll(separator, anchorPane);
        });
    }


    //fliter
    public void onAllClick(ActionEvent actionEvent) {
        eventList = eventList.suffleEvent(eventList);
        setMainListView(eventList);
        if (categoryComboBox.getValue()!= null) {
            getByCategory(eventList);
        }
        categoryComboBox.setValue("");

    }

    public void onNewClick(ActionEvent actionEvent) {
        eventList = eventList.sortNewEvent(eventList);
        setMainListView(eventList);
        if (categoryComboBox.getValue()!= null) {
            getByCategory(eventList);
        }
        categoryComboBox.setValue("");
    }

    public void onUpClick(ActionEvent actionEvent) {
        eventList = eventList.sortUpcoming(eventList);
        setMainListView(eventList);
        if (categoryComboBox.getValue()!= null) {
            getByCategory(eventList);
        }
        categoryComboBox.setValue("");
    }

    public void onCreateClick(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("create-event",currentUser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void onCategory(ActionEvent actionEvent) {
        categoryComboBox.show();
        searchbarTextField.setText("");
    }

    public void onClickTagLabel(MouseEvent mouseEvent) {
        tagComboBox.setText("");
        categoryComboBox.setValue("");
    }
}
