package ghzclicker;

public class Controller {
	private long hertz=0;
	private long kiloHertz=0;
	private long megaHertz=0;
	private long gigaHertz=0;
	private long teraHertz=0;
	private long petaHertz=0;
	private long exaHertz=0;
	
	
	public void hertzClicked(){
		//Om man ska kunna få olika mängd hertz per klick får vi ändra så det inte är "hertz++".
		hertz++;
	}
	
	
	public void Merging(){
		if(hertz>=1000){
			hertz=-1000;
			kiloHertz++;
		}
		if(kiloHertz>=1000){
			kiloHertz=-1000;
			megaHertz++;
		}
		if(megaHertz>=1000){
			megaHertz=-1000;
			gigaHertz++;
		}
		if(gigaHertz>=1000){
			gigaHertz=-1000;
			teraHertz++;
		}
	}	
}
