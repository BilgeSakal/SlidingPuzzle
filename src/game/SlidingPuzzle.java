package game;

import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.ImageIcon;

import ui.GameWindow;
import ui.ImageManipulation;
import ui.panel.MoveCountPanel;

public class SlidingPuzzle {

	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;

	public static final int EMPTY_TILE_IDENTIFIER = 0;
	public static final int SHUFFLE_COEFFCIENT = 1000;

	public static final String VERSION = "v2.0.0";

	private int row;
	private int col;

	private int moveCount = 0;

	private Move currMove;
	private Move goal;

	private BufferedImage img;

	private GameWindow gameFrame;
	private Timer timer = new Timer();
	private MoveCountPanel moveCountPanel;

	public SlidingPuzzle(int row, int col, BufferedImage img, GameWindow gameFrame) {
		setRow(row);
		setCol(col);
		setImg(img);
		setGameFrame(gameFrame);
		setMoveCountPanel(new MoveCountPanel());
	}

	/**
	 * Starts the game.
	 */
	public void startGame() {
		initPuzzleTable();
		shuffle(SHUFFLE_COEFFCIENT);
	}

	public void finishGame() {
		timer.terminate();
		gameFrame.finishGame();
	}

	/**
	 * Initiates the puzzle table in order.
	 */
	private void initPuzzleTable() {
		Tile[][] puzzleTable = new Tile[row][col];
		BufferedImage[][] images = ImageManipulation.parcelImage(img, row, col, GameWindow.BUTTON_PANEL_SIZE,
				GameWindow.BUTTON_PANEL_SIZE);

		for (int i = 0; i < getRow(); i++) {
			for (int j = 0; j < getCol(); j++) {
				puzzleTable[i][j] = new Tile(i * getCol() + j, new Point(i, j), new ImageIcon(images[i][j]));
				if (i == 0 && j == 0)
					puzzleTable[i][j].setImg(null);
				else
					puzzleTable[i][j].setImg(new ImageIcon(images[i][j]));

			}
		}
		setCurrMove(new Move(MatrixManipulation.copyMatrix(puzzleTable), new Point(0, 0)));
		setGoal(new Move(MatrixManipulation.copyMatrix(puzzleTable), new Point(0, 0)));
	}

	/**
	 * Checks if the game is finished or not.
	 * 
	 * @return {@code true} if the game is finished, {@code false} otherwise.
	 */
	public boolean isFinished() {
		return currMove.equals(goal);
	}

	/**
	 * Shuffles the puzzle.
	 * 
	 * @param shuffleCoefficient coefficient.
	 */
	public void shuffle(int shuffleCoefficient) {
		Random rndm = new Random();
		for (int i = 0; i < getShuffleCount(shuffleCoefficient); ++i) {
			int rdIndex = rndm.nextInt(4);
			currMove.moveEmptyTile(rdIndex);
		}
	}

	/**
	 * Increments <i> moveCount </i> by one.
	 */
	private void incMoveCount() {
		++moveCount;
		if (moveCountPanel != null)
			moveCountPanel.updateMoveCount(moveCount);
	}

	/**
	 * Moves the tile to the empty tile if the empty tile is adjacent.
	 * 
	 * @param tile to be moved.
	 * @return {@code true} if empty tile is adjacent and they are swapped,
	 *         {@code false} otherwise.
	 */
	public boolean moveTile(Tile tile) {
		if (currMove.swapEmptyTile(tile)) {
			incMoveCount();
			return true;
		}
		return false;
	}

	/**
	 * Calculates how many times the puzzle will be shuffled.
	 * 
	 * @param shuffleCoefficient coefficient.
	 * @return shuffle count.
	 */
	private int getShuffleCount(int shuffleCoefficient) {
		int tileCount = getCol() * getRow();
		return tileCount * shuffleCoefficient;
	}

	// getters and setters

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public Move getGoal() {
		return goal;
	}

	public void setGoal(Move goal) {
		this.goal = goal;
	}

	public void setCurrMove(Move currMove) {
		this.currMove = currMove;
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	public GameWindow getGameFrame() {
		return gameFrame;
	}

	public void setGameFrame(GameWindow gameFrame) {
		this.gameFrame = gameFrame;
	}

	public int getMoveCount() {
		return moveCount;
	}

	public Move getCurrMove() {
		return currMove;
	}

	public Timer getTimer() {
		return timer;
	}

	public MoveCountPanel getMoveCountPanel() {
		return moveCountPanel;
	}

	public void setMoveCountPanel(MoveCountPanel moveCountPanel) {
		this.moveCountPanel = moveCountPanel;
	}

}
