package cs211.project.services.team;

import cs211.project.controllers.component.UserCardProfileController;
import cs211.project.models.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LoadUserCardProfileComponent {
    public LoadUserCardProfileComponent(AnchorPane userCardProfileAnchorPane, User user) {
        try {
            FXMLLoader userCardProfileComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/user-card-profile.fxml"));
            AnchorPane userCardProfileComponent = userCardProfileComponentLoader.load();

            UserCardProfileController userCardProfileController = userCardProfileComponentLoader.getController();
            userCardProfileController.setUser(user);

            userCardProfileAnchorPane.getChildren().add(userCardProfileComponent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
