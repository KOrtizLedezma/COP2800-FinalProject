package View;

import Model.Obstacle;
import javafx.scene.image.ImageView;

public class Physics {
	static final int SPEED_Y = 1;
	static final int SPEED_X = 1;
	static final int STARTS_X_SINGLEPLAYER = 640;
	static final int STARTS_Y_SINGLEPLAYER = 400;
	static final int STARTS_X_MULTIPLAYER_PLAYER_A = 640;
	static final int STARTS_Y_MULTIPLAYER_PLAYER_A = 400;
	static final int STARTS_X_MULTIPLAYER_PLAYER_B = 200;
	static final int STARTS_Y_MULTIPLAYER_PLAYER_B = 400;
	
	int positionXSinglePlayer = STARTS_X_SINGLEPLAYER;
	int positionYSinglePlayer = STARTS_Y_SINGLEPLAYER;
	int positionXMultiplayerPlayerB = STARTS_X_MULTIPLAYER_PLAYER_B;
	int positionYMultiplayerPlayerB = STARTS_Y_MULTIPLAYER_PLAYER_B;
	int positionXMultiplayerPlayerA = STARTS_X_MULTIPLAYER_PLAYER_A;
	int positionYMultiplayerPlayerA = STARTS_Y_MULTIPLAYER_PLAYER_A;
	int speedX = 0;
	int speedY = 0;

	//Method to update the position the player in each game mode
	void update() {
		positionXSinglePlayer += speedX;
		positionYSinglePlayer += speedY;
		positionXMultiplayerPlayerA += speedX;
		positionYMultiplayerPlayerA += speedY;
		positionXMultiplayerPlayerB += speedX;
		positionYMultiplayerPlayerB += speedY;
	}

	//Method to move the player in the position that is given on the key-listener of the game, this controls the movement on the X axis
	void move(int direction) {
		speedX = 0;
		speedX += SPEED_X * direction;
	}
	//Method to move the player in the position that is given on the key-listener of the game, this controls the movement on the Y axis
	void moveForward(int direction) {
		speedY = 0;
		speedY += SPEED_Y * direction;
	}

	//Method to checks if the player collides with an obstacle
	boolean collideRectangle(Obstacle obstacle, ImageView imageView) {
		return obstacle.collidesWith(imageView);
	}
	
}
