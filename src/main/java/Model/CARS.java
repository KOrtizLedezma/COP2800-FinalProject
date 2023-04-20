package Model;

public enum CARS {
	CAR_BLACK("file:resources/cars/car_black.png"),
	CAR_BLUE("file:resources/cars/car_blue.png"),
	CAR_GREEN("file:resources/cars/car_green.png"),
	CAR_RED("file:resources/cars/car_red.png"),
	CAR_YELLOW("file:resources/cars/car_yellow.png");
	
	private final String urlCar;
	
	//Constructor of the Characters, this method receives the direction of the image of the character
	CARS(String urlCar) {
		this.urlCar = urlCar;
	}
	
	//Returns the url direction of the image of the character
	public String getUrl() {
		return this.urlCar;
	}
}
