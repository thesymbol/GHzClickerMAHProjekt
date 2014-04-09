package ghzclicker;

public class MichaelStuff implements Runnable{
	private Controller controller;
	private MenuGUI GUI;
	private String hertz;
	private boolean isRunning=true;
	private MichaelStuff MS;
	
	public MichaelStuff(Controller controller, MenuGUI GUI){
		this.controller=controller;
		this.GUI=GUI;
		
		Thread TMS = new Thread(MS);
		TMS.run();
		
	}
	
	public void run(){
		while(isRunning){
			controller.merging();
			hertz=controller.stringiFy();
			
			
			GUI.uppdate(hertz);		
		}
	}
	
//	public void gameLoop()
//	{
//	   long lastLoopTime = System.nanoTime();
//	   final int TARGET_FPS = 60;
//	   final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;   
//
//	   // keep looping round til the game ends
//	   while (gameRunning)
//	   {
//	      // work out how long its been since the last update, this
//	      // will be used to calculate how far the entities should
//	      // move this loop
//	      long now = System.nanoTime();
//	      long updateLength = now - lastLoopTime;
//	      lastLoopTime = now;
//	      double delta = updateLength / ((double)OPTIMAL_TIME);
//
//	      // update the frame counter
//	      lastFpsTime += updateLength;
//	      fps++;
//	      
//	      // update our FPS counter if a second has passed since
//	      // we last recorded
//	      if (lastFpsTime >= 1000000000)
//	      {
//	         System.out.println("(FPS: "+fps+")");
//	         lastFpsTime = 0;
//	         fps = 0;
//	      }
//	      
//	      // update the game logic
//	      doGameUpdates(delta);
//	      
//	      // draw everyting
//	      render();
//	      
//	      // we want each frame to take 10 milliseconds, to do this
//	      // we've recorded when we started the frame. We add 10 milliseconds
//	      // to this and then factor in the current time to give 
//	      // us our final value to wait for
//	      // remember this is in ms, whereas our lastLoopTime etc. vars are in ns.
//	      try{Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000 )};
//	   }
//	}
//
//	private void doGameUpdates(double delta)
//	{
//	   for (int i = 0; i < stuff.size(); i++)
//	   {
//	      // all time-related values must be multiplied by delta!
//	      Stuff s = stuff.get(i);
//	      s.velocity += Gravity.VELOCITY * delta;
//	      s.position += s.velocity * delta;
//	      
//	      // stuff that isn't time-related doesn't care about delta...
//	      if (s.velocity >= 1000)
//	      {
//	         s.color = Color.RED;
//	      }
//	      else
//	      {
//	         s.color = Color.BLUE;
//	      }
//	   }
//	}
}
