package guiCluedo.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import guiCluedo.game.Board;
import guiCluedo.game.Card;
import guiCluedo.game.Player;

public class HandCanvas extends Canvas implements MouseListener{

	private static final long serialVersionUID = 1L;
	private Board gameBoard;
	private int width;
	private int height;
	private ArrayList<Card> hand = new ArrayList<Card>();
	private Player p;
	private Card cardClicked;

	public HandCanvas(Board b, int width, int height, Player player)
	{
		this.addMouseListener(this);
		this.gameBoard = b;
		this.height = height;
		this.width = width;
		this.p = player;
		setBackground (Color.BLUE);
		setSize(width, height);
		hand = player.getHand();
		System.out.println("Card 1 = " + hand.get(0).getName());
		System.out.println("Card 2 = " + hand.get(1).getName());
		repaint();
	}
	
	public void setHand(Player p)
	{
		this.hand = p.getHand();
	}
	
	private static final String IMG_PATH = "src/guiCluedo/images/";

	public void paint(Graphics g) {
		for(int i = 0; i < hand.size(); i++){
			try {
				BufferedImage myPicture = ImageIO.read(new File(IMG_PATH + hand.get(i).getName() + ".jpg"));
				int cWidth = width / hand.size();
				BufferedImage scaled = getScaledImage(myPicture, cWidth, height);
				g.drawImage(scaled, cWidth * i,0, getParent());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

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
		setSize(this.width, this.height);
	}
	
	public void setHeight(int height){
		this.height = height;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Get the clicked location
		int x = e.getX();
		int y = e.getY();
		
		//Get the starting x and y location of the hand area
		int handX = 0;
		int handY = 0;
		int maxY = 80;
		
		//Get the width of each card
		ArrayList<Card> hand = p.getHand();
		int numCards = hand.size();
		int cardSize = this.getWidth() / numCards;
		//Figure out which card was clicked
			//for each card in the hand
		for(int i = 0; i < hand.size(); i++)
		{
			int currentX = (cardSize * i) + handX;
				if(x >= currentX && x <= currentX + cardSize)
				{
					if(y >= handY && y <= maxY)
					{
						cardClicked = hand.get(i);
						System.out.println("Clicked card = " + cardClicked.getName());
					}
				}
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	
	public Card getSelectedCard()
	{
		return cardClicked;
	}
	
}