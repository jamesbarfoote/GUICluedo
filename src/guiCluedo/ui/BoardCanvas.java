package guiCluedo.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import guiCluedo.game.Board;

public class BoardCanvas extends Canvas{
	
	private static final long serialVersionUID = 1L;
	Board gameBoard;
	private String[][] board = new String[21][23];
	int width, height;
	
	public BoardCanvas(Board b, int width, int height)
	{
		this.gameBoard = b;
		this.width = width;
		this.height = height;
		setBackground (Color.YELLOW);
        setSize(width, height);
        readFile();
        for(int i = 0; i < board.length; i++){
        	for(int j = 0; j < board[i].length; j++){
        		System.out.printf(board[i][j]);
        	}
        	System.out.println();
        }
        repaint();
	}
	
	public void paint(Graphics g) {
		Graphics2D g2;
        g2 = (Graphics2D) g;
        drawBoard(g2);
        
	}

	@SuppressWarnings("resource")
	private void readFile(){
		File boardFile = new File("CluedoBoard.txt");
		try {
			Scanner scan = new Scanner(boardFile);
			int lineNum = 0;
			while(scan.hasNextLine()){
				String line = scan.nextLine();
				Scanner lineSc = new Scanner(line);
				int rowNum = 0;
				
				while(lineSc.hasNext()){
					
					String value = lineSc.next();
					if(value.equals("R")){
						board[lineNum][rowNum] = value;
					}
					else if(value.equals("W")){
						board[lineNum][rowNum] = value;
					}
					else if(value.equals("C")){
						board[lineNum][rowNum] = value;
					}
					else{
						throw new ArrayStoreException();
					}
					rowNum++;
				}
				lineNum++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Draws a row of the board. There are 21 rows 
	 * equal to the height of the board.
	 * @param row - The current row to draw
	 * @param g - The graphics object
	 */
	private void drawBoard(Graphics2D g){
		for(int i = 0; i < board.length; i++){
        	for(int j = 0; j < board[i].length; j++){
        		if(board[i][j].equals("R")){
        			g.setColor(Color.GRAY);
        			g.fillRect(j*(width/23), i*(height/21), width/23, height/21);
        		}
        		else if(board[i][j].equals("C")){
        			g.setColor(Color.RED);
        			g.fillRect(j*(width/23), i*(height/21), width/23, height/21);
        		}
        	}
        }
	}
}