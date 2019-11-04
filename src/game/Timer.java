package game;

import ui.output.TimePanel;

public class Timer extends Thread {

	private int time;
	private TimePanel timePanel;
	private boolean running = true;

	private void incTime() {
		++time;
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

	public void setTimePanel(TimePanel timePanel) {
		this.timePanel = timePanel;
	}

}
