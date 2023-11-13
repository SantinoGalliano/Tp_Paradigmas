package linea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class WinningFormat {

	private static ArrayList<WinningFormat> formats = new ArrayList<>(
			Arrays.asList(new HorizontalAndVertical(), new Diagonal(), new BothFormats()));

	public abstract boolean applies(Character triumphVariant);

	public abstract boolean isThere4ConsecutiveTokens(String colour, int column, int row, Linea line);
	
	public abstract Character getKey();

	public static WinningFormat strategyFor(char triumphVariant) {
		return formats.stream().filter(format -> (format.applies(triumphVariant))).findFirst()
				.orElseThrow(() -> new RuntimeException(Linea.invalidWinningFormat));
	}

	protected boolean checkFourTokensVertically(String color, int column, Linea linea) {
		ArrayList<String> columnList = linea.getTablero().get(column - 1);

		return IntStream.range(0, columnList.size() - 3)
				.mapToObj(i -> columnList.subList(i, i + 4).stream().allMatch(token -> token.equals(color)))
				.anyMatch(hasFourConsecutive -> hasFourConsecutive);
	}

	protected boolean checkFourTokensHorizontally(String color, int row, Linea linea) {
		ArrayList<String> rowList = getRow(row, linea);

		return IntStream.range(0, rowList.size() - 3).anyMatch(startIndex -> rowList.subList(startIndex, startIndex + 4)
				.stream().allMatch(token -> token.equals(color)));
	}

	protected boolean checkFourTokensDiagonally(String color, int column, int row, Linea linea) {
		return IntStream.rangeClosed(-1, 1).boxed().flatMap(
				j -> IntStream.rangeClosed(-1, 1).filter(s -> j != 0 && s != 0).mapToObj(s -> new int[] { j, s }))
				.anyMatch(direction -> {
					int dx = direction[0];
					int dy = direction[1];

					long consecutiveCount = IntStream.rangeClosed(-3, 3)
							.mapToObj(i -> new int[] { column + i * dx, row + i * dy })
							.filter(coords -> isInsideBounds(coords[0], coords[1], linea)
									&& linea.getTablero().get(coords[0]).size() > coords[1]
									&& linea.getTablero().get(coords[0]).get(coords[1]).equals(color))
							.count();

					return consecutiveCount == 4;
				});

	}

	private ArrayList<String> getRow(int row, Linea linea) {
		return IntStream.range(0, linea.getBase())
				.mapToObj(columna -> linea.getTablero().get(columna).stream().skip(row).findFirst().orElse("0"))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	private boolean isInsideBounds(int column, int row, Linea line) {
		return column >= 0 && column < line.getBase() && row >= 0 && row < line.getAltura();
	}
}
