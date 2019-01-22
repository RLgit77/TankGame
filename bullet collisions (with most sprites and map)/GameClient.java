//imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.lang.Object.*;

public class GameClient implements ActionListener, KeyListener, MouseListener, MouseMotionListener{
	//---------------------------------------------------------------PROPERTIES----------------------------------------------------------------//
	SuperSocketMaster ssm;
	String IP; //set in constructor
	JFrame frame;
	GamePanel panel;
	Timer timer;
	String RecievedData = "127.0.0.1,forward,f";	//default values (just false, so nothing happens)
	String[] split;
	//Boolean clicked = false;	//for combining mouse/motion listeners, not used - bullets only fire on first click
	String[] savedIPs = new String[4];
	{Arrays.fill(savedIPs, "empty");}
	Boolean isW = false;
	Boolean isA = false;
	Boolean isS = false;
	Boolean isD = false;
	
	
	//-----------------------------------------------------------------METHODS-----------------------------------------------------------------//
	
	//get, set variables with client ID number given (from split[0] in next)
	public void ParseAndSet(String[] split){
		int ClientNumber = Integer.parseInt(split[1]);	//split 0/1 are 'formatted'/the ID
		for(int count = 2; count<split.length; count++){
			//w
			if(split[count].equals("forward")){
				count++;
				if(split[count].equals("t")){//forward true
					panel.moveForwards[ClientNumber] = true;
				} else {//forward false
					panel.moveForwards[ClientNumber] = false;
				}
			//s
			} else if(split[count].equals("back")){
				count++;
				if(split[count].equals("t")){
					panel.moveBack[ClientNumber] = true;
				} else {
					panel.moveBack[ClientNumber] = false;
				}
			//a
			} else if(split[count].equals("left")){
				count++;
				if(split[count].equals("t")){
					panel.moveLeft[ClientNumber] = true;
				} else {
					panel.moveLeft[ClientNumber] = false;
				}
			//d
			}  else if(split[count].equals("right")){
				count++;
				if(split[count].equals("t")){
					panel.moveRight[ClientNumber] = true;
				} else {
					panel.moveRight[ClientNumber] = false;
				}
			//mouse clicked
			} else if(split[count].equals("clicked")){		//format is IP,"clicked",mouseX,mouseY
				panel.clicked[ClientNumber] = true;
				count++;
				panel.mouseX[ClientNumber] = Integer.parseInt(split[count]);
				count++;
				panel.mouseY[ClientNumber] = Integer.parseInt(split[count]);
			} else if(split[count].equals("spacebar")){
				panel.placeMine[ClientNumber] = true;
			}
			//end of variables
		}
	}
	

	//ActionListener
	public void actionPerformed(ActionEvent e){
		//get Data from server - will be GUI info, use it to draw
		if(e.getSource() == ssm){
			RecievedData = ssm.readText();
			split = RecievedData.split(",");
			
			//only parse if from server
			if(split[0].equals("formatted")){
				ParseAndSet(split);
				System.out.println("Client received: "+RecievedData);
				System.out.println("");
			} else if (split[0].equals("playerID") && split[1].equals(IP)){
				panel.ClientPlayer = Integer.parseInt(split[2]);
			}
			
			
		}
		
		if(e.getSource() == timer){
			panel.repaint();
		}
	}
	
	//key
	public void keyPressed(KeyEvent e){
		if(e.getKeyChar() == 'w'){
			if(isW == false){
				ssm.sendText(IP+",forward,t");
				isW = true;
			}
		}
		if(e.getKeyChar() == 's'){
			if(isS == false){
				ssm.sendText(IP+",back,t");
				isS = true;
			}
		}
		if(e.getKeyChar() == 'a'){
			if(isA == false){
				ssm.sendText(IP+",left,t");
				isA = true;
			}
		}
		if(e.getKeyChar() == 'd'){
			if(isD == false){
				ssm.sendText(IP+",right,t");
				isD = true;
			}
		}
	}
	public void keyReleased(KeyEvent e){
		//change to false
		if(e.getKeyChar() == 'w'){
			ssm.sendText(IP+",forward,f");
			isW = false;
		}
		if(e.getKeyChar() == 's'){
			ssm.sendText(IP+",back,f");
			isS = false;
		}
		if(e.getKeyChar() == 'a'){
			ssm.sendText(IP+",left,f");
			isA = false;
		}
		if(e.getKeyChar() == 'd'){
			ssm.sendText(IP+",right,f");
			isD = false;
		}
		if(e.getKeyCode() == 32){
			ssm.sendText(IP+",spacebar");
		}
	}
	public void keyTyped(KeyEvent e){
	}
	
	//mouse
	//format is IP,"clicked",mouseX,mouseY	--> true or false not needed
	public void mouseReleased(MouseEvent e){
	}
	public void mousePressed(MouseEvent e){
		//right click
		if(SwingUtilities.isRightMouseButton(e)){
			//do anything here?
		} else {
		//sends location as well
		ssm.sendText(IP+",clicked,"+(e.getX()-panel.xShift)+","+(e.getY()-panel.yShift));
		}
	}
	public void mouseClicked(MouseEvent e){	//after release
	}
	public void mouseExited(MouseEvent e){	//leaves window
	}
	public void mouseEntered(MouseEvent e){	//enters window
	}
	public void mouseMoved(MouseEvent e){
	}
	public void mouseDragged(MouseEvent e){
		//x, y
		//clicked = true;
	}

	//---------------------------------------------------------------CONSTRUCTOR---------------------------------------------------------------//
	public GameClient(){

		frame = new JFrame("Client");
			frame.addKeyListener(this);
		panel = new GamePanel();
			panel.setPreferredSize(new Dimension(1280,720));
			panel.addMouseMotionListener(this);
			panel.addMouseListener(this);
		timer = new Timer(1000/60, this);
			timer.start();

		frame.setContentPane(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
		ssm = new SuperSocketMaster("127.0.0.1",1337, this);	//moved this to end - otherwise it begins before the server and doesn't connect
		IP = ssm.getMyAddress();
		ssm.connect();
		ssm.sendText(IP+",up,f"); //send text to get server to assign this IP a player number
		
	}
	
	//debug (fake ip)
	public GameClient(String fakeIP){
		frame = new JFrame("Client");
			frame.addKeyListener(this);
		panel = new GamePanel();
			panel.setPreferredSize(new Dimension(1280,720));
			panel.addMouseMotionListener(this);
			panel.addMouseListener(this);
		timer = new Timer(1000/60, this);
			timer.start();

		frame.setContentPane(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
		ssm = new SuperSocketMaster("127.0.0.1",1337, this);	//moved this to end - otherwise it begins before the server and doesn't connect
		IP = fakeIP;
		ssm.connect();
		ssm.sendText(IP+",up,f"); //send text to get server to assign this IP a player number
	}
}
