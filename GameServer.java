//just the server - gets client's inputs, sends them back out to other clients (maybe some logic too)

//whoever chooses to be the server runs this in a thread and then connects to it AS A CLIENT themselves


//imports
import javax.swing.*;
import java.awt.event.*;

public class GameServer implements ActionListener{

	//---------------------------------------------------------------PROPERTIES----------------------------------------------------------------//

	SuperSocketMaster ssm;
	Timer timer;

	//used to get variables from string - we'll need to hard code more or use a hashmap if we have multiple variables for players/bullets
	String C1ClickX = "0";
	//-----------------------------------------------------------------METHODS-----------------------------------------------------------------//


	//ActionListener
	public void actionPerformed(ActionEvent e){
		//data received
		if(e.getSource() == ssm){
			//when data arrives, check it for variables
			String data = ssm.readText();
			System.out.println("Recieved data from client: "+data);
			ssm.sendText(data);
		}

		if(e.getSource() == timer){
			//use vars here, calculate movement and send to clients

		}
	}



	//---------------------------------------------------------------CONSTRUCTOR---------------------------------------------------------------//
	public GameServer(){

		timer = new Timer(1000/60, this);
		timer.start();

		ssm = new SuperSocketMaster(1337, this);
		ssm.connect();

	}
}
