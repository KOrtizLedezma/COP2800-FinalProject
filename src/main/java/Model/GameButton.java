package Model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;

public class GameButton extends Button{
	private final String BUTTON_FREE = "-fx-background-color: transparent; -fx-background-image: url('file:Resources/Buttons/yellow_button_released.png')";
	
	//Constructor of the buttons, this method receives the text, height, width and font size
	public GameButton(String text,int height,int width, int fontSize) {
		setText(text);
		setButtonFont(fontSize);
		setPrefHeight(height);
		setPrefWidth(width);
		setStyle(BUTTON_FREE);
		initializeButton(height);
	}
	
	//Sets the font of the button, this method receives the size of the font, it can be changed to receive the direction of the font to use
	private void setButtonFont(int fontSize) {
		try {
			String FONT = "Resources/Font/kenvector_future.ttf";
			setFont(Font.loadFont(new FileInputStream(FONT), fontSize));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			setFont(Font.font("Arial",fontSize));
		}
	}
	
	//Creates the style for when the button is being pressed
	private void setButtonPressed(int height) {
		String BUTTON_PRESSED = "-fx-background-color: transparent; -fx-background-image: url('file:Resources/Buttons/yellow_button_pressed.png')";
		setStyle(BUTTON_PRESSED);
		setPrefHeight(height);
		setLayoutX(getLayoutX()+4);
	}
	
	//Creates the style for when the button is free
	private void setButtonFree(int height) {
		setStyle(BUTTON_FREE);
		setPrefHeight(height);
		setLayoutX(getLayoutX()-4);
	}
	
	//Initialize button giving the ways to react on clicks and releases
	private void initializeButton(int height) {
		setOnMousePressed(arg0 -> {
			if(arg0.getButton().equals(MouseButton.PRIMARY)) {
				setButtonPressed(height);
			}
		});
		setOnMouseReleased(arg0 -> {
			if(arg0.getButton().equals(MouseButton.PRIMARY)) {
				setButtonFree(height);
			}
		});
		setOnMouseEntered(arg0 -> setEffect(new DropShadow()));
		setOnMouseExited(arg0 -> setEffect(null));
	}
}
