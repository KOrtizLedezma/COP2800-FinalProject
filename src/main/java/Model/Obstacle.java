package Model;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Obstacle extends Rectangle{
	
	 public Obstacle(double x, double y, double width, double height) {
	        super(x, y, width, height);
    }

    public boolean collidesWith(ImageView imageView) {
        // get the bounds of the obstacle and the image view
        Bounds obstacleBounds = this.getBoundsInParent();
        Bounds imageViewBounds = imageView.getBoundsInParent();

        // check if the bounds intersect
        return obstacleBounds.intersects(imageViewBounds);
    }
}
