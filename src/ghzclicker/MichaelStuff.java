package ghzclicker;

public class MichaelStuff implements Runnable{
	private Controller controller;
	private MenuGUI GUI;
	private String hertz;


	public MichaelStuff(Controller controller, MenuGUI GUI){
		this.controller=controller;
		this.GUI=GUI;

	}

	public void run(){
		while(true){
			controller.merging();
			hertz=controller.stringiFy();


			GUI.uppdate(hertz);		

		}
	}
}