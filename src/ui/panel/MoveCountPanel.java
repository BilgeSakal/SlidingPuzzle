package ui.panel;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.GameWindow;

public class MoveCountPanel extends JPanel {

	private static final long serialVersionUID = 2968363154213777555L;

	private JLabel label;
	private JLabel countLabel;

	public MoveCountPanel() {
		initPanel();
	}

	private void initPanel() {
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		setBorder(BorderFactory.createEmptyBorder(0, GameWindow.MARGIN, 0, GameWindow.MARGIN));
		initLabel();
		initCountLabel();
	}

	private void initLabel() {
		label = new JLabel("Moves:");
		label.setPreferredSize(new Dimension(50, 20));
		add(label);
	}

	private void initCountLabel() {
		countLabel = new JLabel("0");
		countLabel.setPreferredSize(new Dimension(50, 20));
		add(countLabel);
	}

	public void updateMoveCount(int moveCount) {
		countLabel.setText(moveCount + "");
	}

}
