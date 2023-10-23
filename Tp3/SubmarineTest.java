package nemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class SubmarineTest {

	@Test
	public void test00InitializeSubmarineWithDefaultValues() {
		Submarine nemo = new Submarine(ZERO, ZERO, new North());
		assertSubmarinePosition(nemo, ZERO, ZERO, ZERO);
		assertSubmarineDirection(nemo, NORTH);
	}

	@Test
	public void test01SubmarineRespondsCorrectlyToNullCommands() {
		Submarine nemo = new Submarine(ZERO, ZERO, new West());
		submarineProcessesCommands(nemo, NULL_COMMANDS);
		assertSubmarinePosition(nemo, ZERO, ZERO, ZERO);
		assertSubmarineDirection(nemo, WEST);
	}

	@Test
	public void test02SubmarineDescendsCorrectly() {
		Submarine nemo = new Submarine(ZERO, ZERO, new South());
		submarineProcessesCommands(nemo, "d");
		assertSubmarinePosition(nemo, ZERO, ZERO, MINUS_ONE);
		assertSubmarineDirection(nemo, SOUTH);
	}

	@Test
	public void test03SubmarineAscendsCorrectly() {
		Submarine nemo = new Submarine(ZERO, ZERO, new East());
		submarineProcessesCommands(nemo, "du");

		assertSubmarinePosition(nemo, ZERO, ZERO, ZERO);
		assertSubmarineDirection(nemo, EAST);
	}

	@Test
	public void test04SubmarineTurnsRightCorrectly() {
		Submarine nemo = new Submarine(ZERO, ZERO, new North());
		submarineProcessesCommands(nemo, "r");

		assertSubmarineDirection(nemo, EAST);
	}

	@Test
	public void test05SubmarineTurnsLeftCorrectly() {
		Submarine nemo = new Submarine(ZERO, ZERO, new West());
		submarineProcessesCommands(nemo, "l");

		assertSubmarineDirection(nemo, SOUTH);
	}

	@Test
	public void test06SubmarineMovesForwardCorrectly() {
		Submarine nemo = new Submarine(ZERO, ZERO, new North());
		submarineProcessesCommands(nemo, "f");

		assertSubmarinePosition(nemo, ZERO, ONE, ZERO);
	}

	@Test
	public void test07SubmarineLaunchesCapsuleCorrectlyOnSurface() {
		Submarine nemo = new Submarine(ZERO, ZERO, new North());
		submarineProcessesCommands(nemo, "m");

		assertSubmarinePosition(nemo, ZERO, ZERO, ZERO);
		assertSubmarineDirection(nemo, NORTH);
	}

	@Test
	public void test08SubmarineLaunchesCapsuleCorrectlyOnFirstDive() {
		Submarine nemo = new Submarine(ZERO, ZERO, new North());
		submarineProcessesCommands(nemo, "dm");

		assertSubmarinePosition(nemo, ZERO, ZERO, MINUS_ONE);
		assertSubmarineDirection(nemo, NORTH);
	}

	@Test
	public void test09SubmarineDescendsTwiceCorrectly() {
		Submarine nemo = new Submarine(ZERO, ZERO, new North());
		submarineProcessesCommands(nemo, "dd");

		assertSubmarinePosition(nemo, ZERO, ZERO, MINUS_TWO);
		assertSubmarineDirection(nemo, NORTH);
	}

	@Test
	public void test10SubmarineAscendsTwiceCorrectly() {
		Submarine nemo = new Submarine(ZERO, ZERO, new North());
		submarineProcessesCommands(nemo, "dduu");
		assertSubmarinePosition(nemo, ZERO, ZERO, ZERO);
		assertSubmarineDirection(nemo, NORTH);
	}

	@Test
	public void test11SubmarineTurnsRightTwiceCorrectly() {
		Submarine nemo = new Submarine(ZERO, ZERO, new North());
		submarineProcessesCommands(nemo, "rr");
		assertSubmarinePosition(nemo, ZERO, ZERO, ZERO);
		assertSubmarineDirection(nemo, SOUTH);
	}

	@Test
	public void test12SubmarineTurnsLeftTwiceCorrectly() {
		Submarine nemo = new Submarine(ZERO, ZERO, new North());
		submarineProcessesCommands(nemo, "ll");
		assertSubmarinePosition(nemo, ZERO, ZERO, ZERO);
		assertSubmarineDirection(nemo, SOUTH);
	}

	@Test
	public void test13SubmarineTurnsMoveForwardTwiceCorrectly() {
		Submarine nemo = new Submarine(ZERO, ZERO, new North());
		submarineProcessesCommands(nemo, "ff");
		assertSubmarinePosition(nemo, ZERO, TWO, ZERO);
		assertSubmarineDirection(nemo, NORTH);
	}

	@Test
	public void test14SubmarineLaunchesCapsulesTwiceCorrectly() {
		Submarine nemo = new Submarine(ZERO, ZERO, new North());
		submarineProcessesCommands(nemo, "mm");
		assertSubmarinePosition(nemo, ZERO, ZERO, ZERO);
		assertSubmarineDirection(nemo, NORTH);
	}

	@Test
	public void test15SubmarineRespondsToMultipleTurnsCorrectly() {
		Submarine nemo = new Submarine(ZERO, ZERO, new North());
		submarineProcessesCommands(nemo, "rrrlrlr");

		assertSubmarineDirection(nemo, WEST);
	}

	@Test
	public void test16SubmarineRespondsToMultipleSeesawsCorrectly() {
		Submarine nemo = new Submarine(ZERO, ZERO, new North());
		submarineProcessesCommands(nemo, "ddudddu");

		assertSubmarinePosition(nemo, ZERO, ZERO, MINUS_THREE);
		assertSubmarineDirection(nemo, NORTH);
	}

	@Test
	public void test17SubmarineDoesNotAscendOnSurface() {
		Submarine nemo = new Submarine(ZERO, ZERO, new North());
		submarineProcessesCommands(nemo, "uuu");
		assertSubmarinePosition(nemo, ZERO, ZERO, ZERO);
	}

	@Test
	public void test18SubmarineRespondsToMultipleCommandsCorrectly() {
		Submarine nemo = new Submarine(ZERO, ZERO, new North());
		submarineProcessesCommands(nemo, "ffdrffddlluf");

		assertSubmarinePosition(nemo, ONE, TWO, MINUS_TWO);
		assertSubmarineDirection(nemo, WEST);
	}

	@Test
	public void test19SubmarineLaunchesMultipleCapsulesCorrectlyOnSafeAreas() {
		Submarine nemo = new Submarine(ZERO, ZERO, new North());
		submarineProcessesCommands(nemo, "dmmmmummm");

		assertSubmarinePosition(nemo, ZERO, ZERO, ZERO);
		assertSubmarineDirection(nemo, NORTH);
	}

	@Test
	public void test20SubmarineRespondsToMultipleCommandsAndLaunchesMultipleCapsulesCorrectly() {
		Submarine nemo = new Submarine(ZERO, ZERO, new North());
		submarineProcessesCommands(nemo, "dflrddfrluumudmmmu");

		assertSubmarinePosition(nemo, ZERO, TWO, ZERO);
		assertSubmarineDirection(nemo, NORTH);
	}

	@Test
	public void test21SubmarineExplodesAfterLaunchingCapsuleInDangerZone() {
		Submarine nemo = new Submarine(ZERO, ZERO, new North());
		String commands = "ddm";

		assertThrowsLike(() -> nemo.processCommands(commands), DangerZone.TheSubmarineHasBeenDestroyed);
	}

	@Test
	public void test22SubmarineDoesNotContinueProcessingCommandsAfterExploding() {
		Submarine nemo = new Submarine(ZERO, ZERO, new North());
		assertThrowsLike(() -> nemo.processCommands("dddmdlff"), DangerZone.TheSubmarineHasBeenDestroyed);
		assertSubmarinePosition(nemo, ZERO, ZERO, MINUS_THREE);
		assertSubmarineDirection(nemo, NORTH);
	}

	@Test
	public void test23InitializeSubmarineInDifferentLocationCorrectly() {
		Submarine nemo = new Submarine(ONE, ONE, new North());

		assertSubmarinePosition(nemo, ONE, ONE, ZERO);
	}

	@Test
	public void test24InitializeSubmarineInDifferentLocationAndMovesCorrectly() {
		Submarine nemo = new Submarine(THREE, FOUR, new East());
		submarineProcessesCommands(nemo, "ddfffu");
		assertSubmarinePosition(nemo, SIX, FOUR, MINUS_ONE);
	}

	@Test
	public void test25SubmarineInDifferentLocationRespondsDirectionCorrectly() {
		Submarine nemo = new Submarine(TWENTY_FIVE, MINUS_TEN, new South());
		submarineProcessesCommands(nemo, "dlrudr");
		assertSubmarineDirection(nemo, WEST);
	}

	@Test
	public void test26SubmarineInDifferentLocationExplodesAfterLaunchingCapsuleInDangerZone() {
		Submarine nemo = new Submarine(MINUS_SEVEN, TWENTY_SIX, new West());
		assertThrowsLike(() -> nemo.processCommands("dddmmm"), DangerZone.TheSubmarineHasBeenDestroyed);
	}

	@Test
	public void test27SubmarineInDifferentLocationDoesNotProcessCommandsAfterExploding() {
		Submarine nemo = new Submarine(MINUS_SEVEN, TWENTY_SIX, new West());
		assertThrowsLike(() -> nemo.processCommands("ddddmudl"), DangerZone.TheSubmarineHasBeenDestroyed);
		assertSubmarinePosition(nemo, MINUS_SEVEN, TWENTY_SIX, MINUS_FOUR);
		assertSubmarineDirection(nemo, WEST);
	}

	@Test
	public void test28SubamrineRespondsToMultipleCommandInstances() {
		Submarine nemo = new Submarine(ZERO, ZERO, new West());
		submarineProcessesCommands(nemo, "l");
		assertSubmarineDirection(nemo, SOUTH);
		submarineProcessesCommands(nemo, "l");
		assertSubmarineDirection(nemo, EAST);
		submarineProcessesCommands(nemo, "r");
		assertSubmarineDirection(nemo, SOUTH);
	}

	private void assertSubmarinePosition(Submarine nemo, int expectedX, int expectedY, int expectedZ) {
		assertEquals(expectedX, nemo.getXPosition());
		assertEquals(expectedY, nemo.getYPosition());
		assertEquals(expectedZ, nemo.getZPosition());
	}

	private void assertSubmarineDirection(Submarine nemo, String expectedDirection) {
		assertEquals(expectedDirection, nemo.getDirection());
	}

	private void submarineProcessesCommands(Submarine nemo, String commands) {
		nemo.processCommands(commands);
	}

	private void assertThrowsLike(Executable executable, String message) {
		assertEquals(message, assertThrows(Error.class, executable).getMessage());
	}

	private static final String NORTH = "NORTH";
	private static final String WEST = "WEST";
	private static final String SOUTH = "SOUTH";
	private static final String EAST = "EAST";
	private static final int TWENTY_SIX = 26;
	private static final int TWENTY_FIVE = 25;
	private static final int MINUS_TEN = -10;
	private static final int MINUS_FOUR = -4;
	private static final int MINUS_SEVEN = -7;
	private static final int SIX = 6;
	private static final int FOUR = 4;
	private static final int THREE = 3;
	private static final int MINUS_TWO = -2;
	private static final int TWO = 2;
	private static final int MINUS_THREE = -3;
	private static final int ONE = 1;
	private static final int MINUS_ONE = -1;
	private static final String NULL_COMMANDS = "";
	private static final int ZERO = 0;

}