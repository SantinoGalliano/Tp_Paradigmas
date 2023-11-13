package linea;

public class FinishedGame extends GameState {
	
	protected boolean currentState = true;
	
	public Linea getGameState(Linea line) {
		throw new RuntimeException(Linea.gameIsFinished);
	}
	
	public boolean isFinished() {
		return currentState;
	}
	
	public String getMessage(Linea line) {
		return "> "+ line.getCurrentTurn().getRival() +" won <";
	}
	
}
