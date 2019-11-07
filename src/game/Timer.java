package game;

import ui.panel.TimePanel;

public class Timer extends Thread {

	private int time;
	private TimePanel timePanel;
	private boolean running = true;

	public Timer() {
		setTimePanel(new TimePanel());
	}

	private void incTime() {
		++time;
		if (timePanel != null)
			timePanel.updateTime(time);
	}

	@Override
	public void run() {
		time = 0;
		while (running) {
			try {
				Thread.sleep(1000);
				if (running)
					incTime();
			} catch (InterruptedException e) {
				running = false;
				e.printStackTrace();
			}
		}
	}

	public int getTime() {
		return time;
	}

	public void terminate() {
		running = false;
	}

	// Getters and Setters

	public TimePanel getTimePanel() {
		return timePanel;
	}

	public void setTimePanel(TimePanel timePanel) {
		this.timePanel = timePanel;
	}

}
