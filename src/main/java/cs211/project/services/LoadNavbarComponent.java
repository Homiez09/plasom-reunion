package cs211.project.services;

import cs211.project.models.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/*
วิธีใช้
1 กำหมด fxid ให้ ตัว Anchorpane ในหน้าหลักเช่น AnchorPane navbarAnchorPane;
2 ทำการเรียกใช้ new LoadNavbarComponent(user, navbarAnchorPane);
หมายเหตุ: กรณีหน้าที่มีการรับ FXRouter.getData() มาให้ทำการส่ง param (User) user แบบอย่างข้อ 2
*/
public class LoadNavbarComponent {
    public LoadNavbarComponent(User user, AnchorPane navbarAnchorPane) {
        if ((user == null)) {
            LoadNavbarForGuest(navbarAnchorPane);
        } else {
            LoadNavbarForUser(navbarAnchorPane);
        }
    }
    public LoadNavbarComponent(AnchorPane navbarAnchorPane) {
        this(null, navbarAnchorPane);
    }

    private void LoadNavbarForUser(AnchorPane navbarAnchorPane) {
        FXMLLoader navbarComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/navbar.fxml"));
        try {
            AnchorPane navbarComponent = navbarComponentLoader.load();
            navbarAnchorPane.getChildren().add(navbarComponent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void LoadNavbarForGuest(AnchorPane navbarAnchorPane) {
        FXMLLoader navbarComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/components/navbar-guest.fxml"));
        try {
            AnchorPane navbarComponent = navbarComponentLoader.load();
            navbarAnchorPane.getChildren().add(navbarComponent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
