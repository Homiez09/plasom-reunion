package cs211.project.controllers;

import cs211.project.componentControllers.EventTileController;
import cs211.project.models.Event;
import cs211.project.models.User;
import cs211.project.models.collections.EventList;
import cs211.project.services.*;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class HomeController {
    @FXML private AnchorPane navbarAnchorPane;
    @FXML private AnchorPane upLeftAnchorPane, upRightAnchorPane, upCenterAnchorPane;
    @FXML private AnchorPane newLeftAnchorPane, newRightAnchorPane, newCenterAnchorPane;
    @FXML private AnchorPane recTileAnchorPane1,recTileAnchorPane2,recTileAnchorPane3,
            recTileAnchorPane4,recTileAnchorPane5,recTileAnchorPane6;
    @FXML private Button newLeftButton, newRightButton,upLeftButton,upRightButton;
    @FXML private MenuButton categoryButton,recSortMenuButton;
    @FXML private ImageView categoryButtonImage;
    private User user = (User) FXRouter.getData();
    private Datasource<EventList> datasource;
    private EventList eventList,recEventList,newEventList,upEventList;
    private int currentIndexOfUp = 1;
    private int currnetIndexOfNew = 1;

    @FXML
    private void initialize() {
        datasource = new EventListDataSource();
        eventList = datasource.readData().getAvailableEvent();

        new LoadNavbarComponent(user, navbarAnchorPane);
        upEventList = eventList.sortUpcoming(eventList);
        newEventList = eventList.sortNewEvent(eventList);
        updateButtonState();

        try {
            //if newEvent
            if (newEventList != null && !newEventList.getEvents().isEmpty() && currnetIndexOfNew >= 1) {
                loadOldEventTile(newLeftAnchorPane,currnetIndexOfNew,newEventList);
                loadCurrentEventTile(newCenterAnchorPane,newEventList.getEvents().get(currnetIndexOfNew));
                loadNextEventTile(newRightAnchorPane,currnetIndexOfNew,newEventList);
            }
            //if upcomingEvent
            if (upEventList != null && !upEventList.getEvents().isEmpty() && currentIndexOfUp >= 1) {
                loadOldEventTile(upLeftAnchorPane,currentIndexOfUp,upEventList);
                loadCurrentEventTile(upCenterAnchorPane,upEventList.getEvents().get(currentIndexOfUp));
                loadNextEventTile(upRightAnchorPane,currentIndexOfUp,upEventList);
            }
        }catch (IndexOutOfBoundsException e){
            throw new RuntimeException(e);
        }

        recEventList = eventList.suffleEvent(eventList);
        setCategoryButtonImage();
        setUpCategoryButton();
        setUpSortMenuButton();
        showRecommendedEvent();
    }

    @FXML protected void onUpExploreClick() {
        try {
            FXRouter.goTo("all-events",user,"home","upcoming");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML protected void onNewExploreClick() {
        try {
            FXRouter.goTo("all-events",user,"home","new");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML protected void onRecExploreClick() {
        try {
            FXRouter.goTo("all-events",user,"home","");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
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
        recSortMenuButton.getItems().addAll(eng,recent,upcoming,pop);
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
    private void loadCurrentEventTile(AnchorPane anchorPane,Event event) {
        try {
            FXMLLoader eventTileLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/event-tile.fxml"));
            AnchorPane load = eventTileLoader.load();
            EventTileController eventTileController = eventTileLoader.getController();
            eventTileController.showEventTile(event);

            anchorPane.getChildren().setAll(load);
            AnimateComponent(load);

            // ตรวจสอบสถานะของปุ่ม "Left" และ "Right"
            updateButtonState();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadOldEventTile(AnchorPane anchorPane,int currentIndex, EventList eventList) {
        if (currentIndex <= eventList.getEvents().size()) {
            try {
                FXMLLoader eventTileLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/event-tile.fxml"));
                AnchorPane load = eventTileLoader.load();
                EventTileController eventTileController = eventTileLoader.getController();
                eventTileController.showEventTile(eventList.getEvents().get(currentIndex - 1));

                anchorPane.getChildren().setAll(load);
                AnimateComponent(load);
                updateButtonState();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            // ถ้าไม่มี Event ถัดไป
            anchorPane.getChildren().clear();
        }
    }
    private void loadNextEventTile(AnchorPane anchorPane,int currentIndex, EventList eventList) {
        if (currentIndex < eventList.getEvents().size() - 1) {
            try {
                FXMLLoader eventTileLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/event-tile.fxml"));
                AnchorPane load = eventTileLoader.load();
                EventTileController eventTileController = eventTileLoader.getController();
                eventTileController.showEventTile(eventList.getEvents().get(currentIndex + 1));

                anchorPane.getChildren().setAll(load);
                AnimateComponent(load);
                updateButtonState();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            // ถ้าไม่มี Event ถัดไป
            anchorPane.getChildren().clear();
        }
    }

    @FXML protected void onNewLeftButton() {
        if (currnetIndexOfNew > 1) {
            currnetIndexOfNew--;
            loadOldEventTile(newLeftAnchorPane,currnetIndexOfNew,newEventList);
            loadCurrentEventTile(newCenterAnchorPane,newEventList.getEvents().get(currnetIndexOfNew));
            loadNextEventTile(newRightAnchorPane,currnetIndexOfNew,newEventList);
        }
    }
    @FXML protected void onNewRightButton() {
        if (currnetIndexOfNew < newEventList.getEvents().size()) {
            currnetIndexOfNew++;
            loadOldEventTile(newLeftAnchorPane,currnetIndexOfNew,newEventList);
            loadCurrentEventTile(newCenterAnchorPane,newEventList.getEvents().get(currnetIndexOfNew));
            loadNextEventTile(newRightAnchorPane,currnetIndexOfNew,newEventList);
        }
    }

    @FXML protected void onUpLeftButton() {
        if (currentIndexOfUp > 1) {
            currentIndexOfUp--;
            loadOldEventTile(upLeftAnchorPane,currentIndexOfUp,upEventList);
            loadCurrentEventTile(upCenterAnchorPane,upEventList.getEvents().get(currentIndexOfUp));
            loadNextEventTile(upRightAnchorPane,currentIndexOfUp,upEventList);
        }
    }
    @FXML protected void onUpRightButton() {
        if (currentIndexOfUp < upEventList.getEvents().size()) {
            currentIndexOfUp++;
            loadOldEventTile(upLeftAnchorPane,currentIndexOfUp,upEventList);
            loadCurrentEventTile(upCenterAnchorPane,upEventList.getEvents().get(currentIndexOfUp));
            loadNextEventTile(upRightAnchorPane,currentIndexOfUp,upEventList);
        }
    }

    private void updateButtonState() {
        // ตรวจสอบสถานะของปุ่ม "Left" และ "Right" แล้วอัปเดต
        newLeftButton.setDisable(currnetIndexOfNew == 1);
        newRightButton.setDisable(currnetIndexOfNew == newEventList.getEvents().size()-2);

        upLeftButton.setDisable(currentIndexOfUp == 1);
        upRightButton.setDisable(currentIndexOfUp == upEventList.getEvents().size()-2);
    }

    //-------------Animate Zoom for any AnchorPane-------------\\
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
    //-------------Animate Zoom for any AnchorPane-------------\\

    public void onClickButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("all-events",user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
