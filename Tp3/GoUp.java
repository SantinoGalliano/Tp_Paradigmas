package nemo;

public class GoUp extends Command {

	private Character value = 'u';
	
	public Submarine commandInstruction(Submarine submarine) {
		return submarine.ascend();
	}
	
	public boolean applies(Character characterCommand) {
		return value == characterCommand;
	}
	
}