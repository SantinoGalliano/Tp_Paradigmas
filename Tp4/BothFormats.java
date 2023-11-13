package linea;

public class BothFormats extends WinningFormat {

	private Character key = 'C';

	public boolean isThere4ConsecutiveTokens(String colour, int column, int row, Linea line) {
		return (checkFourTokensVertically(colour, column, line) || checkFourTokensHorizontally(colour, row, line))
				|| checkFourTokensDiagonally(colour, column - 1, row, line);
	}

	public boolean applies(Character triumphVariant) {
		return key == triumphVariant;
	}

	public Character getKey() {
		return key;
	}

}

