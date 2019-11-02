package ui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class ImageManipulation {

	public static BufferedImage resize(BufferedImage img, int width, int height) {
		Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = resized.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return resized;
	}

	public static BufferedImage[][] parcelImage(BufferedImage img, int row, int col, int resW, int resH) {
		img = resize(img, resW, resH);
		BufferedImage[][] images = new BufferedImage[row][col];
		int tileW = resW / col;
		int tileH = resH / row;
		for (int i = 0; i < row; ++i) {
			for (int j = 0; j < col; ++j) {
				BufferedImage tile = img.getSubimage(j * tileW, i * tileH, tileW, tileH);
				images[i][j] = tile;
			}
		}
		return images;
	}

}
