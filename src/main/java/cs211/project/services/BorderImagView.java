package cs211.project.services;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class BorderImagView {
    private final Rectangle clip;
    private final ImageView imageView;

    public BorderImagView(ImageView imageView) {
        this.clip = new Rectangle(imageView.getFitWidth(), imageView.getFitHeight());
        this.imageView = imageView;
    }

    public void setClip(double size){
        clip.setArcHeight(size);
        clip.setArcWidth(size);
        imageView.setClip(clip);
    }

}
