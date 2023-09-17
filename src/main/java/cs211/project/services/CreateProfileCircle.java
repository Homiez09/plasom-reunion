package cs211.project.services;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

/*
วิธีใช้
1. สร้าง Object ImageView ชื่อ profileImageView
2. ทำการ setImage
3. ใช้คำสั่ง new CreateProfileCircle(profileImageView, radius); << radius คือ รัศมีของวงกลมต้องกำหนดค่าทุกครั้ง

โค้ดตัวอย่าง
profileImageView.setImage(new Image(getClass().getResource("/images/profile/default-avatar/default0.png").toString(), 1280, 1280, false, false));
new CreateProfileCircle(profileImageView, 28);
*/
public class CreateProfileCircle {
    public CreateProfileCircle(ImageView profileImageView, double radius) {
        profileImageView.setClip(getProfileCircle(profileImageView, radius));
    }

    public static Circle getProfileCircle(ImageView profileImageView, double radius) {
        Circle circle = new Circle(radius);
        circle.setCenterX(profileImageView.getFitWidth() / 2);
        circle.setCenterY(profileImageView.getFitHeight() / 2);
        return circle;
    }
}
