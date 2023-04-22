package View;

import java.io.*;

import java.util.Random;

import Model.CARS;
import Model.GameSmallButton;
import Model.InfoLabel;
import Model.Obstacle;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GameViewManager {
	private AnchorPane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 800;
	private Stage menuStage;
	private ImageView car1;
	private ImageView car2;
	private final Physics car1Physics = new Physics();
	private final Physics car2Physics = new Physics();
	private boolean isLeftKeyPressed;
	private boolean isRightKeyPressed;
	private boolean isUpKeyPressed;
	private boolean isDownKeyPressed;
	private boolean isAKeyPressed;
	private boolean isDKeyPressed;
	private boolean isWKeyPressed;
	private boolean isSKeyPressed;
	public static boolean playerLeftLose = false;
	public static boolean playerRightLose = false;
	Obstacle obstacleLeft;
	Obstacle obstacleRight;
	Obstacle obstacleMiddle;
	Obstacle obstacleTop;
	Obstacle obstacleBottom;
	private AnimationTimer gameTimer;
	Obstacle[] carsObstaclesSinglePlayer = new Obstacle[20];
	Obstacle[] carsObstaclesMultiPlayerLeftSide = new Obstacle[20];
	Obstacle[] carsObstaclesMultiPlayerRightSide = new Obstacle[20];
	InfoLabel score;
	MediaPlayer mediaPlayer;
    public int elapsedSeconds = 0;

	public GameViewManager() {
		initializeStage();
		createKeyListener();
	}

	//Method use to listen which key is pressed and released, this method changes the value of its respective boolean variable for each key
	private void createKeyListener() {
		gameScene.setOnKeyPressed(arg0 -> {
			if(arg0.getCode()==KeyCode.LEFT) {
				isLeftKeyPressed = true;
			} else if(arg0.getCode()==KeyCode.RIGHT) {
				isRightKeyPressed = true;
			} else if(arg0.getCode()==KeyCode.UP) {
				isUpKeyPressed = true;
			} else if(arg0.getCode()==KeyCode.DOWN) {
				isDownKeyPressed = true;
			} else if(arg0.getCode()==KeyCode.A) {
				isAKeyPressed = true;
			} else if(arg0.getCode()==KeyCode.D) {
				isDKeyPressed = true;
			} else if(arg0.getCode()==KeyCode.W) {
				isWKeyPressed = true;
			} else if(arg0.getCode()==KeyCode.S) {
				isSKeyPressed = true;
			}
		});
		gameScene.setOnKeyReleased(arg0 -> {
			if(arg0.getCode()==KeyCode.LEFT) {
				isLeftKeyPressed = false;
			} else if(arg0.getCode()==KeyCode.RIGHT) {
				isRightKeyPressed = false;
			} else if(arg0.getCode()==KeyCode.UP) {
				isUpKeyPressed = false;
			} else if(arg0.getCode()==KeyCode.DOWN) {
				isDownKeyPressed = false;
			} else if(arg0.getCode()==KeyCode.A) {
				isAKeyPressed = false;
			} else if(arg0.getCode()==KeyCode.D) {
				isDKeyPressed = false;
			} else if(arg0.getCode()==KeyCode.W) {
				isWKeyPressed = false;
			} else if(arg0.getCode()==KeyCode.S) {
				isSKeyPressed = false;
			}
		});
	}

	//Method use to initialize the stage and start the music of the game
	private void initializeStage() {
		gamePane = new AnchorPane();
		gameScene = new Scene(gamePane, WIDTH, HEIGHT);
		gameStage = new Stage();
		gameStage.setResizable(false);
		gameStage.setTitle("MDC FINAL PROJECT");
		gameStage.initStyle(StageStyle.UNDECORATED);
		gameStage.setScene(gameScene);
		createMusicGame();
	}

	//Method use to initialize the background and starts the loop for the multiplayer game
	public void createNewGameMultiplayer(Stage menuStage, CARS carA, CARS carB) {
		this.menuStage = menuStage;
		this.menuStage.hide();
		createCarsMultiplayer(carA, carB);
		createGameLoopMultiplayer();
		createBackgroundMultiPlayer();
		gameStage.show();
	}

	//Method use to initialize the background and starts the loop for the single player game
	public void createNewGameSinglePlayer(Stage menuStage, CARS carA) {
		this.menuStage = menuStage;
		this.menuStage.hide();
		createCarsSinglePlayer(carA);
		createGameLoopSinglePlayer();
		createBackgroundSinglePlayer();
		gameStage.show();
	}

	//Method use to create the cars that the players will use on the multiplayer mode
	//The method also initialize the limits of the map and starts the initialize the obstacles on the game
	private void createCarsMultiplayer(CARS carA, CARS carB) {
		car1 = new ImageView(carA.getUrl());
		car2 = new ImageView(carB.getUrl());
		gamePane.getChildren().add(car1);
		gamePane.getChildren().add(car2);
		initializeLimitsMultiPlayer();
		initializeCarsObstaclesMultiPlayerLeftSide();
		initializeCarsObstaclesMultiPlayerRightSide();
	}

	//Method use to create the car that the player will use on the single player mode
	//The method also initialize the limits of the map and starts the initialize the obstacles on the game
	private void createCarsSinglePlayer(CARS carA) {
		car1 = new ImageView(carA.getUrl());
		gamePane.getChildren().add(car1);
		initializeLimitsSinglePlayer();
		initializeCarsObstaclesSinglePlayer();
	}

	//Method in charge of update each position of all the objects on the screen, this is updated each frame
	//The method also checks for collisions with cars or with limits
	private void createGameLoopMultiplayer() {
		gameTimer = new AnimationTimer() {

			@Override
			public void handle(long arg0) {
				updatePlayer(isLeftKeyPressed, isRightKeyPressed, isUpKeyPressed,isDownKeyPressed, car1Physics);
				updatePlayer(isAKeyPressed, isDKeyPressed, isWKeyPressed, isSKeyPressed, car2Physics);
				updateObstacles(carsObstaclesMultiPlayerLeftSide);
				updateObstacles(carsObstaclesMultiPlayerRightSide);
				checkCollisionLimitsMultiPlayerA();
				checkCollisionLimitsMultiPlayerB();
				checkCollisionCarsMultiPlayerCarRight(carsObstaclesMultiPlayerRightSide);
				checkCollisionCarsMultiPlayerCarLeft(carsObstaclesMultiPlayerLeftSide);
				showPlayerMultiPlayerA(car1, car1Physics);
				showPlayerMultiPlayerB(car2, car2Physics);
			}
		};

		gameTimer.start();
	}

	//Method in charge of update each position of all the objects on the screen, this is updated each frame
	//The method also checks for collisions with cars or with limits
	private void createGameLoopSinglePlayer() {
		gameTimer = new AnimationTimer() {

			@Override
			public void handle(long arg0) {
				updatePlayer(isLeftKeyPressed, isRightKeyPressed, isUpKeyPressed, isDownKeyPressed, car1Physics);
				updateObstacles(carsObstaclesSinglePlayer);
				checkCollisionLimitsSinglePlayer();
				checkCollisionCarsSinglePlayer(carsObstaclesSinglePlayer);
				showPlayerSinglePlayer(car1, car1Physics);
				elapsedSeconds++;
				score.setText("Score: " + elapsedSeconds/100);
			}
		};

		gameTimer.start();
	}

	//Method to create the limits for the single player mode
	public void initializeLimitsSinglePlayer() {
		obstacleTop = new Obstacle(0,-40,1000,50);
		obstacleTop.setFill(Color.BLACK);
		gamePane.getChildren().add(obstacleTop);

		obstacleBottom = new Obstacle(0,790,1000,50);
		obstacleBottom.setFill(Color.BLACK);
		gamePane.getChildren().add(obstacleBottom);

		obstacleLeft = new Obstacle(0, 0, 290, 800);
        obstacleLeft.setFill(Color.BLACK);
		gamePane.getChildren().add(obstacleLeft);

		obstacleRight = new Obstacle(715, 0, 350, 800);
        obstacleRight.setFill(Color.BLACK);
        gamePane.getChildren().add(obstacleRight);
        
        score = new InfoLabel("Score: " + elapsedSeconds/100, 16);
        score.setLayoutX(720);
        score.setLayoutY(10);
        score.setTextFill(Color.WHITE);
        gamePane.getChildren().add(score);
	}

	//Method to create the limits for the multiplayer mode
	public void initializeLimitsMultiPlayer() {
		obstacleTop = new Obstacle(0,-40,1000,50);
		obstacleTop.setFill(Color.BLACK);
		gamePane.getChildren().add(obstacleTop);

		obstacleBottom = new Obstacle(0,790,1000,50);
		obstacleBottom.setFill(Color.BLACK);
		gamePane.getChildren().add(obstacleBottom);

		obstacleLeft = new Obstacle(0, 0, 50, 800);
        obstacleLeft.setFill(Color.BLACK);
        gamePane.getChildren().add(obstacleLeft);
        
        obstacleRight = new Obstacle(950, 0, 50, 800);
        obstacleRight.setFill(Color.BLACK);
        gamePane.getChildren().add(obstacleRight);
        
        obstacleMiddle = new Obstacle(475, 0, 50, 800);
        obstacleMiddle.setFill(Color.BLACK);
        gamePane.getChildren().add(obstacleMiddle);
	}

	//Method to show the player on the single player mode
	protected void showPlayerSinglePlayer(ImageView player, Physics playerPhysics) {
		player.setLayoutX(playerPhysics.positionXSinglePlayer);
		player.setLayoutY(playerPhysics.positionYSinglePlayer);
	}

	//Method to show the player A on the multiplayer mode
	protected void showPlayerMultiPlayerA(ImageView player, Physics playerPhysics) {
		player.setLayoutX(playerPhysics.positionXMultiplayerPlayerA);
		player.setLayoutY(playerPhysics.positionYMultiplayerPlayerA);
	}

	//Method to show the player B on the multiplayer mode
	protected void showPlayerMultiPlayerB(ImageView player, Physics playerPhysics) {
		player.setLayoutX(playerPhysics.positionXMultiplayerPlayerB);
		player.setLayoutY(playerPhysics.positionYMultiplayerPlayerB);
	}

	//Method to deal with the movement when each key is pressed, it works with the class Physics
	private void updatePlayer(boolean left, boolean right, boolean up, boolean down, Physics physics) {
		if(left && !right) {
			physics.move(-1);
		}
		if(!left && right) {
			physics.move(1);
		}
		if(left == right) {
			physics.move(0);
		}
		if(up) {
			physics.moveForward(-1);
		}
		if(down) {
			physics.moveForward(1);
		}
		physics.update();
	}

	//Method to move the obstacle on each frame, all the obstacles moves on the same direction on the same speed
	private void updateObstacles(Obstacle[] obstacles) {
		for(Obstacle current : obstacles) {
			current.setLayoutY(current.getLayoutY()+2);
		}
	}

	//Method to check the collisions with the limits on multiplayer mode, and move the player B to the correct position
	private void checkCollisionLimitsMultiPlayerB() {
		if(car2Physics.collideRectangle(obstacleLeft, car2)) {
			car2Physics.positionXMultiplayerPlayerB = 51;
		}

		if(car2Physics.collideRectangle(obstacleMiddle, car2)) {
			car2Physics.positionXMultiplayerPlayerB = 444;
		}

		if(car2Physics.collideRectangle(obstacleTop, car2)) {
			car2Physics.positionYMultiplayerPlayerB = 11;
		}

		if(car2Physics.collideRectangle(obstacleBottom, car2)) {
			car2Physics.positionYMultiplayerPlayerB = 729;
		}

	}

	//Method to check the collisions with the limits on multiplayer mode, and move the player A to the correct position
	private void checkCollisionLimitsMultiPlayerA() {
		if(car1Physics.collideRectangle(obstacleRight, car1)) {
			car1Physics.positionXMultiplayerPlayerA = 919;
		}

		if(car1Physics.collideRectangle(obstacleMiddle, car1)) {
			car1Physics.positionXMultiplayerPlayerA = 526;
		}

		if(car1Physics.collideRectangle(obstacleTop, car1)) {
			car1Physics.positionYMultiplayerPlayerA = 11;
		}

		if(car1Physics.collideRectangle(obstacleBottom, car1)) {
			car1Physics.positionYMultiplayerPlayerA = 729;
		}
	}

	//Method to check the collisions with the limits on single player mode, and move the player to the correct position
	private void checkCollisionLimitsSinglePlayer() {

		if(car1Physics.collideRectangle(obstacleLeft, car1)) {
			car1Physics.positionXSinglePlayer = 291;
		}

		if(car1Physics.collideRectangle(obstacleRight, car1)) {
			car1Physics.positionXSinglePlayer = 684;
		}

		if(car1Physics.collideRectangle(obstacleTop, car1)) {
			car1Physics.positionYSinglePlayer = 11;
		}

		if(car1Physics.collideRectangle(obstacleBottom, car1)) {
			car1Physics.positionYSinglePlayer = 729;
		}
	}

	//Method to check collisions with the obstacles and checks when the obstacles are on its final position to restart them in a new position
	private void checkCollisionCarsSinglePlayer(Obstacle[] cars) {
		for(Obstacle current : cars) {
			if(car1Physics.collideRectangle(current, car1)) {
				System.out.println("Collide");
				gameTimer.stop();
				mediaPlayer.stop();
				switchToScoreStage(gameStage);
			}
			if(current.getBoundsInParent().intersects(obstacleBottom.getBoundsInParent())) {
				restartCar(current);
			}
		}
		checkCollisionBetweenObstacles(carsObstaclesSinglePlayer);

	}

	//Method to check collisions with the obstacles and checks when the obstacles are on its final position to restart them in a new position
	private void checkCollisionCarsMultiPlayerCarRight(Obstacle[] cars) {
		for(Obstacle current : cars) {
			if(car1Physics.collideRectangle(current, car1)) {
				System.out.println("Collide");
				gameTimer.stop();
				playerRightLose = true;
				mediaPlayer.stop();
				showWinner(gameStage);
			}
			if(current.getBoundsInParent().intersects(obstacleBottom.getBoundsInParent())) {
				restartCar(current);
			}
		}

		checkCollisionBetweenObstacles(carsObstaclesMultiPlayerRightSide);
	}

	//Method to check collisions with the obstacles and checks when the obstacles are on its final position to restart them in a new position
	private void checkCollisionCarsMultiPlayerCarLeft(Obstacle[] cars) {
		for(Obstacle current : cars) {
			if(car1Physics.collideRectangle(current, car2)) {
				System.out.println("Collide");
				gameTimer.stop();
				playerLeftLose = true;
				mediaPlayer.stop();
				showWinner(gameStage);
			}
			if(current.getBoundsInParent().intersects(obstacleBottom.getBoundsInParent())) {
				restartCar(current);
			}
		}

		checkCollisionBetweenObstacles(carsObstaclesMultiPlayerLeftSide);
	}

	//Method to initialize the obstacles in the single player mode
	private void initializeCarsObstaclesSinglePlayer() {

		Random random = new Random();

		for (int i = 0; i < 20; i++) {
			int y = (random.nextInt(11) + 5) * -100;

			String url = generateRandomColor();
			int position = generateRandomPositionXSinglePlayer();
            Obstacle rectangle = new Obstacle(position, y , 30, 60);
            
            Image image = new Image(url);
            
            ImagePattern pattern = new ImagePattern(image);
            rectangle.setFill(pattern);
            
            carsObstaclesSinglePlayer[i] = rectangle;
            
            gamePane.getChildren().add(rectangle);
        }
	}

	//Method to initialize the obstacles on the left side on the multiplayer mode
	private void initializeCarsObstaclesMultiPlayerLeftSide() {

		Random random = new Random();

		for (int i = 0; i < 20; i++) {
			int y = (random.nextInt(5) + 5) * -100;

			String url = generateRandomColor();
			int position = generateRandomPositionXMultiPlayerLeftSide();
            Obstacle rectangle = new Obstacle(position, y , 30, 60);
            
            Image image = new Image(url);
            
            ImagePattern pattern = new ImagePattern(image);
            rectangle.setFill(pattern);
            
            carsObstaclesMultiPlayerLeftSide[i] = rectangle;
            gamePane.getChildren().add(rectangle);
        }
	}

	//Method to initialize the obstacles on the right side on the multiplayer mode
	private void initializeCarsObstaclesMultiPlayerRightSide() {

		Random random = new Random();

		for (int i = 0; i < 20; i++) {
			int y = (random.nextInt(5) + 5) * -100;

			String url = generateRandomColor();
			int position = generateRandomPositionXMultiPlayerRightSide();
            Obstacle rectangle = new Obstacle(position, y , 30, 60);
            
            Image image = new Image(url);
            
            ImagePattern pattern = new ImagePattern(image);
            rectangle.setFill(pattern);
            
            carsObstaclesMultiPlayerRightSide[i] = rectangle;
            
            gamePane.getChildren().add(rectangle);
        }
	}

	//Method to generate a random position for the X axis on the single player
	private int generateRandomPositionXSinglePlayer() {
		Random random = new Random();
		int position = 0;
		int randomColor = random.nextInt(6) + 1;

		switch (randomColor) {
			case 1 -> position = 310;
			case 2 -> position = 380;
			case 3 -> position = 450;
			case 4 -> position = 520;
			case 5 -> position = 590;
			case 6 -> position = 660;
		}
		return position;
	}

	//Method to generate a random position for the X axis on the multiplayer mode for the left side
	private int generateRandomPositionXMultiPlayerLeftSide() {
		Random random = new Random();
		int position = 0;
		int randomColor = random.nextInt(6) + 1;
		switch (randomColor) {
			case 1 -> position = 67;
			case 2 -> position = 132;
			case 3 -> position = 197;
			case 4 -> position = 262;
			case 5 -> position = 327;
			case 6 -> position = 392;
		}
		return position;
	}

	//Method to generate a random position for the X axis on the multiplayer mode for the right side
	private int generateRandomPositionXMultiPlayerRightSide() {
		Random random = new Random();
		int position = 0;
		int randomColor = random.nextInt(6) + 1;
		switch (randomColor) {
			case 1 -> position = 542;
			case 2 -> position = 607;
			case 3 -> position = 672;
			case 4 -> position = 737;
			case 5 -> position = 802;
			case 6 -> position = 867;
		}
		return position;
	}

	//Method to generate a random color for the obstacles
	private String generateRandomColor() {
		Random random = new Random();
		String url = "";
		int randomColor = random.nextInt(5) + 1;
		switch (randomColor) {
			case 1 -> url = "file:resources/cars/car_black.png";
			case 2 -> url = "file:resources/cars/car_blue.png";
			case 3 -> url = "file:resources/cars/car_green.png";
			case 4 -> url = "file:resources/cars/car_red.png";
			case 5 -> url = "file:resources/cars/car_yellow.png";
		}
		return url;
	}

	//Method to restart the obstacles when they are at the bottom of the screen
	private void restartCar(Obstacle obstacle) {
		Random random = new Random();
		int y = (random.nextInt(11) + 5) * -100;
		obstacle.setLayoutY(y);
	}

	//Method to create a new stage that will ask for the name of the player and add the data to the file of scores
	public void switchToScoreStage(Stage currentStage) {
        Stage newStage = new Stage();
        newStage.setTitle("MDC FINAL PROJECT - Score");
		Label label = new Label("Enter your name");
        try {
			label.setFont(Font.loadFont(new FileInputStream("Resources/Font/kenvector_future.ttf"),14));
		}catch(FileNotFoundException e) {
			label.setFont(Font.font("Verdana",14));
		}

        TextField textField = new TextField();
        try {
			textField.setFont(Font.loadFont(new FileInputStream("Resources/Font/kenvector_future.ttf"),14));
		}catch(FileNotFoundException e) {
			textField.setFont(Font.font("Verdana",14));
		}
        GameSmallButton button = new GameSmallButton("Submit");
        button.setLayoutX(150);
        button.setOnAction(e -> {
            String name = textField.getText();
			try {
				FileWriter file = new FileWriter("scores.csv", true);
				file.write(name + "," + elapsedSeconds/100 );
				file.write(System.getProperty( "line.separator" ));
				file.close();
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
			newStage.close();
            createMainMenu();
        });
        VBox layout = new VBox(30);
        layout.getChildren().addAll(label, textField, button);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 300, 200);
        newStage.setScene(scene);
        BackgroundFill backgroundFill = new BackgroundFill(Color.rgb(30, 167, 225), null, null);
        Background background = new Background(backgroundFill);
        layout.setBackground(background);
        currentStage.close();
		newStage.initStyle(StageStyle.UNDECORATED);
		newStage.setResizable(false);
        newStage.show();
    }

	//Method to create a new stage that will show which player is the winner on the multiplayer mode
	public static void showWinner(Stage currentStage) {
        Stage newStage = new Stage();
        newStage.setTitle("MDC FINAL PROJECT - Score");
        String message = "";
        if(playerLeftLose) {
			message = "The Player on the Right is the winner";
        }
        if(playerRightLose) {
			message = "The Player on the Left is the winner";
        }
        Label label = new Label(message);
        try {
			label.setFont(Font.loadFont(new FileInputStream("Resources/Font/kenvector_future.ttf"),14));
		}catch(FileNotFoundException e) {
			label.setFont(Font.font("Verdana",14));
		}
        GameSmallButton button = new GameSmallButton("Ok");
        button.setOnAction(e -> {
			currentStage.close();
			newStage.close();
			createMainMenu();
        });
        VBox layout = new VBox(25);
        layout.getChildren().addAll(label, button);
        layout.setAlignment(Pos.BASELINE_CENTER);
        Scene scene = new Scene(layout, 500, 100);
        newStage.setScene(scene);
        BackgroundFill backgroundFill = new BackgroundFill(Color.rgb(30, 167, 225), null, null);
        Background background = new Background(backgroundFill);
        layout.setBackground(background);
        newStage.setResizable(false);
        newStage.initStyle(StageStyle.UNDECORATED);
        newStage.show();
    }

	//Method to restart the game when the game is over
	public static void createMainMenu() {
		Stage primaryStage;
		ViewManager manager = new ViewManager();
		primaryStage = manager.getMainStage();
		primaryStage.setResizable(false);
		primaryStage.setTitle("MDC FINAL PROJECT");
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.show();
	}

	//Method to avoid obstacles are on top of each other
	private void checkCollisionBetweenObstacles(Obstacle[] obstacles){
		for(int i = 0; i<obstacles.length; i++){
			for(int j = i+1; j < obstacles.length; j++){
				if(obstacles[i].getBoundsInParent().intersects(obstacles[j].getBoundsInParent())){
					restartCar(obstacles[i]);
				}
			}
		}
	}

	//Method to set the background of the multiplayer mode
	private void createBackgroundMultiPlayer() {
		Image bckgImage = new Image("file:Resources/Background/Background_MultiPlayer.png", 1000, 800, false, true);
		BackgroundImage bckg = new BackgroundImage(bckgImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, null);
		gamePane.setBackground(new Background(bckg));
	}

	//Method to set the background of the single player mode
	private void createBackgroundSinglePlayer() {
		Image bckgImage = new Image("file:Resources/Background/Background_SinglePlayer.png", 1000, 800, false, true);
		BackgroundImage bckg = new BackgroundImage(bckgImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, null);
		gamePane.setBackground(new Background(bckg));
	}

	//Method to play the music of the game
	public void createMusicGame(){
		String musicFilePath = "Resources/Music/Playing_Theme.mp3";
		Media media = new Media(new File(musicFilePath).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.play();
	}
}