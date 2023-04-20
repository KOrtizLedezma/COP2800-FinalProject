package Model;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.util.Duration;
import javafx.scene.image.Image;

public class GameSubScene extends SubScene{

	private Boolean isHidden;
	
	//Constructor of all Subscenes, the height and width ids define inside the method
	public GameSubScene() {
		super(new AnchorPane(), 400, 450);
		prefWidth(400);
		prefHeight(450);
		String IMAGE_PATH = "file:Resources/Background/Background_Subscene.png";
		Image bckgImage = new Image(IMAGE_PATH, 400, 450, false, true);
		BackgroundImage bckg = new BackgroundImage(bckgImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		AnchorPane root2 = (AnchorPane) this.getRoot();
		root2.setBackground(new Background(bckg));
		isHidden = true;
		setLayoutX(1024);
		setLayoutY(100);
	}
	
	//Creates the transitions to change between subscenes
	public void moveSubscene() {
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(0.3));
		transition.setNode(this);
		
		if(isHidden) {
			transition.setToX(-640);
			isHidden = false;
		}
		else {
			transition.setToX(640);
			isHidden = true;
		}
		transition.play();
	}
	
	//Returns the AnchorPane
	public AnchorPane getPane() {
		return (AnchorPane) this.getRoot();
	}

}
