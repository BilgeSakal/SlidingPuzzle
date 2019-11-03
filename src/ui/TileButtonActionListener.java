package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import game.SlidingPuzzle;

public class TileButtonActionListener implements ActionListener {

	private GameWindow gameWindow;
	private TileButton tileButton;

	public TileButtonActionListener(GameWindow gameFrame, TileButton tileButton) {
		this.gameWindow = gameFrame;
		this.tileButton = tileButton;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SlidingPuzzle game = gameWindow.getGame();
		game.moveTile(tileButton.getTile());
		if (gameWindow.getGame().isFinished()) {
			gameWindow.finishGame();
		}
	}

}
