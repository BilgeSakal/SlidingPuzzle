package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import game.SlidingPuzzle;

public class TileButtonActionListener implements ActionListener {

	private SlidingPuzzle game;
	private TileButton tileButton;

	public TileButtonActionListener(SlidingPuzzle game, TileButton tileButton) {
		this.game = game;
		this.tileButton = tileButton;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		game.moveTile(tileButton.getTile());
		if (game.isFinished()) {
			game.finishGame();
		}
	}

}
