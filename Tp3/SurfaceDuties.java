package nemo;

import java.util.ArrayList;

public class SurfaceDuties extends NavigationOfficer {
	
	public Submarine ascend (Submarine submarine) {
		return submarine;
	}
	
	public Submarine descend (Submarine submarine) {
		submarine.setAxisZ(submarine.getZPosition() - 1);
		ArrayList<NavigationOfficer> newMemory = submarine.getStateMemory();
		newMemory.add(new FirstDiveDuties());
		submarine.setNewStateMemory( newMemory );
		return submarine;
	}
	
	public Submarine launchCapsule (Submarine submarine) {
		return submarine;
	}
	
}
