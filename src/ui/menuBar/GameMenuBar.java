package ui.menuBar;

import javax.swing.JMenuBar;

import ui.GameWindow;

public class GameMenuBar extends JMenuBar {

	private static final long serialVersionUID = -4081970271083949377L;

	private GameWindow gameWindow;

	private NewGameMenu newGameMenu;
	private SizeMenu sizeMenu;

	public GameMenuBar(GameWindow gameWindow) {
		this.gameWindow = gameWindow;
		initMenuBar();
	}

	private void initMenuBar() {
		newGameMenu = new NewGameMenu(gameWindow);
		sizeMenu = new SizeMenu(gameWindow);
		
		add(newGameMenu);
		add(sizeMenu);
	}

}
