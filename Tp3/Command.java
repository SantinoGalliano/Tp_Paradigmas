package nemo;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Command {
	private static ArrayList<Command> commandList = new ArrayList<>(Arrays.asList(new GoUp(),new GoDown(),new GoForward(),new GoLeft(),new GoRight(),new LaunchCapsule()));
	public abstract Submarine commandInstruction(Submarine submarine);
	public abstract boolean applies(Character characterCommand);
	public static Command commandFor(Character characterCommand) {
		
		return commandList.stream().filter(command -> command.applies(characterCommand)).toList().get(0);
		
	}
	
	
}