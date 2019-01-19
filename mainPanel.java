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
	boolean blnControls = false;
	boolean blnPandT = false;
	BufferedImage mainmenu;
	BufferedImage control;
	BufferedImage pandt;
	// Methods
	public void paintComponent(Graphics g){
		// Background
		if(blnMM == true){
			g.drawImage(mainmenu, 0, 0, null);
		}else if(blnControls == true){
			g.drawImage(control, 0, 0, null);
		}else if(blnPandT == true){
			g.drawImage(pandt, 0, 0, null);
		}
	}
	// Constructors
	public mainPanel(){
		super();
		try{
			mainmenu = ImageIO.read(new File("Tank Menu.png"));
			control = ImageIO.read(new File("Tank Controls.png"));
			pandt = ImageIO.read(new File("Tank PandT.png"));
		}catch(IOException e){
			System.out.println("Unable to load image");
		}
	}
}
