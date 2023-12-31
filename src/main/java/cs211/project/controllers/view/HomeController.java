package cs211.project.controllers.view;

import cs211.project.models.Event;
import cs211.project.models.User;
import cs211.project.models.collections.EventList;
import cs211.project.services.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class HomeController {
    @FXML private HBox upScrollHbox,newScrollHbox;
    @FXML private VBox homeVbox;
    @FXML private ScrollPane upScrollPane,newScrollPane,homeScrollPane;
    @FXML private AnchorPane navbarAnchorPane;
    @FXML private AnchorPane recTileAnchorPane1,recTileAnchorPane2,recTileAnchorPane3,
            recTileAnchorPane4,recTileAnchorPane5,recTileAnchorPane6;
    @FXML private MenuButton categoryButton,recSortMenuButton;
    @FXML private ImageView categoryButtonImage;
    private final User user = (User) FXRouter.getData();
    private EventList eventList, recEventList;

    @FXML private void initialize() {
        Datasource<EventList> datasource = new EventListDataSource();
        eventList = datasource.readData().getAvailableEvent();

        new LoadNavbarComponent(user, navbarAnchorPane);
        EventList upEventList = eventList.sortUpcoming(eventList);
        EventList newEventList = eventList.sortNewEvent(eventList);
        recEventList = eventList.suffleEvent(eventList);
        setCategoryButtonImage();
        setUpCategoryButton();
        setUpSortMenuButton();
        showRecommendedEvent();

        addScrollEventTile(upScrollHbox,upScrollPane, upEventList);
        addScrollEventTile(newScrollHbox,newScrollPane, newEventList);
    }

    // button to another page
    @FXML private void onUpExploreClick() {
        try {
            FXRouter.goTo("all-events",user,"home","upcoming");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML private void onNewExploreClick() {
        try {
            FXRouter.goTo("all-events",user,"home","new");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML private void onRecExploreClick() {
        try {
            FXRouter.goTo("all-events",user,"home","");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML private void onAllEventClick() {
        try {
            FXRouter.goTo("all-events",user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // set up page
    private void setCategoryButtonImage() {
        String defaultImgPath = "/images/home/blackgrid.png";
        String hoverImgPath = "/images/home/whitegrid.png";
        Image defaultImage = new Image(getClass().getResourceAsStream(defaultImgPath), 20, 20, true, false);
        Image hoverImage = new Image(getClass().getResourceAsStream(hoverImgPath), 20, 20, true, false);
        categoryButtonImage.setImage(defaultImage);
        categoryButton.setStyle("");

        categoryButton.setOnMouseEntered(event -> {
            categoryButtonImage.setImage(hoverImage);
        });

        categoryButton.setOnMouseExited(event -> {
            categoryButtonImage.setImage(defaultImage);
        });
    }
    private void setUpCategoryButton() {
        String[] categories = {"All", "Animal", "Art", "Business", "Conference", "Education",
                "Food & Drink", "Music", "Performance", "Seminar", "Sport", "Workshop"};
        for (String category : categories) {
            MenuItem menuItem = new MenuItem(category);
            menuItem.setOnAction(event -> {
                categoryButton.setText(category);
                if ("All".equals(category)) {
                    recEventList = eventList.sortByMember(eventList);
                } else {
                    recEventList = eventList.sortByTag(eventList, category);
                }
                String sortingOption = recSortMenuButton.getText();
                switch (sortingOption) {
                    case "Sort By : A - Z":
                        recEventList = recEventList.sortByName(recEventList);
                        break;
                    case "Sort By : Z - A":
                        recEventList = recEventList.sortByName(recEventList);
                        Collections.reverse(recEventList.getEvents());
                        break;
                    case "Sort By : Recent Event":
                        recEventList = recEventList.sortNewEvent(recEventList);
                        break;
                    case "Sort By : Upcoming Event":
                        recEventList = recEventList.sortUpcoming(recEventList);
                        break;
                    case "Sort By : Popularity":
                        recEventList = recEventList.sortByMember(recEventList);
                        break;
                }
                showRecommendedEvent();
            });
            categoryButton.getItems().add(menuItem);
        }
    }
    private void setUpSortMenuButton() {
        MenuItem eng = new MenuItem("A - Z");
        eng.setOnAction( event -> {
            recSortMenuButton.setText("Sort By : A - Z");
            recEventList = recEventList.sortByName(recEventList);
            showRecommendedEvent();
        });
        MenuItem reversed = new MenuItem("Z - A");
        reversed.setOnAction( event -> {
            recSortMenuButton.setText("Sort By : Z- A");
            recEventList = recEventList.sortByName(recEventList);
            Collections.reverse(recEventList.getEvents());
            showRecommendedEvent();
        });
        MenuItem recent = new MenuItem("Recent Event");
        recent.setOnAction( event -> {
            recSortMenuButton.setText("Sort By : Recent Event");
            recEventList = recEventList.sortNewEvent(recEventList);
            showRecommendedEvent();
        });
        MenuItem upcoming = new MenuItem("Upcoming Event");
        upcoming.setOnAction( event -> {
            recSortMenuButton.setText("Sort By : Upcoming Event");
            recEventList = recEventList.sortUpcoming(recEventList);
            showRecommendedEvent();
        });
        MenuItem pop = new MenuItem("Popularity");
        pop.setOnAction( event -> {
            recSortMenuButton.setText("Sort By : Popularity");
            recEventList = recEventList.sortByMember(recEventList);
            showRecommendedEvent();
        });
        recSortMenuButton.getItems().addAll(eng,reversed,recent,upcoming,pop);
    }
    private void showRecommendedEvent() {
        ArrayList<AnchorPane> recAnchorPaneList = new ArrayList<>(Arrays.asList(
                recTileAnchorPane1,
                recTileAnchorPane2,
                recTileAnchorPane3,
                recTileAnchorPane4,
                recTileAnchorPane5,
                recTileAnchorPane6
        ));
        for (int i = 0 ; i < 6 ; i++) {
            if(i < recEventList.getEvents().size()) {
                new LoadCardEventComponent(recAnchorPaneList.get(i),recEventList.getEvents().get(i),"tile-event");
            } else {
                recAnchorPaneList.get(i).getChildren().clear();
            }
        }
    }

    // load event tile
    private void addScrollEventTile(HBox hBox,ScrollPane scrollPane,EventList eventList) {
        int numEvent = 0;
        for (Event event : eventList.getEvents()) {
            if (numEvent < 10 && numEvent < eventList.getSizeTotalEvent()) {
                AnchorPane anchorPane = new AnchorPane();
                anchorPane.setPrefWidth(250);
                anchorPane.setPrefHeight(325);
                new LoadCardEventComponent(anchorPane,event,"tile-event");
                hBox.getChildren().add(anchorPane);
                numEvent++;
            }
        }
        scrollPane.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (event.getDeltaY() != 0) {
                if (scrollPane.getHvalue() == 1) {
                    homeScrollPane.setVvalue(homeScrollPane.getVvalue() - event.getDeltaY() / homeVbox.getHeight() );
                } else {
                    scrollPane.setHvalue(scrollPane.getHvalue() - event.getDeltaY() / hBox.getWidth());
                }
                event.consume();
            }
        });
    }

    // button for going through event tile
    @FXML private void onUpLeftButton() {
        double upHvalue = upScrollPane.getHvalue();
        if (upHvalue >= 0.2) {
            upHvalue -= 0.2;
        } else {
            upHvalue = 0;
        }
        upScrollPane.setHvalue(upHvalue);
    }
    @FXML private void onUpRightButton() {
        double upHvalue = upScrollPane.getHvalue();
        if (upHvalue <= 0.8) {
            upHvalue += 0.2;
        } else {
            upHvalue = 1;
        }
        upScrollPane.setHvalue(upHvalue);
    }
    @FXML private void onNewLeftButton() {
        double newHvalue = newScrollPane.getHvalue();
        if (newHvalue >= 0.2) {
            newHvalue -= 0.2;
        } else {
            newHvalue = 0;
        }
        newScrollPane.setHvalue(newHvalue);
    }
    @FXML private void onNewRightButton() {
        double newHvalue = newScrollPane.getHvalue();
        if (newHvalue <= 0.8) {
            newHvalue += 0.2;
        } else {
            newHvalue = 1;
        }
        newScrollPane.setHvalue(newHvalue);
    }

}
