package linea;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Linea {

	private int base;
	private int height;
	private WinningFormat strategySelected;
	private ArrayList<ArrayList<String>> board = new ArrayList<>();
	private Turn turn;
	private GameState gameState;
	private int actualTokens;

	public Linea(int base, int height, char triumphVariant) {
		this.base = base;
		this.height = height;
		initializeGame(base, height, triumphVariant);
	}

	public Linea playRedAt(int column) {
		checkExistenceOfColumn(column);
		isThisColumnFull(column);
		checkGameState();
		isRedTurn();
		playToken(column, getColumn(column), "R");

		return this;
	}

	public Linea playBlueAt(int column) {
		checkExistenceOfColumn(column);
		isThisColumnFull(column);
		checkGameState();
		isBlueTurn();
		playToken(column, getColumn(column), "B");

		return this;
	}

	private Linea isThisColumnFull(int column) {
		if (getColumn(column).size() == height) {
			throw new RuntimeException(fullColumn);
		} else {
			return this;
		}
	}

	public String show() {
		return IntStream.rangeClosed(0, height - 1)
				.mapToObj(row -> "| " + IntStream.range(0, base)
						.mapToObj(column -> (!board.get(column).isEmpty() && board.get(column).size() > row)
								? (board.get(column).get(row) + " ")
								: "- ")
						.collect(Collectors.joining()))
				.map(row -> row + "|").collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
					Collections.reverse(list);
					return list.stream().collect(Collectors.joining("\n"));
				})) + "\n" + "| " + IntStream.range(0, base).mapToObj(column -> "^ ").collect(Collectors.joining())
				+ "|" + "\n" + gameState.getMessage(this);
	}

	public boolean finished() {
		return gameState.isFinished();
	}

	private void initializeGame(int base, int height, char triumphVariant) {
		if (base <= 0 || height <= 0) {
			throw new RuntimeException(invalidDimensions);
		}

		strategySelected = WinningFormat.strategyFor(triumphVariant);
		IntStream.range(0, base).forEach((column) -> board.add(new ArrayList<>()));
		turn = new RedTurn();
		gameState = new ActiveGame();
	}

	private void checkExistenceOfColumn(int column) {
		if (column < 1 || column > base) {
			throw new RuntimeException(inexistentColumn);
		}
	}

	private void playToken(int columnNumber, ArrayList<String> columnList, String colour) {
		columnList.add(colour);
		actualTokens += 1;
		Optional.of(columnList)
				.filter(list -> strategySelected.isThere4ConsecutiveTokens(colour, columnNumber, list.size() - 1, this))
				.ifPresent(list -> gameState = new FinishedGame());
		Optional.of(actualTokens == base * height).filter(Boolean::booleanValue)
				.ifPresent(tied -> gameState = new TiedGame());

	}

	private void isRedTurn() {
		turn.isRedTurn(this);
	}

	private void isBlueTurn() {
		turn.isBlueTurn(this);
	}

	private Linea checkGameState() {
		return gameState.getGameState(this);
	}

	public ArrayList<String> getColumn(int column) {
		return board.get(column - 1);
	}

	public ArrayList<ArrayList<String>> getTablero() {
		return board;
	}

	public Turn getCurrentTurn() {
		return turn;
	}

	public int getAltura() {
		return height;
	}

	public int getBase() {
		return base;
	}

	public String getMessageOfTheGame() {
		return gameState.getMessage(this);
	}

	public void setNewTurn(Turn newTurn) {
		this.turn = newTurn;
	}

	public static final String fullColumn = "This column is already full";
	public static final String inexistentColumn = "This column does not exist";
	public static final String invalidDimensions = "Negative dimensions are not permitted";
	public static final String invalidWinningFormat = "This winning format does not exist";
	public static final String aTieHasBeenReached = "> A tie has been reached <";
	public static final String gameIsFinished = "Game is already finished";
	public static final String notRedsTurn = "It is not Reds turn";
	public static final String notBluesTurn = "It is not Blues turn";


}
