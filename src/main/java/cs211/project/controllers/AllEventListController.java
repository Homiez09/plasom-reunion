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

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;



public class AllEventListController {
    @FXML AnchorPane navbarAnchorPane;
    @FXML TilePane tilePaneMain;
    @FXML TextField searchbarTextField;
    @FXML ComboBox<String> categoryComboBox,sortComboBox;
    @FXML ScrollPane scrollPane;
    @FXML Button categoryButton,sortButton,allButton,newButton,upComingButton,createButton;
    //---------------------------------------------------
    private User currentUser = (User) FXRouter.getData();
    private ObservableList<Event> eventObservableList;
    private int currentPage = 1;
    private final int itemsPerPage = 8;
    private Predicate<Event> selectedPredicate = null;

    @FXML
    private void initialize() {
        new LoadNavbarComponent(currentUser, navbarAnchorPane);
        setupPage();
        getByCategory();
        getBySearch();
        sortTilePane();
        createButton.setVisible(currentUser != null);
    }

    private void setupPage() {
        Datasource<EventList> eventListDatasource = new EventListDataSource();
        EventList eventList = eventListDatasource.readData();
        this.eventObservableList = FXCollections.observableArrayList(eventList.getEvents());
        initCategory();
        initSort();
        setupScrollBar();
        allButton.setDisable(true);
        Comparator<Event> eventComparator = Comparator.comparing(Event::getEventName);
        eventObservableList.sort(eventComparator);
        loadData(currentPage,selectedPredicate);
    }

    private void loadData(int page,Predicate<Event> selectedPredicate) {
        FilteredList<Event> filteredList = new FilteredList<>(eventObservableList,selectedPredicate);

        int startIndex = (page - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, filteredList.size());

        for (int i = startIndex; i < endIndex; i++) {
            Event event = filteredList.get(i);
            if (!checkNode(event) && !event.isEnd()) {
                AnchorPane anchorPane = new AnchorPane();
                anchorPane.setUserData(event);
                anchorPane.setId(event.getEventID());
                new LoadCardEventComponent(anchorPane, event, "card-event");
                tilePaneMain.getChildren().add(anchorPane);
            }
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
                if (value >= 0.15 && tilePaneMain.getChildren().size()/itemsPerPage == currentPage) {
                        loadMore();
                }
            }
        });
    }
    private void removeNode(Predicate<Event> selectedPredicate) {
        FilteredList<Event> filteredList = eventObservableList.filtered(selectedPredicate);
        List<Node> nodesToRemove = new ArrayList<>();
        for (Node node:tilePaneMain.getChildren()) {
            Event event = (Event) node.getUserData();
            if (!filteredList.contains(event)) {
                nodesToRemove.add(node);
            }
        }
        tilePaneMain.getChildren().removeAll(nodesToRemove);
    }

    private void getByCategory(){
        categoryComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldTag, newTag) -> {
            selectedPredicate = null;
                switch (newTag) {
                    case "Animal":
                        categoryButton.setText("Animal");
                        selectedPredicate = event -> event.getEventTag().contains("Animal");
                        break;
                    case "Art":
                        categoryButton.setText("Art");
                        selectedPredicate = event -> event.getEventTag().contains("Art");
                        break;
                    case "Business":
                        categoryButton.setText("Business");
                        selectedPredicate = event -> event.getEventTag().contains("Business");
                        break;
                    case "Conference":
                        categoryButton.setText("Conference");
                        selectedPredicate = event -> event.getEventTag().contains("Conference");
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
                    case "Workshop":
                        categoryButton.setText("Workshop");
                        selectedPredicate = event -> event.getEventTag().contains("Workshop");
                        break;
                    default:
                        break;
                }
            currentPage = 1;
            allButton.setDisable(false);
            newButton.setDisable(false);
            upComingButton.setDisable(false);
            removeNode(selectedPredicate);
            if (!oldTag.isEmpty()) {
                loadData(currentPage,selectedPredicate);
            }
        });
    }

    private void getBySearch() {
        searchbarTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            String searchText = newValue.toLowerCase().trim();
            Predicate<Event> searchPredicate = event -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                return event.getEventName().toLowerCase().contains(searchText);
            };

            Predicate<Event> combinedPredicate = searchPredicate;
            if (categoryComboBox.getValue() != null && !categoryComboBox.getValue().isEmpty()) {
                combinedPredicate = combinedPredicate.and(selectedPredicate);
            }

            removeNode(combinedPredicate);
            currentPage = 1;
            loadData(currentPage, combinedPredicate);
        });
    }

    private void sortTilePane(){
        sortComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            String sortBy = newValue.trim();
            tilePaneMain.getChildren().clear();
            scrollPane.setVvalue(0.0);
            currentPage = 1;
            Comparator<Event> eventComparator =null;
            switch (sortBy){
                case "Name":
                    eventComparator = Comparator.comparing(Event::getEventName);
                    eventObservableList.sort(eventComparator);
                    loadData(currentPage,selectedPredicate);
                    break;
                case "Start":
                    eventComparator = Comparator.comparing(Event::getDateStartAsDate);
                    eventObservableList.sort(eventComparator);
                    loadData(currentPage,selectedPredicate);
                    break;
                case "Member":
                    eventComparator = Comparator.comparing(Event::getUserInEvent);
                    eventObservableList.sort(eventComparator);
                    loadData(currentPage,selectedPredicate);
                    break;
                case "End":
                    eventComparator = Comparator.comparing(Event::getDateEndAsDate);
                    eventObservableList.sort(eventComparator);
                    loadData(currentPage,selectedPredicate);
                    break;
            }
        });
    }

    private void initCategory(){
        String category[] = {   "Animal","Art","Business",
                                "Conference","Education","Food & Drink",
                                "Music","Performance","Seminar",
                                "Sport","Workshop"};

        categoryComboBox.getItems().addAll(category);
        categoryComboBox.setValue("");

    }

    private void initSort(){
        String sort[] = {"Name","Start","Member","End"};
        sortComboBox.getItems().addAll(sort);
        sortComboBox.setValue("");
    }

    @FXML
    private void onAllClick(ActionEvent actionEvent) {
        reset();
        allButton.setDisable(true);
        Comparator<Event> eventComparator = Comparator.comparing(Event::getEventName);
        eventObservableList.sort(eventComparator);
        loadData(currentPage,selectedPredicate);


    }

    @FXML
    private void onNewClick(ActionEvent actionEvent) {
        reset();
        newButton.setDisable(true);
        Comparator<Event> eventComparator = Comparator.comparing(Event::getTimestamp);
        eventObservableList.sort(eventComparator);
        loadData(currentPage,selectedPredicate);


    }

    @FXML
    private void onUpClick(ActionEvent actionEvent) {
        reset();
        upComingButton.setDisable(true);
        Comparator<Event> eventComparator = Comparator.comparing(Event::getEventDateEnd);
        eventObservableList.sort(eventComparator);
        loadData(currentPage,selectedPredicate);
    }

    @FXML
    private void onCreateClick(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("create-event",currentUser,null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onCategory(ActionEvent actionEvent) {
        categoryComboBox.show();
        searchbarTextField.setText("");
    }

    @FXML
    private void onSort(ActionEvent actionEvent){
        sortComboBox.show();
    }

    private void reset() {
        allButton.setDisable(false);
        newButton.setDisable(false);
        upComingButton.setDisable(false);
        currentPage = 1;
        scrollPane.setVvalue(0.0);
        tilePaneMain.getChildren().clear();
        selectedPredicate = null;
        categoryComboBox.setValue("");
        categoryButton.setText("Category");
    }

    private boolean checkNode(Event event) {
        for (Node node : tilePaneMain.getChildren()) {
            if (node.getId() != null && node.getId().equals(event.getEventID())) {
                return true;
            }
        }
        return false;
    }

}
