package ui;

import java.awt.Dimension;

import javax.swing.JButton;

import game.Tile;

public class TileButton extends JButton {

	private static final long serialVersionUID = 1L;

	private Tile tile;

	public TileButton(Tile tile, int buttonWidth, int buttonHeight) {
		setTile(tile);
		setIcon(tile.getImg());
		setPreferredSize(new Dimension(buttonWidth, buttonHeight));
	}

	public void update() {
		setIcon(tile.getImg());
	}

	// getters and setters

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}

}
