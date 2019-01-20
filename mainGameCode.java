import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class mainMenu implements ActionListener{
	// Properties
	JFrame frame;
	mainPanel panel;
	JButton play; // Play Button in MM
	JButton help; // ? in MM
	JButton backtomenu; // Arrow Buttons that lead back to MM
	JButton backtocontrols; // Arrow Button that lead to controls
	JButton nexttopandt; // Arrow Button that leads to powerup and tiles
	JButton backtomenu2; // Arrow Buttons that lead back to MM
	Timer timer;
	
	
	// Methods
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == play){
			// Insert code which leads to game client screen
			// Set help button to false so there is no interferences
			System.out.println("Running Game");
			// If you press "?":	
		}else if(e.getSource() == help){
			// Screens:			
			panel.blnMM = false;
			panel.blnControls = true;
			panel.blnPandT = false;
			// Buttons:
			play.setVisible(false);
			help.setVisible(false);
			backtocontrols.setVisible(false);
			nexttopandt.setVisible(true);
			backtomenu.setVisible(true);
			backtomenu2.setVisible(false);
			// Test:
			System.out.println("You are in Controls");
			// If you press "<--" in controls:				
		}else if(e.getSource() == backtomenu){
			// Screens:			
			panel.blnMM = true;
			panel.blnControls = false;
			panel.blnPandT = false;
			// Buttons:
			play.setVisible(true);
			help.setVisible(true);
			backtocontrols.setVisible(false);
			nexttopandt.setVisible(true);
			backtomenu.setVisible(true);
			backtomenu2.setVisible(false);
			// Test:
			System.out.println("You are in Main Menu");
			// If you press "-->" in controls:*/
		}else if(e.getSource() == nexttopandt){
			// Screens:			
			panel.blnMM = false;
			panel.blnControls = false;
			panel.blnPandT = true;
			// Buttons:
			play.setVisible(false);
			help.setVisible(false);
			backtocontrols.setVisible(true);
			nexttopandt.setVisible(false);
			backtomenu.setVisible(false);
			backtomenu2.setVisible(true);
			// Test:
			System.out.println("You are in Powerup and Tiles");
			// If you press "<--" in powerup and tiles:
		}else if(e.getSource() == backtocontrols){
			// Screens:			
			panel.blnMM = false;
			panel.blnControls = true;
			panel.blnPandT = false;
			// Buttons:
			play.setVisible(false);
			help.setVisible(false);
			backtocontrols.setVisible(false);
			nexttopandt.setVisible(true);
			backtomenu.setVisible(true);
			backtomenu2.setVisible(false);
			// Test:
			System.out.println("You are in Powerup and Tiles");
		}else if(e.getSource() == backtomenu2){
			// Screens:			
			panel.blnMM = true;
			panel.blnControls = false;
			panel.blnPandT = false;
			// Buttons:
			play.setVisible(true);
			help.setVisible(true);
			backtocontrols.setVisible(false);
			nexttopandt.setVisible(false);
			backtomenu.setVisible(false);
			backtomenu2.setVisible(false);
			// Test:
			System.out.println("You are in Main Menu");
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
		backtocontrols = new JButton();
		nexttopandt = new JButton();
		backtomenu = new JButton();
		backtomenu2 = new JButton();
		// Set Button Sizes:
		play.setSize(300, 200);
		help.setSize(120, 120);
		backtomenu.setSize(120, 120);
		backtocontrols.setSize(120, 120);
		nexttopandt.setSize(120, 120);
		backtomenu2.setSize(120, 120);
		//Set Location (x, y):
		play.setLocation(490, 260);
		help.setLocation(95, 590);
		backtomenu.setLocation(12, 590);
		backtocontrols.setLocation(12, 590);
		nexttopandt.setLocation(1144, 590);
		backtomenu2.setLocation(1144, 590);
		// Button Feature Toggle:
		// Play:
		play.setBorder(null);
		play.setContentAreaFilled(false);
		play.setOpaque(false);
		// Help:
		help.setBorder(null);
		help.setContentAreaFilled(false);
		help.setOpaque(false);
		// Back to Menu:
		backtomenu.setBorder(null);
		backtomenu.setContentAreaFilled(false);
		backtomenu.setOpaque(false);
		backtomenu2.setBorder(null);
		backtomenu2.setContentAreaFilled(false);
		backtomenu2.setOpaque(false);
		// Controls:
		backtocontrols.setBorder(null);
		backtocontrols.setContentAreaFilled(false);
		backtocontrols.setOpaque(false);
		// Powerup and Tiles:
		nexttopandt.setBorder(null);
		nexttopandt.setContentAreaFilled(false);
		nexttopandt.setOpaque(false);
		// Adding Buttons to Panel:
		panel.add(play);
		panel.add(help);
		panel.add(backtomenu);
		panel.add(backtocontrols);
		panel.add(nexttopandt);
		panel.add(backtomenu2);
		// Adding ActionListener to Buttons:
		play.addActionListener(this);
		help.addActionListener(this);
		backtomenu.addActionListener(this);
		nexttopandt.addActionListener(this);
		backtocontrols.addActionListener(this);
		backtomenu2.addActionListener(this);
		frame.setContentPane(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		timer = new Timer(1000/60, this);
		timer.start();
		
	}
	// Main Method
	public static void main(String[] args){
		new mainMenu();
	}
}

