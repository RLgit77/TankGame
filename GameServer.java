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
			//Split client data
			//Apply Calculation
			String strCData;
			int intUp;
			int intLeft;
			int intDown;
			int intRight;
			
			strCData = ssm.readText();
			String[] clientData = strCData.split(",");
			//Check client IP
			if(strCData[0] == getMyAddress){
				if(strCData[1] == "MvmtUt"){
					intUp = intUp + 10;
				}else if(strCData[1] == "MvmtUf"){
					intUp = intUp;
				}if(strCData[2] == "MvmtLt"){
					intLeft = intLeft + 10;
				}else if(strCData[2] == "MvmtLf"){
					intLeft = intLeft;
				}if(strCData[3] == "MvmtDt"){
					intDown = intDown + 10;
				}else if(strCData[3] == "MvmtDf"){
					intDown = intDown;
				}if(strCData[4] == "MvmtRt"){
					intRight = intRight + 10;
				}else if(strCData[4] == "MvmtRf"){
					intRight = intRight;
				}if(strCData[5] == "Shott"){
					System.out.println("Shot Fired");
				}else if(strCData[5] == "Shotf"){
					System.out.println("Shot Not Fired");
				}if(strCData[6] == mouseX){
					System.out.println("Turret Angled X Coordinate");
				}if(strCData[7] == mouseY){
					System.out.println("Turret Angled Y Coordinate");
				}
			}
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
