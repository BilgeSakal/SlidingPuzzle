package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Move {

	private Tile[][] puzzleTable;

	/**
	 * Location of the empty tile
	 */
	private Point emptyTileLocation;

	public Move(Tile[][] puzzleTable, Point emptyTileLocation) {
		this.puzzleTable = puzzleTable;
		this.emptyTileLocation = emptyTileLocation;
	}

	/**
	 * Moves the <i> empty tile </i> in the given direction.
	 * 
	 * @param dir to be moved.
	 * @return {@code true} if {@code emptyTile} moved in the given direction,
	 *         {@code false} otherwise.
	 */
	public boolean moveEmptyTile(int dir) {
		Tile moveTile = null;

		int emptyX = emptyTileLocation.getX();
		int emptyY = emptyTileLocation.getY();

		switch (dir) {
		case SlidingPuzzle.UP:
			if (emptyX > 0) {
				moveTile = puzzleTable[emptyX - 1][emptyY];
			}
			break;
		case SlidingPuzzle.RIGHT:
			if (emptyY < puzzleTable[0].length - 1) {
				moveTile = puzzleTable[emptyX][emptyY + 1];
			}
			break;
		case SlidingPuzzle.DOWN:
			if (emptyX < puzzleTable.length - 1) {
				moveTile = puzzleTable[emptyX + 1][emptyY];
			}
			break;
		case SlidingPuzzle.LEFT:
			if (emptyY > 0) {
				moveTile = puzzleTable[emptyX][emptyY - 1];
			}
			break;
		default:
			return false;
		}
		if (moveTile != null && swapEmptyTile(moveTile)) {
			return true;
		}
		return false;
	}

	/**
	 * Swaps the empty tile with {@code t}
	 * 
	 * @param t tile to be swapped with <i>empty tile</i>.
	 * @return {@code true} if swapping is successful, {@code false} otherwise.
	 */
	public boolean swapEmptyTile(Tile t) {
		if (isAdjacentEmptyTile(t)) {

			Tile emptyTile = getTile(emptyTileLocation);

			emptyTile.setIdentifier(t.getIdentifier());
			emptyTile.setImg(t.getImg());

			t.setIdentifier(SlidingPuzzle.EMPTY_TILE_IDENTIFIER);
			t.setImg(null);

			emptyTileLocation = t.getLocation();

			return true;
		}
		return false;
	}

	/**
	 * Returns the tile which is at {@code location}
	 * 
	 * @param location of the tile
	 * @return tile which is at {@code location}
	 */
	public Tile getTile(Point location) {
		return puzzleTable[location.getX()][location.getY()];
	}

	/**
	 * Checks if the <i>empty tile</i> is adjacent to the <b>{@code tile}</b>.
	 * 
	 * @param tile
	 * @return {@code true} if the tile is adjacent to the <i>empty tile</i>,
	 *         {@code false} otherwise.
	 */
	private boolean isAdjacentEmptyTile(Tile tile) {
		int tileX = tile.getLocation().getX();
		int tileY = tile.getLocation().getY();

		int emptyX = emptyTileLocation.getX();
		int emptyY = emptyTileLocation.getY();

		if (!tile.getLocation().equals(emptyTileLocation)) {
			int sum = Math.abs(tileX - emptyX) + Math.abs(tileY - emptyY);
			if (sum == 1)
				return true;
		}
		return false;
	}

	public List<Integer> possibleDirections() {
		List<Integer> possDirs = new ArrayList<Integer>(2);

		int emptyX = emptyTileLocation.getX();
		int emptyY = emptyTileLocation.getY();

		if (emptyX > 0)
			possDirs.add(SlidingPuzzle.UP);
		if (emptyX < puzzleTable.length - 1)
			possDirs.add(SlidingPuzzle.DOWN);
		if (emptyY > 0)
			possDirs.add(SlidingPuzzle.LEFT);
		if (emptyY < puzzleTable[0].length)
			possDirs.add(SlidingPuzzle.RIGHT);

		return possDirs;
	}
	
	public static int findMovedDirection(Move m1, Move m2) {
		int m1x = m1.getEmptyTileLocation().getX();
		int m1y = m1.getEmptyTileLocation().getY();

		int m2x = m2.getEmptyTileLocation().getX();
		int m2y = m2.getEmptyTileLocation().getY();

		if (m1x > m2x) {
			return SlidingPuzzle.UP;
		} else if (m2x > m1x) {
			return SlidingPuzzle.DOWN;
		} else if (m1y > m2y) {
			return SlidingPuzzle.LEFT;
		} else {
			return SlidingPuzzle.RIGHT;
		}

	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Move) {
			Move m = (Move) obj;
			if (MatrixManipulation.matrixEqual(m.puzzleTable, puzzleTable))
				return true;
			return false;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		int[] hash = new int[puzzleTable.length * puzzleTable[0].length];
		for (int i = 0; i < puzzleTable.length; ++i) {
			for (int j = 0; j < puzzleTable[i].length; ++j) {
				hash[i * puzzleTable[i].length + j] = puzzleTable[i][j].getIdentifier();
			}
		}
		return Arrays.hashCode(hash);
	}

	public Point getEmptyTileLocation() {
		return emptyTileLocation;
	}
	
	public Tile[][] getPuzzleTable() {
		return puzzleTable;
	}

}
