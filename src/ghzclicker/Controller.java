package ghzclicker;

public class Controller {
	private long hertz=000;
	private long kiloHertz=000;
	private long megaHertz=000;
	private long gigaHertz=000;
	private long teraHertz=000;
	private long petaHertz=000;
	private long exaHertz=000;
	private long baseValueClick=0;
	
	//Michaels test veribaler
	private String dog;
	private long modulus;
	
	
	public void hertzClicked(){
		//Om man ska kunna få olika mängd hertz per klick får vi ändra så det inte är "hertz++".
		hertz++;
		//hertz+=baseValueClick*modifier;
	}
	
	public void merging() {
		if(hertz>=1000){
			modulus=hertz%1000;			
			hertz-=modulus*1000;
			kiloHertz+=modulus;
		}
		if(kiloHertz>=1000){
			modulus=hertz%1000;	
			kiloHertz-=modulus*1000;
			gigaHertz+=modulus;
		}
		if(megaHertz>=1000){
			modulus=gigaHertz%1000;	
			gigaHertz-=modulus*1000;
			megaHertz+=modulus;
		}
		if(kiloHertz>=1000){
			modulus=megaHertz%1000;	
			megaHertz-=modulus*1000;
			teraHertz+=modulus;
		}	
		
	}
		
	


	
	/** 
	 * MICHAEL TESTAR DETTA
	 * Denna gör hertz till en lång string
	 */
	 public String stringiFy(){
		dog=Long.toString(gigaHertz);
		dog+=Long.toString(megaHertz);
		dog+=Long.toString(kiloHertz);
		dog+=Long.toString(hertz);
		 return dog;
	 }


	
	
}