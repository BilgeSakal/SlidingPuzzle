package ui;

import java.awt.Dimension;
import java.awt.EventQueue;
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

public class GameWindow {

	public static final int BUTTON_PANEL_SIZE = 600;
	private static final int MENU_ITEM_SIZE = 30;

	private static final int ROW = 3;
	private static final int COL = 3;

	private JFrame gameWindow;
	private SlidingPuzzle game;
	private TileButton[][] tileButtons;
	private JPanel tilePanel;
	private ArrayList<BufferedImage> images;
	private ArrayList<String> imageNames = new ArrayList<String>(Arrays.asList("farm.jpg", "cn.jpeg", "marvel.jpeg"));

	private JMenuBar menuBar;

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

		JMenu newGameMenu = new JMenu("New Game");

		JMenuItem customImageItem = new JMenuItem("Custom Image");
		GameWindow gw = this;
		customImageItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ImageSelectFrame imgSelect = new ImageSelectFrame(gw);
				imgSelect.createAndShowUI();
			}
		});

		initDefaultImages(newGameMenu);
		newGameMenu.add(customImageItem);

		menuBar.add(newGameMenu);

		gameWindow.setJMenuBar(menuBar);
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

	public void newGame(BufferedImage img) {
		if (gameWindow != null) {
			gameWindow.dispose();
		}
		game = new SlidingPuzzle(ROW, COL, img, this);
		game.startGame();
		createAndShowUI();
	}

	/**
	 * Starts a new game.
	 */
	public void newGame() {
		newGame(images.get(0));
	}

	/**
	 * Inıtiates the frame.
	 */
	public void initFrame() {
		initGameWindow();
		initMenuBar();
		initTilePanel();
		gameWindow.pack();
		setGameWindowLocation();
		gameWindow.setVisible(true);
	}

	private void initGameWindow() {
		gameWindow = new JFrame("Sliding Puzzle " + SlidingPuzzle.VERSION);
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
				TileButton tileButton = new TileButton(game.getCurrMove().getTile(p), BUTTON_PANEL_SIZE / COL,
						BUTTON_PANEL_SIZE / ROW);
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
		disableButtons();
		showFinishedInfo();
	}

	/**
	 * Shows game finished info.
	 */
	private void showFinishedInfo() {
		JOptionPane.showMessageDialog(gameWindow,
				"Congratulations! You finished in " + game.getMoveCount() + " moves.");
	}

	/**
	 * Disables all the tile buttons.
	 */
	private void disableButtons() {
		for (int i = 0; i < tileButtons.length; ++i) {
			for (int j = 0; j < tileButtons[i].length; ++j) {
				tileButtons[i][j].setEnabled(false);
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
