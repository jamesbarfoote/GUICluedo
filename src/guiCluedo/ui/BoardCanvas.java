package guiCluedo.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import guiCluedo.game.Board;
import guiCluedo.game.Player;

public class BoardCanvas extends Canvas{
	
	private static final long serialVersionUID = 1L;
	Board gameBoard;
	private String[][] board;
	private ArrayList<Player> players;
	int width, height;
	
	public BoardCanvas(Board b, int width, int height){
		this.gameBoard = b;
		board = b.getBoard();
		this.players = b.getPlayers();
		this.width = width;
		this.height = height;
		setBackground (Color.YELLOW);
        setSize(width-13, height-17);
        readFile();
	}
	
	public void paint(Graphics g) {
		Graphics2D g2;
        g2 = (Graphics2D) g;
        setSize(width-13, height-17);
        drawBoard(g2);
        drawIcons(g2);
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
	 *Draws the board, for every square it draws the corresponding colour.
	 *@param g - The graphics object
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
	
	private void drawIcons(Graphics2D g){
		for(Player p : players){
			g.setColor(p.getColor());
			g.fillOval((int) p.getLocation().getX()*(width/23), (int) p.getLocation().getY()*(height/21), width/23, height/21);
		}
	}
	
	public void setWidth(int width){
		this.width = width;
	}
	
	public void setHeight(int height){
		this.height = height;
	}
}