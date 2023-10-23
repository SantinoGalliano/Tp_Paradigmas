package nemo;

import java.util.ArrayList;

public class DangerZone extends NavigationOfficer {

	public static final String TheSubmarineHasBeenDestroyed = "The submarine has been destroyed";

	public Submarine ascend(Submarine submarine) {
		submarine.setAxisZ(submarine.getZPosition() + 1);
		ArrayList<NavigationOfficer> newMemory = submarine.getStateMemory();
		newMemory.remove(newMemory.size() - 1);
		submarine.setNewStateMemory(newMemory);
		return submarine;

	}

	public Submarine descend(Submarine submarine) {
		submarine.setAxisZ(submarine.getZPosition() - 1);
		ArrayList<NavigationOfficer> newMemory = submarine.getStateMemory();
		newMemory.add(new DangerZone());
		submarine.setNewStateMemory(newMemory);
		return submarine;
	}

	public Submarine launchCapsule(Submarine submarine) {
		throw new Error(TheSubmarineHasBeenDestroyed);
	}

}