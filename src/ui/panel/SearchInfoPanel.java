package ui.panel;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class SearchInfoPanel extends JPanel {

	private static final long serialVersionUID = -4773783270536030284L;

	private JLabel label;

	public SearchInfoPanel() {
		initPanel();
	}

	private void initPanel() {
		label = new JLabel();
		add(label);
	}

	public void setText(String text) {
		label.setText(text);
	}

}
