package ui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import game.SlidingPuzzle;
import ui.menuBar.GameMenuBar;
import ui.panel.GamePanel;

public class GameWindow {

	public static final int BUTTON_PANEL_SIZE = 600;
	public static final int MENU_ITEM_SIZE = 30;
	public static final int SMALL_IMAGE_SIZE = 200;
	public static final int DEFAULT_IMAGE_COUNT = 3;
	public static final int MARGIN = 5;

	private static final int DEFAULT_ROW = 3;
	private static final int DEFAULT_COL = 3;

	private JFrame gameFrame;
	private SlidingPuzzle game;

	private ArrayList<BufferedImage> images;
	private ArrayList<String> imageNames = new ArrayList<String>(
			Arrays.asList("farm.jpg", "cn.jpeg", "marvel.jpeg", "reset.png", "questionmark.png"));
	private BufferedImage currImage;

	private GamePanel gamePanel;

	private GameMenuBar gameMenuBar;

	private int currRow;
	private int currCol;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				GameWindow gameWindow = new GameWindow();
				gameWindow.newGame();
			}
		});
	}

	public GameWindow() {
		readImages();
	}

//	private void initSearchPanel() {
//		searchPanel = new JPanel();
//		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.PAGE_AXIS));
//
//		JPanel smallImagePanel = new JPanel() {
//			@Override
//			protected void paintComponent(Graphics g) {
//				super.paintComponent(g);
//				g.drawImage(ImageManipulation.resize(currImage, 200, 200), 0, 0, this);
//			}
//		};
//		smallImagePanel.setPreferredSize(new Dimension(200, 200));
//		searchPanel.add(smallImagePanel);
//
//		gameFrame.add(Box.createRigidArea(new Dimension(10, 0)));
//		gameFrame.add(searchPanel);
//	}

	/**
	 * Creates and shows the frame.
	 */
	public void createAndShowUI() {
		initFrame();
	}

	/**
	 * Inıtiates the frame.
	 */
	public void initFrame() {
		gameFrame = new JFrame("Sliding Puzzle " + SlidingPuzzle.VERSION);
		gameFrame.setResizable(false);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setLayout(new BoxLayout(gameFrame.getContentPane(), BoxLayout.LINE_AXIS));

		gameMenuBar = new GameMenuBar(this);
		gameFrame.setJMenuBar(gameMenuBar);

		gamePanel = new GamePanel(game);

		gameFrame.add(gamePanel);
		gameFrame.pack();
		setFrameLocation();
		gameFrame.setVisible(true);
	}

	/**
	 * Reads the image.
	 */
	private void readImages() {
		try {
			images = new ArrayList<BufferedImage>();
			for (String fileName : imageNames) {
				images.add(ImageIO.read(GameWindow.class.getResource("/" + fileName)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void newGame(BufferedImage img) {
		newGame(DEFAULT_ROW, DEFAULT_COL, img);
	}

	/**
	 * Starts a new game.
	 */
	private void newGame() {
		newGame(DEFAULT_ROW, DEFAULT_COL, images.get(0));
	}

	public void newGame(int row, int col) {
		newGame(row, col, currImage);
	}

	public void newGame(int row, int col, BufferedImage img) {
		if (gameFrame != null) {
			gameFrame.dispose();
			game.getTimer().terminate();
		}
		currImage = img;
		currRow = row;
		currCol = col;
		game = new SlidingPuzzle(row, col, img, this);
		game.startGame();
		createAndShowUI();
		game.getTimer().start();
	}

	/**
	 * Called when the game is finished.
	 */
	public void finishGame() {
		gamePanel.getTilePanel().disableButtons();
		showFinishedInfo();
	}

	/**
	 * Shows game finished info.
	 */
	private void showFinishedInfo() {
		JOptionPane.showMessageDialog(gameFrame, "Congratulations! You finished in " + game.getMoveCount()
				+ " moves and " + game.getTimer().getTime() + " seconds.");
	}

	/**
	 * Restarts the game with the previous row, column and image.
	 */
	public void resetGame() {
		newGame(currRow, currCol);
	}

	/**
	 * Places the main window in the middle of the screen.
	 */
	private void setFrameLocation() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		gameFrame.setLocation((int) ((dim.width >> 1) - ((int) gameFrame.getSize().getWidth() >> 1)),
				(int) ((dim.height >> 1) - ((int) gameFrame.getSize().getHeight() >> 1)));
	}

	// Getters and Setters

	public SlidingPuzzle getGame() {
		return game;
	}

	public JFrame getGameFrame() {
		return gameFrame;
	}

	public ArrayList<BufferedImage> getImages() {
		return images;
	}

	public ArrayList<String> getImageNames() {
		return imageNames;
	}

	public BufferedImage getCurrImage() {
		return currImage;
	}

}
