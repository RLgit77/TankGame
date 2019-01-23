import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class mainGameCode implements ActionListener{
	// Properties
	JFrame frame;
	mainPanel panel;
	JButton client; // Client Button in MM
	JButton server; // Server Button in MM
	JButton help; // ? in MM
	JButton backtomenu; // Arrow Buttons that lead back to MM
	JButton backtocontrols; // Arrow Button that lead to controls
	JButton nexttopandt; // Arrow Button that leads to powerup and tiles
	JButton backtomenu2; // Arrow Buttons that lead back to MM
	JButton clientconnect; //Clicking this button will launch the game with the ip specified in area
	JTextArea thearea; //area for Client to type in Server IP
	Timer timer;
	String IP = "127.0.0.1";
		
	// Methods
	// Button Direct Interaction
	public void actionPerformed(ActionEvent e){
		// IN MAIN MENU:
		if(e.getSource() == client){
			// Insert code which leads to game client screen
			System.out.println("Ready for Server IP");
			//Screens
			panel.blnClientConnect = true;
			panel.blnMM = false;
			// Disable Buttons and show area to type in server IP
			thearea.setVisible(true);
			clientconnect.setVisible(true);
			client.setVisible(false);
			server.setVisible(false);
			help.setVisible(false);
			backtocontrols.setVisible(false);
			nexttopandt.setVisible(false);
			backtomenu.setVisible(false);
			backtomenu2.setVisible(false);
		
		}else if(e.getSource() == server){
			// Insert code which leads to game server screen
			System.out.println("Starting a server");
			ServerThread sT = new ServerThread();
			Thread sThread = new Thread(sT);
			sThread.start();
			try{
				Thread.sleep(500);
			} catch (InterruptedException i){
			}
			IP = sT.serverIP;
			thearea.append(IP);
			//Screens
			panel.blnClientConnect = true;
			panel.blnMM = false;
			// Disable Buttons and show area to type in server IP
			thearea.setVisible(true);
			thearea.setEditable(false); 
			clientconnect.setVisible(true);
			client.setVisible(false);
			server.setVisible(false);
			help.setVisible(false);
			backtocontrols.setVisible(false);
			nexttopandt.setVisible(false);
			backtomenu.setVisible(false);
			backtomenu2.setVisible(false);
			
		//If player enters an ip or presses the arrow in the connect screen
		}else if((e.getSource() == clientconnect) || (e.getSource() == thearea)){
			IP = thearea.getText();
			new GameClient(IP);
			frame.setVisible(false);
			// Disable Buttons
			thearea.setVisible(false);
			clientconnect.setVisible(false);
			client.setVisible(false);
			server.setVisible(false);
			help.setVisible(false);
			backtocontrols.setVisible(false);
			nexttopandt.setVisible(false);
			backtomenu.setVisible(false);
			backtomenu2.setVisible(false);
			frame.setExtendedState(JFrame.ICONIFIED);
		// If you press "?":	
		}else if(e.getSource() == help){
			// Screens:			
			panel.blnMM = false;
			panel.blnControls = true;
			panel.blnPandT = false;
			// Buttons:
			server.setVisible(false);
			client.setVisible(false);
			server.setVisible(false);
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
			server.setVisible(true);
			client.setVisible(true);
			help.setVisible(true);
			backtocontrols.setVisible(false);
			nexttopandt.setVisible(false);
			backtomenu.setVisible(false);
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
			server.setVisible(false);
			client.setVisible(false);
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
			server.setVisible(false);
			client.setVisible(false);
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
			server.setVisible(true);
			client.setVisible(true);
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
	public mainGameCode(){
		frame = new JFrame("Tanks");
		panel = new mainPanel();
		panel.setPreferredSize(new Dimension(1280, 720));
		panel.setLayout(null);
		server = new JButton();
		client = new JButton();
		help = new JButton();
		backtocontrols = new JButton();
		nexttopandt = new JButton();
		backtomenu = new JButton();
		backtomenu2 = new JButton();
		clientconnect = new JButton();
		thearea = new JTextArea();
		// Set Button Sizes:
		server.setSize(300, 120);
		client.setSize(300, 120);
		help.setSize(100, 110);
		backtomenu.setSize(120, 120);
		backtocontrols.setSize(120, 120);
		nexttopandt.setSize(120, 120);
		backtomenu2.setSize(120, 120);
		clientconnect.setSize(100, 100);
		thearea.setSize(500,100);
		//Set Location (x, y):
		server.setLocation(490, 382);
		client.setLocation(490, 250);
		help.setLocation(108, 590);
		backtomenu.setLocation(12, 590);
		backtocontrols.setLocation(12, 590);
		nexttopandt.setLocation(1144, 590);
		backtomenu2.setLocation(1144, 590);
		clientconnect.setLocation(906, 349);	
		thearea.setLocation(350,349);					
		// Button Feature Toggle:
		// Client:
		client.setBorder(null);
		client.setContentAreaFilled(false);
		client.setOpaque(false);
		// Server:
		server.setBorder(null);
		server.setContentAreaFilled(false);
		server.setOpaque(false);
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
		// Client Connect:
		clientconnect.setBorder(null);
		clientconnect.setContentAreaFilled(false);
		clientconnect.setOpaque(false);
		// Adding Buttons to Panel:
		panel.add(server);
		panel.add(client);
		panel.add(help);
		panel.add(backtomenu);
		panel.add(backtocontrols);
		panel.add(nexttopandt);
		panel.add(backtomenu2);
		panel.add(clientconnect);
		panel.add(thearea);
		//Making the IP show up in nice big font
		Font font = new Font("Helvetica", Font.PLAIN, 50);
		thearea.setFont(font);
		// Preset these buttons to false so they dont show up
		backtocontrols.setVisible(false);
		nexttopandt.setVisible(false);
		backtomenu.setVisible(false);
		backtomenu2.setVisible(false);
		clientconnect.setVisible(false);
		thearea.setVisible(false);
		// Adding ActionListener to Buttons:
		server.addActionListener(this);
		client.addActionListener(this);
		help.addActionListener(this);
		backtomenu.addActionListener(this);
		nexttopandt.addActionListener(this);
		backtocontrols.addActionListener(this);
		backtomenu2.addActionListener(this);
		clientconnect.addActionListener(this);
		frame.setContentPane(panel);
		frame.setResizable(false);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		timer = new Timer(1000/60, this);
		timer.start();
		
	}
	// Main Method
	public static void main(String[] args){
		mainGameCode mm = new mainGameCode();
	}
}
