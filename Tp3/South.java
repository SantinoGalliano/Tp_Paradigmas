package nemo;

public class South extends DirectionOfficer {

	private String direction = "SOUTH";

	public Submarine turnLeft(Submarine submarine) {
		return updateCardinalPoint(submarine, new East());
	}

	public Submarine turnRight(Submarine submarine) {
		return updateCardinalPoint(submarine, new West());
	}

	public Submarine moveForward(Submarine submarine) {
		int actualCoordinateX = submarine.getXPosition();
		int actualCoordinateY = submarine.getYPosition();
		submarine.setNewCoordinate(new Point(actualCoordinateX, actualCoordinateY - 1));
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
