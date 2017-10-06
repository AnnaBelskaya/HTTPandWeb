package utils;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageUtils {
    private ImageUtils(){}

    public static ImageView crop(String image) {
        ImageView imageView = new ImageView(new Image(image));
        double newMeasure = (imageView.getImage().getWidth() < imageView.getImage().getHeight())
                ? imageView.getImage().getWidth()
                : imageView.getImage().getHeight();
        double x = (imageView.getImage().getWidth() - newMeasure) / 2;
        double y = (imageView.getImage().getHeight() - newMeasure) / 2;
        Rectangle2D rect = new Rectangle2D(x, y, newMeasure, newMeasure);
        imageView.setViewport(rect);
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.setSmooth(true);
        return imageView;
    }
}
