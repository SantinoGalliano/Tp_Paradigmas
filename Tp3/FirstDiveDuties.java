package nemo;

import java.util.ArrayList;

public class FirstDiveDuties extends NavigationOfficer {

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
		return submarine;
	}

}
