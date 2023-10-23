package nemo;

public abstract class DirectionOfficer {
	
	 public abstract String getCardinalPoint ();
	 public abstract Submarine moveForward (Submarine submarine);
	 public abstract Submarine turnLeft (Submarine submarine);
	 public abstract Submarine turnRight (Submarine submarine);
	
}
