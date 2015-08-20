package guiCluedo.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import guiCluedo.game.Board;

public class BoardCanvas extends Canvas{
	
	private static final long serialVersionUID = 1L;
	Board gameBoard;
	private String[][] board;
	
	public BoardCanvas(Board b)
	{
		this.gameBoard = b;
		setBackground (Color.BLUE);
        setSize(300, 300);
        readFile();
        repaint();
	}
	
	public void paint(Graphics g) {
		Graphics2D g2;
        g2 = (Graphics2D) g;
        g2.drawString ("It is a custom canvas area", 70, 70);
        g.setColor(Color.BLACK);
        g2.fillRect(50, 50, 50, 50);
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
	 * @param - The current row to draw
	 */
	private void drawRow(int row, Graphics g){
		int roomCount = 0;
		for(int i = 0; i < board[row].length; i++){
			if(board[row][i].equals("R")){
				roomCount++;
			}
			else if(roomCount > 0){
				
			}
		}
	}
}