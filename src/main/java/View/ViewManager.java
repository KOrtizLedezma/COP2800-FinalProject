package View;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Model.*;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import static java.lang.Integer.parseInt;

public class ViewManager {
	private final AnchorPane mainPane;
	private final Stage mainStage;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private final static int BUTTON_STARTS_X = 100;
	private final static int BUTTON_STARTS_Y = 80;
	private GameSubScene infoSubscene;
	private GameSubScene singlePlayerSubscene;
	private GameSubScene multiPlayerSubscene;
	private GameSubScene scoresSubscene;
	private GameSubScene finalProjectSubscene;
	private GameSubScene sceneToHide;
	private CARS carSP;
	private CARS carA;
	private CARS carB;
	List<GameButton> menuButtons;
	List<CarPicker> carListPlayerSinglePlayer;
	List<CarPicker> carListPlayerA;
	List<CarPicker> carListPlayerB;
	public MediaPlayer mediaPlayer;

	//Initialize all the components 
	public ViewManager() {
		menuButtons = new ArrayList<>();
		mainStage = new Stage();
		mainPane = new AnchorPane();
		Scene mainScene = new Scene(mainPane, WIDTH, HEIGHT);
		mainStage.setScene(mainScene);
		
		initializeMinimizeCloseSoundButtons();
		createSubscenes();
		createButtons();
		createBackground();
		createLogo();
		createMusicMainMenu();
		
	}
	
	//Initialize all SubScene
	private void createSubscenes(){
		createSingleplayerSubScene();
		createMultiplayerSubScene();
		createInfoSubScene();
		createScoresSubScene();
		createFinalProjectSubScene();
	}

	//Initialize the final project subscene
	private void createFinalProjectSubScene(){
		finalProjectSubscene = new GameSubScene();
		mainPane.getChildren().add(finalProjectSubscene);
		finalProjectSubscene.getPane().getChildren().add(createGenericLabel("	Final Project - COP2800",-10));
		finalProjectSubscene.getPane().getChildren().add(createGenericLabel("Kenet Ortiz", 270));
		finalProjectSubscene.getPane().getChildren().add(createGenericLabel("Version 1.0.0", 300));
	}

	//Initialize the score subscene
	private void createScoresSubScene(){
		scoresSubscene = new GameSubScene();
		mainPane.getChildren().add(scoresSubscene);
		createScoresTable();
	}

	//Creates the multiplayer subscene adding all the labels and buttons
	private void createMultiplayerSubScene() {
		multiPlayerSubscene = new GameSubScene();
		mainPane.getChildren().add(multiPlayerSubscene);
		multiPlayerSubscene.getPane().getChildren().add(createGenericLabel("Player Left", -10));
		multiPlayerSubscene.getPane().getChildren().add(createCarChooserPlayerA());
		multiPlayerSubscene.getPane().getChildren().add(createGenericLabel("Player Right", 140));
		multiPlayerSubscene.getPane().getChildren().add(createCarChooserPlayerB());
		multiPlayerSubscene.getPane().getChildren().add(createStartButtonMultiplayer());
		
	}
	
	//creates the single player subscene, including the HBox and labels that appears
	private void createSingleplayerSubScene() {
		singlePlayerSubscene = new GameSubScene();
		mainPane.getChildren().add(singlePlayerSubscene);
		singlePlayerSubscene.getPane().getChildren().add(createGenericLabel("Select your car", 0));
		singlePlayerSubscene.getPane().getChildren().add(createCarChooserPlayerSinglePlayer());
		singlePlayerSubscene.getPane().getChildren().add(createStartButtonSingleplayer());
	}
	
	//Creates the Info subscene, including the image and the labels
	private void createInfoSubScene() {
		infoSubscene = new GameSubScene();
		mainPane.getChildren().add(infoSubscene);
		infoSubscene.getPane().getChildren().add(createGenericLabel("Up Arrow  or W To Accelerate", 10));
		infoSubscene.getPane().getChildren().add(createGenericLabel("Down Arrow  or S To Brake", 30));
		infoSubscene.getPane().getChildren().add(createGenericLabel("Left Arrow  or A To go left", 50));
		infoSubscene.getPane().getChildren().add(createGenericLabel("Right Arrow  or D To go right", 70));
		ImageView info = new ImageView("file:Resources/Info/Info.png");
		info.setLayoutX(70);
		info.setLayoutY(150);
		infoSubscene.getPane().getChildren().add(info);
	}

	//Creates an HBox that have all the possibilities to choose cars for singlePlayer
	private HBox createCarChooserPlayerSinglePlayer() {
		HBox boxPlayerSinglePlayer = new HBox();
		boxPlayerSinglePlayer.setSpacing(35);
		carListPlayerSinglePlayer = new ArrayList<>();
		for(CARS current : CARS.values()) {
			CarPicker carToPickSinglePlayer = new CarPicker(current);
			carListPlayerSinglePlayer.add(carToPickSinglePlayer);
			boxPlayerSinglePlayer.getChildren().add(carToPickSinglePlayer);
			carToPickSinglePlayer.setOnMouseClicked(arg0 -> {
				for(CarPicker current1 : carListPlayerSinglePlayer) {
					current1.setButtonChosen(false);
				}
				carToPickSinglePlayer.setButtonChosen(true);
				carSP = carToPickSinglePlayer.getCar();
			});
		}
		boxPlayerSinglePlayer.setLayoutX(40);
		boxPlayerSinglePlayer.setLayoutY(80);
		return boxPlayerSinglePlayer;
	}

	//Creates an HBox that have all the possibilities to choose cars for multiplayer
	private HBox createCarChooserPlayerA() {
		HBox boxPlayerA = new HBox();
		boxPlayerA.setSpacing(35);
		carListPlayerA = new ArrayList<>();
		for(CARS current : CARS.values()) {
			CarPicker carToPickPlayerA = new CarPicker(current);
			carListPlayerA.add(carToPickPlayerA);
			boxPlayerA.getChildren().add(carToPickPlayerA);
			carToPickPlayerA.setOnMouseClicked(arg0 -> {
				for(CarPicker current1 : carListPlayerA) {
					current1.setButtonChosen(false);
				}
				carToPickPlayerA.setButtonChosen(true);
				carA = carToPickPlayerA.getCar();
			});
		}
		boxPlayerA.setLayoutX(40);
		boxPlayerA.setLayoutY(50);
		return boxPlayerA;
	}
	
	//Creates an HBox that have all the possibilities to choose cars for multiplayer
	private HBox createCarChooserPlayerB() {
		HBox boxPlayerB = new HBox();
		boxPlayerB.setSpacing(35);
		carListPlayerB = new ArrayList<>();
		for(CARS current : CARS.values()) {
			CarPicker carToPickPlayerB = new CarPicker(current);
			carListPlayerB.add(carToPickPlayerB);
			boxPlayerB.getChildren().add(carToPickPlayerB);
			carToPickPlayerB.setOnMouseClicked(arg0 -> {
				for(CarPicker current1 : carListPlayerB) {
					current1.setButtonChosen(false);
				}
				carToPickPlayerB.setButtonChosen(true);
				carB = carToPickPlayerB.getCar();
			});
		}
		boxPlayerB.setLayoutX(40);
		boxPlayerB.setLayoutY(200);
		return boxPlayerB;
	}	

	//Method to show only the subscene selected by the player
	private void showSubscene(GameSubScene scene) {
		if(sceneToHide != null) {
			sceneToHide.moveSubscene();
		}
		scene.moveSubscene();
		sceneToHide = scene;
	}
	
	//return the main page
	public Stage getMainStage() {
		return mainStage;
	}
	
	//add buttons to the main page
	public void addMenuButtons(GameButton button) {
		button.setLayoutY(BUTTON_STARTS_Y + menuButtons.size()*100);
		button.setLayoutX(BUTTON_STARTS_X);
		menuButtons.add(button);
		mainPane.getChildren().add(button);
	}
	
	//Initialize all methods to create buttons
	private void createButtons(){
		createSinglePlayerButton();
		createMultiPlayerButton();
		createInfoButton();
		createScoresButton();
		createExitButton();
	}
	
	//Creates exit button and deals with clicks
	private void createExitButton() {
		GameButton button = new GameButton("Quit",49,190,23);
		addMenuButtons(button);
		button.setOnAction(arg0 -> mainStage.close());
	}
	
	//Creates exit button and deals with clicks
	private void createScoresButton() {
		GameButton button = new GameButton("Scores",49,190,23);
		addMenuButtons(button);
		button.setOnAction(arg0 ->showSubscene(scoresSubscene)
		);
	}
	
	//Creates create player button and deals with clicks
	private void createSinglePlayerButton() {
		GameButton button = new GameButton("Play",49,190,23);
		addMenuButtons(button);
		button.setOnAction(arg0 -> showSubscene(singlePlayerSubscene));
	}
	
	//Creates create player button and deals with clicks
	private void createMultiPlayerButton() {
		GameButton button = new GameButton("MULTIPLAYER",49,190,18);
		addMenuButtons(button);
		button.setOnAction(arg0 -> showSubscene(multiPlayerSubscene));
	}
	
	//Creates info button and deals with clicks
	private void createInfoButton() {
		GameButton button = new GameButton("Information",49,190,18);
		addMenuButtons(button);
		button.setOnAction(arg0 -> showSubscene(infoSubscene));
	}
	
	//Creates generic labels getting text and positions on y
	private InfoLabel createGenericLabel(String text, int y) {
		InfoLabel label = new InfoLabel(text,16);
		label.setLayoutX(0);
		label.setLayoutY(y);
		return label;
	}
	
	//Creates the background of the main page
	private void createBackground() {
		Image bckgImage = new Image("file:Resources/Background/Background_Main_Menu.png", 800, 600, false, true);
		BackgroundImage bckg = new BackgroundImage(bckgImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, null);
		mainPane.setBackground(new Background(bckg));
	}
	
	//Creates the logo that appears on the main page and deals with clicks
	private void createLogo() {
		ImageView logo = new ImageView("file:Resources/Logo/Logo.png");
		logo.setLayoutX(500);
		logo.setLayoutY(30);
		logo.setOnMouseClicked(arg0 -> showSubscene(finalProjectSubscene));
		logo.setOnMouseEntered(arg0 -> logo.setEffect(new DropShadow()));
		logo.setOnMouseExited(arg0 -> logo.setEffect(null));
		mainPane.getChildren().add(logo);
	}
	
	//Creates a button that it's going to start the multiplayer mode, it will also check if both players has selected their cars
	private Button createStartButtonMultiplayer() {
		GameSmallButton button = new GameSmallButton("Play");
		button.setLayoutX(160);
		button.setLayoutY(400);
		button.setOnAction(arg0 -> {
			if(carA != null && carB != null) {
				GameViewManager gameManager = new GameViewManager();
				gameManager.createNewGameMultiplayer(mainStage, carA, carB);
				mediaPlayer.stop();
			}
		});
		return button;
	}
	
	//Creates a button that it's going to start the singleplayer mode, it will also check if the player has selected its car
	private Button createStartButtonSingleplayer() {
		GameSmallButton button = new GameSmallButton("Play");
		button.setLayoutX(160);
		button.setLayoutY(400);
		
		button.setOnAction(arg0 -> {
			if(carSP != null ) {
				GameViewManager gameManager = new GameViewManager();
				gameManager.createNewGameSingleplayer(mainStage, carSP);
				mediaPlayer.stop();
			}
		});
		return button;
	}

	//Creates the minimize button in the top right corner of the main menu
	private Button createMinimizeButton() {
		Button button = new Button("");
		button.setLayoutX(720);
		button.setLayoutY(10);
		button.setPrefHeight(30);
		button.setPrefWidth(30);
		button.setStyle("-fx-background-color: transparent; -fx-background-image: url('file:Resources/Buttons/Minimize_Button.png')");
		button.setOnAction(arg0 -> mainStage.setIconified(true));
		button.setOnMouseEntered(arg0 -> button.setEffect(new DropShadow()));
		button.setOnMouseExited(arg0 -> button.setEffect(null));
		return button;
	}

	//Creates the close button in the top right corner of the main menu
	private Button createCloseButton() {
		Button button = new Button("");
		button.setLayoutX(760);
		button.setLayoutY(10);
		button.setPrefHeight(30);
		button.setPrefWidth(30);
		button.setStyle("-fx-background-color: transparent; -fx-background-image: url('file:Resources/Buttons/Close_Button.png')");
		button.setOnAction(arg0 -> mainStage.close());
		button.setOnMouseEntered(arg0 -> button.setEffect(new DropShadow()));
		button.setOnMouseExited(arg0 -> button.setEffect(null));
		return button;
	}

	private Button createSoundButton() {
		Button button = new Button("");
		button.prefHeight(30);
		button.prefWidth(30);
		button.setLayoutX(680);
		button.setLayoutY(10);
		button.setStyle("-fx-background-color: transparent; -fx-background-image: url('file:Resources/Buttons/Sound_Button.png')");

		button.setOnMousePressed(arg0 -> {
			if(arg0.getButton().equals(MouseButton.PRIMARY)) {
				mediaPlayer.setMute(!mediaPlayer.isMute());
			}
		});
		button.setOnMouseEntered(arg0 -> button.setEffect(new DropShadow()));
		button.setOnMouseExited(arg0 -> button.setEffect(null));
		return button;
	}

	//Initialize buttons close and minimize that appears on the right corner of the screen
	private void initializeMinimizeCloseSoundButtons() {
		mainPane.getChildren().add(createCloseButton());
		mainPane.getChildren().add(createMinimizeButton());
		mainPane.getChildren().add(createSoundButton());
	}

	//Read all the scores that appears on the file and returns a List of Data Type
	private List<Data> readScores(){
		ArrayList<String> scores = new ArrayList<>();
		ArrayList<Data> scoresData = new ArrayList<>();
		try {
			File file = new File( "scores.csv");
			FileReader reader = new FileReader(file);
			Scanner fileScanner = new Scanner(reader);
			while(fileScanner.hasNextLine()){
				String current = fileScanner.nextLine();
				scores.add(current);
			}

		}
		catch (Exception e) {
			System.out.println("File not found");
			e.getStackTrace();
		}

		for(String current : scores){
			String[] parts = current.split(",");
			String name = parts[0];
			String scoreString = parts[1];
			int score = parseInt(scoreString);
			Data data = new Data(name, score);
			scoresData.add(data);
		}
		return scoresData;
	}

	//Sort the scores that we geed on the readScores() and returns a sorted list
	private List<Data> sortList(List<Data> list){
		List<Data> sortedList = new ArrayList<>();
		Data temp;
		for(int i=0; i < list.size(); i++){
			for(int j=1; j < (list.size()-i); j++){
				if(list.get(j-1).getScore() < list.get(j).getScore()){
					temp = list.get(j-1);
					list.set(j-1, list.get(j));
					list.set(j,temp);
				}

			}
		}
		for(Data current : list){
			Data d = new Data(current.getName(), current.getScore());
			sortedList.add(d);
		}
		return sortedList;
	}

	//Creates the score table that appears on the score subscenes
	public void createScoresTable(){
		List<Data> scores = sortList(readScores());

		InfoLabel title = new InfoLabel("Top 10",20);
		title.setLayoutX(125);
		title.setLayoutY(10);
		scoresSubscene.getPane().getChildren().add(title);

		for(int i = 0; i < 10; i++){
			String scoreString = String.valueOf(scores.get(i).getScore());
			InfoLabel labelName = new InfoLabel(scores.get(i).getName(),20);
			labelName.setLayoutY(50 + i*30);
			InfoLabel labelPoints = new InfoLabel("_____",20);
			labelPoints.setLayoutY(50 + i*30);
			labelPoints.setLayoutX(130);
			InfoLabel labelScore = new InfoLabel(scoreString,20);
			labelScore.setLayoutY(50 + i*30);
			labelScore.setLayoutX(250);
			scoresSubscene.getPane().getChildren().add(labelName);
			scoresSubscene.getPane().getChildren().add(labelPoints);
			scoresSubscene.getPane().getChildren().add(labelScore);
		}
	}

	//Creates the music of the main menu
	public void createMusicMainMenu(){
		String musicFilePath = "Resources/Music/Main_Menu_Theme.mp3";
		Media media = new Media(new File(musicFilePath).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.play();
	}
}
