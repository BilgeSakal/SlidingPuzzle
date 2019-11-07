package ui.panel;

import java.awt.GridLayout;

import javax.swing.JPanel;

import game.Point;
import game.SlidingPuzzle;
import ui.GameWindow;
import ui.TileButton;
import ui.TileButtonActionListener;

public class TilePanel extends JPanel {

	private static final long serialVersionUID = -7009790469205400434L;

	private SlidingPuzzle game;
	private TileButton[][] tileButtons;

	public TilePanel(SlidingPuzzle game) {
		this.game = game;
		initPanel();
	}

	private void initPanel() {
		setLayout(new GridLayout(game.getRow(), game.getCol()));
		initTileButtons();
	}

	private void initTileButtons() {
		int row = game.getRow();
		int col = game.getCol();
		tileButtons = new TileButton[row][col];
		for (int i = 0; i < row; ++i) {
			for (int j = 0; j < col; ++j) {
				Point p = new Point(i, j);
				TileButton tileButton = new TileButton(game.getCurrMove().getTile(p),
						GameWindow.BUTTON_PANEL_SIZE / col, GameWindow.BUTTON_PANEL_SIZE / row);
				tileButton.addActionListener(new TileButtonActionListener(game, tileButton));
				game.getCurrMove().getTile(p).setTileButton(tileButton);
				tileButtons[i][j] = tileButton;
				add(tileButton);
			}
		}
	}
	
	/**
	 * Disables all the tile buttons.
	 */
	public void disableButtons() {
		for (int i = 0; i < tileButtons.length; ++i) {
			for (int j = 0; j < tileButtons[i].length; ++j) {
				TileButton btn = tileButtons[i][j];
				// remove the action listener so the button does not do anything on click
				btn.removeActionListener(btn.getActionListeners()[0]);
			}
		}
	}

}
