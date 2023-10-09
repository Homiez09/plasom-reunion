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
        stage.setResizable(false);

        FXRouter.goTo("welcome");
    }

    private static void configRoute() {
        String resourcesPath = "cs211/project/views/";
        FXRouter.when("welcome", resourcesPath+ "welcome-new.fxml");
        FXRouter.when("dev-profile", resourcesPath+ "develop-profile.fxml");
        FXRouter.when("sign-in", resourcesPath + "sign-in.fxml");
        FXRouter.when("sign-up", resourcesPath + "sign-up.fxml");
        FXRouter.when("setting", resourcesPath+ "setting.fxml");
        FXRouter.when("home", resourcesPath+ "home.fxml");
        FXRouter.when("user-profile", resourcesPath+ "user-profile.fxml");
        FXRouter.when("admin-dashboard", resourcesPath+ "admin-dashboard.fxml");
        FXRouter.when("my-event", resourcesPath+ "my-event.fxml");
        FXRouter.when("event", resourcesPath+ "event-view.fxml");
        FXRouter.when("create-event", resourcesPath+ "create-new-event.fxml");
        FXRouter.when("select-team", resourcesPath+ "select-team.fxml");
        FXRouter.when("all-events",resourcesPath+"all-events.fxml");
        FXRouter.when("team-chat",resourcesPath + "team/team-chat.fxml");
        FXRouter.when("team-activity", resourcesPath+"team/team-activity.fxml");
        FXRouter.when("create-team",resourcesPath + "components/" + "create-team.fxml");
        FXRouter.when("event-list",resourcesPath+"event-list-view.fxml");
        FXRouter.when("team-manage",resourcesPath + "team/" + "team-manage.fxml");
        FXRouter.when("edit-event-activity",resourcesPath+"edit-event-activity.fxml");
        FXRouter.when("join-team",resourcesPath+"join-team.fxml");



    }

    public static void main(String[] args) {
        launch();
    }
}