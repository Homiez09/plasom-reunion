package cs211.project.controllers;

import cs211.project.componentControllers.OwnerEventController;
import cs211.project.models.*;
import cs211.project.models.Event;
import cs211.project.models.collections.*;
import cs211.project.services.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class MyEventsController extends AllEventListController {
    @FXML AnchorPane navbarAnchorPane;
    @FXML Button allButton,completeButton,ownerButton,memberButton,staffButton;
    @FXML ComboBox<String> sortComboBox;
    @FXML Separator popupTest;
    @FXML ListView<Node> listViewMain;
    @FXML TextField searchbarTextField;
    //----------------------------
    private User currentUser = (User) FXRouter.getData();
    private ObservableList<Event> eventObservableList;
    private EventList eventList;
    private int currentPage = 1;
    private final int itemsPerPage = 4;
    private Predicate<Event> selectedPredicate = null;
    private ObservableList<Node> nodes;



    @FXML
    private void initialize() {
        new LoadNavbarComponent(currentUser, navbarAnchorPane);
        setupPage();
        allButton.setDisable(true);
        getBySearch();
        sortTilePane();
    }

    private void setupPage(){
        Datasource<EventList> eventDatasource = new EventListDataSource();
        eventList = eventDatasource.readData();
        eventObservableList = FXCollections.observableArrayList(eventList.getUserEvent(currentUser));
        initSort();
        setupScrollBar();
        loadData(currentPage,selectedPredicate);
    }

    private void loadData(int page,Predicate<Event> selectedPredicate) {
        FilteredList<Event> filteredList = new FilteredList<>(eventObservableList,selectedPredicate);
        System.out.println(filteredList.size());
        int startIndex = (page - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, filteredList.size());
        if (nodes ==null) {
            nodes = FXCollections.observableArrayList();
        }
        for (int i = startIndex; i < endIndex; i++) {
            Event event = filteredList.get(i);
            if (!checkNode(event) && !event.isEnd()) {
                AnchorPane anchorPane = new AnchorPane();
                anchorPane.setUserData(event);
                anchorPane.setId(event.getEventID());
                new LoadCardEventComponent(anchorPane, event, "card-my-event");
                nodes.add(anchorPane);
            }
        }
        listViewMain.setItems(nodes);
    }

    @FXML
    private void loadMore() {
        currentPage++;
        loadData(currentPage,selectedPredicate);
    }

    @FXML
    private void setupScrollBar() {
            listViewMain.addEventHandler(ScrollEvent.SCROLL, scrollEvent ->{
                if (scrollEvent.getDeltaY() < 0 && (listViewMain.getItems().size()/itemsPerPage == currentPage)){
                    loadMore();
                }
            });
    }

    private void removeNode(Predicate<Event> selectedPredicate) {
        FilteredList<Event> filteredList = eventObservableList.filtered(selectedPredicate);
        List<Node> nodesToRemove = new ArrayList<>();
        for (Node node: listViewMain.getItems()) {
            Event event = (Event) node.getUserData();
            if (!filteredList.contains(event)) {
                nodesToRemove.add(node);
            }
        }
        listViewMain.getItems().removeAll(nodesToRemove);
    }

    private void initSort(){
        String sort[] = {"Name","Start","Member","End"};
        sortComboBox.getItems().addAll(sort);
        sortComboBox.setValue("");
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
            currentPage = 1;
            loadData(currentPage, searchPredicate);
        });
    }

    private void sortTilePane(){
        sortComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            String sortBy = newValue.trim();
            listViewMain.getItems().clear();
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

    private void checkEventList(){

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
        loadData(currentPage,selectedPredicate);
        allButton.setDisable(true);
    }

    @FXML
    public void onCompleteAction(ActionEvent actionEvent) {
        reset();
        eventObservableList = FXCollections.observableArrayList(eventList.getCompleteEvent(currentUser));
        loadData(currentPage,selectedPredicate);
        completeButton.setDisable(true);
    }

    @FXML
    private void onOwnerEventAction(ActionEvent actionEvent) {
        reset();
        eventObservableList = FXCollections.observableArrayList(eventList.getOwnerEvent(currentUser));
        loadData(currentPage,selectedPredicate);
        ownerButton.setDisable(true);
    }

    @FXML
    private void onMemberAction(ActionEvent actionEvent) {
        reset();
        eventObservableList = FXCollections.observableArrayList(eventList.getUserInEvent(currentUser));
        loadData(currentPage,selectedPredicate);
        memberButton.setDisable(true);
    }

    @FXML
    private void onStaffAction(ActionEvent actionEvent) {
        reset();
        eventObservableList = FXCollections.observableArrayList(eventList.getTeamEvent(currentUser));
        loadData(currentPage,selectedPredicate);
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
            ownerEventController.setDataPopup(popup,currentUser);
            box.getChildren().setAll(loaded);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        popupContent.getChildren().add(box);
        popup.getContent().addAll(popupContent);
        popup.show(navbarAnchorPane.getScene().getWindow());
        if (popup.isShowing()) {
            listViewMain.refresh();
        }
    }

    @FXML
    private void onBackAction(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("home",currentUser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void reset() {
        allButton.setDisable(false);
        completeButton.setDisable(false);
        ownerButton.setDisable(false);
        memberButton.setDisable(false);
        staffButton.setDisable(false);
        currentPage = 1;
        listViewMain.getItems().clear();
        selectedPredicate = null;
    }

    private boolean checkNode(Event event) {
        for (Node node : listViewMain.getItems()) {
            if (node.getId() != null && node.getId().equals(event.getEventID())) {
                return true;
            }
        }
        return false;
    }


}

