package linea;

public class ActiveGame extends GameState {

	protected boolean currentState = false;

	public Linea getGameState(Linea line) {
		return line;
	}

	public boolean isFinished() {
		return currentState;
	}

	protected String getMessage(Linea line) {
		return "> Playing " + line.getCurrentTurn().getCurrentPlayer() + " <";
	}

}
