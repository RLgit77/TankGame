import javax.swing.*;	//jpanel
import java.awt.*;	//graphics
import java.awt.geom.AffineTransform;	//rotate
import java.util.Arrays;
import java.io.*; // USE FOR MAP

public class GamePanel extends JPanel{
	
	//---------------------------------------------------------------PROPERTIES----------------------------------------------------------------//
	
	//default map values (just used below)
	int p0x=640;
	int p1x=100;
	int p2x=200;
	int p3x=300;
	int p0y=360;
	int p1y=100;
	int p2y=100;
	int p3y=100;
	
	int numberOfBullets = 0;
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
	
	//input variables
	int playerNumber = 4;										//start with one player
	boolean[] moveForwards = new boolean[4];
	boolean[] moveBack = new boolean[4];
	boolean[] moveLeft = new boolean[4];
	boolean[] moveRight = new boolean[4];
	double[] angle = new double[4];
	int[] mouseX = new int[4];
	int[] mouseY = new int[4];
	{	//defaults (so not null, fix logic later)
		mouseX[0] = p0x+100;
		mouseX[1] = p1x+100;
		mouseX[2] = p2x+100;
		mouseX[3] = p3x+100;
	}
	{
		mouseY[0] = p0y;
		mouseY[1] = p1y;
		mouseY[2] = p2y;
		mouseY[3] = p3y;
	}
	boolean[] clicked = new boolean[4];
	
	//map variables
	double[] playerX = new double[4];
	{	//code in {} runs after the constructor
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
	
	//-----------------------------------------------------------------METHODS-----------------------------------------------------------------//
	
	
	public void paintComponent(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(0,0,1280,720);
		g.setColor(Color.BLACK);				
		
		//use vars here, calculate movement and send to clients
		for (int i = 0; i<playerNumber; i++){					//runs movement calcs 4 times
			
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
			
			

		}
			
			
			
			//DRAWING
		for(int i = 0; i<playerNumber; i++){					//runs drawings 4 times
		
			if(clicked[i]){
				g.drawLine((int)playerX[i],(int)playerY[i],mouseX[i],mouseY[i]);
				
				bulletAngle[numberOfBullets] = Math.atan((mouseY[i]-playerY[i])/(mouseX[i]-playerX[i]));
				bulletDistance[numberOfBullets] = 0;
				bulletLaunchX[numberOfBullets] = (int)playerX[i];
				bulletLaunchY[numberOfBullets] = (int)playerY[i];
				bulletX[numberOfBullets] = (int)playerX[i];
				bulletY[numberOfBullets] = (int)playerY[i];
				bulletSpeed[numberOfBullets] = 1;
				bulletSize[numberOfBullets] = 5;
				
				//fix trig math
				if(mouseX[i] < playerX[i]){
					flip[numberOfBullets] = 1;
				} else {
					flip[numberOfBullets] = 0;
				}		
				
				clicked[i] = false;
				numberOfBullets++;
			}
			
			//rotate graphics
			//Graphics2D g2d = (Graphics2D)g;
	        //AffineTransform old = g2d.getTransform();
	        //g2d.rotate(angle[i], playerX[i], playerY[i]);
	        //player
	        g.setColor(Color.GREEN);
			g.fillOval((int)playerX[i]-10,(int)playerY[i]-10,20,20);
			g.setColor(Color.BLACK);
	        //reset rotation for other draw0ngs
	        //g2d.setTransform(old);
			
		}
		
		//for loop for all bullets movements/drawing
		for(int i = 0; i < numberOfBullets; i++){
						
			bulletX[i] = (Math.cos(bulletAngle[i])*bulletDistance[i]);
			bulletY[i] = (Math.sin(bulletAngle[i])*bulletDistance[i]);
			
			if(flip[i] == 1){
				bulletX[i] = bulletX[i]*-1;
				bulletY[i] = bulletY[i]*-1;
			}
			
			bulletX[i] = bulletLaunchX[i]+bulletX[i];
			bulletY[i] = bulletLaunchY[i]+bulletY[i];
			
			bulletDistance[i] = bulletDistance[i]+bulletSpeed[i];
			
			g.drawOval((int)bulletX[i]-bulletSize[i],(int)bulletY[i]-bulletSize[i],bulletSize[i],bulletSize[i]);
		}
		
	}
	
	//---------------------------------------------------------------CONSTRUCTOR---------------------------------------------------------------//
	public GamePanel(){
		super();
	}
}

