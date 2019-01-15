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
			thepanel.repaint();
			thepanel.setVisible(false);
			System.out.println("Starting a client");
			new GameClient();
		}
		else if(evt.getSource() == theServerButton){ 
			thepanel.repaint();
			System.out.println("starting a server");
			ServerThread sT = new ServerThread();
			Thread sThread = new Thread(sT);
			sThread.start();
		
			new GameClient();
		}
	}
	
	
	public RUN_GAME(){
		theframe = new JFrame("Tank Game");
		thepanel = new JPanel();
		thepanel.setPreferredSize(new Dimension(1280, 720));
		thepanel.setLayout(null);
		theClientButton = new JButton("Client");
		theClientButton.setLocation(320, 150);
		theClientButton.addActionListener(this);
		theServerButton = new JButton("Client");
		theServerButton.setLocation(620, 150);
		theServerButton.addActionListener(this);
		thelabel = new JLabel("Server or Client");
		thelabel.setLocation(450, 600);
	}
	
	
	public static void main (String args[]){
		
		
	}
}

