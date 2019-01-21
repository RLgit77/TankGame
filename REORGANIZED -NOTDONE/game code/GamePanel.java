import javax.swing.*;	//jpanel
import java.awt.*;	//graphics
import java.awt.geom.AffineTransform;	//rotate
import java.util.Arrays;
import java.io.*; // USE FOR MAP

public class GamePanel extends JPanel{
	
	//---------------------------------------------------------------PROPERTIES----------------------------------------------------------------//
	
	//original map values (just used below in map array - make random? all randomness must be server side)
	int p0x=640;
	int p1x=100;
	int p2x=200;
	int p3x=300;
	int p0y=360;
	int p1y=100;
	int p2y=100;
	int p3y=100;
	
	//bullet values
	int numberOfBullets = 0;
	int removedBullets = 0;
	int cooldown = 0;
	double[] bulletAngle = new double[100000];
	int[] flip = new int[100000];
	double[] bulletDistance = new double[100000];
	int[] bulletLaunchX = new int[100000];
	int[] bulletLaunchY = new int[100000];
	double[] bulletX = new double[100000];
	double[] bulletY = new double[100000];
	double[] bulletSpeed = new double[100000];
	int[] bulletSize = new int[100000];
	
	//landmine values
	boolean[] placedMine = new boolean[4];
	double[] mineX = new double[4];
	double[] mineY = new double[4];
	int[] mineTimer = new int[4];
	{
	mineTimer[0] = 0;
	mineTimer[1] = 0;
	mineTimer[2] = 0;
	mineTimer[3] = 0;
	}
	
	//input variables
	int playerNumber = 4;
	boolean[] moveForwards = new boolean[4];
	boolean[] moveBack = new boolean[4];
	boolean[] moveLeft = new boolean[4];
	boolean[] moveRight = new boolean[4];
	double[] angle = new double[4];
	int[] mouseX = new int[4];
	int[] mouseY = new int[4];
	boolean[] clicked = new boolean[4];
	boolean[] rightclicked = new boolean[4];
	
	//player variables
	double[] playerX = new double[4];
	{	//code in {} runs after the constructor, sets default map locations - in server?
		playerX[0] = p0x;
		playerX[1] = p1x;
		playerX[2] = p2x;
		playerX[3] = p3x;
	}
	double[] playerY = new double[4];
	{
		playerY[0] = p0y;
		playerY[1] = p1y;
		playerY[2] = p2y;
		playerY[3] = p3y;
	}
	int[] playerTimer = new int[4];	//for spawn protection
	{
	playerTimer[0] = 0;
	playerTimer[1] = 0;
	playerTimer[2] = 0;
	playerTimer[3] = 0;
	}
	
		//-----------------------------------------------------------------------METHODS-----------------------------------------------------------------------//
	
	
	public void paintComponent(Graphics g){
		//clear
		g.setColor(Color.WHITE);
		g.fillRect(0,0,1280,720);
		g.setColor(Color.BLACK);				
			
		//----------------------------------------------------------------runs for every player----------------------------------------------------------------//
		for(int i = 0; i<playerNumber; i++){
			
			//use key inputs
			if(moveForwards[i]){
				playerY[i] = playerY[i]-2;
			}
			if(moveBack[i]){
				playerY[i] = playerY[i]+2;
			}
			if(moveLeft[i]){
				playerX[i] = playerX[i]-2;
			}
			if(moveRight[i]){
				playerX[i] = playerX[i]+2;
			}
		
			//mouse inputs
			if(clicked[i]){
				g.drawLine((int)playerX[i],(int)playerY[i],mouseX[i],mouseY[i]);
				
				bulletAngle[numberOfBullets] = Math.atan((mouseY[i]-playerY[i])/(mouseX[i]-playerX[i]));
				bulletLaunchX[numberOfBullets] = (int)playerX[i];
				bulletLaunchY[numberOfBullets] = (int)playerY[i];
				bulletX[numberOfBullets] = (int)playerX[i];
				bulletY[numberOfBullets] = (int)playerY[i];
				bulletSpeed[numberOfBullets] = 1;
				bulletSize[numberOfBullets] = 5;
				bulletDistance[numberOfBullets] = 10+bulletSize[numberOfBullets]+20; //more than player size + bullet size
				
				//fix trig math
				if(mouseX[i] < playerX[i]){
					flip[numberOfBullets] = 1;
				} else {
					flip[numberOfBullets] = 0;
				}		
				
				clicked[i] = false;
				numberOfBullets++;
			}
			
			//mouse right click
			if(rightclicked[i]){
				
				placedMine[i] = true;
				mineX[i] = (int)playerX[i];
				mineY[i] = (int)playerY[i];	
				
				rightclicked[i] = false;
			}
			
			//landmines
			//if mine is placed
			if(placedMine[i]){
				//if placed mine is armed
				if(mineTimer[i] > 60*5){	//5 seconds @ 60 frames/sec
					g.setColor(Color.RED);
					g.fillOval((int)mineX[i]-15,(int)mineY[i]-15,30,30);
					g.setColor(Color.BLACK);
					//check collisions with all players
					for(int p = 0; p<playerNumber; p++){
						if( ((mineX[i]+15) > (playerX[p]-10)) && ((mineX[i]-15) < (playerX[p]+10)) && ((mineY[i]+15) > (playerY[p]-10)) && ((mineY[i]-15) < (playerY[p]+10)) ){
							g.fillRect((int)playerX[p]-15,(int)playerY[p]-15,30,30);
							//reset position when dead, do other stuff here
							playerX[p] = 100+p*50;	//just so they spawn differently
							playerY[p] = 100;
							
							placedMine[i] = false;	//remove exploded mine
							mineTimer[i] = 0;		//reset timer
						}
					}
				//placed, but unarmed
				} else {	
					g.fillOval((int)mineX[i]-15,(int)mineY[i]-15,30,30);
					mineTimer[i]++;
				}	
			}
			
			//	rotate graphics (use for turret in bullet firing)
			//Graphics2D g2d = (Graphics2D)g;
	        //AffineTransform old = g2d.getTransform();
	        //g2d.rotate(angle[i], playerX[i], playerY[i]);
	        
	        //					player
	        g.setColor(Color.GREEN);
			g.fillOval((int)playerX[i]-10,(int)playerY[i]-10,20,20);
			g.setColor(Color.BLACK);
			
	        //		reset rotation for other draw0ngs
	        //g2d.setTransform(old);		
		
		}
		
		//----------------------------------------------------------------runs for every bullet----------------------------------------------------------------//
		for(int i = removedBullets; i < numberOfBullets; i++){										//too many bullets causes lag -> make i start at 'removedbullet' instead of 0, removed++ whenever one leaves the game, also limit the amount per player
						
			bulletX[i] = (Math.cos(bulletAngle[i])*bulletDistance[i]);
			bulletY[i] = (Math.sin(bulletAngle[i])*bulletDistance[i]);
			
			if(flip[i] == 1){
				bulletX[i] = bulletX[i]*-1;
				bulletY[i] = bulletY[i]*-1;
			}
			
			bulletX[i] = bulletLaunchX[i]+bulletX[i];
			bulletY[i] = bulletLaunchY[i]+bulletY[i];
			
			bulletDistance[i] = bulletDistance[i]+bulletSpeed[i];
			if(bulletDistance[i] > 1470) { removedBullets++; }	//the furthest distance a bullet should ever travel, stop rendering it (1470 is diagonal across screen)
			//set max bullet distance here if we want to limit it
			
			g.drawOval((int)bulletX[i]-bulletSize[i],(int)bulletY[i]-bulletSize[i],bulletSize[i],bulletSize[i]);
			
			
			//check collisions with players
			for(int p = 0; p<playerNumber; p++){
				if(playerTimer[p] > 60*5){	//5 seconds @ 60 frames/sec
					if( ((bulletX[i]+bulletSize[i]) > (playerX[p]-10)) && ((bulletX[i]-bulletSize[i]) < (playerX[p]+10)) && ((bulletY[i]+bulletSize[i]) > (playerY[p]-10)) && ((bulletY[i]-bulletSize[i]) < (playerY[p]+10)) ){
						g.fillRect((int)playerX[p]-15,(int)playerY[p]-15,30,30);
						//reset position when dead, do other stuff here
						playerX[p] = 100+p*50;	//just so they spawn differently
						playerY[p] = 100;
						playerTimer[p]=0;
					}
				} else {
				//player just spawned
				playerTimer[p]++;
				}
			}
		}
		
	}
	
	//---------------------------------------------------------------CONSTRUCTOR---------------------------------------------------------------//
	public GamePanel(){
		super();
	}
}

