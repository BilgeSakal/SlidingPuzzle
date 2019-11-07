package ui.menuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import ui.GameWindow;
import ui.ImageManipulation;
import ui.input.ImageSelector;

public class NewGameMenu extends JMenu {

	private static final long serialVersionUID = 1640836091167300784L;

	private GameWindow gameWindow;
	private ArrayList<String> imageNames;
	private ArrayList<BufferedImage> images;

	public NewGameMenu(GameWindow gameWindow) {
		this.gameWindow = gameWindow;
		images = gameWindow.getImages();
		imageNames = gameWindow.getImageNames();
		setText("New Game");
		initMenu();
	}

	private void initMenu() {
		JMenuItem customImageItem = new JMenuItem("Custom Image");
		customImageItem.setIcon(new ImageIcon(ImageManipulation.resize(images.get(GameWindow.DEFAULT_IMAGE_COUNT),
				GameWindow.MENU_ITEM_SIZE, GameWindow.MENU_ITEM_SIZE)));

		customImageItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ImageSelector imgSelector = new ImageSelector(gameWindow);
				imgSelector.selectImaage();
			}
		});
		JMenuItem resetItem = new JMenuItem("Reset");
		resetItem.setIcon(new ImageIcon(ImageManipulation.resize(images.get(GameWindow.DEFAULT_IMAGE_COUNT + 1),
				GameWindow.MENU_ITEM_SIZE, GameWindow.MENU_ITEM_SIZE)));
		resetItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameWindow.resetGame();
			}
		});

		initDefaultImageMenuItems();
		add(customImageItem);
		add(resetItem);
	}

	private void initDefaultImageMenuItems() {
		for (int i = 0; i < GameWindow.DEFAULT_IMAGE_COUNT; ++i) {
			String name = imageNames.get(i);
			JMenuItem item = new JMenuItem(name.substring(0, name.indexOf('.')).toUpperCase());
			BufferedImage img = images.get(i);
			item.setIcon(
					new ImageIcon(ImageManipulation.resize(img, GameWindow.MENU_ITEM_SIZE, GameWindow.MENU_ITEM_SIZE)));
			item.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					gameWindow.newGame(img);
				}
			});
			add(item);
		}
	}

}
