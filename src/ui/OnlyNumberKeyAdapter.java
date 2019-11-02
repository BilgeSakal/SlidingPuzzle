package ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class OnlyNumberKeyAdapter extends KeyAdapter {

	@Override
	public void keyTyped(KeyEvent e) {
		char ch = e.getKeyChar();
		if (!Character.isDigit(ch)) {
			e.consume();
		}
	}

}
