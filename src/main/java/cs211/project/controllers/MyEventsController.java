package cs211.project.controllers;

import cs211.project.models.*;
import cs211.project.models.collections.*;
import cs211.project.services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;


import java.io.IOException;
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
    private JoinEventMap joinEventMap;

    private HashMap<String, Set<String>> eventMap;
    private Set<String> userSet;
    private JoinTeamMap joinTeamMap ;
    private TeamListDataSource teamListDataSource ;
    private TeamList teamList;
    private HashMap<String, TeamList> teamListHashMap;

    @FXML
    public void initialize() {
        initSortCheckBox();

        new LoadNavbarComponent(currentUser, navbarAnchorPane);

        this.eventDatasource = new EventListDataSource();
        this.eventList = eventDatasource.readData();
        this.teamListDataSource = new TeamListDataSource("data","team-list.csv");
        this.teamList = teamListDataSource.readData();


        mainListView.setPlaceholder(new Label("No Events"));
        setMainListView(filterEvent(eventList,"Member"));
    }


    public void  setMainListView(EventList eventList){
        mainListView.getItems().clear();
        mainListView.getStyleClass().add("event-list");

        if (eventList != null){
            for (Event event:eventList.getEvents()){
                    AnchorPane anchorPane = new AnchorPane();
                    Separator separator = new Separator();
                    new LoadCardEventComponent(anchorPane,event,"card-my-event");

                    separator.setOrientation(Orientation.VERTICAL);
                    separator.setOpacity(0.0);
                    separator.setPrefWidth(24.0);

                    mainListView.getItems().add(separator);
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

    public void onClickAscenDecen(MouseEvent mouseEvent) {
        // Invert the ascending flag
        ascending = !ascending;

        // Update the sort icon
        String imgPath = ascending ? "/images/hostevent/sort-ascending.png" : "/images/hostevent/sort-descending.png";
        sortIamgeView.setImage(new Image(getClass().getResourceAsStream(imgPath)));

    }

    private void initSortCheckBox(){
        String sortList[] = {"Name","Date","Member","Tag"};
        sortChoiceBox.getItems().addAll(sortList);
        sortChoiceBox.setValue("Name");
    }

    private void setSearchBar(){

    }

    public void onUpComingButtonAction(ActionEvent actionEvent) {
        showlist =  filterEvent(eventList,"Upcoming");
        setMainListView(showlist);
        sortBox(showlist);



    }
    public void sortBox(EventList eventList){
        sortChoiceBox.getSelectionModel().selectedItemProperty().addListener((observableValue, o, option) -> {
            EventList list = new EventList();
            if (option != null) {
                String selectedOption = option.toString();
                switch (selectedOption) {
                    case "Name":
                        list = eventList.sortByName(eventList);
                        setMainListView(list);
                        break;
                    case "Date":
                        list = eventList.sortUpcoming(eventList);
                        setMainListView(list);
                        break;
                    case "Member":
                        list = eventList.sortByMember(eventList);
                        setMainListView(list);
                        break;
                    case "Tag":
                        list = eventList.sortByTag(eventList);
                        setMainListView(list);
                        break;
                    default:
                        // กรณีไม่มีตัวเลือกที่ตรงกับ case ใด ๆ
                        break;
                }
            }
        });
    }

    public void onCompleteAction(ActionEvent actionEvent) {
        showlist =  filterEvent(eventList,"Complete");
        setMainListView(showlist);
        sortBox(showlist);

    }

    public void onMemberAction(ActionEvent actionEvent) {
        showlist =  filterEvent(eventList,"Member");
        setMainListView(showlist);
        sortBox(showlist);
    }

    public void onOwnerEventAction(ActionEvent actionEvent) {
        showlist =  filterEvent(eventList,"Owner");
        setMainListView(showlist);
        sortBox(showlist);
    }

    public void onStaffAction(ActionEvent actionEvent) {
        showlist =  filterEvent(eventList,"Staff");
        setMainListView(showlist);
        sortBox(showlist);
    }

    public void onManageEventButton(ActionEvent actionEvent) {
        Popup popup = new Popup();
        VBox popupContent = new VBox();
        popupContent.setStyle("-fx-background-color: #F6F4EE;");

        VBox box = new VBox();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs211/project/views/owner-events.fxml"));
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

    private EventList filterEvent(EventList eventList,String type){
        EventList filterlist = new EventList();
        this.joinEventMap = new JoinEventMap();
        this.eventMap = joinEventMap.readData();


        this.joinTeamMap = new JoinTeamMap();
        this.teamListHashMap = joinTeamMap.readData();
        if (teamListHashMap.containsKey(currentUser.getUsername())){
            this.teamList = teamListHashMap.get(currentUser.getUsername());
        }else {
            teamList = new TeamList();
        }



        switch (type){
            case "Upcomming":
                for (Event event:eventList.getEvents()) {
                    if (eventMap.containsKey(event.getEventID())){
                        userSet = eventMap.get(event.getEventID());
                    }else {
                        userSet = new HashSet<>();
                    }
                    if (event.isUpComming() && userSet.contains(currentUser.getUserId())){
                        filterlist.addEvent(event);
                    }
                }
                break;
            case "Complete":
                for (Event event:eventList.getEvents()) {
                    if (eventMap.containsKey(event.getEventID())){
                        userSet = eventMap.get(event.getEventID());
                    }else {
                        userSet = new HashSet<>();
                    }
                    if (userSet.contains(currentUser.getUserId()) && event.isEnd() || event.isHostEvent(currentUser.getUserId()) && event.isEnd()){
                        filterlist.addEvent(event);
                    }
                }
                break;
            case "Owner":
                for (Event event:eventList.getEvents()) {
                    if (event.isHostEvent(currentUser.getUserId()) && !event.isEnd() ){
                        filterlist.addEvent(event);
                    }
                }
                break;
            case "Member":
                for (Event event:eventList.getEvents()) {
                    if (eventMap.containsKey(event.getEventID())){
                        userSet = eventMap.get(event.getEventID());
                    }else {
                        userSet = new HashSet<>();
                    }
                    if (userSet.contains(currentUser.getUserId()) && !event.isEnd()){
                        filterlist.addEvent(event);
                    }

                }
                break;
            case "Staff":
                for (Event event:eventList.getEvents()) {
                    if (eventMap.containsKey(event.getEventID())){
                        userSet = eventMap.get(event.getEventID());
                    }else {
                        userSet = new HashSet<>();
                    }
                    for (Team team:teamList.getTeams()) {
                        if (team.getEventID().equals(event.getEventID())) {
                            filterlist.addEvent(event);
                            System.out.println("Test");
                            break;
                        }
                    }
                }
                break;

        }


        return filterlist;
    }

}

