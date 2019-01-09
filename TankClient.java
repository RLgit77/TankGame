//Tank Client

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class TankClient implements ActionListener, KeyListener, MouseMotionListener, MouseListener{
	
	//Properties
	JFrame theframe = new JFrame("Network Client");
	TankClientPanel thepanel;
	
	
	JTextArea thearea = new JTextArea();
	JScrollPane thescroll = new JScrollPane(thearea);
	JTextField thefield = new JTextField();
	
	SuperSocketMaster ssm;
	
	String finalSentData = ",,,,,,";
	String MvmtU = "MvmtUf";
	String MvmtL = "MvmtLf";
	String MvmtD = "MvmtDf";
	String MvmtR = "MvmtRf";
	String sentMvmtInput = MvmtU + MvmtL + MvmtD + MvmtR;
	String ifShot = "Shotf";
	String mouseX = "";
	String mouseY = "";
	Timer thetimer;
	
	boolean blnGoUp;
	boolean blnGoLeft;
	boolean blnGoDown;
	boolean blnGoRight;
	

	//-----------------------------------------------------------------METHODS-----------------------------------------------------------------//
	
	
	public void actionPerformed(ActionEvent evt){
		//DATA Receiving 
		//Network Code: Receive Data
		if(evt.getSource() == ssm){
			String strData;
			strData = ssm.readText();
		}
		//60 FPS Repaint
		if(evt.getSource() == thetimer){ // If timer is triggered, it will repaint.
			thepanel.repaint();
			
		}
	}
	
	
	//Movement Keys
	public void keyPressed(KeyEvent evt){ 
		if(evt.getKeyCode() == 87){ // W Key
			MvmtU = "MvmtUt,";
		}
		else if(evt.getKeyCode() == 65){ // A Key
			MvmtL = "MvmtLt,";
		}
		else if(evt.getKeyCode() == 83){ // S Key
			MvmtD = "MvmtDt,";
		}
		else if(evt.getKeyCode() == 68){ // D Key
			MvmtR = "MvmtRt,";
		}
		sentMvmtInput = MvmtU + MvmtL + MvmtD + MvmtR;
		finalSentData = sentMvmtInput + ifShot + mouseX + mouseY;
		ssm.sendText(finalSentData);
		System.out.println(finalSentData);
	}
	
	public void keyReleased(KeyEvent evt){ 
		if(evt.getKeyCode() == 87){ // W Key
			MvmtU = "MvmtUf,";
		}
		else if(evt.getKeyCode() == 65){ // A Key
			MvmtL = "MvmtLf,";
		}
		else if(evt.getKeyCode() == 83){ // S Key
			MvmtD = "MvmtDf,";
		}
		else if(evt.getKeyCode() == 68){ // D Key
			MvmtR = "MvmtRf,";
		}
		sentMvmtInput = MvmtU + MvmtL + MvmtD + MvmtR;
		finalSentData = sentMvmtInput + ifShot + mouseX + mouseY;
		ssm.sendText(finalSentData);
		System.out.println(finalSentData);
	}
	//Mouse Location
	public void mouseMoved(MouseEvent evt){
		mouseX = Integer.toString(evt.getX()); 
		mouseY = Integer.toString(evt.getY()); 
		mouseX = mouseX + ",";
		mouseY = mouseY + ",";
		finalSentData = finalSentData + sentMvmtInput + ifShot + mouseX + mouseY;
		ssm.sendText(finalSentData);
		System.out.println(finalSentData);
	}
	//Shots fired
	public void mouseClicked(MouseEvent e){
		ifShot = "Shott,";
		finalSentData = finalSentData + sentMvmtInput + ifShot + mouseX + mouseY;
		ssm.sendText(finalSentData);
		System.out.println(finalSentData);
		ifShot = "Shotf,";
	}
	
	//OVERRIDE
	public void keyTyped(KeyEvent e){
	}
	public void mouseDragged(MouseEvent e){
	}
	public void mouseExited(MouseEvent e){
	}
	public void mouseEntered(MouseEvent e){
	}
	public void mouseReleased(MouseEvent e){
	}
	public void mousePressed(MouseEvent e){
	}
	
	
	
	
	
	
	
	
	
	
	
	
	//Constructor
	public TankClient(){
		theframe = new JFrame("Animation With Keyboard And Mouse");
		thepanel = new TankClientPanel();
		thepanel.setLayout(null);
		thepanel.setPreferredSize(new Dimension(1290, 720));
		thepanel.addMouseMotionListener(this); 
		thepanel.addMouseListener(this);
		
		theframe.addKeyListener(this); 
		theframe.setContentPane(thepanel);
		theframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theframe.pack();
		theframe.setVisible(true);
		
		thetimer = new Timer(1000/60, this); // Triggering timer every 1000/60. Basically 60 FPS.
		thetimer.start();
		
		ssm = new SuperSocketMaster("10.8.12.217",6112, this);
		ssm.connect();
	}
	
	//Main Method
	public static void main (String[] args) {
		new TankClient();
	}
}


