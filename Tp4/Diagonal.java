package linea;

public class Diagonal extends WinningFormat {

	private Character key = 'B';

	public boolean isThere4ConsecutiveTokens(String colour, int column, int row, Linea line) {
		return checkFourTokensDiagonally(colour, column - 1, row, line);
	}

	public boolean applies(Character triumphVariant) {
		return key == triumphVariant;
	}

	public Character getKey() {
		return key;
	}

}
