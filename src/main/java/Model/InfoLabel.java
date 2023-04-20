package Model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class InfoLabel extends Label{

	public final static String FONT_PATH = "Resources/Font/kenvector_future.ttf"; 
	
	//Constructor of Labels, receives the text, the method can be changed to receive width and height of the label
	public InfoLabel(String text,int size) {
		setPrefWidth(600);
		setPrefHeight(10);
		setPadding(new Insets(40,40,40,40));
		setText(text);
		setWrapText(true);
		setLabelFont(size);
	}
	
	//Sets the font of the label, it can be changed to receive the size of the font
	private void setLabelFont(int size) {
		try {
			setFont(Font.loadFont(new FileInputStream(FONT_PATH),size));
		}catch(FileNotFoundException e) {
			setFont(Font.font("Verdana",size));
		}
	}
}
