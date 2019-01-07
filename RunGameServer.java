

//These 'Run' files will be combined at the end, and will allow to person to choose Server or Client. separate for testing




public class RunGameServer{
	public static void main (String args[]){
		
		ServerThread sT = new ServerThread();
		Thread sThread = new Thread(sT);
		sThread.start();
		
		new GameClient();
		
	}
}

