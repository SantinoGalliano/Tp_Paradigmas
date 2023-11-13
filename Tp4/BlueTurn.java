package linea;

public class BlueTurn extends Turn {
	
	protected String rival = "Red";
	protected String currentPlayer = "Blue";
	
	public Linea isRedTurn(Linea game) {
		 throw new RuntimeException(Linea.notRedsTurn);
	}
	
	public Linea isBlueTurn(Linea game) {
		game.setNewTurn(new RedTurn());
		return game;
	}

	public String getRival() {
		return rival;
	}
	
	public String getCurrentPlayer() {
		return currentPlayer;
	}
		
}
