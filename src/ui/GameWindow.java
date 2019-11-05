package ui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import game.Point;
import game.SlidingPuzzle;
import ui.input.RowColReader;
import ui.output.MoveCountPanel;
import ui.output.TimePanel;

public class GameWindow {

	public static final int BUTTON_PANEL_SIZE = 600;
	private static final int MENU_ITEM_SIZE = 30;

	private static final int DEFAULT_ROW = 3;
	private static final int DEFAULT_COL = 3;

	private JFrame gameFrame;
	private SlidingPuzzle game;
	private TileButton[][] tileButtons;

	private ArrayList<BufferedImage> images;
	private ArrayList<String> imageNames = new ArrayList<String>(Arrays.asList("farm.jpg", "cn.jpeg", "marvel.jpeg"));
	private BufferedImage currImage;

	private JPanel tilePanel;
	private JPanel upperPanel;

	private TimePanel timePanel;
	private MoveCountPanel moveCountPanel;

	private BufferedImage questionMarkImage;
	private BufferedImage resetImage;

	private int row;
	private int col;

	private JMenuBar menuBar;

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

	/**
	 * Creates and shows the frame.
	 */
	public void createAndShowUI() {
		initFrame();
	}

	private void initMenuBar() {
		menuBar = new JMenuBar();

		initNewGameMenu(menuBar);
		initSizeMenu(menuBar);

		gameFrame.setJMenuBar(menuBar);
	}

	private void initNewGameMenu(JMenuBar menuBar) {
		JMenu newGameMenu = new JMenu("New Game");

		JMenuItem customImageItem = new JMenuItem("Custom Image");
		customImageItem
				.setIcon(new ImageIcon(ImageManipulation.resize(questionMarkImage, MENU_ITEM_SIZE, MENU_ITEM_SIZE)));

		GameWindow gw = this;
		customImageItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ImageSelectFrame imgSelect = new ImageSelectFrame(gw);
				imgSelect.createAndShowUI();
			}
		});
		JMenuItem resetItem = new JMenuItem("Reset");
		resetItem.setIcon(new ImageIcon(ImageManipulation.resize(resetImage, MENU_ITEM_SIZE, MENU_ITEM_SIZE)));
		resetItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetGame();
			}
		});

		initDefaultImages(newGameMenu);
		newGameMenu.add(customImageItem);
		newGameMenu.add(resetItem);
		menuBar.add(newGameMenu);
	}

	private void initSizeMenu(JMenuBar menuBar) {
		JMenu sizeMenu = new JMenu("Size");

		for (int i = 3; i <= 5; ++i) {
			JMenuItem item = new JMenuItem(i + "x" + i);
			GameWindow gw = this;
			int size = i;
			item.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					newGame(size, size, gw.currImage);
				}
			});
			sizeMenu.add(item);
		}
		JMenuItem customSize = new JMenuItem("Custom Size");
		GameWindow gw = this;
		customSize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RowColReader rowColReader = new RowColReader(gw);
				rowColReader.createAndShowUI();
			}
		});
		sizeMenu.add(customSize);

		menuBar.add(sizeMenu);

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
			resetImage = ImageIO.read(GameWindow.class.getResource("/reset.png"));
			questionMarkImage = ImageIO.read(GameWindow.class.getResource("/questionmark.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initDefaultImages(JMenu menu) {
		for (int i = 0; i < images.size(); ++i) {
			String name = imageNames.get(i);
			JMenuItem item = new JMenuItem(name.substring(0, name.indexOf('.')).toUpperCase());
			BufferedImage img = images.get(i);
			item.setIcon(new ImageIcon(ImageManipulation.resize(img, MENU_ITEM_SIZE, MENU_ITEM_SIZE)));
			item.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					newGame(img);
				}
			});
			menu.add(item);
		}
	}

	private void newGame(BufferedImage img) {
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

	private void newGame(int row, int col, BufferedImage img) {
		if (gameFrame != null) {
			gameFrame.dispose();
			game.getTimer().terminate();
		}
		this.row = row;
		this.col = col;
		currImage = img;
		game = new SlidingPuzzle(row, col, img, this);
		timePanel = new TimePanel();
		moveCountPanel = new MoveCountPanel();
		game.getTimer().setTimePanel(timePanel);
		game.setMoveCountPanel(moveCountPanel);
		game.startGame();
		createAndShowUI();
		game.getTimer().start();
	}

	/**
	 * Inıtiates the frame.
	 */
	public void initFrame() {
		initGameWindow();
		initMenuBar();
		initUpperPanel();
		initTilePanel();
		gameFrame.pack();
		setGameWindowLocation();
		gameFrame.setVisible(true);
	}

	private void initGameWindow() {
		gameFrame = new JFrame("Sliding Puzzle " + SlidingPuzzle.VERSION);
		gameFrame.setResizable(false);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setLayout(new BoxLayout(gameFrame.getContentPane(), BoxLayout.PAGE_AXIS));
	}

	private void initTilePanel() {
		tilePanel = new JPanel();
		tilePanel.setLayout(new GridLayout(row, col));
		initTileButtons();
		gameFrame.add(tilePanel);
	}

	private void initUpperPanel() {
		upperPanel = new JPanel();
		upperPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		upperPanel.add(timePanel);
		upperPanel.add(Box.createRigidArea(new Dimension(50, 0)));
		upperPanel.add(moveCountPanel);

		gameFrame.add(upperPanel);
	}

	private void initTileButtons() {
		tileButtons = new TileButton[row][col];
		for (int i = 0; i < row; ++i) {
			for (int j = 0; j < col; ++j) {
				Point p = new Point(i, j);
				TileButton tileButton = new TileButton(game.getCurrMove().getTile(p), BUTTON_PANEL_SIZE / col,
						BUTTON_PANEL_SIZE / row);
				tileButton.addActionListener(new TileButtonActionListener(this, tileButton));
				game.getCurrMove().getTile(p).setTileButton(tileButton);
				tileButtons[i][j] = tileButton;
				tilePanel.add(tileButton);
			}
		}
	}

	/**
	 * Called when the game is finished.
	 */
	public void finishGame() {
		game.getTimer().terminate();
		disableButtons();
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
	 * Disables all the tile buttons.
	 */
	private void disableButtons() {
		for (int i = 0; i < tileButtons.length; ++i) {
			for (int j = 0; j < tileButtons[i].length; ++j) {
				TileButton btn = tileButtons[i][j];
				// remove the action listener so the button does not do anything on click
				btn.removeActionListener(btn.getActionListeners()[0]);
			}
		}
	}

	private void resetGame() {
		newGame(row, col);
	}

	/**
	 * Places the main window in the middle of the screen.
	 */
	private void setGameWindowLocation() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		gameFrame.setLocation((int) ((dim.width >> 1) - ((int) gameFrame.getSize().getWidth() >> 1)),
				(int) ((dim.height >> 1) - ((int) gameFrame.getSize().getHeight() >> 1)));
	}

	public SlidingPuzzle getGame() {
		return game;
	}

	public JFrame getGameFrame() {
		return gameFrame;
	}

	private class ImageSelectFrame {

		private GameWindow gameWindow;

		private BufferedImage selectedFile;

		public ImageSelectFrame(GameWindow gameWindow) {
			this.gameWindow = gameWindow;
		}

		private void initFrame() {
			FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image files",
					ImageIO.getReaderFileSuffixes());
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());
			fileChooser.addChoosableFileFilter(imageFilter);
			int input = fileChooser.showOpenDialog(null);
			if (input == JFileChooser.APPROVE_OPTION) {
				File selected = fileChooser.getSelectedFile();
				try {
					selectedFile = ImageIO.read(selected);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (selectedFile != null)
					gameWindow.newGame(selectedFile);
			}
		}

		public void createAndShowUI() {
			initFrame();
		}

	}

}
