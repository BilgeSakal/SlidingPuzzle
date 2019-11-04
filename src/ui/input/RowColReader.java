package ui.input;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ui.GameWindow;

public class RowColReader {

	private GameWindow gameWindow;

	private static final int MARGIN = 25;
	private static final int HEIGHT_GAP = 10;

	private static final int MIN_ROW = 2;
	private static final int MIN_COL = 2;
	private static final int MAX_ROW = 50;
	private static final int MAX_COL = 50;

	private JFrame frame;
	private JPanel panel;
	private LabeledDigitTextPanel rowReader = new LabeledDigitTextPanel("row");
	private LabeledDigitTextPanel colReader = new LabeledDigitTextPanel("col");

	private final String boundErrorMessage = "Invalid input!\n" + MIN_ROW + " < row < " + MAX_ROW + "\n" + MIN_COL
			+ " < col < " + MAX_COL;

	private JButton okButton;

	public RowColReader(GameWindow gameWindow) {
		this.gameWindow = gameWindow;
		initFrame();
	}

	private void initFrame() {
		frame = new JFrame("Enter Row&Col");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);

		initPanel();
	}

	private void initPanel() {
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));

		panel.add(rowReader);
		panel.add(Box.createRigidArea(new Dimension(0, HEIGHT_GAP)));
		panel.add(colReader);
		panel.add(Box.createRigidArea(new Dimension(0, HEIGHT_GAP)));
		initOkButton();
		frame.add(panel);
	}

	private void initOkButton() {
		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				okButtonAction();
			}
		});
		panel.add(okButton);
	}

	private void okButtonAction() {
		if (!isValidInput()) {
			displayErrorMessage();
		} else {
			frame.dispose();
			int row = rowReader.getInput();
			int col = colReader.getInput();
			gameWindow.newGame(row, col);
		}
	}

	public void createAndShowUI() {
		frame.pack();
		JFrame gameFrame = gameWindow.getGameFrame();
		Point gameWindowLoc = gameFrame.getLocation();
		frame.setLocation(new Point((int) gameWindowLoc.getX() + ((gameFrame.getWidth() - frame.getWidth()) / 2),
				(int) gameWindowLoc.getY() + ((gameFrame.getHeight() - frame.getHeight()) / 2)));
		frame.setVisible(true);
	}

	private boolean isValidInput() {
		int row = rowReader.getInput();
		int col = colReader.getInput();

		if (row < MIN_ROW || row > MAX_ROW)
			return false;
		if (col < MIN_COL || col > MAX_COL)
			return false;
		return true;
	}

	private void displayErrorMessage() {
		JOptionPane.showMessageDialog(frame, boundErrorMessage, "Error", JOptionPane.ERROR_MESSAGE);
	}

}
