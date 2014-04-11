package ghzclicker;

import java.util.*;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MichaelStuff extends java.util.TimerTask {
	private Controller controller;
	private MenuGUI GUI;
	private String hertz;
	
	
//	Michael testar
	private java.util.Timer timer;
	private boolean isRunning;


	public MichaelStuff(Controller controller, MenuGUI GUI){
		this.controller=controller;
		this.GUI=GUI;
		
	}
	
	public void run(){
		double lastTime = System.nanoTime();
		while (true){
		  double current = System.nanoTime();
		  double elapsed = current - lastTime; 
		  
		  controller.merging();
		  hertz=controller.stringiFy();
		  GUI.uppdate(hertz);
		  
		  lastTime = current;
		}
	}	
	 
//	public void gameLoop(){
//		timer = new Timer();
//		timer.schedule(new LoopyStuff(), 0, 1000 / 60); //new timer at 60 fps, the timing mechanism
//		
//	}  
//
//	private class LoopyStuff extends java.util.TimerTask{
//		
//		public void run(){ //this becomes the loop
//		
//	    	controller.merging();
//			hertz=controller.stringiFy();
//
//
//			GUI.uppdate(hertz);	
//
//	        if (!isRunning){
//	            timer.cancel();
//	        }
//	    }
//	}
}