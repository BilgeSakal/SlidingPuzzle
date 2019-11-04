package ui.output;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TimePanel extends JPanel {

	private static final long serialVersionUID = -1498936730540107534L;

	private JLabel label;
	private JLabel time;

	public TimePanel() {
		initPanel();
	}

	private void initPanel() {
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		initLabel();
		initTime();
	}

	private void initLabel() {
		label = new JLabel("time:");
		label.setPreferredSize(new Dimension(50, 20));
		add(label);
	}

	private void initTime() {
		time = new JLabel("0");
		time.setPreferredSize(new Dimension(50, 20));
		add(time);
	}

	public void updateTime(int time) {
		this.time.setText(time + "");
	}

}
