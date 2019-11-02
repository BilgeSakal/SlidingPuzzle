package ui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import game.Point;
import game.SlidingPuzzle;

public class GameWindow {

	private JFrame gameWindow;

	private SlidingPuzzle game;

	private TileButton[][] tileButtons;

	private JPanel tilePanel;

	private static final int ROW = 3;
	private static final int COL = 3;

	BufferedImage image;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				GameWindow gameWindow = new GameWindow(ROW, COL);
				gameWindow.newGame();
			}
		});
	}

	public GameWindow(int row, int col) {
		readImage();
	}

	/**
	 * Creates and shows the frame.
	 */
	public void createAndShowUI() {
		initFrame();
	}

	/**
	 * Reads the image.
	 */
	private void readImage() {
		try {
			image = ImageIO.read(GameWindow.class.getResource("/farm.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Starts a new game.
	 */
	public void newGame() {
		game = new SlidingPuzzle(ROW, COL, image, this);
		game.startGame();
		createAndShowUI();
	}

	/**
	 * Inıtiates the frame.
	 */
	public void initFrame() {
		initGameWindow();
		initTilePanel();
		gameWindow.pack();
		setGameWindowLocation();
		gameWindow.setVisible(true);
	}

	private void initGameWindow() {
		gameWindow = new JFrame("Sliding Puzzle v1.0");
		gameWindow.setResizable(false);
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.setLayout(new BoxLayout(gameWindow.getContentPane(), BoxLayout.PAGE_AXIS));
	}

	private void initTilePanel() {
		tilePanel = new JPanel();
		tilePanel.setLayout(new GridLayout(ROW, COL));
		initTileButtons();
		gameWindow.add(tilePanel);
	}

	private void initTileButtons() {
		tileButtons = new TileButton[ROW][COL];
		for (int i = 0; i < ROW; ++i) {
			for (int j = 0; j < COL; ++j) {
				Point p = new Point(i, j);
				if (game == null)
					System.out.println("game");
				if (game.getCurrMove() == null)
					System.out.println("curr");
				if (game.getCurrMove().getTile(p) == null)
					System.out.println("tile");
				TileButton tileButton = new TileButton(game.getCurrMove().getTile(p),
						SlidingPuzzle.BUTTON_PANEL_SIZE / COL, SlidingPuzzle.BUTTON_PANEL_SIZE / ROW);
				tileButton.addActionListener(new TileButtonActionListener(this, tileButton));
				game.getCurrMove().getTile(p).setTileButton(tileButton);
				tileButtons[i][j] = tileButton;
				tilePanel.add(tileButton);
			}
		}
	}

	/**
	 * Places the main window in the middle of the screen.
	 */
	private void setGameWindowLocation() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		gameWindow.setLocation((int) ((dim.width >> 1) - ((int) gameWindow.getSize().getWidth() >> 1)),
				(int) ((dim.height >> 1) - ((int) gameWindow.getSize().getHeight() >> 1)));
	}

	public SlidingPuzzle getGame() {
		return game;
	}

}
