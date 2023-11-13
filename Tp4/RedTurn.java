package linea;

public class RedTurn extends Turn {

	protected String rival = "Blue";
	protected String currentPlayer = "Red";

	public Linea isRedTurn(Linea game) {
		game.setNewTurn(new BlueTurn());
		return game;
	}

	public Linea isBlueTurn(Linea game) {
		throw new RuntimeException(Linea.notBluesTurn);
	}

	public String getRival() {
		return rival;
	}

	public String getCurrentPlayer() {
		return currentPlayer;
	}

}
