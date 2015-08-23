package guiCluedo.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import guiCluedo.game.Board;

public class HandCanvas extends Canvas{
	
	private Board gameBoard;
	private int width;
	private int height;

	public HandCanvas(Board b, int width, int height)
	{
		this.gameBoard = b;
		this.height = height;
		this.width = width;
		setBackground (Color.BLUE);
        setSize(width, height);
        repaint();
	}
	
	public void paint(Graphics g) {
        try {
			BufferedImage myPicture = ImageIO.read(new File("hall.jpg"));
			g.drawImage(myPicture, 0,0, getParent());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}

}