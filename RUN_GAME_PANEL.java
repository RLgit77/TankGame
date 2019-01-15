import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;


public class RUN_GAME_PANEL extends JPanel{
	// Properties
	int intBallX = 100;
	int intBallY = 100;
	boolean blnGoDown = false;
	boolean blnGoUp = false;
	boolean blnGoLeft = false;
	boolean blnGoRight = false;
	boolean blnSwitch = false;
	
	// Methods
	public void paintComponent(Graphics g){ // By defaut, JPanel draws a blank panel. We are overriding it so it's no longer drawing a blank screen.
		// White Rectangle Object (Background)
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 1280, 720); 
		
		if(blnSwitch == false){
			
			// Blue Oval Object
			g.setColor(Color.BLUE);
			g.fillOval(intBallX, intBallY, 20, 20);
			//intBallX = intBallX + 1; // Makes the ball X-Coordinate +1 each time panel is refreshed.
			if(blnGoDown){ // Translation movement of Oval Object using Boolean.
				intBallY++;
			}
			if(blnGoUp){
				intBallY--;
			}
			if(blnGoLeft){
				intBallX--;
			}
			if(blnGoRight){
				intBallX++;
			}
		}
		
		
		else if(blnSwitch = true){
			g.setColor(Color.GREEN);
			g.fillRect(0, 0, 1280, 720); 
			g.setColor(Color.BLACK);
			g.fillRect(intBallX, intBallY, 50, 50);
			if(blnGoDown){ // Translation movement of Oval Object using Boolean.
				intBallY++;
			}
			if(blnGoUp){
				intBallY--;
			}
			if(blnGoLeft){
				intBallX--;
			}
			if(blnGoRight){
				intBallX++;
			}
		}
	}
	
	
	// Constructors
	public RUN_GAME_PANEL(){
		super();
	}
	
}

