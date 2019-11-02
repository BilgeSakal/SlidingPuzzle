package game;

import javax.swing.ImageIcon;

import ui.TileButton;

public class Tile {

	private int identifier;
	private Point location;
	private ImageIcon img;

	private TileButton tileButton;

	public Tile(int identifier, Point location, ImageIcon img) {
		setIdentifier(identifier);
		setLocation(location);
		setImg(img);
	}

	public void notifyTileButton() {
		if (tileButton != null) {
			tileButton.update();
		}
	}

	/**
	 * Copies the tile.
	 * 
	 * @return the copy of this instance.
	 */
	public Tile copyTile() {
		return new Tile(this.identifier, this.location, this.img);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Tile && obj != null) {
			Tile t = (Tile) obj;
			return this.identifier == t.identifier && this.location.equals(t.location);
		}
		return false;
	}

	// getters and setters

	public int getIdentifier() {
		return identifier;
	}

	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public ImageIcon getImg() {
		return img;
	}

	public void setImg(ImageIcon img) {
		this.img = img;
		notifyTileButton();
	}

	public void setTileButton(TileButton tileButton) {
		this.tileButton = tileButton;
	}

}
