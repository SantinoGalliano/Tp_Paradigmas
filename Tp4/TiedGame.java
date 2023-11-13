package linea;

public class TiedGame extends GameState {

	protected boolean currentState = true;

	public Linea getGameState(Linea line) {
		return line;
	}

	public boolean isFinished() {
		return currentState;
	}

	public String getMessage(Linea line) {
		return Linea.aTieHasBeenReached;
	}

}
