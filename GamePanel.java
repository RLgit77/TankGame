import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class GamePanel extends JPanel{
	
	//---------------------------------------------------------------PROPERTIES----------------------------------------------------------------//
	
	int Client1ClickX = -1;
	
	//-----------------------------------------------------------------METHODS-----------------------------------------------------------------//
	public void paintComponent(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(400,400,0,0);
		
		if(Client1ClickX != -1){
			g.setColor(Color.BLUE);
			g.fillRect(Client1ClickX-10,50,20,20);
		}
		
	}
	
	//---------------------------------------------------------------CONSTRUCTOR---------------------------------------------------------------//
	public GamePanel(){
		super();
	}
}

