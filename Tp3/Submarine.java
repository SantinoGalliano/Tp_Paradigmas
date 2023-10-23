package nemo;

import java.util.ArrayList;
import java.util.Arrays;

public class Submarine {
	
	private int z = 0;
	private DirectionOfficer direction;
	private Point currentCoordinates;
    private ArrayList<NavigationOfficer> stateMemory = new ArrayList<>(Arrays.asList(new SurfaceDuties()));

	
    public Submarine(int x, int y, DirectionOfficer direction) {
    	this.currentCoordinates = new Point(x, y);
    	this.direction = direction;

    }
    
    public void processCommands(String commands) {
    	commands.chars().forEach(instruction -> executeInstruction( (char) instruction));

    }
    
    public void executeInstruction(Character instruction) {
    	Command.commandFor(instruction).commandInstruction(this);
    }
    
    public Submarine ascend() {
    	return getActualLevelState().ascend(this);
    }
    public Submarine descend() {
    	return getActualLevelState().descend(this);
    }
    
    public Submarine launchCapsule() {
    	return getActualLevelState().launchCapsule(this);
    }
    public Submarine moveForward() {
    	return getCardinalPoint().moveForward(this);
    }
    public Submarine turnRight() {
    	return getCardinalPoint().turnRight(this);
    }
    public Submarine turnLeft() {
    	return getCardinalPoint().turnLeft(this);
    }
 
 
    public NavigationOfficer getActualLevelState() {
    	return stateMemory.get(stateMemory.size()-1);
    }
    
    public int getXPosition() {
        return currentCoordinates.getX();
    } 
    
    public int getYPosition() {
    	 return currentCoordinates.getY();
    }
    
    public int getZPosition() {
        return z;
    }
    
    public String getDirection () {
    	return direction.getCardinalPoint();
    }
    
    public DirectionOfficer getCardinalPoint () {
    	return direction;
    }
    
    public ArrayList<NavigationOfficer> getStateMemory () {
    	return stateMemory;
    }
    
 
    public void setAxisZ (int newZ) {
    	this.z = newZ;
    }
    
    public void setNewCardinalPoint (DirectionOfficer newCardinalPoint) {
    	this.direction = newCardinalPoint;
    }
    
    public void setNewStateMemory (ArrayList<NavigationOfficer> newStateMemory) {
    	this.stateMemory = newStateMemory;
    }
    public void setNewCoordinate(Point newCoordinates) {
    	this.currentCoordinates = newCoordinates;
    	
    }
    
    
}
