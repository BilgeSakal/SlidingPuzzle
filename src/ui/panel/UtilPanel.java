package ui.panel;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import game.SlidingPuzzle;

public class UtilPanel extends JPanel {

	private static final long serialVersionUID = 6313773420057816562L;

	private SlidingPuzzle game;

	private ImagePanel imgPanel;
	private GameInfoPanel gameInfoPanel;
	private SearchPanel searchPanel;
	private SearchInfoPanel searchInfoPanel;

	public UtilPanel(SlidingPuzzle game) {
		this.game = game;
		initPanel();
	}

	private void initPanel() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		imgPanel = new ImagePanel(game.getImg());
		gameInfoPanel = new GameInfoPanel(game);
		searchInfoPanel = new SearchInfoPanel();
		searchPanel = new SearchPanel(game, searchInfoPanel);

		add(imgPanel);
		add(gameInfoPanel);
		add(searchPanel);
		add(searchInfoPanel);
	}
	
	public SearchPanel getSearchPanel() {
		return searchPanel;
	}

}
