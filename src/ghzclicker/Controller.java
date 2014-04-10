package ghzclicker;
import java.io.*;

public class Controller {
	private long hertz=20010;
	private long kiloHertz=110;
	private long megaHertz=11111;
	private long gigaHertz=115;
	private long teraHertz=116;
	private long petaHertz=0;
	private long exaHertz=0;
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
			modulus=hertz/1000;			
			hertz-=modulus*1000;
			kiloHertz+=modulus;
		}
		if(kiloHertz>=1000){
			modulus=kiloHertz/10;	
			kiloHertz-=modulus*1000;
			megaHertz+=modulus;
		}
		if(megaHertz>=1000){
			modulus=megaHertz/1000;	
			megaHertz-=modulus*1000;
			gigaHertz+=modulus;
		}
		if(gigaHertz>=1000){
			modulus=gigaHertz/1000;	
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
			dog+=Long.toString(gigaHertz)+"G   "; 			 
		}
		if(megaHertz>=0) {
			dog+=Long.toString(megaHertz)+"M   ";
		}	
		if(kiloHertz>=0){
			dog+=Long.toString(kiloHertz)+"K   ";
		}	
		if(hertz>=0){
			dog+=Long.toString(hertz) + "Hz";
		}
		return dog;

	 }
	 /**
	  * Viktor testar
	  * Ser om jag kan spara spelet
	  * Ändra till rätt HDD på datorn, på mitt windows8 tillåts inte programmet att skapa och spara en fil på C:/ 
	  */
	 public void saveGame(){
		 try{
			 String txt = gigaHertz + ";" + megaHertz + ";" + kiloHertz + ";" + hertz + ";";
			 File newTextFile = new File("res/GhzSaveGame.txt");
			 FileWriter fw = new FileWriter(newTextFile);
			 fw.write(txt);
			 fw.close();
		 }catch(IOException iox){
			 iox.printStackTrace();
		 }
		 
	 }
	
}