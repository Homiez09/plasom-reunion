package cs211.project.controllers;

import cs211.project.componentControllers.EventComponentController;
import cs211.project.models.*;
import cs211.project.models.collections.*;
import cs211.project.services.*;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MyEventsController {
    @FXML
    AnchorPane navbarAnchorPane;
    @FXML
    Label eventLabel;
    @FXML
    ListView eventsListView,hosteventListView, myeventsListView,historyeventListView;
    @FXML
    Button hosteventButton,myeventButton, historyButton;
    @FXML
    ChoiceBox sortChoiceBox;
    @FXML
    ImageView sortIamgeView;
    private boolean ascending = true;
    private User currentUser ;
    private Datasource<EventList> eventDatasource;
    private EventList eventList;
    private UserEventMap mapDatasource ;
    private HashMap<String, Set<String>> hashMap;
    private Set<String> hashSet;
    private ObservableList<Event> observableEventList;


    @FXML
    public void initialize() {
        this.currentUser = (User) FXRouter.getData();
        this.eventDatasource = new EventListDataSource("data","event-list.csv");
        this.eventList = eventDatasource.readData();
        eventLabel.setText("My Events");
        sortChoiceBox.setValue("Name");
        sortChoiceBox.setItems(FXCollections.observableArrayList(
                "Name",
                "Date",
                "Member",
                "Tag"
        ));

        this.hashMap = new HashMap<>();
        this.hashSet = new HashSet<>();

        new LoadNavbarComponent(currentUser, navbarAnchorPane);

        observableEventList = FXCollections.observableArrayList(eventList.getEvents());






        showList(eventList);
    }

    public void showList(EventList eventList) {

        myeventsListView.getItems().clear();
        historyeventListView.getItems().clear();

        this.mapDatasource = new UserEventMap("data", "join-event.csv");
        this.hashMap = mapDatasource.readData();



        if(eventList != null){
            for (Event event : observableEventList) {
                if (hashMap.containsKey(event.getEventID())) {
                    hashSet = hashMap.get(event.getEventID());
                }
                try {
                    FXMLLoader eventComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/event-component.fxml"));
                    AnchorPane eventAnchorPaneComponent = eventComponentLoader.load();
                    EventComponentController eventComponent = eventComponentLoader.getController();
                    eventComponent.setEventData(event);

                    if (hashSet.contains(currentUser.getUsername()) && hashMap.containsKey(event.getEventID()) ) {
                        if (!event.isEnd()){
                            myeventsListView.getItems().add(eventAnchorPaneComponent);
                            onAnimateComponent(eventAnchorPaneComponent);
                        }else {
                            historyeventListView.getItems().add(eventAnchorPaneComponent);
                            onAnimateComponent(eventAnchorPaneComponent);
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
    public void onHostEventsAction(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("host-events",currentUser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void onMyEventsAction(ActionEvent actionEvent) {
        myeventButton.setVisible(false);
        historyButton.setVisible(true);
        eventLabel.setText("My Events");
        myeventsListView.setVisible(true);
        historyeventListView.setVisible(false);
    }
    public void onHistorysAction(ActionEvent actionEvent) {
        myeventButton.setVisible(true);
        historyButton.setVisible(false);
        eventLabel.setText("History Events");
        myeventsListView.setVisible(false);
        historyeventListView.setVisible(true);
    }
    private void onAnimateComponent(AnchorPane anchorPane) {
        ScaleTransition scaleIn = new ScaleTransition(Duration.seconds(0.2), anchorPane);
        scaleIn.setToX(1.1);
        scaleIn.setToY(1.1);

        ScaleTransition scaleOut = new ScaleTransition(Duration.seconds(0.2), anchorPane);
        scaleOut.setToX(1);
        scaleOut.setToY(1);

        anchorPane.setOnMouseEntered(event -> {
            scaleIn.play();
        });

        anchorPane.setOnMouseExited(event -> {
            scaleOut.play();
        });
    }

    private void sortAndShowEvents(EventList eventList, Comparator<Event> comparator) {
        observableEventList.sort(comparator);
        showList(eventList);
    }

    public void onSortClick(MouseEvent mouseEvent) {
        sortChoiceBox.setOnAction(event -> {
            Comparator<Event> dataComparator = getEventComparator(sortChoiceBox.getValue().toString(), ascending);
            sortAndShowEvents(eventList, dataComparator);

        });
    }

    private Comparator<Event> getEventComparator(String sortOption, boolean ascending) {
        Comparator<Event> comparator = null;

        switch (sortOption) {
            case "Name":
                comparator = Comparator.comparing(Event::getEventName);
                break;
            case "Date":
                comparator = Comparator.comparing(Event::getEventDateStart);
                break;
            case "Member":
                comparator = Comparator.comparingInt(Event::getMember);
                break;
            case "Tag":
                comparator = Comparator.comparing(Event::getEventTag);
                break;
            default:
                // Handle other cases if needed
                break;
        }

        if (!ascending) {
            comparator = comparator.reversed();
        }

        return comparator;
    }

    public void onClickAscenDecen(MouseEvent mouseEvent) {
        // Invert the ascending flag
        ascending = !ascending;
        Comparator<Event> dataComparator = getEventComparator(sortChoiceBox.getValue().toString(), ascending);
        sortAndShowEvents(eventList, dataComparator);




        // Update the sort icon
        String imgPath = ascending ? "/images/hostevent/sort-ascending.png" : "/images/hostevent/sort-descending.png";
        sortIamgeView.setImage(new Image(getClass().getResourceAsStream(imgPath)));

    }
}
