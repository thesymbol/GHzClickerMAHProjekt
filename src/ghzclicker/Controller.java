package ghzclicker;

public class Controller {
	private long hertz=997;
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
			modulus=hertz%999;			
			hertz-=modulus*1000;
			kiloHertz+=modulus;
		}
		if(kiloHertz>=1000){
			modulus=kiloHertz%999;	
			kiloHertz-=modulus*1000;
			megaHertz+=modulus;
		}
		if(megaHertz>=1000){
			modulus=megaHertz%999;	
			megaHertz-=modulus*1000;
			gigaHertz+=modulus;
		}
		if(gigaHertz>=1000){
			modulus=gigaHertz%999;	
			gigaHertz-=modulus*1000;
			teraHertz+=modulus;
		}	
		
	}
		
	


	
	/** 
	 * MICHAEL TESTAR DETTA
	 * Denna gör hertz till en lång string
	 */
	 public String stringiFy(){
		dog="";
		if(gigaHertz>=0){
			dog+=Long.toString(gigaHertz)+"GHz , ";
		}
		if(megaHertz>=0) {
			dog+=Long.toString(megaHertz)+"MHz , ";
		}	
		if(kiloHertz>=0){
			dog+=Long.toString(kiloHertz)+"KHz , ";
		}	
		if(hertz>=0){
			dog+=Long.toString(hertz) + "Hz";
		}
		return dog;
//		dog+=Long.toString(megaHertz);
//		dog+="MHz ,";
//		dog+=Long.toString(kiloHertz);
//		dog+="KHz ,";
//		dog+=Long.toString(hertz);
//		dog+="Hz";
//		 return dog;
	 }
	
}