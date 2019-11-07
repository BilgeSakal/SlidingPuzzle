package ui.panel;

import java.awt.GridLayout;

import javax.swing.JPanel;

import game.SlidingPuzzle;

public class UtilPanel extends JPanel {

	private static final long serialVersionUID = 6313773420057816562L;

	private SlidingPuzzle game;

	private ImagePanel imgPanel;
	private TimePanel timePanel;
	private MoveCountPanel moveCountPanel;

	public UtilPanel(SlidingPuzzle game) {
		this.game = game;
		initPanel();
	}

	private void initPanel() {
		setLayout(new GridLayout(3, 1));
//		int margin = GameWindow.MARGIN;
//		setBorder(BorderFactory.createEmptyBorder(margin, margin, margin, margin));

		imgPanel = new ImagePanel(game.getImg());
		timePanel = game.getTimer().getTimePanel();
		moveCountPanel = game.getMoveCountPanel();

		add(imgPanel);
		add(timePanel);
		add(moveCountPanel);
	}

}
