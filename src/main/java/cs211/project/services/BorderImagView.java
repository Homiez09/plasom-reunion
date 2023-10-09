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

    public void setSquareClip(double size){
        clip.setArcHeight(size);
        clip.setArcWidth(size);
        imageView.setClip(clip);
    }

    public void setRecClip(double size){
        clip.setWidth(imageView.getFitWidth());
        clip.setHeight(imageView.getFitHeight());
        clip.setArcHeight(size);
        clip.setArcWidth(size);
        imageView.setClip(clip);
    }
    

}
