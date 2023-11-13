package linea;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class LineaTest {

	private char DiagonalWinningFormat;
	private char HorizontalAndVerticalWinningFormat;
	private char MultipleWinningFormats;
	private int column1;
	private int column2;
	private int column3;
	private int column4;
	private int column5;
	private int column6;

	@BeforeEach
	public void setUp() {
		column1 = 1;
		column2 = 2;
		column3 = 3;
		column4 = 4;
		column5 = 5;
		column6 = 6;
		DiagonalWinningFormat = 'B';
		HorizontalAndVerticalWinningFormat = 'A';
		MultipleWinningFormats = 'C';
	}

	@Test
	public void test01NullDimensionsAreNotPermitted() {
		assertThrowsLike(() -> new Linea(0, 0, HorizontalAndVerticalWinningFormat), Linea.invalidDimensions);
	}

	@Test
	public void test02StartAGameWithSuitableDimensions() {
		isRedPlaying(initializesA4x4Game(HorizontalAndVerticalWinningFormat));
	}

	@Test
	public void test02CannotStartAGameWithANonSuitableWinningFormat() {
		assertThrowsLike(() -> initializesA4x4Game('D'), Linea.invalidWinningFormat);
	}

	@Test
	public void test03StartAHorizontalVerticalWinningFormatGame() {
		Linea game = initializesA4x4Game(HorizontalAndVerticalWinningFormat);
		isRedPlaying(game);
	}

	@Test
	public void test04StartADiagonalWinningFormatGame() {
		Linea game = initializesA4x4Game(DiagonalWinningFormat);
		isRedPlaying(game);
	}

	@Test
	public void test05StartAMultipleWinningFormatGame() {
		Linea game = initializesA4x4Game(MultipleWinningFormats);
		isRedPlaying(game);
	}

	@Test
	public void test06RedTokensStartPlayingInAHorizontalVerticalWinningFormatGame() {
		Linea game = initializesA4x4Game(HorizontalAndVerticalWinningFormat);
		game.playRedAt(column1);
		isBluePlaying(game);
	}

	@Test
	public void test07BlueTokensPlayInAHorizontalVerticalWinningFormatGameAfterRedTokensTurn() {
		Linea game = initializesA4x4Game(HorizontalAndVerticalWinningFormat);
		game.playRedAt(column1);
		game.playBlueAt(column2);
		isRedPlaying(game);

	}

	@Test
	public void test22BlueTokensCannotPlayInWrongTurn() {
		Linea game = initializesA6x6Game(HorizontalAndVerticalWinningFormat);
		game.playRedAt(column1);
		game.playBlueAt(column2);
		assertThrowsLike(() -> game.playBlueAt(column1), Linea.notBluesTurn);
		isRedPlaying(game);
	}

	@Test
	public void test23RedTokensCannotPlayInAWrongTurn() {
		Linea game = new Linea(6, 6, HorizontalAndVerticalWinningFormat);
		game.playRedAt(column1);
		assertThrowsLike(() -> game.playRedAt(column1), Linea.notRedsTurn);
		isBluePlaying(game);
	}

	@Test
	public void test08RedTokensCanPlayInASameNonFullColumnWhereBlueTokensPlayedPreviously() {
		Linea game = initializesA4x4Game(HorizontalAndVerticalWinningFormat);
		game.playRedAt(column1);
		game.playBlueAt(column2);
		game.playRedAt(column2);
		isBluePlaying(game);
	}

	@Test
	public void test09RedTokensCannotPlayInAFullColumn() {
		Linea game = initializesA4x4Game(HorizontalAndVerticalWinningFormat);
		game.playRedAt(column2);
		game.playBlueAt(column2);
		game.playRedAt(column2);
		game.playBlueAt(column2);
		assertThrowsLike(() -> game.playRedAt(column2), Linea.fullColumn);
		isRedPlaying(game);
	}

	@Test
	public void test10BlueTokensCannotPlayInAFullColumn() {
		Linea game = initializesA4x4Game(HorizontalAndVerticalWinningFormat);
		game.playRedAt(column2);
		game.playBlueAt(column2);
		game.playRedAt(column2);
		game.playBlueAt(column2);
		game.playRedAt(column1);
		assertThrowsLike(() -> game.playBlueAt(column2), Linea.fullColumn);
		isBluePlaying(game);

	}

	@Test
	public void test11RedTokensCannotPlayInAnInexistentColumn() {
		Linea game = initializesA4x4Game(HorizontalAndVerticalWinningFormat);
		assertThrowsLike(() -> game.playRedAt(column5), Linea.inexistentColumn);
		isRedPlaying(game);
	}

	@Test
	public void test12BlueTokensCannotPlayInAnInexistentColumn() {
		Linea game = initializesA4x4Game(HorizontalAndVerticalWinningFormat);
		game.playRedAt(column4);
		int column8 = 8;
		assertThrowsLike(() -> game.playBlueAt(column8), Linea.inexistentColumn);
		isBluePlaying(game);
	}

	@Test
	public void test24ShowWorksCorrectlyInSquareDimensions() {
		Linea game = initializesA4x4Game(HorizontalAndVerticalWinningFormat);
		testingANotFinishedGameBoard(game, "| - - - - |\n" + "| - - - - |\n" + "| - - - - |\n" + "| - - - - |\n"
				+ "| ^ ^ ^ ^ |\n" + "> Playing Red <");
	}

	@Test
	public void test28ShowWorksCorrectlyInNonSquareDimensions() {
		Linea game = new Linea(5, 7, MultipleWinningFormats);
		testingANotFinishedGameBoard(game, "| - - - - - |\n" + "| - - - - - |\n" + "| - - - - - |\n" + "| - - - - - |\n"
				+ "| - - - - - |\n" + "| - - - - - |\n" + "| - - - - - |\n" + "| ^ ^ ^ ^ ^ |\n" + "> Playing Red <");
	}

	@Test
	public void test25ShowRepresentsAMovement() {
		Linea game = initializesA4x4Game(HorizontalAndVerticalWinningFormat);
		game.playRedAt(column1);
		testingANotFinishedGameBoard(game, "| - - - - |\n" + "| - - - - |\n" + "| - - - - |\n" + "| R - - - |\n"
				+ "| ^ ^ ^ ^ |\n" + "> Playing Blue <");
	}

	@Test
	public void test26ShowRepresentesMultipleMovements() {
		Linea game = initializesA4x4Game(HorizontalAndVerticalWinningFormat);
		game.playRedAt(column1);
		game.playBlueAt(column2);
		game.playRedAt(column1);
		game.playBlueAt(column2);
		game.playRedAt(column4);
		testingANotFinishedGameBoard(game, "| - - - - |\n" + "| - - - - |\n" + "| R B - - |\n" + "| R B - R |\n"
				+ "| ^ ^ ^ ^ |\n" + "> Playing Blue <");
	}

	@Test
	public void test15NobodyWinsTheGame() {
		Linea game = initializesA4x4Game(HorizontalAndVerticalWinningFormat);
		game.playRedAt(column1);
		game.playBlueAt(column1);
		game.playRedAt(column1);
		game.playBlueAt(column1);
		isRedPlaying(game);
		testingANotFinishedGameBoard(game, "| B - - - |\n" + "| R - - - |\n" + "| B - - - |\n" + "| R - - - |\n"
				+ "| ^ ^ ^ ^ |\n" + "> Playing Red <");
	}

	@Test
	public void test13RedTokensWinTheGameVertically() {
		Linea game = initializesA4x4Game(HorizontalAndVerticalWinningFormat);
		movementsThatMakeRedTokensWinVertically(game);
		assertTheCurrentGameState(redTokensWin,
				"| R - - - |\n" + "| R B - - |\n" + "| R B - - |\n" + "| R B - - |\n" + "| ^ ^ ^ ^ |\n" + redTokensWin,
				game);
	}

	@Test
	public void test16RedTokensWinTheGameHorizontally() {
		Linea game = initializesA6x6Game(HorizontalAndVerticalWinningFormat);
		movementsThatMakeRedTokensWinHorizontally(game);
		assertTheCurrentGameState(redTokensWin, "| - - - - - - |\n" + "| - - - - - - |\n" + "| - - - - - - |\n"
				+ "| - - - - - - |\n" + "| - B B B - - |\n" + "| - R R R R - |\n" + "| ^ ^ ^ ^ ^ ^ |\n" + redTokensWin,
				game);
	}

	@Test
	public void test17RedTokensWinTheGameDiagonallyUpToTheRight() {
		Linea game = initializesA6x6Game(DiagonalWinningFormat);
		movementsThatMakeRedTokensWinDiagonallyUpToTheRight(game);
		assertTheCurrentGameState(redTokensWin, "| - - - - - - |\n" + "| - - - - - - |\n" + "| - - - R - - |\n"
				+ "| - - R B - - |\n" + "| B R R R - - |\n" + "| R B B B - - |\n" + "| ^ ^ ^ ^ ^ ^ |\n" + redTokensWin,
				game);
	}

	@Test
	public void test18RedTokensWinTheGameDiagonallyDownToTheRight() {
		Linea game = initializesA6x6Game(DiagonalWinningFormat);
		movementsThatMakeRedTokensWinDiagonallyDownToTheRight(game);
		assertTheCurrentGameState(redTokensWin, "| - - - - - - |\n" + "| R - - - - - |\n" + "| B R - - - - |\n"
				+ "| R B R - - - |\n" + "| R B B R B - |\n" + "| R B R R B B |\n" + "| ^ ^ ^ ^ ^ ^ |\n" + redTokensWin,
				game);
	}

	@Test
	public void test19RedTokensWinTheGameDiagonallyUpToTheLeft() {
		Linea game = initializesA6x6Game(DiagonalWinningFormat);
		movementsThatMakeRedTokensWinDiagonallyUpToTheLeft(game);
		assertTheCurrentGameState(redTokensWin, "| - - - - - - |\n" + "| - - - - - - |\n" + "| R - - - - - |\n"
				+ "| B R - - - - |\n" + "| R R R B - - |\n" + "| B B B R - - |\n" + "| ^ ^ ^ ^ ^ ^ |\n" + redTokensWin,
				game);
	}

	@Test
	public void test20RedTokensWinTheGameDiagonallyDownToTheLeft() {
		Linea game = initializesA6x6Game(DiagonalWinningFormat);
		movementsThatMakeRedTokensWinDiagonallyDownToTheLeft(game);
		assertTheCurrentGameState(redTokensWin, "| - - - - - - |\n" + "| - - - - B R |\n" + "| - - - - R B |\n"
				+ "| - - - R B R |\n" + "| - - R R R B |\n" + "| - - B B B R |\n" + "| ^ ^ ^ ^ ^ ^ |\n" + redTokensWin,
				game);
	}

	@Test
	public void test14BlueTokensWinTheGameVertically() {
		Linea game = initializesA4x4Game(HorizontalAndVerticalWinningFormat);
		movementsThatMakeBlueTokensWinVertically(game);
		assertTheCurrentGameState(blueTokensWin,
				"| - B - - |\n" + "| R B - - |\n" + "| R B - - |\n" + "| R B R - |\n" + "| ^ ^ ^ ^ |\n" + blueTokensWin,
				game);
	}

	@Test
	public void test16BlueTokensWinTheGameHorizontally() {
		Linea game = initializesA4x4Game(HorizontalAndVerticalWinningFormat);
		movementsThatMakeBlueTokensWinHorizontally(game);
		assertTheCurrentGameState(blueTokensWin,
				"| - - - - |\n" + "| R R - - |\n" + "| B B B B |\n" + "| R R R B |\n" + "| ^ ^ ^ ^ |\n" + blueTokensWin,
				game);
	}

	@Test
	public void test21BlueTokensWinTheGameDiagonallyUpToTheRight() {
		Linea game = initializesA6x6Game(DiagonalWinningFormat);
		movementsThatMakeBlueTokensWinDiagonallyUpTheRight(game);
		assertTheCurrentGameState(blueTokensWin, "| - - - - - - |\n" + "| - - - - - - |\n" + "| - - - B - - |\n"
				+ "| - - B B - - |\n" + "| - B B R - - |\n" + "| B R R R R R |\n" + "| ^ ^ ^ ^ ^ ^ |\n" + blueTokensWin,
				game);
	}

	@Test
	public void test22BlueTokensWinTheGameDiagonallyDownToTheRight() {
		Linea game = new Linea(6, 6, 'B');
		movementsThatMakeBlueTokensWinDiagonallyDownToTheRight(game);
		assertTheCurrentGameState(blueTokensWin, "| - - - - - - |\n" + "| - - - - - - |\n" + "| - B - - - - |\n"
				+ "| R R B - - - |\n" + "| R B B B - - |\n" + "| R R R B B R |\n" + "| ^ ^ ^ ^ ^ ^ |\n" + blueTokensWin,
				game);
	}

	@Test
	public void test23BlueTokensWinTheGameDiagonallyDownToTheLeft() {
		Linea game = initializesA6x6Game(DiagonalWinningFormat);
		movementsThatMakeBlueTokensWinDiagonallyDownToTheLeft(game);
		assertTheCurrentGameState(blueTokensWin, "| - - - - - - |\n" + "| - - - - - - |\n" + "| - - - - R B |\n"
				+ "| - - - - B R |\n" + "| - - - B B B |\n" + "| R - B R R R |\n" + "| ^ ^ ^ ^ ^ ^ |\n" + blueTokensWin,
				game);
	}

	@Test
	public void test24BlueTokensWinTheGameDiagonallyUpToTheLeft() {
		Linea game = initializesA6x6Game(DiagonalWinningFormat);
		movementsThatMakeBlueTokensWinDiagonallyUpToTheLeft(game);
		assertTheCurrentGameState(blueTokensWin, "| - - - - - - |\n" + "| - - - - - - |\n" + "| B - - - - - |\n"
				+ "| B B R - - - |\n" + "| R B B - - - |\n" + "| R R R B R - |\n" + "| ^ ^ ^ ^ ^ ^ |\n" + blueTokensWin,
				game);
	}

	@Test
	public void test21CannotPlayAfterGameIsFinished() {
		Linea game = initializesA4x4Game(HorizontalAndVerticalWinningFormat);
		movementsThatMakeRedTokensWinVertically(game);
		assertThrowsLike(() -> game.playBlueAt(column3), Linea.gameIsFinished);
		assertTheCurrentGameState(redTokensWin,
				"| R - - - |\n" + "| R B - - |\n" + "| R B - - |\n" + "| R B - - |\n" + "| ^ ^ ^ ^ |\n" + redTokensWin,
				game);
	}

	@Test
	public void test29ATieHasBeenReached() {
		Linea game = initializesA4x4Game(HorizontalAndVerticalWinningFormat);
		movementsThatLeadToA4x4TiedGame(game);
		assertTheCurrentGameState(Linea.aTieHasBeenReached, "| B R B B |\n" + "| R B R R |\n" + "| B R B B |\n"
				+ "| R B R R |\n" + "| ^ ^ ^ ^ |\n" + "> A tie has been reached <", game);

	}

	@Test
	public void test30CannotPlayAfterATiedGame() {
		Linea game = initializesA4x4Game(HorizontalAndVerticalWinningFormat);
		movementsThatLeadToA4x4TiedGame(game);
		assertThrowsLike(() -> game.playRedAt(column1), Linea.fullColumn);
		assertTheCurrentGameState(Linea.aTieHasBeenReached, "| B R B B |\n" + "| R B R R |\n" + "| B R B B |\n"
				+ "| R B R R |\n" + "| ^ ^ ^ ^ |\n" + "> A tie has been reached <", game);
	}

	@Test
	public void test31InAStrategyDiagonalDoesNotWin() {
		Linea game = initializesA6x6Game(HorizontalAndVerticalWinningFormat);
		movementsThatMakeRedTokensWinDiagonallyUpToTheLeft(game);
		isBluePlaying(game);
		assertEquals(game.show(), "| - - - - - - |\n" + "| - - - - - - |\n" + "| R - - - - - |\n" + "| B R - - - - |\n"
				+ "| R R R B - - |\n" + "| B B B R - - |\n" + "| ^ ^ ^ ^ ^ ^ |\n" + "> Playing Blue <");
	}

	@Test
	public void test32InBStrategyHorizontalDoesNotWin() {
		Linea game = initializesA6x6Game(DiagonalWinningFormat);
		movementsThatMakeRedTokensWinHorizontally(game);
		isBluePlaying(game);
		assertEquals(game.show(), "| - - - - - - |\n" + "| - - - - - - |\n" + "| - - - - - - |\n" + "| - - - - - - |\n"
				+ "| - B B B - - |\n" + "| - R R R R - |\n" + "| ^ ^ ^ ^ ^ ^ |\n" + "> Playing Blue <");
	}

	private Linea initializesA6x6Game(Character winningStrategy) {
		Linea game = new Linea(6, 6, winningStrategy);
		return game;
	}

	private void assertThrowsLike(Executable executable, String message) {
		assertEquals(message, assertThrows(Exception.class, executable).getMessage());
	}

	private void movementsThatMakeRedTokensWinVertically(Linea game) {
		game.playRedAt(column1);
		game.playBlueAt(column2);
		game.playRedAt(column1);
		game.playBlueAt(column2);
		game.playRedAt(column1);
		game.playBlueAt(column2);
		game.playRedAt(column1);
	}

	private void movementsThatMakeBlueTokensWinVertically(Linea game) {
		game.playRedAt(column1);
		game.playBlueAt(column2);
		game.playRedAt(column1);
		game.playBlueAt(column2);
		game.playRedAt(column1);
		game.playBlueAt(column2);
		game.playRedAt(column3);
		game.playBlueAt(column2);
	}

	private void movementsThatMakeRedTokensWinHorizontally(Linea game) {
		game.playRedAt(column2);
		game.playBlueAt(column2);
		game.playRedAt(column3);
		game.playBlueAt(column3);
		game.playRedAt(column4);
		game.playBlueAt(column4);
		game.playRedAt(column5);
	}

	private void movementsThatMakeRedTokensWinDiagonallyUpToTheRight(Linea game) {
		game.playRedAt(column1);
		game.playBlueAt(column2);
		game.playRedAt(column2);
		game.playBlueAt(column3);
		game.playRedAt(column3);
		game.playBlueAt(column1);
		game.playRedAt(column3);
		game.playBlueAt(column4);
		game.playRedAt(column4);
		game.playBlueAt(column4);
		game.playRedAt(column4);
	}

	private void movementsThatMakeRedTokensWinDiagonallyDownToTheRight(Linea game) {
		game.playRedAt(column1);
		game.playBlueAt(column2);
		game.playRedAt(column1);
		game.playBlueAt(column2);
		game.playRedAt(column1);
		game.playBlueAt(column2);
		game.playRedAt(column3);
		game.playBlueAt(column3);
		game.playRedAt(column3);
		game.playBlueAt(column1);
		game.playRedAt(column2);
		game.playBlueAt(column5);
		game.playRedAt(column4);
		game.playBlueAt(column6);
		game.playRedAt(column1);
		game.playBlueAt(column5);
		game.playRedAt(column4);
	}

	private void movementsThatMakeRedTokensWinDiagonallyUpToTheLeft(Linea game) {
		game.playRedAt(column4);
		game.playBlueAt(column3);
		game.playRedAt(column3);
		game.playBlueAt(column2);
		game.playRedAt(column2);
		game.playBlueAt(column1);
		game.playRedAt(column1);
		game.playBlueAt(column1);
		game.playRedAt(column2);
		game.playBlueAt(column4);
		game.playRedAt(column1);
	}

	private void movementsThatMakeRedTokensWinDiagonallyDownToTheLeft(Linea game) {
		game.playRedAt(column6);
		game.playBlueAt(column6);
		game.playRedAt(column6);
		game.playBlueAt(column6);
		game.playRedAt(column6);
		game.playBlueAt(column5);
		game.playRedAt(column5);
		game.playBlueAt(column5);
		game.playRedAt(column5);
		game.playBlueAt(column4);
		game.playRedAt(column4);
		game.playBlueAt(column3);
		game.playRedAt(column4);
		game.playBlueAt(column5);
		game.playRedAt(column3);
	}

	private void movementsThatLeadToA4x4TiedGame(Linea game) {
		game.playRedAt(column1);
		game.playBlueAt(column1);
		game.playRedAt(column1);
		game.playBlueAt(column1);
		game.playRedAt(column3);
		game.playBlueAt(column2);
		game.playRedAt(column2);
		game.playBlueAt(column2);
		game.playRedAt(column2);
		game.playBlueAt(column3);
		game.playRedAt(column3);
		game.playBlueAt(column3);
		game.playRedAt(column4);
		game.playBlueAt(column4);
		game.playRedAt(column4);
		game.playBlueAt(column4);
	}

	private Linea initializesA4x4Game(Character winningStrategy) {
		Linea game = new Linea(4, 4, winningStrategy);
		return game;
	}

	private void isRedPlaying(Linea game) {
		assertEquals(game.getMessageOfTheGame(), "> Playing Red <");
		assertFalse(game.finished());
	}

	private void isBluePlaying(Linea game) {
		assertEquals(game.getMessageOfTheGame(), "> Playing Blue <");
		assertFalse(game.finished());
	}

	private void testingANotFinishedGameBoard(Linea game, String expectedBoard) {
		assertEquals(game.show(), expectedBoard);
		assertFalse(game.finished());
	}

	private void assertTheCurrentGameState(String expectedMessage, String expectedBoard, Linea game) {
		assertTrue(game.finished());
		assertEquals(game.getMessageOfTheGame(), expectedMessage);
		assertEquals(game.show(), expectedBoard);
	}

	private void movementsThatMakeBlueTokensWinHorizontally(Linea game) {
		game.playRedAt(column1);
		game.playBlueAt(column1);
		game.playRedAt(column2);
		game.playBlueAt(column2);
		game.playRedAt(column3);
		game.playBlueAt(column3);
		game.playRedAt(column1);
		game.playBlueAt(column4);
		game.playRedAt(column2);
		game.playBlueAt(column4);
	}

	private void movementsThatMakeBlueTokensWinDiagonallyUpTheRight(Linea game) {
		game.playRedAt(column6);
		game.playBlueAt(column1);
		game.playRedAt(column2);
		game.playBlueAt(column2);
		game.playRedAt(column3);
		game.playBlueAt(column3);
		game.playRedAt(column4);
		game.playBlueAt(column3);
		game.playRedAt(column4);
		game.playBlueAt(column4);
		game.playRedAt(column5);
		game.playBlueAt(column4);
	}

	private void movementsThatMakeBlueTokensWinDiagonallyDownToTheRight(Linea game) {
		game.playRedAt(column2);
		game.playBlueAt(column2);
		game.playRedAt(column2);
		game.playBlueAt(column2);
		game.playRedAt(column3);
		game.playBlueAt(column3);
		game.playRedAt(column1);
		game.playBlueAt(column3);
		game.playRedAt(column6);
		game.playBlueAt(column4);
		game.playRedAt(column1);
		game.playBlueAt(column5);
		game.playRedAt(column1);
		game.playBlueAt(column4);
	}

	private void movementsThatMakeBlueTokensWinDiagonallyDownToTheLeft(Linea game) {
		game.playRedAt(column6);
		game.playBlueAt(column6);
		game.playRedAt(column6);
		game.playBlueAt(column6);
		game.playRedAt(column5);
		game.playBlueAt(column5);
		game.playRedAt(column4);
		game.playBlueAt(column5);
		game.playRedAt(column5);
		game.playBlueAt(column4);
		game.playRedAt(column1);
		game.playBlueAt(column3);
	}

	private void movementsThatMakeBlueTokensWinDiagonallyUpToTheLeft(Linea game) {
		game.playRedAt(column5);
		game.playBlueAt(column4);
		game.playRedAt(column3);
		game.playBlueAt(column3);
		game.playRedAt(column2);
		game.playBlueAt(column2);
		game.playRedAt(column1);
		game.playBlueAt(column2);
		game.playRedAt(column1);
		game.playBlueAt(column1);
		game.playRedAt(column3);
		game.playBlueAt(column1);
	}

	private static final String redTokensWin = "> Red won <";
	private static final String blueTokensWin = "> Blue won <";

}
