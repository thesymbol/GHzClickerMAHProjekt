package ghzclicker;

public class Controller {
	private long hertz=0;
	private long kiloHertz=0;
	private long megaHertz=0;
	private long gigaHertz=0;
	private long teraHertz=0;
	private long petaHertz=0;
	private long exaHertz=0;
	
	/*
	 * 
	 */
	public long GIVEMEYOURHERTZ(){
		hertz++;
		return hertz;
	}
	
	public void Merging(){
		if(hertz==1000){
			hertz=0;
			kiloHertz++;
		}
		if(kiloHertz==1000){
			kiloHertz=0;
			megaHertz++;
		}
	}
	
}
