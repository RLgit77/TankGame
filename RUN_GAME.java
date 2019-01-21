import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RUN_GAME implements ActionListener{
	// Properties
	JFrame theframe;
	JPanel thepanel;
	JButton theClientButton;
	JButton theServerButton;
	JLabel thelabel;
	
	
	
	// Methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == theClientButton){ 
			System.out.println("Starting a client");
			new mainGameCode();
			theframe.setState(Frame.ICONIFIED);
		}
		else if(evt.getSource() == theServerButton){ 
			thepanel.repaint();
			System.out.println("Starting a server");
			ServerThread sT = new ServerThread();
			Thread sThread = new Thread(sT);
			sThread.start();
			new mainGameCode();
			theframe.setState(Frame.ICONIFIED);
		}
	}
	
	
	//Constructor
	public RUN_GAME(){
		  theframe = new JFrame("Choose Client or Server for Tank Game");
		  thepanel = new JPanel();
		  thepanel.setPreferredSize(new Dimension(400, 200));
		  thepanel.setLayout(null);
		  
		  
		  theClientButton = new JButton("Click to run Client");
		  theServerButton = new JButton("Click to run Server");
		  theClientButton.setSize(300, 50);
		  theServerButton.setSize(300, 50);
		  theClientButton.addActionListener(this);
		  theServerButton.addActionListener(this);
		  theClientButton.setLocation(50, 50);
		  theServerButton.setLocation(50, 100);
		  
		  thepanel.add(theClientButton);
		  thepanel.add(theServerButton);
		  theframe.setContentPane(thepanel);
		  theframe.pack();
		  theframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  theframe.setVisible(true);
		
	}
	
	// Main Method
	public static void main(String[] args){
		new RUN_GAME();
	}
}


