public class ServerThread implements Runnable{
	//props
	public String serverIP = "empty";

	//method
	public void action(){
		GameServer server = new GameServer();
		serverIP = server.ServerIP;
	}

	public void run(){
		this.action();
	}

	//constructor
	public ServerThread(){
	}

}
