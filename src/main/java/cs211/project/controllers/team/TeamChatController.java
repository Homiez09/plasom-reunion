package cs211.project.controllers.team;

import cs211.project.componentControllers.sideBarControllers.SideBarTeamController;
import cs211.project.models.User;
import cs211.project.models.collections.UserList;
import cs211.project.services.FXRouter;
import cs211.project.services.LoadNavbarComponent;
import cs211.project.services.UserListDataSource;
import cs211.project.services.team.LoadSideBarComponent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class TeamChatController {
    @FXML AnchorPane navbarAnchorPane, sideBarAnchorPane;
    private final User user = (User) FXRouter.getData();

    UserListDataSource datasource ;
    UserList userList ;
    LoadSideBarComponent sideBarAnchorPaneLoad;



    @FXML void initialize(){
        new LoadNavbarComponent(user, navbarAnchorPane);
        LoadSideBarComponent sideBarAnchorPaneLoad;

        sideBarAnchorPaneLoad = new LoadSideBarComponent();
        sideBarAnchorPane.getChildren().add(sideBarAnchorPaneLoad.getSideBarComponent());

        datasource = new UserListDataSource("data","user-list.csv");
        userList = datasource.readData();

        setSideBar();
    }

    protected void setSideBar(){
        SideBarTeamController sideBarTeamController = sideBarAnchorPaneLoad.getController();
        sideBarTeamController.setHoverChat();
    }
}
