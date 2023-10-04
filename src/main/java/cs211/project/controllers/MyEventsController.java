package cs211.project.controllers;

import cs211.project.componentControllers.OwnerEventController;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class MyEventsController extends AllEventListController {
    @FXML AnchorPane navbarAnchorPane;
    @FXML Button allButton,completeButton,ownerButton,memberButton,staffButton;
    @FXML ComboBox<String> sortComboBox;
    @FXML Separator popupTest;
    @FXML TilePane tilePaneMain;
    @FXML ScrollPane scrollPane;
    @FXML TextField searchbarTextField;
    //----------------------------
    private User currentUser = (User) FXRouter.getData();
    private ObservableList<Event> eventObservableList;
    private int currentPage = 1;
    private final int itemsPerPage = 4;
    private Predicate<Event> selectedPredicate = null;



    @FXML
    private void initialize() {
        new LoadNavbarComponent(currentUser, navbarAnchorPane);
        setupPage();
        allButton.setDisable(true);

        getBySearch();
    }

    private void setupPage(){
        Datasource<EventList> eventDatasource = new EventListDataSource();
        EventList eventList = eventDatasource.readData();
        eventObservableList = FXCollections.observableArrayList(eventList.getCurrentEvent(currentUser));
        System.out.println(eventObservableList.size());
        initSort();
        setupScrollBar();
        loadData(currentPage,selectedPredicate);
    }

    private void loadData(int page,Predicate<Event> selectedPredicate) {
        FilteredList<Event> filteredList = new FilteredList<>(eventObservableList,selectedPredicate);

        int startIndex = (page - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, filteredList.size());

        for (int i = startIndex; i < endIndex; i++) {
            Event event = filteredList.get(i);
            if (!checkNode(event)) {
                AnchorPane anchorPane = new AnchorPane();
                anchorPane.setUserData(event);
                anchorPane.setId(event.getEventID());
                new LoadCardEventComponent(anchorPane, event, "card-my-event");
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
                if (value >= 0.55 && tilePaneMain.getChildren().size()/itemsPerPage == currentPage) {
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

            Predicate<Event> combinedPredicate = searchPredicate;
            if (categoryComboBox.getValue() != null && !categoryComboBox.getValue().isEmpty()) {
                combinedPredicate = combinedPredicate.and(selectedPredicate);
            }

            removeNode(combinedPredicate);
            currentPage = 1;
            loadData(currentPage, combinedPredicate);
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
        loadData(currentPage,selectedPredicate);
        allButton.setDisable(true);
    }

    @FXML
    public void onCompleteAction(ActionEvent actionEvent) {
        reset();
        completeButton.setDisable(true);
    }

    @FXML
    private void onOwnerEventAction(ActionEvent actionEvent) {
        reset();
        ownerButton.setDisable(true);
    }
    @FXML
    private void onMemberAction(ActionEvent actionEvent) {
        reset();
        memberButton.setDisable(true);
    }


    @FXML
    private void onStaffAction(ActionEvent actionEvent) {
        reset();
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
        scrollPane.setVvalue(0.0);
        tilePaneMain.getChildren().clear();
        selectedPredicate = null;
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

