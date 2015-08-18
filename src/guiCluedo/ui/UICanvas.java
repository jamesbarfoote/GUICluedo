package guiCluedo.ui;

import java.awt.Canvas;
import java.awt.Graphics;

import guiCluedo.game.Board;

public class UICanvas extends Canvas{
	Board gameBoard;
	public UICanvas(Board b)
	{
		this.gameBoard = b;
	}
	
	public void paint(Graphics g) {
		
	}

}
