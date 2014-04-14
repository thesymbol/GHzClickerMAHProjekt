package ghzclickerserver;

/**
 * 
 * @author Marcus Orwén
 */
public class Main {
	public static void main(String[] args) {
		NetworkServer network = new NetworkServer();
		System.out.println(network.getData().get(0));
	}
}
