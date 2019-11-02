package game;

import java.util.Arrays;

public class MatrixManipulation {

	public static boolean matrixEqual(Tile[][] a, Tile[][] b) {
		// check length
		if (a.length != b.length || a[0].length != b[0].length)
			return false;

		for (int i = 0; i < a.length; i++) {
			if (!Arrays.equals(a[i], b[i]))
				return false;
		}
		return true;
	}

	public static void printMatrix(Tile[][] a) {
		for (int i = 0; i < a.length; ++i) {
			for (int j = 0; j < a[i].length; ++j) {
				System.out.printf("%02d ", a[i][j].getIdentifier());
			}
			System.out.println();
		}
	}

	public static Tile[][] copyMatrix(Tile[][] a) {
		Tile[][] result = new Tile[a.length][a[0].length];

		for (int i = 0; i < a.length; ++i) {
			for (int j = 0; j < a[i].length; ++j) {
				result[i][j] = a[i][j];
			}
		}

		return result;
	}
	
	public static Tile findTileFromPoint(Tile[][] tile, Point point) {
		for (int i = 0; i < tile.length; ++i) {
			for (int j = 0; j < tile[i].length; ++j) {
				if (tile[i][j].getLocation().getX() == point.getY() && tile[i][j].getLocation().getY() == point.getY()) {
					return tile[i][j];
				}
			}
		}
		return null;
	}

}
