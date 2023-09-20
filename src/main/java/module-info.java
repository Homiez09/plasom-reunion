module cs211.project.cs211661project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires bcrypt;
    requires java.sql;


    opens cs211.project.cs211661project to javafx.fxml;
    exports cs211.project.cs211661project;

    exports cs211.project.controllers;
    opens cs211.project.controllers to javafx.fxml;

    exports cs211.project.componentControllers;
    opens cs211.project.componentControllers to javafx.fxml;

    exports cs211.project.controllers.admin;
    opens cs211.project.controllers.admin to javafx.fxml;

    exports cs211.project.models;
    opens cs211.project.models to javafx.base;

    exports cs211.project.models.collections;
    opens cs211.project.models.collections to javafx.base;
    exports cs211.project.componentControllers.teamboxControllers;
    opens cs211.project.componentControllers.teamboxControllers to javafx.fxml;

    exports cs211.project.controllers.team;
    opens cs211.project.controllers.team to javafx.fxml;
    exports cs211.project.componentControllers.sideBarControllers;
    opens cs211.project.componentControllers.sideBarControllers to javafx.fxml;


}