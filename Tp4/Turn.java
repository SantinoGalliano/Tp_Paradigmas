package linea;

public abstract class Turn {

	protected String rival;
	
	protected String currentPlayer;
	
	public abstract Linea isBlueTurn(Linea game);

	public abstract Linea isRedTurn(Linea game);

	public abstract String getRival();

	public abstract String getCurrentPlayer();

}
