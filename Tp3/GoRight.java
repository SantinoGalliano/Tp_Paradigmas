package nemo;

public class GoRight extends Command {
	
	private Character value = 'r';
	
	public Submarine commandInstruction(Submarine submarine) {
		return submarine.turnRight();
	}
	
	public boolean applies(Character characterCommand) {
		return value == characterCommand;
	}
	
}
