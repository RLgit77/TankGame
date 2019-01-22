import java.io.*;

public class RUN_GAME{
	
	public static void main (String args[]) throws IOException{
		//replace this with a menu panel, use it to get 'choice' variable
		System.out.println("Server(s), Client(c) or debug IP (enter an IP string):");
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		String choice = keyboard.readLine();

		if(choice.equals("c")){
			System.out.println("Starting a client");
			new GameClient();
		} else if(choice.equals("s")){
			System.out.println("starting a server");
			ServerThread sT = new ServerThread();
			Thread sThread = new Thread(sT);
			sThread.start();
			new GameClient();
		} else {
			System.out.println("Client IP set as: "+choice);
			new GameClient(choice);
		}
	}
}

