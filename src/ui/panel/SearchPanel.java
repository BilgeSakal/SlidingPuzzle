package ui.panel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import game.SlidingPuzzle;
import search.BFS;
import search.HeuristicSearch;
import search.Search;

public class SearchPanel extends JPanel {

	private static final long serialVersionUID = 1788971653768325430L;

	private SlidingPuzzle game;
	private List<Integer> solved;
	private SearchInfoPanel searchInfoPanel;

	private JCheckBox bfs;
	private JCheckBox heuristic;

	private JButton solveButton;
	private JButton simulateButton;

	public SearchPanel(SlidingPuzzle game, SearchInfoPanel searchInfoPanel) {
		this.game = game;
		this.searchInfoPanel = searchInfoPanel;
		initPanel();
	}

	private void initPanel() {
		setLayout(new GridLayout(2, 2));

		ButtonGroup bg = new ButtonGroup();

		bfs = new JCheckBox("BFS");
		heuristic = new JCheckBox("Heuristic");

		bfs.setSelected(true);

		bg.add(bfs);
		bg.add(heuristic);

		add(bfs);
		add(heuristic);

		solveButton = new JButton("Solve");
		if (game.getRow() + game.getCol() > 6) {
			bfs.setEnabled(false);
			heuristic.setSelected(true);
		}
		if (game.getRow() + game.getCol() > 7) {
			heuristic.setEnabled(false);
		}
		solveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Runnable r = new Runnable() {
					@Override
					public void run() {
						searchInfoPanel.setText("Solving puzzle...");
						if (bfs.isSelected()) {
							Search s = new BFS(game.getCurrMove(), game.getGoal());
							solved = s.solve();
							searchInfoPanel.setText("Solved! " + solved.size() + " moves.");
						} else {
							Search s = new HeuristicSearch(game.getCurrMove(), game.getGoal());
							solved = s.solve();
							searchInfoPanel.setText("Solved! " + solved.size() + " moves.");
						}
					}
				};
				Thread t = new Thread(r);
				t.start();
				simulateButton.setEnabled(true);
			}
		});

		simulateButton = new JButton("Simulate");
		simulateButton.setEnabled(false);
		simulateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Thread t = new Thread() {
					@Override
					public void run() {
						for (Integer dir : solved) {
							game.getCurrMove().moveEmptyTile(dir);
							try {
								sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				};
				t.start();
			}
		});

		add(solveButton);
		add(simulateButton);
	}
	
	public JButton getSimulateButton() {
		return simulateButton;
	}

}
