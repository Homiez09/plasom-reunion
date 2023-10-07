package cs211.project.componentControllers;

import cs211.project.models.Event;
import cs211.project.models.collections.EventList;
import cs211.project.services.EventListDataSource;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Shape;

public class CardUpcoming {
    @FXML private Button backButton, nextButton;
    @FXML private Shape backCircle, nextCircle;
    @FXML private ImageView upComingEventsImageView;
    @FXML private Label eventUpComingDate, eventUpComingName, eventUpComingPlace;

    EventListDataSource eventDatasource;
    EventList eventList;

    private int page = 0;
    private final int maxPage = 5;

    @FXML
    private void initialize() {
        eventDatasource = new EventListDataSource();
        eventList = eventDatasource.readData();

        showImage(page);
        updateVisibleButton();
    }

    @FXML
    private void onNextButtonClick() {
        if (page < maxPage) {
            page++;
        }
        showImage(page);
    }
    @FXML
    private void onBackButtonClick() {
        if (page > 0) {
            page--;
        }
        showImage(page);
    }

    private void updateVisibleButton() {
        backButton.setVisible(page > 0);
        backCircle.setVisible(page > 0);
        nextButton.setVisible(page != maxPage);
        nextCircle.setVisible(page != maxPage);
    }

    private void showImage(int pageNumber) {
        Event event;
        Image image;

        if (eventList.getUpcomingEvent().getEvents().size() == 0) {
            event = eventList.getEvents().get(pageNumber);
        } else {
            event = eventList.getUpcomingEvent().getEvents().get(pageNumber);
        }

        if (event.getEventImagePath().equals("null")) image = new Image(getClass().getResourceAsStream("/images/events/event-default-auth.png"));
        else image = new Image("file:" + event.getEventImagePath(), 1280, 1280, false, false);

        upComingEventsImageView.setImage(image);

        eventUpComingDate.setText(event.getEventDateStart());
        eventUpComingName.setText(event.getEventName());
        eventUpComingPlace.setText(event.getEventLocation());

        updateVisibleButton();
    }
}
