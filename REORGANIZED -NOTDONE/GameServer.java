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
			
			//Split client data
			//Apply Calculation
			String strCData;
			boolean blnUp;
			boolean blnLeft;
			boolean blnDown;
			boolean blnRight;
			
			strCData = ssm.readText();
			String[] splitData = strCData.split(",");
			//Check client IP
			if(splitData[0] == ssm.getMyAddress()){
				if(splitData[1] == "MvmtUt"){
					blnUp = true;
				}else if(splitData[1] == "MvmtUf"){
					blnUp = false;
				}if(splitData[2] == "MvmtLt"){
					blnLeft = true;
				}else if(splitData[2] == "MvmtLf"){
					blnLeft = false;
				}if(splitData[3] == "MvmtDt"){
					blnDown = true;
				}else if(splitData[3] == "MvmtDf"){
					blnDown = false;
				}if(splitData[4] == "MvmtRt"){
					blnRight = true;
				}else if(splitData[4] == "MvmtRf"){
					blnRight = false;
				}if(splitData[5] == "Shott"){
					System.out.println("Shot Fired");
				}else if(splitData[5] == "Shotf"){
					System.out.println("Shot Not Fired");
				}if(splitData[6].substring(0,2) == "mX"){
					System.out.println("Mouse X:" + splitData[6].substring(2,splitData.length));
				}if(splitData[7].substring(0,2) == "mY"){
					System.out.println("Mouse Y:" + splitData[6].substring(2,splitData.length));
				}
			}
		}

		if(e.getSource() == timer){
			//use vars here, calculate movement and send to clients
			//for example, if blnUp is true, move up
			
			//fill this with map values
			String mapData = "TankMap.txt";
			ssm.sendText(mapData);
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
