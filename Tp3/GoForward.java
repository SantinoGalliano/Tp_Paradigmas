package nemo;

public class GoForward extends Command {
	
	private Character value = 'f';

	public Submarine commandInstruction(Submarine submarine) {
		return submarine.moveForward();
	}
	
	public boolean applies(Character characterCommand) {
		return value == characterCommand;
	}
	
}
