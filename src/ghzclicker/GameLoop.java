package ghzclicker;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class GameLoop {
	private Controller controller;
	
	public GameLoop() {
		controller = new Controller();
		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		
		// constant updates (every 10 millisecond).
		exec.scheduleAtFixedRate(new Runnable() {
			public void run() {
				controller.update();
			}
		}, 0, 10, TimeUnit.MILLISECONDS);
		
		// constant updates (every 1 second).
		exec.scheduleAtFixedRate(new Runnable() {
			public void run() {
				controller.updateEverySecond();
			}
		}, 0, 1, TimeUnit.SECONDS);
	}
}