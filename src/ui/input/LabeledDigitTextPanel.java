package ui.input;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LabeledDigitTextPanel extends JPanel {

	private static final long serialVersionUID = -362115476447884470L;

	private JTextField textField;
	private JLabel label;

	public LabeledDigitTextPanel(String title) {
		initPanel(title);
	}

	private void initPanel(String title) {
		setLayout(new GridLayout(1, 2));
		setLabel(title);
		setTextField();
	}

	private void setLabel(String title) {
		label = new JLabel(title);
		label.setPreferredSize(new Dimension(150, 20));
		add(label);
	}

	private void setTextField() {
		textField = new JTextField();
		setPreferredSize(new Dimension(150, 20));
		addOnlyDigitAdapter();
		add(textField);
	}

	private void addOnlyDigitAdapter() {
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char ch = e.getKeyChar();
				if (!Character.isDigit(ch)) {
					e.consume();
				}
			}
		});
	}

	public int getInput() {
		String text = textField.getText();
		if (!text.equals("")) {
			return Integer.parseInt(text);
		}
		return -1;
	}

}
