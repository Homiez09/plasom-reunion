module cs211.project.cs211661project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires bcrypt;


    opens cs211.project.cs211661project to javafx.fxml;
    exports cs211.project.cs211661project;

    exports cs211.project.controllers;
    opens cs211.project.controllers to javafx.fxml;

    exports cs211.project.componentControllers;
    opens cs211.project.componentControllers to javafx.fxml;

    exports cs211.project.controllers.admin;
    opens cs211.project.controllers.admin to javafx.fxml;

    exports cs211.project.models;
    opens cs211.project.models to javafx.fxml;
    exports cs211.project.models.collections;
    opens cs211.project.models.collections to javafx.fxml;


}