package Model;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class CarPicker extends VBox{
	private final ImageView buttonImage;
	private final String buttonNotChosen = "file:Resources/CheckButtons/Button_Unchecked.png";
	private final CARS car;
	private boolean isButtonChosen;
	
	//Constructor of the CharacterPicker, receives a character, creates the VBox
	public CarPicker(CARS car) {
		buttonImage = new ImageView(buttonNotChosen);
		ImageView carImage = new ImageView(car.getUrl());
		this.car = car;
		isButtonChosen = false;
		this.setAlignment(Pos.CENTER);
		this.setSpacing(10);
		this.getChildren().add(buttonImage);
		this.getChildren().add(carImage);
	}
	
	//returns character
	public CARS getCar() {
		return car;
	}
	
	//Change the image of the indicator of which character is being chosen
	public void setButtonChosen(boolean isButtonChose) {
		this.isButtonChosen = isButtonChose;
		String buttonChosen = "file:Resources/CheckButtons/Button_Checked.png";
		String imageToSet = this.isButtonChosen ? buttonChosen : buttonNotChosen;
		buttonImage.setImage(new Image(imageToSet));
	}
}
