import java.io.*;

//These 'Run' files will be combined at the end, and will allow to person to choose Server or Client. separate for testing




public class RUN_GAME{
	public static void main (String args[]) throws IOException{
		
		//replace this with a menu panel, use it to get 'choice' variable
		System.out.println("Server(s) or Client(c):");
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		String choice = keyboard.readLine();

		if(choice.equals("c")){
			System.out.println("Starting a client");
		
			new GameClient();
		} else {
			System.out.println("starting a server");
			ServerThread sT = new ServerThread();
			Thread sThread = new Thread(sT);
			sThread.start();
		
			new GameClient();
		}
		
	}
}

