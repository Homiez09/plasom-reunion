package cs211.project.controllers;

import cs211.project.models.*;
import cs211.project.models.collections.*;
import cs211.project.services.*;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    ListView mainListView;
    @FXML
    Button upcommingButton,completeButton, onOwnerButton,memberButton,staffButton;
    @FXML
    ChoiceBox sortChoiceBox;
    @FXML
    ImageView sortIamgeView;
    private boolean ascending = true;
    private User currentUser = (User) FXRouter.getData();
    ;
    private Datasource<EventList> eventDatasource;
    private EventList eventList;
    private UserEventMap mapDatasource ;
    private HashMap<String, Set<String>> hashMap;
    private Set<String> hashSet;
    private ObservableList<Event> observableEventList;


    @FXML
    public void initialize() {
        new LoadNavbarComponent(currentUser, navbarAnchorPane);
        this.eventDatasource = new EventListDataSource("data","event-list.csv");
        this.eventList = eventDatasource.readData();
        eventLabel.setText("My Events");

        this.hashMap = new HashMap<>();
        this.hashSet = new HashSet<>();

        observableEventList = FXCollections.observableArrayList(eventList.getEvents());

        setMainListView(eventList);
    }


    public void  setMainListView(EventList eventList){
        mainListView.getItems().clear();
        this.mapDatasource = new UserEventMap("data", "join-event.csv");
        this.hashMap = mapDatasource.readData();
        this.hashSet = new HashSet<>();

        if (eventList != null){
            for (Event event:eventList.getEvents()){
                if (hashMap.containsKey(event.getEventID())){
                    hashSet = hashMap.get(event.getEventID());
                }
                AnchorPane anchorPane = new AnchorPane();
                new LoadCardEventComponent(anchorPane,event,"card-my-event");

                mainListView.getItems().add(anchorPane);
            }
        }

    }

//    public void showList(EventList eventList) {
//
//        myeventsListView.getItems().clear();
//        historyeventListView.getItems().clear();
//

//
//
//
//        if(eventList != null){
//            for (Event event : observableEventList) {
//                if (hashMap.containsKey(event.getEventID())) {
//                    hashSet = hashMap.get(event.getEventID());
//                }
//                try {
//                    FXMLLoader eventComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/card-my-event.fxml"));
//                    AnchorPane eventAnchorPaneComponent = eventComponentLoader.load();
//                    EventComponentController eventComponent = eventComponentLoader.getController();
//                    eventComponent.setEventData(event);
//
//                    if (hashSet.contains(currentUser.getUsername()) && hashMap.containsKey(event.getEventID()) ) {
//                        if (!event.isEnd()){
//                            myeventsListView.getItems().add(eventAnchorPaneComponent);
//                            onAnimateComponent(eventAnchorPaneComponent);
//                        }else {
//                            historyeventListView.getItems().add(eventAnchorPaneComponent);
//                            onAnimateComponent(eventAnchorPaneComponent);
//                        }
//                    }
//
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//    }
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

    }
    public void onHistorysAction(ActionEvent actionEvent) {

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
    }

    public void onSortClick(MouseEvent mouseEvent) {
        sortChoiceBox.setOnAction(event -> {
            Comparator<Event> dataComparator = getEventComparator(sortChoiceBox.getValue().toString(), ascending);
            sortAndShowEvents(eventList, dataComparator);

        });
    }

    private Comparator<Event> getEventComparator(String sortOption, boolean ascending) {
        Comparator<Event> comparator = null;



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

    private void initSortCheckBox(){
        String sortList[] = {"Name","Date","Member","Tag"};



    }
    private Comparator<Event> setSortChoiceBox(String sortOption){
        Comparator<Event> comparator= null;
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
                break;
        }
     return comparator;
    }
    private void setSearchBar(){

    }

    public void upcommingButtonAction(ActionEvent actionEvent) {
    }

    public void onCompleteAction(ActionEvent actionEvent) {
    }

    public void onMemberAction(ActionEvent actionEvent) {
    }

    public void onOwnerEvent(ActionEvent actionEvent) {
    }


    public void onstaffAction(ActionEvent actionEvent) {
    }

    public void onManageEventButton(ActionEvent actionEvent) {
    }
}

