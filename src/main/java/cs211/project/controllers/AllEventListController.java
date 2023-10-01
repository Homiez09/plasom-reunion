package cs211.project.controllers;

import cs211.project.models.*;
import cs211.project.models.collections.*;
import cs211.project.services.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;


import java.io.IOException;
import java.util.Comparator;
import java.util.function.Predicate;



public class AllEventListController {
    @FXML AnchorPane navbarAnchorPane;
    @FXML TilePane tilePaneMain;
    @FXML TextField searchbarTextField;
    @FXML ComboBox<String> categoryComboBox;
    @FXML ScrollPane scrollPane;
    @FXML Button categoryButton;
    //---------------------------------------------------
    private User currentUser = (User) FXRouter.getData();
    private Datasource<EventList> eventListDatasource;
    private ObservableList<Event> eventObservableList;
    private EventList eventList;
    private int currentPage = 1;
    private int itemsPerPage = 8;
    private Predicate<Event> selectedPredicate = null;
    private FilteredList<Event> size;

    @FXML
    private void initialize() {
        new LoadNavbarComponent(currentUser, navbarAnchorPane);
        setupPage();


        getByCategory();
        loadData(currentPage,selectedPredicate);

    }
    private void setupPage(){
        this.eventListDatasource = new EventListDataSource();
        this.eventList = eventListDatasource.readData();
        this.eventObservableList = FXCollections.observableArrayList(eventList.getEvents());
        initCategory();
        initSort();
        setupScrollBar();
    }


    private void loadData(int page,Predicate<Event> selectedPredicate) {
        scrollPane.setVvalue(0.0);
        FilteredList<Event> filteredList = new FilteredList<>(eventObservableList,selectedPredicate);
        int startIndex = (page - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, filteredList.size());

        for (int i = startIndex; i < endIndex; i++) {
            Event event = filteredList.get(i);
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setUserData(event);
            new LoadCardEventComponent(anchorPane, event, "card-event");
            tilePaneMain.getChildren().add(anchorPane);
        }

    }

    @FXML
    private void loadMore() {
        currentPage++;
        loadData(currentPage,selectedPredicate);
    }

    private void setupScrollBar(){
        scrollPane.vvalueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double value = newValue.doubleValue();
                if (value >= 0.8) {
                    if (tilePaneMain.getChildren().size()/8 == currentPage) {
                        loadMore();
                    }
                }
            }
        });

    }
    private void removeNode(Predicate<Event> selectedPredicate) {
        // สร้าง FilteredList โดยใช้ Predicate
        // ลบโหนดที่ไม่ตรงกับการกรองออกจาก TilePane
        tilePaneMain.getChildren().removeIf(node -> {
            if (node instanceof AnchorPane anchorPane) {
                Event event = (Event) anchorPane.getUserData();
                return !eventObservableList.filtered(selectedPredicate).contains(event); // ซ่อนโหนดที่ไม่ตรงกับ filteredList
            }
            return false;
        });

    }

    private void getByCategory(){
        categoryComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldTag, newTag) -> {
            selectedPredicate = null;
                switch (newTag.toString()) {
                    case "Art":
                        categoryButton.setText("Art");
                        selectedPredicate = event -> event.getEventTag().contains("Art");
                        break;
                    case "Education":
                        categoryButton.setText("Education");
                        selectedPredicate = event -> event.getEventTag().contains("Education");
                        break;
                    case "Food & Drink":
                        categoryButton.setText("Food & Drink");
                        selectedPredicate = event -> event.getEventTag().contains("Food & Drink");
                        break;
                    case "Music":
                        categoryButton.setText("Music");
                        selectedPredicate = event -> event.getEventTag().contains("Music");
                        break;
                    case "Performance":
                        categoryButton.setText("Performance");
                        selectedPredicate = event -> event.getEventTag().contains("Performance");
                        break;
                    case "Seminar":
                        categoryButton.setText("Seminar");
                        selectedPredicate = event -> event.getEventTag().contains("Seminar");
                        break;
                    case "Sport":
                        categoryButton.setText("Sport");
                        selectedPredicate = event -> event.getEventTag().contains("Sport");
                        break;
                    default:
                        break;

                }
                size = new FilteredList<>(eventObservableList);

            currentPage = 1;
            removeNode(selectedPredicate);
            if (oldTag != null) {
                loadData(currentPage,selectedPredicate);

            }


        });

    }

    private void getBySearch(Predicate<Event> selectedPredicate ) {
        searchbarTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            // สร้าง Predicate ใหม่เพื่อค้นหาตามข้อความที่ผู้ใช้ป้อน
            Predicate<Event> searchPredicate = event -> {
                if (newValue == null || newValue.isEmpty()) {

                    return true;
                }
                String searchText = newValue.toLowerCase().trim();

                return event.getEventName().toLowerCase().contains(searchText) ||
                        event.getEventDescription().toLowerCase().contains(searchText);
            };

            // ลบโหนดที่ไม่ตรงกับเงื่อนไขค้นหาออกจาก TilePane
            removeNode(searchPredicate);
            if (oldValue != null) {

            }

        });
    }

    private void initCategory(){
        String category[] = {"Art","Education","Food & Drink","Music","Performance","Seminar","Sport"};
        categoryComboBox.getItems().addAll(category);

    }

    private void initSort(){
        String sort[] = {"Name","Start","Member","End"};

    }

    public void onAllClick(ActionEvent actionEvent) {
        tilePaneMain.getChildren().clear();
        eventObservableList = FXCollections.observableArrayList(eventList.getEvents());
        currentPage = 1;
        loadData(currentPage,selectedPredicate);
        categoryButton.setText("Category");


    }

    public void onNewClick(ActionEvent actionEvent) {
        currentPage = 1;
        eventObservableList = FXCollections.observableArrayList(eventList.getEvents());
        Comparator<Event> eventComparator = Comparator.comparing(Event::getTimestampAsDate);
        eventObservableList.sort(eventComparator);
        tilePaneMain.getChildren().clear();
        loadData(currentPage,selectedPredicate);


    }

    public void onUpClick(ActionEvent actionEvent) {
        currentPage = 1;
        eventObservableList = FXCollections.observableArrayList(eventList.getEvents());
        Comparator<Event> eventComparator = Comparator.comparing(Event::getEventDateEnd);
        eventObservableList.sort(eventComparator);
        tilePaneMain.getChildren().clear();
        loadData(currentPage,selectedPredicate);
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



    public void onScroll(ScrollEvent scrollEvent) {

    }

    public void onScrollFinished(ScrollEvent scrollEvent) {

    }
}
