package linea;

public class HorizontalAndVertical extends WinningFormat {

	private Character key = 'A';

	public boolean isThere4ConsecutiveTokens(String colour, int column, int row, Linea line) {
		return checkFourTokensVertically(colour, column, line) || checkFourTokensHorizontally(colour, row, line);
	}

	public boolean applies(Character triumphVariant) {
		return key == triumphVariant;
	}

	public Character getKey() {
		return key;
	}

}
