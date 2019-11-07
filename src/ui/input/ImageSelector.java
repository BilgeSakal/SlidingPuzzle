package ui.input;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import ui.GameWindow;

public class ImageSelector {

	private GameWindow gameWindow;

	private BufferedImage selectedFile;

	public ImageSelector(GameWindow gameWindow) {
		this.gameWindow = gameWindow;
	}

	public void selectImaage() {
		FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image files",
				ImageIO.getReaderFileSuffixes());
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());
		fileChooser.addChoosableFileFilter(imageFilter);
		int input = fileChooser.showOpenDialog(null);
		if (input == JFileChooser.APPROVE_OPTION) {
			File selected = fileChooser.getSelectedFile();
			try {
				selectedFile = ImageIO.read(selected);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (selectedFile != null)
				gameWindow.newGame(selectedFile);
		}
	}

}
