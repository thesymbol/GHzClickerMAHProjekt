package ghzclicker;

/**
 * 
 * @author Marcus Orwén
 */
public class Main {
	public static void main(String[] args) {
		GameLoop gameLoop = new GameLoop();
		NetworkClient network = new NetworkClient("localhost");
		network.sendData("test");
	}
}
