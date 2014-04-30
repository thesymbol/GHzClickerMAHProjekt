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
	private LoginController loginController;
	private Controller controller;
	

	

	/**
	 *  Will update the game every chosen second/millisecond.
	 */
	public GameLoop() {		
		controller = new Controller();
		loginController = new LoginController();		
		
	
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