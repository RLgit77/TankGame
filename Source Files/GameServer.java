//imports
import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;

public class GameServer implements ActionListener{

	//---------------------------------------------------------------PROPERTIES----------------------------------------------------------------//

	//ssm variables
	SuperSocketMaster ssm;
	String data;
	String ServerIP;
	
	//ID players - list is initially empty
	String[] savedIPs = new String[4];
	{Arrays.fill(savedIPs, "empty");}
	
	
	//-----------------------------------------------------------------METHODS-----------------------------------------------------------------//
	


	//ActionListener
	public void actionPerformed(ActionEvent e){
		//data received
		if(e.getSource() == ssm){
			data = ssm.readText();
			System.out.println("Server received: "+data);
			String[] split = data.split(",");
			
			//check IP against saved list, assign ID and replace in string
			//player 0
			for(int i = 0; i < 4; i++){
				System.out.println("for loop iteration "+i);
				if(split[0].equals(savedIPs[i])){
					//replace IP with i
					split[0] = i+"";
					data = String.join(",", split);
					break;
				}else if(savedIPs[i].equals("empty")){
					System.out.println("player "+i+" ip set, was "+savedIPs[i]);
					savedIPs[i] = split[0];
					//tell player what ID they are (just for centering panel, data is still formatted here)
					ssm.sendText("playerID,"+split[0]+","+i);
					//IP is set as player i
					split[0] = i+"";
					data = String.join(",", split);
					
					break;
				}
			}
			
			//ADD "server," in front so it is used by clients
			data = "formatted,"+data;
			
			ssm.sendText(data);
			
		}
		
	}



	//---------------------------------------------------------------CONSTRUCTOR---------------------------------------------------------------//
	public GameServer(){

		ssm = new SuperSocketMaster(1337, this);
		ssm.connect();
		ServerIP = ssm.getMyAddress();
		System.out.println("Server launched at: "+ServerIP);


	}
}
