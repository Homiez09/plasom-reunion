package cs211.project.controllers.team;

import cs211.project.models.*;
import cs211.project.models.collections.ActivityTeamList;
import cs211.project.models.collections.UserList;
import cs211.project.services.FXRouter;
import cs211.project.services.LoadNavbarComponent;
import cs211.project.services.UserListDataSource;
import cs211.project.services.team.LoadSideBarComponent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class TeamActivityController {
    @FXML private AnchorPane navbarAnchorPane, sideBarAnchorPane, mainActivityAnchorPane, createActivityAnchorPane;
    @FXML private Button createActivityButton;
    @FXML private TableView activityTableView;
    @FXML private TableColumn<ActivityTeam, String> nameColumn, startTimeColumn, endTimeColumn, descriptionColumn;
    @FXML private TableColumn<ActivityTeam, Boolean> statusColumn;
    private final User user = (User) FXRouter.getData();
    private final Event event = (Event) FXRouter.getData2();
    private final Team team = (Team) FXRouter.getData3();
    UserListDataSource datasource;
    UserList userList;

    @FXML void initialize(){
        new LoadNavbarComponent(user, navbarAnchorPane);
        new LoadSideBarComponent(sideBarAnchorPane);

        mainActivityAnchorPane.setVisible(true);
        createActivityAnchorPane.setVisible(false);
        initUser();
        showTable();
    }

    @FXML
    protected void onCreateActivityButtonClick() {}

    @FXML
    protected void onBackClick() {
        try {
            FXRouter.goTo("team-activity", user, event, team);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onKeyTeamName() {}

    @FXML
    protected void onKeyDescriptionCountText() {}

    @FXML
    protected void onCreateTeamButtonClick() {
        showCreateActivity();
    }

    @FXML
    protected void onCancelButtonClick() throws IOException {
        FXRouter.goTo("team-activity", user, event, team);
    }

    private void initUser() {
        if (user.getRole().equals("Member")) createActivityButton.setVisible(false);
    }

    private void showCreateActivity() {
        mainActivityAnchorPane.setVisible(false);
        createActivityAnchorPane.setVisible(true);
    }

    private void showTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setCellFactory(column -> {
            return new TableCell<ActivityTeam, Boolean>() {
                @Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) setText(null);
                    else if (item) setText("Complete");
                    else setText("On going");
                }
            };
        });
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        activityTableView.getColumns().clear();
        activityTableView.getColumns().addAll(nameColumn, startTimeColumn, endTimeColumn, statusColumn, descriptionColumn);

        activityTableView.getItems().clear();
        for (ActivityTeam activity : team.getActivities()) {
            if (team.getTeamID().equals(activity.getTeamID())) {
                activityTableView.getItems().add(activity);
            }
        }

    }
}
