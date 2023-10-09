package cs211.project.controllers;

import cs211.project.componentControllers.EventTileController;
import cs211.project.models.Event;
import cs211.project.models.User;
import cs211.project.models.collections.EventList;
import cs211.project.services.*;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class HomeController {
    @FXML private HBox upScrollHbox,newScrollHbox;
    @FXML private ScrollPane upScrollPane,newScrollPane;
    @FXML private AnchorPane navbarAnchorPane;
    @FXML private AnchorPane recTileAnchorPane1,recTileAnchorPane2,recTileAnchorPane3,
            recTileAnchorPane4,recTileAnchorPane5,recTileAnchorPane6;
    @FXML private MenuButton categoryButton,recSortMenuButton;
    @FXML private ImageView categoryButtonImage;
    private User user = (User) FXRouter.getData();
    private Datasource<EventList> datasource;
    private EventList eventList,recEventList,newEventList,upEventList;

    @FXML private void initialize() {
        datasource = new EventListDataSource();
        eventList = datasource.readData().getAvailableEvent();

        new LoadNavbarComponent(user, navbarAnchorPane);
        upEventList = eventList.sortUpcoming(eventList);
        newEventList = eventList.sortNewEvent(eventList);
        recEventList = eventList.suffleEvent(eventList);
        setCategoryButtonImage();
        setUpCategoryButton();
        setUpSortMenuButton();
        showRecommendedEvent();

        addScrollEventTile(upScrollHbox,upScrollPane,upEventList);
        addScrollEventTile(newScrollHbox,newScrollPane,newEventList);
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
                loadRecommendedEventTile(recAnchorPaneList.get(i),recEventList.getEvents().get(i));
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
                anchorPane.setPrefHeight(400);
                try {
                    FXMLLoader eventTileLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/event-tile.fxml"));
                    AnchorPane load = eventTileLoader.load();
                    EventTileController eventTileController = eventTileLoader.getController();
                    eventTileController.showEventTile(event);

                    anchorPane.getChildren().setAll(load);
                    AnimateComponent(load);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                hBox.getChildren().add(anchorPane);
                numEvent++;
            }
        }
        scrollPane.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (event.getDeltaY() != 0) {
                scrollPane.setHvalue(scrollPane.getHvalue() - event.getDeltaY() / hBox.getWidth());
                event.consume();
            }
        });
        scrollPane.setOnScroll(event -> {
            if(event.getDeltaX() == 0 && event.getDeltaY() != 0) {
                scrollPane.setHvalue(scrollPane.getHvalue() - event.getDeltaY() / hBox.getWidth());
            }
        });
    }
    private void loadRecommendedEventTile(AnchorPane anchorPane,Event event) {
        try {
            FXMLLoader eventTileLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/event-tile.fxml"));
            AnchorPane load = eventTileLoader.load();
            EventTileController eventTileController = eventTileLoader.getController();
            eventTileController.showEventTile(event);

            anchorPane.getChildren().setAll(load);
            AnimateComponent(load);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    // anchorPane's zoom animation
    private void AnimateComponent(AnchorPane anchorPane) {
        ScaleTransition scaleIn = new ScaleTransition(Duration.seconds(0.2), anchorPane);
        scaleIn.setToX(1.1);
        scaleIn.setToY(1.1);
        ScaleTransition scaleOut = new ScaleTransition(Duration.seconds(0.2), anchorPane);
        scaleOut.setToX(1);
        scaleOut.setToY(1);
        anchorPane.setOnMouseEntered(event -> {scaleIn.play();});
        anchorPane.setOnMouseExited(event -> {scaleOut.play();});
    }
}
