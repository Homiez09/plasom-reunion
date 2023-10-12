module cs211.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires bcrypt;
    requires java.sql;

    opens cs211.project.cs211661project to javafx.fxml;
    exports cs211.project.cs211661project;

    exports cs211.project.controllers.view;
    opens cs211.project.controllers.view to javafx.fxml;

    exports cs211.project.controllers.component;
    opens cs211.project.controllers.component to javafx.fxml;

    exports cs211.project.controllers.view.admin;
    opens cs211.project.controllers.view.admin to javafx.fxml;

    exports cs211.project.models;
    opens cs211.project.models to javafx.base;

    exports cs211.project.models.collections;
    opens cs211.project.models.collections to javafx.base;

    exports cs211.project.controllers.component.teamControllers.teamboxControllers;
    opens cs211.project.controllers.component.teamControllers.teamboxControllers to javafx.fxml;

    exports cs211.project.controllers.view.team;
    opens cs211.project.controllers.view.team to javafx.fxml;

    exports cs211.project.controllers.component.sideBarControllers;
    opens cs211.project.controllers.component.sideBarControllers to javafx.fxml;

    exports cs211.project.controllers.component.teamControllers.manageTeamsController;
    opens cs211.project.controllers.component.teamControllers.manageTeamsController to javafx.fxml;

    exports cs211.project.controllers.component.teamControllers.manageTeamController;
    opens cs211.project.controllers.component.teamControllers.manageTeamController to javafx.fxml;

    exports cs211.project.controllers.component.alertBox;
    opens cs211.project.controllers.component.alertBox to javafx.fxml;
    exports cs211.project.controllers.view.auth;
    opens cs211.project.controllers.view.auth to javafx.fxml;
    exports cs211.project.controllers.view.event;
    opens cs211.project.controllers.view.event to javafx.fxml;
}