import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class mainMenu implements ActionListener{
	// Properties
	JFrame frame;
	mainPanel panel;
	JButton play;
	JButton help;
	JButton back;
	Timer timer;
	
	
	// Methods
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == play){
			// Insert code which leads to game client screen
			System.out.println("Running Game");
		}else if(e.getSource() == help){
			panel.blnHelp = true;
			panel.blnMM = false;
			play.setVisible(false);
			help.setVisible(false);
			back.setVisible(true);
			System.out.println("Initializing Help");				
		}else if(e.getSource() == back){
			panel.blnMM = true;
			play.setVisible(true);
			help.setVisible(true);
			back.setVisible(false);
		}
		if(e.getSource() == timer){
			panel.repaint();
		}		
	}
			
	
	// Constructors
	public mainMenu(){
		frame = new JFrame("Tanks");
		panel = new mainPanel();
		panel.setPreferredSize(new Dimension(1280, 720));
		panel.setLayout(null);
		play = new JButton();
		help = new JButton();
		back = new JButton();
		play.setSize(320, 80);
		help.setSize(320, 80);
		back.setSize(320, 80);
		
		play.setLocation(480, 300);
		help.setLocation(480, 450);
		back.setLocation(920, 620);
		
		play.setOpaque(false);
		play.setContentAreaFilled(false);
		play.setBorderPainted(true);
		//play.setFocusPainted(false);
		
		
		
		help.setFocusPainted(false);
		help.setContentAreaFilled(false);
		help.setOpaque(false);
		
		back.setFocusPainted(false);
		back.setContentAreaFilled(false);
		back.setOpaque(false);
		
		panel.add(play);
		panel.add(help);
		panel.add(back);
		
		play.addActionListener(this);
		help.addActionListener(this);
		back.addActionListener(this);

		
		frame.setContentPane(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		back.setVisible(false);
		
		timer = new Timer(1000/60, this);
		timer.start();
		
	}
	// Main Method
	public static void main(String[] args){
		new mainMenu();
	}
}
	



