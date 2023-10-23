package nemo;

public class West extends DirectionOfficer {

	private String direction = "WEST";

	public Submarine turnLeft(Submarine submarine) {
		return updateCardinalPoint(submarine, new South());
	}

	public Submarine turnRight(Submarine submarine) {
		return updateCardinalPoint(submarine, new North());
	}

	public Submarine moveForward(Submarine submarine) {
		int actualCoordinateX = submarine.getXPosition();
		int actualCoordinateY = submarine.getYPosition();
		submarine.setNewCoordinate(new Point(actualCoordinateX - 1, actualCoordinateY));
		return submarine;
	}

	private Submarine updateCardinalPoint(Submarine submarine, DirectionOfficer newCardinalPoint) {
		submarine.setNewCardinalPoint(newCardinalPoint);
		return submarine;
	}

	public String getCardinalPoint() {
		return direction;
	}

}
