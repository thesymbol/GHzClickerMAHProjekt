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
    private NetworkClient network = null;

    /**
     * Will update the game every chosen second/millisecond.
     */
    public GameLoop() {
        try {
            network = new NetworkClient(SERVER_IP);
        } catch (IOException e) {
            e.printStackTrace();
        }
        controller = new Controller(network);
        @SuppressWarnings("unused")
        LoginController loginController = new LoginController(network, controller);

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

        Runtime.getRuntime().addShutdownHook(new Thread() { // safely close connection to server when game is closing.
            public void run() {
                try {
                    network.close();
                    System.out.println("[Info] Closing game");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}