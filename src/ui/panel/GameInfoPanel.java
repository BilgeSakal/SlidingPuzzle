package ui.panel;

import java.awt.GridLayout;

import javax.swing.JPanel;

import game.SlidingPuzzle;

public class GameInfoPanel extends JPanel {

	private static final long serialVersionUID = -8127445630161242380L;

	private SlidingPuzzle game;

	private TimePanel timePanel;
	private MoveCountPanel moveCountPanel;

	public GameInfoPanel(SlidingPuzzle game) {
		this.game = game;
		initPanel();
	}

	private void initPanel() {
		setLayout(new GridLayout(2, 1));

		timePanel = game.getTimer().getTimePanel();
		moveCountPanel = game.getMoveCountPanel();

		add(timePanel);
		add(moveCountPanel);
	}

}
