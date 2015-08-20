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
        for(int i = 0; i < board.length; i++){
        	drawRow(i, g2);
        }
        
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
	private void drawRow(int row, Graphics2D g){
		int roomFrom = 0;
		int roomTo = 0;
		int centreFrom = 0;
		int centreTo = 0;
		String location = "Hallway";
		for(int i = 0; i < board[row].length; i++){
			if(board[row][i].equals("R") && location.equals("Hallway")){
				roomFrom = roomTo;
				roomTo++;
				location = "Rooom";
			}
			else if(board[row][i].equals("R")){
				roomTo++;
			}
			else if(board[row][i].equals("C") && location.equals("Hallway")){
				centreFrom = centreTo;
				centreTo++;
				location = "Centre";
			}
			else if(board[row][i].equals("C")){
				centreTo++;
			}
			else if(roomTo - roomFrom > 0){
				g.setColor(Color.GRAY);
				g.fillRect(roomFrom*(width/23), row*(height/21), (roomTo - roomFrom)*(width/23), row*(height/21));
				location = "Hallway";
			}
			else if(centreTo - centreFrom > 0){
				g.setColor(Color.RED);
				g.fillRect(centreFrom*(width/23), row*(height/21), (centreTo - centreFrom)*(width/23), row*(height/21));
				location = "Hallway";
			}
		}
	}
}