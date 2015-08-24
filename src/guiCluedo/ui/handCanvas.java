package guiCluedo.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import guiCluedo.game.Board;
import guiCluedo.game.Card;
import guiCluedo.game.Player;

public class HandCanvas extends Canvas{

	private Board gameBoard;
	private int width;
	private int height;
	private ArrayList<Card> hand = new ArrayList<Card>();

	public HandCanvas(Board b, int width, int height, Player player)
	{
		this.gameBoard = b;
		this.height = height;
		this.width = width;
		setBackground (Color.BLUE);
		setSize(500, 500);
		//hand = player.getHand();
		repaint();
	}

	public void paint(Graphics g) {
		//for(int i = 0; i < hand.size(); i++){
			try {
				//BufferedImage myPicture = ImageIO.read(new File("images/" + "hand.get(i)" + ".jpg"));
				BufferedImage myPicture = ImageIO.read(new File("hall.jpg"));
				//int cWidth = width / hand.size();
				int cWidth = width;
				BufferedImage scaled = getScaledImage(myPicture, cWidth, height);
				g.drawImage(scaled, 0,0, getParent());
				//g.drawImage(scaled, cWidth,0, getParent());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//}

	}
	
	//Scale the image
	private BufferedImage getScaledImage(Image img, int w, int h){
	    BufferedImage resized = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
	    Graphics2D g2 = resized.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(img, 0, 0, w, h, null);
	    g2.dispose();
	    return resized;
	}
	
	public void setWidth(int width){
		this.width = width;
	}
	
	public void setHeight(int height){
		this.height = height;
	}
	
}