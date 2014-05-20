package ghzclicker;

/**
 * A Class that will run the game.
 * 
 * @author Marcus Orwén
 */
public class Main {
    /**
     * Main method to run the game
     * 
     * @parma args Command line arguments
     */
    public static void main(String[] args) {
        ClientLogger.init();
        @SuppressWarnings("unused")
        GameLoop gameLoop = new GameLoop();
    }
}
