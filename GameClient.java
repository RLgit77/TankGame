//imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameClient implements ActionListener,KeyListener,MouseMotionListener,MouseListener{
	
	//---------------------------------------------------------------PROPERTIES----------------------------------------------------------------//
	SuperSocketMaster ssm;
	String socketSetup = "Server";
	
	String socketID = "Client 1";	//this will be set by the program, maybe use IP?
	//clients would send their movement/clicks to the server, which would forward them to all other players
	//
	//OR the clients could only act as controllers, essentially streaming the game from the server - since supersocket only supports text
	//   we would need to send all variables in one string which could get complicated, we could look into other networking tools
	
	JFrame frame;
	GamePanel panel;
	Timer timer;
	
	String yMove = "noY";
	String xMove = "noX";
	
	//-----------------------------------------------------------------METHODS-----------------------------------------------------------------//
	
	//ActionListener
	public void actionPerformed(ActionEvent e){
		//data received
		if(e.getSource() == ssm){
			String data = ssm.readText();
			System.out.println(data);
		}
	}
	
	//KeyListener
	public void keyPressed(KeyEvent e){
		if(e.getKeyChar() == 'w'){
			yMove = "up";
		} else if(e.getKeyChar() == 's'){
			yMove = "down";
		} else if(e.getKeyChar() == 'a'){
			xMove = "left";
		} else if(e.getKeyChar() == 'd'){
			xMove = "right";
		}
		System.out.println(xMove + "," + yMove);
		ssm.sendText(socketID+": "+xMove+","+yMove);
	}
	//
	public void keyTyped(KeyEvent e){
	}
	//
	public void keyReleased(KeyEvent e){
		if(e.getKeyChar() == 'w' || yMove.equals("up")){
			yMove = "noY";
		}
		if(e.getKeyChar() == 's' || yMove.equals("down")){
			yMove = "noY";
		}
		if(e.getKeyChar() == 'a' || xMove.equals("left")){
			xMove = "noX";
		}
		if(e.getKeyChar() == 'd' || xMove.equals("right")){
			xMove = "noX";
		}
		System.out.println(xMove + "," + yMove);
		ssm.sendText(socketID+": "+xMove+","+yMove);
	}
	
	//MouseMotionListener
	@Override
	public void mouseDragged(MouseEvent e){
	}
	//
	public void mouseMoved(MouseEvent e){
	}
	
	//MouseListener
	public void mouseExited(MouseEvent e){
	}
	//
	public void mouseEntered(MouseEvent e){
	}
	//
	public void mouseReleased(MouseEvent e){
	}
	//
	public void mousePressed(MouseEvent e){
	}
	//
	public void mouseClicked(MouseEvent e){
		System.out.println("Clicked: "+e.getX()+","+e.getY());
		ssm.sendText(socketID+" clicked: "+e.getX()+","+e.getY());
	}
	
	
	//---------------------------------------------------------------CONSTRUCTOR---------------------------------------------------------------//
	public GameClient(String setup){
		socketSetup = setup;
		System.out.println(socketSetup);
		
		frame = new JFrame("Animation Keyboard Mouse Test");
			frame.addKeyListener(this);
		panel = new GamePanel();
			panel.setPreferredSize(new Dimension(960,540));
			panel.addMouseMotionListener(this);
			panel.addMouseListener(this);
		timer = new Timer(1000/60, this);
			timer.start();
		
		frame.setContentPane(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
		if(socketSetup.equals("Server")){
			ssm = new SuperSocketMaster(1337, this);
			ssm.connect();
		} else {
			//testing IP
			ssm = new SuperSocketMaster("127.0.0.1",1337, this);
			ssm.connect();
		}
	}
	
	//------------------------------------------------------------------MAIN-------------------------------------------------------------------//
	public static void main (String[] args) {
		String setup;
		//change these to test client/server versions
		//setup = "Server";
		setup = "Client";
		
		new GameClient(setup);
	}
}

