package Model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;

public class GameSmallButton extends Button{
	private final String BUTTON_PRESSED = "-fx-background-color: transparent; -fx-background-image: url('file:Resources/Buttons/Button_Play_Pressed.png')";
	private final String BUTTON_FREE = "-fx-background-color: transparent; -fx-background-image: url('file:Resources/Buttons/Button_Play_Free.png')";
	
	//Constructor of the buttons, this method receives the text, height, width and font size
	public GameSmallButton(String text) {
		setText(text);
		setButtonFont();
		setPrefHeight(30);
		setPrefWidth(90);
		setStyle(BUTTON_FREE);
		initializeButton();
	}
	
	//Sets the font of the button, this method receives the size of the font, it can be changed to receive the direction of the font to use
	private void setButtonFont() {
		try {
			String FONT = "Resources/Font/kenvector_future.ttf";
			setFont(Font.loadFont(new FileInputStream(FONT), 14));
		} catch (FileNotFoundException e) {
			setFont(Font.font("Arial",14));
		}
	}
	
	//Initialize button giving the ways to react on clicks and releases
	private void initializeButton() {
		setOnMousePressed(arg0 -> {
			if(arg0.getButton().equals(MouseButton.PRIMARY)) {
				setStyle(BUTTON_PRESSED);
				setLayoutY(getLayoutY()+4);
			}
		});
		setOnMouseReleased(arg0 -> {
			if(arg0.getButton().equals(MouseButton.PRIMARY)) {
				setStyle(BUTTON_FREE);
				setLayoutY(getLayoutY()-4);
			}
		});
		setOnMouseEntered(arg0 -> setEffect(new DropShadow()));
		setOnMouseExited(arg0 -> setEffect(null));
	}
}
