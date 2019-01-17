import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import java.awt.Font.*;

public class mainPanel extends JPanel{
	// Properties
	boolean blnStart = false;
	boolean blnMM = true;
	boolean blnHelp = false;
	BufferedImage mainmenu;
	// Methods
	public void paintComponent(Graphics g){
		// Background
		if(blnMM == true){
			g.drawImage(mainmenu, 0, 0, null);
		}else if(blnHelp == true){
			g.setColor(Color.RED);
			g.fillRect(0, 0, 1280, 720);
		}
	}
	// Constructors
	public mainPanel(){
		super();
		try{
			mainmenu = ImageIO.read(new File("Tank Menu.png"));
		}catch(IOException e){
			System.out.println("Unable to load image");
		}
	}
}

