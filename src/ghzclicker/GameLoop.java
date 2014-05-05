package ghzclicker;

import java.io.IOException;
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

    private static final String SERVER_IP = "127.0.0.1";

    /**
     * Will update the game every chosen second/millisecond.
     */
    public GameLoop() {
        NetworkClient network = null;
        try {
            network = new NetworkClient(SERVER_IP);
        } catch (IOException e) {
            e.printStackTrace();
        }
        controller = new Controller(network);
        @SuppressWarnings("unused")
        LoginController loginController = new LoginController(network);

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