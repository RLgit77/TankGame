import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.Font.*;

public class mainPanel extends JPanel{
	// Properties
	boolean blnStart = false;
	boolean blnMM = true;
	boolean blnHelp = false;
	// Methods
	public void paintComponent(Graphics g){
		// Background
		if(blnMM == true){
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 1280, 720);
		}else if(blnHelp == true){
			g.setColor(Color.RED);
			g.fillRect(0, 0, 1280, 720);
		}
	}
	// Constructors
	public mainPanel(){
		super();
	}
}
