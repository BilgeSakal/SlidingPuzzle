package ui.panel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import game.SlidingPuzzle;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = 350614577827139195L;

	private TilePanel tilePanel;
	private UtilPanel utilPanel;

	private SlidingPuzzle game;

	public GamePanel(SlidingPuzzle game) {
		this.game = game;
		initPanel();
	}

	private void initPanel() {
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		setBackground(new Color(178, 235, 242));

		tilePanel = new TilePanel(game);
		utilPanel = new UtilPanel(game);

		add(tilePanel);
		add(Box.createRigidArea(new Dimension(10, 0)));
		add(utilPanel);
	}

	public TilePanel getTilePanel() {
		return tilePanel;
	}
	
	public UtilPanel getUtilPanel() {
		return utilPanel;
	}

}
