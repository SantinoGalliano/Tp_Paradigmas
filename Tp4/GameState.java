package linea;

public abstract class GameState {

	protected boolean currentState;

	protected abstract String getMessage(Linea line);

	public abstract Linea getGameState(Linea line);

	public abstract boolean isFinished();

}
