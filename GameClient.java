//imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameClient implements ActionListener,KeyListener,MouseMotionListener,MouseListener{
	//---------------------------------------------------------------PROPERTIES----------------------------------------------------------------//
	SuperSocketMaster ssm;

	JFrame frame;
	GamePanel panel;
	Timer timer;
	
	int Client1ClickX = 0;
	
	//-----------------------------------------------------------------METHODS-----------------------------------------------------------------//
	
	//ActionListener
	public void actionPerformed(ActionEvent e){
		//get Data from server - will be GUI info, use it to draw
		if(e.getSource() == ssm){
			String data = ssm.readText();
			System.out.println("Received data from server: "+data);
			panel.Client1ClickX = Integer.parseInt(data);
			panel.repaint();
		}
	}
	
	
	//KeyListener
	public void keyPressed(KeyEvent e){
	}
	//
	public void keyTyped(KeyEvent e){
	}
	//
	public void keyReleased(KeyEvent e){
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
		ssm.sendText(e.getX()+"");
		System.out.println("Sent data to server: "+e.getX());
	}
	
	//---------------------------------------------------------------CONSTRUCTOR---------------------------------------------------------------//
	public GameClient(){
		
		ssm = new SuperSocketMaster("127.0.0.1",1337, this);
		ssm.connect();
		
		frame = new JFrame("Client");
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
		
	}
}

