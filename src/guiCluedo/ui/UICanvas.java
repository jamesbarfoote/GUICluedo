package guiCluedo.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import guiCluedo.game.Board;

public class UICanvas extends Canvas{
	Board gameBoard;
	public UICanvas(Board b)
	{
		this.gameBoard = b;
		System.out.println("Reached");
		setBackground (Color.BLUE);
        setSize(300, 300);
        repaint();
	}
	
	public void paint(Graphics g) {
		System.out.println("Reached 2");
		Graphics2D g2;
        g2 = (Graphics2D) g;
        g2.drawString ("It is a custom canvas area", 70, 70);
        g.setColor(Color.BLACK);
        g2.fillRect(50, 50, 50, 50);
	}

}