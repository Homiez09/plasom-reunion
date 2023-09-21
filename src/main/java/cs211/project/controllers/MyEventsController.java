package cs211.project.controllers;

import cs211.project.models.*;
import cs211.project.models.collections.*;
import cs211.project.services.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;


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
    Button upcomingButton,completeButton,ownerButton,memberButton,staffButton;
    @FXML
    ChoiceBox sortChoiceBox;
    @FXML
    ImageView sortIamgeView;
    @FXML
    Separator popupTest;
    private boolean ascending = true;
    private User currentUser = (User) FXRouter.getData();
    private Datasource<EventList> eventDatasource;
    private EventList eventList;
    private EventList showlist;
    private JoinEventMap mapDatasource ;
    private HashMap<String, Set<String>> hashMap;
    private Set<String> hashSet;
    private ObservableList<Event> observableEventList;




    @FXML
    public void initialize() {
        // กำหนดโหมดการเลือกเป็น MULTIPLE เพื่อป้องกันการเลือก
        mainListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // กำหนดค่าคุณสมบัติ selectionModel เป็น null เพื่อป้องกันการเลือก
        mainListView.setSelectionModel(null);
        new LoadNavbarComponent(currentUser, navbarAnchorPane);
        this.eventDatasource = new EventListDataSource("data","event-list.csv");
        this.eventList = eventDatasource.readData();


        observableEventList = FXCollections.observableArrayList(eventList.getEvents());
        mainListView.setPlaceholder(new Label("No Events"));
        setMainListView(filterEvent(eventList,"Member"));
    }


    public void  setMainListView(EventList eventList){
        mainListView.getItems().clear();

        if (eventList != null){
            for (Event event:eventList.getEvents()){
                    AnchorPane anchorPane = new AnchorPane();
                    new LoadCardEventComponent(anchorPane,event,"card-my-event");
                    mainListView.getItems().add(anchorPane);

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

    public void onUpComingButtonAction(ActionEvent actionEvent) {
        showlist =  filterEvent(eventList,"Complete");
        setMainListView(showlist);


    }

    public void onCompleteAction(ActionEvent actionEvent) {
        showlist =  filterEvent(eventList,"Complete");
        setMainListView(showlist);

    }

    public void onMemberAction(ActionEvent actionEvent) {
        showlist =  filterEvent(eventList,"Member");
        setMainListView(showlist);

    }

    public void onOwnerEventAction(ActionEvent actionEvent) {
        showlist =  filterEvent(eventList,"Owner");
        setMainListView(showlist);



    }


    public void onStaffAction(ActionEvent actionEvent) {
        showlist =  filterEvent(eventList,"Staff");
        setMainListView(showlist);

    }

    public void onManageEventButton(ActionEvent actionEvent) {
        Popup popup = new Popup();
        VBox popupContent = new VBox();
        popupContent.setStyle("-fx-background-color: #F6F4EE;");
        // กำหนดปุ่มปิดป๊อปอัพ



        // แสดงรายการและปุ่มปิดในป๊อปอัพ
        VBox box = new VBox();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs211/project/views/owner-events.fxml"));
            VBox loaded = loader.load();
            OwnerEventController ownerEventController = loader.getController();
            ownerEventController.setDataPopup(popup);
            ownerEventController.setDataUser(currentUser);
            box.getChildren().setAll(loaded);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        popupContent.getChildren().add(box);

        // กำหนดเนื้อหาในป๊อปอัพ
        popup.getContent().addAll(popupContent);

        // กำหนดตำแหน่งและแสดงป๊อปอัพ
        popup.show(navbarAnchorPane.getScene().getWindow());
    }

    private EventList filterEvent(EventList eventList,String type){
        EventList filterlist = new EventList();
        this.mapDatasource = new JoinEventMap("data", "join-event.csv");
        this.hashMap = mapDatasource.readData();



        switch (type){
            case "Upcomming":
                for (Event event:eventList.getEvents()) {
                    hashSet = hashMap.get(event.getEventID());
                    if (!hashMap.containsKey(event.getEventID())){ hashSet = new HashSet<>();}
                    if (event.isUpComming() && hashSet.contains(currentUser.getUserId())){
                        filterlist.addEvent(event);
                    }
                }
                break;
            case "Complete":
                for (Event event:eventList.getEvents()) {
                    hashSet = hashMap.get(event.getEventID());
                    if (!hashMap.containsKey(event.getEventID())){ hashSet = new HashSet<>();}
                    if (event.isEnd() && hashSet.contains(currentUser.getUserId())){
                        filterlist.addEvent(event);
                    }
                }
                break;
            case "Owner":
                for (Event event:eventList.getEvents()) {
                    if (event.isHostEvent(currentUser.getUserId()) && !event.isEnd()){
                        filterlist.addEvent(event);
                    }
                }
                break;
            case "Member":
                for (Event event:eventList.getEvents()) {
                    hashSet = hashMap.get(event.getEventID());
                    if (!hashMap.containsKey(event.getEventID())){ hashSet = new HashSet<>();}
                    if (!event.isEnd() && hashSet.contains(currentUser.getUserId())){
                        filterlist.addEvent(event);
                    }
                }
                break;
            case "Staff":
                for (Event event:eventList.getEvents()) {
                    hashSet = hashMap.get(event.getEventID());
                    if (!hashMap.containsKey(event.getEventID())){ hashSet = new HashSet<>();}
                    if (event.isJoinTeam()){
                        filterlist.addEvent(event);
                    }
                }
                break;
        }
        return filterlist;
    }

}

