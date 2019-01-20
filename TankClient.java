//Tank Client

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class TankClient implements ActionListener, KeyListener, MouseMotionListener, MouseListener{
	
	//Properties
	JFrame theframe = new JFrame("HoverTank Client");
	TankClientPanel thepanel;
	
	
	
	SuperSocketMaster ssm;
	
	String finalSentData = "";
	String MvmtU = "MvmtUf,";
	String MvmtL = "MvmtLf,";
	String MvmtD = "MvmtDf,";
	String MvmtR = "MvmtRf,";
	String sentMvmtInput = MvmtU + MvmtL + MvmtD + MvmtR;
	String ifShot = "Shotf,";
	String mouseX = "640";
	String mouseY = "360";
	String UserIP = "";
	Timer thetimer;
	
	boolean blnDown = false;
	boolean blnUp = false;
	boolean blnLeft = false;
	boolean blnRight = false;
	

	//-----------------------------------------------------------------METHODS-----------------------------------------------------------------//
	
	
	public void actionPerformed(ActionEvent evt){
		//DATA Receiving 
		//Network Code: Receive Data, input into proper variables, send to graphics panel to draw
		if(evt.getSource() == ssm){
			String strData;
			strData = ssm.readText();
			String[] receivedData = strData.split(",");
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
			//sentMvmtInput = MvmtU;
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
		finalSentData = UserIP +","+ sentMvmtInput + ifShot + mouseX + mouseY;
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
		UserIP = ssm.getMyAddress();
		finalSentData = UserIP +","+ sentMvmtInput + ifShot + mouseX + mouseY;
		ssm.sendText(finalSentData);
		System.out.println(finalSentData);
	}
	//Shots fired
	//Initializes sent ip when first clicked
	public void mouseClicked(MouseEvent e){
		ifShot = "Shott,";
		finalSentData = finalSentData + UserIP + sentMvmtInput + ifShot + mouseX + mouseY;
		ssm.sendText(finalSentData);
		System.out.println(finalSentData);
		ifShot = "Shotf,";
		UserIP = ssm.getMyAddress();
	}
	//Mouse Location
	public void mouseMoved(MouseEvent evt){
		mouseX = Integer.toString(evt.getX()); 
		mouseY = Integer.toString(evt.getY()); 
		mouseX = mouseX + ",";
		mouseY = mouseY + "";
		finalSentData = finalSentData + UserIP + sentMvmtInput + ifShot + mouseX + mouseY;
		ssm.sendText(finalSentData);
		System.out.println(finalSentData);
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
		thepanel = new TankClientPanel();
		thepanel.setLayout(null);
		thepanel.setPreferredSize(new Dimension(1280, 720));
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


