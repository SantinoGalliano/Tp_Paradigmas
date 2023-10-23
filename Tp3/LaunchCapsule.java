package nemo;

public class LaunchCapsule extends Command {
	
	private Character value = 'm';
	
	public Submarine commandInstruction(Submarine submarine) {
		return submarine.launchCapsule();
	}
	
	public boolean applies(Character characterCommand) {
		return value == characterCommand;
	}
	
}
