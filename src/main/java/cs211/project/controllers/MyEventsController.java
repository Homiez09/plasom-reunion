package cs211.project.controllers;

import cs211.project.componentControllers.OwnerEventController;
import cs211.project.models.*;
import cs211.project.models.Event;
import cs211.project.models.collections.*;
import cs211.project.services.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.util.Callback;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class MyEventsController{
    @FXML AnchorPane navbarAnchorPane;
    @FXML Button allButton,completeButton,ownerButton,memberButton,staffButton;
    @FXML ComboBox<String> sortComboBox;
    @FXML MenuButton sortMenuButton;
    @FXML Separator popupTest;
    @FXML ListView<Node> listViewMain;
    @FXML TextField searchbarTextField;
    //----------------------------
    private User currentUser = (User) FXRouter.getData();
    private String from = (String) FXRouter.getData2();
    private ObservableList<Event> eventObservableList;
    private Datasource<EventList> eventDatasource;
    private EventList eventList;
    private Predicate<Event> selectedPredicate = null;
    private ObservableList<Node> nodes;
    private ScrollBar scrollBar ;


    @FXML
    private void initialize() {
        new LoadNavbarComponent(currentUser, navbarAnchorPane);
        setupPage();
        allButton.setDisable(true);
        getBySearch();
        if (from!=null){
            resetButton();
            eventObservableList = FXCollections.observableArrayList(eventList.getUserInEvent(currentUser));
            memberButton.setDisable(true);
        }
        loadFirst(selectedPredicate);
        listViewMain.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Node> call(ListView<Node> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Node item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setGraphic(item);
                        } else {
                            setGraphic(null);
                        }
                        setAlignment(Pos.CENTER);
                    }
                };
            }
        });
    }

    private void setupPage(){
        eventDatasource = new EventListDataSource();
        eventList = eventDatasource.readData();
        eventObservableList = FXCollections.observableArrayList(eventList.getUserEvent(currentUser));
        setupScrollBar();
    }

    private void loadFirst(Predicate<Event> selectedPredicate) {
        FilteredList<Event> filteredList = new FilteredList<>(eventObservableList, selectedPredicate);
        if (nodes == null) {
            nodes = FXCollections.observableArrayList();
        }
        LoadComponent(filteredList, 3);
    }
    private void loadMore(Predicate<Event> selectedPredicate) {
        FilteredList<Event> filteredList = new FilteredList<>(eventObservableList, selectedPredicate);
        LoadComponent(filteredList, 1);
    }

    private void LoadComponent(FilteredList<Event> filteredList, int maxItemsPerLoad) {
        int itemsLoaded = 0;
        for (Event event : filteredList) {
            if (!checkNode(event)) {
                AnchorPane anchorPane = new AnchorPane();
                anchorPane.setUserData(event);
                anchorPane.setId(event.getEventID());
                new LoadCardEventComponent(anchorPane, event, "card-my-event");
                nodes.add(anchorPane);
                itemsLoaded++;
                if (itemsLoaded >= maxItemsPerLoad) {
                    break;
                }
            }
        }
        listViewMain.setItems(nodes);
        if (scrollBar!=null){
            scrollBar.setValue(0.5);
        }
    }


    @FXML
    private void setupScrollBar() {
        Platform.runLater(() -> {
            scrollBar = (ScrollBar) listViewMain.lookup(".scroll-bar:vertical");
            scrollBar.valueProperty().addListener((observable, oldValue, newValue) -> {
                double scrollValue = newValue.doubleValue();
                if (scrollValue > 0.8 && nodes.size() < eventObservableList.size()){
                    loadMore(selectedPredicate);
                }
            });
        });
    }

    private void removeNode(Predicate<Event> selectedPredicate) {
        FilteredList<Event> filteredList = eventObservableList.filtered(selectedPredicate);
        List<Node> nodesToRemove = new ArrayList<>();
        for (Node node: nodes) {
            Event event = (Event) node.getUserData();
            if (!filteredList.contains(event)) {
                nodesToRemove.add(node);
            }
        }
//        listViewMain.getItems().removeAll(nodesToRemove);
        nodes.removeAll(nodesToRemove);
    }

    private void getBySearch(){
        searchbarTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            String searchText = newValue.toLowerCase().trim();
            Predicate<Event> searchPredicate = event -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                return event.getEventName().toLowerCase().contains(searchText);
            };
            removeNode(searchPredicate);
            loadFirst(searchPredicate);
            sortComboBox.setValue("");
        });
    }

    @FXML
    private void onCreateAction(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("create-event",currentUser,null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onAllAction(ActionEvent actionEvent) {
        reset();
        eventObservableList = FXCollections.observableArrayList(eventList.getUserEvent(currentUser));
        removeNode(selectedPredicate);
        loadFirst(selectedPredicate);
        allButton.setDisable(true);
    }

    @FXML
    public void onCompleteAction(ActionEvent actionEvent) {
        reset();
        eventObservableList = FXCollections.observableArrayList(eventList.getCompleteEvent(currentUser));
        removeNode(selectedPredicate);
        loadFirst(selectedPredicate);
        completeButton.setDisable(true);
    }

    @FXML
    private void onOwnerEventAction(ActionEvent actionEvent) {
        reset();
        eventObservableList = FXCollections.observableArrayList(eventList.getOwnerEvent(currentUser));
        removeNode(selectedPredicate);
        loadFirst(selectedPredicate);
        ownerButton.setDisable(true);
    }

    @FXML
    private void onMemberAction(ActionEvent actionEvent) {
        reset();
        eventObservableList = FXCollections.observableArrayList(eventList.getUserInEvent(currentUser));
        removeNode(selectedPredicate);
        loadFirst(selectedPredicate);
        memberButton.setDisable(true);
    }

    @FXML
    private void onStaffAction(ActionEvent actionEvent) {
        reset();
        eventObservableList = FXCollections.observableArrayList(eventList.getTeamEvent(currentUser));
        removeNode(selectedPredicate);
        loadFirst(selectedPredicate);
        staffButton.setDisable(true);
    }

    @FXML
    private void onManageEventButton(ActionEvent actionEvent) {
        Popup popup = new Popup();
        VBox popupContent = new VBox();
        popup.setAutoHide(true);
        VBox box = new VBox();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/owner-event.fxml"));
            VBox loaded = loader.load();
            OwnerEventController ownerEventController = loader.getController();
            ownerEventController.setDataPopup(popup,currentUser,nodes,eventObservableList);
            box.getChildren().setAll(loaded);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        popupContent.getChildren().add(box);
        popup.getContent().addAll(popupContent);
        popup.show(navbarAnchorPane.getScene().getWindow());
        itemLess();
    }

    private void reset() {
        resetButton();
        selectedPredicate = null;
        eventList = eventDatasource.readData();
        eventObservableList = FXCollections.observableArrayList(eventList.getEvents());
    }

    private void resetButton(){
        allButton.setDisable(false);
        completeButton.setDisable(false);
        ownerButton.setDisable(false);
        memberButton.setDisable(false);
        staffButton.setDisable(false);
    }

    private boolean checkNode(Event event) {
        for (Node node : listViewMain.getItems()) {
            if (node.getId() != null && node.getId().equals(event.getEventID())) {
                return true;
            }
        }
        return false;
    }

    private void itemLess(){
        listViewMain.getItems().addListener((ListChangeListener<? super Node>) change -> {
                int itemCount = listViewMain.getItems().size();
                if (itemCount == 2) {
                    System.out.println("Test Lock");
                    loadFirst(selectedPredicate);
                }
        });
    }

    public void onEventsClick(MouseEvent mouseEvent) {
        try {
            FXRouter.goTo("all-events",currentUser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onSortName(ActionEvent actionEvent) {
        listViewMain.getItems().clear();
        Comparator<Event> eventComparator = Comparator.comparing(Event::getEventName);
        eventObservableList.sort(eventComparator);
        loadFirst(selectedPredicate);
        sortMenuButton.setText("A - Z");
        searchbarTextField.setText("");
    }

    public void onSortStart(ActionEvent actionEvent) {
        listViewMain.getItems().clear();
        Comparator<Event> eventComparator = Comparator.comparing(Event::getDateStartAsDate);
        eventObservableList.sort(eventComparator);
        loadFirst(selectedPredicate);
        sortMenuButton.setText("Start Date");
        searchbarTextField.setText("");
    }

    public void onSortPopularity(ActionEvent actionEvent) {
        listViewMain.getItems().clear();
        Comparator<Event> eventComparator = Comparator.comparing(Event::getUserInEvent);
        eventObservableList.sort(eventComparator);
        Collections.reverse(eventObservableList);
        loadFirst(selectedPredicate);
        sortMenuButton.setText("Popularity");
        searchbarTextField.setText("");
    }

    public void onSortEnd(ActionEvent actionEvent) {
        listViewMain.getItems().clear();
        Comparator<Event> eventComparator = Comparator.comparing(Event::getDateEndAsDate);
        eventObservableList.sort(eventComparator);
        loadFirst(selectedPredicate);
        sortMenuButton.setText("End Date");
        searchbarTextField.setText("");
    }
}

