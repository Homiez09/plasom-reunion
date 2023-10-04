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
    @FXML private ScrollPane newEventScrollPane;
    @FXML private Button newLeftButton, newRightButton,upLeftButton,upRightButton;
    @FXML private MenuButton categoryButton,recSortMenuButton;
    @FXML private ImageView categoryButtonImage;
    private User user = (User) FXRouter.getData();
    private Datasource<EventList> datasource;
    private EventList eventList,recEventList;
    private int currentIndexOfUp = 1;
    private int currnetIndexOfNew = 1;

    @FXML
    private void initialize() {
        datasource = new EventListDataSource();
        eventList = datasource.readData();

        new LoadNavbarComponent(user, navbarAnchorPane);
        updateButtonState();

        try {
            //if newEvent
            if (eventList != null && !eventList.getEvents().isEmpty() && currnetIndexOfNew >= 1) {
                loadOldEventTile(newLeftAnchorPane,currnetIndexOfNew);
                loadCurrentEventTile(newCenterAnchorPane,eventList.getEvents().get(currnetIndexOfNew));
                loadNextEventTile(newRightAnchorPane,currnetIndexOfNew);
            }
            //if upcomingEvent
            if (eventList != null && !eventList.getEvents().isEmpty() && currnetIndexOfNew >= 1) {
                loadOldEventTile(upLeftAnchorPane,currentIndexOfUp);
                loadCurrentEventTile(upCenterAnchorPane,eventList.getEvents().get(currentIndexOfUp));
                loadNextEventTile(upRightAnchorPane,currentIndexOfUp);
            }
        }catch (IndexOutOfBoundsException e){
            throw new RuntimeException(e);
        }

    setCategoryButtonImage();
    setUpCategoryButton();
    setUpSortMenuButton();
    showRecommendedEvent();
    }

    @FXML protected void onUpExploreClick() {}
    @FXML protected void onNewExploreClick() {}
    @FXML protected void onRecExploreClick() {}
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
        MenuItem all = new MenuItem("All");
        all.setOnAction( event -> {
            categoryButton.setText("All events");
        });
        MenuItem animal = new MenuItem("Animal");
        animal.setOnAction( event -> {
            categoryButton.setText("Animal");
        });
        MenuItem art = new MenuItem("Art");
        art.setOnAction( event -> {
            categoryButton.setText("Art");
        });
        MenuItem business = new MenuItem("Business");
        business.setOnAction( event -> {
            categoryButton.setText("Business");
        });
        MenuItem conference = new MenuItem("Conference");
        conference.setOnAction( event -> {
            categoryButton.setText("Conference");
        });
        MenuItem education = new MenuItem("Education");
        education.setOnAction( event -> {
            categoryButton.setText("Education");
        });
        MenuItem foodDrink = new MenuItem("Food & Drink");
        foodDrink.setOnAction( event -> {
            categoryButton.setText("Food & Drink");
        });
        MenuItem music = new MenuItem("Music");
        music.setOnAction( event -> {
            categoryButton.setText("Music");
        });
        MenuItem performance = new MenuItem("Performance");
        performance.setOnAction( event -> {
            categoryButton.setText("Performance");
        });
        MenuItem sport = new MenuItem("Sport");
        sport.setOnAction( event -> {
            categoryButton.setText("Sport");
        });
        MenuItem workshop = new MenuItem("Workshop");
        workshop.setOnAction( event -> {
            categoryButton.setText("Workshop");
        });
        categoryButton.getItems().addAll(all,animal,art,business,conference,education,foodDrink,music,performance,sport,workshop);
    }
    private void setUpSortMenuButton() {
        MenuItem eng = new MenuItem("A - Z");
        eng.setOnAction( event -> {
            recSortMenuButton.setText("Sort By : A - Z");
        });
        MenuItem thai = new MenuItem("ก - ฮ");
        thai.setOnAction( event -> {
            recSortMenuButton.setText("Sort By : ก - ฮ");
        });
        MenuItem recent = new MenuItem("Recent Event");
        recent.setOnAction( event -> {
            recSortMenuButton.setText("Sort By : Recent Event");
        });
        recSortMenuButton.getItems().addAll(eng,thai,recent);
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
        // use resEventList as sorted eventList
        for (int i = 0 ; i < 6 ; i++) {
            if(i < eventList.getEvents().size()) {
                loadRecommendedEventTile(recAnchorPaneList.get(i),eventList.getEvents().get(i));
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
    private void loadOldEventTile(AnchorPane anchorPane,int currentIndex) {
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
    private void loadNextEventTile(AnchorPane anchorPane,int currentIndex) {
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

    //-------------Button of upComingEvent-------------\\
    public void onNewLeftButton(ActionEvent actionEvent) {
        if (currnetIndexOfNew > 1) {
            currnetIndexOfNew--;
            loadOldEventTile(newLeftAnchorPane,currnetIndexOfNew);
            loadCurrentEventTile(newCenterAnchorPane,eventList.getEvents().get(currnetIndexOfNew));
            loadNextEventTile(newRightAnchorPane,currnetIndexOfNew);
        }
    }
    public void onNewRightButton(ActionEvent actionEvent) {
        if (currnetIndexOfNew < eventList.getEvents().size()) {
            currnetIndexOfNew++;
            loadOldEventTile(newLeftAnchorPane,currnetIndexOfNew);
            loadCurrentEventTile(newCenterAnchorPane,eventList.getEvents().get(currnetIndexOfNew));
            loadNextEventTile(newRightAnchorPane,currnetIndexOfNew);
        }
    }
    //-------------Button of upComingEvent-------------\\

    // -------------Button of upComingEvent-------------\\
    public void onUpLeftButton(ActionEvent actionEvent) {
        if (currentIndexOfUp > 1) {
            currentIndexOfUp--;
            loadOldEventTile(upLeftAnchorPane,currentIndexOfUp);
            loadCurrentEventTile(upCenterAnchorPane,eventList.getEvents().get(currentIndexOfUp));
            loadNextEventTile(upRightAnchorPane,currentIndexOfUp);
        }
    }
    public void onUpRightButton(ActionEvent actionEvent) {
        if (currentIndexOfUp < eventList.getEvents().size()) {
            currentIndexOfUp++;
            loadOldEventTile(upLeftAnchorPane,currentIndexOfUp);
            loadCurrentEventTile(upCenterAnchorPane,eventList.getEvents().get(currentIndexOfUp));
            loadNextEventTile(upRightAnchorPane,currentIndexOfUp);
        }
    }
    //-------------Button of upComingEvent-------------\\

    private void updateButtonState() {
        // ตรวจสอบสถานะของปุ่ม "Left" และ "Right" แล้วอัปเดต
        newLeftButton.setDisable(currnetIndexOfNew == 1);
        newRightButton.setDisable(currnetIndexOfNew == eventList.getEvents().size()-2);

        upLeftButton.setDisable(currentIndexOfUp == 1);
        upRightButton.setDisable(currentIndexOfUp == eventList.getEvents().size()-2);
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
