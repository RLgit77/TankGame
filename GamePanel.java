import javax.swing.*;	//jpanel
import java.awt.*;	//graphics
import java.awt.geom.AffineTransform;	//rotate
import java.util.Arrays;
import java.io.*; // USE FOR MAP
import java.awt.image.*;
import javax.imageio.*;

public class GamePanel extends JPanel{
	
	//---------------------------------------------------------------PROPERTIES----------------------------------------------------------------//
	
	//map
	String[][] map = new String [19][11];
	{//load map from file
		try{
			BufferedReader data = new BufferedReader(new FileReader("TankMap.txt"));
		
			for (int y = 0; y < 11; y++){
				String line = data.readLine();
				String[] split = line.split(",");
				for (int x = 0; x < 19; x++){
					map[x][y] = split[x];
				}
			}
			data.close();		
		} catch (IOException e){
			System.out.println("No map detected");
		}
	}
	
	//scoring/deaths
	int[] shotBullet = new int[100000];
	int[] score = new int[4];
	int explosionNumber = 0;
	int[] explosionX = new int[100000];
	int[] explosionY = new int[100000];
	int[] explosionTimer = new int[100000];
		
	//drawing vars
	String[] turretType = new String[4];
	{Arrays.fill(turretType, "default");}
	{
		turretType[0] = "minigun";	//testing
		turretType[1] = "laser";
	}
	
	//images
	BufferedImage redTank;
	BufferedImage blueTank;
	BufferedImage greenTank;
	BufferedImage yellowTank;
	BufferedImage defaultRed;
	BufferedImage defaultBlue;
	BufferedImage defaultGreen;
	BufferedImage defaultYellow;
	BufferedImage laserRed;
	BufferedImage laserBlue;
	BufferedImage laserGreen;
	BufferedImage laserYellow;
	BufferedImage miniRed;
	BufferedImage miniBlue;
	BufferedImage miniGreen;
	BufferedImage miniYellow;
	BufferedImage wall;
	BufferedImage unarmedMine;
	BufferedImage armedMine;
	BufferedImage bullet;
	BufferedImage beam;
	BufferedImage entireMap;
	BufferedImage explosion1;
	BufferedImage explosion2;
	BufferedImage explosion3;
	BufferedImage hole;
	{
		try {
	    redTank = ImageIO.read(new File("Sprites/Tank Body Red.png"));
	    blueTank = ImageIO.read(new File("Sprites/Tank Body Blue.png"));
	    greenTank = ImageIO.read(new File("Sprites/Tank Body Green.png"));
	    yellowTank = ImageIO.read(new File("Sprites/Tank Body Yellow.png"));
		defaultRed = ImageIO.read(new File("Sprites/Default Red.png"));
		defaultBlue = ImageIO.read(new File("Sprites/Default Blue.png"));
		defaultGreen = ImageIO.read(new File("Sprites/Default Green.png"));
		defaultYellow = ImageIO.read(new File("Sprites/Default Yellow.png"));
		laserRed = ImageIO.read(new File("Sprites/Laser Red.png"));
		laserBlue = ImageIO.read(new File("Sprites/Laser Blue.png"));
		laserGreen = ImageIO.read(new File("Sprites/Laser Green.png"));
		laserYellow = ImageIO.read(new File("Sprites/Laser Yellow.png"));
		miniRed = ImageIO.read(new File("Sprites/Minigun Red.png"));
		miniBlue = ImageIO.read(new File("Sprites/Minigun Blue.png"));
		miniGreen = ImageIO.read(new File("Sprites/Minigun Green.png"));
		miniYellow = ImageIO.read(new File("Sprites/Minigun Yellow.png"));
		wall = ImageIO.read(new File("Sprites/Wall Tile.png"));
		unarmedMine = ImageIO.read(new File("Sprites/Landmine Body.png"));
		armedMine = ImageIO.read(new File("Sprites/Landmine Body 2.png"));
		bullet = ImageIO.read(new File("Sprites/Bullet.png"));
		beam = ImageIO.read(new File("Sprites/Beam.png"));
		entireMap = ImageIO.read(new File("Sprites/Hovertanks Map.png"));
		explosion1 = ImageIO.read(new File("Sprites/explosion1.png"));
		explosion2 = ImageIO.read(new File("Sprites/explosion2.png"));
		explosion3 = ImageIO.read(new File("Sprites/explosion3.png"));
		explosion3 = ImageIO.read(new File("Sprites/crater.png"));
	    
		} catch (IOException e) {
			System.out.println("Image loading error: "+e);
		}
	}
	
	//for centering player
	int ClientPlayer =  -1;
	int xShift = 0;
	int yShift = 0;
	
	//original map values (just used below in map array - make random? all randomness must be server side)
	int p0x=640;
	int p1x=300;
	int p2x=400;
	int p3x=500;
	int p0y=360;
	int p1y=400;
	int p2y=400;
	int p3y=400;
	
	//bullet values
	boolean[] isRemoved = new boolean[100000];
	{Arrays.fill(isRemoved, false);}
	int numberOfBullets = 0;
	int removedBullets = 0;
	int[] cooldown = new int[4];
	{Arrays.fill(cooldown, 0);}
	double[] bulletAngle = new double[100000];
	int[] flip = new int[100000];
	double[] bulletDistance = new double[100000];
	int[] bulletLaunchX = new int[100000];
	int[] bulletLaunchY = new int[100000];
	double[] bulletX = new double[100000];
	double[] bulletY = new double[100000];
	double[] bulletSpeed = new double[100000];
	int[] bulletSize = new int[100000];
	String[] bulletType = new String[100000];
	int[] powerupTimer = new int[4];
	{Arrays.fill(powerupTimer, 0);}
	
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
	boolean[] placeMine = new boolean[4];
	
	//player variables
	double[] playerX = new double[4];
	{	//code in {} runs after the constructor, sets default map locations
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
		
		//get shift to center player
		if(ClientPlayer != -1){
			xShift = 640 - (int)playerX[ClientPlayer];
			yShift = 360 - (int)playerY[ClientPlayer];
		}	
		
		//clear
		g.setColor(Color.BLACK);
		g.fillRect(0,0,1280,720);
		g.setColor(Color.BLACK);
		
		//draw map
		g.drawImage(entireMap,xShift,yShift,2432,1408,null);	
		
		//--------------------------------------------------------------runs for every explosion--------------------------------------------------------------//
		for(int i = 0; i < explosionNumber; i++){
			if(explosionTimer[i] < 20){	//all 64x64
				g.drawImage(explosion1,explosionX[i]-32+xShift,explosionY[i]-32+yShift,64,64,null);
			} else if(explosionTimer[i] < 40){
				g.drawImage(explosion2,explosionX[i]-32+xShift,explosionY[i]-32+yShift,64,64,null);
			} else if(explosionTimer[i] < 60){
				g.drawImage(explosion3,explosionX[i]-32+xShift,explosionY[i]-32+yShift,64,64,null);
			} else {	//hole
				g.drawImage(hole,explosionX[i]-32+xShift,explosionY[i]-32+yShift,64,64,null);
			}
			explosionTimer[i]++;
		}
			
		//----------------------------------------------------------------runs for every player----------------------------------------------------------------//
		for(int i = 0; i<playerNumber; i++){
			//use key inputs and check for wall
			if(moveForwards[i]){
				if(!map[(int) (playerX[i]/128.0)][(int) ((playerY[i]-30)/128.0)].equals("w")){
					playerY[i] = playerY[i]-2;
				}
			}
			if(moveBack[i]){
				if(!map[(int) (playerX[i]/128.0)][(int) ((playerY[i]+30)/128.0)].equals("w")){
					playerY[i] = playerY[i]+2;
				}
			}
			if(moveLeft[i]){
				if(!map[(int) ((playerX[i]-30)/128.0)][(int) (playerY[i]/128.0)].equals("w")){
					playerX[i] = playerX[i]-2;
				}
			}
			if(moveRight[i]){
				if(!map[(int) ((playerX[i]+30)/128.0)][(int) (playerY[i]/128.0)].equals("w")){
					playerX[i] = playerX[i]+2;
				}
			}
			
			//powerups
			if(map[(int) (playerX[i]/128.0)][(int) (playerY[i]/128.0)].equals("l")){
				turretType[i] = "laser";
				powerupTimer[i] = 600;
			} else if(map[(int) (playerX[i]/128.0)][(int) (playerY[i]/128.0)].equals("g")){
				turretType[i] = "minigun";
				powerupTimer[i] = 600;
			} else {
				if(powerupTimer[i] < 0){
					turretType[i] = "default";
				} else {
					powerupTimer[i]--;
				}
			}
				
		
			//mouse input
			cooldown[i]--;
			if(clicked[i]){
				if(cooldown[i] < 0){
					Boolean fireAnother = true; //for different gun types
					int numberFired = 0;
					
					while(fireAnother){ //stuff for one bullet, multiple times
						shotBullet[numberOfBullets] = i;
						bulletAngle[numberOfBullets] = Math.atan((mouseY[i]-playerY[i])/(mouseX[i]-playerX[i]));
						bulletLaunchX[numberOfBullets] = (int)playerX[i];
						bulletLaunchY[numberOfBullets] = (int)playerY[i];
						bulletX[numberOfBullets] = (int)playerX[i];
						bulletY[numberOfBullets] = (int)playerY[i];
						bulletSpeed[numberOfBullets] = 3;
						bulletSize[numberOfBullets] = 10;
						bulletDistance[numberOfBullets] = 10+bulletSize[numberOfBullets]+30; //more than player size + bullet size
						//fix trig math
						if(mouseX[i] < playerX[i]){
							flip[numberOfBullets] = 1;
							angle[i] = bulletAngle[numberOfBullets] - 1.57;	//half pi since all images are already rotated
						} else {
							flip[numberOfBullets] = 0;
							angle[i] = bulletAngle[numberOfBullets] + 1.57;
						}
						//changes based in bullet type
						bulletType[numberOfBullets] = turretType[i];
						if(turretType[i].equals("laser")){	//faster bullets, start further
							bulletDistance[numberOfBullets] = 10+bulletSize[numberOfBullets]+30+numberFired*5;
							bulletSpeed[numberOfBullets] = 8;
						} else if (turretType[i].equals("minigun")){
							bulletSpeed[numberOfBullets] = 2;
							bulletAngle[numberOfBullets] = Math.atan((mouseY[i]-playerY[i])/(mouseX[i]-playerX[i]))+(Math.random()*0.4-0.2);//shotgun should be in a +- 0.2 arc
							bulletDistance[numberOfBullets] = 10+bulletSize[numberOfBullets]+30+numberFired*2;
						}
						
						//see if it should fire another bullet
						if(turretType[i].equals("laser")){	//faster bullets, start further
							if(numberFired > 15){
								fireAnother = false;
							}
						} else if (turretType[i].equals("minigun")){
							if(numberFired > 6){
								fireAnother = false;
							}
						} else {
							fireAnother = false;
						}
						
						numberFired++;
						numberOfBullets++;
					}	//while fireAnother
					
					cooldown[i] = 120;
					
				}//cooldown
				
			}	//if clicked
			
			clicked[i] = false;
			
			//mouse right click
			if(placeMine[i]){
				placedMine[i] = true;
				mineX[i] = (int)playerX[i];
				mineY[i] = (int)playerY[i];	
				placeMine[i] = false;
				mineTimer[i] = 0;
			}
			
			//landmines
			//if mine is placed
			if(placedMine[i]){
				//if placed mine is armed
				if(mineTimer[i] > 60*5){	//5 seconds @ 60 frames/sec
					g.setColor(Color.RED);
					g.drawImage(armedMine,(int)mineX[i]-16+xShift,(int)mineY[i]-16+yShift,32,32,null);
					g.setColor(Color.BLACK);
					//check collisions with all players
					for(int p = 0; p<playerNumber; p++){
						if( ((mineX[i]+15) > (playerX[p]-10)) && ((mineX[i]-15) < (playerX[p]+10)) && ((mineY[i]+15) > (playerY[p]-10)) && ((mineY[i]-15) < (playerY[p]+10)) ){
							//explosion at that location
							explosionX[explosionNumber] = (int)playerX[p];
							explosionY[explosionNumber] = (int)playerY[p];
							explosionNumber++;
/*EDIT HERE */							playerX[p] = 300+p*50;	//just so they spawn differently
							playerY[p] = 300;
							
							placedMine[i] = false;	//remove exploded mine
							mineTimer[i] = 0;		//reset timer
						}
					}
				//placed, but unarmed
				} else {	
					g.drawImage(unarmedMine,(int)mineX[i]-16+xShift,(int)mineY[i]-16+yShift,32,32,null);
					mineTimer[i]++;
				}	
			}
			
			
	        
	        //tank body
			
			switch (i){
				case 0: g.drawImage(redTank,(int)playerX[i]-64+xShift,(int)playerY[i]-64+yShift,128,128,null);
					break;
				case 1: g.drawImage(yellowTank,(int)playerX[i]-64+xShift,(int)playerY[i]-64+yShift,128,128,null);
					break;
				case 2: g.drawImage(greenTank,(int)playerX[i]-64+xShift,(int)playerY[i]-64+yShift,128,128,null);
					break;
				case 3: g.drawImage(blueTank,(int)playerX[i]-64+xShift,(int)playerY[i]-64+yShift,128,128,null);
					break;
			}
			
			//turret rotation
			Graphics2D g2d = (Graphics2D)g;
	        AffineTransform old = g2d.getTransform();
	        g2d.rotate(angle[i], playerX[i]+xShift, playerY[i]+yShift);
	        
	        
			if(turretType[i].equals("default")){
				switch (i){
					case 0: g.drawImage(defaultRed,(int)playerX[i]-64+xShift,(int)playerY[i]-64+yShift,128,128,null);
						break;
					case 1: g.drawImage(defaultYellow,(int)playerX[i]-64+xShift,(int)playerY[i]-64+yShift,128,128,null);
						break;
					case 2: g.drawImage(defaultGreen,(int)playerX[i]-64+xShift,(int)playerY[i]-64+yShift,128,128,null);
						break;
					case 3: g.drawImage(defaultBlue,(int)playerX[i]-64+xShift,(int)playerY[i]-64+yShift,128,128,null);
						break;
				}
			} else if(turretType[i].equals("laser")){
				switch (i){
					case 0: g.drawImage(laserRed,(int)playerX[i]-64+xShift,(int)playerY[i]-64+yShift,128,128,null);
						break;
					case 1: g.drawImage(laserYellow,(int)playerX[i]-64+xShift,(int)playerY[i]-64+yShift,128,128,null);
						break;
					case 2: g.drawImage(laserGreen,(int)playerX[i]-64+xShift,(int)playerY[i]-64+yShift,128,128,null);
						break;
					case 3: g.drawImage(laserBlue,(int)playerX[i]-64+xShift,(int)playerY[i]-64+yShift,128,128,null);
						break;
				}
			} else if(turretType[i].equals("minigun")){
				switch (i){
					case 0: g.drawImage(miniRed,(int)playerX[i]-64+xShift,(int)playerY[i]-64+yShift,128,128,null);
						break;
					case 1: g.drawImage(miniYellow,(int)playerX[i]-64+xShift,(int)playerY[i]-64+yShift,128,128,null);
						break;
					case 2: g.drawImage(miniGreen,(int)playerX[i]-64+xShift,(int)playerY[i]-64+yShift,128,128,null);
						break;
					case 3: g.drawImage(miniBlue,(int)playerX[i]-64+xShift,(int)playerY[i]-64+yShift,128,128,null);
						break;
				}
			}
			
			
						
	        //reset rotation for other drawings
	        g2d.setTransform(old);		
		
		}
		
		//----------------------------------------------------------------runs for every bullet----------------------------------------------------------------//
		for(int i = removedBullets; i < numberOfBullets; i++){	//stops running for bullets off screen
			if(isRemoved[i] == false){		//set to true if bullet hits someone or a wall
				
				if(map[(int) (bulletX[i]/128.0)][(int) (bulletY[i]/128.0)].equals("w")){
					isRemoved[i] = true;
				}
				
				bulletX[i] = (Math.cos(bulletAngle[i])*bulletDistance[i]);
				bulletY[i] = (Math.sin(bulletAngle[i])*bulletDistance[i]);
				
				if(flip[i] == 1){
					bulletX[i] = bulletX[i]*-1;
					bulletY[i] = bulletY[i]*-1;
				}
				
				bulletX[i] = bulletLaunchX[i]+bulletX[i];
				bulletY[i] = bulletLaunchY[i]+bulletY[i];
				
				bulletDistance[i] = bulletDistance[i]+bulletSpeed[i];
				if(bulletDistance[i] > 2811) { removedBullets++; }	//the furthest distance a bullet should ever travel, stop rendering it (2811 is diagonal across screen)
				//set max bullet distance here if we want to limit it
				
				if(bulletType[i].equals("laser")){
					g.drawImage(beam,(int)bulletX[i]-bulletSize[i]+xShift,(int)bulletY[i]-bulletSize[i]+yShift,bulletSize[i],bulletSize[i],null);
				} else {
					g.drawImage(bullet,(int)bulletX[i]-bulletSize[i]+xShift,(int)bulletY[i]-bulletSize[i]+yShift,bulletSize[i],bulletSize[i],null);
				}
				
				
				//check collisions with players
				for(int p = 0; p<playerNumber; p++){
					if(playerTimer[p] > 60*5){	//5 seconds @ 60 frames/sec
						if( ((bulletX[i]+bulletSize[i]) > (playerX[p]-10)) && ((bulletX[i]-bulletSize[i]) < (playerX[p]+10)) && ((bulletY[i]+bulletSize[i]) > (playerY[p]-10)) && ((bulletY[i]-bulletSize[i]) < (playerY[p]+10)) ){
							explosionX[explosionNumber] = (int)playerX[p];
							explosionY[explosionNumber] = (int)playerY[p];
							explosionNumber++;
/*EDIT HERE */							playerX[p] = 300+p*50;
							playerY[p] = 300;
							playerTimer[p]=0;
							isRemoved[i] = true;
							score[shotBullet[i]]++;
							System.out.println("1 point for player "+(shotBullet[i]+1)+", score: "+score[shotBullet[i]]);	//scoring
						}
					} else {
					//player just spawned
					playerTimer[p]++;
					}
				}
			}
		}
		
	}
	
	//---------------------------------------------------------------CONSTRUCTOR---------------------------------------------------------------//
	public GamePanel(){
		super();
	}
}

