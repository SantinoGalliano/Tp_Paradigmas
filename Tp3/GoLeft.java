package nemo;

public class GoLeft extends Command {
	
	private Character value = 'l';

	public Submarine commandInstruction(Submarine submarine) {
		return submarine.turnLeft();
	}
	
	public boolean applies(Character characterCommand) {
		return value == characterCommand;
	}
	
}
