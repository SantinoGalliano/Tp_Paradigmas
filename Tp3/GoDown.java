package nemo;

public class GoDown extends Command {
	
	private Character value = 'd';
	
	public Submarine commandInstruction(Submarine submarine) {
		return submarine.descend();	
	}
	
	public boolean applies(Character characterCommand) {
		return value == characterCommand;
	}
	
}
