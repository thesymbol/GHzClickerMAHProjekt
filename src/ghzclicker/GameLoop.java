package ghzclicker;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * A loop which will let the application to run for forever and keep updating the game.
 * 
 * @author Marcus Orwén
 * 
 */
public class GameLoop {
	private Controller controller;
	private dController dcontroller;

	/**
	 * 
	 */
	public GameLoop() {
//		controller = new Controller();
		dcontroller= new dController();
		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();

		// constant updates (every 10 millisecond).
		exec.scheduleAtFixedRate(new Runnable() {
			public void run() {
//				controller.update();
				dcontroller.update();
			}
		}, 0, 10, TimeUnit.MILLISECONDS);

		// constant updates (every 1 second).
		exec.scheduleAtFixedRate(new Runnable() {
			public void run() {
//				controller.updateEverySecond();
				dcontroller.updateEverySecond();
			}
		}, 0, 1, TimeUnit.SECONDS);
	}
}