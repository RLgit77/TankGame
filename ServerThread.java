public class ServerThread implements Runnable{
	//props

	//method
	public void action(){
		new GameServer();
	}

	public void run(){
		this.action();
	}

	//constructor
	public ServerThread(){
	}

}
