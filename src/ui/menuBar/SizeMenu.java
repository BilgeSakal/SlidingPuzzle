package ui.menuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import ui.GameWindow;
import ui.input.RowColReader;

public class SizeMenu extends JMenu {

	private static final long serialVersionUID = 3848000767250714142L;

	private GameWindow gameWindow;

	public SizeMenu(GameWindow gameWindow) {
		this.gameWindow = gameWindow;
		setText("Size");
		initMenu();
	}

	private void initMenu() {
		for (int i = 3; i <= 5; ++i) {
			JMenuItem item = new JMenuItem(i + "x" + i);
			int size = i;
			item.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					gameWindow.newGame(size, size, gameWindow.getCurrImage());
				}
			});
			add(item);
		}
		JMenuItem customSize = new JMenuItem("Custom Size");
		customSize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RowColReader rowColReader = new RowColReader(gameWindow);
				rowColReader.createAndShowUI();
			}
		});
		add(customSize);
	}

}
