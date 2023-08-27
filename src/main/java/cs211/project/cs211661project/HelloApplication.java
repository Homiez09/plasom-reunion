package cs211.project.cs211661project;

import javafx.application.Application;
import javafx.stage.Stage;
import cs211.project.services.FXRouter;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        configRoute();

        FXRouter.bind(this, stage, "CS211 661 Project");

        FXRouter.goTo("welcome");
    }

    private static void configRoute() {
        String resourcesPath = "cs211/project/views/";
        FXRouter.when("welcome", resourcesPath+ "welcome-view.fxml");
        FXRouter.when("dev-profile", resourcesPath+ "develop-profile-view.fxml");
        FXRouter.when("sign-in", resourcesPath + "sign-in.fxml");
        FXRouter.when("sign-up", resourcesPath + "sign-up.fxml");
        FXRouter.when("setting", resourcesPath+ "setting-view.fxml");
        FXRouter.when("home", resourcesPath+ "home.fxml");
        FXRouter.when("user-profile", resourcesPath+ "user-profile.fxml");
        FXRouter.when("admin-dashboard", resourcesPath+ "admin-dashboard.fxml");
        FXRouter.when("my-event", resourcesPath+ "my-event-view.fxml");


    }

    public static void main(String[] args) {
        launch();
    }
}