package ui.panel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import ui.GameWindow;
import ui.ImageManipulation;

public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 1801110859135979940L;

	private BufferedImage img;

	public ImagePanel(BufferedImage img) {
		this.img = img;
		initPanel();
	}

	private void initPanel() {
		setPreferredSize(new Dimension(GameWindow.SMALL_IMAGE_SIZE, GameWindow.SMALL_IMAGE_SIZE));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(ImageManipulation.resize(img, GameWindow.SMALL_IMAGE_SIZE, GameWindow.SMALL_IMAGE_SIZE), 0, 0,
				this);
	}

}
